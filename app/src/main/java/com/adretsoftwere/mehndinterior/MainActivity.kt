package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.adretsoftwere.mehndinterior.adapters.SliderAdapter
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityMainBinding
import com.adretsoftwere.mehndinterior.models.RetrofitImage
import com.adretsoftwere.mehndinterior.models.RetrofitUser
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
     lateinit var user_type:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)
        user_type =MySharedStorage.getUserType()
        saveUser()
        Log.d("TAG","gg"+user_type)
        if(user_type==Constants.MANUFACTURER){
            binding.grid.removeView(binding.orders)
            binding.grid.removeView(binding.shop)
            binding.grid.removeView(binding.cart)

        }else{
            binding.grid.removeView(binding.manageItems)
            binding.grid.removeView(binding.manageUsers)
            binding.grid.removeView(binding.newItem)

        }


        binding.shop.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Items::class.java))
        })
        binding.newItem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, NewItem::class.java))
        })
        binding.manageUsers.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,Users::class.java))
        })
        binding.cart.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,Cart::class.java))
        })
        binding.manageOrders.setOnClickListener { startActivity(Intent(applicationContext,OrdersSeller::class.java)) }
        binding.orders.setOnClickListener { startActivity(Intent(applicationContext,Orders::class.java)) }

        binding.logout.setOnClickListener { MySharedStorage.setUserId("");startActivity(Intent(applicationContext,Login::class.java)); finish() }

        var slideAdapter= SliderAdapter()
        binding.sliderView.setSliderAdapter(slideAdapter)
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderView.startAutoCycle()

        RetrofitClient.getApiHolder().bannerImageDownload().enqueue(object :
            Callback<RetrofitImage> {
            override fun onResponse(call: Call<RetrofitImage>, response: Response<RetrofitImage>) {
                if(response.code()== Constants.code_OK)
                    slideAdapter.updateData(response.body()!!.data)
                else{
                    Log.d("TAG",response.code().toString())
                }
            }
            override fun onFailure(call: Call<RetrofitImage>, t: Throwable) {
                Log.d("TAG",t.localizedMessage)
            }
        })
    }
    fun saveUser(){
        val mob= RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())
        RetrofitClient.getApiHolder().searchUserByMobile(mob).enqueue(object : Callback<RetrofitUser> {
            override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                if(response.code()== Constants.code_OK)
                    MySharedStorage.saveUser(response.body()!!.data[0])
                Log.d("TAG",response.code().toString())
            }
            override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                Log.d("TAG",t.localizedMessage)
            }
        })
    }
}