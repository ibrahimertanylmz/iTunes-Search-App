package com.finanskatilim.itunessearchapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finanskatilim.itunessearchapp.model.AudioBook
import com.finanskatilim.itunessearchapp.model.LoadState
import com.finanskatilim.itunessearchapp.model.SearchResponse
import com.finanskatilim.itunessearchapp.model.Track
import com.finanskatilim.itunessearchapp.service.TunesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TunesViewModel(private val TunesApiService: TunesApiService) : ViewModel() {
    var tracksLiveData = MutableLiveData<ArrayList<Track>>()
    var trackDetailsLiveData = MutableLiveData<Track>()
    var audioBooksLiveData = MutableLiveData<ArrayList<AudioBook>>()
    val searchLoadingStateLiveData = MutableLiveData<LoadState>()
    val trackDetailsLoadingStateLiveData = MutableLiveData<LoadState>()
    private var trackList = ArrayList<Track>()
    private var audioBookList = ArrayList<AudioBook>()

    fun onSearchQuery(query: String) {
        viewModelScope.launch {
            if (query.length > 2) {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        withContext(Dispatchers.Main) {
                            searchLoadingStateLiveData.value = LoadState.Loading
                        }
                        val searchResponse = TunesApiService.getResults(query)
                        postSearchResponses(searchResponse)
                        searchLoadingStateLiveData.postValue(LoadState.Loaded)
                    } catch (e: Exception) {
                        searchLoadingStateLiveData.postValue(LoadState.Error)
                        Log.d("exception", e.message.toString())
                    }
                }
            }
        }
    }

    private fun postSearchResponses(searchResponse: SearchResponse){
        trackList.clear()
        audioBookList.clear()
        searchResponse.results.forEach {
            if (it.wrapperType == "track"){
                val track = Track(it.trackId, it.trackName)
                track.artworkUrl = it.artworkUrl100
                track.primaryGenre = it.primaryGenreName
                track.kind = it.kind
                trackList.add(track)
            }else{
                val audioBook = AudioBook(it.collectionId)
                audioBook.artworkUrl = it.artworkUrl100
                audioBook.artistName = it.artistName
                audioBook.collectionName = it.collectionName
                audioBook.primaryGenre = it.primaryGenreName
                audioBookList.add(audioBook)
            }
        }
        tracksLiveData.postValue(trackList)
        audioBooksLiveData.postValue(audioBookList)
    }

    fun getTrackList(): ArrayList<Track> {
        return trackList
    }

    fun getAudioBookList(): ArrayList<AudioBook> {
        return audioBookList
    }

    fun getTrackListSize(): Int {
        return trackList.size
    }

    fun getTrackByPosition(position: Int): Track{
        return trackList.get(position)
    }

    fun getAudioBookByPosition(position: Int): AudioBook{
        return audioBookList.get(position)
    }

    fun getAudioBookListSize(): Int {
        return audioBookList.size
    }

    fun getTrackDetailsById(trackId: String){
        viewModelScope.launch {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    withContext(Dispatchers.Main) {
                        trackDetailsLoadingStateLiveData.value = LoadState.Loading
                    }
                    val trackDetails = TunesApiService.getTrackById(trackId)
                    trackDetails.trackResults[0].apply {
                        val track = Track(this.trackId,this.trackName)
                        track.artworkUrl = this.artworkUrl100
                        track.primaryGenre = this.primaryGenreName
                        track.kind = this.kind
                        track.previewUrl = this.previewUrl
                        track.price = this.trackPrice
                        trackDetailsLiveData.postValue(track)
                    }
                    trackDetailsLoadingStateLiveData.postValue(LoadState.Loaded)
                } catch (e: Exception) {
                    trackDetailsLoadingStateLiveData.postValue(LoadState.Error)
                    Log.d("error", e.message.toString())
                }
            }
        }
    }

}