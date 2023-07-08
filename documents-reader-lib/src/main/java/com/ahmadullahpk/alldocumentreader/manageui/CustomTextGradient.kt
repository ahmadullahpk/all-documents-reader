package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.ahmadullahpk.alldocumentreader.R

class CustomTextGradient : CustomFontTextView {
    private var startColor = 0
    private var endColor = 0
    private var gradientType = 0

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        if (!isInEditMode) {
            val obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R.styleable.gradientTextView, 0, 0)
            try {
                startColor = ContextCompat.getColor(
                    getContext(),
                    obtainStyledAttributes.getResourceId(
                        R.styleable.gradientTextView_startColor,
                        R.color.defaultStartColor
                    )
                )
                endColor = ContextCompat.getColor(
                    getContext(),
                    obtainStyledAttributes.getResourceId(
                        R.styleable.gradientTextView_endColor,
                        R.color.defaultEndColor
                    )
                )
                gradientType =
                    obtainStyledAttributes.getInt(R.styleable.gradientTextView_gradientType, 0)
            } finally {
                obtainStyledAttributes.recycle()
            }
        }
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context,
        attributeSet,
        i
    )

    fun setGradient(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
        init()
        invalidate()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            init()
        }
    }

    private fun init() {
        val f: Float
        val f2: Float
        val measureText = paint.measureText(text.toString())
        if (gradientType == 0) {
            f2 = measureText
            f = java.lang.Float.intBitsToFloat(1)
        } else {
            f = height.toFloat()
            f2 = java.lang.Float.intBitsToFloat(1)
        }
        setTextColor(ContextCompat.getColor(context, android.R.color.black))
        val paint = paint
        val linearGradient = LinearGradient(
            java.lang.Float.intBitsToFloat(1),
            java.lang.Float.intBitsToFloat(1),
            f2,
            f,
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
        paint.shader = linearGradient
    }
}