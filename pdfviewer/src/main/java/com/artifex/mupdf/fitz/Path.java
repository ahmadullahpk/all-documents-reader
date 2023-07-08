package com.artifex.mupdf.fitz;

public class Path implements PathWalker {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative();

   private native long cloneNative();

   public Path() {
      this.pointer = this.newNative();
   }

   private Path(long p) {
      this.pointer = p;
   }

   public Path(Path old) {
      this.pointer = old.cloneNative();
   }

   public native Point currentPoint();

   public native void moveTo(float var1, float var2);

   public native void lineTo(float var1, float var2);

   public native void curveTo(float var1, float var2, float var3, float var4, float var5, float var6);

   public native void curveToV(float var1, float var2, float var3, float var4);

   public native void curveToY(float var1, float var2, float var3, float var4);

   public native void rect(int var1, int var2, int var3, int var4);

   public native void closePath();

   public void moveTo(Point xy) {
      this.moveTo(xy.x, xy.y);
   }

   public void lineTo(Point xy) {
      this.lineTo(xy.x, xy.y);
   }

   public void curveTo(Point c1, Point c2, Point e) {
      this.curveTo(c1.x, c1.y, c2.x, c2.y, e.x, e.y);
   }

   public void curveToV(Point c, Point e) {
      this.curveToV(c.x, c.y, e.x, e.y);
   }

   public void curveToY(Point c, Point e) {
      this.curveToY(c.x, c.y, e.x, e.y);
   }

   public native void transform(Matrix var1);

   public native Rect getBounds(StrokeState var1, Matrix var2);

   public native void walk(PathWalker var1);

   static {
      Context.init();
   }
}
