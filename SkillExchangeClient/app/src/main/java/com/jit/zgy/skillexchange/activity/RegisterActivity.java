package com.jit.zgy.skillexchange.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.Register;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.code;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {
    private Button rl_btregister;
    private EditText rl_uphone;
    private EditText rl_uyanzheng;
    private EditText rl_upassword;
    private EditText rl_upasswordagain;
    private CheckBox cb_xieyi;
    private String uphone;
    private String upassword;
    private ImageView iv_loginactivity_showCode;
    private String realCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        ininListener();
        iv_loginactivity_showCode.setImageBitmap(code.getInstance().createBitmap());
        realCode = code.getInstance().getCode().toLowerCase(); //将验证码用图片的形式显示出来

    }

    public void initView(){
        rl_btregister = (Button)findViewById(R.id.rl_btregister);
        rl_uphone = (EditText)findViewById(R.id.rl_uphone);
        rl_uyanzheng = (EditText)findViewById(R.id.rl_uyanzheng);
        rl_upassword = (EditText)findViewById(R.id.rl_upassword);
        rl_upasswordagain = (EditText)findViewById(R.id.rl_upasswordagain);
        cb_xieyi = (CheckBox)findViewById(R.id.cb_xieyi);
        iv_loginactivity_showCode = (ImageView)findViewById(R.id.iv_loginactivity_showCode);
    }

    public void ininListener(){
        //注册
        rl_btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uphone = rl_uphone.getText().toString();
                if(rl_upassword.getText().toString().equals(rl_upasswordagain.getText().toString())&&cb_xieyi.isChecked()){
                    upassword = rl_upassword.getText().toString();
                    Register register = new Register();
                    register.setUphone(uphone);
                    register.setUpasswrod(upassword);
                    Register(register);
                }else if(!(cb_xieyi.isChecked())){
                    Toast.makeText(RegisterActivity.this,"请勾选相关协议",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }

                String phoneCode = rl_uyanzheng.getText().toString().toLowerCase();
                if(phoneCode.length()==0){
                    Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }
                else if (!phoneCode.equals(realCode)){
                    Toast.makeText(RegisterActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                }


            }
        });

        //验证码
        iv_loginactivity_showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_loginactivity_showCode.setImageBitmap(code.getInstance().createBitmap());
                realCode = code.getInstance().getCode().toLowerCase(); //将验证码用图片的形式显示出来
            }
        });
    }

    public void Register(Register register){
        String url="http://172.20.10.2:8080/SkillExchange/RegisterServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        Gson gson = new Gson();
        String registerString = gson.toJson(register);
        params.put("registerString",registerString);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,WelcomeActivity.class));
                }
            }
        });
    }
}
