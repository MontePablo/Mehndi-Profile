package com.adretsoftwere.mehndinterior.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.adretsoftwere.mehndinterior.R
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.User

class UserAdapter(listener:userFunctions): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var listener:userFunctions
    var users= (arrayListOf <User>())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.customview_user,parent,false)
        val vh= UserAdapter.ViewHolder(view)
        return vh
    }
    init {
        this.listener=listener
    }
    fun update(users:ArrayList<User>){
        this.users=users
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.email.text=users[position].email
        holder.name.text=users[position].name
        holder.number.text=users[position].mobile
        holder.root.setOnClickListener(View.OnClickListener {
            listener.itemClick(users[position])
        })
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var name=view.findViewById<TextView>(R.id.user_name)
        var email=view.findViewById<TextView>(R.id.user_email)
        var number=view.findViewById<TextView>(R.id.user_number)
        var root=view.findViewById<CardView>(R.id.user_root)
    }
}
interface userFunctions{
    fun itemClick(user: User)
}