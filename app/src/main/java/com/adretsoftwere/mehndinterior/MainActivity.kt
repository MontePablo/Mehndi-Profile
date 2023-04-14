package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.adretsoftwere.mehndinterior.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)
        binding.shop.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Items::class.java))
        })
        binding.newItem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, NewItem::class.java))
        })
        binding.manageUsers.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,Users::class.java))
        })
    }
}