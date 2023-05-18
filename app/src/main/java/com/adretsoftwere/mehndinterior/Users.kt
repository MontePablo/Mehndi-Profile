package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.UserAdapter
import com.adretsoftwere.mehndinterior.adapters.userFunctions
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityUsersBinding
import com.adretsoftwere.mehndinterior.databinding.UserOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.models.RetrofitUser
import com.adretsoftwere.mehndinterior.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Users : AppCompatActivity(),userFunctions {
    lateinit var binding:ActivityUsersBinding
    lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        adapter= UserAdapter(this)



        RetrofitClient.getApiHolder().getUser().enqueue(object: Callback<RetrofitUser> {
            override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                if(response.code()== Constants.code_OK){
                    adapter.update(response.body()!!.data)
                }else {
                    Log.d("TAG 2",response.code().toString() + response.message().toString())
                }
            }

            override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                Log.d("TAG","on failure retro : ${t.localizedMessage}")

            }

        })
        binding.addUserBtn.setOnClickListener(View.OnClickListener { addUser() })
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
    }

    fun addUser(){
        startActivity(Intent(applicationContext,NewProfile::class.java))
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