package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApiHolder {

    @GET("mehndi_profile/item.php")
    fun getItems(): Call<RetrofitResponse>

    @POST("mehndi_profile/item.php")
    fun sendItem(@Body item: Item): Call<RetrofitResponse>

    @Multipart
    @POST("mehndi_profile/upload.php")
    fun photoUpload(@Part image:MultipartBody.Part):Call<RetrofitResponse>
}