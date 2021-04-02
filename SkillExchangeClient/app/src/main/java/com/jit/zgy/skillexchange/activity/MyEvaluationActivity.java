package com.jit.zgy.skillexchange.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.adapter.MyEvaluationAdapter;
import com.jit.zgy.skillexchange.bean.ECEvaluationBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public class MyEvaluationActivity extends AppCompatActivity {
    private MyEvaluationAdapter myEvaluationAdapter;
    private ListView lv_emmyevaluation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_evaluation);
        initView();
        ShowMyEvaluaiton();
    }

    public void initView(){
        lv_emmyevaluation = (ListView)findViewById(R.id.lv_emmyevaluation);
    }
    public void ShowMyEvaluaiton(){
        String url="http://172.20.10.2:8080/SkillExchange/MyEvaluationServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid","0");
        params.put("uid",uid);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Log.i(TAG, "MyEvaluaition" + response);
                    int i;
                    ArrayList<ECEvaluationBean> ecEvaluationBeans = new ArrayList<>();
                    try{
                        JSONArray es = response.getJSONArray("evaluationlist");
                        for(i = 0; i < es.length(); i++){
                            ECEvaluationBean ecEvaluationBean = new ECEvaluationBean();
                            org.json.JSONObject jsonObject = es.getJSONObject(i);
                            ecEvaluationBean.setUavatar(jsonObject.getString("uavatar"));
                            ecEvaluationBean.setUname(jsonObject.getString("uname"));
                            ecEvaluationBean.setComment(jsonObject.getString("comment"));
                            ecEvaluationBean.setEstars(Float.parseFloat(jsonObject.getString("estars")));
                            ecEvaluationBeans.add(ecEvaluationBean);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    myEvaluationAdapter = new MyEvaluationAdapter(MyEvaluationActivity.this,ecEvaluationBeans);
                    lv_emmyevaluation.setAdapter(myEvaluationAdapter);
                }
            }
        });

    }
}
