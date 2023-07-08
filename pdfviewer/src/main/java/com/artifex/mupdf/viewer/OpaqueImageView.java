package com.artifex.mupdf.viewer;

import android.content.Context;
import android.widget.ImageView;


class OpaqueImageView extends ImageView {
    public boolean isOpaque() {
        return true;
    }

    public OpaqueImageView(Context context) {
        super(context);
    }
}
