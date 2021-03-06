package com.garenalnews.custom.star;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by hoang on 12/04/2018.
 */

public class Star {
    private int x;
    private int y;

    private Bitmap bitmap;

    public Star(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;

    }
    public void move(){
        x--;
        y++;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
