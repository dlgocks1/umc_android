package com.example.flo.Service

import com.example.flo.Service.LookFragment_Response.LookSongsResponse
import com.example.flo.Service.SongsResponse.Songsresponse
import com.example.flo.dataclass.AuthResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumRetrofitInterface {

    @GET("/albums")
    fun getalbums() : Call<AlbumResponse_revise>

    @GET("/albums/{index}")
    fun getsongsbyIdx(
        @Path("index") index : Int,
    ) : Call<Songsresponse>

    @GET("/songs")
    fun getsongs() : Call<LookSongsResponse>
}