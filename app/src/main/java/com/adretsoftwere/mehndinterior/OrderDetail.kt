package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.OrderItemAdapter
import com.adretsoftwere.mehndinterior.adapters.orderItemFunctions
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityOrderDetailBinding
import com.adretsoftwere.mehndinterior.models.Order
import com.adretsoftwere.mehndinterior.models.RetrofitOrderItem
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetail : AppCompatActivity(),orderItemFunctions {
    lateinit var order: Order
    lateinit var adapter:OrderItemAdapter
    lateinit var binding:ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= OrderItemAdapter(this)
        order= Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
        binding.recylerView.layoutManager=LinearLayoutManager(this)
        binding.recylerView.adapter=adapter
        loadData()


    }
    fun loadData(){

        binding.status.text=order.status
        binding.date.text=order.date
        binding.orderid.text=order.order_id
        binding.price.text=order.price
        binding.invoicebtn.setOnClickListener { invoice() }
        binding.confirmbtn.setOnClickListener { confirm() }
        binding.cancelbtn.setOnClickListener { cancel() }
        val order_id=RequestBody.create(MediaType.parse("text/plain"), order.order_id)
        RetrofitClient.getApiHolder().getOrderItems(order_id).enqueue(object:Callback<RetrofitOrderItem>{
            override fun onResponse(call: Call<RetrofitOrderItem>, response: Response<RetrofitOrderItem>) {
                if(response.code()==Constants.code_OK){
                    adapter.update(response.body()!!.data)
                }else
                    Log.d("TAG","getOrderITem:"+response.code())
            }

            override fun onFailure(call: Call<RetrofitOrderItem>, t: Throwable) {
                Log.d("TAG","getOrderItem:"+t.localizedMessage)
            }
        })

    }

    override fun itemClick(itemId: String) {
    }

     fun invoice() {

    }
    fun confirm(){
        //send comission
    }
    fun cancel(){

    }
}