package com.example.flo.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumViewparAdapter
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Like
import com.example.flo.dataclass.Song
import com.example.flo.dataclass.SongDatabase
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson


class AlbumFragment : Fragment() {

    lateinit var binding: FragmentAlbumBinding
    val information = arrayListOf("  수록곡  ", "  상세정보  ", "  영상  ")
    private var gson: Gson = Gson()
    private var isLiked: Boolean = false

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
        isLiked = isLikedAlbum(album.id)

        setInit(album)
        //setClickListeners(album)

        binding.albumArrowbackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, HomeFragment())
                .commitNowAllowingStateLoss()
        }

        binding.albumIslikeIv.setOnClickListener {
            isLiked = !isLiked
            setClickListeners(album)
        }

        val albumAdapter = AlbumViewparAdapter(this, album.id)
        binding.albumContentVp.adapter = albumAdapter

        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }

    private fun setClickListeners(album: Album) {
        val userId: Int = getJwt()
        if (isLiked) {
            binding.albumIslikeIv.setImageResource(R.drawable.ic_my_like_on)
            likeAlbum(userId, album.id)
            ToastIsLike(isLiked)
        } else {
            binding.albumIslikeIv.setImageResource(R.drawable.ic_my_like_off)
            disLikedAlbum(userId, album.id)
            ToastIsLike(isLiked)
        }
    }

    private fun ToastIsLike(islike : Boolean) {
//        var toasttx: TextView? = v1.findViewById(R.id.islike_toast_tv)
        if(islike){
            var toast = Toast(requireContext())!!

            var v1 = layoutInflater.inflate(R.layout.toast_islike_img,null)
            toast.view = v1
            toast.setGravity(0, Gravity.CENTER, Gravity.CENTER)
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }
//        else{
//            toasttx?.setText("시러요")
//        }

    }


    private fun likeAlbum(userId: Int, albumId: Int) {
        var songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)

        songDB.albumDao().likeAlbum(like)
    }

    private fun isLikedAlbum(albumId: Int): Boolean {
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        var likeId: Int? = songDB.albumDao().isLikeAlbum(userId, albumId)
        return likeId != null
    }

    private fun disLikedAlbum(userId: Int, albumId: Int) {
        val songDB = SongDatabase.getInstance(requireContext())!!
        songDB.albumDao().disLikeAlbum(userId, albumId)
    }

    private fun setInit(album: Album) {
        binding.albumMainimgIv.setImageResource(album.coverImg!!)
        binding.albumTitleSongTv.text = album.title.toString()
        binding.albumSingerSongTv.text = album.singer.toString()

        if (isLiked) {
            binding.albumIslikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.albumIslikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0)
    }
}

