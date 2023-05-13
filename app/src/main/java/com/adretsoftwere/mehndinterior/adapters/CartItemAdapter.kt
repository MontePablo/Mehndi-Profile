package com.adretsoftwere.mehndinterior.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.models.CartItem
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class CartItemAdapter(
    listener: cartItemFunctions,
    layoutInflater: LayoutInflater,
    applicationContext: Context
): RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    var listener:cartItemFunctions
    var items= arrayListOf<CartItem>()
    lateinit var layoutInflater: LayoutInflater
    lateinit var context: Context
    init {
        this.layoutInflater=layoutInflater
        this.context=applicationContext
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.customview_cart_item,parent,false)
        val vh=ViewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun update(items:ArrayList<CartItem>){
        this.items=items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.code.text=items[position].code
        holder.name.text=items[position].name
        holder.price.text=items[position].price
        holder.increase.setOnClickListener(View.OnClickListener {
            holder.quantity.text=(holder.quantity.text.toString().toInt()+1).toString()
            listener.increaseQuantity(items[position].user_id,items[position].item_id)
        })
        holder.decrease.setOnClickListener(View.OnClickListener {
            if(holder.quantity.text.toString().toInt()>1) {
                holder.quantity.text = (holder.quantity.text.toString().toInt() - 1).toString()
                listener.decreaseQuantity(MySharedStorage.getUserId(), items[position].item_id)
            }else{
                items.removeAt(position)

                notifyDataSetChanged()
                listener.deleteItem(items[position].user_id,items[position].item_id)
            }
        })
        holder.delete.setOnClickListener(View.OnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
            listener.deleteItem(MySharedStorage.getUserId(),items[position].item_id)
        })
        val url=ApiConstants.apiUrl+ApiConstants.imageUrl+items[position].image_url
        Log.d("TAG",url)
        Glide.with(holder.itemView.context).load(url).into(holder.image)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.cart_image)
        var price=view.findViewById<TextView>(R.id.cart_price)
        var name=view.findViewById<TextView>(R.id.cart_name)
        var code=view.findViewById<TextView>(R.id.cart_code)
        var root=view.findViewById<CardView>(R.id.cart_root)
        var decrease=view.findViewById<ImageButton>(R.id.cart_decrease)
        var increase=view.findViewById<ImageButton>(R.id.cart_increase)
        var delete=view.findViewById<ImageButton>(R.id.cart_remove)
        val quantity=view.findViewById<TextView>(R.id.cart_quantity)

    }
}
interface cartItemFunctions{
    fun deleteItem(userId: String, itemId: String)
    fun increaseQuantity(userId: String, itemId: String)
    fun decreaseQuantity(userId: String, itemId: String)
}


//val item_id= RequestBody.create(MediaType.parse("text/plain"),items[position].item_id)
//val user_id= RequestBody.create(MediaType.parse("text/plain"),items[position].user_id)
//RetrofitClient.getApiHolder().deleteCart(item_id,user_id).enqueue(object : Callback<RetrofitResponse>{
//    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
//        if(response.code()==ApiConstants.code_OK){
//            Toast.makeText(context,"failed!",Toast.LENGTH_SHORT).show()
//        }else{
//            Log.d("TAG",response.code().toString())
//        }
//    }
//    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
//        Log.d("TAG",t.localizedMessage)
//    }
//})
