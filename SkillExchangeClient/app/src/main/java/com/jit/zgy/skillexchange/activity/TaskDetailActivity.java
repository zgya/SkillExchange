package com.jit.zgy.skillexchange.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.adapter.ECEvaluationAdapter;
import com.jit.zgy.skillexchange.bean.ECEvaluationBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.BlurBitmapUtil;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView td_warning;
    private CircleImageView td_iv_uavatar;
    private TextView td_uname;
    private TextView tp_tv2;//任务截至时间
    private TextView tp_price;
    private TextView tp_ttitle;
    private TextView td_tdescripotion;
    private View ic_task_state;
    private View ic_tdtprice;
    private ImageView td_iv1_orange;
    private ImageView td_iv1_gray;
    private ImageView td_iv1_orange1;
    private ImageView td_iv1_gray1;
    private ImageView td_iv1_orange2;
    private ImageView td_iv1_gray2;
    private TextView td_tv1;
    private TextView td_tv2;
    private ListView lv_td;
    private ImageView td_printer;
    private TextView td_tv9;
    private TextView td_tvaddress;
    private Button tp_toubiao;
    private List<ECEvaluationBean> ecEvaluationBeanList;
    private ECEvaluationAdapter ecEvaluationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        initView();
        initData();
        ShowTaskDetail();
        ShowTaskState();
        initEvaluaiton();
        WanaBid();
    }

    public void initView(){
        ic_task_state = (View)findViewById(R.id.ic_task_state);
        ic_tdtprice = (View)findViewById(R.id.ic_tdtprice);
        td_warning = (TextView)findViewById(R.id.td_warning);
        td_iv_uavatar = (CircleImageView)findViewById(R.id.td_iv_uavatar);
        td_uname = (TextView)findViewById(R.id.td_uname);
        tp_tv2 = (TextView)findViewById(R.id.tp_tv2);
        tp_ttitle = (TextView)findViewById(R.id.tp_ttitle);
        tp_price = (TextView)ic_tdtprice.findViewById(R.id.tp_price);
        td_tdescripotion = (TextView)findViewById(R.id.td_tdescripotion);
        td_iv1_orange = (ImageView)ic_task_state.findViewById(R.id.td_iv1_orange);
        td_iv1_gray = (ImageView)ic_task_state.findViewById(R.id.td_iv1_gray);
        td_iv1_orange1 = (ImageView)ic_task_state.findViewById(R.id.td_iv1_orange1);
        td_iv1_gray1 = (ImageView)ic_task_state.findViewById(R.id.td_iv1_gray1);
        td_iv1_orange2 = (ImageView)ic_task_state.findViewById(R.id.td_iv1_orange2);
        td_iv1_gray2 = (ImageView)ic_task_state.findViewById(R.id.td_iv1_gray2);
        td_tv1 = (TextView)ic_task_state.findViewById(R.id.td_tv1);
        td_tv2 = (TextView)ic_task_state.findViewById(R.id.td_tv2);
        lv_td = (ListView)findViewById(R.id.lv_td);
        td_printer = (ImageView)findViewById(R.id.td_printer);
        td_tv9 = (TextView)findViewById(R.id. td_tv9);
        td_tvaddress = (TextView)findViewById(R.id.td_tvaddress);
        tp_toubiao = (Button)ic_tdtprice.findViewById(R.id.tp_toubiao);
    }

    public void initData(){
        String td_tvwarning = "技能交换提醒你，请勿与他人进行私下交易，否则资金安全和服务质量将无法得到任何保障。（请确保使用技能交换任务平台完成任务交易，任务酬劳才会由技能交换担保交易平台发放至达人）";
        SpannableString spanText = new SpannableString("图"+td_tvwarning);
        Drawable d = getResources().getDrawable(R.mipmap.warning);
        // 左上右下 控制图片大小
        d.setBounds(0, 0, 30, 30);
        // 替换0,1的字符
        spanText.setSpan(new ImageSpan(d), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        td_warning.append("\n");
        td_warning.append(spanText);
    }

    public void ShowTaskDetail(){
        String tid = getIntent().getStringExtra("tid");
        String url="http://172.20.10.2:8080/SkillExchange/ShowTaskDetailServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("tid",tid);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Log.i(TAG,"任务详细信息:"+response);
                    try {
                        JSONObject jsonObject = response.getJSONObject("taskdetail");
                        String uavatar = jsonObject.getString("uavatar");
                        byte[] bytes = Base64.decode(uavatar, Base64.DEFAULT);
                        td_iv_uavatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        td_uname.setText(jsonObject.getString("uname"));
                        tp_tv2.setText(jsonObject.getString("tdeadlineDate"));
                        tp_price.setText(jsonObject.getString("tprice"));
                        tp_ttitle.setText(jsonObject.getString("ttitle"));
                        td_tdescripotion.setText(jsonObject.getString("tcontent"));
                        td_tvaddress.setText(jsonObject.getString("taddress"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //初始化任务状态
    public void ShowTaskState(){
        int tstate = Integer.parseInt(getIntent().getStringExtra("tstate"));
        if(tstate == 0){
            td_iv1_orange.setVisibility(View.VISIBLE);
            td_iv1_gray.setVisibility(View.INVISIBLE);
            td_iv1_orange1.setVisibility(View.INVISIBLE);
            td_iv1_gray1.setVisibility(View.VISIBLE);
            td_iv1_orange2.setVisibility(View.INVISIBLE);
            td_iv1_gray2.setVisibility(View.VISIBLE);
            td_tv1.setVisibility(View.VISIBLE);
            td_tv2.setVisibility(View.VISIBLE);
        }else if(tstate == 1){
            td_iv1_orange.setVisibility(View.VISIBLE);
            td_iv1_gray.setVisibility(View.INVISIBLE);
            td_iv1_orange1.setVisibility(View.VISIBLE);
            td_iv1_gray1.setVisibility(View.INVISIBLE);
            td_iv1_orange2.setVisibility(View.INVISIBLE);
            td_iv1_gray2.setVisibility(View.VISIBLE);
            td_tv1.setTextColor(getResources().getColor(R.color.orange));
        }else if(tstate == 2){
            td_iv1_orange.setVisibility(View.VISIBLE);
            td_iv1_gray.setVisibility(View.INVISIBLE);
            td_iv1_orange1.setVisibility(View.VISIBLE);
            td_iv1_gray1.setVisibility(View.INVISIBLE);
            td_iv1_orange2.setVisibility(View.VISIBLE);
            td_iv1_gray2.setVisibility(View.INVISIBLE);
            td_tv1.setTextColor(getResources().getColor(R.color.orange));
            td_tv2.setTextColor(getResources().getColor(R.color.orange));
        }
    }

    //初始化任务评论（最多2条：达人和用户各一条评论）
    public void initEvaluaiton(){
        String tid = getIntent().getStringExtra("tid");
        String url="http://172.20.10.2:8080/SkillExchange/ShowEvaluationServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("tid",tid);
        params.put("method",2);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Log.i(TAG,"任务评论信息:"+response);
                    int i;
                    ecEvaluationBeanList = new ArrayList<ECEvaluationBean>();
                    try {
                        JSONArray evaluationlist2 = response.getJSONArray("evaluationlist2");
                        for(i = 0; i < evaluationlist2.length(); i++){
                            ECEvaluationBean ecEvaluationBean = new ECEvaluationBean();
                            JSONObject jsonObject = evaluationlist2.getJSONObject(i);
                                ecEvaluationBean.setUavatar(jsonObject.getString("uavatar"));
                                ecEvaluationBean.setUname(jsonObject.getString("uname"));
                                ecEvaluationBean.setComment(jsonObject.getString("comment"));
                                ecEvaluationBeanList.add(ecEvaluationBean);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(ecEvaluationBeanList.size() != 0){
                        td_printer.setVisibility(View.INVISIBLE);
                        td_tv9.setVisibility(View.INVISIBLE);
                        lv_td.setVisibility(View.VISIBLE);
                        ecEvaluationAdapter = new ECEvaluationAdapter(TaskDetailActivity.this,ecEvaluationBeanList);
                        lv_td.setAdapter(ecEvaluationAdapter);
                    }else {
                        td_printer.setVisibility(View.VISIBLE);
                        td_tv9.setVisibility(View.VISIBLE);
                        lv_td.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    public void WanaBid(){
        tp_toubiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TaskDetailActivity.this, R.style.SquareEntranceDialogStyle);
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
                    blurBg = BlurBitmapUtil.blur(TaskDetailActivity.this, bmp);
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
                View layout = View.inflate(TaskDetailActivity.this, R.layout.wanaprice_layout, null);
                dialog.setContentView(layout);
                dialog.show();
                //点击确认
                dialog.getWindow().findViewById(R.id.tp_toubiao).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();//，退出dialog，返回主界面
                        Toast.makeText(TaskDetailActivity.this,"投标成功",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

}
