package com.jit.zgy.skillexchange.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.activity.AddSkillActivity;
import com.jit.zgy.skillexchange.activity.MainActivity;
import com.jit.zgy.skillexchange.activity.MoneyActivity;
import com.jit.zgy.skillexchange.activity.MyEvaluationActivity;
import com.jit.zgy.skillexchange.activity.SettingsActivity;
import com.jit.zgy.skillexchange.activity.WelcomeActivity;
import com.jit.zgy.skillexchange.http.HttpUtil;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.view.RatingBar;
import com.jit.zgy.skillexchange.view.TextViewBorder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


public class MineFragment extends Fragment implements View.OnClickListener{
    private View mView;
    /* ???????????? */
    private static final String IMAGE_FILE_NAME = "default_personal_image.png";
    /* ??????????????? */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    // ?????????????????????(X)??????(Y),480 X 480????????????????????????bitmap??????????????????????????????????????????????????????
    private static int output_X = 480;
    private static int output_Y = 480;
    private Bitmap photo;
    private String uavatar;

    private CircleImageView iv_personal_icon;
    private RatingBar mRatingBar;
    private TextViewBorder tv_addskill;
    private TextViewBorder ltextViewBorder_1;
    private TextViewBorder ltextViewBorder_2;
    private TextViewBorder ltextViewBorder_3;
    private TextViewBorder ltextViewBorder_4;
    private TextViewBorder ltextViewBorder_5;
    private TextViewBorder tv_daren;
    private TextViewBorder tv_customn;
    private ListView listView_mine;
    private ImageView iv_uname_edit;
    private TextView tv_uname;
    private EditText et_uname;
    private Handler handler;
    private TextView tv_mine_address;
    private ListView lv_mine;
    private ImageView fm_settings;
    public MineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView=inflater.inflate(R.layout.fragment_mine, container,false);
        initView();
        initData();
        //??????????????????
        tv_daren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_daren.setBackgroundResource(R.color.bisque);
                tv_customn.setBackgroundResource(R.color.bg_white);
                daren_mineListview();
            }
        });
        tv_customn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_customn.setBackgroundResource(R.color.bisque);
                tv_daren.setBackgroundResource(R.color.bg_white);
                customn_mineListview();
            }
        });
        iv_personal_icon.setOnClickListener(this);
        iv_uname_edit.setOnClickListener(this);
        tv_addskill.setOnClickListener(this);
        fm_settings.setOnClickListener(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange",MODE_PRIVATE);
        String uphone = sharedPreferences.getString("uphone","");
        FindUavatar(uphone);
        FindUname(uphone);
        setHasOptionsMenu(true);
        GoMyEvaluation();
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(getActivity(),SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initView(){
        mRatingBar =  mView.findViewById(R.id.star);
        tv_addskill = mView.findViewById(R.id.tv_addskill);
        tv_daren = mView.findViewById(R.id.tv_daren);
        tv_customn = mView.findViewById(R.id.tv_customn);
        listView_mine = mView.findViewById(R.id.lv_mine);
        iv_personal_icon = mView.findViewById(R.id.iv_personal_icon);
        iv_uname_edit = mView.findViewById(R.id.iv_uname_edit);
        tv_uname = mView.findViewById(R.id.tv_uname);
        et_uname = mView.findViewById(R.id.et_uname);
        tv_mine_address = mView.findViewById(R.id.tv_mine_address);
        lv_mine = (ListView)mView.findViewById(R.id.lv_mine);
        fm_settings = (ImageView)mView.findViewById(R.id.fm_settings);
    }

    private void initData(){
        mRatingBar.setClickable(false);
        mRatingBar.setStar(3.5f);
        tv_addskill.setBorderColor(getResources().getColor(R.color.bisque));
        tv_addskill.setTextColor(getResources().getColor(R.color.darkorange));
        tv_daren.setBorderColor(getResources().getColor(R.color.darkorange));
        tv_daren.setTextColor(getResources().getColor(R.color.darkorange));
        tv_customn.setBorderColor(getResources().getColor(R.color.darkorange));
        tv_customn.setTextColor(getResources().getColor(R.color.darkorange));
        tv_daren.setBackgroundResource(R.color.bisque);
        daren_mineListview();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange",MODE_PRIVATE);
        String address = sharedPreferences.getString("address","");
        tv_mine_address.setText(address);
    }

    //??????????????????????????????
    public void daren_mineListview() {
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        int[] iv_mine = new int[]{R.mipmap.mine_certification, R.mipmap.mine_wallet, R.mipmap.mine_task, R.mipmap.mine_evaluation};
        String[] tv_mine = new String[]{"????????????", "????????????", "???????????????", "????????????"};
        int[] arrow_mine = new int[]{R.mipmap.right_arrow, R.mipmap.right_arrow, R.mipmap.right_arrow, R.mipmap.right_arrow};
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("iv_mine", iv_mine[i]);
            map.put("tv_mine", tv_mine[i]);
            map.put("arrow_mine", arrow_mine[i]);
            listitem.add(map);
        }
        SimpleAdapter MineSimpleAdapter = new SimpleAdapter(getActivity(),
                listitem,
                R.layout.fragment_mine_lv,
                new String[]{"iv_mine", "tv_mine", "arrow_mine"},
                new int[]{R.id.iv_mine, R.id.tv_mine, R.id.arrow_mine});
        listView_mine.setAdapter(MineSimpleAdapter);
    }

    //??????????????????????????????
    public void customn_mineListview() {
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        int[] iv_mine = new int[]{R.mipmap.mine_certification, R.mipmap.mine_wallet, R.mipmap.mine_task, R.mipmap.mine_evaluation};
        String[] tv_mine = new String[]{"????????????", "????????????", "???????????????", "????????????"};
        int[] arrow_mine = new int[]{R.mipmap.right_arrow, R.mipmap.right_arrow, R.mipmap.right_arrow, R.mipmap.right_arrow};
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("iv_mine", iv_mine[i]);
            map.put("tv_mine", tv_mine[i]);
            map.put("arrow_mine", arrow_mine[i]);
            listitem.add(map);
        }
        SimpleAdapter MineSimpleAdapter = new SimpleAdapter(getActivity(),
                listitem,
                R.layout.fragment_mine_lv,
                new String[]{"iv_mine", "tv_mine", "arrow_mine"},
                new int[]{R.id.iv_mine, R.id.tv_mine, R.id.arrow_mine});
        listView_mine.setAdapter(MineSimpleAdapter);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.iv_personal_icon:
                choseHeadImageFromGallery();
                break;
            case R.id.iv_uname_edit:
                UpdateUname();
                break;
            case R.id.tv_addskill:
                startActivity(new Intent(getActivity(),AddSkillActivity.class));
                break;
            case R.id.fm_settings:
                startActivity(new Intent(getActivity(),SettingsActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // ????????????????????????????????????????????????
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(),"??????",Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getActivity(),"??????SDCard!",Toast.LENGTH_LONG).show();
                }
                break;
            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * ?????????????????????
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // ?????????????????????1???1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // ????????????????????????????????????
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // ????????????
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// ??????????????????
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * ??????????????????????????????????????????????????????????????????View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
            UavatarUpdate(photo);
            iv_personal_icon.setImageBitmap(photo);
        }
    }

    //???????????????????????????????????????????????????????????????
    public void FindUavatar(String uphone){
        String url="http://172.20.10.2:8080/SkillExchange/UavatarServlet";
        RequestParams params = new RequestParams(); // ????????????
        params.put("uphone",uphone);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(statusCode == 200){
                    try {
                        String uavatar = response.getString("uavatar");
                        if(uavatar != null && uavatar != "uavatar"){
                            byte[] bytes = Base64.decode(uavatar, Base64.DEFAULT);
                            iv_personal_icon.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                        }else{
                            iv_personal_icon.setImageResource(R.drawable.default_personal_image);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //??????????????????
    public void FindUname(String uphone){
        String url = "http://172.20.10.2:8080/SkillExchange/UserInfoServlet";
        RequestParams params = new RequestParams(); // ????????????
        params.put("method",1);
        params.put("uphone",uphone);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        String uname = response.getString("uname");
                        if(uname != null){
                            tv_uname.setText(uname);
                        }else{
                            tv_uname.setText("???????????????");
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void UavatarUpdate(Bitmap photo){
        String url="http://172.20.10.2:8080/SkillExchange/UavatarUpdateServlet";
        RequestParams params = new RequestParams(); // ????????????
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//???Bitmap??????Byte[]
        photo.compress(Bitmap.CompressFormat.JPEG, 50, baos);//??????
        final String uavatar =Base64.encodeToString(baos.toByteArray(),Base64.DEFAULT);//???????????????String
        Log.i(TAG,"??????base64??????-----"+uavatar);
        //1.
        params.put("uavatar",uavatar);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange",MODE_PRIVATE);
        String uphone = sharedPreferences.getString("uphone","");
        //2.
        params.put("uphone",uphone);
        HttpUtil.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode==200){
                    try {
                        if(response.getBoolean("update_uavatar")==true){
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(),"??????????????????",Toast.LENGTH_LONG).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    // ??????????????????base64??????
    public String getBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //??????????????????60%
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, out);
        //??????base64??????
        String base = Base64.encodeToString(out.toByteArray(), Base64.DEFAULT);
        return base;
    }

    // ???????????????????????????????????????
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK);
        // ??????????????????
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }


    public boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // ????????????SDCard
            return true;
        } else {
            return false;
        }
    }

    public void UpdateUname(){
        final String url = "http://172.20.10.2:8080/SkillExchange/UserInfoServlet";
        et_uname.setVisibility(View.VISIBLE);
        tv_uname.setVisibility(View.INVISIBLE);
        et_uname.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        //???????????????
        et_uname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        //????????????
        et_uname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())){
                    final String uname = et_uname.getText().toString();
                    final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("skillexchange",MODE_PRIVATE);
                    String uphone = sharedPreferences.getString("uphone","");
                    RequestParams params = new RequestParams(); // ????????????
                    params.put("method",2);
                    params.put("uname",uname);
                    params.put("uphone",uphone);
                    HttpUtil.get(url,params,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            if(statusCode == 200){
                                try {
                                    if(response.getBoolean("result") == true){
                                        tv_uname.setText(uname);
                                        tv_uname.setVisibility(View.VISIBLE);
                                        et_uname.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(),"?????????????????????",Toast.LENGTH_SHORT).show();
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                }
                return false;
            }
        });
    }

    //??????????????????
    public void GoMyEvaluation(){
        lv_mine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        startActivity(new Intent(getActivity(),MoneyActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(),MyEvaluationActivity.class));
                        break;
                }
            }
        });
    }


}
