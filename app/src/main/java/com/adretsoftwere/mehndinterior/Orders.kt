package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.OrderAdapter
import com.adretsoftwere.mehndinterior.adapters.orderFunctions
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityOrdersBinding
import com.adretsoftwere.mehndinterior.models.RetrofitOrder
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Orders : AppCompatActivity(),orderFunctions {
    lateinit var binding:ActivityOrdersBinding
    lateinit var adapter:OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter=OrderAdapter(this,layoutInflater,this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter

        val user_id= RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())
        RetrofitClient.getApiHolder().getOrder(user_id).enqueue(object:Callback<RetrofitOrder>{
            override fun onResponse(call: Call<RetrofitOrder>, response: Response<RetrofitOrder>) {
                if(response.code()==ApiConstants.code_OK){
                    Log.d("TAG","getOrder:"+response.code().toString())
                    adapter.update(response.body()!!.data)
                }else if(response.code()==ApiConstants.code_NO_CONTENT){
                    Log.d("TAG","getOrder:"+response.code().toString())
                    binding.empty.visibility= View.VISIBLE
                }
            }

            override fun onFailure(call: Call<RetrofitOrder>, t: Throwable) {
                Log.d("TAG","getOrder:"+t.localizedMessage)
            }
        })
    }

    override fun itemClick(order_id: String) {

    }
}