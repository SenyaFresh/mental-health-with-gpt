package ru.edu.hse.home.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.repositories.DepressionTestRepository
import javax.inject.Inject

class GetDepressionPointsUseCase @Inject constructor(
    private val depressionTestRepository: DepressionTestRepository
) {

    fun getDepressionPoints() : Flow<ResultContainer<Int>> =
        depressionTestRepository.getUserDepressionPoints()

}