package com.artifex.mupdf.fitz.android;

import android.graphics.Bitmap;
import com.artifex.mupdf.fitz.Context;
import com.artifex.mupdf.fitz.Image;

public final class AndroidImage extends Image {
   private native long newAndroidImageFromBitmap(Bitmap var1, long var2);

   public AndroidImage(Bitmap bitmap, AndroidImage mask) {
      super(0L);
      this.pointer = this.newAndroidImageFromBitmap(bitmap, mask.pointer);
   }

   static {
      Context.init();
   }
}
