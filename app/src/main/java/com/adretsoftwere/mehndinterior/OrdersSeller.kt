package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.OrderAdapter
import com.adretsoftwere.mehndinterior.adapters.orderFunctions
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityOrdersBinding
import com.adretsoftwere.mehndinterior.databinding.ActivityOrdersSellerBinding
import com.adretsoftwere.mehndinterior.models.Order
import com.adretsoftwere.mehndinterior.models.RetrofitOrder
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersSeller : AppCompatActivity(),orderFunctions {
    lateinit var binding:ActivityOrdersSellerBinding
    lateinit var adapter:OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrdersSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        adapter= OrderAdapter(this,layoutInflater,this,Constants.COMISSION)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

        val user_id= RequestBody.create(MediaType.parse("text/plain"), MySharedStorage.getUserId())
        RetrofitClient.getApiHolder().getOrder().enqueue(object:
            Callback<RetrofitOrder> {
            override fun onResponse(call: Call<RetrofitOrder>, response: Response<RetrofitOrder>) {
                if(response.code()== Constants.code_OK){
                    Log.d("TAG","getOrder:"+response.code().toString())
                    adapter.update(response.body()!!.data)
                }else if(response.code()== Constants.code_NO_CONTENT){
                    Log.d("TAG","getOrder:"+response.code().toString())
                    binding.empty.visibility= View.VISIBLE
                }
            }

            override fun onFailure(call: Call<RetrofitOrder>, t: Throwable) {
                Log.d("TAG","getOrder:"+t.localizedMessage)
            }
        })
    }

    override fun itemClick(order: Order) {
        val gson = Gson()
        val intent = Intent(applicationContext, OrderDetail::class.java)
        intent.putExtra("order", gson.toJson(order))
        startActivity(intent)
    }
}