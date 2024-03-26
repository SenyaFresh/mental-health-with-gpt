package ru.edu.hse.data.accounts.exceptions

import ru.edu.hse.common.UserFriendlyException

class AccountAlreadyExistsDataException: UserFriendlyException("Такой аккаунт уже существует.")