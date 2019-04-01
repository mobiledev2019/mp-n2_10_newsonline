package com.garenalnews.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garenalnews.R;
import com.garenalnews.common.Config;
import com.garenalnews.common.Untils;
import com.garenalnews.fragment.FragmentNews;
import com.garenalnews.model.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private User user;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private ImageView imAvata;
    private LinearLayout lo_logout;
    private LinearLayout lo_News;
//    private LinearLayout lo_weather;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentNews fragmentNews;
//    private FragmentWeather fragmentWeather;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (User) getIntent().getExtras().getSerializable(Config.SEND_USER);
        Untils.saveUser(this, user);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        toolbar = findViewById(R.id.Toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        imAvata = findViewById(R.id.imAvata);
        lo_logout = findViewById(R.id.lo_logout);
        lo_News = findViewById(R.id.lo_news);
//        lo_weather = findViewById(R.id.lo_weather);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void initData() {
        fragmentNews = new FragmentNews();
//        fragmentWeather = new FragmentWeather();
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorwhite));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        updateUser(user);
        replaceFragment(fragmentNews);
    }

    public void updateUser(User user) {
        switch (user.getTypeLogin()) {
            case Config.LOGIN_FB:
                if (!user.getImAvata().isEmpty()) {
                    try {
                        URL fb_url = new URL(user.getImAvata().toString());//small | noraml | large
                        HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
                        HttpsURLConnection.setFollowRedirects(true);
                        conn1.setInstanceFollowRedirects(true);
                        Bitmap fb_img = null;
                        fb_img = BitmapFactory.decodeStream(conn1.getInputStream());
                        imAvata.setImageBitmap(fb_img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!user.getName().isEmpty()) {
                        tvName.setText("Xin chào, " + user.getName());
                    } else {
                        tvName.setVisibility(View.GONE);
                    }
                    if (!user.getEmail().isEmpty()) {
                        tvEmail.setText(user.getEmail().toString());
                    } else {
                        tvEmail.setVisibility(View.GONE);
                    }
                    if (!user.getPhone().isEmpty()) {
                        tvPhone.setText(user.getPhone().toString());
                    } else {
                        tvPhone.setVisibility(View.GONE);
                    }
                }
                break;
            case Config.LOGIN_EMAIL:
                if (!user.getName().isEmpty()) {
                    tvName.setText("Xin chào, " + user.getName());
                } else {
                    tvName.setVisibility(View.GONE);
                }
                if (!user.getEmail().isEmpty()) {
                    tvEmail.setText(user.getEmail().toString());
                } else {
                    tvEmail.setVisibility(View.GONE);
                }
                if (!user.getPhone().isEmpty()) {
                    tvPhone.setText(user.getPhone().toString());
                } else {
                    tvPhone.setVisibility(View.GONE);
                }
                if (!user.getImAvata().isEmpty()) {
                    Picasso.with(this).load(Uri.parse(user.getImAvata().toString())).into(imAvata);
                } else {
                    Picasso.with(this).load(R.mipmap.ic_user).into(imAvata);
                }
                break;
            case Config.LOGIN_PHONE:
                tvEmail.setVisibility(View.GONE);
                tvEmail.setVisibility(View.GONE);
                tvPhone.setText("Xin chào, " + user.getPhone());
                Picasso.with(this).load(R.mipmap.ic_user).into(imAvata);
                break;
        }
    }

    private void initControl() {
//        lo_weather.setOnClickListener(this);
        lo_logout.setOnClickListener(this);
        lo_News.setOnClickListener(this);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        toolbar.setTitle(getResources().getString(R.string.news));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.showFragment, fragment);
        ft.commit();
    }

    public void addFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.showFragment, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.lo_weather:
//                toolbar.setTitle(getResources().getString(R.string.weather));
//                replaceFragment(new FragmentWeather());
//                drawerLayout.closeDrawer(Gravity.LEFT);
//                break;
            case R.id.lo_news:
                toolbar.setTitle(getResources().getString(R.string.news));
                replaceFragment(fragmentNews);
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
            case R.id.lo_logout:
                drawerLayout.closeDrawer(Gravity.LEFT);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle(getString(R.string.app_name));
                alertDialog.setMessage("Bạn có muốn đăng xuất ?");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, MainLogin.class);
                        Untils.saveUser(MainActivity.this, null);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }
}
