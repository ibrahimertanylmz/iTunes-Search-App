package com.finanskatilim.itunessearchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finanskatilim.itunessearchapp.model.LoadState
import com.finanskatilim.itunessearchapp.model.SearchResponse
import com.finanskatilim.itunessearchapp.service.iTunesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val iTunesApiService: iTunesApiService) : ViewModel() {
    var searchLiveData = MutableLiveData<SearchResponse>()
    val searchLoadingStateLiveData = MutableLiveData<LoadState>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        withContext(Dispatchers.Main) {
                            searchLoadingStateLiveData.value = LoadState.Loading
                        }
                        val searchResults = iTunesApiService.getResults(query)
                        searchLiveData.postValue(searchResults)
                        searchLoadingStateLiveData.postValue(LoadState.Loaded)
                    } catch (e: Exception) {
                        searchLoadingStateLiveData.postValue(LoadState.Error)
                        Log.d("exception", e.message.toString())
                    }
                }
            }
        }
    }

}