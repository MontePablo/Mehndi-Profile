package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.adretsoftwere.mehndinterior.adapters.SliderAdapter
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityItemDetailBinding
import com.adretsoftwere.mehndinterior.databinding.ItemSliderBinding
import com.adretsoftwere.mehndinterior.models.*
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call

class ItemDetail : AppCompatActivity() {
    lateinit var binding:ActivityItemDetailBinding
    lateinit var item:Item
    var price:Float=0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = Gson().fromJson(intent.getStringExtra("item"), Item::class.java)

        binding.about.setText(item.about)
        binding.features.setText(item.features)
        binding.name.setText(item.name)
        binding.price.setText(item.price)

        binding.addtocart.setOnClickListener(View.OnClickListener {
            addtoCart()
        })

        sliderImageLoad(item)

        priceLoad(item)
    }
    fun sliderImageLoad(item:Item){
        val item_id= RequestBody.create(MediaType.parse("text/plain"),item.item_id)
        RetrofitClient.getApiHolder().itemImageDownload(item_id).enqueue(object :
            retrofit2.Callback<RetrofitImage> {
            override fun onResponse(
                call: Call<RetrofitImage>,
                response: retrofit2.Response<RetrofitImage>
            ) {
                if (response.code() == ApiConstants.code_OK) {

                    var slideAdapter = SliderAdapter()
                    binding.sliderView.setSliderAdapter(slideAdapter)
                    binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
                    binding.sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
                    binding.sliderView.startAutoCycle()
                    slideAdapter.updateData(response.body()!!.data)
                    imageLoad(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<RetrofitImage>, t: Throwable) {
                Log.d("TAG", t.localizedMessage)
            }
        })
    }
    fun imageLoad(data: ArrayList<Image>) {
        for(i in data) {
            val viewBinding = ItemSliderBinding.inflate(layoutInflater)
            val url=ApiConstants.apiUrl+ApiConstants.imageUrl+i
            Glide.with(applicationContext).load(url).into(viewBinding.slideImage)
            binding.images.addView(viewBinding.root)
        }
    }
    fun addtoCart(){
        val cart=CartItem()
        cart.item_id=item.item_id
        cart.user_id=MySharedStorage.getUserId()
        if(price!=0F){
            cart.price=price.toString()
        }
        cart.quantity="1"
        RetrofitClient.getApiHolder().setCart(cart).enqueue(object :
            retrofit2.Callback<RetrofitResponse> {
            override fun onResponse(call: Call<RetrofitResponse>, response: retrofit2.Response<RetrofitResponse>) {
                if (response.code() == ApiConstants.code_OK) {
                    Log.d("TAG","cart added")
                    binding.addtocart.text="Added to cart"
                    binding.addtocart.isClickable=false
                    Toast.makeText(applicationContext,"added to cart!",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG", t.localizedMessage)
            }
        })
    }
    fun priceLoad(item:Item){
        val user_id= RequestBody.create(MediaType.parse("text/plain"),MySharedStorage.getUserId())
        val item_id= RequestBody.create(MediaType.parse("text/plain"),item.item_id)

        RetrofitClient.getApiHolder().getDiscountByUser(user_id,item_id).enqueue(object :
            retrofit2.Callback<RetrofitDiscount> {
            override fun onResponse(call: Call<RetrofitDiscount>, response: retrofit2.Response<RetrofitDiscount>) {
                if (response.code() == ApiConstants.code_OK) {
                    loadDiscountToView(response.body()!!.data)
                }else if(response.code()==ApiConstants.code_NO_CONTENT){

                }else{
                    Log.d("TAG",response.code().toString())
                }
            }
            override fun onFailure(call: Call<RetrofitDiscount>, t: Throwable) {
                Log.d("TAG", t.localizedMessage)
            }
        })

    }

    private fun loadDiscountToView(data: ArrayList<Discount>) {
        val disc=data[0]
        if(disc.discount_type==Discount.PRICE){
           price=item.price.toFloat() - disc.amount.toFloat()
        }else{
            price="0.${disc.amount}".toFloat() * item.price.toFloat()
        }
        binding.price.setText(price.toString())
    }


}