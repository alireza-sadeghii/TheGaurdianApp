package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(@SerializedName("status") val status: String,
                       @SerializedName ("total") val resultNum: Int,
                       @SerializedName ("results") val news: List<News>)