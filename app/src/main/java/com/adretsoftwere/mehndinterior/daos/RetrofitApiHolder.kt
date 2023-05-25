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
    @POST("item_by_parent.php")
    fun getItemsByParent(@Part("parent") parent:RequestBody): Call<RetrofitItem>






    @Multipart
    @POST("image_upload.php")
    fun imageUpload(@Part image:MultipartBody.Part,@Part("id") id:RequestBody):Call<RetrofitResponse>

    @Multipart
    @POST("item_image_download.php")
    fun itemImageDownload(@Part("item_id") id:RequestBody):Call<RetrofitImage>

    @GET("banner_image_download.php")
    fun bannerImageDownload():Call<RetrofitImage>









    @Multipart
    @POST("get_discount.php")
    fun getDiscountByUser(@Part("user_id") user_id:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>


//    @Multipart
//    @POST("discount_by_user_type.php")
//    fun getDiscountByUserType(@Part("user_type") user_type:RequestBody,@Part("item_id") item_id:RequestBody): Call<RetrofitDiscount>

    @POST("discount.php")
    fun setDiscount(@Body discount: Discount): Call<RetrofitResponse>




    @POST("user.php")
    fun setUser(@Body user:User): Call<RetrofitResponse>
    @POST("update_user.php")
    fun updateUser(@Body user:User): Call<RetrofitResponse>

    @Multipart
    @POST("update_user_password.php")
    fun updateUserPassword(@Part("user_id") user_id: RequestBody,@Part("password") password:RequestBody): Call<RetrofitResponse>

    @GET("user.php")
    fun getUser(): Call<RetrofitUser>
    @Multipart
    @POST("search_user_by_parent.php")
    fun getUserByParent(@Part("parent") parent:RequestBody): Call<RetrofitUser>

    @Multipart
    @POST("search_user_by_mobile.php")
    fun getUserByMobile(@Part("mobile") mobile: RequestBody): Call<RetrofitUser>

    @Multipart
    @POST("search_user_by_id.php")
    fun getUserById(@Part("user_id") user_id: RequestBody): Call<RetrofitUser>





    @POST("cart.php")
    fun setCart(@Body cart:CartItem): Call<RetrofitResponse>
    @Multipart
    @POST("get_cart.php")
    fun getCart(@Part("user_id") user_id:RequestBody): Call<RetrofitCartItem>

    @Multipart
    @POST("update_cart_quantity.php")
    fun updateCartQuantity(@Part("item_id") item_id: RequestBody, @Part("user_id") user_id: RequestBody, @Part("quantity")  quantity:RequestBody, @Part("total_price") total_price:RequestBody): Call<RetrofitResponse>

    @Multipart
    @POST("update_cart_delete.php")
    fun deleteCart(@Part("item_id") item_id: RequestBody,@Part("user_id") user_id: RequestBody): Call<RetrofitResponse>

    @Multipart
    @POST("update_cart_delete_whole.php")
    fun deleteWholeCart(@Part("user_id") user_id: RequestBody): Call<RetrofitResponse>








    @POST("order.php")
    fun setOrder(@Body order:Order):Call<RetrofitResponse>
    @Multipart
    @POST("get_order.php")
    fun getOrderByUser(@Part("user_id") user_id: RequestBody):Call<RetrofitOrder>
    @GET("order.php")
    fun getOrder():Call<RetrofitOrder>

    @Multipart
    @POST("update_order.php")
    fun updateOrderStatus(@Part("order_id") order_id:RequestBody,@Part("status") status:RequestBody):Call<RetrofitResponse>





    @POST("order_item.php")
    fun setOrderItems(@Body orderItem:OrderItem): Call<RetrofitResponse>

    @Multipart
    @POST("get_order_item.php")
    fun getOrderItems(@Part("order_id") order_id:RequestBody): Call<RetrofitOrderItem>


}