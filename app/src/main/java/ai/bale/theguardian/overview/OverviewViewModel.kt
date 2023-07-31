package ai.bale.theguardian.overview

import ai.bale.theguardian.network.GuardianApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.Exception


class OverviewViewModel : ViewModel(){
    private val _status = MutableLiveData<String>()
    val status : LiveData<String> = _status

    init {
        getApiData()
    }


    private fun getApiData(){
        viewModelScope.launch {
            try {
                val result = GuardianApi.retrofitService.callData(query = "", tag = "") //TODO:
                _status.value = "Success: ${result.size} fetched"
            }catch (e: Exception){
                _status.value = "Failure: ${e.message}"
            }
        }

    }
}