package com.ahmadullahpk.alldocumentreader.util

import android.content.Context
import android.graphics.Typeface

object FontCache {
    private val sCachedFonts: MutableMap<String, Typeface> = HashMap()
    fun setFontStyle(context: Context, str: String): Typeface {
        val createFromAsset = Typeface.createFromAsset(context.assets, str)
        sCachedFonts[str] = createFromAsset
        return createFromAsset
    }
}