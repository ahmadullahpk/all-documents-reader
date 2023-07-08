package com.artifex.mupdf.fitz;

import java.io.IOException;

public interface SeekableStream {
   int SEEK_SET = 0;
   int SEEK_CUR = 1;
   int SEEK_END = 2;

   long seek(long var1, int var3) throws IOException;

   long position() throws IOException;
}
