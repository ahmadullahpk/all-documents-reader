package com.artifex.mupdf.fitz;

import java.util.ArrayList;

public class StructuredText {
   public static final int SELECT_CHARS = 0;
   public static final int SELECT_WORDS = 1;
   public static final int SELECT_LINES = 2;
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   private StructuredText(long p) {
      this.pointer = p;
   }

   public native Quad[] search(String var1);

   public native Quad[] highlight(Point var1, Point var2);

   public native Quad snapSelection(Point var1, Point var2, int var3);

   public native String copy(Point var1, Point var2);

   public native void walk(StructuredTextWalker var1);

   public TextBlock[] getBlocks() {
      BlockWalker walker = new BlockWalker();
      this.walk(walker);
      return walker.getBlocks();
   }

   static {
      Context.init();
   }

   public class TextChar {
      public int c;
      public Quad quad;

      public boolean isWhitespace() {
         return Character.isWhitespace(this.c);
      }
   }

   public class TextLine {
      public TextChar[] chars;
      public Rect bbox;
   }

   public class TextBlock {
      public TextLine[] lines;
      public Rect bbox;
   }

   class BlockWalker implements StructuredTextWalker {
      ArrayList<TextBlock> blocks = new ArrayList();
      ArrayList<TextLine> lines;
      ArrayList<TextChar> chrs;
      Rect lineBbox;
      Rect blockBbox;

      public void onImageBlock(Rect bbox, Matrix transform, Image image) {
      }

      public void beginTextBlock(Rect bbox) {
         this.lines = new ArrayList();
         this.blockBbox = bbox;
      }

      public void endTextBlock() {
         TextBlock block = StructuredText.this.new TextBlock();
         block.bbox = this.blockBbox;
         block.lines = (TextLine[])((TextLine[])this.lines.toArray(new TextLine[0]));
         this.blocks.add(block);
      }

      public void beginLine(Rect bbox, int wmode) {
         this.chrs = new ArrayList();
         this.lineBbox = bbox;
      }

      public void endLine() {
         TextLine line = StructuredText.this.new TextLine();
         line.bbox = this.lineBbox;
         line.chars = (TextChar[])this.chrs.toArray(new TextChar[0]);
         this.lines.add(line);
      }

      public void onChar(int c, Point origin, Font font, float size, Quad quad) {
         TextChar chr = StructuredText.this.new TextChar();
         chr.c = c;
         chr.quad = quad;
         this.chrs.add(chr);
      }

      TextBlock[] getBlocks() {
         return (TextBlock[])((TextBlock[])this.blocks.toArray(new TextBlock[0]));
      }
   }
}
