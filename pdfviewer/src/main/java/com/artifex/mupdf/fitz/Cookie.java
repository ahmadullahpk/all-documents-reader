package com.artifex.mupdf.fitz;

public class Cookie {
   private long pointer = this.newNative();

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNative();

   public native void abort();

   static {
      Context.init();
   }
}
