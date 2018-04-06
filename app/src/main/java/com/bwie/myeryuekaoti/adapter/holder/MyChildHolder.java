package com.bwie.myeryuekaoti.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.myeryuekaoti.R;

/**
 * Created by admin on 2018/3/31.
 */

public class MyChildHolder extends RecyclerView.ViewHolder {

    public CheckBox che_child;
    public ImageView image_child;
    public TextView text_childtitle;
    public TextView text_chidprice;
    public TextView text_jian;
    public TextView text_childsum;
    public TextView text_jia;


    public MyChildHolder(View itemView) {
        super(itemView);
        che_child = itemView.findViewById(R.id.che_child);
        image_child = itemView.findViewById(R.id.image_child);
        text_childtitle = itemView.findViewById(R.id.text_childtitle);
        text_chidprice = itemView.findViewById(R.id.text_chidprice);
        text_jian = itemView.findViewById(R.id.text_jian);
        text_childsum = itemView.findViewById(R.id.text_childsum);
        text_jia = itemView.findViewById(R.id.text_jia);

    }
}
