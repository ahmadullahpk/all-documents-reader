package com.artifex.mupdf.fitz;

public class Document {
   public static final String META_FORMAT = "format";
   public static final String META_ENCRYPTION = "encryption";
   public static final String META_INFO_AUTHOR = "info:Author";
   public static final String META_INFO_TITLE = "info:Title";
   protected long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   protected Document(long p) {
      this.pointer = p;
   }

   protected static native Document openNativeWithPath(String var0);

   protected static native Document openNativeWithBuffer(byte[] var0, String var1);

   protected static native Document openNativeWithStream(SeekableInputStream var0, String var1);

   public static Document openDocument(String filename) {
      return openNativeWithPath(filename);
   }

   public static Document openDocument(byte[] buffer, String magic) {
      return openNativeWithBuffer(buffer, magic);
   }

   public static Document openDocument(SeekableInputStream stream, String magic) {
      return openNativeWithStream(stream, magic);
   }

   public static native boolean recognize(String var0);

   public native boolean needsPassword();

   public native boolean authenticatePassword(String var1);

   public native int countPages();

   public native Page loadPage(int var1);

   public native Outline[] loadOutline();

   public native String getMetaData(String var1);

   public native boolean isReflowable();

   public native void layout(float var1, float var2, float var3);

   public native long makeBookmark(int var1);

   public native int findBookmark(long var1);

   public native boolean isUnencryptedPDF();

   public boolean isPDF() {
      return false;
   }

   static {
      Context.init();
   }
}
