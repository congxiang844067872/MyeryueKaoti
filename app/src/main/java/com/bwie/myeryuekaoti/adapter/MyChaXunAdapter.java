package com.bwie.myeryuekaoti.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.adapter.MyChildAdapter;
import com.bwie.myeryuekaoti.adapter.holder.MyChaXunHolder;
import com.bwie.myeryuekaoti.bean.MyBean;
import com.bwie.myeryuekaoti.bean.MyHeJiBean;
import com.bwie.myeryuekaoti.presenter.Presenter;
import com.bwie.myeryuekaoti.url.ApiUrl;
import com.bwie.myeryuekaoti.url.RetrofitUtil;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/31.
 */

public class MyChaXunAdapter extends RecyclerView.Adapter<MyChaXunHolder> {
    private final Context context;
    private final MyBean myBean;
    private final Presenter presenter;
    private final Handler handler;
    private final HashMap<String, String> map;
    private int conindex;
    private MyChildAdapter adapter;

    public MyChaXunAdapter(Context context, MyBean myBean, Presenter presenter
            , Handler handler, HashMap<String, String> map) {

        this.context = context;
        this.myBean = myBean;
        this.presenter = presenter;
        this.handler = handler;
        this.map = map;
    }

    @Override
    public MyChaXunHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R
                .layout.item_group, parent, false);
        MyChaXunHolder holder = new MyChaXunHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyChaXunHolder holder, final int position) {
        final List<MyBean.DataBean> data = myBean.getData();
        holder.che_group.setChecked(data.get(position).isCheckbox());
        holder.text_group.setText(data.get(position).getSellerName());
        holder.che_group.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                conindex = 0;
                allChildChecked(holder.che_group.isChecked(),data.get(position));
            }

        });
        holder.recycler_child.setLayoutManager(new LinearLayoutManager(context
                ,LinearLayoutManager.VERTICAL,false));
        adapter = new MyChildAdapter(context, myBean, handler, presenter,position,map);
        holder.recycler_child.setAdapter(adapter);

    }

    private void allChildChecked(final boolean checked, final MyBean.DataBean dataBean) {
        final MyBean.DataBean.ListBean bean = dataBean.getList().get(conindex);

            Map<String, String> params = new HashMap<>();
            params.put("uid", "4427");
            params.put("sellerid", String.valueOf(bean.getSellerid()));
            params.put("pid", String.valueOf(bean.getPid()));
            params.put("num", String.valueOf(bean.getNum()));
            params.put("selected", String.valueOf(checked ? 1 : 0));

        RetrofitUtil.getSerVice().doGet(ApiUrl.gengxin,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        conindex++;
                        if (conindex<dataBean.getList().size()){
                            allChildChecked(checked,dataBean);
                        }else {
                            presenter.getUrl(RetrofitUtil.getSerVice()
                                    .doGet(ApiUrl.select,map));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }



    @Override
    public int getItemCount() {
        return myBean.getData().size();
    }

    public void setNumSumAll() {

        double price=0;
        int num=0;
        List<MyBean.DataBean> data = myBean.getData();
        for (int i=0;i<data.size();i++){
            List<MyBean.DataBean.ListBean> beans = data.get(i).getList();
            for (int j=0;j<beans.size();j++){
                if (beans.get(j).getSelected()==1){
                    price+=beans.get(j).getBargainPrice()*beans.get(j).getNum();
                    num+=beans.get(j).getNum();
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(price);
        MyHeJiBean myHeJiBean = new MyHeJiBean(format, num);
        Message msg = Message.obtain();
        msg.what=0;
        msg.obj=myHeJiBean;
        handler.sendMessage(msg);
    }

    public void allChed(boolean checked) {
        adapter.allChed(checked);

    }
}
