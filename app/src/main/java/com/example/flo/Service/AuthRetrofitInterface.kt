package com.example.flo.Service

import com.example.flo.dataclass.AuthResponse
import com.example.flo.dataclass.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user: User): Call<AuthResponse>
    //함수(바디에보낼것) : 결과로 받을 것

    @POST("/users/login")
    fun login(@Body user: User) : Call<AuthResponse>

    @GET("/users/auto-login")
    fun autologin(@Header("X-ACCESS-TOKEN") jwt: String?) : Call<AuthResponse>

}