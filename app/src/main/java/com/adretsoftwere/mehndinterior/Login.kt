package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.adretsoftwere.mehndinterior.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        binding.signIn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        })
        binding.signUp.setOnClickListener(View.OnClickListener {
        })
    }
}