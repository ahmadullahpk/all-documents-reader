package com.artifex.mupdf.viewer;

import android.app.ProgressDialog;
import android.content.Context;


class ProgressDialogX extends ProgressDialog {
    private boolean mCancelled = false;

    public ProgressDialogX(Context context) {
        super(context);
    }

    public boolean isCancelled() {
        return this.mCancelled;
    }

    public void cancel() {
        this.mCancelled = true;
        super.cancel();
    }
}
