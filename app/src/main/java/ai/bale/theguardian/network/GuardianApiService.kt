package ai.bale.theguardian.network

import ai.bale.theguardian.model.FetchedData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://content.guardianapis.com"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


object GuardianApi {
    val retrofitService: GuardianApiService by lazy {
        retrofit.create(GuardianApiService::class.java)
    }
}

interface GuardianApiService {
    @GET("search?format=json&show-tags=contributor&show-fields=starRating,headline,thumbnail,body&api-key=test")
    fun callData(
        @Query("section") section: String
    ): Call<FetchedData>
}
