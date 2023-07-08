package com.ahmadullahpk.alldocumentreader.manageui;




import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import   com.ahmadullahpk.alldocumentreader.R;


public final class RoundCornerFrameLay extends FrameLayout {


    private final float[] arrayDimens;

    private final Path path;

    private final Path path1;

    private final Paint paint;

    private boolean roundAsCircle;

    private final int strokeColor;

    private final int strokeWidth;

    private final Region mRegion;

    private final RectF rectangleC;

    public RoundCornerFrameLay(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.arrayDimens = new float[8];
        rectangleC = new RectF();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundCornerLayout);
        roundAsCircle = obtainStyledAttributes.getBoolean(R.styleable.RoundCornerLayout_round_as_circle, false);
        strokeColor = obtainStyledAttributes.getColor(R.styleable.RoundCornerLayout_stroke_color, -1);
        strokeWidth = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_stroke_width, 0);
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius, 0);
        int dimensionPixelOffset2 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_top_left, dimensionPixelOffset);
        int dimensionPixelOffset3 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_top_right, dimensionPixelOffset);
        int dimensionPixelOffset4 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_bottom_left, dimensionPixelOffset);
        int dimensionPixelOffset5 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_bottom_right, dimensionPixelOffset);
        int dimensionPixelOffset6 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_top, 0);
        int dimensionPixelOffset7 = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RoundCornerLayout_corner_radius_bottom, 0);
        if (dimensionPixelOffset6 != 0) {
            dimensionPixelOffset2 = dimensionPixelOffset6;
            dimensionPixelOffset3 = dimensionPixelOffset2;
        }
        if (dimensionPixelOffset7 != 0) {
            dimensionPixelOffset5 = dimensionPixelOffset7;
            dimensionPixelOffset4 = dimensionPixelOffset5;
        }
        float[] fArr = this.arrayDimens;
        float f = (float) dimensionPixelOffset2;
        fArr[0] = f;
        fArr[1] = f;
        float f2 = (float) dimensionPixelOffset3;
        fArr[2] = f2;
        fArr[3] = f2;
        float f3 = (float) dimensionPixelOffset4;
        fArr[4] = f3;
        fArr[5] = f3;
        float f4 = (float) dimensionPixelOffset5;
        fArr[6] = f4;
        fArr[7] = f4;
        this.path = new Path();
        this.path1 = new Path();
        this.mRegion = new Region();
        this.paint = new Paint();
        this.paint.setColor(-1);
        this.paint.setAntiAlias(true);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.RoundCornerLayout_rounded_content_layout, 0);
        obtainStyledAttributes.recycle();
        if (resourceId > 0) {
            addView(LayoutInflater.from(getContext()).inflate(resourceId, null));
        }
    }


    public RoundCornerFrameLay(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4);
    }

    public RoundCornerFrameLay(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i);

    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.rectangleC.left = (float) getPaddingLeft();
        this.rectangleC.top = (float) getPaddingTop();
        this.rectangleC.right = (float) (i - getPaddingRight());
        this.rectangleC.bottom = (float) (i2 - getPaddingBottom());
        if (Build.VERSION.SDK_INT < 21) {
            this.path.reset();
            if (this.roundAsCircle) {
                float height = (Math.min(this.rectangleC.width(), this.rectangleC.height())) / ((float) 2);
                PointF pointF = new PointF((float) (i / 2), (float) (i2 / 2));
                this.path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
                this.path.addRect(this.rectangleC, Path.Direction.CW);
                this.path.addCircle(pointF.x, pointF.y, height, Path.Direction.CW);
                this.path1.addCircle(pointF.x, pointF.y, height, Path.Direction.CW);
            } else {
                this.path.setFillType(Path.FillType.EVEN_ODD);
                this.path.addRoundRect(this.rectangleC, this.arrayDimens, Path.Direction.CW);
                this.path1.addRoundRect(this.rectangleC, this.arrayDimens, Path.Direction.CW);
            }
            this.mRegion.setPath(this.path1, new Region((int) this.rectangleC.left, (int) this.rectangleC.top, (int) this.rectangleC.right, (int) this.rectangleC.bottom));
            return;
        }



        ViewParent parent = getParent();
        if (!(parent instanceof View)) {
            parent = null;
        }
        View view = (View) parent;
        if (view != null) {
            view.setBackgroundColor(-16777216);
        }
        setClipToOutline(true);
    }

    public final RectF getAras() {
        return this.rectangleC;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.rectangleC.contains(motionEvent.getX(), motionEvent.getY())) {
            return false;
        }
        return super.dispatchTouchEvent(motionEvent);
    }


}
