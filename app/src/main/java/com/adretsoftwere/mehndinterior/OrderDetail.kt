package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.OrderItemAdapter
import com.adretsoftwere.mehndinterior.adapters.orderItemFunctions
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityOrderDetailBinding
import com.adretsoftwere.mehndinterior.models.*
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderDetail : AppCompatActivity(),orderItemFunctions {
    lateinit var order: Order
    lateinit var user: User
    lateinit var orderItems: ArrayList<OrderItem>
    lateinit var adapter: OrderItemAdapter
    lateinit var binding:ActivityOrderDetailBinding
    var command:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        order= Gson().fromJson(intent.getStringExtra("order"), Order::class.java)
        command=intent.getStringExtra("command")

        if(command==Constants.COMISSION)
            adapter=OrderItemAdapter(this,Constants.COMISSION)
        else
            adapter= OrderItemAdapter(this)
        binding.recylerView.layoutManager=LinearLayoutManager(this)
        binding.recylerView.adapter=adapter
        loadData()
        binding.confirmbtn.setOnClickListener { confirm() }

    }
    fun loadData(){
        binding.status.text=order.status
        binding.date.text=order.date
        binding.orderid.text=order.order_id
        binding.price.text=order.price
        binding.invoicebtn.setOnClickListener { invoice() }
        binding.confirmbtn.setOnClickListener { confirm() }
        binding.cancelbtn.setOnClickListener { cancel() }

        if(order.status!=Constants.CONFIRMED && MySharedStorage.getUserType()==Constants.MANUFACTURER)
            binding.confirmbtn.visibility=View.VISIBLE
        if(order.status==Constants.CONFIRMED) {
            binding.invoicebtn.visibility = View.VISIBLE
            val user_id=RequestBody.create(MediaType.parse("text/plain"),order.user_id)
            RetrofitClient.getApiHolder().getUserById(user_id).enqueue(object:Callback<RetrofitUser>{
                override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                    Log.d("TAG","getUserbyId:"+response.code())
                    if(response.code()==Constants.code_OK){
                        user=response.body()!!.data[0]
                    }
                }

                override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                    Log.d("TAG","getUserbyId:"+t.localizedMessage)
                }
            })
        }
        if(order.status!=Constants.DELIVERED)
            binding.cancelbtn.visibility=View.VISIBLE
        val order_id=RequestBody.create(MediaType.parse("text/plain"), order.order_id)
        RetrofitClient.getApiHolder().getOrderItems(order_id).enqueue(object:Callback<RetrofitOrderItem>{
            override fun onResponse(call: Call<RetrofitOrderItem>, response: Response<RetrofitOrderItem>) {
                if(response.code()==Constants.code_OK){
                    orderItems=response.body()!!.data
                    adapter.update(orderItems)
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
        Toast.makeText(applicationContext,"please hold on for a moment. Don't press back button",Toast.LENGTH_LONG).show()
         val invoiceData=InvoiceData()
         invoiceData.order=order
         invoiceData.user=user
         invoiceData.items=orderItems
         InvoiceGenerator(this,invoiceData)
    }
    fun confirm(){
        val order_id=RequestBody.create(MediaType.parse("text/plain"),order.order_id)
        val status=RequestBody.create(MediaType.parse("text/plain"),Constants.CONFIRMED)

        RetrofitClient.getApiHolder().updateOrderStatus(order_id,status).enqueue(object:Callback<RetrofitResponse>{
            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                if(response.code()==Constants.code_OK){
                    binding.confirmbtn.visibility= View.GONE
                    Toast.makeText(applicationContext,"Confirmed!",Toast.LENGTH_SHORT).show()
                }
                Log.d("TAG","updateOrderStatus:"+response.code())
            }

            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG","updateOrderStatus:"+t.localizedMessage)
            }
        })
    }
    fun cancel(){

    }
}