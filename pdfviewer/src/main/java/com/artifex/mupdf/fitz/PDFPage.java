package com.artifex.mupdf.fitz;

public class PDFPage extends Page {
   private PDFWidget[] widgets;

   private PDFPage(long p) {
      super(p);
   }

   public native PDFAnnotation[] getAnnotations();

   public native PDFAnnotation createAnnotation(int var1);

   public native void deleteAnnotation(PDFAnnotation var1);

   public native boolean applyRedactions();

   public native boolean update();

   private native PDFWidget[] getWidgetsNative();

   public PDFWidget[] getWidgets() {
      if (this.widgets == null) {
         this.widgets = this.getWidgetsNative();
      }

      return this.widgets;
   }

   public PDFWidget activateWidgetAt(float pageX, float pageY) {
      PDFWidget[] var3 = this.getWidgets();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PDFWidget widget = var3[var5];
         if (widget.getBounds().contains(pageX, pageY)) {
            widget.eventEnter();
            widget.eventDown();
            widget.eventFocus();
            widget.eventUp();
            widget.eventExit();
            widget.eventBlur();
            return widget;
         }
      }

      return null;
   }

   static {
      Context.init();
   }
}
