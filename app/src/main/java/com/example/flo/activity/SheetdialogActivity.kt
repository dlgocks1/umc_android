package com.example.flo.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySheetdialogBinding

class SheetdialogActivity : AppCompatActivity() {
    lateinit var binding : ActivitySheetdialogBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivitySheetdialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}