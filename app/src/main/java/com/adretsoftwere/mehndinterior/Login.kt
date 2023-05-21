package com.adretsoftwere.mehndinterior

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.adretsoftwere.mehndinterior.daos.Constants
import com.adretsoftwere.mehndinterior.daos.MySharedStorage
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityLoginBinding
import com.adretsoftwere.mehndinterior.models.InvoiceData
import com.adretsoftwere.mehndinterior.models.OrderItem
import com.adretsoftwere.mehndinterior.models.RetrofitUser
import com.adretsoftwere.mehndinterior.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)

        binding.signIn.setOnClickListener(View.OnClickListener {
            val user=User()
            if(binding.id.text.isBlank() || binding.password.text.isBlank()){
                Toast.makeText(applicationContext,"fill all fields first",Toast.LENGTH_SHORT).show()
            }else{
                val id=binding.id.text.toString()
                val password=binding.password.text.toString()

                if(id.contains("@")){
                    user.email=id
                }else{
                    user.mobile=id
                }
                RetrofitClient.getApiHolder().searchUser(user).enqueue(object: Callback<RetrofitUser>{
                    override fun onResponse(call: Call<RetrofitUser>, response: Response<RetrofitUser>) {
                        if(response.code()==Constants.code_OK){
                            val fetchedUser=response.body()!!.data[0]
                            if(fetchedUser.password==password){
                                Toast.makeText(applicationContext,"welcome!",Toast.LENGTH_SHORT).show()
                                MySharedStorage.setUserId(fetchedUser.user_id)
                                MySharedStorage.setUserType(fetchedUser.user_type)
                                val intent = Intent(applicationContext, MainActivity::class.java)
                                intent.putExtra("user_type", fetchedUser.user_type)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(applicationContext,"wrong password!",Toast.LENGTH_SHORT).show()
                            }
                        }else if(response.code()==Constants.code_NO_CONTENT){
                            Toast.makeText(applicationContext,"no user found!",Toast.LENGTH_SHORT).show()
                        }
                        Log.d("TAG","searchUser:"+response.code())
                    }
                    override fun onFailure(call: Call<RetrofitUser>, t: Throwable) {
                        Log.d("TAG","searchUser:"+t.localizedMessage)
                    }
                })
            }

        })


        binding.invoice.setOnClickListener {

            val data=InvoiceData()
            data.apply {
                user.user_id="24524675"
                user.name="Maharaj Dey"
                user.email="maharajdey23@gmail.com"
                user.mobile="1234567890"
                user.address="Madarat,Baruipur"
                order.date="2023-10-4"
                order.order_id="64226926262"
                order.price="11000"
                val c=OrderItem().apply {
                    code="ASD-32"
                    item_id="465426262"
                    name="Wardrobe"
                    quantity="10"
                    price="1000"
                    total_price="10000"
                }
                items.add(c)
                items.add(c)
                items.add(c)
                items.add(c)
                items.add(c)
                items.add(c)

            }
            InvoiceGenerator(this,data)

        }
    }
}