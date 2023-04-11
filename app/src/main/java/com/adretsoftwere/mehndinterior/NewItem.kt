package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        imageBinding.insert.setOnClickListener(View.OnClickListener {
            photoPick(imageBinding.hashCode())
            Log.d("TAG","imageView: $imageBinding")
        })

        binding.imageLayout.addView(imageBinding.root)
    }
    fun photoPick(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }
}