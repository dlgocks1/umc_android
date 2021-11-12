package com.example.flo.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.flo.R
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumInSongAdapter
import com.example.flo.adaptors.AlbumLockerRVAdapter
import com.example.flo.databinding.FragmentSongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Album_Locker
import com.google.gson.Gson


class SongFragment(albumconnect:Album) : Fragment() {

    lateinit var binding: FragmentSongBinding
    var album :Album = albumconnect
    private var albumDatas = ArrayList<Album_Locker>();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        albumDatas.apply {
            for(i in 0..album.songs!!.size-1){
                add(Album_Locker(album.songs!![i].title.toString(),album.songs!![i].singer.toString(),i))
            }
        }
        var albuminsongAdapter = AlbumInSongAdapter(albumDatas)
        binding.songWidgetRcv.adapter= albuminsongAdapter

        albuminsongAdapter.setMyItemClickListener(object : AlbumInSongAdapter.MyItemClickListener{
            override fun onChangeMusic(position: Int) {
                album.position = position
                album.songs!![position].isPlaying = true
                (activity as MainActivity).changeMusic(album)
            }

        })

        binding.albumMyfaverbtIv.setOnClickListener {
            setMixingStatus(true)
        }

        binding.albumMyfaverbtIv1.setOnClickListener {
            setMixingStatus(false)
        }

//        binding.albumSongtotalLayout1.setOnClickListener {
//            Toast.makeText(activity, binding.albumMoretitleTv1.text, Toast.LENGTH_SHORT).show()
//        }
//
//        binding.albumSongtotalLayout2.setOnClickListener {
//            Toast.makeText(activity, binding.albumMyfaverSongtitleTv2.text, Toast.LENGTH_SHORT)
//                .show()
//        }
//
//        binding.albumSongtotalLayout3.setOnClickListener {
//            Toast.makeText(activity, binding.albumMyfaverSongtitleTv3.text, Toast.LENGTH_SHORT)
//                .show()
//        }
//        binding.albumSongtotalLayout4.setOnClickListener {
//            Toast.makeText(activity, binding.albumMyfaverSongtitleTv4.text, Toast.LENGTH_SHORT)
//                .show()
//        }
//        binding.albumSongtotalLayout5.setOnClickListener {
//            Toast.makeText(activity, binding.albumMyfaverSongtitleTv5.text, Toast.LENGTH_SHORT)
//                .show()
//        }
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