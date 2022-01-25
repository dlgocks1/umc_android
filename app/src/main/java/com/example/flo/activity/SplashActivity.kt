package com.example.flo.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.flo.Service.*
import com.example.flo.databinding.ActivitySplashBinding
import com.example.flo.dataclass.Auth

class SplashActivity : AppCompatActivity(), SignUpView {
    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            //val authService = AuthService()
            //authService.setSignUpView(this)
            //authService.autologin(getJwt(this))
            //Log.d("splash",getJwt(this).toString())

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //var intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            //네트워킹 처리, 데이터 처리를 요기서 할수 있을듯
        },2000)
    }

    override fun onSignUpLoading() {
    }

    override fun onSignUpSuccess() {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onSignUpFailure(code: Int, message: String) {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

