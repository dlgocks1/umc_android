package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.FragmentHomeimgBinding

class HomeimgFragment(val typemain : Int,val imgRes : Int, val title : String,
                      val imgResalbum1: Int, val imgResalbum2: Int, val albumtitle1 : String, val albumtitle2: String,
                      val albumsinger1 : String, val albumsinger2: String, val infotext : String, val imgResalbum3 : Int = 1) : Fragment() {
    lateinit var binding : FragmentHomeimgBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeimgBinding.inflate(inflater,container,false)

        binding.homeimgIv.setImageResource(imgRes)
        binding.homeBackgroundTitleTv.setText(title)

        when(typemain){
            1->{
                binding.homeBackgroundMusicIv1.setImageResource(imgResalbum1)
                binding.homeBackgroundMusicIv2.setImageResource(imgResalbum2)
                binding.homeBackgroundMusicNameTv1.setText(albumsinger1)
                binding.homeBackgroundMusicTv1.setText(albumtitle1)
                binding.homeBackgroundMusicNameTv2.setText(albumsinger2)
                binding.homeBackgroundMusicTv2.setText(albumtitle2)
                binding.homeInfotext.setText(infotext)
            }
            else->{
                binding.homeBackgroundMusicroundIv1.setImageResource(imgResalbum1)
                binding.homeBackgroundMusicroundIv2.setImageResource(imgResalbum2)
                binding.homeBackgroundMusicroundIv3.setImageResource(imgResalbum3)
                binding.homeInfotext2.setText(infotext)
            }
        }


        return binding.root
    }

}

