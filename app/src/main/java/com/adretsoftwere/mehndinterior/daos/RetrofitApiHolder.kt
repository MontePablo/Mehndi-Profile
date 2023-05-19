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
    @GET("item_by_parent.php")
    fun getItemsByParent(@Part("parent") parent:RequestBody): Call<RetrofitItem>






    @Multipart
    @POST("image_upload.php")
    fun imageUpload(@Part image:MultipartBody.Part,@Part("id") id:RequestBody):Call<RetrofitResponse>

    @Multipart
    @GET("item_image_download.php")
    fun itemImageDownload(@Part("id") id:RequestBody):Call<RetrofitImage>

    @GET("banner_image_download.php")
    fun bannerImageDownload():Call<RetrofitImage>









    @Multipart
    @GET("discount.php")
    fun getDiscountByUser(@Part("user_id") user_id:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>


//    @Multipart
//    @POST("discount_by_user_type.php")
//    fun getDiscountByUserType(@Part("user_type") user_type:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>

    @POST("discount.php")
    fun setDiscount(@Body discount: Discount): Call<RetrofitResponse>




    @POST("user.php")
    fun setUser(@Body user:User): Call<RetrofitResponse>

    @GET("user.php")
    fun getUser(): Call<RetrofitUser>
    @Multipart
    @GET("search_user_by_parent.php")
    fun getUserByParent(@Part("parent") parent:RequestBody): Call<RetrofitUser>
    @POST("search_user.php")
    fun searchUser(@Body user: User): Call<RetrofitUser>







    @POST("cart.php")
    fun setCart(@Body cart:CartItem): Call<RetrofitResponse>
    @Multipart
    @GET("cart.php")
    fun getCart(@Part("user_id") user_id:RequestBody): Call<RetrofitCartItem>

    @Multipart
    @POST("update_cart_quantity.php")
    fun increaseCart(@Part("item_id") item_id: RequestBody,@Part("user_id") user_id: RequestBody,@Part("quantity")  quantity:RequestBody,@Part("total_price") total_price:RequestBody): Call<RetrofitResponse>
    @Multipart
    @POST("update_cart_quantity.php")
    fun decreaseCart(@Part("item_id") item_id: RequestBody,@Part("user_id") user_id: RequestBody,@Part("quantity") quantity:RequestBody,@Part("total_price") total_price:RequestBody): Call<RetrofitResponse>
    @Multipart
    @POST("update_cart_delete.php")
    fun deleteCart(@Part("item_id") item_id: RequestBody,@Part("user_id") user_id: RequestBody): Call<RetrofitResponse>

    @Multipart
    @POST("update_cart_delete_whole.php")
    fun deleteWholeCart(@Part("user_id") user_id: RequestBody): Call<RetrofitResponse>








    @POST("order.php")
    fun setOrder(@Body order:Order):Call<RetrofitResponse>
    @Multipart
    @GET("order.php")
    fun getOrder(@Part("user_id") user_id: RequestBody):Call<RetrofitOrder>






    @POST("order_item.php")
    fun setOrderItems(@Body orderItem:OrderItem): Call<RetrofitResponse>

    @Multipart
    @GET("cart.php")
    fun getOrderItems(@Part("order_id") order_id:RequestBody): Call<RetrofitOrderItem>


}