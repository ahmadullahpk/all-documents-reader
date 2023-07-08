package com.artifex.mupdf.fitz;

public class Quad {
   public float ul_x;
   public float ul_y;
   public float ur_x;
   public float ur_y;
   public float ll_x;
   public float ll_y;
   public float lr_x;
   public float lr_y;

   public Quad(float ul_x, float ul_y, float ur_x, float ur_y, float ll_x, float ll_y, float lr_x, float lr_y) {
      this.ul_x = ul_x;
      this.ul_y = ul_y;
      this.ur_x = ur_x;
      this.ur_y = ur_y;
      this.ll_x = ll_x;
      this.ll_y = ll_y;
      this.lr_x = lr_x;
      this.lr_y = lr_y;
   }

   public Rect toRect() {
      float x0 = Math.min(Math.min(this.ul_x, this.ur_x), Math.min(this.ll_x, this.lr_x));
      float y0 = Math.min(Math.min(this.ul_y, this.ur_y), Math.min(this.ll_y, this.lr_y));
      float x1 = Math.max(Math.max(this.ul_x, this.ur_x), Math.max(this.ll_x, this.lr_x));
      float y1 = Math.max(Math.max(this.ul_y, this.ur_y), Math.max(this.ll_y, this.lr_y));
      return new Rect(x0, y0, x1, y1);
   }

   public Quad transformed(Matrix m) {
      float t_ul_x = this.ul_x * m.a + this.ul_y * m.c + m.e;
      float t_ul_y = this.ul_x * m.b + this.ul_y * m.d + m.f;
      float t_ur_x = this.ur_x * m.a + this.ur_y * m.c + m.e;
      float t_ur_y = this.ur_x * m.b + this.ur_y * m.d + m.f;
      float t_ll_x = this.ll_x * m.a + this.ll_y * m.c + m.e;
      float t_ll_y = this.ll_x * m.b + this.ll_y * m.d + m.f;
      float t_lr_x = this.lr_x * m.a + this.lr_y * m.c + m.e;
      float t_lr_y = this.lr_x * m.b + this.lr_y * m.d + m.f;
      return new Quad(t_ul_x, t_ul_y, t_ur_x, t_ur_y, t_ll_x, t_ll_y, t_lr_x, t_lr_y);
   }

   public Quad transform(Matrix m) {
      float t_ul_x = this.ul_x * m.a + this.ul_y * m.c + m.e;
      float t_ul_y = this.ul_x * m.b + this.ul_y * m.d + m.f;
      float t_ur_x = this.ur_x * m.a + this.ur_y * m.c + m.e;
      float t_ur_y = this.ur_x * m.b + this.ur_y * m.d + m.f;
      float t_ll_x = this.ll_x * m.a + this.ll_y * m.c + m.e;
      float t_ll_y = this.ll_x * m.b + this.ll_y * m.d + m.f;
      float t_lr_x = this.lr_x * m.a + this.lr_y * m.c + m.e;
      float t_lr_y = this.lr_x * m.b + this.lr_y * m.d + m.f;
      this.ul_x = t_ul_x;
      this.ul_y = t_ul_y;
      this.ur_x = t_ur_x;
      this.ur_y = t_ur_y;
      this.ll_x = t_ll_x;
      this.ll_y = t_ll_y;
      this.lr_x = t_lr_x;
      this.lr_y = t_lr_y;
      return this;
   }

   public String toString() {
      return "[" + this.ul_x + " " + this.ul_y + " " + this.ur_x + " " + this.ur_y + " " + this.ll_x + " " + this.ll_y + " " + this.lr_x + " " + this.lr_y + "]";
   }
}
