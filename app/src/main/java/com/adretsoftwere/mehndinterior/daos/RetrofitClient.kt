package com.adretsoftwere.mehndinterior.daos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitApiHolder:RetrofitApiHolder
    init {
        val retrofit= Retrofit.Builder().baseUrl(ApiConstants.apiUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        retrofitApiHolder=retrofit.create(RetrofitApiHolder::class.java)
    }
    fun getApiHolder(): RetrofitApiHolder {
        return retrofitApiHolder
    }

}