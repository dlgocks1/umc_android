package com.example.flo.adaptors

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.dataclass.Album
import com.example.flo.fragment.DetailFragment
import com.example.flo.fragment.SongFragment
import com.example.flo.fragment.VideoFragment

class AlbumViewparAdapter(fragment : Fragment,var albumconnect : Album) : FragmentStateAdapter(fragment){

    var album : Album = albumconnect
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){//when은 swich문과 동일
            0 -> SongFragment(album)
            1 ->  DetailFragment()
            else -> VideoFragment()
        }
    }

}