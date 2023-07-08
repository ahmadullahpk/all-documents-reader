package   com.ahmadullahpk.alldocumentreader.widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import   com.ahmadullahpk.alldocumentreader.R;

public class ShimmerLayout extends FrameLayout {
    private static final byte DEFAULT_ANGLE = 20;
    private static final int DEFAULT_ANIMATION_DURATION = 1500;
    private static final byte MAX_ANGLE_VALUE = 45;
    private static final byte MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE = 1;
    private static final byte MAX_MASK_WIDTH_VALUE = 1;
    private static final byte MIN_ANGLE_VALUE = -45;
    private static final byte MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE = 0;
    private static final byte MIN_MASK_WIDTH_VALUE = 0;
    private final boolean autoStart;
    private Canvas canvasForShimmerMask;
    private float gradientCenterColorWidth;
    private Paint gradientTexturePaint;
    private boolean isAnimationReversed;
    private boolean isAnimationStarted;
    private Bitmap localMaskBitmap;
    private ValueAnimator maskAnimator;
    private Bitmap maskBitmap;
    private int maskOffsetX;
    private Rect maskRect;
    private float maskWidth;
    private int shimmerAngle;
    private int shimmerAnimationDuration;
    private int shimmerColor;
    private ViewTreeObserver.OnPreDrawListener startAnimationPreDrawListener;

    public ShimmerLayout(Context context) {
        this(context, null);
    }

    public ShimmerLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

