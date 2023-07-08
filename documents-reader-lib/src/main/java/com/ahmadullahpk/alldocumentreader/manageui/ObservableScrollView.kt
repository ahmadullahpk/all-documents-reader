package com.ahmadullahpk.alldocumentreader.manageui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import java.util.ArrayList
import kotlin.math.abs

class ObservableScrollView : NestedScrollView {
    private val viewListenerList = ArrayList<OnScrollViewListener>()
    private val onScrollStateListeners = ArrayList<OnScrollStateListener>()

    interface OnScrollStateListener {
        fun onScroll(observableScrollView: ObservableScrollView?)
    }

    interface OnScrollViewListener {
        fun onScrollChanged(
            observableScrollView: ObservableScrollView?,
            i: Int,
            i2: Int,
            i3: Int,
            i4: Int
        )
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(
        context!!, attributeSet
    ) {
    }

    constructor(context: Context?, attributeSet: AttributeSet?, i: Int) : super(
        context!!, attributeSet, i
    ) {
    }

    override fun onScrollChanged(i: Int, i2: Int, i3: Int, i4: Int) {
        sizeOnScroll(i, i2, i3, i4)
        if (abs(i2 - i4) < 2) {
            sizeOnScroll()
        }
        super.onScrollChanged(i, i2, i3, i4)
    }

    private fun sizeOnScroll() {
        for (size in onScrollStateListeners.indices.reversed()) {
            onScrollStateListeners[size].onScroll(this)
        }
    }

    private fun sizeOnScroll(i: Int, i2: Int, i3: Int, i4: Int) {
        for (size in viewListenerList.indices.reversed()) {
            viewListenerList[size].onScrollChanged(this, i, i2, i3, i4)
        }
    }

    override fun requestChildFocus(view: View, view2: View) {
        super.requestChildFocus(view, view2)
    }
}