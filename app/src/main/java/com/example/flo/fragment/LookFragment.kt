package com.example.flo.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.Service.AlbumService
import com.example.flo.Service.LookFragment_Response.Result
import com.example.flo.Service.LookSongsView
import com.example.flo.adaptors.LookFlochartRvAdapter
import com.example.flo.databinding.FragmentLookBinding


class LookFragment : Fragment() {

    lateinit var binding: FragmentLookBinding
    lateinit var flochartAdapter: LookFlochartRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentLookBinding.inflate(inflater, container, false)



        val lookSongViewManager = LookSongViewManager()
        val songService = AlbumService()
        songService.setlooksongView(lookSongViewManager)
        songService.getLookSongs()

        return binding.root
    }

    inner class LookSongViewManager : LookSongsView {
        override fun ongetSongsLoading() {
        }

        override fun songetSongsSuccess(result: Result) {
            flochartAdapter = LookFlochartRvAdapter(result.songs)
            binding.lookFlochartRv.adapter = flochartAdapter
        }

        override fun ongetSongsFailure(code: Int, message: String) {
            when(code){
                400 -> Log.d("LoooFloChartRvAdatper: ","LoooFloChartRvAdatper Error")
            }
        }

    }

}

