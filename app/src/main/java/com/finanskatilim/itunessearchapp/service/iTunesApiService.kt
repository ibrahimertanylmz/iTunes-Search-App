package com.finanskatilim.itunessearchapp.service

import com.finanskatilim.itunessearchapp.model.SearchResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class iTunesApiService {
    private val BASE_URL = "https://itunes.apple.com/"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(iTunesApi::class.java)

    suspend fun getResults(query: String): SearchResponse {
        return api.getResults(query)
    }
}