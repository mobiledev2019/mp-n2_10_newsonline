package com.garenalnews.custom.star;

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

public class StarManager {
    private List<Star> stars;
    private Random random;
    private int widthScreen;
    private Context context;

    public StarManager(Context context) {
        stars = new ArrayList<>();
        random = new Random();
        this.context = context;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        initializeStar();
    }


    public void initializeStar() {
        for (int i = 0; i < 2; i++) {
            createStar();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void createStar() {
        int x = random.nextInt(widthScreen);
        int y = -random.nextInt(400);
        Bitmap bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_shooting_star));
        stars.add(new Star(bitmap, x, y));
    }


    public void moveStar(int heightScreen) {
        for (int i = 0; i < stars.size(); i++) {
            Star star = stars.get(i);
            star.move();
            if (star.getY() > heightScreen) {
                try {
                    stars.remove(i);
                    i--;
                    createStar();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
        }    }


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


    public void drawStar(Canvas canvas, Paint paint) {
        for (int i = 0; i < stars.size(); i++) {
            try {
                stars.get(i).draw(canvas, paint);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

}