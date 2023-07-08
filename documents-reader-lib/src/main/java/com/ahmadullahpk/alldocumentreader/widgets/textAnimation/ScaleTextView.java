package   com.ahmadullahpk.alldocumentreader.widgets.textAnimation;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;

public class ScaleTextView extends HTextView {
    private ScaleText scaleText;

    public ScaleTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ScaleTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScaleTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        ScaleText scaleText2 = new ScaleText();
        this.scaleText = scaleText2;
        scaleText2.init(this, attributeSet, i);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.scaleText.setAnimationListener(animationListener);
    }

    protected void onDraw(Canvas canvas) {
        this.scaleText.onDraw(canvas);
    }

    public void setProgress(float f) {
        this.scaleText.setProgress(f);
    }

    public void animateText(CharSequence charSequence) {
        this.scaleText.animateText(charSequence);
    }
}
