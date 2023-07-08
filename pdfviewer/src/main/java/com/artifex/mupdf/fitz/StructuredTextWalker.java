package com.artifex.mupdf.fitz;

public interface StructuredTextWalker {
   void onImageBlock(Rect var1, Matrix var2, Image var3);

   void beginTextBlock(Rect var1);

   void endTextBlock();

   void beginLine(Rect var1, int var2);

   void endLine();

   void onChar(int var1, Point var2, Font var3, float var4, Quad var5);
}
