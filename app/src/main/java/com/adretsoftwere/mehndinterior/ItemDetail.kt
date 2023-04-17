package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adretsoftwere.mehndinterior.databinding.ActivityItemDetailBinding

class ItemDetail : AppCompatActivity() {
    lateinit var binding:ActivityItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}