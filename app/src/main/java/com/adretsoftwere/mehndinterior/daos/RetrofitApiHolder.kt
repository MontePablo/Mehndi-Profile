package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitApiHolder {

    @GET("mehndi_profile/item.php")
    fun getItems(): Call<RetrofitItem>

    @POST("mehndi_profile/item.php")
    fun sendItem(@Body item: Item): Call<Item>
}