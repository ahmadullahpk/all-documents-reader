package com.ahmadullah.alldocumentsreader

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.ahmadullah.alldocumentsreader.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        

        binding.rlWord.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "doc")
            startActivity(intent)
        })

        binding.rlExcel.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "xls")
            startActivity(intent)
        })

        binding.rlPpt.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "ppt")
            startActivity(intent)
        })


        binding.rlPdf.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "pdf")
            startActivity(intent)
        })

        binding.rlRtf.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "rtf")
            startActivity(intent)
        })
        binding.rlCsv.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "csv")
            startActivity(intent)
        })


        binding.rlJson.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "json")
            startActivity(intent)
        })



        binding.rlXml.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "xml")
            startActivity(intent)
        })

        binding.rlJava.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "java")
            startActivity(intent)
        })

        binding.rlKotlin.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "kt")
            startActivity(intent)
        })

        binding.rlHtml.setOnClickListener(View.OnClickListener {
            var intent = Intent(this@MainActivity, List_Files_Activity::class.java)
            intent.putExtra("format", "html")
            startActivity(intent)
        })



    }


    private fun dialog_exit() {
        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        val txt_yes = dialog.findViewById<TextView>(R.id.txt_yes)
        val txt_no = dialog.findViewById<TextView>(R.id.txt_no)
        txt_yes.setOnClickListener {
            dialog.dismiss()
            finishAffinity()
        }
        txt_no.setOnClickListener {
            dialog.dismiss()
        }
        if (dialog != null) {
            dialog.show()
        }
    }

    override fun onBackPressed() {
        dialog_exit()
    }



}