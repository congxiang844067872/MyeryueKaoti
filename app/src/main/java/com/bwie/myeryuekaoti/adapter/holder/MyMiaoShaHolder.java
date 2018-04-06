package com.bwie.myeryuekaoti.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bwie.myeryuekaoti.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by admin on 2018/3/31.
 */

public class MyMiaoShaHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView sdv_miao;

    public MyMiaoShaHolder(View itemView) {
        super(itemView);
        sdv_miao = itemView.findViewById(R.id.sdv_miao);
    }
}
