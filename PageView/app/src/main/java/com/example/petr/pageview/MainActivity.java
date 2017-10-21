package com.example.petr.pageview;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //=======================================================================================
        // getting PageView
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        // get TabLayout - dots
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);

        // binding PageView and dots
        tabLayout.setupWithViewPager(pager, true);

        // init adapter for manage fragments
        PagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        // binding adapter and PageView
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("myLog", "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //=======================================================================================
    }


    //=======================================================================================
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 10;
        }
    }
    //=======================================================================================


}
