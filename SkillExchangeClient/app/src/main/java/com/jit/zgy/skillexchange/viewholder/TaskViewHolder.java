package com.jit.zgy.skillexchange.viewholder;


import android.widget.TextView;

import com.jit.zgy.skillexchange.view.CircleImageView;

//这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
public class TaskViewHolder{
    public TextView ti_tid;
    public TextView tv1;
    public TextView tv_tprice;
    public TextView tv_tbids;
    public TextView tv_tdeadline;
    public CircleImageView iv_uavatar;
    public TextView tv_tuname;
    public TextView daren_uidd;
    public TextView ti_tstate;
    public TextView tvshouye_address;
}