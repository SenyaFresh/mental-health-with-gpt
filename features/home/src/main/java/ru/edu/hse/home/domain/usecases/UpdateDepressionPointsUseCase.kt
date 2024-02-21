package ru.edu.hse.home.domain.usecases

import ru.edu.hse.home.domain.repositories.DepressionTestRepository
import javax.inject.Inject

class UpdateDepressionPointsUseCase @Inject constructor(
    private val depressionTestRepository: DepressionTestRepository
) {

    suspend fun updateDepressionPoints(points: Int) =
        depressionTestRepository.updateUserDepressionPoints(points)

}