package com.adretsoftwere.mehndinterior.models

open class Item(name:String="",imageurl:String="",price:String="") {
    var item_id=""
    var name=""
    var code=""
    var image_url=""
    var parent=""
    var price=""
    var quantity=""
    var about=""
    var features=""
    init {
        this.name=name
        this.image_url=imageurl
        this.price=price
    }

}
