package com.artifex.mupdf.fitz;

public class Buffer {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private native long newNativeBuffer(int var1);

   public Buffer(int n) {
      this.pointer = this.newNativeBuffer(n);
   }

   public Buffer() {
      this.pointer = this.newNativeBuffer(0);
   }

   public native int getLength();

   public native int readByte(int var1);

   public native int readBytes(int var1, byte[] var2);

   public native int readBytesInto(int var1, byte[] var2, int var3, int var4);

   public native void writeByte(byte var1);

   public native void writeBytes(byte[] var1);

   public native void writeBytesFrom(byte[] var1, int var2, int var3);

   public native void writeBuffer(Buffer var1);

   public native void writeRune(int var1);

   public native void writeLine(String var1);

   public native void writeLines(String... var1);

   public native void save(String var1);

   static {
      Context.init();
   }
}
