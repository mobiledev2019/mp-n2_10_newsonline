package com.garenalnews.custom.rain;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import com.garenalnews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainManager {
    private List<Rain> rains;
    private Random random;
    private int widthScreen;
    private Bitmap bitmap;
    private Context context;

    public RainManager(Context context) {
        rains = new ArrayList<>();
        random = new Random();
        this.context = context;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        initializeRain();
    }



    public void initializeRain() {
        for (int i = 0; i < 100; i++) {
            createRain();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createRain() {
        int x = random.nextInt(widthScreen);
        int y = -random.nextInt(500);
        bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_drop));
        rains.add(new Rain(bitmap, x, y));
    }

    public void moveRain(int heightScreen) {
        for (int i = 0; i < rains.size(); i++) {
            Rain rain = rains.get(i);
            rain.move();
            if (rain.getY() > heightScreen) {
                try{
                    rains.remove(i);
                    i--;
                    createRain();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public void drawRain(Canvas canvas, Paint paint) {
        for (int i = 0; i < rains.size(); i++) {
            try{
                rains.get(i).draw(canvas, paint);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

}