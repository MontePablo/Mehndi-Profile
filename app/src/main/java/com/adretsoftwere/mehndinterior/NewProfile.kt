package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityNewProfileBinding
import com.adretsoftwere.mehndinterior.databinding.SearchUserFragviewBinding
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import com.adretsoftwere.mehndinterior.models.RetrofitUser
import com.adretsoftwere.mehndinterior.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewProfile : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var accountType="Manufacturer"
    lateinit var parentUser:User
    lateinit var binding:ActivityNewProfileBinding
    lateinit var users:ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)
        loadParents()
        val list= listOf<String>(Constants.DISTRIBUTER,Constants.AGENT,Constants.MANUFACTURER,Constants.RETAILER,Constants.WHOLESALER)
        val shapeAdapter: ArrayAdapter<*>
        shapeAdapter= ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list)
        binding.spinnerAccountType.adapter=shapeAdapter
        binding.spinnerAccountType.onItemSelectedListener=this
        binding.searchUserText.setOnClickListener(View.OnClickListener { funcUserSpin() })

        binding.create.setOnClickListener(View.OnClickListener {
            val name=binding.name.text.toString();val email=binding.email.text.toString();val number=binding.mobile.text.toString()
            val address=binding.address.text.toString()
            val password=binding.password.text.toString()
            if(name.isBlank() || email.isBlank() || number.isBlank() || password.isBlank() )
                Toast.makeText(applicationContext,"fill all fields first!",Toast.LENGTH_SHORT).show()
            else{
                val user=User()
                user.let {
                    it.name=name;it.email=email;it.mobile=number;it.address=address;it.parent=parentUser.user_id
                    it.password=password
                }
                RetrofitClient.getApiHolder().setUser(user).enqueue(object:Callback<RetrofitResponse>{
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        if(response.code()==Constants.code_OK){
                            Log.d("TAG",response.code().toString())
                            Toast.makeText(applicationContext,"created! succesfully!",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(applicationContext,Users::class.java))
                            finish()
                        }else {
                            Log.d("TAG 2",response.code().toString() + response.message().toString())
                        }
                    }
                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG","on failure retro : ${t.localizedMessage}")
                    }})
            }
        })
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
                binding.searchUserText.text=users[p2].name
                parentUser=users[p2]
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
        for ( user in users){
            arr.add("${user.name} ${user.email} ${user.mobile} ${user.address}")
        }
        val adapter=ArrayAdapter<String>(applicationContext,android.R.layout.simple_list_item_1,arr)
        return adapter
    }
    fun loadParents(){
        RetrofitClient.getApiHolder().getUser().enqueue(object: Callback<RetrofitUser> {
            override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                if(response.code()== Constants.code_OK){
                    Log.d("TAG",response.code().toString())
                    users=response.body()!!.data
                }else if(response.code()==Constants.code_NO_CONTENT){

                }
                else {
                    Log.d("TAG 2",response.code().toString() + response.message().toString())
                }
            }

            override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                Log.d("TAG","on failure retro : ${t.localizedMessage}")

            }

        })
    }
}