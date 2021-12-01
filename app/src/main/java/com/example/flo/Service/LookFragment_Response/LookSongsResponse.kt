package com.example.flo.Service.LookFragment_Response

data class LookSongsResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)

data class Result(
    val songs: List<Song>
)