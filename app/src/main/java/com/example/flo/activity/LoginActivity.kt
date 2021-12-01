package com.example.flo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.Service.AuthService
import com.example.flo.Service.LoginView
import com.example.flo.Service.saveUserIdx
import com.example.flo.Service.savejwt
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.dataclass.Auth
import com.example.flo.dataclass.SongDatabase
import com.example.flo.dataclass.User

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSigninTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginLoginBt.setOnClickListener{
            login()
        }

    }

//    private fun login() {
//        if (binding.loginEmailEd.text.toString()
//                .isEmpty() || binding.loginEmailadrEd.text.toString().isEmpty()
//        ) {
//            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if (binding.loginPwEd.text.toString().isEmpty()) {
//            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val email : String = binding.loginEmailEd.text.toString() + "@" + binding.loginEmailadrEd.text.toString()
//        val pwd : String = binding.loginPwEd.text.toString()
//
//        var userDB = SongDatabase.getInstance(this)!!
//        val user = userDB.UserDao().getUser(email,pwd)
//
//        user?.let { //user가 null이 아닐때 실행
//            Log.d("test", "userId : ${user.id}, $user")
//            //발급받은 jwt를 저장해주는 함수
//            saveJwt(user.id)
//
//            startMainActivity()
//            setResult(Activity.RESULT_OK, intent)
//            finish()
//            return
//        }
//        Toast.makeText(this,"회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//    }

    private fun login(){
        binding.loginErrorTv.visibility = View.GONE
         if (binding.loginEmailEd.text.toString()
                .isEmpty() || binding.loginEmailadrEd.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPwEd.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(getUser())
    }

    private fun getUser(): User {
        val email : String = binding.loginEmailEd.text.toString() + "@" + binding.loginEmailadrEd.text.toString()
        val pwd : String = binding.loginPwEd.text.toString()

        return User(email,pwd,"")
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveJwt(jwt : String){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt",jwt)
        editor.commit()
    }

    override fun onLoginLoading() {
    }

    override fun onLoginSuccess(auth: Auth) {
        //saveJwt(auth.jwt)
        saveUserIdx(this, auth.userIdx)
        savejwt(this, auth.jwt)

        startMainActivity()
        setResult(Activity.RESULT_OK, intent)
        finish()
        return
    }

    override fun onLoginFailure(code: Int, message: String) {
        when(code){
            2015, 2019, 3014->{
                binding.loginErrorTv.visibility = View.VISIBLE
                binding.loginErrorTv.text = message
            }
        }
    }

}