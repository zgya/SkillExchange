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
import com.jit.zgy.skillexchange.bean.DarenBean;
import com.jit.zgy.skillexchange.bean.ECEvaluationBean;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.viewholder.DarenViewHolder;
import com.jit.zgy.skillexchange.viewholder.EvaluationViewHolder;

import java.util.List;

public class ECEvaluationAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ECEvaluationBean> ecEvaluationBeanList;

    public ECEvaluationAdapter(Context context, List<ECEvaluationBean> list){
        mInflater = LayoutInflater.from(context);
        ecEvaluationBeanList = list;
    }

    public  ECEvaluationAdapter(){}

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
        EvaluationViewHolder evaluationViewHolder = null;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.evaluation_item,parent,false);
            evaluationViewHolder = new EvaluationViewHolder();

            evaluationViewHolder.e_uavatar = (CircleImageView)convertView.findViewById(R.id.e_uavatar);
            evaluationViewHolder.e_uname = (TextView)convertView.findViewById(R.id.e_uname);
            evaluationViewHolder.e_ucomment = (TextView)convertView.findViewById(R.id.e_comment);
            convertView.setTag(evaluationViewHolder);
        }else{
            evaluationViewHolder = (EvaluationViewHolder) convertView.getTag();
        }

        ECEvaluationBean ecEvaluationBean = ecEvaluationBeanList.get(position);
        //用户头像
        byte[] bytes = Base64.decode(ecEvaluationBean.getUavatar(), Base64.DEFAULT);
        evaluationViewHolder.e_uavatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        evaluationViewHolder.e_uname.setText(ecEvaluationBean.getUname());
        evaluationViewHolder.e_ucomment.setText(ecEvaluationBean.getComment());
        return convertView;
    }
}
