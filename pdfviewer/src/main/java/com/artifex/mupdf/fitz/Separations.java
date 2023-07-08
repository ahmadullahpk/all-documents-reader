package com.artifex.mupdf.fitz;

public class Separations {
   private long pointer;
   public static final int SEPARATION_COMPOSITE = 0;
   public static final int SEPARATION_SPOT = 1;
   public static final int SEPARATION_DISABLED = 2;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   protected Separations(long p) {
      this.pointer = p;
   }

   public native int getNumberOfSeparations();

   public native Separation getSeparation(int var1);

   public native boolean areSeparationsControllable();

   public native int getSeparationBehavior(int var1);

   public native void setSeparationBehavior(int var1, int var2);

   static {
      Context.init();
   }
}
