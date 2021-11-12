package com.example.flo.dataclass

import java.text.FieldPosition


data class Album(
    var title: String = "",
    var singer: String = "",
    var coverImg : Int? = null,
    var songs: ArrayList<Song>? = null,
    var position: Int = 0
)
