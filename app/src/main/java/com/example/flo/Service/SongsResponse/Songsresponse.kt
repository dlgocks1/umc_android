package com.example.flo.Service.SongsResponse

data class Songsresponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
)