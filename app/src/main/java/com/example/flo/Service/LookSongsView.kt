package com.example.flo.Service

import com.example.flo.Service.LookFragment_Response.Result

interface LookSongsView {
    fun ongetSongsLoading()
    fun songetSongsSuccess(result: Result)
    fun ongetSongsFailure(code : Int, message : String)
}