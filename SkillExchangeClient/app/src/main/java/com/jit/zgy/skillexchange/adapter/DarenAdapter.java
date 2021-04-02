package com.jit.zgy.skillexchange.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.provider.SyncStateContract;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.DarenBean;
import com.jit.zgy.skillexchange.bean.TaskBean;
import com.jit.zgy.skillexchange.fragment.DarenFragment;
import com.jit.zgy.skillexchange.utils.TypefaceUtil;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.view.RatingBar;
import com.jit.zgy.skillexchange.view.TextViewBorder;
import com.jit.zgy.skillexchange.viewholder.DarenViewHolder;
import com.jit.zgy.skillexchange.viewholder.TaskViewHolder;

import java.util.List;

public class DarenAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<DarenBean> darenlist;

    public DarenAdapter(Context context, List<DarenBean> darenBeanList){
        mInflater = LayoutInflater.from(context);
        darenlist = darenBeanList;
    }

    public DarenAdapter() {
        super();
    }
    @Override
    public int getCount() {
        return darenlist.size();
    }

    @Override
    public Object getItem(int position) {
        return darenlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DarenViewHolder darenViewHolder = null;

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.daren_item,parent,false);
            darenViewHolder = new DarenViewHolder();

            darenViewHolder.iv_uavatardaren = (CircleImageView)convertView.findViewById(R.id.iv_uavatardaren);
            darenViewHolder.tv_unamedaren = (TextView)convertView.findViewById(R.id.tv_unamedaren);
            TypefaceUtil tfUtil = new TypefaceUtil(convertView.getContext(),"fonts/heiti.ttf");
            tfUtil.setTypeface(darenViewHolder.tv_unamedaren ,false);
            darenViewHolder.tv_ucertificationdaren = (TextViewBorder) convertView.findViewById(R.id.tv_ucertificationdaren);
            darenViewHolder.tv_uprice1 = (TextView)convertView.findViewById(R.id.tv_uprice1);
            darenViewHolder.tv_uskillsdaren = (TextView)convertView.findViewById(R.id.tv_uskillsdaren);
            darenViewHolder.star_daren =(RatingBar) convertView.findViewById(R.id.star_daren);
            darenViewHolder.tv_neid = (TextView)convertView.findViewById(R.id.tv_neid);
            darenViewHolder.daren_uid = (TextView)convertView.findViewById(R.id.daren_uid);
            convertView.setTag(darenViewHolder);
        }else{//convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            darenViewHolder = (DarenViewHolder)convertView.getTag();
        }

        DarenBean darenBean = darenlist.get(position);

        //用户头像
        byte[] bytes = Base64.decode(darenBean.getUavatar(), Base64.DEFAULT);
        darenViewHolder.iv_uavatardaren.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        darenViewHolder.tv_unamedaren.setText(darenBean.getUname());
        darenViewHolder.tv_ucertificationdaren.setText(darenBean.getUcertification());
        if(darenViewHolder.tv_ucertificationdaren.getText().equals("未认证")){
            darenViewHolder.tv_ucertificationdaren.setBorderColor(R.color.text_gray);
            darenViewHolder.tv_ucertificationdaren.setTextColor(convertView.getResources().getColor(R.color.text_gray));
        }else if(darenViewHolder.tv_ucertificationdaren.getText().equals("已认证")){
            darenViewHolder.tv_ucertificationdaren.setBorderColor(R.color.orange);
            darenViewHolder.tv_ucertificationdaren.setTextColor(convertView.getResources().getColor(R.color.darkorange));
        }

        darenViewHolder.tv_uprice1.setText(darenBean.getUprice() + "");
        darenViewHolder.tv_uskillsdaren.setText(darenBean.getUskills()+"");
        darenViewHolder.star_daren.setStar(darenBean.getUstars());
        darenViewHolder.tv_neid.setText(darenBean.getEnumbers()+"");
        darenViewHolder.daren_uid.setText(darenBean.getUid()+"");
        return convertView;
    }

}
