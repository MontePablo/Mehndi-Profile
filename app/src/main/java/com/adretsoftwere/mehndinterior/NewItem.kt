package com.adretsoftwere.mehndinterior

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.adretsoftwere.mehndinterior.adapters.ItemAdapter
import com.adretsoftwere.mehndinterior.adapters.itemFunctions
import com.adretsoftwere.mehndinterior.databinding.ActivityNewItemBinding
import com.adretsoftwere.mehndinterior.databinding.CustomviewImageBinding
import com.adretsoftwere.mehndinterior.models.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NewItem : AppCompatActivity(), itemFunctions {
    var imageViewTable: Hashtable<Int, CustomviewImageBinding> = Hashtable<Int,CustomviewImageBinding>()
    lateinit var binding: ActivityNewItemBinding
    lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor=getColor(R.color.sixty1)
        binding.addImage.setOnClickListener(View.OnClickListener {
            addImage()
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
            imageBinding?.imageview?.setImageURI(imageUri)
            imageBinding?.storeUri?.text = imageUri.toString()
            imageBinding?.insert?.visibility=View.GONE
//            CoroutineScope(Dispatchers.Default).launch {
//                uploadImage(imageBinding!!,imageUri!!)
//            }
        }
        return

    }
    suspend fun uploadImage(viewBinding:CustomviewImageBinding,imageUri: Uri){
            viewBinding.imageview.setImageURI(imageUri)
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

}