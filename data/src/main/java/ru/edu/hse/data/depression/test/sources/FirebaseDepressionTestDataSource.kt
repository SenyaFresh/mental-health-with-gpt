package ru.edu.hse.data.depression.test.sources

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import ru.edu.hse.common.Core
import ru.edu.hse.data.depression.test.entities.CategoryEntity
import ru.edu.hse.data.depression.test.entities.DepressionTestEntity
import ru.edu.hse.data.depression.test.exceptions.TestRepositoryException

class FirebaseDepressionTestDataSource : DepressionTestDataSource {

    private val db = Firebase.firestore

    private val logger = Core.logger

    override suspend fun getDepressionTest(): DepressionTestEntity {
        val categories = mutableListOf<CategoryEntity>()

        for (i in (1..21)) {

            db.collection(DEPRESSION_TEST_COLLECTION)
                .document("category_$i")
                .get()
                .addOnSuccessListener {
                    logger.log("getDepressionTest:success")
                    categories += CategoryEntity(
                        firstStatement = it[KEY_FIRST_STATEMENT].toString(),
                        secondStatement = it[KEY_SECOND_STATEMENT].toString(),
                        thirdStatement = it[KEY_THIRD_STATEMENT].toString(),
                        fourthStatement = it[KEY_FOURTH_STATEMENT].toString(),
                    )
                }
                .addOnFailureListener {
                    logger.log("getDepressionTest:failure")
                    throw TestRepositoryException()
                }
        }

        return DepressionTestEntity(categories)
    }

    companion object {
        const val DEPRESSION_TEST_COLLECTION = "mental_test"

        const val KEY_FIRST_STATEMENT = "statement_1"
        const val KEY_SECOND_STATEMENT = "statement_2"
        const val KEY_THIRD_STATEMENT = "statement_3"
        const val KEY_FOURTH_STATEMENT = "statement_4"
    }
}