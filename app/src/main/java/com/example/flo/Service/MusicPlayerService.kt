package com.example.flo.Service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicPlayerService : Service() {

    val binder = LocalBinder()
    val randomNumber: Int = 5
    // Service 객체와 통신할 때 사용
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): MusicPlayerService = this@MusicPlayerService
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}