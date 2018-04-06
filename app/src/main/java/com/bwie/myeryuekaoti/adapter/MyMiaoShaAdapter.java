package com.bwie.myeryuekaoti.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myeryuekaoti.MainActivity;
import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.adapter.holder.MyMiaoShaHolder;
import com.bwie.myeryuekaoti.bean.ShouYeBean;
import com.bwie.myeryuekaoti.view.SetClicked;

/**
 * Created by admin on 2018/3/31.
 */

public class MyMiaoShaAdapter extends RecyclerView.Adapter<MyMiaoShaHolder> {
    private final Context context;
    private final ShouYeBean shouYeBean;
    private SetClicked setClicked;

    public MyMiaoShaAdapter(Context context, ShouYeBean shouYeBean) {

        this.context = context;
        this.shouYeBean = shouYeBean;
    }

    @Override
    public MyMiaoShaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_miaosha, parent, false);
        MyMiaoShaHolder holder = new MyMiaoShaHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyMiaoShaHolder holder, final int position) {
        String[] split = shouYeBean.getMiaosha().getList().get(position).getImages().split("\\|");
        holder.sdv_miao.setImageURI(split[0]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClicked.onClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shouYeBean.getMiaosha().getList().size();
    }

    public void setonclick(SetClicked setClicked) {
        this.setClicked = setClicked;
    }
}
