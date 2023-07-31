package ai.bale.theguardian.network

import ai.bale.theguardian.R
import ai.bale.theguardian.model.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://content.guardianapis.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


object GuardianApi {
    val retrofitService: GuardianApiService by lazy {
        retrofit.create(GuardianApiService::class.java)
    }
}

interface GuardianApiService {
    @GET("search")
    fun callData(
        @Query("q") query: String, @Query("format") format: String = "json",
        @Query("tag") tag: String, @Query("show-tags") show_tags: String = "contributor",
        @Query("show-fields") fields: String = "starRating,headline,thumbnail",
        @Query("show-blocks") block: String = "body",
        @Query("order-by") order: String = "relevance",
        @Query("api-key") key: String = R.string.api_key.toString()
    ) : List<ApiResponse>
}
