package ai.bale.theguardian.repository

import ai.bale.theguardian.db.AppDatabase
import ai.bale.theguardian.db.NewsEntity
import ai.bale.theguardian.network.GuardianApiService
import ai.bale.theguardian.remote.RemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig


@OptIn(ExperimentalPagingApi::class)
class DataRepository(private val apiService: GuardianApiService, private val database: AppDatabase) {
    fun providePager(category: String): Pager<Int, NewsEntity>{
       return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = RemoteMediator(apiService, database, category),
            pagingSourceFactory = { database.dao().getAllBySection(category) }
        )
    }
}