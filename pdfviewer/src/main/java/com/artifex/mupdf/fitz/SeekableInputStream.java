package com.artifex.mupdf.fitz;

import java.io.IOException;

public interface SeekableInputStream extends SeekableStream {
   int read(byte[] var1) throws IOException;
}
