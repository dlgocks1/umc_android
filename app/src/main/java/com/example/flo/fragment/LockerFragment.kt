package com.example.flo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.adaptors.LockerViewAdapter
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment(var isselect : Boolean) : Fragment() {

    lateinit var binding: FragmentLockerBinding
    val information = arrayListOf("♡ 좋아요", "저장한 곡","음악파일","많이 들은","팔로잉","최근 감상","음악 파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerViewAdapter(this,isselect)

        binding.lockerVp.adapter = lockerAdapter

        TabLayoutMediator(binding.lockerTablayoutTb, binding.lockerVp){
                tab, position -> tab.text=information[position]
        }.attach()


        return binding.root
    }

}