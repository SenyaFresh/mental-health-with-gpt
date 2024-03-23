package ru.edu.hse.mentalhealthwithgpt.glue.sign_up.repositories

import ru.edu.hse.data.accounts.entities.SignUpDataEntity
import ru.edu.hse.data.accounts.repository.AccountsDataRepository
import ru.edu.hse.sign_up.domain.repositories.SignUpRepository
import javax.inject.Inject

class AdapterSignUpRepository @Inject constructor(
    private val accountsDataRepository: AccountsDataRepository
) : SignUpRepository{

    override suspend fun signUp(email: String, username: String, password: String) {
        accountsDataRepository.singUp(SignUpDataEntity(email, username, password))
    }

}