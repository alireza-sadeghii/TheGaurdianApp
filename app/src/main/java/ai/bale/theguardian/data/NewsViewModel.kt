package ai.bale.theguardian.data

import ai.bale.theguardian.db.AppDatabase
import ai.bale.theguardian.db.toNews
import ai.bale.theguardian.model.News
import ai.bale.theguardian.network.GuardianApiService
import ai.bale.theguardian.repository.DataRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsViewModel(private val database: AppDatabase, private val api: GuardianApiService, private val category: String) : ViewModel() {
    private var repository: DataRepository


    init {
        repository = DataRepository(apiService = api, database = database)
    }

    fun pagingFlow(): Flow<PagingData<News>> {
        val pager = repository.providePager(category)
        return pager
            .flow
            .map { data ->
                data.map { it.toNews() }
            }.cachedIn(viewModelScope)
    }
}