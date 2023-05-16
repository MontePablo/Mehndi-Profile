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
import com.adretsoftwere.mehndinterior.models.CartItem
import com.adretsoftwere.mehndinterior.models.Order
import com.bumptech.glide.Glide


class OrderAdapter(
    listener: orderFunctions,
    layoutInflater: LayoutInflater,
    applicationContext: Context
): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    var listener:orderFunctions
    var items= arrayListOf<Order>()
    lateinit var layoutInflater: LayoutInflater
    lateinit var context: Context
    init {
        this.layoutInflater=layoutInflater
        this.context=applicationContext
        this.listener=listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.customview_order,parent,false)
        val vh=ViewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun update(items:ArrayList<Order>){
        this.items=items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text=items[position].date
        holder.name.text=items[position].name
        holder.price.text=items[position].price
        holder.status.text=items[position].status
        holder.root.setOnClickListener { listener.itemClick(items[position].order_id) }
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var price=view.findViewById<TextView>(R.id.order_price)
        var name=view.findViewById<TextView>(R.id.order_name)
        var root=view.findViewById<CardView>(R.id.order_root)
        var date=view.findViewById<TextView>(R.id.order_date)
        var status=view.findViewById<TextView>(R.id.order_status)
    }
}
interface orderFunctions{
    fun itemClick(order_id:String)
}
