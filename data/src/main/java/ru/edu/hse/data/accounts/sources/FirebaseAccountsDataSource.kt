package ru.edu.hse.data.accounts.sources

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.data.accounts.entities.SignUpDataEntity
import ru.edu.hse.data.accounts.exceptions.AccountAlreadyExistsDataException
import javax.inject.Inject

class FirebaseAccountsDataSource @Inject constructor(): AccountsDataSource {

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
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            logger.log("signIn:success")
        } catch (e: Exception) {
            logger.logError(e, "signIn:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun signUp(signUpData: SignUpDataEntity) {
        try {
            auth.createUserWithEmailAndPassword(signUpData.email, signUpData.password).await()
            val firestoreSignUpData = hashMapOf(
                KEY_EMAIL to signUpData.email,
                KEY_USERNAME to signUpData.username,
                KEY_FETCHED_DATA to false
            )

            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .set(firestoreSignUpData)
                .await()
            logger.log("signUp:success")
        } catch (e: FirebaseAuthUserCollisionException) {
            logger.logError(e, "signUp:failure")
            throw AccountAlreadyExistsDataException()
        } catch (e: Exception) {
            logger.logError(e, "signUp:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun getAccount(): AccountDataEntity {
        val user = auth.currentUser ?: throw AuthenticationException()

        return try {
            val documentSnapshot = db.collection(USERS_COLLECTION).document(user.uid).get().await()
            val accountDataEntity = documentSnapshot.toObject(AccountDataEntity::class.java)
            accountDataEntity ?: throw AuthenticationException()
        } catch (e: Exception) {
            logger.logError(e, "getAccount:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun updateAccount(
        username: String?,
        email: String?,
        depressionPoints: Int?
    ): AccountDataEntity {
        var accountDataEntity: AccountDataEntity = getAccount()
        val firestoreAccountUpdateData = HashMap<String, Any>()

        username?.let { firestoreAccountUpdateData[KEY_USERNAME] = it }
        email?.let { firestoreAccountUpdateData[KEY_EMAIL] = it }
        depressionPoints?.let { firestoreAccountUpdateData[KEY_DEPRESSION_POINTS] = it }

        return try {
            db.collection(USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .set(firestoreAccountUpdateData, SetOptions.merge())
                .await()
            logger.log("updateAccount:success")
            accountDataEntity = accountDataEntity.copy(
                email = email ?: accountDataEntity.email,
                username = username ?: accountDataEntity.username,
                depressionPoints = depressionPoints ?: accountDataEntity.depressionPoints,
            )
            accountDataEntity
        } catch (e: Exception) {
            logger.logError(e, "updateAccount:failure")
            throw AuthenticationException()
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    companion object {
        const val USERS_COLLECTION = "users"

        const val KEY_EMAIL = "email"
        const val KEY_USERNAME = "username"
        const val KEY_FETCHED_DATA = "fetchedData"
        const val KEY_DEPRESSION_POINTS = "depressionPoints"
    }

}