package com.adretsoftwere.mehndinterior.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.models.Item
import com.bumptech.glide.Glide


class ItemAdapter(listener:itemFunctions,z:String): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    var listener:itemFunctions
    lateinit var items:ArrayList<Item>
    var z=""
    init {
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
            if(z=="surf") {
                listener.itemClick(items[position])
            }else if(z=="discount"){
                //            holder.root.isPressed=true
                listener.openDiscountFunc(items[position])
            }
        })
        Glide.with(holder.itemView.context).load(items[position].imageurl).into(holder.image)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_image)
        var price=view.findViewById<TextView>(R.id.item_price)
        var priceUnit=view.findViewById<TextView>(R.id.item_price_unit)
        var name=view.findViewById<TextView>(R.id.item_title)
        var root=view.findViewById<ConstraintLayout>(R.id.item_root)
    }
}
interface itemFunctions{
    fun itemClick(item:Item)
    fun openDiscountFunc(item:Item)

}
