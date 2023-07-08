package com.artifex.mupdf.fitz;

import java.io.IOException;
import java.io.InputStream;

public class BufferInputStream extends InputStream {
   protected Buffer buffer;
   protected int position;
   protected int resetPosition;

   public BufferInputStream(Buffer buffer) {
      this.buffer = buffer;
      this.position = 0;
      this.resetPosition = -1;
   }

   public int available() {
      return this.buffer.getLength();
   }

   public void mark(int readlimit) {
      this.resetPosition = this.position;
   }

   public boolean markSupported() {
      return true;
   }

   public int read() {
      int c = this.buffer.readByte(this.position);
      if (c >= 0) {
         ++this.position;
      }

      return c;
   }

   public int read(byte[] b) {
      int n = this.buffer.readBytes(this.position, b);
      if (n >= 0) {
         this.position += n;
      }

      return n;
   }

   public int read(byte[] b, int off, int len) {
      int n = this.buffer.readBytesInto(this.position, b, off, len);
      if (n >= 0) {
         this.position += n;
      }

      return n;
   }

   public void reset() throws IOException {
      if (this.resetPosition < 0) {
         throw new IOException("cannot reset because mark never set");
      } else if (this.resetPosition >= this.buffer.getLength()) {
         throw new IOException("cannot reset because mark set outside of buffer");
      } else {
         this.position = this.resetPosition;
      }
   }
}
