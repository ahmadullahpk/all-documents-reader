package com.artifex.mupdf.fitz;

public class Image {
   protected long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNativeFromPixmap(Pixmap var1);

   private native long newNativeFromFile(String var1);

   protected Image(long p) {
      this.pointer = p;
   }

   public Image(Pixmap pixmap) {
      this.pointer = this.newNativeFromPixmap(pixmap);
   }

   public Image(String filename) {
      this.pointer = this.newNativeFromFile(filename);
   }

   public native int getWidth();

   public native int getHeight();

   public native int getXResolution();

   public native int getYResolution();

   public native ColorSpace getColorSpace();

   public native int getNumberOfComponents();

   public native int getBitsPerComponent();

   public native boolean getImageMask();

   public native boolean getInterpolate();

   public native Image getMask();

   public native Pixmap toPixmap();

   static {
      Context.init();
   }
}
