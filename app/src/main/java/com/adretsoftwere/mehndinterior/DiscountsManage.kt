package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.databinding.ActivityDiscountsManageBinding
import com.adretsoftwere.mehndinterior.databinding.DiscountOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.models.Item

class DiscountsManage : AppCompatActivity(), itemFunctions{
    lateinit var binding:ActivityDiscountsManageBinding
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiscountsManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor=getColor(R.color.sixty1)

        adapter= ItemAdapter(this,ItemAdapter.DISCOUNT)
        adapter.update(FakeData.dataset)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=
            GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)
    }

    override fun ItemClickFunc(item: Item, view: View) {
    }

    override fun openDiscountFunc(item: Item) {
        val dialogBuilder= AlertDialog.Builder(this)
        val viewBinding= DiscountOptionsFragviewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
        func(viewBinding)
    }

    private fun func(viewBinding: DiscountOptionsFragviewBinding) {
        viewBinding.save.setOnClickListener(View.OnClickListener {

        })
        viewBinding.subItems.setOnClickListener(View.OnClickListener {
            adapter.update(FakeData.dataset2)
        })
    }
}