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
    //???????????????????????????
    private int oldPosition = 0;
    //???????????????id
    private int[] imageIds = new int[]{
            R.drawable.image_ad1,
            R.drawable.image_ad2,
            R.drawable.image_ad3,
            R.drawable.image_ad4,
            R.drawable.image_ad5
    };
    //?????????????????????
    private String[] titles = new String[]{
            "??????1",
            "??????2",
            "??????3",
            "??????4",
            "??????5"
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
        //????????????????????????M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALL_LOCATION);
            } else {
                //"???????????????";
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
                //"???????????????"
                showLocation();
            } else {
                Toast.makeText(getActivity(), "???????????????,????????????", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showLocation() {
        try {
            mlocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            //???????????????????????????????????????Battery_Saving?????????????????????Device_Sensors??????????????????
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(5000);
            mLocationOption.setNeedAddress(true);
            //??????????????????
            mlocationClient.setLocationOption(mLocationOption);
            //????????????
            mlocationClient.startLocation();
        } catch (Exception e) {

        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        try {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //?????????????????????????????????????????????

                    //??????????????????????????????????????????????????????????????????????????????
                    Log.i("????????????", amapLocation.getLocationType() + "");
                    Log.i("????????????", amapLocation.getLongitude() + "");
                    Log.i("????????????", amapLocation.getLatitude() + "");
                    Log.i("??????????????????", amapLocation.getAccuracy() + "");
                    longitude = amapLocation.getLongitude() + "";
                    latitude = amapLocation.getLatitude() + "";
//
//                    //??????option?????????isNeedAddress???false??????????????????????????????????????????????????????????????????GPS??????????????????????????????
//                    Log.i("??????", amapLocation.getAddress()+"");
//                    Log.i("????????????", amapLocation.getCountry()+"");
//                    Log.i("?????????", amapLocation.getProvince());
//                    Log.i("????????????", amapLocation.getCity());
//                    Log.i("????????????", amapLocation.getDistrict());
//                    Log.i("????????????", amapLocation.getStreet());
//                    Log.i("?????????????????????", amapLocation.getStreetNum());
//                    Log.i("????????????", amapLocation.getCityCode());
//                    Log.i("????????????", amapLocation.getAdCode());
//                    Log.i("????????????????????????AOI??????", amapLocation.getAoiName());
//                    Log.i("????????????????????????????????????Id", amapLocation.getBuildingId());
//                    Log.i("?????????????????????????????????", amapLocation.getFloor());
//                    Log.i("??????GPS???????????????", amapLocation.getGpsAccuracyStatus() + "");
                    //??????????????????
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    Log.i("??????????????????", df.format(date));
                    // ????????????
                    mlocationClient.stopLocation();

                    String url = "https://restapi.amap.com/v3/geocode/regeo?output=xml&location=";
                    StringBuilder sb = new StringBuilder(url);
                    sb.append(longitude);
                    sb.append(",");
                    sb.append(latitude);
                    sb.append("&key=85d27cc556eaf0bf4bbd7f6d61ad320f&radius=1000&extensions=all");
                    urlString = sb.toString();
                    Log.i("url", url);

                    //??????????????????????????????
                    new Thread().start();
                } else {
                    //???????????????????????????ErrCode????????????????????????????????????????????????errInfo???????????????????????????????????????
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
                    //????????????????????????????????????
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
                 * ?????????????????????????????????HttpURLConnection????????????????????????????????????HttpClient???
                 */
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");//??????GET????????????
                conn.setConnectTimeout(5000);
                code = conn.getResponseCode();
                if (code == 200) {
                    /**
                     * ???????????????code???200???????????????????????????????????????
                     */
                    InputStream is = conn.getInputStream();
                    String result = HttpUtils.readMyInputStream(is);

                    /**
                     * ????????????????????????????????????????????????????????????????????????????????????????????????UI???
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
                 * ??????????????????????????????????????????????????????????????????????????????FAILURE?????????????????????????????????Toast????????????????????????????????????????????????
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
        // ??????????????? ?????? InputSource ?????????????????????XML
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
        // ????????????
        if (null != mlocationClient) {
            mlocationClient.stopLocation();
        }
    }

    /**
     * ????????????
     */
    private void destroyLocation() {
        if (null != mlocationClient) {
            /**
             * ??????AMapLocationClient????????????Activity???????????????
             * ???Activity???onDestroy??????????????????AMapLocationClient???onDestroy
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

    //??????
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
        //???????????????
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //???????????????
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

    /*??????????????????*/
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
     * ???????????????????????????????????????
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
     * ??????????????????
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
     * ????????????????????????????????????
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
        RequestParams params = new RequestParams(); // ????????????
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
                    //????????????????????????
                    taskAdapter = new TaskAdapter(getActivity(), taskslist);
                    lv_shouye.setAdapter(taskAdapter);
                }
            }
        });
    }

    //?????????????????????
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
        RequestParams params = new RequestParams(); // ????????????
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

    //jieba??????
    public ArrayList<TFIDFBean> JieBatf(){
        String url = "http://172.20.10.2:8080/SkillExchange/TaskJieBaServlet";
        RequestParams params = new RequestParams(); // ????????????
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
