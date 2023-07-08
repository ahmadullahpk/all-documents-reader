package com.ahmadullahpk.alldocumentreader.manageui.backgroundshadow

import android.view.View
import androidx.core.view.ViewCompat

object ShadowSetup {
    fun setUpShadow(view: View, builder: ShadowOpacity.Builder) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        ViewCompat
            .setBackground(view, builder.build())
    }
}