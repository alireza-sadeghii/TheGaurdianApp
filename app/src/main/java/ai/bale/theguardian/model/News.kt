package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("sectionName") val sectionName: String,
    @SerializedName("webPublicationDate") val pDate: String,
    @SerializedName("webUrl") val url: String,
    @SerializedName("fields") val fields: Field,
    @SerializedName("tags") val tags: List<Tag>
)