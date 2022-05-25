package com.finanskatilim.itunessearchapp.model

class Track(val id: Int, val trackName: String) {
    var artworkUrl: String = ""
    var kind : String = ""
    var primaryGenre: String = ""
    var previewUrl : String = ""
    var price: Double = 0.0
}