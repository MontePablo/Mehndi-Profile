package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.adretsoftwere.mehndinterior.databinding.ActivityNewProfileBinding
import com.adretsoftwere.mehndinterior.databinding.SearchUserFragviewBinding
import com.adretsoftwere.mehndinterior.models.User

class NewProfile : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var accountType="Manufacturer"
    lateinit var parentUser:User
    lateinit var binding:ActivityNewProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        val list= listOf<String>("Manufacturer","Distributor","Wholesaler","Retailer","Agent")
        val shapeAdapter: ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerAccountType.adapter=shapeAdapter
        binding.spinnerAccountType.onItemSelectedListener=this
        binding.searchUserText.setOnClickListener(View.OnClickListener { funcUserSpin() })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        accountType=p0?.selectedItem.toString()

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    fun funcUserSpin(){
        val dialogBuilder= AlertDialog.Builder(this)
        val viewBinding= SearchUserFragviewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
        val adapter=getUserAsAdapter()
        viewBinding.listView.adapter=adapter
        viewBinding.listView.onItemClickListener=object:AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val user=FakeData.userSet[p2]
                binding.searchUserText.text=user.name
                parentUser=user
                dialog.dismiss()
            }
        }
        viewBinding.editText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                adapter.filter.filter(p0)
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    fun getUserAsAdapter():ArrayAdapter<String>{
        var arr=ArrayList<String>()
        for ( user in FakeData.userSet){
            arr.add("${user.name} ${user.email} ${user.mobile} ${user.address}")
        }
        val adapter=ArrayAdapter<String>(applicationContext,android.R.layout.simple_list_item_1,arr)
        return adapter
    }
}