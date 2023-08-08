package ai.bale.theguardian.remote

import ai.bale.theguardian.db.AppDatabase
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
import java.io.IOException
import java.lang.Exception

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val apiService: GuardianApiService,
    private val database: AppDatabase
) : RemoteMediator<Int, News>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, News>
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
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }else {
                    (lastItem.id.toInt() / state.config.pageSize) + 1
                }
            }
        }

        try {
            val apiResponse = apiService.callData("news", page, state.config.pageSize)
            var allNews : List<News> = emptyList()

            apiResponse.enqueue(object : Callback<FetchedData> {
                override fun onResponse(call: Call<FetchedData>, response: Response<FetchedData>) {
                    if (response.isSuccessful) {
                        val fetchedData = response.body()
                        if (fetchedData != null) {
                            allNews = fetchedData.data.news
                        }
                    }
                }
                override fun onFailure(call: Call<FetchedData>, t: Throwable) {
                    MediatorResult.Error(t)

                }
            })

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.dao().clearAll()
                }
                Log.v("checking", allNews.size.toString())
                for (new in allNews){
                    Log.v("checking", new.fields.title)
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
