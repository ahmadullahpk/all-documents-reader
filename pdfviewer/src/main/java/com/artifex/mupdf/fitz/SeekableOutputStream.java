package com.artifex.mupdf.fitz;

import java.io.IOException;

public interface SeekableOutputStream extends SeekableStream {
   void write(byte[] var1, int var2, int var3) throws IOException;
}
