package com.example.flo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.R
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.dataclass.SongDatabase
import com.example.flo.fragment.HomeFragment

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSigninTv.setOnClickListener {
            startActivity(Intent(this, SinginActivity::class.java))
        }

        binding.loginLoginBt.setOnClickListener{
            login()
        }

    }

    private fun login() {
        if (binding.loginEmailEd.text.toString()
                .isEmpty() || binding.loginEmailadrEd.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginPwEd.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email : String = binding.loginEmailEd.text.toString() + "@" + binding.loginEmailadrEd.text.toString()
        val pwd : String = binding.loginPwEd.text.toString()

        var userDB = SongDatabase.getInstance(this)!!
        val user = userDB.UserDao().getUser(email,pwd)

        user?.let { //user가 null이 아닐때 실행
            Log.d("test", "userId : ${user.id}, $user")
            //발급받은 jwt를 저장해주는 함수
            saveJwt(user.id)

            startMainActivity()
            setResult(Activity.RESULT_OK, intent)
            finish()
            return
        }
        Toast.makeText(this,"회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun saveJwt(jwt : Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt",jwt)
        editor.commit()
    }

}