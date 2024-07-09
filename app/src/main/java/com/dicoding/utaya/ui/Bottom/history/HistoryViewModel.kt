package com.dicoding.utaya.ui.Bottom.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.utaya.data.DataRepository
import com.dicoding.utaya.data.Result
import com.dicoding.utaya.data.response.history.HistorysItem
import kotlinx.coroutines.launch

class HistoryViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _listHistory = MutableLiveData<List<HistorysItem>>()
    val listHistory: LiveData<List<HistorysItem>> = _listHistory
    val loading = MutableLiveData<Boolean>()

    fun fetchHistory() {
        viewModelScope.launch {
            try {
                loading.value = true
                dataRepository.getHistory().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val responseHistory = result.data
                            Log.d("Cek Data History ", responseHistory.toString())

                            val historyItems = responseHistory.historys?.filterNotNull()?.map { historyItem ->
                                HistorysItem(
                                    skinType = historyItem.skinType,
                                    skinTypePercentage = historyItem.skinTypePercentage,
                                    urlImage = historyItem.urlImage
                                )
                            } ?: emptyList()

                            _listHistory.value = historyItems
                            loading.value = false
                        }
                        is Result.Error -> {
                            Log.e("HistoryViewModel", "Error fetching history: ${result.error}")
                            loading.value = false
                        }
                        is Result.Loading -> {
                            loading.value = true
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("HistoryViewModel", "Exception fetching history: ${e.message}", e)
                loading.value = false
            }
        }
    }
}
