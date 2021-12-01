package com.example.flo.activity

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.flo.R
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.example.flo.dataclass.SongDatabase
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {
    //코틀린에서 상속은 콜론 : 그리고 클래스이름뒤에 ()붙이기
    lateinit var binding: ActivitySongBinding
    private var song: Song = Song()
    var album :Album = Album()
    private var songlist = ArrayList<Song>()
    private var nowPos :Int = 0
    private lateinit var SongDB : SongDatabase
    private lateinit var player: Player
//    private val handler = Handler(Looper.getMainLooper())

    //3이 한곡재생 2는 전체반복, 1은 X
    private var RepeatStatus : Int = 3
    private var mediaplayer : MediaPlayer? = null
    private var gson : Gson = Gson()
    var ontouchnow : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initPlaylist()  // SongDB album, song 가져오기 초기화
        initClickListeener()    //클릭리스너 모음
        ChangePlayertext()  //플레이어 텍스트 바꿈

        player = Player()
        player.start()

        setMediaplayerMusic()
        setPlayerStatus(song.isPlaying)

    }

    private fun initClickListeener() {
        binding.songArrowdownIv.setOnClickListener {
            finish()
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

        binding.songPlayingIv.setOnClickListener {
            song.isPlaying = true
            setPlayerStatus(true)
            mediaplayer?.start()
        }

        binding.songPausingIv.setOnClickListener {
            song.isPlaying = false
            setPlayerStatus(false)
            mediaplayer?.pause()
            mediaplayer?.seekTo(song.nowPlay * 1000 + player.mil)
        }

        binding.songUnlikeIv.setOnClickListener{
            setLike(songlist[nowPos].isLike)
            setIslikeStatus(true)
            ToastIsLike(true)

        }

        binding.songLikeIv.setOnClickListener{
            setLike(songlist[nowPos].isLike)
            setIslikeStatus(false)

            ToastIsLike(false)

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

        binding.songPreviousIv.setOnClickListener{
            moveSong(-1)
        }

        binding.songNextIv.setOnClickListener{
            moveSong(1)
        }
    }

    private fun ToastIsLike(islike : Boolean) {
        var toast = Toast(this)
        var v1 = layoutInflater.inflate(R.layout.toast_islike,null)
        var toasttx: TextView? = v1.findViewById(R.id.islike_toast_tv)
        if(islike){
            toasttx?.setText(R.string.islike)
        }
        else{
            toasttx?.setText(R.string.isunlike)
        }
        toast.view = v1
        toast.setGravity(0, Gravity.CENTER, 600)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }


    private fun setIslikeStatus(islike: Boolean) {
        if (!islike) {
            binding.songLikeIv.visibility = View.INVISIBLE    // 지금 좋아함.
            binding.songUnlikeIv.visibility = View.VISIBLE
        } else {
            binding.songLikeIv.visibility = View.VISIBLE       // 안좋아함
            binding.songUnlikeIv.visibility = View.INVISIBLE
        }
    }

    private fun moveSong(direct : Int){
        if( nowPos + direct < 0 ) {
            //Toast.makeText(this,"first song",Toast.LENGTH_SHORT).show()
            song.now_mil=0
            song.nowPlay=0

            SongDB.SongDao().update(song)   //실행했던 곡 진행시간을 0으로 초기화

            nowPos = songlist.size -1

            mediaplayer?.release()
            mediaplayer = null
            player.interrupt()
            setPlayer()
            return
        }
        if ( nowPos + direct >= songlist.size){
            //Toast.makeText(this,"last song",Toast.LENGTH_SHORT).show()

            song.now_mil=0
            song.nowPlay=0
            SongDB.SongDao().update(song)   //실행했던 곡 진행시간을 0으로 초기화

            nowPos = 0
            mediaplayer?.release()
            mediaplayer = null
            player.interrupt()
            setPlayer()
            return
        }

        song.now_mil=0
        song.nowPlay=0
        SongDB.SongDao().update(song)   //실행했던 곡 진행시간을 0으로 초기화

        nowPos += direct
        mediaplayer?.release()
        mediaplayer = null
        player.interrupt()
        setPlayer() // 플레이어 재설정, 텍스트및 이미지 변경
    }

    fun setPlayer(){
        song = songlist[nowPos]
        song.isPlaying = true
        mediaplayer?.seekTo(song.now_mil)
        player = Player()
        player.start()

        ChangePlayertext()
        setMediaplayerMusic()

        setPlayerStatus(song.isPlaying)
    }

    private fun setLike(isLike : Boolean){
        songlist[nowPos].isLike = !isLike
        SongDB.SongDao().updateIsLikeById(!isLike, songlist[nowPos].id)
    }

    private fun setMediaplayerMusic() {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaplayer = MediaPlayer.create(this, music)
        mediaplayer?.seekTo(song.now_mil)
        if (song.isPlaying) {
            mediaplayer?.start()
        }
    }

    private fun initPlaylist(){
        SongDB = SongDatabase.getInstance(this)!!

        val album_gtf =  getSharedPreferences("albumId", MODE_PRIVATE)  //앨범 받아오기
        val albumId = album_gtf.getInt("albumId",0)
        album = if(albumId == 0){
            Album(
                2,
                "GLASSY", "조유리", R.drawable.album_img2
            )
        }
        else {
            SongDB.albumDao().getAlbum(albumId)
        }

        val spf = getSharedPreferences("songId", MODE_PRIVATE) // Song 받아오기
        val songId = spf.getInt("songId",0)
        song = if(songId == 0){
            SongDB.SongDao().getSong(1)
        }else{
            SongDB.SongDao().getSong(songId)
        }
        
        songlist = SongDB!!.SongDao().getSongInAlbum(album.albumIdx) as ArrayList<Song>
        nowPos = getPlayingSongPosition()
        song = songlist[nowPos]
    }


    private fun ChangePlayertext() {

        binding.songSeekbar.progress = song.nowPlay * 1000 / song.playTime
        binding.songTimeNowTv.text =
            String.format("%02d:%02d", song.nowPlay / 60, song.nowPlay % 60)
        binding.songTimeEndTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songMainimgIv.setImageResource(album.coverImg!!)

        setIslikeStatus(SongDB.SongDao().getSong(songlist[nowPos].id).isLike)
    }

    private fun getPlayingSongPosition() : Int{
        for (i in 0 until songlist.size){
            if (songlist[i].id == song.id){
                return i
            }
        }
        return 0
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

    inner class Player() : Thread (){
        var mil = 0
        override fun run() {
            try {
                while(true){
                    if(song.nowPlay >= song.playTime){
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
                                        song.nowPlay * 1000 / song.playTime // song.nowPlay/song.playTime * 1000
                                }
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

        song.now_mil = mediaplayer?.currentPosition!!
        SongDB.SongDao().update(song)

        var spf = getSharedPreferences("albumId", MODE_PRIVATE)
        var editor : SharedPreferences.Editor = spf.edit() //
        editor.putInt("albumId",album.albumIdx)
        editor.commit()



        spf =  getSharedPreferences("songId", MODE_PRIVATE)
        editor = spf.edit()
        editor.putInt("songId",song.id)
        editor.commit()

        mediaplayer?.pause()
        player.interrupt()
        setPlayerStatus(false)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test","SongActivity onDestroy")
        player.interrupt()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제

    }
}