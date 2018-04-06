package com.bwie.myeryuekaoti.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.adapter.holder.MyTuiJianHolder;
import com.bwie.myeryuekaoti.bean.ShouYeBean;
import com.bwie.myeryuekaoti.view.SetClicked;

/**
 * Created by admin on 2018/3/31.
 */

public class MyTuiJianAdapter extends RecyclerView.Adapter<MyTuiJianHolder> {
    private final Context context;
    private final ShouYeBean shouYeBean;
    private SetClicked onclick;

    public MyTuiJianAdapter(Context context, ShouYeBean shouYeBean) {

        this.context = context;
        this.shouYeBean = shouYeBean;
    }

    @Override
    public MyTuiJianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuijian, parent, false);
        MyTuiJianHolder holder = new MyTuiJianHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyTuiJianHolder holder, final int position) {
        ShouYeBean.TuijianBean.ListBean listBean = shouYeBean.getTuijian().getList().get(position);
        String[] split = listBean.getImages().split("\\|");
        holder.sdv_tuijian.setImageURI(split[0]);
        holder.text_tite.setText(listBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.onClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shouYeBean.getTuijian().getList().size();
    }

    public void setonclick(SetClicked onclick) {
        this.onclick = onclick;
    }
}
