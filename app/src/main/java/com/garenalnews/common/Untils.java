package com.garenalnews.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.garenalnews.model.User;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ducth on 3/15/2018.
 */

public class Untils {
    public static String DateTimeFormat(String styleIn, String styleOut, String inDate, Locale locale) {
        String outDate = "";
        SimpleDateFormat in = new SimpleDateFormat(styleIn, locale);
        SimpleDateFormat out = new SimpleDateFormat(styleOut, locale);
        if (inDate != null) {
            try {
                Date date = in.parse(inDate);
                outDate = out.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return outDate;
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String converstTime(float duration) {
        int second = (int) (duration / 1000);
        String timeOut = "";
        int hour = 0;
        int minute = second / 60;
        if (minute > 60) {
            hour = minute / 60;
            timeOut = converstTwoNumber(hour) +
                    ":" + converstTwoNumber(minute % 60) +
                    ":" + converstTwoNumber(second % 60);
        } else {
            timeOut = converstTwoNumber(second / 60) +
                    ":" + converstTwoNumber(second % 60);
        }
        return timeOut;
    }

    public static String converstTwoNumber(int number) {
        String out = number + "";
        if (out.length() == 1) {
            out = "0" + out;
        }
        return out;
    }

    public static void saveUser(Context context, User user) {
        Gson gson = new Gson();
        String profile = gson.toJson(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.PREF, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SAVE_USER, profile).commit();
    }

    public static User getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.PREF, context.MODE_PRIVATE);
        String profile = sharedPreferences.getString(Config.SAVE_USER, "");
        Gson gson = new Gson();
        User user = gson.fromJson(profile, User.class);
        return user;
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }
}

