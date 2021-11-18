package com.example.flo.adaptors

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song

class AlbumRVAdapter(private var albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.Viewholder>() {


    //쓸 함수를 인터페이스로정의
    interface MyItemClickListener{
        fun onItemClick(album: Album)
        fun setminiplayer(album: Album)
//        fun onRemoveAlbum(position: Int)
    }

    //리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수를 생성?
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    // 뷰홀더를 생성해줘야 할 때 호출되는 함수 -> 아이템 뷰 객체를 만들어서 뷰홀더에 던져줌
    override fun onCreateViewHolder(viewgroup: ViewGroup, viewType: Int): AlbumRVAdapter.Viewholder {
        val binding:ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewgroup.context),viewgroup,false)

        return Viewholder(binding)
    }

    fun addItem(album : Album){
        albumList.add(album)
        notifyDataSetChanged()
    }

    fun removeItem(position : Int){
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    //뷰홀더에 데이터를 바인딩해줘야 할 때마다 호출되는 함수 -> 엄청나게 많이 호출
    override fun onBindViewHolder(holder: AlbumRVAdapter.Viewholder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(albumList[position])    //외부에서 처리할 수 있도록
        }
        holder.binding.lockerSavedsongPlayIv.setOnClickListener{
            mItemClickListener.setminiplayer(albumList[position])
        }
//        holder.binding.itemAlbumTitleTv.setOnClickListener{mIt emClickListener.onRemoveAlbum(position)}
    }


    //데이터 세트 크기를 알려주는 함수 -> 리사이클러뷰가 마지막이 언제인지를 알게 된다.
    override fun getItemCount(): Int = albumList.size

    //뷰홀더
    inner class Viewholder(val binding : ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}