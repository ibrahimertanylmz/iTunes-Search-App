package com.finanskatilim.itunessearchapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.finanskatilim.itunessearchapp.service.TunesApiService
import com.finanskatilim.itunessearchapp.ui.viewmodel.TunesViewModel

class TunesViewModelFactory (private val TunesApiService: TunesApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((TunesViewModel::class.java))) {
            return TunesViewModel(TunesApiService) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}