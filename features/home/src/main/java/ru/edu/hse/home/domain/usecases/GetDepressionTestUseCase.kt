package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.entities.DepressionTest
import ru.edu.hse.home.domain.repositories.DepressionTestRepository
import javax.inject.Inject


class GetDepressionTestUseCase @Inject constructor(
    private val depressionTestRepository: DepressionTestRepository
) {

    suspend fun getDepressionTest(): DepressionTest =
        depressionTestRepository.getDepressionTest()

}
