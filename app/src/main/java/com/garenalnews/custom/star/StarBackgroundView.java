package com.garenalnews.custom.star;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hoang on 12/04/2018.
 */

public class StarBackgroundView extends View implements Runnable{
    private Paint paint;

    private StarManager starManager;
    private Thread thread;

    public StarBackgroundView(Context context) {
        super(context);
        setup();
    }

    public StarBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);

        starManager = new StarManager(getContext());
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        starManager.drawStar(canvas, paint);
    }


    @Override
    public void run() {
        while (true) {
            starManager.moveStar(this.getHeight()-100);
            postInvalidate();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
