package ai.bale.theguardian.model

import com.squareup.moshi.Json

data class ApiResponse(@Json (name = "status") val status: String,@Json (name = "total") val resultNum: Int,@Json (name = "results") val news: List<News>)