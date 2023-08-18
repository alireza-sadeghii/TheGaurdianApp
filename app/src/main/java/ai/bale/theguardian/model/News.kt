package ai.bale.theguardian.model

import ai.bale.theguardian.db.NewsEntity
import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id") val id: String,
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("webPublicationDate") val pDate: String,
    @SerializedName("webUrl") val url: String,
    @SerializedName("fields") val fields: Field,
    @SerializedName("tags") val tags: List<Tag>
)

fun News.toEntity(): NewsEntity {
    return NewsEntity(0, sectionName, pDate, url, fields, tags)
}