package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityItemsBinding
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Items : AppCompatActivity(),itemFunctions {
    lateinit var binding:ActivityItemsBinding
    lateinit var adapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        adapter= ItemAdapter(this,layoutInflater,applicationContext)


        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)

        RetrofitClient.getApiHolder().getItems().enqueue(object:Callback<RetrofitItem>{
            override fun onResponse(call: Call<RetrofitItem>, response: Response<RetrofitItem>) {
                if(response.code()==Constants.code_OK){
                    Log.d("TAG",response.code().toString())
                    adapter.update(response.body()!!.data)
                }else {
                    Log.d("TAG 2",response.code().toString() + response.message().toString())
                }
            }

            override fun onFailure(call: Call<RetrofitItem>, t: Throwable) {
                Log.d("TAG","on failure retro : ${t.localizedMessage}")

            }

        })
    }

    override fun ItemClickFunc(item: Item, view: View) {
        val parent= RequestBody.create(MediaType.parse("text/plain"),item.item_id)
         if(item.price.isNullOrBlank()) {
             RetrofitClient.getApiHolder().getItemsByParent(parent)
                 .enqueue(object : Callback<RetrofitItem> {
                     override fun onResponse(
                         call: Call<RetrofitItem>,
                         response: Response<RetrofitItem>
                     ) {
                         if (response.code() == Constants.code_OK)
                             adapter.update(response.body()!!.data)
                         else {
                             Log.d("TAG", response.code().toString())
                         }
                     }

                     override fun onFailure(call: Call<RetrofitItem>, t: Throwable) {
                         Log.d("TAG", t.localizedMessage)
                     }
                 })
         }else{
             val gson = Gson()
             val intent = Intent(applicationContext, ItemDetail::class.java)
             intent.putExtra("item", gson.toJson(item))
             startActivity(intent)
         }
    }
}