package com.finanskatilim.itunessearchapp.service

import com.finanskatilim.itunessearchapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesApi {
    @GET("search")
    suspend fun getResults(
        @Query("term") term: String,
    ): SearchResponse
}