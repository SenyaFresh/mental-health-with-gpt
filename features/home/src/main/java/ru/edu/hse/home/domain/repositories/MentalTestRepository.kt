package ru.edu.hse.home.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.edu.hse.common.ResultContainer
import ru.edu.hse.home.domain.entities.MentalTestEntity

interface MentalTestRepository {

    /**
     * Get mental test.
     */
    fun getMentalTest() : Flow<ResultContainer<MentalTestEntity>>

}