package com.artifex.mupdf.fitz;

import java.util.Date;

public class PDFAnnotation {
   private long pointer;
   public static final int TYPE_TEXT = 0;
   public static final int TYPE_LINK = 1;
   public static final int TYPE_FREE_TEXT = 2;
   public static final int TYPE_LINE = 3;
   public static final int TYPE_SQUARE = 4;
   public static final int TYPE_CIRCLE = 5;
   public static final int TYPE_POLYGON = 6;
   public static final int TYPE_POLY_LINE = 7;
   public static final int TYPE_HIGHLIGHT = 8;
   public static final int TYPE_UNDERLINE = 9;
   public static final int TYPE_SQUIGGLY = 10;
   public static final int TYPE_STRIKE_OUT = 11;
   public static final int TYPE_REDACT = 12;
   public static final int TYPE_STAMP = 13;
   public static final int TYPE_CARET = 14;
   public static final int TYPE_INK = 15;
   public static final int TYPE_POPUP = 16;
   public static final int TYPE_FILE_ATTACHMENT = 17;
   public static final int TYPE_SOUND = 18;
   public static final int TYPE_MOVIE = 19;
   public static final int TYPE_WIDGET = 20;
   public static final int TYPE_SCREEN = 21;
   public static final int TYPE_PRINTER_MARK = 22;
   public static final int TYPE_TRAP_NET = 23;
   public static final int TYPE_WATERMARK = 24;
   public static final int TYPE_3D = 25;
   public static final int TYPE_UNKNOWN = -1;
   public static final int LINE_ENDING_NONE = 0;
   public static final int LINE_ENDING_SQUARE = 1;
   public static final int LINE_ENDING_CIRCLE = 2;
   public static final int LINE_ENDING_DIAMOND = 3;
   public static final int LINE_ENDING_OPEN_ARROW = 4;
   public static final int LINE_ENDING_CLOSED_ARROW = 5;
   public static final int LINE_ENDING_BUTT = 6;
   public static final int LINE_ENDING_R_OPEN_ARROW = 7;
   public static final int LINE_ENDING_R_CLOSED_ARROW = 8;
   public static final int LINE_ENDING_SLASH = 9;
   public static final int IS_INVISIBLE = 1;
   public static final int IS_HIDDEN = 2;
   public static final int IS_PRINT = 4;
   public static final int IS_NO_ZOOM = 8;
   public static final int IS_NO_ROTATE = 16;
   public static final int IS_NO_VIEW = 32;
   public static final int IS_READ_ONLY = 64;
   public static final int IS_LOCKED = 128;
   public static final int IS_TOGGLE_NO_VIEW = 256;
   public static final int IS_LOCKED_CONTENTS = 512;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   protected PDFAnnotation(long p) {
      this.pointer = p;
   }

   public boolean equals(PDFAnnotation other) {
      return this.pointer == other.pointer;
   }

   public boolean equals(long other) {
      return this.pointer == other;
   }

   public native void run(Device var1, Matrix var2, Cookie var3);

   public native Pixmap toPixmap(Matrix var1, ColorSpace var2, boolean var3);

   public native Rect getBounds();

   public native DisplayList toDisplayList();

   public native int getType();

   public native int getFlags();

   public native void setFlags(int var1);

   public native String getContents();

   public native void setContents(String var1);

   public native Rect getRect();

   public native void setRect(Rect var1);

   public native float getBorder();

   public native void setBorder(float var1);

   public native float[] getColor();

   public native void setColor(float[] var1);

   public native float[] getInteriorColor();

   public native void setInteriorColor(float[] var1);

   public native String getAuthor();

   public native void setAuthor(String var1);

   protected native long getModificationDateNative();

   protected native void setModificationDate(long var1);

   public Date getModificationDate() {
      return new Date(this.getModificationDateNative());
   }

   public void setModificationDate(Date date) {
      this.setModificationDate(date.getTime());
   }

   public native int[] getLineEndingStyles();

   public native void setLineEndingStyles(int var1, int var2);

   public void setLineEndingStyles(int[] styles) {
      this.setLineEndingStyles(styles[0], styles[1]);
   }

   public native float[] getVertices();

   public native void setVertices(float[] var1);

   public native float[][] getQuadPoints();

   public native void setQuadPoints(float[][] var1);

   public native float[][] getInkList();

   public native void setInkList(float[][] var1);

   public native String getIcon();

   public native void setIcon(String var1);

   public native boolean isOpen();

   public native void setIsOpen(boolean var1);

   public native void eventEnter();

   public native void eventExit();

   public native void eventDown();

   public native void eventUp();

   public native void eventFocus();

   public native void eventBlur();

   public native boolean update();

   static {
      Context.init();
   }
}
