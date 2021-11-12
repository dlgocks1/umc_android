package com.example.flo.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockersongfileBinding

class LockersongfileFragment : Fragment() {

    lateinit var binding : FragmentLockersongfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLockersongfileBinding.inflate(inflater,container,false)

        return binding.root
    }
}