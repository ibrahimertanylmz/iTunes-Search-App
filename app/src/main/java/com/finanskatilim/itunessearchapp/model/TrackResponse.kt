package com.finanskatilim.itunessearchapp.model


import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val trackResults: List<TrackResult>
)