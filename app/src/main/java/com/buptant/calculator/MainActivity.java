package com.buptant.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private CalculatorFragment calculatorFragment;
    private HistoryFragment historyFragment;
    private FragmentManager fragmentManager;

    private ViewPager viewPager;
    private TextView tv_cal;
    private TextView tv_his;
    private MyFragmentAdapter myFragmentAdapter;
    private LinearLayout ll_bottom;
    private long firstTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tv_cal = (TextView) findViewById(R.id.tv_cal);
        tv_his = (TextView) findViewById(R.id.tv_his);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ll_bottom.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();

        List<Fragment> list = new ArrayList<>();
        list.add(new CalculatorFragment());
        list.add(new HistoryFragment());
        myFragmentAdapter = new MyFragmentAdapter(fragmentManager, list);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.setCurrentItem(0);
        setTabSelection(0);
        setViewPagerListener();

        tv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(0);
            }
        });

        tv_his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTabSelection(1);
            }
        });
    }

    private void setViewPagerListener(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("test", "position: " + position);
//                setTabSelection(position);
            }

            @Override
            public void onPageSelected(int position) {
                setTabSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTabSelection(int index) {

        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                tv_cal.setTextColor(Color.RED);
                if (calculatorFragment == null) {
                    calculatorFragment = new CalculatorFragment();
                    transaction.add(R.id.view_pager, calculatorFragment);
                } else {
                    transaction.show(calculatorFragment);
                }
                viewPager.setCurrentItem(0);
                break;
            case 1:
                tv_his.setTextColor(Color.RED);
                if (historyFragment == null) {
                    historyFragment = new HistoryFragment();
                    transaction.add(R.id.view_pager, historyFragment);
                } else {
                    transaction.show(historyFragment);
                }
                viewPager.setCurrentItem(1);
                break;
        }
        transaction.commit();

    }

    private void clearSelection() {
        tv_his.setTextColor(Color.parseColor("#000000"));
        tv_cal.setTextColor(Color.parseColor("#000000"));
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (calculatorFragment != null) {
            transaction.hide(calculatorFragment);
        }
        if (historyFragment != null) {
            transaction.hide(historyFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {      //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {                //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
