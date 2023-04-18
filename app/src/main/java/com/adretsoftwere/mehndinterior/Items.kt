package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityItemsBinding
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
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

        adapter= ItemAdapter(this,ItemAdapter.SURF)


        adapter.update(FakeData.dataset)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        Log.d("TAG","startingg")

        RetrofitClient.getApiHolder().getItems().enqueue(object:Callback<RetrofitItem>{
            override fun onResponse(call: Call<RetrofitItem>, response: Response<RetrofitItem>) {
                Log.d("TAG","onresponse: ${response.message()}")
                val s= response.body()?.data
                for(i in s!!){
                    Log.d("TAG",i.name+" "+i.price+"\n")
                    Log.d("TAG","onresponse: ${response.message()}")

                }
            }

            override fun onFailure(call: Call<RetrofitItem>, t: Throwable) {
                Log.d("TAG","on failure retro : ${t.localizedMessage}")

            }

        })
        Log.d("TAG","finsihed")

    }

    override fun ItemClickFunc(item: Item) {
        adapter.update(FakeData.dataset2)
    }

    override fun openDiscountFunc(item: Item) {
    }
}