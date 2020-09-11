package com.wiryatech.consumerapp.ui

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiryatech.consumerapp.R
import com.wiryatech.consumerapp.models.User

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
