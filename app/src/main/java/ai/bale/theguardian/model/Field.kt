package ai.bale.theguardian.model

import com.google.gson.annotations.SerializedName

data class Field (@SerializedName("headline") val title: String,
                  @SerializedName ("body") val news_body: String,
                  @SerializedName ("starRating") val score: String,
                  @SerializedName ("thumbnail") val image: String)