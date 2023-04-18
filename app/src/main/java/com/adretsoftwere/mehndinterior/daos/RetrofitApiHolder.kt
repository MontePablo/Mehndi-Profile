package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.RetrofitItem
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApiHolder {

    @GET("mehndi_profile/item.php")
    fun getItems(): Call<RetrofitItem>
}