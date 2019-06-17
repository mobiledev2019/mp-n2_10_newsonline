package com.garenalnews.activity;

import android.content.res.Configuration;
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
import com.garenalnews.fragment.FragmentNews;
import com.garenalnews.model.User;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout lo_logout;
    private LinearLayout lo_News;
//    private LinearLayout lo_weather;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentNews fragmentNews;
//    private FragmentWeather fragmentWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initControl();
    }

    private void initView() {
        toolbar = findViewById(R.id.Toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
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
        replaceFragment(fragmentNews);

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


        }
    }
}
