package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.UserAdapter
import com.adretsoftwere.mehndinterior.adapters.userFunctions
import com.adretsoftwere.mehndinterior.databinding.ActivityUsersBinding
import com.adretsoftwere.mehndinterior.databinding.DiscountOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.databinding.UserOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.models.User

class Users : AppCompatActivity(),userFunctions {
    lateinit var binding:ActivityUsersBinding
    lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        adapter= UserAdapter(this)

        adapter.update(FakeData.userSet)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
    }

    override fun itemClick(user: User) {
        val dialogBuilder= AlertDialog.Builder(this)
        val viewBinding= UserOptionsFragviewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
        viewBinding.setDiscount.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,DiscountsManage::class.java))
        })
        viewBinding.updatePersonalData.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,NewProfile::class.java))
        })
    }

}