package com.artifex.mupdf.fitz;

public class NativeDevice extends Device {
   private long nativeInfo;
   private Object nativeResource;

   protected native void finalize();

   public void destroy() {
      super.destroy();
      this.nativeInfo = 0L;
      this.nativeResource = null;
   }

   protected NativeDevice(long p) {
      super(p);
   }

   public final native void close();

   public final native void fillPath(Path var1, boolean var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public final native void strokePath(Path var1, StrokeState var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public final native void clipPath(Path var1, boolean var2, Matrix var3);

   public final native void clipStrokePath(Path var1, StrokeState var2, Matrix var3);

   public final native void fillText(Text var1, Matrix var2, ColorSpace var3, float[] var4, float var5, int var6);

   public final native void strokeText(Text var1, StrokeState var2, Matrix var3, ColorSpace var4, float[] var5, float var6, int var7);

   public final native void clipText(Text var1, Matrix var2);

   public final native void clipStrokeText(Text var1, StrokeState var2, Matrix var3);

   public final native void ignoreText(Text var1, Matrix var2);

   public final native void fillShade(Shade var1, Matrix var2, float var3, int var4);

   public final native void fillImage(Image var1, Matrix var2, float var3, int var4);

   public final native void fillImageMask(Image var1, Matrix var2, ColorSpace var3, float[] var4, float var5, int var6);

   public final native void clipImageMask(Image var1, Matrix var2);

   public final native void popClip();

   public final native void beginMask(Rect var1, boolean var2, ColorSpace var3, float[] var4, int var5);

   public final native void endMask();

   public final native void beginGroup(Rect var1, ColorSpace var2, boolean var3, boolean var4, int var5, float var6);

   public final native void endGroup();

   public final native int beginTile(Rect var1, Rect var2, float var3, float var4, Matrix var5, int var6);

   public final native void endTile();

   public final native void beginLayer(String var1);

   public final native void endLayer();

   static {
      Context.init();
   }
}
