package com.finanskatilim.itunessearchapp.service

import com.finanskatilim.itunessearchapp.model.SearchResponse
import com.finanskatilim.itunessearchapp.model.TrackResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TunesApiService {
    private val BASE_URL = "https://itunes.apple.com/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TunesApi::class.java)

    suspend fun getResults(query: String): SearchResponse {
        return api.getResults(query)
    }

    suspend fun getTrackById(query: String): TrackResponse {
        return api.getTrackById(query)
    }
}