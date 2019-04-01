package com.garenalnews.custom.cloud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hoang on 12/04/2018.
 */

public class CloudBackgroundView extends View implements Runnable{
    private Paint paint;

    private CloudManager cloudManager;
    private Thread thread;

    public CloudBackgroundView(Context context) {
        super(context);
        setup();
    }

    public CloudBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        paint = new Paint();
        paint.setAntiAlias(true);

        cloudManager = new CloudManager(getContext());
        thread = new Thread(this);
        thread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cloudManager.drawCloud(canvas, paint);
        // drawDemo(canvas);
    }


    @Override
    public void run() {
        while (true) {
            cloudManager.moveCloud();
            postInvalidate();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
