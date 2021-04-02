package com.jit.zgy.skillexchange.activity;


import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.Tag;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.adapter.MyFragmentPagerAdapter;
import com.jit.zgy.skillexchange.utils.BlurBitmapUtil;
import com.jit.zgy.skillexchange.view.RatingBar;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener{
    public static final  String TAG="MainActivity";
    //UI Objects
    private RadioGroup rg_tab_bar;
    private RadioButton rb_shouye;
    private RadioButton rb_daren;
    private RadioButton rb_message;
    private RadioButton rb_mine;
    private ViewPager vpager;
    private ImageView civ_dp14;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        rb_shouye.setChecked(true);
    }


    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_shouye = (RadioButton) findViewById(R.id.rb_shouye);
        rb_daren = (RadioButton) findViewById(R.id.rb_daren);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_shouye:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_daren:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rb_mine:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
            case R.id.rb_plus:
                ShowDpTask();
                break;
        }
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_shouye.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_daren.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_message.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_mine.setChecked(true);
                    break;
            }
        }
    }

    //获取当前屏幕截图
    public Bitmap getBitmap(){
        View activityView = getWindow().getDecorView();
        activityView.setDrawingCacheEnabled(true);
        activityView.destroyDrawingCache();
        activityView.buildDrawingCache();
        Bitmap bmp = activityView.getDrawingCache();
        return bmp;
    }

    public void ShowDpTask(){
        final Dialog dialog = new Dialog(MainActivity.this, R.style.SquareEntranceDialogStyle);
        Window window = dialog.getWindow();
        Bitmap blurBg = null;
        if (window != null) {
            long startMs = System.currentTimeMillis();
            // 获取截图
            View activityView = getWindow().getDecorView();
            activityView.setDrawingCacheEnabled(true);
            activityView.destroyDrawingCache();
            activityView.buildDrawingCache();
            Bitmap bmp = activityView.getDrawingCache();
            Log.d(TAG,"getDrawingCache take away:" + (System.currentTimeMillis() - startMs) + "ms");
            // 模糊处理并保存
            blurBg = BlurBitmapUtil.blur(MainActivity.this, bmp);
            Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
            // 设置成dialog的背景
            window.setBackgroundDrawable(new BitmapDrawable(getResources(), blurBg));
            bmp.recycle();
        }
        final Bitmap finalBlurBg = blurBg;
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // 对话框取消时释放背景图bitmap
                if (finalBlurBg != null && !finalBlurBg.isRecycled()) {
                    finalBlurBg.recycle();
                }
            }
        });
        View layout = View.inflate(MainActivity.this, R.layout.activity_dispatch_task, null);
        dialog.setContentView(layout);
        dialog.show();
        //在dialog中添加点击事件
        //点击X
        dialog.getWindow().findViewById(R.id.civ_dp14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();//，退出dialog，返回主界面
            }
        });

        //点击清洁打扫
        dialog.getWindow().findViewById(R.id.civ_dp1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TaskCleanActivity.class));
            }
        });

        //点击“其他”
        dialog.getWindow().findViewById(R.id.civ_dp13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TaskOthersActivity.class);
                startActivity(intent);
            }
        });
    }



}
