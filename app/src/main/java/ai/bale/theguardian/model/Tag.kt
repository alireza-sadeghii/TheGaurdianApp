package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String
) {
    fun getAuthor(): String {
        return "$firstName $lastName"
    }
}