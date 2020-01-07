package com.zhuge.myfilter.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhuge.myfilter.R;

/**
 * 首页
 */
public class HomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * 跳转新房
     */

    public void jumpnewhouse(View view) {
        startActivity(new Intent(this, NewHouseFilterActivity.class));
    }

    /**
     * 跳转二手房
     */

    public void jumpsecondhouse(View view) {
        startActivity(new Intent(this, SecondHouseFilterActivity.class));
    }
}
