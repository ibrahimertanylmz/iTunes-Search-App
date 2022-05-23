package com.finanskatilim.itunessearchapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.finanskatilim.itunessearchapp.service.iTunesApiService
import com.finanskatilim.itunessearchapp.ui.viewmodel.SearchViewModel

class SearchViewModelFactory (private val iTunesApiService: iTunesApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((SearchViewModel::class.java))) {
            return SearchViewModel(iTunesApiService) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}