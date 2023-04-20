package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApiHolder {

    @GET("mehndi_profile/item.php")
    fun getItems(): Call<RetrofitItem>

    @POST("mehndi_profile/item.php")
    fun sendItem(@Body item: Item): Call<Item>

    @POST("mehndi_profile/photo_upload.php")
    fun photoUpload(@Part image:MultipartBody):Call<String>
}