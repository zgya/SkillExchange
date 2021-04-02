package com.jit.zgy.skillexchange.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt_login;
    Button bt_register;
    EditText et_uphone;
    EditText et_upassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initComponent();
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
    }

    //初始化组件
    public void initComponent(){
        bt_login = (Button)findViewById(R.id.bt_login);
        bt_register = (Button)findViewById(R.id.bt_register);
        et_uphone = (EditText)findViewById(R.id.et_uphone);
        et_upassword = (EditText)findViewById(R.id.et_upassword);

    }

    public void connectionURL(final String uphone, final String upassword){

        String url="http://172.20.10.2:8080/SkillExchange/LoginServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("uphone",uphone);
        params.put("upassword",upassword);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("login_result")==true){
                            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                            SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uphone",uphone);
                            editor.commit();
                        }
                        else {
                            Toast.makeText(WelcomeActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getUid(String uphone){
        String url="http://172.20.10.2:8080/SkillExchange/UidServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("uphone",uphone);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        String uid = response.getString("uid");
                        if(uid != null){
                            SharedPreferences sharedPreferences = getSharedPreferences("skillexchange",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("uid",uid);
                            editor.commit();

                        }
                        else {
                            Toast.makeText(WelcomeActivity.this, "获取uid失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登陆
            case R.id.bt_login:
                Intent it0=new Intent();
                it0.setClass(WelcomeActivity.this,MainActivity.class);
                String uphone=et_uphone.getText().toString().trim();
                String upassword=et_upassword.getText().toString().trim();
                connectionURL(uphone,upassword);
                getUid(uphone);
                //  startActivity(it0);
                break;
            case R.id.bt_register:
                GoRegister();
                break;
        }
    }

    public void GoRegister(){
        startActivity(new Intent(WelcomeActivity.this,RegisterActivity.class));
    }

}
