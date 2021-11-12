package com.example.flo.adaptors

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.fragment.BannerFragment

class BannerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){

    private val fragmentlist : ArrayList<Fragment> = ArrayList()
    lateinit var bannerFragment : BannerFragment

//    override fun getItemCount(): Int = fragmentlist.size
    override fun getItemCount(): Int = fragmentlist.size


    override fun createFragment(position: Int): Fragment {
        Log.d("test",position.toString())
        return fragmentlist[position]
    }


//    fun addFragment(fragment: Fragment){
//        fragmentlist.add(fragment)
//        notifyItemInserted(fragmentlist.size-1) //viewpager2 에게 새로운인자가 있음을 알려줌
//    }

    fun addFragment(imgRes : Int){
        bannerFragment = BannerFragment(imgRes)
        fragmentlist.add(bannerFragment)

        notifyItemInserted(fragmentlist.size-1)
    }

}