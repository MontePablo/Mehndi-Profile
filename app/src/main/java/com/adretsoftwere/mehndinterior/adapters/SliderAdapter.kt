package com.adretsoftwere.mehndinterior.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.models.Image
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(): SliderViewAdapter<SliderAdapter.ViewHolder>() {
    var images:ArrayList<Image> =ArrayList()
    override fun getCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_slider, parent, false)
        return ViewHolder(view)
    }
    fun updateData(images:ArrayList<Image>){
        this.images=images
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {

        val url= Constants.apiUrl+ Constants.imageUrl+images[position].filename
        Glide.with(viewHolder?.imageView!!.context).load(url).into(viewHolder?.imageView!!)
    }
    class ViewHolder(itemView: View): SliderViewAdapter.ViewHolder(itemView){
        var imageView=itemView.findViewById<ImageView>(R.id.slide_image)
    }
}