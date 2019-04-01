package com.garenalnews.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.garenalnews.R;
import com.garenalnews.common.Config;
import com.garenalnews.model.News;

/**
 * Created by ducth on 4/15/2018.
 */

public class DetailsActivity extends BaseActivity {
    private WebView webView;
    private News news;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        news = (News) getIntent().getSerializableExtra(Config.SEND_NEWS);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        webView = findViewById(R.id.WebView);
    }

    private void initData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải...");
        progressDialog.setCancelable(false);
    }

    private void initControl() {
//        progressDialog.show();
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
//                progressDialog.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                progressDialog.dismiss();
            }
        });
        webView.loadUrl(news.getLink());
    }
}
