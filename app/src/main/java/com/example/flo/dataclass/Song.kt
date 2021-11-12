package com.example.flo.dataclass


data class Song(
    var music: String = "",
    var title: String = "",
    var singer: String = "",
    var nowPlay: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var now_mil : Int = 0
)
