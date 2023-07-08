package   com.ahmadullahpk.alldocumentreader.widgets.textAnimation;


import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public abstract class HTextView extends AppCompatTextView {
    public abstract void animateText(CharSequence charSequence);

    public abstract void setAnimationListener(AnimationListener animationListener);

    public abstract void setProgress(float f);

    public HTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
