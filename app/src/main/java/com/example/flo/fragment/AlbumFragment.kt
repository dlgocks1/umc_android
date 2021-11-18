package com.example.flo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumViewparAdapter
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.dataclass.Album
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    val information = arrayListOf("  수록곡  ","  상세정보  ","  영상  ")
    private var gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //번들 받아오기
        val albumData = arguments?.getString("album")
        val album = gson.fromJson(albumData, Album::class.java)
        //Home에서 넘어온 데이터들 반영
        setInit(album)

        binding.albumArrowbackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitNowAllowingStateLoss()
        }

        val albumAdapter = AlbumViewparAdapter(this,album.id)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position -> tab.text=information[position]
        }.attach()

        return binding.root
    }

    private fun setInit(album: Album) {
        binding.albumMainimgIv.setImageResource(album.coverImg!!)
        binding.albumTitleSongTv.text = album.title.toString()
        binding.albumSingerSongTv.text = album.singer.toString()
    }


}