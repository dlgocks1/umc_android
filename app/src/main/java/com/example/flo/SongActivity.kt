package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import kotlin.math.log

class SongActivity : AppCompatActivity() {
    //코틀린에서 상속은 콜론 : 그리고 클래스이름뒤에 ()붙이기
    lateinit var binding: ActivitySongBinding
    var Playingcheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songTitleTv.text = intent.getStringExtra("title")
            binding.songSingerTv.text = intent.getStringExtra("singer")
        }

        if (intent.hasExtra("isPlaying")) {
            if (intent.getBooleanExtra("isPlaying", true)) {
                setPlayerStatus(true)
            } else {
                setPlayerStatus(false)
            }
        }

        binding.songArrowdownIv.setOnClickListener {
//            intent.putExtra("isPlaying", Playingcheck)
//            setResult(3000, intent)
            finish()
        }

        binding.songPlayingIv.setOnClickListener {
            Playingcheck = true
            setPlayerStatus(false)
        }

        binding.songPausingIv.setOnClickListener {
            Playingcheck = false
            setPlayerStatus(true)
        }

        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(true)
        }

        binding.songRepeatIv1.setOnClickListener {
            setRepeatStatus(false)
        }

        binding.songShuffleIv.setOnClickListener {
            setShuffleStatus(true)
        }

        binding.songShuffleIv1.setOnClickListener {
            setShuffleStatus(false)
        }

    }

    fun setShuffleStatus(isShufle: Boolean) {
        if (isShufle) {
            binding.songShuffleIv.visibility = View.INVISIBLE
            binding.songShuffleIv1.visibility = View.VISIBLE
        } else {
            binding.songShuffleIv.visibility = View.VISIBLE
            binding.songShuffleIv1.visibility = View.INVISIBLE
        }
    }

    fun setRepeatStatus(isRepeating: Boolean) {
        if (isRepeating) {
            binding.songRepeatIv.visibility = View.INVISIBLE
            binding.songRepeatIv1.visibility = View.VISIBLE
        } else {
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRepeatIv1.visibility = View.INVISIBLE
        }
    }

    fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songPausingIv.visibility = View.GONE
            binding.songPlayingIv.visibility = View.VISIBLE
        } else {
            binding.songPlayingIv.visibility = View.GONE
            binding.songPausingIv.visibility = View.VISIBLE
        }
    }


//    fun setPlayerStatus(isPlayting : Boolean){ //직관성을 위해
//        if(isPlayting){
//
//        }
//    }

    //binding.miniplayer.sectonClickLisnter(){setPlayerStatus(flase or ture)} //

    //fun 함수선언()

    // lateinit var 이란? 변수를 만들고 나중에 꼭 초기화를 하는것
    //코틀린에서 변수 생성은
    //    var test1 : Int = 2 //var 처음선언후에도 값 변경 가능
    //    val test2 : String = "메롱" //val 처음선언후에는 값 변경 X


}