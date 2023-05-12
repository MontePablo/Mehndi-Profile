package com.adretsoftwere.mehndinterior.adapters

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object AtStartup {
    lateinit var  context:Context
    lateinit var preference:SharedPreferences
     var user_id="String"
    fun setId(context:Context,data:String){
        this.context=context
        val editor=preference.edit()
        editor.putString("user_id",data)
        editor.apply()
    }
    fun getId(context:Context):String{
        preference=PreferenceManager.getDefaultSharedPreferences(AtStartup.context)
        user_id= preference.getString("user_id",null)!!
        this.context=context
        return user_id
    }
}