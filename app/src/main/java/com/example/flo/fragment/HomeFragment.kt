package com.example.flo.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.R
import com.example.flo.Service.*
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.*
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Auth
import com.example.flo.dataclass.SongDatabase
import com.google.gson.Gson


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        SetTodayMusicAdapter()

        //오늘의 추천 곡 어댑터 설정
        //albumDatas //여기에 앨범데이타 넣기.

        //배너 어뎁터 설정
        SetbannerAdapter()

        //홈이미지 어뎁터 설정
        SetHomeimgAdapter()

        //홈 인디케이터
        SetIndicator()

        var albumServicemanager = getAlbumManager()
        val albumService = AlbumService()
        albumService.setalbumView(albumServicemanager)
        albumService.getAlbum()

        return binding.root
    }

    private inner class getAlbumManager() : GetAlbumView{
        override fun onGetAlbumLoading() {
        }

        override fun onGetAlbumSuccess(result: Result) {
            var albumDatas1 = ArrayList<Album>()
            albumDatas1.addAll(result.albums)

            val albumrcRvAdapter = AlbumRCRVAdapter(albumDatas1)
            binding.homeRecommedntodayRv.adapter = albumrcRvAdapter

            albumrcRvAdapter.setClickListiner(object : AlbumRCRVAdapter.MyItemClickListener{
                override fun onItemClick(album: Album) {
                    changeAlbumFragment(album)
                }
                private fun changeAlbumFragment(album: Album) {
                    (context as MainActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, AlbumFragment().apply {
                            arguments = Bundle().apply {
                                var gson = Gson()
                                var albumJson = gson.toJson(album)
                                putString("album", albumJson)
                            }
                        }).commitNowAllowingStateLoss()
                }
            })
        }
        override fun onGetAlbumFailure(code: Int, message: String) {

        }
    }

    private fun SetTodayMusicAdapter() {
        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums()) // songDB에서 album list를 가져옵니다.
        //오늘의 뮤직 어댑터 설정
        val albumRvAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicRecyclerView.adapter = albumRvAdapter

        albumRvAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun setminiplayer(album: Album) {
                (activity as MainActivity).initChangesong()
                (activity as MainActivity).changeMusic(album, 0)
            }

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
    }

    private fun SetbannerAdapter() {
        val bannerAdapter = BannerViewpagerAdapter(this)
        bannerAdapter.addFragment(R.drawable.banner_ad1)
        bannerAdapter.addFragment(R.drawable.img_home_viewpager_exp)
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //        val bannerAdapter = BannerViewpagerAdapter2()
        //        var imglist = arrayListOf<Int>()
        //        imglist.add(R.drawable.banner_ad1)
        //        imglist.add(R.drawable.img_home_viewpager_exp)
        //        bannerAdapter.addImgList(imglist)
        //        binding.homeBannerVp.adapter = bannerAdapter
        //        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun SetHomeimgAdapter() {
        val homeimgAdapter = HomeimgViewpagerAdapter(this)
        homeimgAdapter.addFragment(
            HomeimgFragment(
                2, R.drawable.home_backgroundimg2, "솔지, 안예은 감성충만 플레이 리스트", "해찬이가 좋아하는 노래 MIX ",
                R.drawable.home_mainimg_21, R.drawable.home_mainimg_22, " ", " ",
                " ", " ", "2021.10.09", R.drawable.home_mainimg_23
            )
        )

        homeimgAdapter.addFragment(
            HomeimgFragment(
                1,
                R.drawable.img_default_4_x_1,
                "아드레날린 가득한 일렉트로닉",
                " ",
                R.drawable.home_mainimg13,
                R.drawable.home_mainimg14,
                "Warp 1.9",
                "Bangarang (feat. Sirah)",
                "The Bloody Beetrrots",
                "Bangarang EP",
                "총 6곡 2020.11.11"
            )
        ) //프래그먼트가 들어가야함,

        homeimgAdapter.addFragment(
            HomeimgFragment(
                1, R.drawable.home_backgroundimg1, "포근하게 덮어주는 꿈의 목소리", " ",
                R.drawable.home_mainimg1, R.drawable.home_mainimg2, "찬란한 계절", "떠나지 말아요",
                "폴킴", "조혜선", "총 15곡 2019.11.11"
            )
        )

        binding.homeBackimgVp.adapter = homeimgAdapter
        binding.homeBackimgVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }

    private fun SetIndicator() {
        binding.homeIndicator1.setImageResource(R.drawable.blue_indicator)
        binding.homeBackimgVp.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> {
                            binding.homeIndicator1.setImageResource(R.drawable.blue_indicator)
                            binding.homeIndicator2.setImageResource(R.drawable.gray_indicator)
                            binding.homeIndicator3.setImageResource(R.drawable.gray_indicator)
                        }
                        1 -> {
                            binding.homeIndicator1.setImageResource(R.drawable.gray_indicator)
                            binding.homeIndicator2.setImageResource(R.drawable.blue_indicator)
                            binding.homeIndicator3.setImageResource(R.drawable.gray_indicator)
                        }
                        else -> {
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
    }


}