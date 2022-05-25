package com.finanskatilim.itunessearchapp.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val results: List<Result>
)