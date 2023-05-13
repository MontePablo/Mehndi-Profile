package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.adretsoftwere.mehndinterior.daos.MySharedStorage

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.statusBarColor=getColor(R.color.white)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        MySharedStorage.init(applicationContext)
//        actionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(applicationContext, Login::class.java))
            finish()
        }, 2800)
    }
}