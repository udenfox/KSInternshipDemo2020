package com.keepsolid.ksinternshipdemo2020.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.keepsolid.ksinternshipdemo2020.R;
import com.keepsolid.ksinternshipdemo2020.activity.base.BaseActivity;
import com.keepsolid.ksinternshipdemo2020.fragment.FirstFragment;
import com.keepsolid.ksinternshipdemo2020.fragment.SecondFragment;
import com.keepsolid.ksinternshipdemo2020.utils.Constants;
import com.keepsolid.ksinternshipdemo2020.utils.adapter.ViewPagerAdapter;

public class MainActivity extends BaseActivity {

    private FirstFragment fragmentOne;
    private SecondFragment fragmentTwo;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        fragmentOne = FirstFragment.newInstance("Denis", "Shevtsov");
        fragmentTwo = new SecondFragment();

        adapter.addFragment(fragmentOne, "Fragment One");
        adapter.addFragment(fragmentTwo, "Fragment Two");

        viewPager.setAdapter(adapter);

        initToolbar(getString(R.string.app_name));

        tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        getToolbar().setTitle(adapter.getPageTitle(0));

        setListeners();

    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

    }

    private void setListeners() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                getToolbar().setTitle(adapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
