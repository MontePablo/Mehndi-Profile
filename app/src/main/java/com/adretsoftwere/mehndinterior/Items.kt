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
        window.statusBarColor=getColor(R.color.sixty1)

        adapter= ItemAdapter(this,ItemAdapter.SURF)


        adapter.update(FakeData.dataset)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)

    }

    override fun ItemClickFunc(item: Item) {
        adapter.update(FakeData.dataset2)
    }

    override fun openDiscountFunc(item: Item) {
    }
}