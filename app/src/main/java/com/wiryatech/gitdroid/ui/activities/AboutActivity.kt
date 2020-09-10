package com.wiryatech.gitdroid.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wiryatech.gitdroid.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        wv.loadUrl("https://github.com/w1rya/gitdroid-new")
        wv.settings.javaScriptEnabled
    }
}