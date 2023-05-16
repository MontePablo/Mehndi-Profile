package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.adretsoftwere.mehndinterior.adapters.SliderAdapter
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityMainBinding
import com.adretsoftwere.mehndinterior.models.RetrofitImage
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
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
        user_type =intent.getStringExtra("user_type")!!
        binding.shop.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, Items::class.java))
        })
        binding.newItem.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, NewItem::class.java))
        })
        binding.manageUsers.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext,Users::class.java))
        })
        binding.manageOrders.setOnClickListener { startActivity(Intent(applicationContext,Orders::class.java)) }
        binding.orders.setOnClickListener { startActivity(Intent(applicationContext,Orders::class.java)) }

        binding.logout.setOnClickListener { MySharedStorage.setUserId("") }

        var slideAdapter= SliderAdapter()
        binding.sliderView.setSliderAdapter(slideAdapter)
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        binding.sliderView.startAutoCycle()

        RetrofitClient.getApiHolder().bannerImageDownload().enqueue(object :
            Callback<RetrofitImage> {
            override fun onResponse(call: Call<RetrofitImage>, response: Response<RetrofitImage>) {
                if(response.code()== ApiConstants.code_OK)
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
}