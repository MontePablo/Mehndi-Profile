package com.adretsoftwere.mehndinterior.daos

import com.adretsoftwere.mehndinterior.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApiHolder {

    @GET("item.php")
    fun getItems(): Call<RetrofitItem>

    @POST("item.php")
    fun sendItem(@Body item: Item): Call<RetrofitResponse>

    @Multipart
    @POST("image_upload.php")
    fun imageUpload(@Part image:MultipartBody.Part,@Part("id") id:RequestBody):Call<RetrofitResponse>

//    @GET("mehndi_profile/search_user.php")
//    fun getItems(): Call<RetrofitItem>

    @Multipart
    @POST("item_image_download.php")
    fun itemImageDownload(@Part("id") id:RequestBody):Call<RetrofitImage>

    @GET("banner_image_download.php")
    fun bannerImageDownload():Call<RetrofitImage>

    @Multipart
    @POST("item_by_parent.php")
    fun getItemsByParent(@Part("parent") parent:RequestBody): Call<RetrofitItem>

    @Multipart
    @POST("discount_by_user.php")
    fun getDiscountByUser(@Part("user_id") user_id:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>


    @Multipart
    @POST("discount_by_user_type.php")
    fun getDiscountByUserType(@Part("user_type") user_type:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>

    @POST("discount.php")
    fun setDiscount(@Body discount: Discount): Call<RetrofitResponse>

    @POST("user.php")
    fun setUser(@Body user:User): Call<RetrofitResponse>

    @GET("user.php")
    fun getUser(): Call<RetrofitUser>

    @POST("cart.php")
    fun setCart(@Body cart:Cart): Call<RetrofitResponse>

    @GET("cart.php")
    fun getCart(@Part("user_id") user_id:RequestBody): Call<RetrofitCart>


}