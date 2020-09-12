package com.wiryatech.consumerapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wiryatech.consumerapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment()).commit()
    }

}
