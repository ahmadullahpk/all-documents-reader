package com.artifex.mupdf.fitz;

public class Context {
   private static boolean inited = false;

   private static native int initNative();

   public static void init() {
      if (!inited) {
         inited = true;

         try {
            System.loadLibrary("mupdf_java");
         } catch (UnsatisfiedLinkError var3) {
            try {
               System.loadLibrary("mupdf_java64");
            } catch (UnsatisfiedLinkError var2) {
               System.loadLibrary("mupdf_java32");
            }
         }

         if (initNative() < 0) {
            throw new RuntimeException("cannot initialize mupdf library");
         }
      }

   }

   static {
      init();
   }
}
