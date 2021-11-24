package com.example.flo.activity

import android.app.Activity
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.*
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.dataclass.Album
import com.example.flo.dataclass.Song
import com.example.flo.dataclass.SongDatabase
import com.example.flo.fragment.HomeFragment
import com.example.flo.locker.LockerFragment
import com.example.flo.fragment.LookFragment
import com.example.flo.fragment.SearchFragment
import com.example.flo.locker.SavedsongFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    var album: Album = Album()  //현재 무슨 앨범인가?
    var song: Song = Song() //현재 플레이중인 노래
    lateinit var songlist : ArrayList<Song> // 앨범에 무슨 노래가 들었는가?
    private var mediaplayer : MediaPlayer? = null
    private lateinit var SongDB : SongDatabase
    private var nowPos :Int = 0

    var threadcondition = false //쓰레드컨디션
    lateinit var player: Player //쓰레드
    var ontouchnow : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
        inputDummySongs()
        inputDummyAlbums()
        setClickListener()

    }

    private fun getPlayingSongPosition() : Int{
        for (i in 0 until songlist.size){
            if (songlist[i].id == song.id){
                return i
            }
        }
        return 0
    }

    private fun setClickListener() {
        binding.mainPlayerLayout.setOnClickListener {
            val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("playTime", song.playTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("nowPlay", song.nowPlay)
//            intent.putExtra("music", song.music)
//            song.now_mil = mediaplayer!!.currentPosition
//            intent.putExtra("now_mil", song.now_mil)
//            intent.putExtra("id",song.id)
//            var json = gson.toJson(album)
//            intent.putExtra("album", json)
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


        binding.mainMiniplayerBtn.setOnClickListener {
            song.isPlaying = true
            setMiniPlayer(song)
            mediaplayer?.start()
        }

        binding.mainPauseBtn.setOnClickListener {
            song.isPlaying = false
            setMiniPlayer(song)
            mediaplayer?.pause()
            mediaplayer?.seekTo(song.nowPlay * 1000 + player.mil)
        }

        binding.mainPreviousBtn.setOnClickListener{
            moveSong(-1)
        }

        binding.mainNextBtn.setOnClickListener{
            moveSong(1)
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
                        .replace(R.id.main_frm, LockerFragment(false))
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

        binding.lockerSheetdialog.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.sheetdialog_hear -> {

                }

                R.id.sheetdialog_playlist -> {

                }

                R.id.sheetdialog_mylist -> {

                }

                R.id.sheetdialog_savesong -> {
                    var songs = SongDB.SongDao().getLikedSongs(true)
                    for(i in 0..songs.size-1){
                        SongDB.SongDao().updateIsLikeById(false,songs[i].id)
                        if( songs[i].id == song.id){
                            song.isLike = false
                        }
                    }
                    val fragment = supportFragmentManager?.findFragmentById(R.id.locker_savedsong_root) as? SavedsongFragment
//                    supportFragmentManager.executePendingTransactions()
                    if (fragment != null) {
                        fragment.isselect = true
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment(true))
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }

    fun showSheetdialog(isvisible : Boolean){
        if(isvisible){
            binding.mainBnv.visibility = View.GONE
            var animation :Animation = TranslateAnimation(0f,0f,150f, 0f);
            animation.setDuration(200)
            binding.lockerSheetdialog.animation = animation
            binding.lockerSheetdialog.visibility = View.VISIBLE
        }
        else{
            var animation :Animation = AlphaAnimation(0f,1f);
            animation.setDuration(500)
            binding.mainBnv.animation = animation
            binding.mainMiniplayerBtn.animation = animation
            binding.mainBnv.visibility = View.VISIBLE

            animation  = TranslateAnimation(0f,0f,0f, 1500f);
            animation.setDuration(500)
            binding.lockerSheetdialog.animation = animation
            binding.lockerSheetdialog.visibility = View.GONE
        }
    }

    private fun moveSong(direct : Int){

        if( nowPos + direct < 0 ) {
            //Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
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
            //Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
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

        setMiniPlayer(song)
        setMediaplayerMusic()

    }

    private fun setMediaplayerMusic() {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaplayer = MediaPlayer.create(this, music)
        mediaplayer?.seekTo(song.now_mil)
        if (song.isPlaying) {
            mediaplayer?.start()
        }
    }

    private fun inputDummyAlbums() {
        //SongDB = SongDatabase.getInstance(this)!!
        val albums = SongDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        SongDB.albumDao().insert(
            Album(
                1,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )

        SongDB.albumDao().insert(
            Album(
                2,
                "GLASSY", "조유리", R.drawable.album_img2
            )
        )

        SongDB.albumDao().insert(
            Album(
                3,
                "잊혀진 계절", "The One(더원)", R.drawable.album_img3
            )
        )

        SongDB.albumDao().insert(
            Album(
                4,
                "WOO", "27RING", R.drawable.album_img4
            )
        )

    }

    private fun inputDummySongs() {
        SongDB = SongDatabase.getInstance(this)!!
        val songs = SongDB.SongDao().getSongs()

        if(songs.isNotEmpty()) return

        SongDB.SongDao().insert(Song("music_lilac","라일락","아이유(IU)",true,0,212,false, 0,false,1,R.drawable.img_album_exp2))
        SongDB.SongDao().insert(Song("music_flu","Flu","아이유(IU)",false,0,189,false, 0,false,1,R.drawable.img_album_exp2))
        SongDB.SongDao().insert(Song("music_coin","Coin","아이유(IU)",false,0,223,false, 0,false,1,R.drawable.img_album_exp2))
        SongDB.SongDao().insert(Song("music_sayspringsay","봄 안녕 봄","아이유(IU)",false,0,325,false, 0,false,1,R.drawable.img_album_exp2))
        SongDB.SongDao().insert(Song("music_celebrity","Celebrity","아이유(IU)",true,0,196,false, 0,false,1,R.drawable.img_album_exp2))
        SongDB.SongDao().insert(Song("music_glassy","GLASSY","조유리",true,0,190,false, 0,false, 2,R.drawable.album_img2))
        SongDB.SongDao().insert(Song("music_expressmoon","Express Moon","조유리",false,0,185,false, 0,false, 2,R.drawable.album_img2))
        SongDB.SongDao().insert(Song("music_fallbox","가을 상자(With 이석훈)","조유리",false,0,267,false, 0,false, 2,R.drawable.album_img2))
        SongDB.SongDao().insert(Song("music_forgottenseason","잊혀진 계절","The One (더원)",true,0,251,false, 0,false, 3,R.drawable.album_img3))
        SongDB.SongDao().insert(Song("music_woo","WOO","27RING",true,0,141,false, 0,false, 4,R.drawable.album_img4))

    }



    fun changeMusic(album : Album, position: Int){ //  포지션값으로 노래바꿈
        initChangesong()
        this.album = album

        player.interrupt()
        mediaplayer?.pause()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제

        val SongDB = SongDatabase.getInstance(this)
        songlist = SongDB!!.SongDao().getSongInAlbum(album.id) as ArrayList<Song>
        nowPos = position
        song = songlist[nowPos]
        song.isPlaying = true

        setMiniPlayer(song)
        val music = resources.getIdentifier(song.music, "raw",this.packageName)
        mediaplayer = MediaPlayer.create(this,music)
        if(song.isPlaying){
            mediaplayer?.start()
        }
        player = Player()
        player.start()
    }

    private fun nextSong(){
        var position :Int = 0
        val SongDB = SongDatabase.getInstance(this)!!
        songlist = SongDB!!.SongDao().getSongInAlbum(album.id) as ArrayList<Song>


        for (i in 0..songlist.size-1){
            if (song.id == songlist[i].id){
                position = i
            }
        }

        position++

        if(songlist.size-1 < position){
            position=0
        }
        nowPos = position
        song = songlist[nowPos]
        changeMusic(album,nowPos)
    }

    fun initChangesong(){
        song.nowPlay = 0
        song.now_mil = 0
        SongDB.SongDao().update(song)
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

    inner class Player() : Thread (){
        var mil = 0
        override fun run() {
            try {
                while(true && threadcondition){
                    if(song.nowPlay >= song.playTime){
                        runOnUiThread {
                            initChangesong()
                            nextSong()
                        }
//                        mil = 0
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
            }

        }

    }

    override fun onStart() {
        super.onStart()
        //val SongDB = SongDatabase.getInstance(this)!!
        val album_gtf = getSharedPreferences("albumId", MODE_PRIVATE)  //앨범 받아오기
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

        songlist = SongDB!!.SongDao().getSongInAlbum(album.id) as ArrayList<Song>
        val spf =  getSharedPreferences("songId", MODE_PRIVATE) // Song 받아오기
        val songId = spf.getInt("songId",0)
        song = if(songId == 0){
            SongDB.SongDao().getSong(1)
        }else{
            SongDB.SongDao().getSong(songId)
        }

        nowPos = getPlayingSongPosition()
        song = songlist[nowPos]
        setMiniPlayer(song)

        player = Player()
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
        threadcondition = false
        song.now_mil = mediaplayer!!.currentPosition
        SongDB.SongDao().update(song)
        mediaplayer?.pause()

        var spf = getSharedPreferences("albumId", MODE_PRIVATE)
        var editor : SharedPreferences.Editor = spf.edit() // sharedPreferences 를 조작할 때 사용
        editor.putInt("albumId",album.id) // 자바 객체를 string으로 바꿔서 한번에 넣음
        editor.commit()

        spf =  getSharedPreferences("songId", MODE_PRIVATE)
        editor = spf.edit() // sharedPreferences 를 조작할 때 사용
        editor.putInt("songId",song.id) // 자바 객체를 string으로 바꿔서 한번에 넣음
        editor.commit()

    }

    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        song.isPlaying = false
        SongDB.SongDao().update(song)

        player.interrupt()
        mediaplayer?.release() //미디어 플레이어의 리소스 해제
        mediaplayer = null //미디어플레이어 해제
    }

    fun updateIsLike(islike: Boolean,songId : Int) {
        SongDB.SongDao().updateIsLikeById(islike,songId)
        SongDB.SongDao().update(SongDB.SongDao().getSong(songId))
        if(song.id == songId){
            song.isLike = false
        }
    }
}

