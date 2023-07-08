package com.artifex.mupdf.fitz;

public class ColorSpace {
   private long pointer;
   public static ColorSpace DeviceGray;
   public static ColorSpace DeviceRGB;
   public static ColorSpace DeviceBGR;
   public static ColorSpace DeviceCMYK;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private ColorSpace(long p) {
      this.pointer = p;
   }

   private static native long nativeDeviceGray();

   private static native long nativeDeviceRGB();

   private static native long nativeDeviceBGR();

   private static native long nativeDeviceCMYK();

   protected static ColorSpace fromPointer(long p) {
      if (p == DeviceGray.pointer) {
         return DeviceGray;
      } else if (p == DeviceRGB.pointer) {
         return DeviceRGB;
      } else if (p == DeviceBGR.pointer) {
         return DeviceBGR;
      } else {
         return p == DeviceCMYK.pointer ? DeviceCMYK : new ColorSpace(p);
      }
   }

   public native int getNumberOfComponents();

   public String toString() {
      if (this == DeviceGray) {
         return "DeviceGray";
      } else if (this == DeviceRGB) {
         return "DeviceRGB";
      } else if (this == DeviceBGR) {
         return "DeviceBGR";
      } else {
         return this == DeviceCMYK ? "DeviceCMYK" : "ColorSpace(" + this.getNumberOfComponents() + ")";
      }
   }

   static {
      Context.init();
      DeviceGray = new ColorSpace(nativeDeviceGray());
      DeviceRGB = new ColorSpace(nativeDeviceRGB());
      DeviceBGR = new ColorSpace(nativeDeviceBGR());
      DeviceCMYK = new ColorSpace(nativeDeviceCMYK());
   }
}
