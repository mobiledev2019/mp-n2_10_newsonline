package com.garenalnews.custom.cloud;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;


import com.garenalnews.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloudManager {
    private List<Cloud> clouds;
    private Random random;
    private int widthScreen;
    private int heightScreen;
    private Context context;

    public CloudManager(Context context) {
        clouds = new ArrayList<>();
        random = new Random();
        this.context = context;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        initializeCloud();
    }


    public void initializeCloud() {
        for (int i = 0; i < 4; i++) {
            createCloud();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createCloud() {
        int x = -random.nextInt(800);
        int y = random.nextInt(400);
        clouds.add(new Cloud(randomBm(), x, y));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bitmap randomBm(){
        int rd = random.nextInt(2);
        Bitmap bitmap;
        if (rd == 1) {
            bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_clouds_v));
        } else {
            bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_clouds));
        }
        return bitmap;
    }

    public void moveCloud() {
        for (int i = 0; i < clouds.size(); i++) {
            Cloud cloud = clouds.get(i);
            cloud.move();
            if (cloud.getX() > widthScreen) {
                try {
                    clouds.remove(i);
                    i--;
                    createCloud();
                } catch (IndexOutOfBoundsException e) {
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


    public void drawCloud(Canvas canvas, Paint paint) {
        for (int i = 0; i < clouds.size(); i++) {
            try {
                clouds.get(i).draw(canvas, paint);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

}