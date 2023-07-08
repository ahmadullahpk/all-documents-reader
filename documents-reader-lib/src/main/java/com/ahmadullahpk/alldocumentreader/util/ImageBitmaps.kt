package com.ahmadullahpk.alldocumentreader.util

import android.graphics.*
import android.graphics.Shader.TileMode

object ImageBitmaps {
    fun setBitmapGradient(bitmap: Bitmap, startColor: Int, endColor: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(createBitmap)
        canvas.drawBitmap(
            bitmap,
            java.lang.Float.intBitsToFloat(1),
            java.lang.Float.intBitsToFloat(1),
            null
        )
        val paint = Paint()
        val f = width.toFloat()
        val linearGradient = LinearGradient(
            java.lang.Float.intBitsToFloat(1),
            java.lang.Float.intBitsToFloat(1),
            f,
            java.lang.Float.intBitsToFloat(1),
            startColor,
            endColor,
            TileMode.CLAMP
        )
        paint.shader = linearGradient
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawRect(
            java.lang.Float.intBitsToFloat(1),
            java.lang.Float.intBitsToFloat(1),
            f,
            height.toFloat(),
            paint
        )
        return createBitmap
    }
}