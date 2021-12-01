package com.example.flo.dataclass

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insert(user:User)

    @Query("SELECT * FROM UserTable")
    fun getUsers():List<User>

    @Query("SELECT * FROM UserTable WHERE email = :email AND password = :password")
    fun getUser(email: String, password: String) : User?

    @Query("SELECT id FROM UserTable WHERE name = :nickname")
    fun duplecheck_nickname(nickname : String) : Int?

    @Query("SELECT id FROM UserTable WHERE email = :email")
    fun duplecheck_email(email : String) : Int?

    @Query("SELECT name FROM UserTable WHERE id = :id")
    fun getNicknameByid(id: Int) : String


}