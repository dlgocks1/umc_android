package com.example.flo.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.Service.getUserIdx
import com.example.flo.adaptors.AlbumIsLikedLockerRVAdapter
import com.example.flo.adaptors.LockerViewAdapter
import com.example.flo.databinding.FragmentSavedalbumBinding
import com.example.flo.databinding.FragmentSavedsongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.SongDatabase

class LockerSavedAlbumFragment : Fragment() {
    lateinit var binding : FragmentSavedalbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedalbumBinding.inflate(inflater,container,false)
        val songDB = SongDatabase.getInstance(requireContext())!!
        var userId = getUserIdx(requireContext())

        val likealbumadapte = AlbumIsLikedLockerRVAdapter()
        binding.lockerSavedalbumRv.adapter = likealbumadapte
        likealbumadapte.addSongList(songDB.albumDao().getLikedAlbums(userId) as ArrayList<Album>)


        return binding.root
    }


}