package com.adretsoftwere.mehndinterior.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.models.Item
import com.bumptech.glide.Glide


class ItemAdapter(listener:itemFunctions): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
     var listener:itemFunctions
     lateinit var items:ArrayList<Item>
    init {
        this.listener=listener
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
        }else {
            holder.price.text = items[position].price
        }
        Glide.with(holder.itemView.context).load(items[position].imageurl).into(holder.image)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_image)
        var price=view.findViewById<TextView>(R.id.item_price)
        var name=view.findViewById<TextView>(R.id.item_title)

    }
}
interface itemFunctions{
    fun itemClick(item:Item)
}
