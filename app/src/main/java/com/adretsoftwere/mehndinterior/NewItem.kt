package com.adretsoftwere.mehndinterior

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.FileUtils
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityNewItemBinding
import com.adretsoftwere.mehndinterior.databinding.CustomviewImageBinding
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitItem
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class NewItem : AppCompatActivity(), itemFunctions {
    var imageViewTable: Hashtable<Int, CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    lateinit var binding: ActivityNewItemBinding
    lateinit var adapter: ItemAdapter
    val item=Item()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)
        binding.addImage.setOnClickListener(View.OnClickListener {
            addImage()
        })
        binding.uploadbtn.setOnClickListener(View.OnClickListener {
            item.name="demo name"
            item.price="300"
            item.image_url="https:ngn.jpg"
            item.code="234FG"
            item.parent="5"
            item.quantity="4"
            upload() })
        permission()
        func()
    }
    fun addImage(){
        val imageBinding=CustomviewImageBinding.inflate(layoutInflater)

        imageBinding.insert.setOnClickListener(View.OnClickListener {
            photoPick(imageBinding.hashCode())
            imageViewTable.put(imageBinding.hashCode(),imageBinding)
        })

        binding.imageLayout.addView(imageBinding.root)
    }
    fun photoPick(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                } else {
                }
            }
        }else{
            Log.d("TAG","onactivity else")
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding= imageViewTable[requestCode]

           imageUpload(imageUri)
            imageBinding?.imageview?.setImageURI(imageUri)
            imageBinding?.storeUri?.text = imageUri.toString()
            imageBinding?.insert?.visibility=View.GONE

        }
        return

    }

    private fun imageUpload(imageUri: Uri?) {
            if(imageUri!=null){
                val file= File(RealPathUtil.getRealPath(this, imageUri))
                val requestFile= RequestBody.create(MediaType.parse("image/*"), file);
//                val requestFile= RequestBody.create(MediaType.parse("multipart/form-data"), file);
                val body=MultipartBody.Part.createFormData("uploaded_file",file.name,requestFile)

                RetrofitClient.getApiHolder().photoUpload(body).enqueue(object: Callback<RetrofitResponse>{
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        Toast.makeText(applicationContext,"uploaded!",Toast.LENGTH_SHORT).show()
                        Log.d("TAG","photoUpload finished : ${response.body()?.message} ${response.message()} ${response.code()}")

                    }

                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        Log.d("TAG","photo upload failed: ${t.localizedMessage}")
                    }
                })
            }
    }


    fun permission() {
        if (!checkPermission()){
            showPermissionDialog()
        }
    }
    private fun showPermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(
                    String.format(
                        "package:%s", *arrayOf<Any>(
                            applicationContext.packageName
                        )
                    )
                )
                startActivityForResult(intent, 2000)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivityForResult(intent, 2000)
            }
        } else ActivityCompat.requestPermissions(
            this@NewItem,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            333
        )
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            val read = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            write == PackageManager.PERMISSION_GRANTED &&
                    read == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 333) {
            if (grantResults.size > 0) {
                val write = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val read = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (read && write) {
                } else {
                }
            }
        }
    }
    fun func(){
        adapter= ItemAdapter(this,ItemAdapter.SURF)

        adapter.update(FakeData.dataset)
        binding.recylerView.adapter=adapter
        binding.recylerView.layoutManager=
            GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false)
    }

    override fun ItemClickFunc(item: Item) {

    }

    override fun openDiscountFunc(item: Item) {
    }
    fun upload(){
        RetrofitClient.getApiHolder().sendItem(item).enqueue(object :
            Callback<RetrofitResponse>{
            override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                Log.d("TAG","upload success ${response.code()}")
                Toast.makeText(applicationContext,"uploaded!",Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                Log.d("TAG","upload failed ${t.localizedMessage}")
            }
        })
    }

}