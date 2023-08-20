package ai.bale.theguardian.data

import ai.bale.theguardian.repository.SettingsRepository
import androidx.lifecycle.ViewModel
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) : ViewModel() {

    fun getNumberOfItems(): Int {
        return settingsRepository.getNumberOfItems()
    }

    fun getItemOrderBy(): String {
        return settingsRepository.getItemOrderBy()
    }

    fun getFromDate(): String {
        return settingsRepository.getFromDate()
    }

    fun getOrderDate(): String {
        return settingsRepository.getOrderDate()
    }

    fun getColorTheme(): String {
        return settingsRepository.getColorTheme()
    }

    fun getTextSize(): Int {
        return settingsRepository.getTextSize()
    }
}