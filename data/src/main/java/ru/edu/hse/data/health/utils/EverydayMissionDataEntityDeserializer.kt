package ru.edu.hse.data.health.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.edu.hse.data.health.entities.EverydayMissionDataEntity
import java.lang.reflect.Type
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EverydayMissionDataEntityDeserializer : JsonDeserializer<EverydayMissionDataEntity> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): EverydayMissionDataEntity {
        val jsonObject = json.asJsonObject

        val text = jsonObject.get("text").asString
        val completed = jsonObject.has("completed") && jsonObject.get("completed").asBoolean
        val date = if (jsonObject.has("date")) {
            jsonObject.get("date").asString
        } else {
            ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).format(DateTimeFormatter.ISO_INSTANT)
        }

        return EverydayMissionDataEntity(text, completed, date)
    }
}