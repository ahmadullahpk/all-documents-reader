package com.ahmadullahpk.alldocumentreader.manageui

import android.graphics.drawable.GradientDrawable

class GradientStrokeDrawable(val gradientDrawable: GradientDrawable) {
    fun setGradientCornerRadius(angle: Float) {
        gradientDrawable.cornerRadius = angle
    }

    fun setGradient(i: Int, i2: Int, i3: Int) {
        gradientDrawable.colors = intArrayOf(i, i2)
        gradientDrawable.orientation =
            if (i3 == 0) GradientDrawable.Orientation.LEFT_RIGHT else GradientDrawable.Orientation.TOP_BOTTOM
    }
}