package com.garenalnews.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ducth on 3/15/2018.
 */

@SuppressLint("AppCompatCustomView")
public class CustomTextViewBold extends TextView {
    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "UTM_HelveBold.ttf"));
    }
}
