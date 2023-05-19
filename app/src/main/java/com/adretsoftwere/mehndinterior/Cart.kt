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
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityCartBinding
import com.adretsoftwere.mehndinterior.models.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Cart : AppCompatActivity(), cartItemFunctions {
    lateinit var binding:ActivityCartBinding
    lateinit var adapter: CartItemAdapter
    lateinit var items:ArrayList<CartItem>
    var price=0F
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

    override fun deleteItem(position: Int) {
        deleteCart(items[position].user_id,items[position].item_id)
        items.removeAt(position)
        updateData()
    }

    override fun increaseQuantity(holder:CartItemAdapter.ViewHolder,position: Int) {
        items[position].quantity={items[position].quantity.toFloat()+1}.toString()
        val quantity=RequestBody.create(MediaType.parse("text/plain"),items[position].quantity)
        val user_id=RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())
        val item_id=RequestBody.create(MediaType.parse("text/plain"),items[position].item_id)
        items[position].total_price={items[position].quantity.toFloat()*items[position].price.toFloat()}.toString()
        val total_price=RequestBody.create(MediaType.parse("text/plain"),items[position].total_price)
        price+=items[position].price.toFloat()
        updateData()
        RetrofitClient.getApiHolder().increaseCart(item_id,user_id,quantity,total_price).enqueue(object : Callback<RetrofitResponse>{
            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                Log.d("TAG","increaseCart:"+response.code().toString())
            }
            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG","increaseCart:"+t.localizedMessage)
            }
        })
    }
    fun updateData(){
        binding.amount.text=price.toString()
        adapter.update(items)
    }

    override fun decreaseQuantity(holder:CartItemAdapter.ViewHolder,position: Int) {
        if(items[position].quantity.toInt()!=1) {
            items[position].quantity={items[position].quantity.toFloat()-1}.toString()
            val quantity =
                RequestBody.create(MediaType.parse("text/plain"), items[position].quantity)
            val user_id =
                RequestBody.create(MediaType.parse("text/plain"), MySharedStorage.getUserId())
            val item_id = RequestBody.create(MediaType.parse("text/plain"), items[position].item_id)
            items[position].total_price =
                { items[position].quantity.toFloat() * items[position].price.toFloat() }.toString()
            val total_price =
                RequestBody.create(MediaType.parse("text/plain"), items[position].total_price)
            price -= items[position].price.toFloat()
            updateData()
            RetrofitClient.getApiHolder()
                .decreaseCart(item_id, user_id, quantity, total_price)
                .enqueue(object : Callback<RetrofitResponse> {
                    override fun onResponse(
                        call: Call<RetrofitResponse>,
                        response: Response<RetrofitResponse>
                    ) {
                        Log.d("TAG", "increaseCart:" + response.code().toString())
                    }

                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG", "increaseCart:" + t.localizedMessage)
                    }
                })
        }else{
            deleteItem(position)
        }
    }

    fun loadData(){
        val user_id= RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())

        RetrofitClient.getApiHolder().getCart(user_id).enqueue(object: retrofit2.Callback<RetrofitCartItem>{
            override fun onResponse(call: Call<RetrofitCartItem>, response: Response<RetrofitCartItem>) {
                if(response.code()==Constants.code_OK){
                    binding.empty.visibility= View.GONE
                    items=response.body()!!.data

                    for(item in items){
                        price+=item.total_price.toFloat()
                    }
                    updateData()

                }else if(response.code()==Constants.code_NO_CONTENT){
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
                    if(response.code()==Constants.code_OK) {
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
        order.order_id=System.currentTimeMillis().toString()
        val calendar: Calendar = Calendar.getInstance() // Returns instance with current date and time set
        val formatter = SimpleDateFormat("yyyy-dd-MM")
        order.date=formatter.format(calendar.time)
        for(item in items) {
            order.name = item.name + ","
            price += item.total_price.toFloat()
            val user_id = RequestBody.create(MediaType.parse("text/plain"), MySharedStorage.getUserId())
            RetrofitClient.getApiHolder().deleteWholeCart(user_id)
                .enqueue(object : Callback<RetrofitResponse> {
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        Log.d("TAG", "deleteWholeCart:" + response.code().toString())
                    }

                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG", "deleteWholeCart:" + t.localizedMessage)
                    }
                })
            val orderItem = OrderItem();orderItem.fromCartItem(item);
            orderItem.order_id = order.order_id
            RetrofitClient.getApiHolder().setOrderItems(orderItem)
                .enqueue(object : Callback<RetrofitResponse> {
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        Log.d("TAG", "setorderITems:" + response.code())
                    }
                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG", "setOrderItems:" + t.localizedMessage)
                    }
                })
            if(item==items.last()){
                RetrofitClient.getApiHolder().setOrder(order).enqueue(object: Callback<RetrofitResponse>{
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        Toast.makeText(applicationContext, "done!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, Orders::class.java))
                        Log.d("TAG", "setOrder:" + response.code())
                        finish()
                    }
                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG", "setOrder:" + t.localizedMessage)
                    }
                })
            }
        }
    }
}