package com.bwie.myeryuekaoti.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.adapter.holder.MyChildHolder;
import com.bwie.myeryuekaoti.bean.MyBean;
import com.bwie.myeryuekaoti.presenter.Presenter;
import com.bwie.myeryuekaoti.url.ApiUrl;
import com.bwie.myeryuekaoti.url.RetrofitUtil;

import java.util.ArrayList;
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

public class MyChildAdapter extends RecyclerView.Adapter<MyChildHolder> {


    private final Context context;
    private final MyBean myBean;
    private final Handler handler;
    private final Presenter presenter;
    private final int groupposition;
    private HashMap<String, String> map;
    private int conAllCheck;
    private TextView textjian;
    private EditText edit_childsum;
    private TextView textjia;
    private int num;

    public MyChildAdapter(Context context, MyBean myBean, Handler handler
            , Presenter presenter, int groupposition, HashMap<String, String> map) {

        this.context = context;
        this.myBean = myBean;
        this.handler = handler;
        this.presenter = presenter;
        this.groupposition = groupposition;
        this.map = map;
    }

    @Override
    public MyChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_child, parent
                , false);
        MyChildHolder holder = new MyChildHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyChildHolder holder, int position) {
        List<MyBean.DataBean.ListBean> list = myBean.getData().get(groupposition)
                .getList();
        final MyBean.DataBean.ListBean listBean = myBean.getData()
                .get(groupposition).getList().get(position);
        String[] split = listBean.getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.image_child);
        holder.text_childtitle.setText(listBean.getTitle());
        holder.text_chidprice.setText("¥"+listBean.getBargainPrice());
        holder.text_childsum.setText(listBean.getNum()+"");
        //holder.text_chidprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        holder.text_chidprice.getPaint().setFlags(Paint
                .STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

        holder.che_child.setChecked(Boolean.parseBoolean(String.valueOf(listBean
                .getSelected()==0?false:true)));
        holder.text_childsum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = View.inflate(context, R.layout.item_view, null);
                textjian = view.findViewById(R.id.textjian);
                edit_childsum = view.findViewById(R.id.edit_childsum);
                textjia = view.findViewById(R.id.textjia);
                edit_childsum.setText(listBean.getNum()+"");
                num = listBean.getNum();
                textjian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (num ==1){
                            return;
                        }else {
                            num--;
                        }
                        //num--;
                        edit_childsum.setText(num +"");
                    }
                });

                textjia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (num ==200){
                            return;
                        }else {
                            num++;
                        }

                        edit_childsum.setText(num+"");

                    }
                });

                final String s = edit_childsum.getText().toString();
                builder.setTitle("修改购买数量")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("-------000",s);
                                Map<String, String> params=new HashMap<>();
                                params.put("uid","4427");
                                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                                params.put("pid", String.valueOf(listBean.getPid()));
                                params.put("num", String.valueOf(edit_childsum.getText().toString()));
                                params.put("selected", String.valueOf(listBean.getSelected()));
                                RetrofitUtil.getSerVice().doGet(ApiUrl.gengxin,params)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Observer<ResponseBody>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(ResponseBody value) {

                                                presenter.getUrl(RetrofitUtil.getSerVice()
                                                        .doGet(ApiUrl.select,map));
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                                holder.text_childsum.setText(edit_childsum.getText().toString()+"");
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


            }
        });

        holder.che_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Map<String, String> params=new HashMap<>();
                    params.put("uid","4427");
                    params.put("sellerid", String.valueOf(listBean.getSellerid()));
                    params.put("pid", String.valueOf(listBean.getPid()));
                    params.put("num", String.valueOf(listBean.getNum()));
                    params.put("selected", String.valueOf(listBean.getSelected()==0?1:0));
                RetrofitUtil.getSerVice().doGet(ApiUrl.gengxin,params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody value) {

                                presenter.getUrl(RetrofitUtil.getSerVice()
                                        .doGet(ApiUrl.select,map));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

                }

        });

        holder.text_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Map<String, String> params=new HashMap<>();
                    params.put("uid","4427");
                    params.put("sellerid", String.valueOf(listBean.getSellerid()));
                    params.put("pid", String.valueOf(listBean.getPid()));
                    params.put("num", String.valueOf(listBean.getNum()+1));
                    params.put("selected", String.valueOf(listBean.getSelected()));
                RetrofitUtil.getSerVice().doGet(ApiUrl.gengxin,params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody value) {

                                presenter.getUrl(RetrofitUtil.getSerVice()
                                        .doGet(ApiUrl.select,map));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        holder.text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBean.getNum();
                if (num==1){
                    return;
                }

                    Map<String, String> params=new HashMap<>();
                    params.put("uid","4427");
                    params.put("sellerid", String.valueOf(listBean.getSellerid()));
                    params.put("pid", String.valueOf(listBean.getPid()));
                    params.put("num", String.valueOf(listBean.getNum()-1));
                    params.put("selected", String.valueOf(listBean.getSelected()));
                RetrofitUtil.getSerVice().doGet(ApiUrl.gengxin,params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody value) {

                                presenter.getUrl(RetrofitUtil.getSerVice()
                                        .doGet(ApiUrl.select,map));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                }

        });





    }

    @Override
    public int getItemCount() {
        return myBean.getData().get(groupposition).getList().size();
    }


    public void allChed(boolean checked) {
        ArrayList<MyBean.DataBean.ListBean> listBeans = new ArrayList<>();
        List<MyBean.DataBean> data = myBean.getData();
        for (int i=0;i<data.size();i++){
            List<MyBean.DataBean.ListBean> list = data.get(i).getList();
            for (int j=0;j<list.size();j++){
                listBeans.add(list.get(j));
            }
        }


        conAllCheck = 0;
        conAllChecked(listBeans,checked);

    }
    private void conAllChecked(final ArrayList<MyBean.DataBean.ListBean> listBeans, final boolean checked) {

        final MyBean.DataBean.ListBean listBean = listBeans.get(conAllCheck);

            Map<String, String> params = new HashMap<>();
            params.put("uid", "4427");
            params.put("sellerid", String.valueOf(listBean.getSellerid()));
            params.put("pid", String.valueOf(listBean.getPid()));
            params.put("num", String.valueOf(listBean.getNum()));
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

                        conAllCheck++;
                        if (conAllCheck<listBeans.size()){
                            conAllChecked(listBeans,checked);
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
}
