package ai.bale.theguardian.repository

import ai.bale.theguardian.db.AppDatabase
import ai.bale.theguardian.model.News
import ai.bale.theguardian.network.GuardianApiService
import ai.bale.theguardian.remote.RemoteMediator
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class DataRepository(private val apiService: GuardianApiService, private val database: AppDatabase) {

    fun getNews(category: String): Flow<PagingData<News>> {
        val pagingSourceFactory = { database.dao().getAllBySection(category) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            remoteMediator = RemoteMediator(apiService, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}