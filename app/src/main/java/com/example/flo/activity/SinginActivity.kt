package com.example.flo.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySigninBinding
import com.example.flo.dataclass.SongDatabase
import com.example.flo.dataclass.User
import java.util.regex.Pattern

class SinginActivity : AppCompatActivity() {
    lateinit var binding: ActivitySigninBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signinBt.setOnClickListener {
            binding.signinNicknamechekcTv.visibility = View.INVISIBLE
            binding.signinEmailcheckTv.visibility = View.INVISIBLE
            binding.signinPwcheckTv.visibility = View.INVISIBLE
            binding.signinPwc2heckTv.visibility = View.INVISIBLE
            SingIn()
        }
    }

    private fun getUser(): User {
        var nickname : String = binding.signinNicknameEv.text.toString()
        val email : String = binding.signinEmailEv.text.toString() + "@" + binding.signinEmailadrEd.text.toString()
        val pwd : String = binding.signinPwEd.text.toString()

        return User(nickname,email,pwd)
    }

    private fun SingIn(){
        val userDB = SongDatabase.getInstance(this)!!
        //Log.d("test",userDB.UserDao().duplecheck_nickname(binding.signinNicknameEv.text.toString()).toString())
        if(binding.signinNicknameEv.text.toString().isEmpty()){
            Toast.makeText(this,"닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signinEmailEv.text.toString().isEmpty() || binding.signinEmailadrEd.text.toString().isEmpty()){
            Toast.makeText(this,"이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(userDB.UserDao().duplecheck_nickname(binding.signinNicknameEv.text.toString()) != null){
            binding.signinNicknamechekcTv.visibility = View.VISIBLE
            return
        }
        if(userDB.UserDao().duplecheck_email(binding.signinEmailEv.text.toString() + "@" + binding.signinEmailadrEd.text.toString()) != null){
            binding.signinEmailcheckTv.visibility = View.VISIBLE
            return
        }

        if(binding.signinPwEd.text.toString() != binding.signinPwcheckEd.text.toString()){
            binding.signinPwc2heckTv.visibility = View.VISIBLE
            Toast.makeText(this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$", binding.signinPwEd.text.toString())){
            binding.signinPwcheckTv.visibility = View.VISIBLE
            Toast.makeText(this,"숫자, 문자, 특수문자 모두 포함해야합니다. (8~15자)", Toast.LENGTH_SHORT).show()
            return
        }
        //^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$





        userDB.UserDao().insert(getUser())
        val users = userDB.UserDao().getUsers()
        Log.d("test",users.toString())

        finish()
    }
}