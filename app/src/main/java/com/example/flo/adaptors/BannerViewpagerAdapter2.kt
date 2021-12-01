package com.example.flo.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.flo.R
import com.example.flo.databinding.FragmentBannerBinding
import com.example.flo.databinding.ItemAlbumBinding
import com.example.flo.databinding.ItemLockerBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.example.flo.fragment.BannerFragment

class BannerViewpagerAdapter2: RecyclerView.Adapter<BannerViewpagerAdapter2.Viewholder>() {

    companion object {
        const val ITEM_COUNT = 2
    }

    private var bannerItemList = arrayListOf<Int>()

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): BannerViewpagerAdapter2.Viewholder {
        val binding: FragmentBannerBinding = FragmentBannerBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(bannerItemList!![position])
    }


    override fun getItemCount(): Int {
        return ITEM_COUNT
    }


    fun addImgList(imgs : ArrayList<Int>){
        bannerItemList?.clear()
        bannerItemList?.addAll(imgs)
        notifyDataSetChanged()
    }

    inner class Viewholder(val binding : FragmentBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(img : Int){
            binding.bannerImageIv.setImageResource(R.drawable.album_img6)
        }
    }
}

