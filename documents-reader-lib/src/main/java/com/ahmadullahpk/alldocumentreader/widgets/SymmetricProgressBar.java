package com.ahmadullahpk.alldocumentreader.widgets;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.ahmadullahpk.alldocumentreader.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SymmetricProgressBar extends View implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
    private final List<Integer> colors = new ArrayList<>(4);
    private final List<Drawable> drawables = new ArrayList<>(this.colors.size());
    private final List<ValueAnimator> animators = new ArrayList<>(this.colors.size());

    private Interpolator interpolator;
    private boolean animating;
    private boolean hasRepeated;
    private int duration;

    /**
     * Creates a new progress bar with range 0..100 and initial progress of 0.
     *
     * @param context The application context.
     */
    public SymmetricProgressBar(final Context context) {
        this(context, null);
    }

    public SymmetricProgressBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final TypedArray array;
        final int[] colors;

        if (attrs == null) {
            array = null;
            colors = context.getResources().getIntArray(R.array.SymmetricProgressBar_colors);
        } else {
            array = context.obtainStyledAttributes(attrs, R.styleable.SymmetricProgressBar);
            colors = context.getResources().getIntArray(array.getResourceId(R.styleable.SymmetricProgressBar_colors, R.array.SymmetricProgressBar_colors));
        }

        for (final int color : colors) {
            this.colors.add(color);
        }

        final int interpolatorResId = array == null ? android.R.anim.decelerate_interpolator : array.getResourceId(R.styleable.SymmetricProgressBar_interpolator, android.R.anim.decelerate_interpolator);

        if (interpolatorResId > 0) {
            this.setInterpolator(context, interpolatorResId);
        }

        if (array != null) {
            this.setDuration(array.getInteger(R.styleable.SymmetricProgressBar_duration, context.getResources().getInteger(R.integer.SymmetricProgressBar_duration)));

            array.recycle();
        }
    }

    /**
     * Gets the colors used to draw the progress bar in progress mode.
     */
    public List<Integer> getColors() {
        return Collections.unmodifiableList(this.colors);
    }

    /**
     * Sets the colors used to draw the progress bar in progress mode.
     */
    public void setColors(final Collection<Integer> colors) {
        this.colors.clear();

        this.colors.addAll(colors);
    }

    /**
     * Gets the acceleration curve type for the indeterminate animation.
     *
     * @return The {@link Interpolator} associated to this animation.
     */
    public Interpolator getInterpolator() {
        return this.interpolator;
    }

    /**
     * Sets the acceleration curve for the indeterminate animation.
     * <p>The interpolator is loaded as a resource from the specified context.</p>
     *
     * @param context The application context.
     * @param resId   The resource identifier of the interpolator to load.
     */
    public void setInterpolator(final Context context, final int resId) {
        this.setInterpolator(AnimationUtils.loadInterpolator(context, resId));
    }

    /**
     * Sets the acceleration curve for the indeterminate animation.
     * <p>Defaults to a linear interpolation.</p>
     *
     * @param interpolator The interpolator which defines the acceleration curve.
     */
    public void setInterpolator(final Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public boolean isAnimating() {
        return this.animating;
    }

    @Override
    public void setVisibility(final int visibility) {
        if (this.getVisibility() != visibility) {
            super.setVisibility(visibility);

            if (visibility == View.VISIBLE) {
                this.startAnimation();
            } else {
                this.stopAnimation();
            }
        }
    }

    public void startAnimation() {
        if (!this.animating) {
            this.animating = true;
            this.hasRepeated = false;

            for (final ValueAnimator animator : this.animators) {
                animator.removeAllListeners();
            }

            this.drawables.clear();
            this.animators.clear();

            for (final int color : this.colors) {
                final Drawable drawable = new ColorDrawable(color);

                this.updateDrawables(this.getWidth(), this.getHeight());

                this.drawables.add(drawable);
                this.animators.add(ValueAnimator.ofFloat(0, 1.1f));
            }

            for (int i = 0; i < this.animators.size(); i++) {
                final ValueAnimator animator = this.animators.get(i);

                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setRepeatCount(Animation.INFINITE);
                animator.setDuration(this.duration / this.colors.size() * this.colors.size());
                animator.setInterpolator(this.interpolator);
                animator.setStartDelay(i * this.duration / this.colors.size());
                animator.addListener(this);
                animator.addUpdateListener(this);
                animator.start();
            }

            if (this.getVisibility() == View.VISIBLE) {
                this.postInvalidate();
            }
        }
    }

    public void stopAnimation() {
        this.animating = false;
        this.hasRepeated = false;

        for (final ValueAnimator animator : this.animators) {
            animator.end();

            animator.removeAllListeners();
        }

        this.postInvalidate();
    }

    @Override
    public void onAnimationStart(final Animator animation) {
    }

    @Override
    public void onAnimationEnd(final Animator animation) {
    }

    @Override
    public void onAnimationCancel(final Animator animation) {
    }

    @Override
    public void onAnimationRepeat(final Animator animation) {
        synchronized (this.drawables) {
            synchronized (this.animators) {
                final int index = this.animators.indexOf(animation);

                this.drawables.add(this.drawables.remove(index));
                this.animators.add(this.animators.remove(index));
            }
        }

        this.hasRepeated = true;
    }

    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        this.postInvalidate();
    }

    @Override
    protected synchronized void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (this.animating) {
            synchronized (this.drawables) {
                synchronized (this.animators) {
                    if (this.hasRepeated) {
                        this.drawables.get(0).draw(canvas);
                    }

                    for (int i = 0; i < this.drawables.size(); i++) {
                        final Drawable drawable = this.drawables.get(i);
                        final float fraction = (Float) this.animators.get(i).getAnimatedValue();

                        canvas.save();
                        canvas.clipRect(this.getWidth() / 2f * (1f - fraction), 0, this.getWidth() / 2f * (1f + fraction), this.getHeight());

                        drawable.draw(canvas);

                        canvas.restore();
                    }
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        this.stopAnimation();

        super.onDetachedFromWindow();
    }


    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        this.updateDrawables(w, h);
    }

    private void updateDrawables(final int w, final int h) {
        for (final Drawable drawable : this.drawables) {
            drawable.setBounds(0, 0, w, h);
        }
    }
}
