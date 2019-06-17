package com.garenalnews.custom.snow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SnowBackgroundView
        extends View
        implements Runnable {
    private Paint paint;

    private SnowManager snowManager;
    private Thread thread;

    public SnowBackgroundView(Context context) {
        super(context);
        setup();
    }

    public SnowBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);

        snowManager = new SnowManager(getContext());
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        snowManager.drawSnows(canvas, paint);
        // drawDemo(canvas);
    }


    @Override
    public void run() {
        while (true) {
            snowManager.moveSnows(this.getHeight());
            postInvalidate();

            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}