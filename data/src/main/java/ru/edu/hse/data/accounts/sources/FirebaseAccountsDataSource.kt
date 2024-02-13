package ru.edu.hse.data.accounts.sources

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity
import ru.edu.hse.data.accounts.exceptions.AccountAlreadyExistsDataException

class FirebaseAccountsDataSource : AccountsDataSource {

    private val auth = Firebase.auth

    private val db = Firebase.firestore

    private val logger = Core.logger

    private val authStateFlow = MutableStateFlow<String?>(null)

    init {
        auth.addAuthStateListener {
            authStateFlow.value = it.currentUser?.uid
        }
    }

    override fun listenAuthState(): Flow<String?> {
        return authStateFlow
    }

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                signInCompletion(task.isSuccessful)
            }
    }

    private fun signInCompletion(isSuccessful: Boolean) {
        if (isSuccessful) {
            logger.log("signIn:success")
        } else {
            logger.log("signIn:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        auth.createUserWithEmailAndPassword(signUpData.email, signUpData.password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val firestoreSignUpData = hashMapOf(
                        KEY_EMAIL to signUpData.email,
                        KEY_USERNAME to signUpData.username
                    )

                    db.collection(USERS_COLLECTION)
                        .document(auth.currentUser!!.uid)
                        .set(firestoreSignUpData)
                        .addOnCompleteListener { dbTask ->
                            signUpCompletion(dbTask.isSuccessful)
                        }
                } else {
                    if (authTask.exception is FirebaseAuthUserCollisionException) {
                        logger.log("signUp:failure")
                        throw AccountAlreadyExistsDataException()
                    }
                    signUpCompletion(false)
                }
            }
    }

    private fun signUpCompletion(isSuccessful: Boolean) {
        if (isSuccessful) {
            logger.log("signUp:success")
        } else {
            logger.log("signUp:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun getAccount(): AccountDataEntity {
        val user = auth.currentUser ?: throw AuthenticationException()
        var accountDataEntity: AccountDataEntity? = null

        db.collection(USERS_COLLECTION)
            .document(user.uid)
            .get()
            .addOnSuccessListener {
                logger.log("getAccount:success")
                accountDataEntity = AccountDataEntity(
                    id = user.uid,
                    email = it[KEY_EMAIL].toString(),
                    username = it[KEY_USERNAME].toString()
                )
            }
            .addOnFailureListener {
                logger.log("getAccount:failure")
                throw AuthenticationException()
            }
        return accountDataEntity!!
    }

    override suspend fun updateAccount(username: String, email: String): AccountDataEntity {
        val user = auth.currentUser ?: throw AuthenticationException()
        var accountDataEntity: AccountDataEntity? = null
        val firestoreAccountUpdateData = hashMapOf(
            KEY_EMAIL to email,
            KEY_USERNAME to username
        )

        db.collection(USERS_COLLECTION)
            .document(auth.currentUser!!.uid)
            .set(firestoreAccountUpdateData, SetOptions.merge())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logger.log("updateAccount:success")
                    accountDataEntity = AccountDataEntity(
                        id = user.uid,
                        email = email,
                        username = username
                    )
                } else {
                    logger.log("updateAccount:failure")
                    throw AuthenticationException()
                }
            }

        return accountDataEntity!!
    }

    override suspend fun logout() {
        auth.signOut()
    }

    companion object {
        const val USERS_COLLECTION = "users"

        const val KEY_EMAIL = "email"
        const val KEY_USERNAME = "username"
    }

}