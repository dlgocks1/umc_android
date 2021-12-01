package com.example.flo.Service

import com.example.flo.dataclass.Album
import com.google.gson.annotations.SerializedName

data class AlbumResponse_revise(
    @SerializedName("isSuccess")val isSuccess: Boolean,
    @SerializedName("code")val code:Int,
    @SerializedName("message")val message: String,
    @SerializedName("result") val result: Result,
)

data class Result(
    val albums: List<Album>
)

//data class Album(
//    val albumIdx: Int,
//    val coverImgUrl: String,
//    val singer: String,
//    val title: String
//)