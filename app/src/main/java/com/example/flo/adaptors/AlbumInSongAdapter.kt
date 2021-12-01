package com.example.flo.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.activity.MainActivity
import com.example.flo.databinding.ItemLockerBinding
import com.example.flo.databinding.ItemSongfragmentBinding
import com.example.flo.dataclass.Album_Locker
import com.example.flo.dataclass.Album_Song

class AlbumInSongAdapter(private val albumList : ArrayList<Album_Song>) : RecyclerView.Adapter<AlbumInSongAdapter.Viewholder>(){

    interface MyItemClickListener{
        fun onChangeMusic(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun onChangeMusic(position: Int){
        Log.d("test",position.toString())
    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): Viewholder {
        val binding: ItemSongfragmentBinding = ItemSongfragmentBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)


        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(albumList[position])
//        holder.binding.albumSongplayIv1.setOnClickListener{
//            mItemClickListener.onChangeMusic(position)
//        }
    }

    override fun getItemCount(): Int = albumList.size

    class Viewholder (val binding: ItemSongfragmentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(album: Album_Song){
            binding.albumSongNumTv.text = "0" + (album.position +1).toString()
            binding.albumMoretitleTv.text = album.title
            binding.albumMoresingerTv.text = album.singer
            if(album.istitle || album.isTitleSong == "T"){
                binding.songIstitileTv.visibility = View.VISIBLE
            }
            else{
                binding.songIstitileTv.visibility = View.GONE
            }
        }

    }
}