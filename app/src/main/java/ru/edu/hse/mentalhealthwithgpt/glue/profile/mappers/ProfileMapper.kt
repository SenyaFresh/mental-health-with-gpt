package ru.edu.hse.mentalhealthwithgpt.glue.profile.mappers

import ru.edu.hse.data.accounts.entities.AccountDataEntity
import ru.edu.hse.profile.domain.entities.Profile


fun AccountDataEntity.toProfile(): Profile {
    return Profile(email, username)
}

