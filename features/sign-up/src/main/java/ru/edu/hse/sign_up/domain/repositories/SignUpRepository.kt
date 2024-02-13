package ru.edu.hse.sign_up.domain.repositories

interface SignUpRepository {

    /**
     * Register user in repository.
     */
    suspend fun signUp(email: String, username: String, password: String)
}