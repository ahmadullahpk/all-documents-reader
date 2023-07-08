package com.artifex.mupdf.fitz;

public final class DrawDevice extends NativeDevice {
   private static native long newNative(Pixmap var0);

   public DrawDevice(Pixmap pixmap) {
      super(newNative(pixmap));
   }

   static {
      Context.init();
   }
}
