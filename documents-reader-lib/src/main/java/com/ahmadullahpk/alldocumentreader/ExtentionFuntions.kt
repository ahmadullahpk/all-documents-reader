package com.ahmadullahpk.alldocumentreader

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toasty(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}