package com.jit.zgy.skillexchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.SettingsBean;
import com.jit.zgy.skillexchange.viewholder.SettingsViewHolder;

import java.util.List;

public class SettingsAdapter extends ArrayAdapter<SettingsBean> {
    private int resourceId;
    public SettingsAdapter(Context context , int textViewResourceId, List<SettingsBean> objects){
        super(context,textViewResourceId,objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingsBean settingsBean = getItem(position);
        View view;
        SettingsViewHolder settingsViewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            settingsViewHolder = new SettingsViewHolder();
            settingsViewHolder.iv_settings = (ImageView)view.findViewById(R.id.iv_settings);
            settingsViewHolder.tv_settings = (TextView)view.findViewById(R.id.tv_settings);
            settingsViewHolder.iv_settings1 = (ImageView)view.findViewById(R.id.iv_settings1);

            view.setTag(settingsViewHolder);
        }else {
            view = convertView;
            settingsViewHolder = (SettingsViewHolder)view.getTag();
        }
        settingsViewHolder.iv_settings.setImageResource(settingsBean.getImage1());
        settingsViewHolder.tv_settings.setText(settingsBean.getText1());
        settingsViewHolder.iv_settings1.setImageResource(settingsBean.getImage2());
        return view;
    }
}
