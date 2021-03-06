package com.example.flo.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

const val BASE_URL = "http://13.125.121.202"
//val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(
//    GsonConverterFactory.create()).build()

fun getReposit() : Retrofit{
    val retrofit = Retrofit.Builder()
        .baseUrl("http://13.125.121.202")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit
}