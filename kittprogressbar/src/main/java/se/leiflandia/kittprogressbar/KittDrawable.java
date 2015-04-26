package se.leiflandia.kittprogressbar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

public class KittDrawable extends Drawable implements Animatable {
    private static final int NUMBER_OF_LIGHTS = 8;
    /** Relative gap (between lights) length */
    private static final float GAP = 0.005f;
    // 30 fps as Glen A. Larson (creator of Knight Rider) intended it to be
    private static final long FRAME_DURATION = 1000 / 30;

    /** Paint for the lights */
    private Paint[] mPaint = new Paint[NUMBER_OF_LIGHTS];
    /** Light intensity */
    private int[] mIntensity = new int[NUMBER_OF_LIGHTS];
    /** Index of turned on light */
    private int current = 0;
    /** True if animation is running */
    private boolean mRunning;

    private int currentDiff = 1;
    private int tick = 0;
    private int intensityDiff = -10;

    /**
     * Updates the animation.
     */
    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < NUMBER_OF_LIGHTS; ++i) {
                mPaint[i].setColor(Color.rgb(mIntensity[i], 0, 0));
                if (i == current) {
                    mIntensity[i] = 255;
                } else {
                    mIntensity[i] = Math.max(0, mIntensity[i] + intensityDiff) % 255;
                }
            }
            if (tick == 0) {
                current = (current + currentDiff) % NUMBER_OF_LIGHTS;
                if (current == NUMBER_OF_LIGHTS - 1 || current == 0) {
                    currentDiff = -currentDiff;
                }
            }
            tick = (tick + 1) % 3;
            if (isRunning()) {
                scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
            }
            invalidateSelf();
        }
    };

    public KittDrawable() {
        for (int i = 0; i < NUMBER_OF_LIGHTS; ++i) {
            mPaint[i] = new Paint();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        float left = 0;
        float height = getBounds().height();
        float gap = GAP * getBounds().width();
        float width = calcWidth(gap);
        for (int i = 0; i < NUMBER_OF_LIGHTS; ++i) {
            RectF rect = new RectF(left, 0, left+width, height);
            canvas.drawRect(rect, mPaint[i]);
            left += width + gap;
        }
    }

    private float calcWidth(float gap) {
        return (getBounds().width() - gap * (NUMBER_OF_LIGHTS - 1)) / (float) NUMBER_OF_LIGHTS;
    }

    @Override
    public void setAlpha(int alpha) {
        for (int i = 0; i < NUMBER_OF_LIGHTS; ++i) {
            mPaint[i].setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        for (int i = 0; i < NUMBER_OF_LIGHTS; ++i) {
            mPaint[i].setColorFilter(cf);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void start() {
        if (isRunning()) return;

        scheduleSelf(mUpdater, SystemClock.uptimeMillis() + FRAME_DURATION);
        invalidateSelf();
    }

    @Override
    public void stop() {
        if (!isRunning()) return;
        mRunning = false;
        unscheduleSelf(mUpdater);
    }

    @Override
    public void scheduleSelf(Runnable what, long when) {
        mRunning = true;
        super.scheduleSelf(what, when);
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }
}
