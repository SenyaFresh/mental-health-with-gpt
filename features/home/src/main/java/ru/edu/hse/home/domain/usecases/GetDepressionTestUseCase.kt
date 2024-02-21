package ru.edu.hse.home.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.DepressionTest
import ru.edu.hse.home.domain.repositories.DepressionTestRepository
import javax.inject.Inject


class GetDepressionTestUseCase @Inject constructor(
    private val depressionTestRepository: DepressionTestRepository
) {

    fun getDepressionTest(): Flow<ResultContainer<DepressionTest>> =
        depressionTestRepository.getDepressionTest()

}
