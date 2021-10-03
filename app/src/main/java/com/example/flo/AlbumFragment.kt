package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment(){

    //lateinit var binding : FramentAlbumBinding{
     //
    //}
    
    //OnCreateView() 함수가 Fragment에선 실행됨

    lateinit var binding : FragmentAlbumBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        binding.albumArrowbackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
                .commitNowAllowingStateLoss()
        }
        binding.albumSongtotalLayout1.setOnClickListener {
            Toast.makeText(activity,binding.albumMoretitleTv1.text,Toast.LENGTH_SHORT).show()
        }

        binding.albumSongtotalLayout2.setOnClickListener {
            Toast.makeText(activity,binding.albumMyfaverSongtitleTv2.text,Toast.LENGTH_SHORT).show()
        }

        binding.albumSongtotalLayout3.setOnClickListener {
            Toast.makeText(activity,binding.albumMyfaverSongtitleTv3.text,Toast.LENGTH_SHORT).show()
        }
        binding.albumSongtotalLayout4.setOnClickListener {
            Toast.makeText(activity,binding.albumMyfaverSongtitleTv4.text,Toast.LENGTH_SHORT).show()
        }
        binding.albumSongtotalLayout5.setOnClickListener {
            Toast.makeText(activity,binding.albumMyfaverSongtitleTv5.text,Toast.LENGTH_SHORT).show()
        }

        binding.albumMyfaverbtIv.setOnClickListener{
            setMixingStatus(true)
        }

        binding.albumMyfaverbtIv1.setOnClickListener{
            setMixingStatus(false)
        }
        return binding.root
    }

    fun setMixingStatus(isMixing : Boolean){
        if(isMixing){
            binding.albumMyfaverbtIv.visibility=View.GONE
            binding.albumMyfaverbtIv1.visibility=View.VISIBLE
        }
        else{
            binding.albumMyfaverbtIv.visibility=View.VISIBLE
            binding.albumMyfaverbtIv1.visibility=View.GONE
        }
    }

}