package com.garenalnews.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.garenalnews.R;
import com.garenalnews.model.News;

/**
 * Created by ducth on 4/15/2018.
 */

public class DialogShare extends Dialog implements View.OnClickListener {
    private OnResult onResult;
    private View loContent;
    private LinearLayout lo_fb;
    private LinearLayout lo_Gmail;
    private LinearLayout lo_zalo;
    private LinearLayout lo_messenger;

    public DialogShare(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_share);
        initView();
        initControl();
    }

    @SuppressLint("NewApi")
    public static DialogShare newInstance(Context context, OnResult onResult) {
        DialogShare dialogShare = new DialogShare(context, R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        dialogShare.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogShare.onResult = onResult;
        return dialogShare;
    }


    public interface OnResult {
        void facebook();

        void gmail();

        void zalo();

        void messenger();
    }

    private void initControl() {
        /*set animation*/
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.translate_up);
        loContent.setAnimation(animation);
        /**/
        lo_fb.setOnClickListener(this);
        lo_Gmail.setOnClickListener(this);
        lo_zalo.setOnClickListener(this);
        lo_messenger.setOnClickListener(this);
    }

    private void initView() {
        loContent = findViewById(R.id.loContent);
        lo_fb = findViewById(R.id.lo_fb);
        lo_Gmail = findViewById(R.id.lo_Gmail);
        lo_zalo = findViewById(R.id.lo_zalo);
        lo_messenger = findViewById(R.id.lo_messenger);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lo_fb:
                onResult.facebook();
                break;
            case R.id.lo_Gmail:
                onResult.gmail();
                break;
            case R.id.lo_zalo:
                onResult.zalo();
                break;
            case R.id.lo_messenger:
                onResult.messenger();
                break;
        }
        dismiss();
    }
}
