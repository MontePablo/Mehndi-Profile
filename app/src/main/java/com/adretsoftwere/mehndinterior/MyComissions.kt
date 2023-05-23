package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.*
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityMyComissionsBinding
import com.adretsoftwere.mehndinterior.models.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyComissions : AppCompatActivity(),userFunctions,orderItemFunctions,orderFunctions {
    lateinit var binding: ActivityMyComissionsBinding
    lateinit var userAdapter:UserAdapter
    lateinit var orderAdapter:OrderAdapter
    lateinit var orderItemAdapter: OrderItemAdapter
    lateinit var users:ArrayList<User>
    lateinit var orders:ArrayList<Order>
    lateinit var orderItems:ArrayList<OrderItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMyComissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        userAdapter=UserAdapter(this)
        orderAdapter= OrderAdapter(this,layoutInflater,this)
        orderItemAdapter= OrderItemAdapter(this)
        binding.recyclerView.adapter=userAdapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        val parent= RequestBody.create(MediaType.parse("text/plain"), MySharedStorage.getUserId())
        RetrofitClient.getApiHolder().getUserByParent(parent).enqueue(object:Callback<RetrofitUser>{
            override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                if(response.code()==Constants.code_OK){
                    users=response.body()!!.data
                    userAdapter.update(users)
                }else if(response.code()==Constants.code_NO_CONTENT){
                    Toast.makeText(applicationContext,"no user present under you",Toast.LENGTH_SHORT).show()
                    Log.d("TAG","getUserbyParent:"+response.code())
                }else{
                    Log.d("TAG","getUserbyParent:"+response.code())
                }
            }

            override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                Log.d("TAG","getUserbyParent:"+t.getLocalizedMessage())
            }
        })

    }

    override fun itemClick(user: User) {
        val user_id=RequestBody.create(MediaType.parse("text/plain"),user.user_id)
        RetrofitClient.getApiHolder().getOrderByUser(user_id).enqueue(object:Callback<RetrofitOrder>{
            override fun onResponse(call: Call<RetrofitOrder>, response: Response<RetrofitOrder>) {
                if(response.code()==Constants.code_OK){
                    orders=response.body()!!.data
                    binding.recyclerView.adapter=orderAdapter
                    orderAdapter.update(orders)
                }else if(response.code()==Constants.code_NO_CONTENT){
                    Toast.makeText(applicationContext,"no orders found!",Toast.LENGTH_SHORT).show()
                }else{
                    Log.d("TAG","getOrderByUser:"+response.code())
                }
            }

            override fun onFailure(call: Call<RetrofitOrder>, t: Throwable) {
                Log.d("TAG","getOrderByUser"+t.localizedMessage)
            }
        })
    }

    override fun itemClick(order: Order) {
        val order_id=RequestBody.create(MediaType.parse("text/plain"),order.order_id)
        RetrofitClient.getApiHolder().getOrderItems(order_id).enqueue(object:Callback<RetrofitOrderItem>{
            override fun onResponse(call: Call<RetrofitOrderItem>, response: Response<RetrofitOrderItem>) {
                if(response.code()==Constants.code_OK){
                    orderItems=response.body()!!.data
                    binding.recyclerView.adapter=orderItemAdapter
                    orderItemAdapter.update(orderItems)
                }else if(response.code()==Constants.code_NO_CONTENT){
                    Toast.makeText(applicationContext,"no items found!",Toast.LENGTH_SHORT).show()
                }else{
                    Log.d("TAG","getOrderItems:"+response.code())
                }
            }
            override fun onFailure(call: Call<RetrofitOrderItem>, t: Throwable) {
                Log.d("TAG","getOrderItems:"+t.localizedMessage)
            }
        })
    }

    override fun itemClick(itemId: String) {

    }
}