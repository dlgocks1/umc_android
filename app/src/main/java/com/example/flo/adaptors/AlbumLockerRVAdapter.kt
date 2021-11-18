package com.example.flo.adaptors

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.R
import com.example.flo.R.color
import com.example.flo.databinding.ItemLockerBinding
import com.example.flo.dataclass.Song

class AlbumLockerRVAdapter(isselect: Boolean) : RecyclerView.Adapter<AlbumLockerRVAdapter.Viewholder>() {
    //private val albumList : ArrayList<Album_Locker>
    private var songs = ArrayList<Song>()
    private val isselect = isselect


    interface MyItemClickListener{
        fun onRemoveSong(songId: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun removeSong(position: Int){
        songs.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): AlbumLockerRVAdapter.Viewholder {
        val binding: ItemLockerBinding = ItemLockerBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)

        return Viewholder(binding)
    }

    override fun getItemCount(): Int = songs.size

    //뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 -> 엄청나게 많이 호출
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(songs[position])
        holder.setbackground(isselect)
        holder.binding.lockerSavedsongInfoIv.setOnClickListener{
            mItemClickListener.onRemoveSong(songs[position].id)
            removeSong(position)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addSongList(songs : ArrayList<Song>){
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }


    inner class Viewholder(val binding : ItemLockerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song){
            binding.lockerSavedsongTitleTv .text = song.title
            binding.lockerSavedsongSingerTv.text = song.singer
            binding.lockerSavedsongIv.setImageResource(song.coverImg!!)
        }
        @SuppressLint("ResourceAsColor")
        fun setbackground(isselect: Boolean) {
            if(isselect){
                binding.itemLocker.setBackgroundColor(Color.rgb(235,235,235))
            }else{
                binding.itemLocker.setBackgroundColor(Color.WHITE)
            }
        }
    }
}
