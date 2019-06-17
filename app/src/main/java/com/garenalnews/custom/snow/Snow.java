package com.garenalnews.custom.snow;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Snow {
    private int x;
    private int y;
    private Bitmap bitmap;

    public Snow(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);

    }

    public void move() {
        y++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}