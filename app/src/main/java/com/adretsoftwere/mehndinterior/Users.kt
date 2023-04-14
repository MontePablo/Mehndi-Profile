package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.UserAdapter
import com.adretsoftwere.mehndinterior.adapters.userFunctions
import com.adretsoftwere.mehndinterior.databinding.ActivityUsersBinding
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
//        adapter.update()
    }

}