package com.ahmadullahpk.alldocumentreader.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.widget.ProgressBar;

import androidx.appcompat.content.res.AppCompatResources;

import com.ahmadullahpk.alldocumentreader.R;

public class ViewUtils {

    public static final float INT_BITS_TO_FLOAT = Float.intBitsToFloat(1);


    public enum CornerRadius {
        AllSide,
        OnlyTop,
        OnlyBottom
    }

    public static int getDimensions(Resources resources) {
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static Bitmap createDrawableFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static boolean getScreenLayout(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) == 4;
    }


    public static void updateProgressBar(ProgressBar progressBar, int i, int i2, int i3) {
        progressBar.setProgress(0);
        ShapeDrawable shapeDrawable =
                new ShapeDrawable(
                        new RoundRectShape(
                                new float[]{15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f, 15.0f},
                                null,
                                null));

        LinearGradient linearGradient =
                new LinearGradient(INT_BITS_TO_FLOAT, INT_BITS_TO_FLOAT,
                        (float) progressBar.getMeasuredWidth(),
                        INT_BITS_TO_FLOAT, i2, i3, Shader.TileMode.CLAMP);
        shapeDrawable.getPaint().setDither(true);
        shapeDrawable.getPaint().setShader(linearGradient);
        progressBar.setProgressDrawable(new ClipDrawable(shapeDrawable, 3, 1));
        progressBar.setBackground(AppCompatResources.getDrawable(progressBar.getContext(), R.drawable.progress_background));
        progressBar.refreshDrawableState();
        progressBar.setProgress(i);
    }


    public static int setWidth(boolean z, boolean z2) {
        int i = z2 ? 6918 : 256;
        if (Build.VERSION.SDK_INT < 23) {
            return i;
        }
        int i2 = i | 1024;
        if (z) {
            i2 |= 8192;
        }
        return i2;
    }

}
