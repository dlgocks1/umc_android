package com.example.flo.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.FieldPosition

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id: Int = 0, // album의 pk는 임의로 지정해주기 위해 autogenerate 안씁니다.
    var title: String = "",
    var singer: String = "",
    var coverImg : Int? = null,
)

