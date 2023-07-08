package com.artifex.mupdf.fitz;

public class Pixmap {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative(ColorSpace var1, int var2, int var3, int var4, int var5, boolean var6);

   private Pixmap(long p) {
      this.pointer = p;
   }

   public Pixmap(ColorSpace cs, int x, int y, int w, int h, boolean alpha) {
      this.pointer = this.newNative(cs, x, y, w, h, alpha);
   }

   public Pixmap(ColorSpace cs, int x, int y, int w, int h) {
      this(cs, x, y, w, h, false);
   }

   public Pixmap(ColorSpace cs, int w, int h, boolean alpha) {
      this(cs, 0, 0, w, h, alpha);
   }

   public Pixmap(ColorSpace cs, int w, int h) {
      this(cs, 0, 0, w, h, false);
   }

   public Pixmap(ColorSpace cs, Rect rect, boolean alpha) {
      this(cs, (int)rect.x0, (int)rect.y0, (int)(rect.x1 - rect.x0), (int)(rect.y1 - rect.y0), alpha);
   }

   public Pixmap(ColorSpace cs, Rect rect) {
      this(cs, rect, false);
   }

   public native void clear();

   private native void clearWithValue(int var1);

   public void clear(int value) {
      this.clearWithValue(value);
   }

   public native void saveAsPNG(String var1);

   public native int getX();

   public native int getY();

   public native int getWidth();

   public native int getHeight();

   public native int getStride();

   public native int getNumberOfComponents();

   public native boolean getAlpha();

   public native ColorSpace getColorSpace();

   public native byte[] getSamples();

   public native byte getSample(int var1, int var2, int var3);

   public native int[] getPixels();

   public native int getXResolution();

   public native int getYResolution();

   public native void invert();

   public native void gamma(float var1);

   public Rect getBounds() {
      int x = this.getX();
      int y = this.getY();
      return new Rect((float)x, (float)y, (float)(x + this.getWidth()), (float)(y + this.getHeight()));
   }

   public String toString() {
      return "Pixmap(w=" + this.getWidth() + " h=" + this.getHeight() + " x=" + this.getX() + " y=" + this.getY() + " n=" + this.getNumberOfComponents() + " alpha=" + this.getAlpha() + " cs=" + this.getColorSpace() + ")";
   }

   static {
      Context.init();
   }
}
