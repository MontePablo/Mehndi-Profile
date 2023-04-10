package com.adretsoftwere.mehndinterior.models

class Item(name:String,imageurl:String,price:String) {
    var id=""
    var name=""
    var code=""
    var imageurl=""
    var parent=""
    var price=""
    var quantity=""
    init {
        this.name=name
        this.price=price
        this.imageurl=imageurl
    }

}
