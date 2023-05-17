package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adretsoftwere.mehndinterior.adapters.CartItemAdapter
import com.adretsoftwere.mehndinterior.adapters.cartItemFunctions
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityCartBinding
import com.adretsoftwere.mehndinterior.models.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Cart : AppCompatActivity(), cartItemFunctions {
    lateinit var binding:ActivityCartBinding
    lateinit var adapter: CartItemAdapter
    lateinit var items:ArrayList<CartItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= CartItemAdapter(this,layoutInflater,this)
        loadData()
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.continueBtn.setOnClickListener { placeOrder() }
    }

    override fun deleteItem(userId: String, itemId: String, position: Int) {
        deleteCart(userId,itemId)
        items.removeAt(position)
        adapter.update(items)
    }

    override fun increaseQuantity(userId: String, itemId: String, position: Int,quant:String) {
        val quantity=RequestBody.create(MediaType.parse("text/plain"),quant)
        val type=RequestBody.create(MediaType.parse("text/plain"),ApiConstants.INCREASE)
        val user_id=RequestBody.create(MediaType.parse("text/plain"),userId)
        val item_id=RequestBody.create(MediaType.parse("text/plain"),itemId)
        RetrofitClient.getApiHolder().increaseCart(item_id,user_id,quantity,type).enqueue(object : Callback<RetrofitResponse>{
            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                Log.d("TAG","increaseCart:"+response.code().toString())
                items[position].quantity=quant
            }
            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG","increaseCart:"+t.localizedMessage)
            }
        })
    }

    override fun decreaseQuantity(userId: String, itemId: String,position: Int,quant:String ) {
        val quantity=RequestBody.create(MediaType.parse("text/plain"),quant)
        val type=RequestBody.create(MediaType.parse("text/plain"),ApiConstants.DECREASE)
        val user_id=RequestBody.create(MediaType.parse("text/plain"),userId)
        val item_id=RequestBody.create(MediaType.parse("text/plain"),itemId)
        RetrofitClient.getApiHolder().increaseCart(item_id,user_id,quantity,type)
        items[position].quantity=quant
    }

    fun loadData(){
        val user_id= RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())

        RetrofitClient.getApiHolder().getCart(user_id).enqueue(object: retrofit2.Callback<RetrofitCartItem>{
            override fun onResponse(call: Call<RetrofitCartItem>, response: Response<RetrofitCartItem>) {
                if(response.code()==ApiConstants.code_OK){
                    binding.empty.visibility= View.GONE
                    items=response.body()!!.data
                    adapter.update(items)

                }else if(response.code()==ApiConstants.code_NO_CONTENT){
                    binding.empty.visibility= View.VISIBLE
                }else{
                    Log.d("TAG","getCart:"+response.code().toString())
                }
            }

            override fun onFailure(call: Call<RetrofitCartItem>, t: Throwable) {
                Log.d("TAG","getCart:"+t.localizedMessage)
            }

        })
    }

    fun deleteCart(userId: String, itemId: String) {
        val user_id=RequestBody.create(MediaType.parse("text/plain"),userId)
        val item_id=RequestBody.create(MediaType.parse("text/plain"),itemId)
        RetrofitClient.getApiHolder().deleteCart(item_id, user_id).enqueue(object : Callback<RetrofitResponse>{
            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                    if(response.code()==ApiConstants.code_OK) {
                        Log.d("TAG","deleteCart"+"successfull")
                    }
                }

            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG","deleteCart:"+t.localizedMessage)
            }
        })

    }
    fun placeOrder(){
        val order= Order()
        var price=0
        order.order_id=System.currentTimeMillis().toString()
        val calendar: Calendar = Calendar.getInstance() // Returns instance with current date and time set
        val formatter = SimpleDateFormat("yyyy-dd-MM")
        order.date=formatter.format(calendar.time)
        val order_id=RequestBody.create(MediaType.parse("text/plain"),order.order_id)
        for(item in items){
            order.name=item.name+","
            price+=item.price.toInt()
            val item_id=RequestBody.create(MediaType.parse("text/plain"),item.item_id)
            RetrofitClient.getApiHolder().setItemToOrder(order_id,item_id).enqueue(object: Callback<RetrofitResponse>{
                override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                    Log.d("TAG","setItemToOrder:"+response.code().toString())
                    if(item==items.last()) {
                        order.price=price.toString()
                        RetrofitClient.getApiHolder().setOrder(order).enqueue(object :Callback<RetrofitResponse>{
                            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                                if(response.code()==ApiConstants.code_OK){
                                    Toast.makeText(applicationContext,"done!",Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(applicationContext,Orders::class.java))
                                    val user_id=RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())
                                    RetrofitClient.getApiHolder().deleteWholeCart(user_id).enqueue(object:Callback<RetrofitResponse>{
                                        override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                                            Log.d("TAG","deleteWholeCart:"+response.code().toString())
                                        }
                                        override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                                            Log.d("TAG","deleteWholeCart:"+t.localizedMessage)
                                        }
                                    })
                                    for(cartItem in items){
                                        val orderItem=OrderItem();orderItem.fromCartItem(cartItem);
                                        orderItem.order_id=order.order_id
                                        RetrofitClient.getApiHolder().setOrderItems(orderItem).enqueue(object:Callback<RetrofitResponse>{
                                            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                                                Log.d("TAG","setorderITems:"+response.code())
                                                if(cartItem==items.last())
                                                    finish()
                                            }
                                            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                                                Log.d("TAG","setOrderItems:"+t.localizedMessage)
                                            }
                                        })
                                    }
                                }else{
                                    Log.d("TAG","setOrder:"+response.code().toString())
                                }
                            }
                            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                                Log.d("TAG","SetOrder:"+t.localizedMessage)
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                    Log.d("TAG","setItemtoOrder:"+t.localizedMessage)
                }
            })
        }
    }
}