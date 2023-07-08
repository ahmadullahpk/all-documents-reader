package com.artifex.mupdf.fitz;

public interface PathWalker {
   void moveTo(float var1, float var2);

   void lineTo(float var1, float var2);

   void curveTo(float var1, float var2, float var3, float var4, float var5, float var6);

   void closePath();
}
