package com.example.flo.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "SongTable")
data class Song(
    var music: String = "",
    var title: String = "",
    var singer: String = "",
    var istitle : Boolean = false,
    var nowPlay: Int = 0,
    var playTime: Int = 0,
    var isPlaying: Boolean = false,
    var now_mil : Int = 0,
    var isLike: Boolean = false,
    var albumIdx: Int = 0, // 이 song이 어떤 앨범에 담겨 있는지 가리키는 변수 (foreign key 역할)
    var coverImg : Int = 0
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
