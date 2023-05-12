package com.adretsoftwere.mehndinterior

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.AtStartup
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityDiscountsManageBinding
import com.adretsoftwere.mehndinterior.databinding.DiscountOptionsFragviewBinding
import com.adretsoftwere.mehndinterior.models.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscountsManage : AppCompatActivity(), itemFunctions{
    lateinit var binding:ActivityDiscountsManageBinding
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiscountsManageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor=getColor(R.color.sixty1)

        adapter= ItemAdapter(this, layoutInflater, applicationContext)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.layoutManager=
            GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)

        RetrofitClient.getApiHolder().getItems().enqueue(object:Callback<RetrofitItem>{
            override fun onResponse(call: Call<RetrofitItem>, response: Response<RetrofitItem>) {
                if(response.code()==ApiConstants.code_OK){
                    Log.d("TAG",response.code().toString())
                    adapter.update(response.body()!!.data)
                }else {
                    Log.d("TAG 2",response.code().toString() + response.message().toString())
                }
            }

            override fun onFailure(call: Call<RetrofitItem>, t: Throwable) {
                Log.d("TAG","on failure retro : ${t.localizedMessage}")

            }

        })
    }

    override fun ItemClickFunc(item: Item, view: View) {
        val dialogBuilder= AlertDialog.Builder(this)
        val viewBinding= DiscountOptionsFragviewBinding.inflate(layoutInflater)
        dialogBuilder.setView(viewBinding.root)
        val dialog=dialogBuilder.create()
        dialog.show()
        if(!AtStartup.getId(this).isBlank()){
            getDiscount(AtStartup.user_id,item.item_id,viewBinding,dialog,item)
        }
    }

    private fun func(viewBinding: DiscountOptionsFragviewBinding) {
        viewBinding.save.setOnClickListener(View.OnClickListener {

        })
        viewBinding.subItems.setOnClickListener(View.OnClickListener {
//            adapter.update(FakeData.dataset2)
        })
    }

    fun getDiscount(
        userid: String,
        itemid: String,
        viewBinding: DiscountOptionsFragviewBinding,
        dialog: AlertDialog,
        item: Item,){
        val item_id= RequestBody.create(MediaType.parse("text/plain"),itemid)
        val user_id= RequestBody.create(MediaType.parse("text/plain"),userid)
        RetrofitClient.getApiHolder().getDiscountByUser(user_id, item_id).enqueue(object :
            Callback<RetrofitDiscount> {
            override fun onResponse(call: Call<RetrofitDiscount>, response: Response<RetrofitDiscount>) {
                if(response.code()== ApiConstants.code_OK){
                    viewBinding.amount.setText(response.body()!!.data[0].amount)
                    if(response.body()!!.data[0].discount_type==Discount.asPercentage)
                        viewBinding.radiopercent.isChecked=true
                    else
                        viewBinding.radioprice.isChecked=true
                }else if(response.code()==ApiConstants.code_NO_CONTENT){
                }
                else{
                    Log.d("TAG",response.code().toString())
                }
            }
            override fun onFailure(call: Call<RetrofitDiscount>, t: Throwable) {
                Log.d("TAG",t.localizedMessage)
            }
        })

        viewBinding.save.setOnClickListener(View.OnClickListener {
            var type=""
            if(viewBinding.radiogroup.checkedRadioButtonId==viewBinding.radiopercent.id)
                type=Discount.asPercentage
            else if(viewBinding.radiogroup.checkedRadioButtonId==viewBinding.radioprice.id)
                type=Discount.asPrice
            else{ Toast.makeText(applicationContext,"choose type first",Toast.LENGTH_SHORT).show()}

            type=Discount.asPrice

            val price=viewBinding.amount.text.toString()
            if(price.isNullOrBlank())
                Toast.makeText(applicationContext,"enter price first",Toast.LENGTH_SHORT).show()
            else{
                val discount=Discount()
                discount.discount_type=type
                discount.user_id=userid
                discount.item_id=itemid
                discount.amount=price
                RetrofitClient.getApiHolder().setDiscount(discount).enqueue(object : Callback<RetrofitResponse>{
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        if(response.code()==ApiConstants.code_OK) {
                            Toast.makeText(applicationContext, "saved", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        else{
                            Log.d("TAG",response.code().toString())
                        }
                    }
                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG",t.localizedMessage)
                    }
                })

            }
        })
        viewBinding.subItems.setOnClickListener(View.OnClickListener {
            val parent= RequestBody.create(MediaType.parse("text/plain"),item.item_id)
            Log.d("TAG",item.item_id + item.name)
            RetrofitClient.getApiHolder().getItemsByParent(parent).enqueue(object : Callback<RetrofitItem>{
                override fun onResponse(call: Call<RetrofitItem>, response: Response<RetrofitItem>) {
                    if(response.code()==ApiConstants.code_OK)
                        adapter.update(response.body()!!.data)
                    else{
                        Log.d("TAG",response.code().toString())
                    }
                }
                override fun onFailure(call: Call<RetrofitItem>, t: Throwable) {
                    Log.d("TAG",t.localizedMessage)
                }
            })
        })

    }

}