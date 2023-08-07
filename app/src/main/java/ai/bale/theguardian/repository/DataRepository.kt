package ai.bale.theguardian.repository

import ai.bale.theguardian.model.News
import ai.bale.theguardian.network.GuardianApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DataRepository(private val apiService: GuardianApiService) {

    fun getNews(category: String): Flow<List<News>> = flow {
        val call = apiService.callData(category)

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val news = response.body()?.data?.news ?: emptyList()
                emit(news)
            }
        } catch (_: Throwable) {
        }
    }.flowOn(Dispatchers.IO)
}
