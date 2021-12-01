package com.example.flo.dataclass

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class,Album::class,User::class,Like::class,NameJwt::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun SongDao() : SongDao
    abstract fun albumDao(): AlbumDao
    abstract fun UserDao(): UserDao
    abstract fun NameJwtDao() : NameJwtDao

    companion object {
        private var instance: SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,SongDatabase::class.java,"song-database"//다른 데이터 베이스랑 이름겹치면 꼬임
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }//Please provide the necessary Migration path via RoomDatabase ->fallbackToDestructiveMigration
            }

            return instance
        }
    }
}