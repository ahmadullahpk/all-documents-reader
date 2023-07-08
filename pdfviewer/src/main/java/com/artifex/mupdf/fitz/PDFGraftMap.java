package com.artifex.mupdf.fitz;

public class PDFGraftMap {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   public native PDFObject graftObject(PDFObject var1);

   private PDFGraftMap(long p) {
      this.pointer = p;
   }

   static {
      Context.init();
   }
}
