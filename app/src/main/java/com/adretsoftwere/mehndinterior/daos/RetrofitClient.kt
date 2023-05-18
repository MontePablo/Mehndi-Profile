package com.adretsoftwere.mehndinterior.daos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofitApiHolder:RetrofitApiHolder
    init {
//        val gson = GsonBuilder()
//            .setLenient()
//            .create()
//        val retrofit= Retrofit.Builder().baseUrl(Constants.apiUrl)
//            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        val retrofit= Retrofit.Builder().baseUrl(Constants.apiUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()

        retrofitApiHolder=retrofit.create(RetrofitApiHolder::class.java)
    }
    fun getApiHolder(): RetrofitApiHolder {
        return retrofitApiHolder
    }

}