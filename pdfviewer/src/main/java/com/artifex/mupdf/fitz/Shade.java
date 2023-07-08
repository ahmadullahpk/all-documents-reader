package com.artifex.mupdf.fitz;

public class Shade {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private Shade(long p) {
      this.pointer = p;
   }

   static {
      Context.init();
   }
}
