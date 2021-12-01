package com.example.flo.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.Service.LookFragment_Response.Song
import com.example.flo.databinding.ItemLookFlochartBinding

class LookFlochartRvAdapter(val songs: List<Song>) :  RecyclerView.Adapter<LookFlochartRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LookFlochartRvAdapter.ViewHolder {
        val binding: ItemLookFlochartBinding = ItemLookFlochartBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LookFlochartRvAdapter.ViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = 7

    inner class ViewHolder(val binding : ItemLookFlochartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song){
            binding.lookFlochartAlbumSingerTv.text = song.singer
            binding.lookFlochartAlbumTitleTv.text = song.title
            binding.lookFlochartAlbumTv.text = (position+1).toString()
            if(song.coverImgUrl != null){
                val media = song.coverImgUrl
                if (media !== null) {
                    Glide.with(binding.lookFlochartAlbumIv).load(media).into(binding.lookFlochartAlbumIv)
                }
            }
        }

    }

}