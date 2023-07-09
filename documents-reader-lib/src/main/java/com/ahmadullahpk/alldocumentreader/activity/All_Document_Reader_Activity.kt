package com.ahmadullahpk.alldocumentreader.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.ahmadullahpk.alldocumentreader.toasty
import com.ahmadullahpk.alldocumentreader.util.Utility
import com.ahmadullahpk.alldocumentreader.xs.constant.MainConstant
import java.io.File
import java.io.FileOutputStream

class All_Document_Reader_Activity : AppCompatActivity() {

    var fileName: String? = ""
    var filepath: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)

        val intent = intent
        if (intent != null) {
            if(intent.getBooleanExtra("fromAppActivity",false)){
                filepath = getIntent().getStringExtra("path")
            }else{
                val action = intent.action
                if (Intent.ACTION_VIEW == action) {
                    val data = intent.data
                    if (data != null) {
                        filepath = getFilePathForN(data)
                    } else {
                        null
                    }
                } else if (Intent.ACTION_SEND == action) {
                    Log.e("checkahmad","action  "+intent.data )
                    val uri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)
                    if (uri != null) {
                        filepath = getFilePathForN(uri)

                    } else {
                        filepath = null
                    }
                }
            }

            val str = filepath
            if (str != null) {
                var m_intent : Intent ? = null
                val fileType = MainConstant.getFileType(str)
                m_intent = Intent(this, ViewFiles_Activity::class.java)
                if(filepath!!.endsWith("pdf")){
                    m_intent = Intent(this, PDF_Reader_Activity::class.java)
                    m_intent.action = "android.intent.action.VIEW";
                    m_intent.data = Uri.fromFile( File(filepath));
                }
                if(filepath!!.endsWith("rtf")){
                    m_intent = Intent(this, ViewRtf_Activity::class.java)
                }

                if(filepath!!.endsWith("csv")){
                    m_intent = Intent(this, CSVViewer_Activity::class.java)
                }

                m_intent?.putExtra("name", fileName)
                m_intent?.putExtra("fromConverterApp", true)
                m_intent?.putExtra("fileType", fileType.toString() + "")
                m_intent?.putExtra("fromAppActivity", true)
                m_intent?.putExtra("path", filepath)
                startActivity(m_intent)
                finish()
                return
            }
            toasty("Unable to open file from here, Go to Document Reader App and try to open from them")
            finish()
        }
    }

    private fun getFilePathForN(uri: Uri): String? {
        val query = contentResolver.query(uri, null, null, null, null)
        if (query == null) {
            val path = uri.path
            fileName = File(path).name
            Log.e("checkahmad", "Null query")
            return path
        }
        val columnIndex = query.getColumnIndex("_display_name")
        query.moveToFirst()
        if (query.count <= 0) {
            return null
        }
        val string = query.getString(columnIndex)
        fileName = string
        if (string == null) {
            val columnIndex2 = query.getColumnIndex("_data")
            return if (columnIndex2 >= 0) {
                query.getString(columnIndex2)
            } else null
        }
        Log.e("checkahmad", "out")
        val file = File("$cacheDir/.temp")
        Utility.deleteDir(file)
        file.mkdirs()
        val file2 = File(file, fileName)
        val path2 = file2.path
        query.close()
        try {
            val openInputStream = contentResolver.openInputStream(uri)
            val fileOutputStream = FileOutputStream(file2)
            val bArr = ByteArray(openInputStream!!.available().coerceAtMost(1048576))
            while (true) {
                val read = openInputStream.read(bArr)
                if (read == -1) {
                    break
                }
                fileOutputStream.write(bArr, 0, read)
            }
            openInputStream.close()
            fileOutputStream.close()
        } catch (e: Exception) {
            Log.e("checkahmad", e.message.toString())
        }
        Log.e("checkahmad", path2.toString())
        return path2
    }

    fun getPath(context: Context, uri: Uri?): String {
        val strArr = arrayOf("_data")
        val query = context.contentResolver.query(uri!!, strArr, null, null, null)
        var str: String? = null
        if (query != null) {
            if (query.moveToFirst()) {
                str = query.getString(query.getColumnIndexOrThrow(strArr[0]))
            }
            query.close()
        }
        return str ?: "Not found"
    }
}