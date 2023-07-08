package com.ahmadullahpk.alldocumentreader.manageui.backgroundshadow

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import android.graphics.Shader.TileMode

class CustomBackgroundShadow(
    @field:ColorInt private val mColor: Int,
    private val mGradientColorArrays: IntArray?,
    private val mGradientPositions: FloatArray?,
    @field:ColorInt private val mShadowColor: Int,
    private val mLinearGradient: LinearGradient?,
    private val mShadowRadius: Int,
    private val mOffsetX: Int,
    private val mOffsetY: Int,
    private val offset: Int,
    private val mRadius: Int
) : Drawable() {
    private var mRectF: RectF? = null
    private var mPaint: Paint? = null
    private var paint: Paint? = null
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun draw(canvas: Canvas) {
        if (mRectF == null) {
            val bounds = bounds
            mRectF = RectF(
                (bounds.left + mOffsetX - mOffsetY).toFloat(),
                (bounds.top + mOffsetX - offset).toFloat(),
                (bounds.right - mOffsetX - mOffsetY).toFloat(),
                (bounds.bottom - mOffsetX - offset).toFloat()
            )
        }
        if (mPaint == null) {
            defaultGradient
        }
        val rectF = mRectF
        val i = mShadowRadius
        canvas.drawRoundRect(rectF!!, i.toFloat(), i.toFloat(), mPaint!!)
        if (mRadius != -1) {
            if (paint == null) {
                initPaint()
            }
            val rectF2 = mRectF
            val i2 = mShadowRadius
            canvas.drawRoundRect(rectF2!!, i2.toFloat(), i2.toFloat(), paint!!)
        }
    }

    private fun initPaint() {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.STROKE
        paint!!.color = mRadius
        paint!!.strokeWidth = setDisplayMetrics(1).toFloat()
    }

    override fun setAlpha(i: Int) {
        mPaint!!.alpha = i
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint!!.colorFilter = colorFilter
    }

    private val defaultGradient: Unit
        private get() {
            mPaint = Paint()
            var z = true
            mPaint!!.isAntiAlias = true
            mPaint!!.setShadowLayer(
                mOffsetX.toFloat(),
                mOffsetY.toFloat(),
                offset.toFloat(),
                mShadowColor
            )
            val iArr = mGradientColorArrays
            if (iArr == null || iArr.size <= 1) {
                mPaint!!.color = mColor
                return
            }
            val fArr = mGradientPositions
            if (fArr == null || fArr.size <= 0 || fArr.size != iArr.size) {
                z = false
            }
            val paint = mPaint
            var linearGradient = mLinearGradient
            if (linearGradient == null) {
                linearGradient = LinearGradient(
                    mRectF!!.left,
                    java.lang.Float.intBitsToFloat(1),
                    mRectF!!.right,
                    java.lang.Float.intBitsToFloat(1),
                    mGradientColorArrays!!,
                    if (z) mGradientPositions else null,
                    TileMode.CLAMP
                )
            }
            paint!!.shader = linearGradient
        }

    companion object {
        fun setDisplayMetrics(i: Int): Int {
            return (i.toFloat() * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}