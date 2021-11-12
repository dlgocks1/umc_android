package com.example.flo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flo.databinding.FragmentVedioBinding


class VideoFragment : Fragment() {

    lateinit var binding : FragmentVedioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVedioBinding.inflate(inflater,container,false)

        return binding.root
    }


}