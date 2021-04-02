package com.jit.zgy.skillexchange.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.adapter.SettingsAdapter;
import com.jit.zgy.skillexchange.bean.SettingsBean;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    List<SettingsBean>  settingsBeanList;
    private ListView lv_settings;
    int iv_settings[] = {R.mipmap.lock,R.mipmap.lock,R.mipmap.good,R.mipmap.callback,R.mipmap.aboutus};
    String tv_settings[] = {"修改登录密码","忘记密码","给软件打分","反馈与帮助","关于我们"};
    int iv_settings1[] = {R.mipmap.right_arrow,R.mipmap.right_arrow,R.mipmap.right_arrow,R.mipmap.right_arrow,R.mipmap.right_arrow};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.setTitle("账户设置");
        initView();
        initData();
        SettingsAdapter settingsAdapter = new SettingsAdapter(SettingsActivity.this,R.layout.settings_item,settingsBeanList);
        lv_settings.setAdapter(settingsAdapter);

    }

    public void initView(){
        lv_settings = (ListView)findViewById(R.id.lv_settings);
    }

    public void initData(){
        settingsBeanList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            SettingsBean settingsBean = new SettingsBean(iv_settings[i],tv_settings[i],iv_settings1[i]);
            settingsBeanList.add(settingsBean);
        }
    }
}
