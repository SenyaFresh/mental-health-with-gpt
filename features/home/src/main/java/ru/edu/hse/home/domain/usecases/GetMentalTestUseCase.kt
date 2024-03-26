package ru.edu.hse.home.domain.usecases

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.MentalTestEntity
import ru.edu.hse.home.domain.repositories.MentalTestRepository
import javax.inject.Inject


class GetMentalTestUseCase @Inject constructor(
    private val mentalTestRepository: MentalTestRepository
) {

    /**
     * Get mental test for user.
     */
    fun getMentalTest(): Flow<ResultContainer<MentalTestEntity>> =
        mentalTestRepository.getMentalTest()

}
