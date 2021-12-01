package com.example.flo.Service.SongsResponse

data class Result(
    val isTitleSong: String,
    val singer: String,
    val songIdx: Int,
    val title: String
)