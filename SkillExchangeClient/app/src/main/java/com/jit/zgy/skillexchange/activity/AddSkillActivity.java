package com.jit.zgy.skillexchange.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.SkillBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.GetJsonDataUtil;
import com.jit.zgy.skillexchange.utils.StringUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cz.msebera.android.httpclient.Header;

public class AddSkillActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView addskill_plus;
    private LinearLayout linear_addskill;
    private Toolbar addskill_toolbar;
    private ActionBar actionBar;

    private ArrayList<SkillBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<String> bankNameList = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private String Scategory;
    private String skills;
    private String uskills;
    private int i = 0;
    private StringBuffer sb = new StringBuffer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addskill);
        initView();
        initData();
        addskill_plus.setOnClickListener(this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    public void initView(){
        addskill_plus = findViewById(R.id.addskill_plus);
        linear_addskill = findViewById(R.id.linear_addskill);
        addskill_toolbar = findViewById(R.id.addskill_toolbar);
    }

    public void initData(){
        addskill_toolbar.setTitle("我的技能标签");
        setSupportActionBar(addskill_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addskill_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddSkillActivity.this,MainActivity.class));
                SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
                uskills = sharedPreferences.getString("uskills","技能为空");
                Toast.makeText(AddSkillActivity.this,uskills,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.addskill_plus:
                AddSkillPickerView();
                break;


        }
    }



    //判断地址数据是否获取成功
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread==null){//如果已创建就不再重新创建子线程了
                        Log.i("addr","地址数据开始解析");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Log.i("addr","地址数据获取成功");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Log.i("addr","地址数据获取失败");
                    break;

            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String CityData = new GetJsonDataUtil().getJson(this,"skills.json");//获取assets目录下的json文件数据

        ArrayList<SkillBean> jsonBean = parseData(CityData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getSkills().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getSkills().get(c);
                CityList.add(CityName);//添加城市

            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    public ArrayList<SkillBean> parseData(String result) {//Gson 解析
        ArrayList<SkillBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                SkillBean entity = gson.fromJson(data.optJSONObject(i).toString(), SkillBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    //用户动态添加技能标签,弹出选择器
    public void AddSkillPickerView(){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Scategory = options1Items.get(options1).getPickerViewText();
                skills = options2Items.get(options1).get(options2);
//                        + options3Items.get(options1).get(options2).get(options3);
                //选定后显示  显示的位置，也可以从这里取值 mText是一个xml的textview的id
                AddViewSkillLabel(skills);

            }
        })
                //这几个值没需求的可以不要
                .setDividerColor(Color.BLACK)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                // default is true
                .setOutSideCancelable(false)
                .build();//这个不能丢

        //二级选择器（市区）
        pvOptions.setPicker(options1Items, options2Items);
        //pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();

        }

    //动态添加技能标签
        public void AddViewSkillLabel(final String skill){
            final Button im = new Button(this);
            im.setText(skill);
            im.setTextSize(12f);
            im.setGravity(Gravity.CENTER);
            im.setBackground(getResources().getDrawable(R.mipmap.skillbg));
            linear_addskill.addView(im);
            sb.append(im.getText().toString());
            sb.append(",");
             //长按删除
            im.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    sb = StringUtil.deleteSubString(sb,im.getText().toString());
                    im.setVisibility(View.GONE);
                    return true;
                }
            });
            //将String技能放到轻量级数据库
            SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uskills",sb.toString());
            editor.commit();
        }

        public void InsertSkills(String uskills){
            String url = "http://172.20.10.2:8080/SkillExchange/AddskillServlet";
            RequestParams params = new RequestParams(); // 绑定参数
            SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);;
            int uid = sharedPreferences.getInt("uid",0);
            params.put("uid",uid);
            params.put("uskills",uskills);
            HttpUtil.get(url,params,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    if (statusCode==200){
                            Toast.makeText(AddSkillActivity.this,"添加技能标签成功",Toast.LENGTH_LONG).show();
                        }
                    }

            });

        }
}
