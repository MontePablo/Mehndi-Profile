package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.databinding.ActivityItemsBinding
import com.adretsoftwere.mehndinterior.models.Item

class Items : AppCompatActivity(),itemFunctions {
    lateinit var binding:ActivityItemsBinding
    lateinit var adapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= ItemAdapter(this)
        val dataset= arrayListOf<Item>()

        dataset.add(Item("Red velvet","https://i.ibb.co/n0B7QLD/a.png","2000.00"))
        dataset.add(Item("green velvet","https://i.ibb.co/n0B7QLD/b.png","2000.00"))
        dataset.add(Item("white velvet","https://i.ibb.co/n0B7QLD/c.png","2000.00"))
        dataset.add(Item("wooden velvet","https://i.ibb.co/n0B7QLD/d.png","2000.00"))
        dataset.add(Item("Red velvet","https://i.ibb.co/n0B7QLD/a.png","2000.00"))
        dataset.add(Item("green velvet","https://i.ibb.co/n0B7QLD/b.png","2000.00"))
        dataset.add(Item("white velvet","https://i.ibb.co/n0B7QLD/c.png","2000.00"))
        dataset.add(Item("wooden velvet","https://i.ibb.co/n0B7QLD/d.png","2000.00"))
        dataset.add(Item("Red velvet","https://i.ibb.co/n0B7QLD/a.png","2000.00"))
        dataset.add(Item("green velvet","https://i.ibb.co/n0B7QLD/b.png","2000.00"))
        dataset.add(Item("white velvet","https://i.ibb.co/n0B7QLD/c.png","2000.00"))
        dataset.add(Item("wooden velvet","https://i.ibb.co/n0B7QLD/d.png","2000.00"))

        adapter.update(dataset)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)

    }

    override fun itemClick(item: Item) {
        TODO("Not yet implemented")
    }
}