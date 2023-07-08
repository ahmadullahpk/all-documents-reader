package com.artifex.mupdf.fitz;

public class PDFDocument extends Document {
   private static native long newNative();

   protected PDFDocument(long p) {
      super(p);
   }

   public PDFDocument() {
      super(newNative());
   }

   public boolean isPDF() {
      return true;
   }

   public native PDFObject findPage(int var1);

   public native PDFObject getTrailer();

   public native int countObjects();

   public native PDFObject newNull();

   public native PDFObject newBoolean(boolean var1);

   public native PDFObject newInteger(int var1);

   public native PDFObject newReal(float var1);

   public native PDFObject newString(String var1);

   public native PDFObject newByteString(byte[] var1);

   public native PDFObject newName(String var1);

   public native PDFObject newIndirect(int var1, int var2);

   public native PDFObject newArray();

   public native PDFObject newDictionary();

   public native PDFObject addObject(PDFObject var1);

   public native PDFObject createObject();

   public native void deleteObject(int var1);

   public void deleteObject(PDFObject obj) {
      this.deleteObject(obj.asIndirect());
   }

   public native PDFGraftMap newPDFGraftMap();

   public native PDFObject graftObject(PDFObject var1);

   private native PDFObject addStreamBuffer(Buffer var1, Object var2, boolean var3);

   private native PDFObject addStreamString(String var1, Object var2, boolean var3);

   public PDFObject addRawStream(Buffer buf, Object obj) {
      return this.addStreamBuffer(buf, obj, true);
   }

   public PDFObject addStream(Buffer buf, Object obj) {
      return this.addStreamBuffer(buf, obj, false);
   }

   public PDFObject addRawStream(String str, Object obj) {
      return this.addStreamString(str, obj, true);
   }

   public PDFObject addStream(String str, Object obj) {
      return this.addStreamString(str, obj, false);
   }

   public PDFObject addRawStream(Buffer buf) {
      return this.addStreamBuffer(buf, (Object)null, true);
   }

   public PDFObject addStream(Buffer buf) {
      return this.addStreamBuffer(buf, (Object)null, false);
   }

   public PDFObject addRawStream(String str) {
      return this.addStreamString(str, (Object)null, true);
   }

   public PDFObject addStream(String str) {
      return this.addStreamString(str, (Object)null, false);
   }

   private native PDFObject addPageBuffer(Rect var1, int var2, PDFObject var3, Buffer var4);

   private native PDFObject addPageString(Rect var1, int var2, PDFObject var3, String var4);

   public PDFObject addPage(Rect mediabox, int rotate, PDFObject resources, Buffer contents) {
      return this.addPageBuffer(mediabox, rotate, resources, contents);
   }

   public PDFObject addPage(Rect mediabox, int rotate, PDFObject resources, String contents) {
      return this.addPageString(mediabox, rotate, resources, contents);
   }

   public native void insertPage(int var1, PDFObject var2);

   public native void deletePage(int var1);

   public native PDFObject addImage(Image var1);

   public native PDFObject addSimpleFont(Font var1, int var2);

   public native PDFObject addCJKFont(Font var1, int var2, int var3, boolean var4);

   public native PDFObject addFont(Font var1);

   public native boolean hasUnsavedChanges();

   public native boolean canBeSavedIncrementally();

   public native void save(String var1, String var2);

   protected native void nativeSaveWithStream(SeekableOutputStream var1, String var2);

   public void save(SeekableOutputStream stream, String options) {
      this.nativeSaveWithStream(stream, options);
   }

   public native void enableJs();

   public native void disableJs();

   public native boolean isJsSupported();

   public native void setJsEventListener(JsEventListener var1);

   public native void calculate();

   public boolean hasAcroForm() {
      return this.getTrailer().get("Root").get("AcroForm").get("Fields").size() > 0;
   }

   public boolean hasXFAForm() {
      return !this.getTrailer().get("Root").get("AcroForm").get("XFA").isNull();
   }

   static {
      Context.init();
   }

   public interface JsEventListener {
      void onAlert(String var1);
   }
}
