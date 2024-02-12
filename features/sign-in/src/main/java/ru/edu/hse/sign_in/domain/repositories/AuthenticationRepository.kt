package ru.edu.hse.sign_in.domain.repositories

interface AuthenticationRepository {

    /**
     * Check if user already signed in.
     */
    suspend fun isSignedIn() : Boolean


    /**
     * Sign in by [email] and [password].
     */
    suspend fun signIn(email: String, password: String)
}