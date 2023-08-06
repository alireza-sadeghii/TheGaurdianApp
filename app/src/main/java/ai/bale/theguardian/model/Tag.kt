package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("firstName") val fName: String,
    @SerializedName("lastName") val lName: String
) {
    fun getAuthor(): String {
        return "$fName $lName"
    }
}