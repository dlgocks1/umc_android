package com.example.flo.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockerBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Album_Locker

class AlbumLockerRVAdapter(private val albumList : ArrayList<Album_Locker>) : RecyclerView.Adapter<AlbumLockerRVAdapter.Viewholder>() {

    interface MyItemClickListener{
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun onremoveItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): AlbumLockerRVAdapter.Viewholder {
        val binding: ItemLockerBinding = ItemLockerBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)

        return Viewholder(binding)
    }

    override fun getItemCount(): Int = albumList.size

    //뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 -> 엄청나게 많이 호출
    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bind(albumList[position])
        holder.binding.lockerSavedsongInfoIv.setOnClickListener{
            mItemClickListener.onRemoveAlbum(position)
        }
    }

    inner class Viewholder(val binding : ItemLockerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album_Locker){
            binding.lockerSavedsongTitleTv .text = album.title
            binding.lockerSavedsongSingerTv.text = album.singer
            binding.lockerSavedsongIv.setImageResource(album.coverImg!!)
        }
    }
}
