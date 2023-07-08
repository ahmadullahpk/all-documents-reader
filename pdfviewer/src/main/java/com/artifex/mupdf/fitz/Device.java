package com.artifex.mupdf.fitz;

public abstract class Device {
   protected long pointer;
   public static final int BLEND_NORMAL = 0;
   public static final int BLEND_MULTIPLY = 1;
   public static final int BLEND_SCREEN = 2;
   public static final int BLEND_OVERLAY = 3;
   public static final int BLEND_DARKEN = 4;
   public static final int BLEND_LIGHTEN = 5;
   public static final int BLEND_COLOR_DODGE = 6;
   public static final int BLEND_COLOR_BURN = 7;
   public static final int BLEND_HARD_LIGHT = 8;
   public static final int BLEND_SOFT_LIGHT = 9;
   public static final int BLEND_DIFFERENCE = 10;
   public static final int BLEND_EXCLUSION = 11;
   public static final int BLEND_HUE = 12;
   public static final int BLEND_SATURATION = 13;
   public static final int BLEND_COLOR = 14;
   public static final int BLEND_LUMINOSITY = 15;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative();

   protected Device() {
      this.pointer = this.newNative();
   }

   protected Device(long p) {
      this.pointer = p;
   }

   public abstract void close();

   public abstract void fillPath(Path var1, boolean var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public abstract void strokePath(Path var1, StrokeState var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public abstract void clipPath(Path var1, boolean var2, Matrix var3);

   public abstract void clipStrokePath(Path var1, StrokeState var2, Matrix var3);

   public abstract void fillText(Text var1, Matrix var2, ColorSpace var3, float[] var4, float var5, int var6);

   public abstract void strokeText(Text var1, StrokeState var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public abstract void clipText(Text var1, Matrix var2);

   public abstract void clipStrokeText(Text var1, StrokeState var2, Matrix var3);

   public abstract void ignoreText(Text var1, Matrix var2);

   public abstract void fillShade(Shade var1, Matrix var2, float var3, int var4);

   public abstract void fillImage(Image var1, Matrix var2, float var3, int var4);

   public abstract void fillImageMask(Image var1, Matrix var2, ColorSpace var3, float[] var4, float var5, int var6);

   public abstract void clipImageMask(Image var1, Matrix var2);

   public abstract void popClip();

   public abstract void beginMask(Rect var1, boolean var2, ColorSpace var3, float[] var4, int var5);

   public abstract void endMask();

   public abstract void beginGroup(Rect var1, ColorSpace var2, boolean var3, boolean var4, int var5, float var6);

   public abstract void endGroup();

   public abstract int beginTile(Rect var1, Rect var2, float var3, float var4, Matrix var5, int var6);

   public abstract void endTile();

   public abstract void beginLayer(String var1);

   public abstract void endLayer();

   static {
      Context.init();
   }
}
