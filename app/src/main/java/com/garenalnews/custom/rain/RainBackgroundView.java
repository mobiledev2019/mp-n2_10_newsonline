package com.garenalnews.custom.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hoang on 12/04/2018.
 */

public class RainBackgroundView extends View implements Runnable{
    private Paint paint;

    private RainManager rainManager;
    private Thread thread;

    public RainBackgroundView(Context context) {
        super(context);
        setup();
    }

    public RainBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);

        rainManager = new RainManager(getContext());
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rainManager.drawRain(canvas, paint);
        // drawDemo(canvas);
    }


    @Override
    public void run() {
        while (true) {
            rainManager.moveRain(this.getHeight());
            postInvalidate();

            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
