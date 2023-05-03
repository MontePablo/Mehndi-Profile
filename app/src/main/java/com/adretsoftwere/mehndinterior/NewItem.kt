package com.adretsoftwere.mehndinterior

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.daos.ApiConstants
import com.adretsoftwere.mehndinterior.daos.RetrofitClient
import com.adretsoftwere.mehndinterior.databinding.ActivityNewItemBinding
import com.adretsoftwere.mehndinterior.databinding.CustomviewImageBinding
import com.adretsoftwere.mehndinterior.models.Item
import com.adretsoftwere.mehndinterior.models.RetrofitResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.concurrent.timerTask

class NewItem : AppCompatActivity(), itemFunctions {
    val NOT_IMAGE="0"
    var imageViewTable: Hashtable<Int, CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    lateinit var binding: ActivityNewItemBinding
    lateinit var adapter: ItemAdapter
    var mainImage=""
    var parent=""
    val images= arrayListOf<String>()
    val item=Item()
    val item_id=System.currentTimeMillis().toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        if(item.item_id.isNullOrEmpty())
//            item.item_id=System.currentTimeMillis().toString()
        window.statusBarColor=getColor(R.color.sixty1)
        addImage()
        binding.addImage.setOnClickListener(View.OnClickListener {
            addImage()
        })
        binding.mainImage.insert.setOnClickListener(View.OnClickListener {
            photoPick(binding.mainImage.hashCode())
            imageViewTable.put(binding.mainImage.hashCode(),binding.mainImage)
        })
        binding.uploadbtn.setOnClickListener(View.OnClickListener {
//            item.name="demo name"
//            item.price="300"
//            item.image_url="https:ngn.jpg"
//            item.code="234FG"
//            item.parent="5"
//            item.quantity="4"
            if(parent.isBlank() || binding.name.text.isBlank() || binding.price.text.isBlank() || binding.code.text.isBlank() || binding.quantity.text.isBlank() || imageViewTable.isEmpty)
                Toast.makeText(applicationContext,"fill all the fields first!",Toast.LENGTH_LONG).show()
            else
                upload()
            })
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
            var imageUri=data!!.data
            Log.d("TAG","onActivityResult Image received")
            val imageBinding= imageViewTable[requestCode]
           imageUpload(imageUri,item_id,imageBinding)
        }
        return
    }

    private fun imageUpload(imageUri: Uri?, idd: String, imageBinding: CustomviewImageBinding?) {
            if(imageUri!=null){
                imageBinding?.imageview?.setImageURI(imageUri)
                imageBinding?.insert?.visibility=View.GONE
                progressBarFunc(imageBinding!!)
                imageBinding.retry.visibility=View.GONE
                val file= File(RealPathUtil.getRealPath(this, imageUri))
                val fileName=getNewFileName(file.name)
                imageBinding.storeName.text=fileName
                val requestFile= RequestBody.create(MediaType.parse("image/*"), file);
                val id=RequestBody.create(MediaType.parse("text/plain"),idd)
//                val requestFile= RequestBody.create(MediaType.parse("multipart/form-data"), file);
                val body=MultipartBody.Part.createFormData("uploaded_file",fileName,requestFile)
                RetrofitClient.getApiHolder().imageUpload(body,id).enqueue(object: Callback<RetrofitResponse>{
                    override fun onResponse(call: Call<RetrofitResponse>, response: Response<RetrofitResponse>) {
                        if(response.code()==ApiConstants.code_CREATED) {
                            Toast.makeText(applicationContext, "uploaded!", Toast.LENGTH_SHORT)
                                .show()
                            Log.d("TAG ",response.code().toString())
                            imageBinding!!.insert.visibility = View.GONE
                        }else{
                            Log.d("TAG ",response.code().toString())
                            Toast.makeText(applicationContext,"Failed!",Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<RetrofitResponse>, t: Throwable) {
                        imageBinding!!.retry.visibility=View.VISIBLE
                        imageBinding!!.retry.setOnClickListener(View.OnClickListener {
                            imageUpload(imageUri,idd,imageBinding)
                        })
                        imageBinding.progressBar.visibility=View.GONE
                        imageBinding.insert.visibility=View.GONE
                        Toast.makeText(applicationContext,"upload failed! ${t.localizedMessage}",Toast.LENGTH_SHORT).show()
                        Log.d("TAG","photo upload failed: ${t.localizedMessage}")
                    }
                })
            }
    }
    private fun progressBarFunc(viewBinding: CustomviewImageBinding){
        viewBinding.progressBar.visibility=View.VISIBLE
        var counter=0
        var timer=Timer()
        var timertask= timerTask {
            run(){
                super.runOnUiThread(Runnable {
                    counter++;
                    viewBinding.progressBar.setProgress(counter)
                    if(counter==100){
                        timer.cancel()
                        viewBinding.progressBar.visibility=View.INVISIBLE
                    }
                })
            }
        }
        timer.schedule(timertask,0,15)
    }
    private fun getNewFileName(n:String):String{
        var name=System.currentTimeMillis().toString()
        if(n.contains(".jpg") || n.contains("JPG")){
           name+=".jpg"
        }else if(n.contains(".png")){
            name+=".png"
        }else{
            name=NOT_IMAGE
        }
        return name
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

        dataStore()

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

    fun dataStore(){
        for(i in imageViewTable){
            if(i.value==binding.mainImage){
                mainImage=i.value.storeName.text.toString()
            }else{
                images.add(i.value.storeName.text.toString())
            }
        }
        item.name=binding.name.text.toString()
        item.code=binding.code.text.toString()
        item.price=binding.price.text.toString()
        item.quantity=binding.quantity.text.toString()
        item.image_url=mainImage
        item.parent=parent
        item.item_id=item_id
    }
}