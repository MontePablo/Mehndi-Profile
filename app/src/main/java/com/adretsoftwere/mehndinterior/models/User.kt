package com.adretsoftwere.mehndinterior.models

class User(name:String,mobile:String,email:String) {
    var id=""
    var password=""
    var name=""
    var mobile=""
    var email=""
    var address=""
    var parent=""
    init {
        this.name=name
        this.email=email
        this.mobile=mobile
    }

}