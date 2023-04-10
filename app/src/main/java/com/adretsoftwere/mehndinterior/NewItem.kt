package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.adretsoftwere.mehndinterior.databinding.ActivityNewItemBinding
import com.adretsoftwere.mehndinterior.databinding.CustomviewImageBinding

class NewItem : AppCompatActivity() {
    lateinit var binding: ActivityNewItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addImage.setOnClickListener(View.OnClickListener {
            addImage()
        })
    }
    fun addImage(){
        val imageBinding=CustomviewImageBinding.inflate(layoutInflater)

        binding.imageLayout.addView(imageBinding.root)
    }
}