package com.example.flo.Service

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

fun saveUserIdx(context: Context, userIdx : Int){
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()
    editor.putInt("userIdx",userIdx)
    editor.commit()
}

fun savejwt(context: Context, jwt : String){
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
    val editor = spf.edit()
    editor.putString("jwt",jwt)
    editor.commit()
}

fun getJwt(context: Context) : String{
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getString("jwt","")!!
}

fun getUserIdx(context: Context): Int {
    val spf = context.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)

    return spf.getInt("userIdx",0)
}