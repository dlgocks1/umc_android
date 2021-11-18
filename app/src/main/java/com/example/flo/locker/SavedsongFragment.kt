package com.example.flo.locker

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.adaptors.AlbumLockerRVAdapter
import com.example.flo.databinding.FragmentSavedsongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Album_Locker
import com.example.flo.dataclass.Song
import com.example.flo.dataclass.SongDatabase
import android.content.Intent
import android.graphics.Color
import com.example.flo.R.color.light_blue2
import com.example.flo.activity.MainActivity
import com.example.flo.activity.SheetdialogActivity


class SavedsongFragment(var issel: Boolean) : Fragment() {

    lateinit var binding : FragmentSavedsongBinding
    lateinit var SongDB : SongDatabase
    var isselect : Boolean = issel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedsongBinding.inflate(inflater,container,false)
        SongDB = SongDatabase.getInstance(requireContext())!!

        @SuppressLint("ResourceAsColor")
        if(isselect){   //이렇게 하는게 맞나..?
            binding.lockerSavedsongRaTv.text = "선택 해제"
            binding.lockerSavedsongRaTv.setTextColor(R.color.light_blue2)
            binding.lockerSavedsongSaoffIv.visibility = View.INVISIBLE
            binding.lockerSavedsongSaonIv.visibility = View.VISIBLE
        }
        clickEventListener()

        return binding.root
    }

    private fun clickEventListener() {
        binding.lockerSavedsongSaoffIv.setOnClickListener {
            isselect = true
            selectAll(isselect)
        }
        binding.lockerSavedsongRaTv.setOnClickListener {
            isselect = !isselect
            selectAll(isselect)
        }

        binding.lockerSavedsongSaonIv.setOnClickListener {
            isselect = false
            selectAll(isselect)
        }
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    fun initRecyclerView() {
        var lockersongAdapter = AlbumLockerRVAdapter(isselect)

        lockersongAdapter.setMyItemClickListener(object : AlbumLockerRVAdapter.MyItemClickListener{
            override fun onRemoveSong(songId: Int) {
                SongDB = SongDatabase.getInstance(requireContext())!!
                SongDB.SongDao().updateIsLikeById(false,songId)
                SongDB.SongDao().update(SongDB.SongDao().getSong(songId))
                (activity as MainActivity).updateIsLike(false,songId)
                //Log.d("test",SongDB.SongDao().getSong(songId).toString())
            }
        })
        binding.lockerSavedsongRv.adapter= lockersongAdapter
        lockersongAdapter.addSongList(SongDB.SongDao().getLikedSongs(true) as ArrayList)

    }

    @SuppressLint("ResourceAsColor")
    private fun selectAll(isselect:Boolean) {
        if(isselect){
            binding.lockerSavedsongRaTv.text = "선택 해제"
            binding.lockerSavedsongRaTv.setTextColor(R.color.light_blue2)
            (activity as MainActivity).showSheetdialog(true)
            binding.lockerSavedsongSaoffIv.visibility = View.INVISIBLE
            binding.lockerSavedsongSaonIv.visibility = View.VISIBLE
            initRecyclerView()

        }
        else{
            binding.lockerSavedsongRaTv.text = "전체 선택"
            binding.lockerSavedsongRaTv.setTextColor(Color.BLACK)
            (activity as MainActivity).showSheetdialog(false)
            binding.lockerSavedsongSaoffIv.visibility = View.VISIBLE
            binding.lockerSavedsongSaonIv.visibility = View.INVISIBLE
            initRecyclerView()

        }
    }


}