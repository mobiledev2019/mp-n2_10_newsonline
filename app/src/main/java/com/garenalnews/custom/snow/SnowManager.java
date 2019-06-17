package com.garenalnews.custom.snow;

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

public class SnowManager {
    private List<Snow> snows;
    private Random random;
    private int widthScreen;
    private Bitmap bitmap;
    private Context context;

    public SnowManager(Context context) {
        snows = new ArrayList<>();

        random = new Random();
        this.context = context;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        initializeSnows();
    }

    public void initializeSnows() {
        for (int i = 0; i < 48; i++) {
            createSnow();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createSnow() {
        int x = random.nextInt(widthScreen);
        int y = -random.nextInt(500);
        bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_snow));
        snows.add(new Snow(bitmap, x, y));
    }


    public void moveSnows(int heightScreen) {
        for (int i = 0; i < snows.size(); i++) {
            Snow snow = snows.get(i);
            snow.move();
            if (snow.getY() > heightScreen) {
                try {
                    snows.remove(i);
                    i--;
                    createSnow();
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

    public void drawSnows(Canvas canvas, Paint paint) {
        for (int i = 0; i < snows.size(); i++) {
            try {
                snows.get(i).draw(canvas, paint);
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }
        }
    }

}