package com.example.flo.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.R
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumInSongAdapter
import com.example.flo.databinding.FragmentSongBinding
import com.example.flo.dataclass.*
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

        val songDB = SongDatabase.getInstance(requireContext())!!
        val song : ArrayList<Song>
        song = songDB.SongDao().getSongInAlbum(albumid) as ArrayList<Song>
        albumDatas.apply {
            for(i in 0..song.size-1){
                    add(Album_Song(song[i].title.toString(),song[i].singer.toString(),i,song[i].istitle))
            }
        }

        var albuminsongAdapter = AlbumInSongAdapter(albumDatas)
        binding.songWidgetRcv.adapter= albuminsongAdapter

        albuminsongAdapter.setMyItemClickListener(object : AlbumInSongAdapter.MyItemClickListener{
            override fun onChangeMusic(position: Int) {
                var album = songDB.albumDao().getAlbum(albumid)
                (activity as MainActivity).initChangesong()
                (activity as MainActivity).changeMusic(album,position)
            }
        })

        binding.albumMyfaverbtIv.setOnClickListener {
            setMixingStatus(true)
        }

        binding.albumMyfaverbtIv1.setOnClickListener {
            setMixingStatus(false)
        }



        return binding.root
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