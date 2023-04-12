package com.adretsoftwere.mehndinterior

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.adretsoftwere.mehndinterior.databinding.AdminCodeFragviewBinding

object AdminCodeDialog {
    lateinit var activity: AppCompatActivity
    lateinit var layoutInflater:LayoutInflater

    fun process(activity: Login,layoutInflater: LayoutInflater){
        this.activity=activity
        this.layoutInflater=layoutInflater

        var dialogBuilder= AlertDialog.Builder(activity)
        var viewBinding= AdminCodeFragviewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
        viewBinding.btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, NewProfile::class.java)
            ContextCompat.startActivity(activity, intent, null)
        })

    }
}