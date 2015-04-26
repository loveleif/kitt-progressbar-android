package se.leiflandia.kittprogressbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class KittProgressBar extends ProgressBar {
    public KittProgressBar(Context context) {
        this(context, null, 0);
    }

    public KittProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KittProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setIndeterminateDrawable(new KittDrawable());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isIndeterminate() && getIndeterminateDrawable() instanceof KittDrawable &&
                !((KittDrawable) getIndeterminateDrawable()).isRunning()) {
            getIndeterminateDrawable().draw(canvas);
        }
    }
}
