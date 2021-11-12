package com.example.flo.activity

import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.*
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.example.flo.fragment.HomeFragment
import com.example.flo.fragment.LockerFragment
import com.example.flo.fragment.LookFragment
import com.example.flo.fragment.SearchFragment
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var album: Album = Album()
    var song: Song = Song()
    private var mediaplayer : MediaPlayer? = null
    var threadcondition = false
    lateinit var player: Player
    var ontouchnow : Boolean = false
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var songarray = ArrayList<Song>()
        songarray.apply {
            songarray.add(Song("music_lilac","라일락","아이유(IU)",0,234,false, 0))
            songarray.add(Song("music_flu","Flu","아이유(IU)",0,189,false, 0))
        }
        album = Album("Lilac", "아이유(IU)", R.drawable.img_album_exp2,songarray)

        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("nowPlay",song.nowPlay)
            intent.putExtra("music",song.music)
            song.now_mil = mediaplayer!!.currentPosition
            intent.putExtra("now_mil",song.now_mil)

            var json = gson.toJson(album)
            intent.putExtra("album",json)

            startActivity(intent)
        }

        binding.mainSongSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {//500 / 1000
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                ontouchnow = true
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                song.nowPlay = p0!!.progress * song.playTime /1000
                mediaplayer?.seekTo(song.nowPlay *1000)
                ontouchnow = false
            }

        })

        initNavigation()

        binding.mainMiniplayerBtn.setOnClickListener {
            song.isPlaying=true
            player.isPlaying=true
            setMiniPlayer(song)
            mediaplayer?.start()

        }

        binding.mainPauseBtn.setOnClickListener {
            song.isPlaying=false
            player.isPlaying=false
            setMiniPlayer(song)
            mediaplayer?.pause()
            mediaplayer?.seekTo(song.nowPlay *1000 + player.mil)

        }


        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }

    fun changeMusic(album : Album){
        this.album = album

        mediaplayer?.pause()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제

        song = album.songs!![album.position]
        song.nowPlay = 0
        song.now_mil = 0
        setMiniPlayer(song)
        val music = resources.getIdentifier(song.music, "raw",this.packageName)
        mediaplayer = MediaPlayer.create(this,music)
        if(song.isPlaying){
            mediaplayer?.start()
        }

    }

    fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainSongSeekbar.progress = (song.nowPlay*1000/song.playTime)

        if(song.isPlaying){
            binding.mainMiniplayerBtn.visibility= View.GONE
            binding.mainPauseBtn.visibility= View.VISIBLE
        }
        else{
            binding.mainMiniplayerBtn.visibility= View.VISIBLE
            binding.mainPauseBtn.visibility= View.GONE
        }

    }

    inner class Player(private val playTime:Int, var isPlaying: Boolean) : Thread (){
        var mil = 0
        override fun run() {
            try {
                while(true && threadcondition){
                    if(song.nowPlay >= song.playTime){
                        runOnUiThread {
                            NextSong()
                        }
                        mil = 0
//                        break
                    }
                    if(song.isPlaying){
                        mil = mil + 100
                        sleep(100)
                        if(mil >= 1000){
                            mil = 0
                            song.nowPlay ++
                            runOnUiThread{
                                if(!ontouchnow) {
                                    binding.mainSongSeekbar.progress = song.nowPlay *1000 / song.playTime
                                }
                            }
                        }
                    }
                }
            }catch (e : InterruptedException){
                Log.d("test","쓰레드 종료")
            }

        }

        private fun NextSong() {
//            Log.d("test", album.songs?.size.toString() + album.position)
            album.position++
            if (album.songs!!.size-1 < album.position) {
                album.position = 0
            }
            album.songs!![album.position].isPlaying = true
            changeMusic(album)
        }
    }

    override fun onStart() {
        super.onStart()

        var sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val jsonSong = sharedPreferences.getString("song",null)
//        Log.d("test","메인스타트")
        song = if(jsonSong == null){
            Song("music_lilac","라일락","아이유(IU)",0,234,false, 0)
        }
        else {
//            Song("music_lilac","라일락","아이유(IU)",0,234,false, 0)
            gson.fromJson(jsonSong, Song::class.java)
        }

//        Log.d("test",jsonSong.toString())
        setMiniPlayer(song)
        player = Player(song.playTime,song.isPlaying)
        player.start()
        threadcondition = true

        val music = resources.getIdentifier(song.music, "raw",this.packageName)
        mediaplayer = MediaPlayer.create(this,music)
        mediaplayer?.seekTo(song.now_mil)

        if(song.isPlaying){
            mediaplayer?.start()
        }

    }


    override fun onPause() {
        super.onPause()
//        Log.d("test","메인퍼즈")
        threadcondition = false
        song.isPlaying = false
        song.now_mil = mediaplayer!!.currentPosition
        mediaplayer?.pause()
        var sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit() // sharedPreferences 를 조작할 때 사용
        var json = gson.toJson(song)
        editor.putString("song",json) // 자바 객체를 string으로 바꿔서 한번에 넣음
        editor.commit()
    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()

        song.isPlaying = false

        Log.d("test","메인 종료")
        player.interrupt()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제
    }
}

