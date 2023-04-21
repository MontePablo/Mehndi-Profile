package com.adretsoftwere.mehndinterior.daos

import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream

class ProgressRequestBody(file:File,listener:UploadCallbacks): RequestBody() {
    val DEFAULT_BUFFER_SIZE=4096
     val file:File
     val listener:UploadCallbacks
    init {
        this.file=file
        this.listener=listener
    }

    override fun contentType(): MediaType? {
        return MediaType.parse("image/*")
    }

    override fun writeTo(sink: BufferedSink) {
        val fileLength=file.length()
        val buffer= ByteArray(DEFAULT_BUFFER_SIZE)
        val inputStream=FileInputStream(file)
        var uploaded:Long=0
        try {
            val read=inputStream.read(buffer)
            val handler= android.os.Handler(Looper.getMainLooper())
            while (read!=-1){
                handler.post(ProgressUpdater(uploaded,fileLength))
                uploaded+=read
                sink.write(buffer,0,read)
            }
        }finally {
            inputStream.close()
        }
    }


    inner class ProgressUpdater(uploaded:Long,fileLength:Long):Runnable{
        var uploaded:Long=0
        var fileLength:Long=0
        init {
            this.uploaded=uploaded
            this.fileLength=fileLength
        }
        override fun run() {
            listener.onProgressUpdate(((100*uploaded)/fileLength).toInt())
        }
    }
}
 interface UploadCallbacks{
    fun onProgressUpdate(percentage:Int)
}
