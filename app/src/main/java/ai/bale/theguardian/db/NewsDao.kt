package ai.bale.theguardian.db

import ai.bale.theguardian.model.News
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(newsEntity: List<NewsEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearAll()

    @Query("SELECT * FROM articles")
    fun getAll() : PagingSource<Int, News>

    @Query("SELECT * FROM articles WHERE sectionName = :category")
    fun getAllBySection(category: String) : PagingSource<Int, News>
}