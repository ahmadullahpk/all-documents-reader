package com.ahmadullahpk.alldocumentreader.util

import android.os.Handler
import android.widget.TextView

object TextAnimation {
    @JvmStatic
    fun marqueeAfterDelay(i: Int, textView: TextView) {
        Handler().postDelayed({ textView.isSelected = true }, i.toLong())
    }
}