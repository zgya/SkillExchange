package com.jit.zgy.skillexchange.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.bean.DarenBean;
import com.jit.zgy.skillexchange.bean.TaskBean;
import com.jit.zgy.skillexchange.view.CircleImageView;
import com.jit.zgy.skillexchange.viewholder.TaskViewHolder;

import java.util.Date;
import java.util.List;

public class TaskAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<TaskBean> list_tasks;

    public TaskAdapter(Context context,List<TaskBean> tasks){
        mInflater = LayoutInflater.from(context);
        list_tasks = tasks;
    }

    public TaskAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return list_tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return list_tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.task_item,parent,false);
            holder = new TaskViewHolder();
            holder.ti_tid = (TextView)convertView.findViewById(R.id.ti_tid);
            holder.tv1 = (TextView)convertView.findViewById(R.id.tv1);
            holder.tv_tbids = (TextView)convertView.findViewById(R.id.tv_tbids);
            holder.tv_tprice = (TextView)convertView.findViewById(R.id.tv_tprice);
            holder.tv_tdeadline = (TextView)convertView.findViewById(R.id.tv_tdeadline);
            holder.tv_tuname = (TextView)convertView.findViewById(R.id.tv_tuname);
            holder.iv_uavatar = (CircleImageView)convertView.findViewById(R.id.iv_uavatar);
            holder.daren_uidd = (TextView)convertView.findViewById(R.id.daren_uidd);
            holder.ti_tstate = (TextView)convertView.findViewById(R.id.ti_tstate);
            holder.tvshouye_address = (TextView) convertView.findViewById(R.id.tvshouye_address);
            convertView.setTag(holder);
        }else{//convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (TaskViewHolder)convertView.getTag();
        }
        TaskBean taskBean = list_tasks.get(position);
        //用户头像
        byte[] bytes = Base64.decode(taskBean.getUavatar(), Base64.DEFAULT);
        holder.iv_uavatar.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
        TaskBean tasks = list_tasks.get(position);
        holder.ti_tid.setText(tasks.getTid()+"");
        holder.tv1.setText(tasks.getTtitle());
        holder.tv_tprice.setText(tasks.getTprice()+"");
        holder.tv_tbids.setText(tasks.getTbids()+"");
        holder.tv_tdeadline.setText(tasks.getTdeadlineDate());
        holder.tv_tuname.setText(tasks.getUname());
        holder.daren_uidd.setText(tasks.getUid()+"");
        holder.ti_tstate.setText(tasks.getTstate()+"");
        holder.tvshouye_address.setText(tasks.getTaddress());
        return convertView;
    }

}
