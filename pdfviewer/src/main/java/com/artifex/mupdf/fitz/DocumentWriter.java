package com.artifex.mupdf.fitz;

public class DocumentWriter {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNativeDocumentWriter(String var1, String var2, String var3);

   public DocumentWriter(String filename, String format, String options) {
      this.pointer = this.newNativeDocumentWriter(filename, format, options);
   }

   public native Device beginPage(Rect var1);

   public native void endPage();

   public native void close();

   static {
      Context.init();
   }
}
