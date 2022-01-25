package com.example.flo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.R
import com.example.flo.Service.AlbumService
import com.example.flo.Service.GetAlbumView
import com.example.flo.Service.Result
import com.example.flo.Service.SongsView
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumInSongAdapter
import com.example.flo.adaptors.AlbumRCRVAdapter
import com.example.flo.databinding.FragmentSongBinding
import com.example.flo.dataclass.*
import com.google.gson.Gson
import java.text.FieldPosition


class SongFragment(album_id: Int) : Fragment() {

    lateinit var binding: FragmentSongBinding
    private var albumDatas = ArrayList<Album_Song>();
    private val albumid : Int = album_id

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        Log.d("test",albumid.toString())

        //DB에서 노래를 가져오는 소스 인덱스를 겹치지않게 옮겨놈
        val songDB = SongDatabase.getInstance(requireContext())!!
        val song : ArrayList<Song>
        song = songDB.SongDao().getSongInAlbum(albumid) as ArrayList<Song>
        albumDatas.apply {
            for(i in 0..song.size-1){
                    add(Album_Song(song[i].title,song[i].singer,i,song[i].istitle))
            }
        }

        //서버에서 노래를 가져오는 소스
        var songServicemanager = getSongManaer()
        val albumService = AlbumService()
        albumService.setsongView(songServicemanager)
        //albumService.getSongsbyAlbumindex(albumid)

        var albuminsongAdapter = AlbumInSongAdapter(albumDatas)
        binding.songWidgetRcv.adapter= albuminsongAdapter

        albuminsongAdapter.setMyItemClickListener(object : AlbumInSongAdapter.MyItemClickListener{
            override fun onChangeMusic(position: Int) {
                var album = songDB.albumDao().getAlbum(albumid)
                (activity as MainActivity).initChangesong()
                (activity as MainActivity).changeMusic(album,position)
            }
        })

        setClciklistener()


        return binding.root
    }

    inner class getSongManaer() : SongsView {
        override fun onGetSongsLoading() {
        }

        override fun onGetSongsSuccess(album: List<com.example.flo.Service.SongsResponse.Result>) {
            albumDatas.apply {
                for(i in 0..album.size-1){
                    add(Album_Song(album[i].title,album[i].singer,i,false,album[i].isTitleSong))
                }
            }
            var albuminsongAdapter = AlbumInSongAdapter(albumDatas)
            binding.songWidgetRcv.adapter= albuminsongAdapter
        }

        override fun onGetSongsFailure(code: Int, message: String) {

        }
    }

    private fun setClciklistener() {
        binding.albumMyfaverbtIv.setOnClickListener {
            setMixingStatus(true)
        }

        binding.albumMyfaverbtIv1.setOnClickListener {
            setMixingStatus(false)
        }
    }

    fun setMixingStatus(isMixing: Boolean) {
        if (isMixing) {
            binding.albumMyfaverbtIv.visibility = View.GONE
            binding.albumMyfaverbtIv1.visibility = View.VISIBLE
        } else {
            binding.albumMyfaverbtIv.visibility = View.VISIBLE
            binding.albumMyfaverbtIv1.visibility = View.GONE
        }
    }

}