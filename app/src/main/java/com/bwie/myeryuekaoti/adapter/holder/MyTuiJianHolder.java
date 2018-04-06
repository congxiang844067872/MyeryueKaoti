package com.bwie.myeryuekaoti.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bwie.myeryuekaoti.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by admin on 2018/3/31.
 */

public class MyTuiJianHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView sdv_tuijian;
    public TextView text_tite;

    public MyTuiJianHolder(View itemView) {
        super(itemView);
        sdv_tuijian = itemView.findViewById(R.id.sdv_tuijian);
        text_tite = itemView.findViewById(R.id.text_tite);
    }
}
