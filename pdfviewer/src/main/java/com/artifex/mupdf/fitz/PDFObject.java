package com.artifex.mupdf.fitz;

public class PDFObject {
   private long pointer;
   public static final PDFObject Null;

   protected native void finalize();

   private PDFObject(long p) {
      this.pointer = p;
   }

   public native boolean isIndirect();

   public native boolean isNull();

   public native boolean isBoolean();

   public native boolean isInteger();

   public native boolean isReal();

   public native boolean isNumber();

   public native boolean isString();

   public native boolean isName();

   public native boolean isArray();

   public native boolean isDictionary();

   public native boolean isStream();

   public native boolean asBoolean();

   public native int asInteger();

   public native float asFloat();

   public native int asIndirect();

   public native String asName();

   public native String asString();

   public native byte[] asByteString();

   public native String toString(boolean var1, boolean var2);

   public String toString(boolean tight) {
      return this.toString(tight, false);
   }

   public String toString() {
      return this.toString(false, false);
   }

   public native PDFObject resolve();

   public native byte[] readStream();

   public native byte[] readRawStream();

   public native void writeObject(PDFObject var1);

   private native void writeStreamBuffer(Buffer var1);

   private native void writeStreamString(String var1);

   private native void writeRawStreamBuffer(Buffer var1);

   private native void writeRawStreamString(String var1);

   public void writeStream(Buffer buf) {
      this.writeStreamBuffer(buf);
   }

   public void writeStream(String str) {
      this.writeStreamString(str);
   }

   public void writeRawStream(Buffer buf) {
      this.writeRawStreamBuffer(buf);
   }

   public void writeRawStream(String str) {
      this.writeRawStreamString(str);
   }

   private native PDFObject getArray(int var1);

   private native PDFObject getDictionary(String var1);

   public PDFObject get(int index) {
      return this.getArray(index);
   }

   public PDFObject get(String name) {
      return this.getDictionary(name);
   }

   private native void putArrayBoolean(int var1, boolean var2);

   private native void putArrayInteger(int var1, int var2);

   private native void putArrayFloat(int var1, float var2);

   private native void putArrayString(int var1, String var2);

   private native void putArrayPDFObject(int var1, PDFObject var2);

   private native void putDictionaryStringBoolean(String var1, boolean var2);

   private native void putDictionaryStringInteger(String var1, int var2);

   private native void putDictionaryStringFloat(String var1, float var2);

   private native void putDictionaryStringString(String var1, String var2);

   private native void putDictionaryStringPDFObject(String var1, PDFObject var2);

   private native void putDictionaryPDFObjectBoolean(PDFObject var1, boolean var2);

   private native void putDictionaryPDFObjectInteger(PDFObject var1, int var2);

   private native void putDictionaryPDFObjectFloat(PDFObject var1, float var2);

   private native void putDictionaryPDFObjectString(PDFObject var1, String var2);

   private native void putDictionaryPDFObjectPDFObject(PDFObject var1, PDFObject var2);

   public void put(int index, boolean b) {
      this.putArrayBoolean(index, b);
   }

   public void put(int index, int i) {
      this.putArrayInteger(index, i);
   }

   public void put(int index, float f) {
      this.putArrayFloat(index, f);
   }

   public void put(int index, String s) {
      this.putArrayString(index, s);
   }

   public void put(int index, PDFObject obj) {
      this.putArrayPDFObject(index, obj);
   }

   public void put(String name, boolean b) {
      this.putDictionaryStringBoolean(name, b);
   }

   public void put(String name, int i) {
      this.putDictionaryStringInteger(name, i);
   }

   public void put(String name, float f) {
      this.putDictionaryStringFloat(name, f);
   }

   public void put(String name, String str) {
      this.putDictionaryStringString(name, str);
   }

   public void put(String name, PDFObject obj) {
      this.putDictionaryStringPDFObject(name, obj);
   }

   public void put(PDFObject name, boolean b) {
      this.putDictionaryPDFObjectBoolean(name, b);
   }

   public void put(PDFObject name, int i) {
      this.putDictionaryPDFObjectInteger(name, i);
   }

   public void put(PDFObject name, float f) {
      this.putDictionaryPDFObjectFloat(name, f);
   }

   public void put(PDFObject name, String str) {
      this.putDictionaryPDFObjectString(name, str);
   }

   public void put(PDFObject name, PDFObject obj) {
      this.putDictionaryPDFObjectPDFObject(name, obj);
   }

   private native void deleteArray(int var1);

   private native void deleteDictionaryString(String var1);

   private native void deleteDictionaryPDFObject(PDFObject var1);

   public void delete(int index) {
      this.deleteArray(index);
   }

   public void delete(String name) {
      this.deleteDictionaryString(name);
   }

   public void delete(PDFObject name) {
      this.deleteDictionaryPDFObject(name);
   }

   public native int size();

   private native void pushBoolean(boolean var1);

   private native void pushInteger(int var1);

   private native void pushFloat(float var1);

   private native void pushString(String var1);

   private native void pushPDFObject(PDFObject var1);

   public void push(boolean b) {
      this.pushBoolean(b);
   }

   public void push(int i) {
      this.pushInteger(i);
   }

   public void push(float f) {
      this.pushFloat(f);
   }

   public void push(String s) {
      this.pushString(s);
   }

   public void push(PDFObject obj) {
      this.pushPDFObject(obj);
   }

   static {
      Context.init();
      Null = new PDFObject(0L);
   }
}
