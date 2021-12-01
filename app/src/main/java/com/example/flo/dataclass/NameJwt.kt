package com.example.flo.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NameJwtTable")
data class NameJwt(
    val jwt : String,
    val name : String
){
    @PrimaryKey(autoGenerate = true) var id : Int =0
}

