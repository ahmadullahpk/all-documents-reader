package com.artifex.mupdf.fitz;

public final class ColorParams {
   public static final int BP = 32;
   public static final int OP = 64;
   public static final int OPM = 128;

   public static RenderingIntent RI(int flags) {
      switch(flags & 3) {
      case 0:
      default:
         return RenderingIntent.PERCEPTUAL;
      case 1:
         return RenderingIntent.RELATIVE_COLORIMETRIC;
      case 2:
         return RenderingIntent.SATURATION;
      case 3:
         return RenderingIntent.ABSOLUTE_COLORIMETRIC;
      }
   }

   public static boolean BP(int flags) {
      return (flags & 32) != 0;
   }

   public static boolean OP(int flags) {
      return (flags & 64) != 0;
   }

   public static boolean OPM(int flags) {
      return (flags & 128) != 0;
   }

   public static int pack(RenderingIntent ri, boolean bp, boolean op, boolean opm) {
      int flags;
      switch(ri) {
      case PERCEPTUAL:
      default:
         flags = 0;
         break;
      case RELATIVE_COLORIMETRIC:
         flags = 1;
         break;
      case SATURATION:
         flags = 2;
         break;
      case ABSOLUTE_COLORIMETRIC:
         flags = 3;
      }

      if (bp) {
         flags |= 32;
      }

      if (op) {
         flags |= 64;
      }

      if (opm) {
         flags |= 128;
      }

      return flags;
   }

   public static enum RenderingIntent {
      PERCEPTUAL,
      RELATIVE_COLORIMETRIC,
      SATURATION,
      ABSOLUTE_COLORIMETRIC;
   }
}
