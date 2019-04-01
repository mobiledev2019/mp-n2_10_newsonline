package com.garenalnews.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garenalnews.R;
import com.garenalnews.adapter.CustomFragmentPagerAdapter;
import com.garenalnews.common.Config;

import io.karim.MaterialTabs;

/**
 * Created by ducth on 4/4/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentNews extends Fragment {
    private ViewPager viewPager;
    private MaterialTabs tabs;
    private CustomFragmentPagerAdapter customFragmentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_news, container, false);
        initView(view);
        initData();
        initControl();
        return view;
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabs = view.findViewById(R.id.tabs);
    }

    private void initData() {
        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getChildFragmentManager());
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.NEWHOT), "Mới nhất");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.IMPORT), "Nổi bật");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.SOCCER), "Bóng đá");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.WORLD), "Thế giới");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.FINANCE), "Tài chính");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.HEALTH), "Sức khỏe");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.HITECH), "Công nghệ");
        customFragmentPagerAdapter.addFragment(new FramgentNewsDetail(Config.SHOWBIZ), "Showbiz");
        viewPager.setAdapter(customFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(customFragmentPagerAdapter.getCount());
        viewPager.setCurrentItem(0);
        tabs.setTextColorUnselected(getResources().getColor(R.color.colorwhite));
        tabs.setViewPager(viewPager);
    }


    private void initControl() {

    }

}
