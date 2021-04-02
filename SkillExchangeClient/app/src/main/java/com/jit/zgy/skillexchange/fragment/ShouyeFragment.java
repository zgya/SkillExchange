package com.jit.zgy.skillexchange.fragment;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.activity.MainActivity;
import com.jit.zgy.skillexchange.activity.TaskDetailActivity;
import com.jit.zgy.skillexchange.activity.WelcomeActivity;
import com.jit.zgy.skillexchange.adapter.TaskAdapter;
import com.jit.zgy.skillexchange.bean.AddressBean;
import com.jit.zgy.skillexchange.bean.KuJieBaBean;
import com.jit.zgy.skillexchange.bean.TFIDFBean;
import com.jit.zgy.skillexchange.bean.TaskBean;
import com.jit.zgy.skillexchange.handler.StringHandler;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.utils.GetAddressUtil;
import com.jit.zgy.skillexchange.utils.HttpUtils;
import com.jit.zgy.skillexchange.utils.tfCalculateUtil;
import com.jit.zgy.skillexchange.utils.tfidfCalculateUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class ShouyeFragment extends Fragment implements AMapLocationListener {
    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.image_ad1,
            R.drawable.image_ad2,
            R.drawable.image_ad3,
            R.drawable.image_ad4,
            R.drawable.image_ad5
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "轮播1",
            "轮播2",
            "轮播3",
            "轮播4",
            "轮播5"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;
    private MenuItem mSearchItem;
    private SearchView sv;
    private static final int MY_PERMISSIONS_REQUEST_CALL_LOCATION = 1;
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    private ListView lv_shouye;
    private List<TaskBean> taskslist;
    private TaskAdapter taskAdapter;
    private String longitude;
    private String latitude;
    private final int SUCCESS = 1;
    private final int FAILURE = 0;
    private final int ERRORCODE = 2;
    private String urlString;
    private StringHandler mStringHandler;
    private AddressBean addressBean;
    private View fs_mtoolbar;
    private EditText et_search_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_shouye, container, false);
        setView();
        ShowTask();
        //检查版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"权限已申请";
                showLocation();
            }
        }
        initListener();
        GoTaskDetail();
        ArrayList<TFIDFBean> tfidfBeanArrayList = new ArrayList<>();
        tfidfBeanArrayList = JieBatf();
        return mView;
    }

    //___________________________________________________________________________________________________________
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //"权限已申请"
                showLocation();
            } else {
                Toast.makeText(getActivity(), "权限已拒绝,不能定位", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showLocation() {
        try {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(5000);
            mLocationOption.setNeedAddress(true);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //启动定位
            mlocationClient.startLocation();
        } catch (Exception e) {

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息

                    //获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i("定位类型", amapLocation.getLocationType() + "");
                    Log.i("获取经度", amapLocation.getLongitude() + "");
                    Log.i("获取纬度", amapLocation.getLatitude() + "");
                    Log.i("获取精度信息", amapLocation.getAccuracy() + "");
                    longitude = amapLocation.getLongitude() + "";
                    latitude = amapLocation.getLatitude() + "";
//
//                    //如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    Log.i("地址", amapLocation.getAddress()+"");
//                    Log.i("国家信息", amapLocation.getCountry()+"");
//                    Log.i("省信息", amapLocation.getProvince());
//                    Log.i("城市信息", amapLocation.getCity());
//                    Log.i("城区信息", amapLocation.getDistrict());
//                    Log.i("街道信息", amapLocation.getStreet());
//                    Log.i("街道门牌号信息", amapLocation.getStreetNum());
//                    Log.i("城市编码", amapLocation.getCityCode());
//                    Log.i("地区编码", amapLocation.getAdCode());
//                    Log.i("获取当前定位点的AOI信息", amapLocation.getAoiName());
//                    Log.i("获取当前室内定位的建筑物Id", amapLocation.getBuildingId());
//                    Log.i("获取当前室内定位的楼层", amapLocation.getFloor());
//                    Log.i("获取GPS的当前状态", amapLocation.getGpsAccuracyStatus() + "");
                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    Log.i("获取定位时间", df.format(date));
                    // 停止定位
                    mlocationClient.stopLocation();

                    String url = "https://restapi.amap.com/v3/geocode/regeo?output=xml&location=";
                    StringBuilder sb = new StringBuilder(url);
                    sb.append(longitude);
                    sb.append(",");
                    sb.append(latitude);
                    sb.append("&key=85d27cc556eaf0bf4bbd7f6d61ad320f&radius=1000&extensions=all");
                    urlString = sb.toString();
                    Log.i("url", url);

                    //获取地理位置详细信息
                    new Thread().start();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        } catch (Exception e) {
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    XmlParse(msg.obj.toString());
                    addressBean = mStringHandler.getParsedData();
                    //成功获取到模拟器虚拟地址
                    String address = addressBean.getFormatted_address();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("address", address);
                    editor.commit();
                    break;
                default:
                    break;
            }
        }
    };

    class Thread extends java.lang.Thread {
        @Override
        public void run() {
            int code;
            try {
                URL url = new URL(urlString);
                /**
                 * 这里网络请求使用的是类HttpURLConnection，另外一种可以选择使用类HttpClient。
                 */
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");//使用GET方法获取
                conn.setConnectTimeout(5000);
                code = conn.getResponseCode();
                if (code == 200) {
                    /**
                     * 如果获取的code为200，则证明数据获取是正确的。
                     */
                    InputStream is = conn.getInputStream();
                    String result = HttpUtils.readMyInputStream(is);

                    /**
                     * 子线程发送消息到主线程，并将获取的结果带到主线程，让主线程来更新UI。
                     */
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = SUCCESS;
                    handler.sendMessage(msg);

                } else {

                    Message msg = new Message();
                    msg.what = ERRORCODE;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {

                e.printStackTrace();
                /**
                 * 如果获取失败，或出现异常，那么子线程发送失败的消息（FAILURE）到主线程，主线程显示Toast，来告诉使用者，数据获取是失败。
                 */
                Message msg = new Message();
                msg.what = FAILURE;
                handler.sendMessage(msg);
            }
        }
    }

    ;

    public void XmlParse(String xml) {
        Log.i("xml", xml);
        StringReader read = new StringReader(xml);
        // 创建输入源 使用 InputSource 对象来确定读取XML
        InputSource mInputSource = new InputSource(read);
        try {
            SAXParserFactory msSaxParserFactory = SAXParserFactory.newInstance();
            SAXParser msSaxParser = msSaxParserFactory.newSAXParser();
            XMLReader msXmlReader = msSaxParser.getXMLReader();
            mStringHandler = new StringHandler();
            msXmlReader.setContentHandler(mStringHandler);
            msXmlReader.parse(mInputSource);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        // 停止定位
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
        }
    }

    /**
     * 销毁定位
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
    }

    @Override
    public void onDestroyView() {
        destroyLocation();
        super.onDestroyView();
    }
//______________________________________________________________________________________________________

    //搜索
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shouye_search, menu); // Put your search menu in "menu_search" menu file.
        mSearchItem = menu.findItem(R.id.shouye_search);
        sv = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        sv.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sv.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void setView() {
        mViewPaper = (ViewPager) mView.findViewById(R.id.vp);
        lv_shouye = (ListView) mView.findViewById(R.id.lv_shouye);
        fs_mtoolbar = (View) mView.findViewById(R.id.fs_mtoolbar);
        et_search_text = (EditText) fs_mtoolbar.findViewById(R.id.et_search_text);
        //显示的图片
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));
        dots.add(mView.findViewById(R.id.dot_4));

        title = (TextView) mView.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
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
     *
     * @author zhuguangyao
     */
    private class ViewPageTask implements Runnable {

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        }

        ;
    };

    @Override
    public void onStop() {
        super.onStop();
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }

    public void ShowTask() {
        String url = "http://172.20.10.2:8080/SkillExchange/ShowTaskServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange", MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", "");
        params.put("uid", uid);
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                if (statusCode == 200) {
                    Log.i(TAG, "response-------------" + response);
                    int i;
                    taskslist = new ArrayList<TaskBean>();
                    try {
                        JSONArray tasks = response.getJSONArray("tasks");
                        for (i = 0; i < tasks.length(); i++) {
                            TaskBean taskBean = new TaskBean();
                            org.json.JSONObject jsonObject = tasks.getJSONObject(i);
                            taskBean.setTid(jsonObject.getInt("tid"));
                            taskBean.setTtitle(jsonObject.getString("ttitle"));
//                          taskBean.setTcontent(jsonObject.getString("tcontent"));
                            taskBean.setUid(jsonObject.getInt("uid"));
                            taskBean.setUname(jsonObject.getString("uname"));
                            taskBean.setUavatar(jsonObject.getString("uavatar"));
                            taskBean.setTprice(Float.parseFloat(jsonObject.getString("tprice")));
                            taskBean.setTdeadlineDate(jsonObject.getString("tdeadlineDate"));
                            taskBean.setTbids(jsonObject.getInt("tbids"));
                            taskBean.setTaddress(jsonObject.getString("taddress"));
                            taskslist.add(taskBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //为数据绑定适配器
                    taskAdapter = new TaskAdapter(getActivity(), taskslist);
                    lv_shouye.setAdapter(taskAdapter);
                }
            }
        });
    }

    //搜索框回车监听
    public void initListener() {
        et_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //todo

                    return true;
                }
                return false;
            }
        });
    }

    public final Map<String,String> JieBatfidf() {
        String url = "http://172.20.10.2:8080/SkillExchange/KuJieBaServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        params.put("null", "");
        final Map<String, String> doc_words = new HashMap<>();
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                if (statusCode == 200) {
                    int i;
                    Log.i(TAG, "kuJieBas" + response);
                    try {
                        JSONArray kuJieBas = response.getJSONArray("kuJieBas");
                        for (i = 0; i < kuJieBas.length(); i++) {
                            org.json.JSONObject jsonObject = kuJieBas.getJSONObject(i);
                            doc_words.put(jsonObject.getString("filename"), jsonObject.getString("fspcontent"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return doc_words;
    }

    //jieba分词
    public ArrayList<TFIDFBean> JieBatf(){
        String url = "http://172.20.10.2:8080/SkillExchange/TaskJieBaServlet";
        RequestParams params = new RequestParams(); // 绑定参数
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange", MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", "");
        params.put("uid", uid);
        final ArrayList<TFIDFBean> tfidfBeanArrayList = new ArrayList<>();
        Map<String ,String> maptest = new HashMap<>();
        HttpUtil.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                if (statusCode == 200) {
                    int i;
                    Log.i(TAG, "jieBas" + response);

                    try {
                        JSONArray jieBas = response.getJSONArray("jieBas");
                        for (i = 0; i < jieBas.length(); i++) {
                            TFIDFBean tfidfBean = new TFIDFBean();
                            org.json.JSONObject jsonObject = jieBas.getJSONObject(i);
                            tfidfBean.setTid(Integer.parseInt(jsonObject.getString("tid")));
                            tfidfBean.setTf(tfCalculateUtil.tfCalculate(jsonObject.getString("tspcontent")));
                            tfidfBean.setTfidf(tfidfCalculateUtil.tfidfCalculate(9,JieBatfidf(),tfidfBean.getTf()));
                            tfidfBeanArrayList.add(tfidfBean);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return tfidfBeanArrayList;
    }




    public void GoTaskDetail(){
        lv_shouye.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),TaskDetailActivity.class);
                TextView ti_tid = (TextView)view.findViewById(R.id.ti_tid);
                TextView ti_tstate = (TextView)view.findViewById(R.id.ti_tstate);
                String tid = ti_tid.getText().toString();
                String tstate = ti_tstate.getText().toString();
                intent.putExtra("tid",tid);
                intent.putExtra("tstate",tstate);
                startActivity(intent);
            }
        });
    }

}
