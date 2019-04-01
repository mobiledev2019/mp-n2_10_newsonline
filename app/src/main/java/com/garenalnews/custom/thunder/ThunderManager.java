package com.garenalnews.custom.thunder;

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

public class ThunderManager {
    private List<Thunder> thunders;
    private Random random;
    private int widthScreen;
    private int heightScreen;
    private Context context;

    public ThunderManager(Context context) {
        thunders = new ArrayList<>();
        random = new Random();
        this.context = context;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthScreen = metrics.widthPixels;

        initializeThunder();
    }


    public void initializeThunder() {
        for (int i = 0; i < 1; i++) {
            createThunder();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void createThunder() {
        int x = random.nextInt(widthScreen);
        int y = random.nextInt(400);
        Bitmap bitmap = drawableToBitmap(context.getDrawable(R.drawable.ic_thunder_storm));
        thunders.add(new Thunder(bitmap, x, y));
    }


    public void visibleThunder() {
//        for (int i = 0; i < thunders.size(); i++) {
//            Star thunder = thunders.get(i);
//            thunder.move();
//            if (thunder.getX() > widthScreen) {
//                try {
//                    thunders.remove(i);
//                    i--;
//                    createThunder();
//                } catch (IndexOutOfBoundsException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
            int rd = random.nextInt(1);
            thunders.remove(rd);
            createThunder();
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


    public void drawThunder(Canvas canvas, Paint paint) {
        for (int i = 0; i < thunders.size(); i++) {
            try {
                thunders.get(i).draw(canvas, paint);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

}