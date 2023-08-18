package ai.bale.theguardian.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ai.bale.theguardian.db.TypeConverters as converter

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
@TypeConverters(converter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun dao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Github.db")
                .build()
    }

}