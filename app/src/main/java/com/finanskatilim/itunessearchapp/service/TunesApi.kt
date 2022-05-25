package com.finanskatilim.itunessearchapp.service

import com.finanskatilim.itunessearchapp.model.SearchResponse
import com.finanskatilim.itunessearchapp.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TunesApi {
    @GET("search")
    suspend fun getResults(
        @Query("term") term: String
    ): SearchResponse

    @GET("lookup")
    suspend fun getTrackById(
            @Query("id") id: String
    ): TrackResponse
}