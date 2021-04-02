package com.jit.zgy.skillexchange.activity;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.adapter.ECEvaluationAdapter;
import com.jit.zgy.skillexchange.bean.DarenBean;
import com.jit.zgy.skillexchange.bean.ECEvaluationBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;

public class DarenDetailActivity extends AppCompatActivity {
    private CircleImageView cv_uavatar;
    private TextView tv_unamedd;
    private TextView tv_addresssdd;
    private TextView tv_udescription;
    private LinearLayout ll_skillsdd;
    private TextView tv_dd1;
    private TextView tv_dd2;
    private ImageView iv_thumbup;
    private TextView tv_dd5;
    private List<ECEvaluationBean> ecEvaluationBeanList;
    private ECEvaluationAdapter ecEvaluationAdapter;
    private ListView lv_evaluation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daren_detail);
        this.setTitle("用户详情");
        initView();
        ShowDarenDetail();
    }

    public void initView(){
        cv_uavatar = (CircleImageView)findViewById(R.id.cv_uavatar);
        tv_unamedd = (TextView)findViewById(R.id.tv_unamedd);
        tv_addresssdd = (TextView)findViewById(R.id.tv_addresssdd);
        tv_udescription = (TextView)findViewById(R.id.tv_udescription);
        ll_skillsdd = (LinearLayout)findViewById(R.id.ll_skillsdd);
        tv_dd1 = (TextView)findViewById(R.id.tv_dd1);
        tv_dd2 = (TextView)findViewById(R.id.tv_dd2);
        iv_thumbup = (ImageView)findViewById(R.id.iv_thumbup);
        tv_dd5 = (TextView)findViewById(R.id.tv_dd5);
        lv_evaluation = (ListView)findViewById(R.id.lv_evaluation);
    }

    //进入用户详情
    public void ShowDarenDetail(){
        String uid = getIntent().getStringExtra("uid");
        //访问url
        String url="http://172.20.10.2:8080/SkillExchange/DarenDetailsServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("uid",uid);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Log.i(TAG,"达人详细信息:"+response);
                    int i;
                    try {
                        JSONObject jsonObject = response.getJSONObject("darendetail");
                            String uavatar = jsonObject.getString("uavatar");
                            byte[] bytes = Base64.decode(uavatar, Base64.DEFAULT);
                            cv_uavatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            tv_unamedd.setText(jsonObject.getString("uname"));
                            tv_udescription.setText(jsonObject.getString("udescription"));
                            //addview和split，将技能字符串分割成数组
                            String uskills[] = jsonObject.getString("uskills").split(",");
                            for(i = 0; i < uskills.length; i++){
                                TextView tv = new TextView(DarenDetailActivity.this);

                                tv.setText(uskills[i]);
                                tv.setTextSize(10f);
                                tv.setBackground(getResources().getDrawable(R.mipmap.rectangle));
                                tv.setGravity(Gravity.CENTER);
                                ll_skillsdd.addView(tv);

                                TextView space  = new TextView(DarenDetailActivity.this);
                                space.setWidth(15);
                                ll_skillsdd.addView(space);
                            }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        String url1="http://172.20.10.2:8080/SkillExchange/ShowEvaluationServlet";
        RequestParams params1 = new RequestParams(); // 绑定参数
        params1.put("uid",uid);
        params1.put("method",1);
        HttpUtil.get(url1,params1,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, final JSONObject response) {

                if (statusCode==200){
                    Log.i(TAG,"达人评论详细信息:"+response);
                    int i;
                    ecEvaluationBeanList = new ArrayList<ECEvaluationBean>();
                    try {
                        JSONArray evaluationlist = response.getJSONArray("evaluationlist");
                        for(i = 0; i < evaluationlist.length(); i++){
                            ECEvaluationBean ecEvaluationBean = new ECEvaluationBean();
                            JSONObject jsonObject = evaluationlist.getJSONObject(i);
                            //默认作为达人展示评论
                            if(jsonObject.getString("dc").equals("daren")){
                                ecEvaluationBean.setUavatar(jsonObject.getString("uavatar"));
                                ecEvaluationBean.setUname(jsonObject.getString("uname"));
                                ecEvaluationBean.setComment(jsonObject.getString("comment"));
                                ecEvaluationBeanList.add(ecEvaluationBean);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(ecEvaluationBeanList.size() != 0){
                        iv_thumbup.setVisibility(View.GONE);
                        tv_dd5.setVisibility(View.GONE);
                        ecEvaluationAdapter = new ECEvaluationAdapter(DarenDetailActivity.this,ecEvaluationBeanList);
                        lv_evaluation.setAdapter(ecEvaluationAdapter);
                    }else {
                        iv_thumbup.setVisibility(View.VISIBLE);
                        tv_dd5.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

    }
}
