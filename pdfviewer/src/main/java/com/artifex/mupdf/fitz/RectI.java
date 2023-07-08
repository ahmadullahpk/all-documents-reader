package com.artifex.mupdf.fitz;

public class RectI {
   public int x0;
   public int y0;
   public int x1;
   public int y1;

   public RectI() {
      this.x0 = this.y0 = this.x1 = this.y1 = 0;
   }

   public RectI(int x0, int y0, int x1, int y1) {
      this.x0 = x0;
      this.y0 = y0;
      this.x1 = x1;
      this.y1 = y1;
   }

   public RectI(RectI r) {
      this(r.x0, r.y0, r.x1, r.y1);
   }

   public RectI(Rect r) {
      this.x0 = (int)Math.floor((double)r.x0);
      this.y0 = (int)Math.ceil((double)r.y0);
      this.x1 = (int)Math.floor((double)r.x1);
      this.y1 = (int)Math.ceil((double)r.y1);
   }

   public String toString() {
      return "[" + this.x0 + " " + this.y0 + " " + this.x1 + " " + this.y1 + "]";
   }

   public RectI transform(Matrix tm) {
      float ax0 = (float)this.x0 * tm.a;
      float ax1 = (float)this.x1 * tm.a;
      float cy0;
      if (ax0 > ax1) {
         cy0 = ax0;
         ax0 = ax1;
         ax1 = cy0;
      }

      cy0 = (float)this.y0 * tm.c;
      float cy1 = (float)this.y1 * tm.c;
      float bx0;
      if (cy0 > cy1) {
         bx0 = cy0;
         cy0 = cy1;
         cy1 = bx0;
      }

      ax0 += cy0 + tm.e;
      ax1 += cy1 + tm.e;
      bx0 = (float)this.x0 * tm.b;
      float bx1 = (float)this.x1 * tm.b;
      float dy0;
      if (bx0 > bx1) {
         dy0 = bx0;
         bx0 = bx1;
         bx1 = dy0;
      }

      dy0 = (float)this.y0 * tm.d;
      float dy1 = (float)this.y1 * tm.d;
      if (dy0 > dy1) {
         float t = dy0;
         dy0 = dy1;
         dy1 = t;
      }

      bx0 += dy0 + tm.f;
      bx1 += dy1 + tm.f;
      this.x0 = (int)Math.floor((double)ax0);
      this.x1 = (int)Math.ceil((double)ax1);
      this.y0 = (int)Math.floor((double)bx0);
      this.y1 = (int)Math.ceil((double)bx1);
      return this;
   }

   public boolean contains(int x, int y) {
      if (this.isEmpty()) {
         return false;
      } else {
         return x >= this.x0 && x < this.x1 && y >= this.y0 && y < this.y1;
      }
   }

   public boolean contains(Rect r) {
      if (!this.isEmpty() && !r.isEmpty()) {
         return r.x0 >= (float)this.x0 && r.x1 <= (float)this.x1 && r.y0 >= (float)this.y0 && r.y1 <= (float)this.y1;
      } else {
         return false;
      }
   }

   public boolean isEmpty() {
      return this.x0 == this.x1 || this.y0 == this.y1;
   }

   public void union(RectI r) {
      if (this.isEmpty()) {
         this.x0 = r.x0;
         this.y0 = r.y0;
         this.x1 = r.x1;
         this.y1 = r.y1;
      } else {
         if (r.x0 < this.x0) {
            this.x0 = r.x0;
         }

         if (r.y0 < this.y0) {
            this.y0 = r.y0;
         }

         if (r.x1 > this.x1) {
            this.x1 = r.x1;
         }

         if (r.y1 > this.y1) {
            this.y1 = r.y1;
         }
      }

   }
}
