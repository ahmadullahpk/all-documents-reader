package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import com.ahmadullahpk.alldocumentreader.R
import android.graphics.Typeface
import android.util.AttributeSet
import com.ahmadullahpk.alldocumentreader.util.FontCache

open class CustomFontTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        if (!isInEditMode) {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.AppTextView, 0, 0)
            try {
                setFont(
                    context,
                    obtainStyledAttributes.getInt(R.styleable.AppTextView_textStyle, 0)
                )
            } finally {
                obtainStyledAttributes.recycle()
            }
        }
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet, i
    ) {
    }

    fun setFont(context: Context, i: Int) {
        val typeface: Typeface = when (i) {
            0 -> FontCache.setFontStyle(context, "fonts/gothamssm_bold.otf")
            1 -> FontCache.setFontStyle(context, "fonts/gothamssm_light.otf")
            2 -> FontCache.setFontStyle(context, "fonts/gothamssm_medium.otf")
            4 -> FontCache.setFontStyle(context, "fonts/gothamssm_black.otf")
            5 -> Typeface.create(
                FontCache.setFontStyle(context, "fonts/gothamssm_book.otf"),
                Typeface.ITALIC
            )
            3 -> FontCache.setFontStyle(context, "fonts/gothamssm_book.otf")
            else -> FontCache.setFontStyle(context, "fonts/gothamssm_book.otf")
        }
        setTypeface(typeface)
    }
}