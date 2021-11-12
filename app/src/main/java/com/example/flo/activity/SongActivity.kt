package com.example.flo.activity

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    //코틀린에서 상속은 콜론 : 그리고 클래스이름뒤에 ()붙이기
    lateinit var binding: ActivitySongBinding
    private var song: Song = Song()
    private lateinit var player: Player
//    private val handler = Handler(Looper.getMainLooper())

    //3이 한곡재생 2는 전체반복, 1은 X
    private var RepeatStatus : Int = 3
    private var mediaplayer : MediaPlayer? = null
    private var gson : Gson = Gson()
    var ontouchnow : Boolean = false
    var album :Album = Album()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        player = Player(song.playTime,song.isPlaying)
        player.start()//쓰레드시작
        mediaplayer?.seekTo(song.now_mil)
        if(song.isPlaying){
            mediaplayer?.start()
        }

        binding.songSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {//500 / 1000
                binding.songTimeNowTv.text = String.format("%02d:%02d",song.nowPlay/60,song.nowPlay%60)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                song.nowPlay = p0!!.progress * song.playTime /1000
                ontouchnow = true

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                ontouchnow = false
                song.nowPlay = p0!!.progress * song.playTime /1000
                mediaplayer?.seekTo(song.nowPlay *1000 )
                binding.songTimeNowTv.text = String.format("%02d:%02d",song.nowPlay/60,song.nowPlay%60)
            }
        })

        binding.songArrowdownIv.setOnClickListener {
            finish()
        }

        binding.songPlayingIv.setOnClickListener {
            player.isPlaying=true
            song.isPlaying = true
            setPlayerStatus(true)
            mediaplayer?.start()
        }

        binding.songPausingIv.setOnClickListener {
            player.isPlaying=false
            song.isPlaying = false
            setPlayerStatus(false)
            mediaplayer?.pause()
            mediaplayer?.seekTo(song.nowPlay *1000 + player.mil)
        }

        binding.songRepeatIv.setOnClickListener {
            RepeatStatus = 1
            setRepeatStatus(RepeatStatus)
        }

        binding.songRepeatIv1.setOnClickListener {
            RepeatStatus = 2
            setRepeatStatus(RepeatStatus)
        }
        binding.songRepeatIv2.setOnClickListener {
            RepeatStatus = 3
            setRepeatStatus(RepeatStatus)
        }

        binding.songShuffleIv.setOnClickListener {
            setShuffleStatus(true)
        }

        binding.songShuffleIv1.setOnClickListener {
            setShuffleStatus(false)
        }

    }

    override fun onStart() {
        super.onStart()

    }

    private fun initSong(){
        if (intent.hasExtra("title") && intent.hasExtra("singer") && intent.hasExtra("playTime")
            && intent.hasExtra("isPlaying") && intent.hasExtra("nowPlay") && intent.hasExtra("music")&& intent.hasExtra("now_mil")
            && intent.hasExtra("album")) {
            song.title = intent.getStringExtra("title")!!
            song.singer = intent.getStringExtra("singer")!!
            song.playTime = intent.getIntExtra("playTime",0)
            song.isPlaying = intent.getBooleanExtra("isPlaying", false)
            song.nowPlay = intent.getIntExtra("nowPlay",0)
            song.music = intent.getStringExtra("music")!!
            song.now_mil = intent.getIntExtra("now_mil",0)

            val music = resources.getIdentifier(song.music, "raw",this.packageName)
            mediaplayer = MediaPlayer.create(this,music)

            binding.songSeekbar.progress = song.nowPlay*1000/song.playTime
            binding.songTimeNowTv.text = String.format("%02d:%02d",song.nowPlay/60,song.nowPlay%60)
            binding.songTimeEndTv.text = String.format("%02d:%02d",song.playTime/60,song.playTime%60)
            binding.songTitleTv.text = song.title
            binding.songSingerTv.text = song.singer

            var jsonAlbum = intent.getStringExtra("album")!!
            album =  gson.fromJson(jsonAlbum, Album::class.java)
            binding.songMainimgIv.setImageResource(album.coverImg!!)

            setPlayerStatus(song.isPlaying)
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

    fun setRepeatStatus(isRepeating: Int) {
        if (isRepeating == 1) {
            binding.songRepeatIv.visibility = View.INVISIBLE
            binding.songRepeatIv1.visibility = View.VISIBLE
            binding.songRepeatIv2.visibility = View.INVISIBLE
            Toast.makeText(applicationContext, "전체음악을 반복합니다.", Toast.LENGTH_SHORT).show()
        } else if (isRepeating == 2) {
            binding.songRepeatIv.visibility = View.INVISIBLE
            binding.songRepeatIv1.visibility = View.INVISIBLE
            binding.songRepeatIv2.visibility = View.VISIBLE
            Toast.makeText(applicationContext, "현재음악을 반복합니다.", Toast.LENGTH_SHORT).show()
        }
        else{
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRepeatIv1.visibility = View.INVISIBLE
            binding.songRepeatIv2.visibility = View.INVISIBLE
            Toast.makeText(applicationContext, "반복을 사용하지 않습니다.", Toast.LENGTH_SHORT).show()

        }
    }

    fun setPlayerStatus(isPlaying: Boolean) {
        if (isPlaying) {
            binding.songPausingIv.visibility = View.VISIBLE
            binding.songPlayingIv.visibility = View.GONE
        } else {
            binding.songPlayingIv.visibility = View.VISIBLE
            binding.songPausingIv.visibility = View.GONE
        }
    }

    //내부클래스 inner


    inner class Player(private val playTime:Int, var isPlaying: Boolean) : Thread (){
        var mil = 0
        override fun run() {
            try {
                while(true){
                    if(song.nowPlay >= playTime){
                        if(RepeatStatus == 1 || RepeatStatus == 2){
                            song.nowPlay = 0
                            song.now_mil= 0
                            mediaplayer?.seekTo(song.nowPlay)
                        }
                        else{
                            break
                        }

                    }
                    if(song.isPlaying){
                        mil = mil +100 //mil을 작게하면 느리게 감
                        sleep(100)
                        if(mil >= 1000){
                            mil = 0
                            song.nowPlay ++
                            runOnUiThread{
                                if(!ontouchnow) {
                                    binding.songSeekbar.progress =
                                        song.nowPlay * 1000 / playTime // song.nowPlay/song.playTime * 1000
                                }
//                                binding.songTimeNowTv.text = String.format("%02d:%02d",song.nowPlay/60,song.nowPlay%60)
                            }
                        }
                    }
                }
            }catch (e : InterruptedException){
            }
        }
    }

    override fun onPause() {
        super.onPause()
        song.nowPlay = (binding.songSeekbar.progress * song.playTime) / 1000
        song.now_mil = mediaplayer?.getCurrentPosition()!!

        var sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit() // sharedPreferences 를 조작할 때 사용
        var json = gson.toJson(song)
        editor.putString("song",json) // 자바 객체를 string으로 바꿔서 한번에 넣음
        editor.commit()
        Log.d("test", "SongActivity OnPause")

        mediaplayer?.pause()
        player.interrupt()
        song.isPlaying = false
        setPlayerStatus(false)

    }

    override fun onStop() {
        super.onStop()

        Log.d("test", "SongActivity OnsTop")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test","SongActivity onDestroy")
        player.interrupt()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제

    }

//fun 함수선언()

    // lateinit var 이란? 변수를 만들고 나중에 꼭 초기화를 하는것
    //코틀린에서 변수 생성은
    //    var test1 : Int = 2 //var 처음선언후에도 값 변경 가능
    //    val test2 : String = "메롱" //val 처음선언후에는 값 변경 X


}