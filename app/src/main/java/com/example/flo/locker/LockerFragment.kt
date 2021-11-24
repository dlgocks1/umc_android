package com.example.flo.locker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.activity.LoginActivity
import com.example.flo.activity.MainActivity
import com.example.flo.adaptors.LockerViewAdapter
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.dataclass.SongDatabase
import com.google.android.material.tabs.TabLayoutMediator


class LockerFragment(var isselect : Boolean) : Fragment() {

    lateinit var binding: FragmentLockerBinding
    val information = arrayListOf("♡ 좋아요", "저장앨범","음악파일","많이 들은","팔로잉","최근 감상","음악 파일")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerViewAdapter(this,isselect)
        binding.lockerVp.adapter = lockerAdapter

        TabLayoutMediator(binding.lockerTablayoutTb, binding.lockerVp){
                tab, position -> tab.text=information[position]
        }.attach()


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initView()

    }

    private fun initView(){
        val jwt = getJwt()
        val userDB = SongDatabase.getInstance(requireContext())!!
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener{
                //startActivity(Intent(activity, LoginActivity::class.java))
                startActivityForResult(Intent(activity, LoginActivity::class.java),100)
            }
            binding.lockerProfileIv.visibility = View.INVISIBLE
            binding.lockerNicknameTv.visibility = View.INVISIBLE
        }
        else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
                (activity as MainActivity).finish()
                startActivity(Intent(activity, MainActivity::class.java))
            }
            binding.lockerNicknameTv.visibility = View.VISIBLE
            binding.lockerProfileIv.visibility = View.VISIBLE
            binding.lockerNicknameTv.text = userDB.UserDao().getNicknameByid(jwt)
        }
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    (activity as MainActivity).finish()
                }
            }
        }
    }

    private fun logout(){
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt")
        editor.commit()
    }

}