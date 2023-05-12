package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.adretsoftwere.mehndinterior.adapters.SliderAdapter
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityItemDetailBinding
import com.adretsoftwere.mehndinterior.databinding.ItemSliderBinding
import com.adretsoftwere.mehndinterior.models.Image
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitImage
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

        })

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


}