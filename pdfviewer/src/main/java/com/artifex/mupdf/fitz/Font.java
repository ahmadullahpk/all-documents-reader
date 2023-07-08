package com.artifex.mupdf.fitz;

public class Font {
   public static final int LATIN = 0;
   public static final int GREEK = 1;
   public static final int CYRILLIC = 2;
   public static final int ADOBE_CNS = 0;
   public static final int ADOBE_GB = 1;
   public static final int ADOBE_JAPAN = 2;
   public static final int ADOBE_KOREA = 3;
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative(String var1, int var2);

   private Font(long p) {
      this.pointer = p;
   }

   public Font(String name, int index) {
      this.pointer = this.newNative(name, index);
   }

   public Font(String name) {
      this.pointer = this.newNative(name, 0);
   }

   public native String getName();

   public native int encodeCharacter(int var1);

   public native float advanceGlyph(int var1, boolean var2);

   public float advanceGlyph(int glyph) {
      return this.advanceGlyph(glyph, false);
   }

   public String toString() {
      return "Font(" + this.getName() + ")";
   }

   static {
      Context.init();
   }
}
