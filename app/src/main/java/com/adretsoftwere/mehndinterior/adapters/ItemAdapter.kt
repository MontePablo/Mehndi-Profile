package com.adretsoftwere.mehndinterior.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.databinding.UserOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.models.Item
import com.bumptech.glide.Glide


class ItemAdapter(
    listener: itemFunctions,
    layoutInflater: LayoutInflater,
    applicationContext: Context
): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    var listener:itemFunctions
    var items= arrayListOf<Item>()
    var z=""
    lateinit var layoutInflater: LayoutInflater
    lateinit var context: Context
    init {
        this.layoutInflater=layoutInflater
        this.context=applicationContext
        this.listener=listener
        this.z=z

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.customview_item,parent,false)
        val vh=ViewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun update(items:ArrayList<Item>){
        this.items=items
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text=items[position].name
        if(items[position].price.isNullOrEmpty()){
            holder.price.text=""
            holder.priceUnit.text=""
        }else {
            holder.price.text = items[position].price
        }
        holder.root.setOnClickListener(View.OnClickListener {
            listener.ItemClickFunc(items[position],it)
        })
        val url=ApiConstants.apiUrl+ApiConstants.imageUrl+items[position].image_url
        Log.d("TAG",url)
        Glide.with(holder.itemView.context).load(url).into(holder.image)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_image)
        var price=view.findViewById<TextView>(R.id.item_price)
        var priceUnit=view.findViewById<TextView>(R.id.item_price_unit)
        var name=view.findViewById<TextView>(R.id.item_title)
        var root=view.findViewById<CardView>(R.id.item_root)
    }
}
interface itemFunctions{
    fun ItemClickFunc(item: Item, view: View)
}
