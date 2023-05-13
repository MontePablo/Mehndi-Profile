package com.adretsoftwere.mehndinterior.daos

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object MySharedStorage {
    lateinit var context: Context
    lateinit var preference: SharedPreferences
    var user_id =""
    var loginId = ""
    var password = ""

    lateinit var editor: SharedPreferences.Editor

    init {
        preference = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preference.edit()
    }

    fun init(context: Context) {
        this.context = context
    }

    fun getUserId(): String {
        user_id = preference.getString("user_id", null)!!
        return user_id
    }

    fun setUserId(data: String) {
        editor.putString("user_id", data)
        editor.apply()
    }
//    fun getLoginId():String{
//        user_id = preference.getString("user_id",null)!!
//        return user_id
//    }
//    fun setLoginId(data:String){
//        editor.putString("user_id",data)
//        editor.apply()
//    }
//    fun getPassword():String{
//        password = preference.getString("password",null)!!
//        return password
//    }
//    fun setPassword(data:String){
//        editor.putString("password",data)
//        editor.apply()
//    }

}