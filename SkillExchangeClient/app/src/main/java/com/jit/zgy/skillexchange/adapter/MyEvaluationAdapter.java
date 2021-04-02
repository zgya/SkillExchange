package com.jit.zgy.skillexchange.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.ECEvaluationBean;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.view.RatingBar;
import com.jit.zgy.skillexchange.viewholder.EvaluationViewHolder;
import com.jit.zgy.skillexchange.viewholder.MyEvaluationViewHolder;

import java.util.List;

public class MyEvaluationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ECEvaluationBean> ecEvaluationBeanList;

    public MyEvaluationAdapter(Context context, List<ECEvaluationBean> list){
        mInflater = LayoutInflater.from(context);
        ecEvaluationBeanList = list;
    }

    public  MyEvaluationAdapter(){}

    @Override
    public int getCount() {
        return ecEvaluationBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return ecEvaluationBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyEvaluationViewHolder myEvaluationViewHolder = null;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.my_evaluation_item,parent,false);
            myEvaluationViewHolder = new MyEvaluationViewHolder();

            myEvaluationViewHolder.em_uavatar = (CircleImageView)convertView.findViewById(R.id.em_uavatar);
            myEvaluationViewHolder.em_uname = (TextView)convertView.findViewById(R.id.em_uname);
            myEvaluationViewHolder.em_comment = (TextView)convertView.findViewById(R.id.em_comment);
            myEvaluationViewHolder.em_stars= (RatingBar)convertView.findViewById(R.id.em_stars);
            convertView.setTag(myEvaluationViewHolder);
        }else{
            myEvaluationViewHolder = ( MyEvaluationViewHolder) convertView.getTag();
        }

        ECEvaluationBean ecEvaluationBean = ecEvaluationBeanList.get(position);
        //用户头像
        byte[] bytes = Base64.decode(ecEvaluationBean.getUavatar(), Base64.DEFAULT);
        myEvaluationViewHolder.em_uavatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        myEvaluationViewHolder.em_uname.setText(ecEvaluationBean.getUname());
        myEvaluationViewHolder.em_comment.setText(ecEvaluationBean.getComment());
        myEvaluationViewHolder.em_stars.setStar(ecEvaluationBean.getEstars());
        return convertView;
    }
}
