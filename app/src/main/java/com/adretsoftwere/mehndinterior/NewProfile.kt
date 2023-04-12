package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.adretsoftwere.mehndinterior.databinding.ActivityNewProfileBinding

class NewProfile : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var accountType="Manufacturer"
    lateinit var binding:ActivityNewProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        val list= listOf<String>("Manufacturer","Distributor","Wholesaler","Retailer","Agent")
        val shapeAdapter: ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,R.drawable.my_dropdown_item,list)
        binding.spinner.adapter=shapeAdapter
        binding.spinner.onItemSelectedListener=this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        accountType=p0?.selectedItem.toString()

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}