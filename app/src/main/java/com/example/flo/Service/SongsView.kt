package com.example.flo.Service

import com.example.flo.Service.SongsResponse.Result

interface SongsView {
    fun onGetSongsLoading()
    fun onGetSongsSuccess(result: List<Result>)
    fun onGetSongsFailure(code : Int, message : String)
}