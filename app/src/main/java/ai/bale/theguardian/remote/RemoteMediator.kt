package ai.bale.theguardian.remote

import ai.bale.theguardian.db.AppDatabase
import ai.bale.theguardian.db.NewsEntity
import ai.bale.theguardian.model.ApiResponse
import ai.bale.theguardian.model.FetchedData
import ai.bale.theguardian.model.Field
import ai.bale.theguardian.model.News
import ai.bale.theguardian.model.Tag
import ai.bale.theguardian.model.toEntity
import ai.bale.theguardian.network.GuardianApiService
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.io.IOException
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val apiService: GuardianApiService,
    private val database: AppDatabase,
    private val category: String
) : RemoteMediator<Int, NewsEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                }else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }

        try {
            val response = apiService.callData(category, page, state.config.pageSize).await()
            val allNews: List<News> = response.data.news


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.dao().clearAll()
                }
                database.dao().insertAll(allNews.map { it.toEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = allNews.isEmpty())

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}
