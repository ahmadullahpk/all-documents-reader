package com.ahmadullahpk.alldocumentreader.widgets.textAnimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public class ScaleText extends HText {
    private ValueAnimator animator;
    float charTime = 400.0f;
    private final List<CharacterDiffResult> differentList = new ArrayList<>();
    private long duration;
    float mostCount = 20.0f;


    public void initVariables() {
    }

    public void init(HTextView hTextView, AttributeSet attributeSet, int i) {
        super.init(hTextView, attributeSet, i);
        ValueAnimator valueAnimator = new ValueAnimator();
        this.animator = valueAnimator;
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.animator.addListener(new DefaultAnimatorListener() {
            public void onAnimationEnd(Animator animator) {
                if (ScaleText.this.animationListener != null) {
                    ScaleText.this.animationListener.onAnimationEnd(ScaleText.this.mHTextView);
                }
            }
        });
        this.animator.addUpdateListener(valueAnimator1 -> {
            ScaleText.this.progress = (Float) valueAnimator1.getAnimatedValue();
            ScaleText.this.mHTextView.invalidate();
        });
        int length = this.mText.length();
        if (length <= 0) {
            length = 1;
        }
        float f = this.charTime;
        this.duration = (long) (f + ((f / this.mostCount) * ((float) (length - 1))));
    }

    public void animateText(final CharSequence charSequence) {
        if (this.mHTextView != null && this.mHTextView.getLayout() != null) {
            this.mHTextView.post(() -> {
                if (ScaleText.this.mHTextView != null && ScaleText.this.mHTextView.getLayout() != null) {
                    ScaleText scaleText = ScaleText.this;
                    scaleText.oldStartX = scaleText.mHTextView.getLayout().getLineLeft(0);
                    ScaleText.super.animateText(charSequence);
                }
            });
        }
    }


    public void animatePrepare(CharSequence charSequence) {
        this.differentList.clear();
        this.differentList.addAll(CharacterUtils.diff(this.mOldText, this.mText));
    }


    public void animateStart(CharSequence charSequence) {
        int length = this.mText.length();
        if (length <= 0) {
            length = 1;
        }
        float f = this.charTime;
        this.duration = (long) (f + ((f / this.mostCount) * ((float) (length - 1))));
        this.animator.cancel();
        this.animator.setFloatValues(0.0f, 1.0f);
        this.animator.setDuration(500);
        this.animator.start();
    }

    public void drawFrame(Canvas canvas) {
        float f;
        String str;
        int i;
        float lineLeft = this.mHTextView.getLayout().getLineLeft(0);
        float baseline = (float) this.mHTextView.getBaseline();
        float f2 = this.oldStartX;
        int max = Math.max(this.mText.length(), this.mOldText.length());
        float f3 = lineLeft;
        float f4 = f2;
        int i2 = 0;
        while (i2 < max) {
            if (i2 < this.mOldText.length()) {
                int needMove = CharacterUtils.needMove(i2, this.differentList);
                if (needMove != -1) {
                    this.mOldPaint.setTextSize(this.mTextSize);
                    this.mOldPaint.setAlpha(255);
                    float f5 = this.progress * 2.0f;
                    str = "";
                    float offset = CharacterUtils.getOffset(i2, needMove, f5 > 1.0f ? 1.0f : f5, lineLeft, this.oldStartX, this.gapList, this.oldGapList);
                    f = lineLeft;
                    i = 255;
                    canvas.drawText(this.mOldText.charAt(i2) + str, 0, 1, offset, baseline, this.mOldPaint);
                } else {
                    f = lineLeft;
                    str = "";
                    i = 255;
                    this.mOldPaint.setAlpha((int) ((1.0f - this.progress) * 255.0f));
                    this.mOldPaint.setTextSize(this.mTextSize * (1.0f - this.progress));
                    TextPaint textPaint = this.mOldPaint;
                    float measureText = textPaint.measureText(this.mOldText.charAt(i2) + str);
                    canvas.drawText(this.mOldText.charAt(i2) + str, 0, 1, f4 + ((this.oldGapList.get(i2) - measureText) / 2.0f), baseline, this.mOldPaint);
                }
                f4 += this.oldGapList.get(i2);
            } else {
                f = lineLeft;
                str = "";
                i = 255;
            }
            if (i2 < this.mText.length()) {
                if (!CharacterUtils.stayHere(i2, this.differentList)) {
                    float f6 = (float) i2;
                    int i3 = (int) (((this.progress * ((float) this.duration)) - ((this.charTime * f6) / this.mostCount)) * (255.0f / this.charTime));
                    if (i3 > i) {
                        i3 = 255;
                    }
                    if (i3 < 0) {
                        i3 = 0;
                    }
                    float f7 = ((this.mTextSize) / this.charTime) * ((this.progress * ((float) this.duration)) - ((this.charTime * f6) / this.mostCount));
                    if (f7 > this.mTextSize) {
                        f7 = this.mTextSize;
                    }
                    if (f7 < 0.0f) {
                        f7 = 0.0f;
                    }
                    this.mPaint.setAlpha(i3);
                    this.mPaint.setTextSize(f7);
                    TextPaint textPaint2 = this.mPaint;
                    float measureText2 = textPaint2.measureText(this.mText.charAt(i2) + str);
                    canvas.drawText(this.mText.charAt(i2) + str, 0, 1, f3 + ((this.gapList.get(i2) - measureText2) / 2.0f), baseline, this.mPaint);
                }
                f3 += this.gapList.get(i2);
            }
            i2++;
            lineLeft = f;
        }
    }
}
