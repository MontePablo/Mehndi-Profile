package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitImage
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApiHolder {

    @GET("mehndi_profile/item.php")
    fun getItems(): Call<RetrofitItem>

    @POST("mehndi_profile/item.php")
    fun sendItem(@Body item: Item): Call<RetrofitResponse>

    @Multipart
    @POST("mehndi_profile/image_upload.php")
    fun imageUpload(@Part image:MultipartBody.Part,@Part("id") id:RequestBody):Call<RetrofitResponse>

//    @GET("mehndi_profile/search_user.php")
//    fun getItems(): Call<RetrofitItem>

    @Multipart
    @POST("mehndi_profile/image_download.php")
    fun imageDownload(@Part("id") id:RequestBody):Call<RetrofitImage>
}