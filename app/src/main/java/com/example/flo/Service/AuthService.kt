package com.example.flo.Service

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import com.example.flo.dataclass.AuthResponse
import com.example.flo.dataclass.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    //이걸 왜하지?
    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user : User){
//        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
//        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        val authService = getReposit().create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(user).enqueue(object : Callback<AuthResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SINGUPACT/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("SINGUPACT/API-RESPONCE-FLO",resp.toString())
                when(resp.code){
                    1000 -> signUpView.onSignUpSuccess()
                    2016,2017 -> {
                        signUpView.onSignUpFailure(resp.code, resp.message)
                    }

                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SINGUPACT/API-ERROR",t.message.toString())
                signUpView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun login(user: User) {
//        val retrofit = Retrofit.Builder().baseUrl("http://13.125.121.202").addConverterFactory(GsonConverterFactory.create()).build()
//        val authService = retrofit.create(AuthRetrofitInterface::class.java)
        val authService = getReposit().create(AuthRetrofitInterface::class.java)
        loginView.onLoginLoading()

        authService.login(user).enqueue(object : Callback<AuthResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("LOGINACT/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("LOGINACT/API-RESPONCE-FLO",resp.toString())
                when(resp.code){
                    1000 -> loginView.onLoginSuccess(resp.result!!)
                    else -> {
                        loginView.onLoginFailure(resp.code, resp.message)
                    }

                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("LOGINACT/API-ERROR",t.message.toString())
                loginView.onLoginFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun autologin(jwt: String) {

        val authService = getReposit().create(AuthRetrofitInterface::class.java)
        signUpView.onSignUpLoading()

        authService.autologin(jwt).enqueue(object : Callback<AuthResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("AUTOLOGIN/API-RESPONCE",response.toString())

                val resp = response.body()!!
                Log.d("AUTOLOGIN/API-RESPONCE-FLO",resp.toString())
                when(resp.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> {
                        signUpView.onSignUpFailure(resp.code, resp.message)
                    }

                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AUTOLOGIN/API-ERROR",t.message.toString())
                signUpView.onSignUpFailure(400,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}