package com.artifex.mupdf.fitz;

public class Outline {
   public String title;
   public String uri;
   public int page;
   public Outline[] down;
   public float x;
   public float y;

   public Outline(String title, int page, String uri, float x, float y, Outline[] down) {
      this.title = title;
      this.page = page;
      this.uri = uri;
      this.down = down;
      this.x = x;
      this.y = y;
   }

   public String toString() {
      StringBuffer s = new StringBuffer();
      s.append(this.page);
      s.append(": ");
      s.append(this.title);
      s.append(' ');
      s.append(this.uri);
      s.append('\n');
      if (this.down != null) {
         for(int i = 0; i < this.down.length; ++i) {
            s.append('\t');
            s.append(this.down[i]);
            s.append('\n');
         }
      }

      s.deleteCharAt(s.length() - 1);
      return s.toString();
   }
}
