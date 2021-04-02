package com.jit.zgy.skillexchange.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.jit.zgy.skillexchange.R;

public class TaskOthersActivity extends AppCompatActivity {
    private MapView task_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_others);
        initMap();
        task_map.onCreate(savedInstanceState);
        AMap aMap = task_map.getMap();//初始化地图控制器对象
    }

    public void initMap(){
        task_map = (MapView)findViewById(R.id.task_map);
    }

    @Override
    protected void onResume(){
        super.onResume();
        task_map.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        task_map.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        task_map.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        task_map.onDestroy();
    }



}
