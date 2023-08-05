package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class FetchedData (@SerializedName("response") val data: ApiResponse)