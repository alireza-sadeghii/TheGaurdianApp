package ai.bale.theguardian.repository

import android.content.SharedPreferences
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getNumberOfItems(): Int {
        return sharedPreferences.getString("numOfItem", "10")?.toInt() ?: 10
    }

    fun getItemOrderBy(): String {
        return sharedPreferences.getString("orderBy", "oldest") ?: "oldest"
    }

    fun getFromDate(): String {
        return sharedPreferences.getString("fromDate", "") ?: ""
    }

    fun getOrderDate(): String {
        return sharedPreferences.getString("orderDate", "published") ?: "published"
    }

    fun getColorTheme(): String {
        return sharedPreferences.getString("colorTheme", "#FFFFFF") ?: "#FFFFFF"
    }

    fun getTextSize(): Int {
        return sharedPreferences.getString("textSize", "22")?.toInt() ?: 22
    }

}