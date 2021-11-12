package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumcoodinateBinding

class AlbumcoordinateFragment : Fragment() {

    lateinit var binding : FragmentAlbumcoodinateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAlbumcoodinateBinding.inflate(inflater,container,false)

        return binding.root
    }
}