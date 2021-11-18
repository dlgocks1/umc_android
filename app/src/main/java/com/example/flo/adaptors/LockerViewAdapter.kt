package com.example.flo.adaptors

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.locker.LockersongfileFragment
import com.example.flo.locker.SavedsongFragment

class LockerViewAdapter(fragment:Fragment,var isselect : Boolean) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return when(position){//when은 swich문과 동일
            0 -> SavedsongFragment(isselect)
            1 -> LockersongfileFragment()
            2 -> LockersongfileFragment()
            3 -> LockersongfileFragment()
            4 -> LockersongfileFragment()
            5 -> LockersongfileFragment()
            6 -> LockersongfileFragment()
            else -> LockersongfileFragment()
        }    }

}