package com.dicoding.utaya.ui.Bottom.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.utaya.data.DataRepository
import com.dicoding.utaya.data.Result
import com.dicoding.utaya.data.response.produk.RecommendationsItem
import kotlinx.coroutines.launch

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _listProduk = MutableLiveData<List<RecommendationsItem>>()
    val listProduk: LiveData<List<RecommendationsItem>> = _listProduk
    val loading = MutableLiveData<Boolean>()

    fun fetchProduk() {
        viewModelScope.launch {
            try {
                loading.value = true
                dataRepository.getProduk().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            val responseProdukItemList = result.data
                            Log.d("Cek Data result bosss", responseProdukItemList.toString())

                            // Buat list untuk menyimpan semua recommendations
                            val allRecommendations = mutableListOf<RecommendationsItem>()

                            // Iterasi melalui setiap item dan ambil recommendations
                            responseProdukItemList.forEach { item ->
                                item.data?.recommendations?.filterNotNull()?.let { recommendations ->
                                    allRecommendations.addAll(recommendations)
                                }
                            }

                            // Lakukan sesuatu dengan allRecommendations
                            Log.d("Cek Recommendations", allRecommendations.toString())
                            // Contoh: Menyimpan recommendations ke dalam LiveData untuk diobservasi di UI
                            _listProduk.value = allRecommendations
                            loading.value = false
                        }
                        is Result.Error -> {
                            Log.e("HomeViewModel", "Error fetching produk: ${result.error.toString()}")
                            loading.value = false
                        }
                        is Result.Loading -> {
                            loading.value = true
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Exception fetching produk: ${e.message}", e)
                loading.value = false
            }
        }
    }

}
