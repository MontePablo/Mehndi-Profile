package com.adretsoftwere.mehndinterior.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.User

class UserAdapter(listener:userFunctions): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    lateinit var listener:userFunctions

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image=view.findViewById<ImageView>(R.id.item_image)
        var price=view.findViewById<TextView>(R.id.item_price)
        var priceUnit=view.findViewById<TextView>(R.id.item_price_unit)
        var name=view.findViewById<TextView>(R.id.item_title)
        var root=view.findViewById<ConstraintLayout>(R.id.item_root)
    }
}
interface userFunctions{
    fun itemClick(user: User)
}