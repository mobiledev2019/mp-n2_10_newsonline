package com.garenalnews.custom.thunder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hoang on 12/04/2018.
 */

public class ThunderBackgroundView extends View implements Runnable{
    private Paint paint;

    private ThunderManager thunderManager;
    private Thread thread;

    public ThunderBackgroundView(Context context) {
        super(context);
        setup();
    }

    public ThunderBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);

        thunderManager = new ThunderManager(getContext());
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        thunderManager.drawThunder(canvas, paint);
    }


    @Override
    public void run() {
        while (true) {
            thunderManager.visibleThunder();
            postInvalidate();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
