package com.adretsoftwere.mehndinterior.models

class InvoiceData {
    val user=User()
    val order=Order()
    val items= arrayListOf<OrderItem>()
}