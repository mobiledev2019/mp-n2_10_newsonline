package com.garenalnews.custom.cloud;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by hoang on 12/04/2018.
 */

public class Cloud {
    private int x;
    private int y;

    private Bitmap bitmap;

    public Cloud(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public void move() {
        x++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
