package com.example.flo.Service

import android.annotation.SuppressLint
import android.util.Log
import com.example.flo.Service.LookFragment_Response.LookSongsResponse
import com.example.flo.Service.SongsResponse.Songsresponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumService {
    private lateinit var albumView: GetAlbumView
    private lateinit var songsView: SongsView
    private lateinit var looksongview : LookSongsView

    fun setalbumView(albumView: GetAlbumView){
        this.albumView =  albumView
    }

    fun setsongView(songsView: SongsView){
        this.songsView = songsView
    }

    fun setlooksongView(looksongview: LookSongsView){
        this.looksongview = looksongview
    }

    fun getLookSongs(){
        val authService = getReposit().create(AlbumRetrofitInterface::class.java)
        looksongview.ongetSongsLoading()

        authService.getsongs().enqueue(object : Callback<LookSongsResponse>{

            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<LookSongsResponse>, response: Response<LookSongsResponse>) {
                Log.d("GETLOOKSONGS/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("GETLOOKSONGS/API-RESPONCE_FLO", resp.result.toString())
                when(resp.code){
                    1000 -> looksongview.songetSongsSuccess(resp.result)
                    else -> {
                        looksongview.ongetSongsFailure(resp.code, resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<LookSongsResponse>, t: Throwable) {
                Log.d("GETLOOKSONGS/API-ERROR",t.message.toString())
                albumView.onGetAlbumFailure(400,"네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun getSongsbyAlbumindex(index : Int){
        val authService = getReposit().create(AlbumRetrofitInterface::class.java)
        songsView.onGetSongsLoading()

        authService.getsongsbyIdx(index).enqueue(object : Callback<Songsresponse>{

            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<Songsresponse>, response: Response<Songsresponse>) {
                Log.d("GETSONGS/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("GETSONGS/API-RESPONCE_FLO", resp.toString())
                when(resp.code){
                    1000 -> songsView.onGetSongsSuccess(resp.result)
                    else -> {
                        songsView.onGetSongsFailure(resp.code, resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<Songsresponse>, t: Throwable) {
                Log.d("GETSONGS/API-ERROR",t.message.toString())
                albumView.onGetAlbumFailure(400,"네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun getAlbum(){
        val authService = getReposit().create(AlbumRetrofitInterface::class.java)

        albumView.onGetAlbumLoading()

        authService.getalbums().enqueue(object : Callback<AlbumResponse_revise> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<AlbumResponse_revise>, response: Response<AlbumResponse_revise>) {
                Log.d("GETALBUMACT/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("GETALBUMACT/API-RESPONCE_FLO", resp.toString())
                when(resp.code){
                    1000 -> albumView.onGetAlbumSuccess(resp.result)
                    else -> {
                        albumView.onGetAlbumFailure(resp.code, resp.message)
                    }

                }
            }

            override fun onFailure(call: Call<AlbumResponse_revise>, t: Throwable) {
                Log.d("GETALBUMACT/API-ERROR",t.message.toString())
                albumView.onGetAlbumFailure(400,"네트워크 오류가 발생했습니다.")
            }


        })
    }
}