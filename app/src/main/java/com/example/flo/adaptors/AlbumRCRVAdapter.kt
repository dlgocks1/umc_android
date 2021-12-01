package com.example.flo.adaptors

import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flo.R
import com.example.flo.databinding.ItemAlbumBinding
import com.example.flo.dataclass.Album

class AlbumRCRVAdapter(private val albumlist : ArrayList<Album>) : RecyclerView.Adapter<AlbumRCRVAdapter.ViewHolder>() {

    private lateinit var mItemClickListener: AlbumRCRVAdapter.MyItemClickListener

    interface MyItemClickListener{
        fun onItemClick(album: Album)
    }

    fun setClickListiner(itemClickListener: MyItemClickListener){
         mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumlist[position])
        holder.itemView.setOnClickListener{
            mItemClickListener.onItemClick(albumlist[position])    //외부에서 처리할 수 있도록
        }
    }

    override fun getItemCount(): Int = albumlist.size

    inner class ViewHolder(val binding : ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album : Album){
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumTitleTv.text = album.title
            val media = album.coverImgUrl
            if (media !== null) {
                Glide.with(binding.itemAlbumCoverImgIv).load(media).into(binding.itemAlbumCoverImgIv)
            } else {
                binding.itemAlbumCoverImgIv.setImageResource(R.drawable.ic_launcher_background)
            }

            //Glide.with(this).load(ImgUrls[i]).override(800, 800).into(viewHolder.img_android)

            //binding.itemAlbumCoverImgIv.setImageURI(album.coverImgUrl)
            //binding.itemAlbumCoverImgIv.setImageResource(R.drawable.album_img6)
        }
    }
}