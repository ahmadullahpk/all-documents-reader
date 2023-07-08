package com.artifex.mupdf.fitz;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileStream implements SeekableInputStream, SeekableOutputStream {
   protected RandomAccessFile file;

   public FileStream(String path, String mode) throws IOException {
      this.file = new RandomAccessFile(path, mode);
   }

   public FileStream(File path, String mode) throws IOException {
      this.file = new RandomAccessFile(path, mode);
   }

   public int read(byte[] buf) throws IOException {
      return this.file.read(buf);
   }

   public void write(byte[] buf, int off, int len) throws IOException {
      this.file.write(buf, off, len);
   }

   public long seek(long offset, int whence) throws IOException {
      switch(whence) {
      case 0:
         this.file.seek(offset);
         break;
      case 1:
         this.file.seek(this.file.getFilePointer() + offset);
         break;
      case 2:
         this.file.seek(this.file.length() + offset);
      }

      return this.file.getFilePointer();
   }

   public long position() throws IOException {
      return this.file.getFilePointer();
   }

   public void close() throws IOException {
      this.file.close();
   }
}
