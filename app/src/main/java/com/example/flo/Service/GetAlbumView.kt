package com.example.flo.Service


interface GetAlbumView {
    fun onGetAlbumLoading()
    fun onGetAlbumSuccess(result : Result)//(albums : Albumsresult: albumList?
    fun onGetAlbumFailure(code : Int, message : String)
}