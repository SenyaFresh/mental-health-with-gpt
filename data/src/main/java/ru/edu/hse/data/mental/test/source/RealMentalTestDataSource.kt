package ru.edu.hse.data.mental.test.source

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import ru.edu.hse.common.AuthenticationException
import ru.edu.hse.common.Core
import ru.edu.hse.data.RemoteConfigManager
import ru.edu.hse.data.mental.test.entities.MentalTestDataEntity
import ru.edu.hse.data.mental.test.entities.MentalTestQuestionDataEntity
import ru.edu.hse.data.mental.test.exceptions.WrongAnswerException
import ru.edu.hse.data.mental.test.repository.RealMentalTestDataRepository

class RealMentalTestDataSource: MentalTestDataSource {

    private val auth = Firebase.auth

    private val db = Firebase.firestore

    private val logger = Core.logger

    override fun getMentalTest(): MentalTestDataEntity {
        return RemoteConfigManager.getMentalTest()
    }

    override suspend fun setMentalTestAnswer(
        mentalQuestion: MentalTestQuestionDataEntity,
        answer: String
    ) {
        if (mentalQuestion.questionType == "withOptions" && !mentalQuestion.answers!!.contains(answer))
            throw WrongAnswerException()
        try {
            db.collection(RealMentalTestDataRepository.USERS_COLLECTION)
                .document(auth.currentUser!!.uid)
                .collection(RealMentalTestDataRepository.ANSWERS_COLLECTION)
                .document(mentalQuestion.id)
                .set(hashMapOf<String, Any>(mentalQuestion.question to answer), SetOptions.merge())
                .await()
            logger.log("setMentalTestAnswer:success")
        } catch (e: Exception) {
            logger.logError(e, "setMentalTestAnswer:failure")
            throw AuthenticationException()
        }
    }

}