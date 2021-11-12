package com.example.flo.adaptors

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.locker.LockersongfileFragment
import com.example.flo.locker.SavedsongFragment

class LockerViewAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){//when은 swich문과 동일
            0 -> SavedsongFragment()
            else -> LockersongfileFragment()
        }    }

}