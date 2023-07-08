package com.artifex.mupdf.fitz;

public class DisplayList {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative(Rect var1);

   public DisplayList(Rect mediabox) {
      this.pointer = this.newNative(mediabox);
   }

   private DisplayList(long p) {
      this.pointer = p;
   }

   public native Pixmap toPixmap(Matrix var1, ColorSpace var2, boolean var3);

   public native StructuredText toStructuredText(String var1);

   public StructuredText toStructuredText() {
      return this.toStructuredText((String)null);
   }

   public native Quad[] search(String var1);

   public native void run(Device var1, Matrix var2, Rect var3, Cookie var4);

   public void run(Device dev, Matrix ctm, Cookie cookie) {
      this.run(dev, ctm, (Rect)null, cookie);
   }

   static {
      Context.init();
   }
}
