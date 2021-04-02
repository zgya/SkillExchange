package com.jit.zgy.skillexchange.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.activity.DarenDetailActivity;
import com.jit.zgy.skillexchange.activity.WelcomeActivity;
import com.jit.zgy.skillexchange.adapter.DarenAdapter;
import com.jit.zgy.skillexchange.adapter.TaskAdapter;
import com.jit.zgy.skillexchange.adapter.ViewPagerAdapter;
import com.jit.zgy.skillexchange.bean.DarenBean;
import com.jit.zgy.skillexchange.bean.TaskBean;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.TypefaceUtil;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.view.TextViewBorder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class DarenFragment extends Fragment{
    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private ListView lv_daren;
    private List<DarenBean> darenBeanList;
    private DarenAdapter darenAdapter;
    private TextViewBorder tv_ucertificationdaren;
    private TextView tv_unamedaren;
    private CircleImageView iv_uavatardaren;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.image_ad11,
            R.drawable.image_ad21,
            R.drawable.image_ad31,
            R.drawable.image_ad41,
            R.drawable.image_ad51
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
            "轮播4",
            "轮播5"
    };
    private TextView title1;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private MenuItem mSearchItem;

    public DarenFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_daren, container,false);
        setView();
        ShowDaren();
        initData();
        GoDarenDescription();
        return mView;
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    private void setView(){
        mViewPaper = (ViewPager)mView.findViewById(R.id.vp1);
        lv_daren = (ListView)mView.findViewById(R.id.lv_daren);
        tv_ucertificationdaren = (TextViewBorder)mView.findViewById(R.id.tv_ucertificationdaren);
        tv_unamedaren = (TextView)mView.findViewById(R.id.tv_unamedaren);
        iv_uavatardaren = (CircleImageView)mView.findViewById(R.id.iv_uavatardaren);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_01));
        dots.add(mView.findViewById(R.id.dot_11));
        dots.add(mView.findViewById(R.id.dot_21));
        dots.add(mView.findViewById(R.id.dot_31));
        dots.add(mView.findViewById(R.id.dot_41));

        title1 = (TextView) mView.findViewById(R.id.title1);
        title1.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title1.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_yes);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_no);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    public void initData(){

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }

    /**
     * 图片轮播任务
     * @author zhuguangyao
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    public void ShowDaren(){
        String url="http://172.20.10.2:8080/SkillExchange/ShowDarenServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange",MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid","");
        params.put("uid",uid);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    Log.i(TAG,"response-------------"+response);//达人json形式
                    int i;
                    darenBeanList = new ArrayList<DarenBean>();
                    try {
                        JSONArray darens = response.getJSONArray("darenlist");
                        for(i = 0; i < darens.length(); i++){
                            DarenBean darenBean = new DarenBean();
                            JSONObject jsonObject = darens.getJSONObject(i);
                            darenBean.setUavatar(jsonObject.getString("uavatar"));
                            darenBean.setEnumbers(jsonObject.getInt("enumbers"));
                            darenBean.setUcertification(jsonObject.getString("ucertification"));
                            darenBean.setUname(jsonObject.getString("uname"));
                            darenBean.setUprice(jsonObject.getInt("uprice"));
                            darenBean.setUskills(jsonObject.getString("uskills"));
                            darenBean.setUstars(Float.parseFloat(jsonObject.getString("ustars")));
                            darenBean.setUid(jsonObject.getInt("uid"));
                            darenBeanList.add(darenBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //为数据绑定适配器
                    darenAdapter = new DarenAdapter(getActivity(),darenBeanList);
                    lv_daren.setAdapter(darenAdapter);
                }
            }
        });
    }

    //listview内部组件达人头像点击事件
    public void GoDarenDescription(){
        lv_daren.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                view.findViewById(R.id.iv_uavatardaren).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent = new Intent(getActivity(),DarenDetailActivity.class);
                        TextView daren_uid = (TextView)view.findViewById(R.id.daren_uid);
                        String uid = daren_uid.getText().toString();
                        intent.putExtra("uid",uid);
                        startActivity(intent);

                    }
                });


            }
        });
    }

}
