package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class CustomViewPager(context: Context?, attributeSet: AttributeSet?) : ViewPager(
    context!!, attributeSet
) {
    var mStartDragX = 0f
    private var onSwipeOutListener: OnSwipeOutListener? = null

    interface OnSwipeOutListener {
        fun onSwipeOutAtStart()
        fun onSwipeOutAtEnd()
    }

    @JvmName("setOnSwipeOutListener1")
    fun setOnSwipeOutListener(onSwipeOutListener: OnSwipeOutListener?) {
        this.onSwipeOutListener = onSwipeOutListener
    }

    private fun onSwipeOutAtStart() {
        val onSwipeOutListener = onSwipeOutListener
        onSwipeOutListener?.onSwipeOutAtStart()
    }

    private fun onSwipeOutAtEnd() {
        val onSwipeOutListener = onSwipeOutListener
        onSwipeOutListener?.onSwipeOutAtEnd()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action and 255 == 0) {
            mStartDragX = ev.x
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (currentItem == 0 || currentItem == adapter!!.count - 1) {
            val action = ev.action
            val x = ev.x
            if (action and 255 == 1) {
                if (currentItem == 0 && x > mStartDragX) {
                    onSwipeOutAtStart()
                }
                if (currentItem == adapter!!.count - 1 && x < mStartDragX) {
                    onSwipeOutAtEnd()
                }
            }
        } else {
            mStartDragX = INT_BITS_TO_FLOAT
        }
        return super.onTouchEvent(ev)
    }

    companion object {
        val INT_BITS_TO_FLOAT = java.lang.Float.intBitsToFloat(1)
    }
}