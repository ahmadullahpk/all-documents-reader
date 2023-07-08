package com.ahmadullahpk.alldocumentreader.manageui.backgroundshadow

import android.graphics.LinearGradient
import com.ahmadullahpk.alldocumentreader.R

class ShadowOpacity {
    class Builder {
        private var mColor: Int
        private var mShadowColor: Int
        private var shadowColor: Int
        private var mGradientColorArray: IntArray? = null
        private var mGradientPositions: FloatArray? = null
        private val mLinearGradient: LinearGradient? = null
        private var mRadius: Int
        private var mShadowRadius: Int
        private var mOffsetX: Int
        private var mOffsetY: Int
        fun setShadowColor(i: Int): Builder {
            mShadowColor = i
            return this
        }

        fun setColor(i: Int): Builder {
            mColor = i
            return this
        }

        fun setOShadowColor(i: Int): Builder {
            if (i != -1) {
                shadowColor = i
            }
            return this
        }

        fun setGradientColorArray(iArr: IntArray): Builder {
            mGradientColorArray = iArr
            return this
        }

        fun setRadius(i: Int): Builder {
            mRadius = i
            return this
        }

        fun setShadowRadius(i: Int): Builder {
            mShadowRadius = i
            return this
        }

        fun setOffsetX(i: Int): Builder {
            mOffsetX = i
            return this
        }

        fun setOffsetY(i: Int): Builder {
            mOffsetY = i
            return this
        }

        fun build(): CustomBackgroundShadow {
            return CustomBackgroundShadow(
                mColor,
                mGradientColorArray,
                mGradientPositions,
                shadowColor,
                mLinearGradient,
                mRadius,
                mShadowRadius,
                mOffsetX,
                mOffsetY,
                mShadowColor
            )
        }

        init {
            mShadowColor = -1
            mOffsetX = 0
            mOffsetY = 0
            mColor = R.color.primary_material_dark
            shadowColor = R.color.card_default_shadow_color
            mRadius = 10
            mShadowRadius = 16
            mOffsetX = 0
            mOffsetY = 0
            mShadowColor = R.color.card_default_shadow_color
            mShadowColor = -1
        }
    }
}