     public ShimmerLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setWillNotDraw(false);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.ShimmerLayout, 0, 0);
        try {
            this.shimmerAngle = obtainStyledAttributes.getInteger(0, DEFAULT_ANGLE);
            this.shimmerAnimationDuration = obtainStyledAttributes.getInteger(1, DEFAULT_ANIMATION_DURATION);
            this.shimmerColor = obtainStyledAttributes.getColor(3, getColor(R.color.shimmer_color));
            this.autoStart = obtainStyledAttributes.getBoolean(2, false);
            this.maskWidth = obtainStyledAttributes.getFloat(5, 0.4f);
            this.gradientCenterColorWidth = obtainStyledAttributes.getFloat(4, 0.01f);
            this.isAnimationReversed = obtainStyledAttributes.getBoolean(6, false);
            obtainStyledAttributes.recycle();
            setMaskWidth(this.maskWidth);
            setGradientCenterColorWidth(this.gradientCenterColorWidth);
            setShimmerAngle(this.shimmerAngle);
            enableForcedSoftwareLayerIfNeeded();
            if (this.autoStart && getVisibility() == View.GONE) {
                startShimmerAnimation();
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        resetShimmering();
        super.onDetachedFromWindow();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!this.isAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
            super.dispatchDraw(canvas);
        } else {
            dispatchDrawShimmer(canvas);
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i != 0) {
            stopShimmerAnimation();
        } else if (this.autoStart) {
            startShimmerAnimation();
        }
    }

    public void startShimmerAnimation() {
        if (!this.isAnimationStarted) {
            if (getWidth() == 0) {
                this.startAnimationPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        ShimmerLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                        ShimmerLayout.this.startShimmerAnimation();
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(this.startAnimationPreDrawListener);
                return;
            }
            getShimmerAnimation().start();
            this.isAnimationStarted = true;
        }
    }

    public void stopShimmerAnimation() {
        if (this.startAnimationPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.startAnimationPreDrawListener);
        }
        resetShimmering();
    }

    public boolean isAnimationStarted() {
        return this.isAnimationStarted;
    }

    public void setShimmerColor(int i) {
        this.shimmerColor = i;
        resetIfStarted();
    }

    public void setShimmerAnimationDuration(int i) {
        this.shimmerAnimationDuration = i;
        resetIfStarted();
    }

    public void setAnimationReversed(boolean z) {
        this.isAnimationReversed = z;
        resetIfStarted();
    }

    public void setShimmerAngle(int i) {
        if (i < MIN_ANGLE_VALUE || MAX_ANGLE_VALUE < i) {
            throw new IllegalArgumentException(String.format("shimmerAngle value must be between %d and %d", MIN_ANGLE_VALUE, (byte) 45));
        }
        this.shimmerAngle = i;
        resetIfStarted();
    }

    public void setMaskWidth(float f) {
        if (f <= 0.0f || 1.0f < f) {
            throw new IllegalArgumentException(String.format("maskWidth value must be higher than %d and less or equal to %d", (byte) 0, (byte) 1));
        }
        this.maskWidth = f;
        resetIfStarted();
    }

    public void setGradientCenterColorWidth(float f) {
        if (f <= 0.0f || 1.0f <= f) {
            throw new IllegalArgumentException(String.format("gradientCenterColorWidth value must be higher than %d and less than %d", (byte) 0, (byte) 1));
        }
        this.gradientCenterColorWidth = f;
        resetIfStarted();
    }

    private void resetIfStarted() {
        if (this.isAnimationStarted) {
            resetShimmering();
            startShimmerAnimation();
        }
    }

    private void dispatchDrawShimmer(Canvas canvas) {
        super.dispatchDraw(canvas);
        Bitmap maskBitmap2 = getMaskBitmap();
        this.localMaskBitmap = maskBitmap2;
        if (maskBitmap2 != null) {
            if (this.canvasForShimmerMask == null) {
                this.canvasForShimmerMask = new Canvas(this.localMaskBitmap);
            }
            this.canvasForShimmerMask.drawColor(0, PorterDuff.Mode.CLEAR);
            this.canvasForShimmerMask.save();
            this.canvasForShimmerMask.translate((float) (-this.maskOffsetX), 0.0f);
            super.dispatchDraw(this.canvasForShimmerMask);
            this.canvasForShimmerMask.restore();
            drawShimmer(canvas);
            this.localMaskBitmap = null;
        }
    }

    private void drawShimmer(Canvas canvas) {
        createShimmerPaint();
        canvas.save();
        canvas.translate((float) this.maskOffsetX, 0.0f);
        canvas.drawRect((float) this.maskRect.left, 0.0f, (float) this.maskRect.width(), (float) this.maskRect.height(), this.gradientTexturePaint);
        canvas.restore();
    }

    private void resetShimmering() {
        ValueAnimator valueAnimator = this.maskAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.maskAnimator.removeAllUpdateListeners();
        }
        this.maskAnimator = null;
        this.gradientTexturePaint = null;
        this.isAnimationStarted = false;
        releaseBitMaps();
    }

    private void releaseBitMaps() {
        this.canvasForShimmerMask = null;
        Bitmap bitmap = this.maskBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.maskBitmap = null;
        }
    }

    private Bitmap getMaskBitmap() {
        if (this.maskBitmap == null) {
            this.maskBitmap = createBitmap(this.maskRect.width(), getHeight());
        }
        return this.maskBitmap;
    }

    private void createShimmerPaint() {
        if (this.gradientTexturePaint == null) {
            int reduceColorAlphaValueToZero = reduceColorAlphaValueToZero(this.shimmerColor);
            float width = ((float) (getWidth() / 2)) * this.maskWidth;
            float height = this.shimmerAngle >= 0 ? (float) getHeight() : 0.0f;
            int i = this.shimmerColor;
            ComposeShader composeShader = new ComposeShader(new LinearGradient(0.0f, height, ((float) Math.cos(Math.toRadians(this.shimmerAngle))) * width, height + (((float) Math.sin(Math.toRadians(this.shimmerAngle))) * width), new int[]{reduceColorAlphaValueToZero, i, i, reduceColorAlphaValueToZero}, getGradientColorDistribution(), Shader.TileMode.CLAMP), new BitmapShader(this.localMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP), PorterDuff.Mode.DST_IN);
            Paint paint = new Paint();
            this.gradientTexturePaint = paint;
            paint.setAntiAlias(true);
            this.gradientTexturePaint.setDither(true);
            this.gradientTexturePaint.setFilterBitmap(true);
            this.gradientTexturePaint.setShader(composeShader);
        }
    }

    private Animator getShimmerAnimation() {
        final int i;
        ValueAnimator valueAnimator;
        ValueAnimator valueAnimator2 = this.maskAnimator;
        if (valueAnimator2 != null) {
            return valueAnimator2;
        }
        if (this.maskRect == null) {
            this.maskRect = calculateBitmapMaskRect();
        }
        int width = getWidth();
        if (getWidth() > this.maskRect.width()) {
            i = -width;
        } else {
            i = -this.maskRect.width();
        }
        final int width2 = this.maskRect.width();
        int i2 = width - i;
        if (this.isAnimationReversed) {
            valueAnimator = ValueAnimator.ofInt(i2, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, i2);
        }
        this.maskAnimator = valueAnimator;
        valueAnimator.setDuration(this.shimmerAnimationDuration);
        this.maskAnimator.setRepeatCount(-1);
        this.maskAnimator.addUpdateListener(valueAnimator1 -> {
            ShimmerLayout.this.maskOffsetX = i + (Integer) valueAnimator1.getAnimatedValue();
            if (ShimmerLayout.this.maskOffsetX + width2 >= 0) {
                ShimmerLayout.this.invalidate();
            }
        });
        return this.maskAnimator;
    }

    private Bitmap createBitmap(int i, int i2) {
        try {
            return Bitmap.createBitmap(i, i2, Bitmap.Config.ALPHA_8);
        } catch (OutOfMemoryError unused) {
            System.gc();
            return null;
        }
    }

    private int getColor(int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            return getContext().getColor(i);
        }
        return getResources().getColor(i);
    }

    private int reduceColorAlphaValueToZero(int i) {
        return Color.argb(0, Color.red(i), Color.green(i), Color.blue(i));
    }

    private Rect calculateBitmapMaskRect() {
        return new Rect(0, 0, calculateMaskWidth(), getHeight());
    }

    private int calculateMaskWidth() {
        double width = ((float) (getWidth() / 2)) * this.maskWidth;
        double cos = Math.cos(Math.toRadians(Math.abs(this.shimmerAngle)));
        Double.isNaN(width);
        double d = width / cos;
        double height = getHeight();
        double tan = Math.tan(Math.toRadians(Math.abs(this.shimmerAngle)));
        Double.isNaN(height);
        return (int) (d + (height * tan));
    }

    private float[] getGradientColorDistribution() {
        float[] fArr = new float[4];
        fArr[0] = 0.0f;
        fArr[3] = 1.0f;
        float f = this.gradientCenterColorWidth;
        fArr[1] = 0.5f - (f / 2.0f);
        fArr[2] = (f / 2.0f) + 0.5f;
        return fArr;
    }

    private void enableForcedSoftwareLayerIfNeeded() {
    }
}
