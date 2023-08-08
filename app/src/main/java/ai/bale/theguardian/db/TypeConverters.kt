package ai.bale.theguardian.db

import ai.bale.theguardian.model.Field
import ai.bale.theguardian.model.Tag
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {
    @TypeConverter
    fun fromFields(fields: Field): String {
        return Gson().toJson(fields)
    }

    @TypeConverter
    fun toFields(json: String): Field {
        val type = object : TypeToken<Field>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromTags(tags: List<Tag>): String {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toTags(json: String): List<Tag> {
        val type = object : TypeToken<List<Tag>>() {}.type
        return Gson().fromJson(json, type)
    }
}