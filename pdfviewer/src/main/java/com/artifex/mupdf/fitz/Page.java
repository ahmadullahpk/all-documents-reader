package com.artifex.mupdf.fitz;

public class Page {
   private long pointer;

   protected native void finalize();

   public void destroy() {
      this.finalize();
      this.pointer = 0L;
   }

   protected Page(long p) {
      this.pointer = p;
   }

   public native Rect getBounds();

   public native void run(Device var1, Matrix var2, Cookie var3);

   public native void runPageContents(Device var1, Matrix var2, Cookie var3);

   public native void runPageAnnots(Device var1, Matrix var2, Cookie var3);

   public native void runPageWidgets(Device var1, Matrix var2, Cookie var3);

   public void run(Device dev, Matrix ctm) {
      this.run(dev, ctm, (Cookie)null);
   }

   public native Link[] getLinks();

   public native Pixmap toPixmap(Matrix var1, ColorSpace var2, boolean var3, boolean var4);

   public Pixmap toPixmap(Matrix ctm, ColorSpace cs, boolean alpha) {
      return this.toPixmap(ctm, cs, alpha, true);
   }

   public native DisplayList toDisplayList(boolean var1);

   public DisplayList toDisplayList() {
      return this.toDisplayList(true);
   }

   public native StructuredText toStructuredText(String var1);

   public StructuredText toStructuredText() {
      return this.toStructuredText((String)null);
   }

   public native Quad[] search(String var1);

   public native byte[] textAsHtml();

   public native byte[] textAsHtml2(String var1);

   public native byte[] textAsXHtml(String var1);

   public native byte[] textAsXml(String var1);

   public native byte[] textAsText(String var1);

   public native Separations getSeparations();

   static {
      Context.init();
   }
}
