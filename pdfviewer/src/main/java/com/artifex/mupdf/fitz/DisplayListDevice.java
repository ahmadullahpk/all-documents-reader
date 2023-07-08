package com.artifex.mupdf.fitz;

public final class DisplayListDevice extends NativeDevice {
   private static native long newNative(DisplayList var0);

   public DisplayListDevice(DisplayList list) {
      super(newNative(list));
   }
}
