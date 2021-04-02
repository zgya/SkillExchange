package com.jit.zgy.skillexchange.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.api.maps2d.MapView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.ShengBean;
import com.jit.zgy.skillexchange.bean.TaskBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.GetJsonDataUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TaskCleanActivity extends AppCompatActivity{
    private MapView tctask_map;
    private View ic_tc_mp1;
    private View ic_tc_mp2;
    private View ic_tc_mp3;
    private TextView tv_bednumber;
    private TextView tv_bathnumber;
    private TextView tv_kicnumber;
    private ImageView iv_bed1;
    private ImageView iv_bath1;
    private ImageView iv_kic1;
    private ImageView iv_bed2;
    private ImageView iv_bath2;
    private ImageView iv_kic2;
    private ImageView tc_arrowdown;
    private TextView tc_tv4;
    private EditText tc_ettcity;
    private Button bt_tcetegeory1;
    private Button bt_tcetegeory2;
    private EditText et_categeory;
    private EditText et_standard;
    private Button bt_tstandard1;
    private Button bt_tstandard2;
    private Button bt_tstandard3;
    private EditText et_tdeadline;
    private EditText et_title;
    private TextView tc_ditan;
    private CheckBox cb1;
    private Button tc_bdispatchtask;
    private EditText tc_tpeice;
    private EditText tc_beizhu;
    //  省
    private List<ShengBean> options1Items = new ArrayList<ShengBean>();
    //  市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    //  区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    TimePickerView pvTime;
    int num1 = 0;
    int num2 = 0;
    int num3 = 0;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_clean);
        initView();
        tctask_map.onCreate(savedInstanceState);
        initListener();
    }
    public void initView(){
        tctask_map = (MapView)findViewById(R.id.tctask_map);
        ic_tc_mp1 = (View)findViewById(R.id.ic_tc_mp1);
        ic_tc_mp2 = (View)findViewById(R.id.ic_tc_mp2);
        ic_tc_mp3 = (View)findViewById(R.id.ic_tc_mp3);
        tv_bednumber = (TextView)ic_tc_mp1.findViewById(R.id.tv_number);
        tv_bathnumber = (TextView)ic_tc_mp2.findViewById(R.id.tv_number);
        tv_kicnumber = (TextView)ic_tc_mp3.findViewById(R.id.tv_number);
        iv_bed1 = (ImageView)ic_tc_mp1.findViewById(R.id.iv_1);
        iv_bed2 = (ImageView) ic_tc_mp1.findViewById(R.id.iv_2);
        iv_bath1 = (ImageView) ic_tc_mp2.findViewById(R.id.iv_1);
        iv_bath2 = (ImageView) ic_tc_mp2.findViewById(R.id.iv_2);
        iv_kic1 = (ImageView) ic_tc_mp3.findViewById(R.id.iv_1);
        iv_kic2 = (ImageView) ic_tc_mp3.findViewById(R.id.iv_2);
        tc_arrowdown = (ImageView)findViewById(R.id.tc_arrowdown);
        tc_tv4 = (TextView)findViewById(R.id.tc_tv4);
        tc_ettcity = (EditText)findViewById(R.id.tc_ettcity);
        bt_tcetegeory1 = (Button)findViewById(R.id.bt_tcetegeory1);
        bt_tcetegeory2 = (Button)findViewById(R.id.bt_tcetegeory2);
        et_categeory = (EditText)findViewById(R.id.et_categeory);
        et_standard = (EditText)findViewById(R.id.et_standard);
        bt_tstandard1 = (Button)findViewById(R.id.bt_tstandard1);
        bt_tstandard2 = (Button)findViewById(R.id.bt_tstandard2);
        bt_tstandard3 = (Button)findViewById(R.id.bt_tstandard3);
        et_tdeadline = (EditText)findViewById(R.id.et_tdeadline);
        et_title = (EditText)findViewById(R.id.et_title);
        tc_ditan = (TextView)findViewById(R.id.tc_ditan);
        cb1 = (CheckBox)findViewById(R.id.cb1);
        tc_bdispatchtask = (Button)findViewById(R.id.tc_bdispatchtask);
        tc_tpeice = (EditText)findViewById(R.id.tc_tpeice);
        tc_beizhu = (EditText)findViewById(R.id.tc_beizhu);
    }

    @Override
    protected void onResume(){
        super.onResume();
        tctask_map.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        tctask_map.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        tctask_map.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        tctask_map.onDestroy();
    }

    public void initListener(){
        iv_bed1.setOnClickListener(new View.OnClickListener() {
            @Override
            //-
            public void onClick(View v) {
                num1=Integer.parseInt(tv_bednumber.getText().toString());
                if(num1>0){
                    num1-=1;
                }
                tv_bednumber.setText(Integer.toString(num1));
            }
        });
        iv_bed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=Integer.parseInt(tv_bednumber.getText().toString());
                if(num1<5){
                    num1++;
                }
                tv_bednumber.setText(Integer.toString(num1));
            }
        });
        iv_bath1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num2=Integer.parseInt(tv_bathnumber.getText().toString());
                if(num2>0)
                    num2--;
                tv_bathnumber.setText(Integer.toString(num2));
            }
        });
        iv_bath2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num2=Integer.parseInt(tv_bathnumber.getText().toString());
                if(num2<5)
                    num2++;
                tv_bathnumber.setText(Integer.toString(num2));
            }
        });
        iv_kic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num3=Integer.parseInt(tv_kicnumber.getText().toString());
                if(num3>0)
                    num3--;
                tv_kicnumber.setText(Integer.toString(num3));
            }
        });
        iv_kic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num3=Integer.parseInt(tv_kicnumber.getText().toString());
                if(num3<5)
                    num3++;
                tv_kicnumber.setText(Integer.toString(num3));
            }
        });
        //弹出选择器
        tc_arrowdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseData();
                showPickerView();
            }
        });

        //任务类型
        bt_tcetegeory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_categeory.setText(bt_tcetegeory1.getText().toString());
            }
        });

        bt_tcetegeory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_categeory.setText(bt_tcetegeory2.getText().toString());
            }
        });

        bt_tstandard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_standard.setText(bt_tstandard1.getText().toString());
            }
        });

        bt_tstandard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_standard.setText(bt_tstandard2.getText().toString());
            }
        });

        bt_tstandard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_standard.setText(bt_tstandard3.getText().toString());
            }
        });
        //选择任务截止时间
        et_tdeadline.setFocusable(false);
        et_tdeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerView();
            }
        });

        //点击发布任务
        tc_bdispatchtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskBean taskBean = new TaskBean();
                taskBean.setTtitle(et_title.getText().toString());
                taskBean.setTprice(Float.parseFloat(tc_tpeice.getText().toString()));
                taskBean.setTdeadlineDate(et_tdeadline.getText().toString());
                String taddress = tc_tv4.getText().toString()+tc_ettcity.getText().toString();
                taskBean.setTaddress(taddress);
                StringBuilder sb = new StringBuilder();
                sb.append(et_categeory.getText().toString()).append(et_standard.getText().toString());
                if(!(tv_bednumber.getText().toString().equals(""))){
                    sb.append(",").append(tv_bednumber.getText().toString()).append("个卧室");
                }
                if(!(tv_bathnumber.getText().toString().equals(""))){
                    sb.append(",").append(tv_bathnumber.getText().toString()).append("个浴室");
                }
                if(!(tv_kicnumber.getText().toString().equals(""))){
                    sb.append(",").append(tv_kicnumber.getText().toString()).append("个厨房");
                }
                if(cb1.isChecked()){
                    sb.append(",").append("准备蒸汽清晰地毯").append("。");
                }
                sb.append(tc_beizhu.getText().toString());
                taskBean.setTcontent(sb.toString());
                taskBean.setTbids(0);
                SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
                int uid = Integer.parseInt(sharedPreferences.getString("uid",""));
                taskBean.setUid(uid);

                String url="http://172.20.10.2:8080/SkillExchange/TaskInsertServlet";
                Gson gson = new Gson();
                String insertTask = gson.toJson(taskBean);
                RequestParams params = new RequestParams(); // 绑定参数
                params.put("insertTask",insertTask);
                HttpUtil.get(url,params,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode==200){
                            Toast.makeText(TaskCleanActivity.this,"任务发布成功",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(TaskCleanActivity.this,MainActivity.class));
                        }
                    }
                });
            }
        });
    }

    /**
     * 解析数据并组装成自己想要的list
     */
    private void parseData(){
        String jsonStr = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
//     数据解析
        Gson gson =new Gson();
        java.lang.reflect.Type type =new TypeToken<List<ShengBean>>(){}.getType();
        List<ShengBean>shengList=gson.fromJson(jsonStr, type);
//     把解析后的数据组装成想要的list
        options1Items = shengList;
//     遍历省
        for(int i = 0; i <shengList.size() ; i++) {
//         存放城市
            ArrayList<String> cityList = new ArrayList<>();
//         存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
//         遍历市
            for(int c = 0; c <shengList.get(i).city.size() ; c++) {
//        拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    /**
     * 展示选择器
     */
    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
//                Toast.makeText(TaskCleanActivity.this, tx, Toast.LENGTH_SHORT).show();
                tc_tv4.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    //选择任务截至时间
    public void showTimePickerView(){
        Calendar selectedDate = Calendar.getInstance();

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = simpleDateFormat.format(date);
                et_tdeadline.setText(format);
            }
        }).setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(R.color.text_gray)//确定按钮文字颜色
                .setCancelColor(R.color.text_gray)//取消按钮文字颜色
//                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
                pvTime.show();

    }
}
