package com.example.flo.adaptors

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding
import com.example.flo.databinding.ItemLockerAlbumBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song

class AlbumIsLikedLockerRVAdapter() : RecyclerView.Adapter<AlbumIsLikedLockerRVAdapter.Viewholder>() {
    private val albumList = ArrayList<Album>()

    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun setminiplayer(album: Album)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): AlbumIsLikedLockerRVAdapter.Viewholder {
        val binding:ItemLockerAlbumBinding = ItemLockerAlbumBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)

        return Viewholder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSongList(albums : ArrayList<Album>){
        this.albumList.clear()
        this.albumList.addAll(albums)
        notifyDataSetChanged()
    }


    fun addItem(album : Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position : Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = albumList.size

    inner class Viewholder(val binding : ItemLockerAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album){
            binding.lockerSavedalbumitemTitleTv.text = album.title
            binding.lockerSavedalbumitemSingerTv.text = album.singer
            binding.lockerSavedalbumitemImgIv.setImageResource(album.coverImg!!)
        }
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(albumList[position])
//        holder.itemView.setOnClickListener{
//            mItemClickListener.onItemClick(albumList[position])    //외부에서 처리할 수 있도록
//        }
//        holder.binding.lockerSavedsongPlayIv.setOnClickListener{
//            mItemClickListener.setminiplayer(albumList[position])
//        }
    }

}