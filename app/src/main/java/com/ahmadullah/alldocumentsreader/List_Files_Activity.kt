package com.ahmadullah.alldocumentsreader

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.io.File

class List_Files_Activity : AppCompatActivity() {


    var adapter: Adapter_Doc_Files? = null
    var itemsList: ArrayList<model_doc_File> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var rl_no_files: RelativeLayout
    lateinit var txt_file_name: TextView
    val REQ_FILE_ACCESS = 444
    var file_format = "doc"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_files)


        rl_no_files = findViewById(R.id.rl_no_files)
        txt_file_name = findViewById(R.id.txt_file_name)
        recyclerView = findViewById(R.id.recyclerView)

         file_format = intent.getStringExtra("format").toString()
         txt_file_name.setText("All " + file_format + " Files")

        if(checkPermission()){

        }else{
            requestPermission()
        }


    }

    private fun setAdapter(itemsList: ArrayList<model_doc_File>) {
        recyclerView!!.visibility = View.VISIBLE
        recyclerView!!.setHasFixedSize(true)
        adapter = Adapter_Doc_Files(
            this@List_Files_Activity,
            itemsList
        )
        recyclerView!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
        if (itemsList.size < 1) {
            rl_no_files?.setVisibility(View.VISIBLE)
        } else {
            rl_no_files?.setVisibility(View.GONE)
        }
    }

    fun fileListDocx(file_format: String?): ArrayList<model_doc_File>? {
        val path: String = Environment.getExternalStorageDirectory().getPath()
        val documentArrayList: ArrayList<model_doc_File> = ArrayList()
        var format1 = ""
        var format2 = ""
        if (file_format.equals("doc")) {
            format1 = "docx"
            format2 = "doc"
        }
        else if (file_format.equals("xls")) {
            format1 = "xlsx"
            format2 = "xls"
        }

        else if (file_format.equals("ppt")) {
            format1 = "pptx"
            format2 = "ppt"
        }

        else if (file_format.equals("pdf")) {
            format1 = "pdf"
            format2 = "PDF"
        }

        else if (file_format.equals("rtf")) {
            format1 = "rtf"
            format2 = "RTF"
        }

        else if (file_format.equals("csv")) {
            format1 = "csv"
            format2 = "CSV"
        }

        else if (file_format.equals("json")) {
            format1 = "json"
            format2 = "JSON"
        }

        else if (file_format.equals("xml")) {
            format1 = "xml"
            format2 = "XML"
        }

        else if (file_format.equals("html")) {
            format1 = "html"
            format2 = "HTML"
        }

        else if (file_format.equals("java")) {
            format1 = "java"
            format2 = "JAVA"
        }

        else if (file_format.equals("kt")) {
            format1 = "kt"
            format2 = "KT"
        }


        val uri: Uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf<String>(MediaStore.Files.FileColumns.DATA)
        val orderBy: String = MediaStore.Files.FileColumns.DATE_TAKEN
        val cursor: Cursor? = this@List_Files_Activity.getContentResolver().query(
            uri, projection, MediaStore.Files.FileColumns.DATA + " like ? ", arrayOf(
                "%$path%"
            ),
            "$orderBy DESC"
        )
        val files: Array<File?> = arrayOfNulls<File>(cursor!!.getCount())
        cursor?.moveToFirst()
        var i = 0
        if (cursor?.getCount() !== 0) {
            do {
                files[i] =
                    File(cursor?.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)))
                if (files[i]!!.getName().endsWith(format1) || files[i]!!.getName()
                        .endsWith(format2)
                ) {
                    val model_document = model_doc_File()
                    model_document.setName(files[i]!!.getName())
                    model_document.setPath(files[i]!!.getPath())
                    documentArrayList.add(model_document)
                    i++
                }
            } while (cursor!!.moveToNext())
        } else {
            Toast.makeText(
                this@List_Files_Activity,
                "No Document Files Present",
                Toast.LENGTH_LONG
            ).show()
        }
        return documentArrayList
    }



    fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val uri = Uri.parse("package:${this.getPackageName()}")
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri)
                startActivityForResult(intent, REQ_FILE_ACCESS)

            } catch (e: Exception) {
                val obj = Intent()
                obj.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(obj, REQ_FILE_ACCESS)
            }
        } else {
            ActivityCompat.requestPermissions(
                this@List_Files_Activity,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQ_FILE_ACCESS
            )
        }
    }


    fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val write =
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            val read = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            (write == PackageManager.PERMISSION_GRANTED
                    && read == PackageManager.PERMISSION_GRANTED)
        }
    }

    override fun onResume() {
        super.onResume()
        if(checkPermission()){
            itemsList = fileListDocx(file_format)!!
            setAdapter(itemsList)
        }else{
            requestPermission()
        }
    }


}