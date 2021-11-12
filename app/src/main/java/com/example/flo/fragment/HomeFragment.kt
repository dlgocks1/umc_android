package com.example.flo.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.adaptors.BannerViewpagerAdapter
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.R
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.AlbumRVAdapter
import com.example.flo.adaptors.HomeimgViewpagerAdapter
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.google.gson.Gson
import java.time.LocalDate
import kotlin.math.log


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
//    val information = arrayListOf("●","●","●")
    private var albumDatas = ArrayList<Album>()
    private var gson : Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodaymusicAlbumIv1.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment())
//                .commitNowAllowingStateLoss()
//        }
//        binding.homeTodaymusicAlbumIv2.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumcoordinateFragment())
//                .commitNowAllowingStateLoss()
//        }

        //오늘의 뮤직 데이터 리스트 생성 더미데이터
        var song:Song = Song()
        var songarray = ArrayList<Song>()
        songarray.apply {
            songarray.add(Song("music_lilac","라일락","아이유(IU)",0,212,false, 0))
            songarray.add(Song("music_flu","Flu","아이유(IU)",0,189,false, 0))
            songarray.add(Song("music_coin","Coin","아이유(IU)",0,223,false, 0))
            songarray.add(Song("music_sayspringsay","봄 안녕 봄","아이유(IU)",0,325,false, 0))
            songarray.add(Song("music_celebrity","Celebrity","아이유(IU)",0,196,false, 0))

        }
        albumDatas.add(Album("Lilac", "아이유(IU)", R.drawable.img_album_exp2,songarray))

        songarray = ArrayList<Song>()
        songarray.clear()

        songarray.apply {
            songarray.add(Song("music_glassy","GLASSY","조유리",0,190,false, 0))
            songarray.add(Song("music_expressmoon","Express Moon","조유리",0,185,false, 0))
            songarray.add(Song("music_fallbox","가을 상자(With 이석훈)","조유리",0,267,false, 0))
        }
        albumDatas.add(Album("GLASSY", "조유리", R.drawable.album_img2,songarray))

        songarray = ArrayList<Song>()
        songarray.clear()

        songarray.apply {
            songarray.add(Song("music_forgottenseason","잊혀진 계절","The One (더원)",0,251,false, 0))
        }
        albumDatas.add(Album("잊혀진 계절", "The One (더원)", R.drawable.album_img3,songarray))

        songarray = ArrayList<Song>()
        songarray.clear()

        songarray.apply {
            songarray.add(Song("music_woo","WOO","27RING",0,141,false, 0))
        }
        albumDatas.add(Album("WOO", "27RING", R.drawable.album_img4,songarray))

//        albumDatas.apply {
//            var song:Song = Song()
//            var songarray = ArrayList<Song>()
//            songarray.apply {
//                songarray.add(Song("music_lilac","라일락","아이유(IU)",0,234,false, 0))
//            }
//            add(Album("Lilac", "아이유(IU)", R.drawable.img_album_exp2,songarray))
//            songarray.apply {
//                songarray.add(Song("music_glassy","GLASSY","조유리",0,234,false, 0))
//            }
//            add(Album("GLASSY", "조유리", R.drawable.album_img2,songarray))
//            add(Album("잊혀진 계절", "The One (더원)", R.drawable.album_img3,songarray))
//            add(Album("WOO", "27RING", R.drawable.album_img4,songarray))
//        }

        //오늘의 뮤직 어댑터 설정
        val albumRvAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicRecyclerView.adapter = albumRvAdapter
//        binding.homeTodayMusicRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL)
        albumRvAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album : Album) {
                changeAlbumFragment(album)
            }

            override fun setminiplayer(album: Album) {
                album.position=0
                (activity as MainActivity).changeMusic(album)
            }

//            override fun onRemoveAlbum(position: Int) {
//                albumRvAdapter.removeItem(position)
//            }

            private fun changeAlbumFragment(album: Album) {
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, AlbumFragment().apply {
                        arguments = Bundle().apply {
                            var gson = Gson()
                            var albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    })
                    .commitNowAllowingStateLoss()
            }
        })

        //배너 어뎁터 설정
        val bannerAdapter = BannerViewpagerAdapter(this)
        bannerAdapter.addFragment(R.drawable.banner_ad1)
        bannerAdapter.addFragment(R.drawable.img_home_viewpager_exp)
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        //홈이미지 어뎁터 설정
        val homeimgAdapter = HomeimgViewpagerAdapter(this)
        homeimgAdapter.addFragment(
            HomeimgFragment(2,R.drawable.home_backgroundimg2,"솔지, 안예은 감성충만 플레이 리스트",
            R.drawable.home_mainimg_21,R.drawable.home_mainimg_22, " ", " ",
            " ", " ","2021.10.09",R.drawable.home_mainimg_23)
        )

        homeimgAdapter.addFragment(
            HomeimgFragment(1,R.drawable.img_default_4_x_1,"아드레날린 가득한 일렉트로닉",
            R.drawable.home_mainimg13,R.drawable.home_mainimg14,"Warp 1.9","Bangarang (feat. Sirah)",
        "The Bloody Beetrrots","Bangarang EP","총 6곡 2020.11.11")
        ) //프래그먼트가 들어가야함,

        homeimgAdapter.addFragment(
            HomeimgFragment(1,R.drawable.home_backgroundimg1,"포근하게 덮어주는 꿈의 목소리",
            R.drawable.home_mainimg1,R.drawable.home_mainimg2, "찬란한 계절", "떠나지 말아요",
        "폴킴", "조혜선","총 15곡 2019.11.11")
        )

        binding.homeBackimgVp.adapter = homeimgAdapter
        binding.homeBackimgVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        
        //홈 인디케이터
        binding.homeIndicator1.setImageResource(R.drawable.blue_indicator)
        binding.homeBackimgVp.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position){
                        0->{
                            binding.homeIndicator1.setImageResource(R.drawable.blue_indicator)
                            binding.homeIndicator2.setImageResource(R.drawable.gray_indicator)
                            binding.homeIndicator3.setImageResource(R.drawable.gray_indicator)
                        }
                        1->{
                            binding.homeIndicator1.setImageResource(R.drawable.gray_indicator)
                            binding.homeIndicator2.setImageResource(R.drawable.blue_indicator)
                            binding.homeIndicator3.setImageResource(R.drawable.gray_indicator)
                        }
                        else->{
                                binding.homeIndicator1.setImageResource(R.drawable.gray_indicator)
                                binding.homeIndicator2.setImageResource(R.drawable.gray_indicator)
                                binding.homeIndicator3.setImageResource(R.drawable.blue_indicator)
                        }
                    }
                }
            })
        }
//        TabLayoutMediator(binding.homeMainimgTb, binding.homeBackimgVp){
//                tab, position -> tab.setIcon(R.drawable.gray_indicator)
//        }.attach()


//        val springDotsIndicator = binding.homeMainimgIndicator
//        springDotsIndicator.setViewPager2(binding.homeBackimgVp)

//        binding.homeTodaymusicTv.setOnClickListener{
//            Log.d("Test",  binding.homeBackimgVp.currentItem.toString())
//        }


        return binding.root
    }




}