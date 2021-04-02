package com.jit.zgy.skillexchange.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jit.zgy.skillexchange.R;
import com.jit.zgy.skillexchange.activity.SysMessage_item;

public class MessageFragment extends Fragment {
    private View mView;
    private Toolbar tb_message;
    private View ic_system_message;
    public MessageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        setView();
        initListener();
        return mView;
    }

    public void initView(){
        tb_message = (Toolbar)mView.findViewById(R.id.tb_message);
        ic_system_message= (View)mView.findViewById(R.id.ic_system_message);
    }

    public void setView(){

    }

    public void initListener(){
        ic_system_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SysMessage_item.class));
            }
        });
    }
}

