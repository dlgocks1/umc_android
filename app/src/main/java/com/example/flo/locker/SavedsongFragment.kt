package com.example.flo.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.R
import com.example.flo.adaptors.AlbumLockerRVAdapter
import com.example.flo.databinding.FragmentSavedsongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Album_Locker

class SavedsongFragment : Fragment() {

    lateinit var binding : FragmentSavedsongBinding
    private var albumDatas = ArrayList<Album_Locker>();

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedsongBinding.inflate(inflater,container,false)

        albumDatas.apply {
            add(Album_Locker("Drunk Texting (feat. Jimmy Brown)","Alisha (알리샤)", R.drawable.album_img11))
            add(Album_Locker("Daydream","주영", R.drawable.album_img10))
            add(Album_Locker("차를 세워","아이디 (Eyedi)", R.drawable.album_img9))
            add(Album_Locker("Little Things","소피야 (Sophiya)", R.drawable.album_img8))
            add(Album_Locker("아는 사람 얘기","독립음악", R.drawable.album_img7))
            add(Album_Locker("Rock N Roll Is A Risk (Dialogue)","Sing Street (Original Motion Picture Soundtrack)", R.drawable.album_img6))
            add(Album_Locker("Lost Star", "Begin Again - Music From And Inspired By The Original Motion Picture", R.drawable.album_img5))
            add(Album_Locker("Lilac", "아이유(IU)", R.drawable.img_album_exp2))
            add(Album_Locker("GLASSY", "조유리", R.drawable.album_img2))
            add(Album_Locker("잊혀진 계절", "The One (더원)", R.drawable.album_img3))
            add(Album_Locker("WOO", "27RING", R.drawable.album_img4))
        }
        var lockersongAdapter = AlbumLockerRVAdapter(albumDatas)
        binding.lockerSavedsongRv.adapter= lockersongAdapter
        lockersongAdapter.setMyItemClickListener(object : AlbumLockerRVAdapter.MyItemClickListener{
            override fun onRemoveAlbum(position: Int) {
                lockersongAdapter.onremoveItem(position)
            }

        })


        return binding.root
    }
}