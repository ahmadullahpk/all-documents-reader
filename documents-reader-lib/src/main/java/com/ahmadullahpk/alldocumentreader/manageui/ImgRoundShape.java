package com.ahmadullahpk.alldocumentreader.manageui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.ahmadullahpk.alldocumentreader.R;


public class ImgRoundShape extends AppCompatImageView {
    private Path path;
    public float radius = 22.0f;
    private RectF rect;

    public ImgRoundShape(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getContext().obtainStyledAttributes(attributeSet, R.styleable.RoundImageView).recycle();
        init();
    }

    public ImgRoundShape(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        getContext().obtainStyledAttributes(attributeSet, R.styleable.RoundImageView).recycle();
        init();
    }

    private void init() {
        this.path = new Path();
    }

     protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.rect = rectF;
        Path path2 = this.path;
        float f = this.radius;
        path2.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.path);
        super.onDraw(canvas);
    }
}