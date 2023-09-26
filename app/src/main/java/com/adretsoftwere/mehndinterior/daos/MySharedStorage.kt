package com.adretsoftwere.mehndinterior.daos

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.adretsoftwere.mehndinterior.models.User

object MySharedStorage {
    lateinit var context: Context
    lateinit var preference: SharedPreferences
    lateinit var user:User
    lateinit var editor: SharedPreferences.Editor


    fun func(context: Context) {
        this.context = context
        preference = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preference.edit()
    }

    fun getUserId(): String {
        return  preference.getString("user_id", "")!!
    }
    fun saveUser(user:User){
        this.user=user
    }
    fun getUserr():User{
        return user
    }

    fun setUserId(data: String) {
        editor.putString("user_id", data)
        editor.apply()
    }
    fun getUserType(): String {
        return  preference.getString("user_type", null)!!
    }

    fun setUserType(data: String) {
        editor.putString("user_type", data)
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