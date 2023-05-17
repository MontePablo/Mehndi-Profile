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
import com.adretsoftwere.mehndinterior.models.CartItem
import com.adretsoftwere.mehndinterior.models.OrderItem
import com.bumptech.glide.Glide


class OrderItemAdapter(
    listener: orderItemFunctions,
    layoutInflater: LayoutInflater,
    applicationContext: Context
): RecyclerView.Adapter<OrderItemAdapter.ViewHolder>() {

    var listener:orderItemFunctions
    var items= arrayListOf<OrderItem>()
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
    fun update(items:ArrayList<OrderItem>){
        this.items=items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.code.text=items[position].code
        holder.name.text=items[position].name
        holder.price.text=items[position].price
        holder.quantity.text=items[position].quantity
        holder.root.setOnClickListener { listener.itemClick(items[position].item_id) }
        val url=ApiConstants.apiUrl+ApiConstants.imageUrl+items[position].image_url
        Log.d("TAG",url)
        Glide.with(holder.itemView.context).load(url).into(holder.image)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.orderitem_image)
        var price=view.findViewById<TextView>(R.id.orderitem_price)
        var name=view.findViewById<TextView>(R.id.orderitem_name)
        var code=view.findViewById<TextView>(R.id.orderitem_code)
        var root=view.findViewById<CardView>(R.id.orderitem_root)
        val quantity=view.findViewById<TextView>(R.id.orderitem_quantity)
    }
}
interface orderItemFunctions{
    fun itemClick(itemId: String)
}
