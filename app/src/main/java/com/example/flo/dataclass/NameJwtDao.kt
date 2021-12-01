package com.example.flo.dataclass

import androidx.room.*

@Dao
interface NameJwtDao {
    @Insert
    fun insert(nameJwt: NameJwt)

    @Update
    fun update(nameJwt: NameJwt)

    @Delete
    fun delete(nameJwt: NameJwt)

    //@Query("SELECT * FROM NameJwtTable") // 테이블의 모든 값을 가져와라
    //fun getAllNJ(): List<NameJwt>

//    @Query("SELECT * FROM NameJwtTable WHERE jwt = :jwt")
//    fun getSong(id: Int): Song

    @Query("SELECT name FROM NameJwtTable WHERE jwt = :jwt")
    fun getnameByjwt(jwt: Int) : String
}