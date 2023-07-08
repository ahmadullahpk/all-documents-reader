package com.artifex.mupdf.fitz;

public class Text implements TextWalker {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative();

   private Text(long p) {
      this.pointer = p;
   }

   public Text() {
      this.pointer = this.newNative();
   }

   public native void showGlyph(Font var1, Matrix var2, int var3, int var4, boolean var5);

   public native void showString(Font var1, Matrix var2, String var3, boolean var4);

   public native Rect getBounds(StrokeState var1, Matrix var2);

   public void showGlyph(Font font, Matrix trm, int glyph, int unicode) {
      this.showGlyph(font, trm, glyph, unicode, false);
   }

   public void showString(Font font, Matrix trm, String str) {
      this.showString(font, trm, str, false);
   }

   public native void walk(TextWalker var1);

   static {
      Context.init();
   }
}
