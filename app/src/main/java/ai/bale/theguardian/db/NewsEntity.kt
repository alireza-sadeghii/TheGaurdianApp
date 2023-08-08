package ai.bale.theguardian.db

import ai.bale.theguardian.model.Field
import ai.bale.theguardian.model.News
import ai.bale.theguardian.model.Tag
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "sectionName")
    val sectionName: String,

    @ColumnInfo(name = "pDate")
    val pDate: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "fields")
    val field: Field,

    @ColumnInfo(name = "tags")
    val tags: List<Tag>
)

fun NewsEntity.toNews(): News {
    return News(id.toString(), sectionName, pDate, url, field, tags)
}
