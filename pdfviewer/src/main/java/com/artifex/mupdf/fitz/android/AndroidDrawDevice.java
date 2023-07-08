package com.artifex.mupdf.fitz.android;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.artifex.mupdf.fitz.Context;
import com.artifex.mupdf.fitz.Cookie;
import com.artifex.mupdf.fitz.Matrix;
import com.artifex.mupdf.fitz.NativeDevice;
import com.artifex.mupdf.fitz.Page;
import com.artifex.mupdf.fitz.Rect;
import com.artifex.mupdf.fitz.RectI;

public final class AndroidDrawDevice extends NativeDevice {
   private native long newNative(Bitmap var1, int var2, int var3, int var4, int var5, int var6, int var7);

   public AndroidDrawDevice(Bitmap bitmap, int xOrigin, int yOrigin, int patchX0, int patchY0, int patchX1, int patchY1) {
      super(0L);
      this.pointer = this.newNative(bitmap, xOrigin, yOrigin, patchX0, patchY0, patchX1, patchY1);
   }

   public AndroidDrawDevice(Bitmap bitmap, int xOrigin, int yOrigin) {
      this(bitmap, xOrigin, yOrigin, 0, 0, bitmap.getWidth(), bitmap.getHeight());
   }

   public AndroidDrawDevice(Bitmap bitmap) {
      this(bitmap, 0, 0);
   }

   public static Bitmap drawPage(Page page, Matrix ctm) {
      Rect fbox = page.getBounds().transform(ctm);
      RectI ibox = new RectI((int)fbox.x0, (int)fbox.y0, (int)fbox.x1, (int)fbox.y1);
      int w = ibox.x1 - ibox.x0;
      int h = ibox.y1 - ibox.y0;
      Bitmap bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
      AndroidDrawDevice dev = new AndroidDrawDevice(bmp, ibox.x0, ibox.y0);
      page.run(dev, ctm, (Cookie)null);
      dev.close();
      dev.destroy();
      return bmp;
   }

   public static Bitmap drawPage(Page page, float dpi, int rotate) {
      return drawPage(page, (new Matrix(dpi / 72.0F)).rotate((float)rotate));
   }

   public static Bitmap drawPage(Page page, float dpi) {
      return drawPage(page, new Matrix(dpi / 72.0F));
   }

   public static Matrix fitPage(Page page, int fitW, int fitH) {
      Rect bbox = page.getBounds();
      float pageW = bbox.x1 - bbox.x0;
      float pageH = bbox.y1 - bbox.y0;
      float scaleH = (float)fitW / pageW;
      float scaleV = (float)fitH / pageH;
      float scale = scaleH < scaleV ? scaleH : scaleV;
      scaleH = (float)Math.floor((double)(pageW * scale)) / pageW;
      scaleV = (float)Math.floor((double)(pageH * scale)) / pageH;
      return new Matrix(scaleH, scaleV);
   }

   public static Bitmap drawPageFit(Page page, int fitW, int fitH) {
      return drawPage(page, fitPage(page, fitW, fitH));
   }

   public static Matrix fitPageWidth(Page page, int fitW) {
      Rect bbox = page.getBounds();
      float pageW = bbox.x1 - bbox.x0;
      float scale = (float)fitW / pageW;
      scale = (float)Math.floor((double)(pageW * scale)) / pageW;
      return new Matrix(scale);
   }

   public static Bitmap drawPageFitWidth(Page page, int fitW) {
      return drawPage(page, fitPageWidth(page, fitW));
   }

   static {
      Context.init();
   }
}
