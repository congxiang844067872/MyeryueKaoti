package com.bwie.myeryuekaoti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bwie.myeryuekaoti.activity.GouWuCheActivity;
import com.bwie.myeryuekaoti.activity.Main2Activity;
import com.bwie.myeryuekaoti.adapter.MyMiaoShaAdapter;
import com.bwie.myeryuekaoti.adapter.MyTuiJianAdapter;
import com.bwie.myeryuekaoti.bean.ShouYeBean;
import com.bwie.myeryuekaoti.presenter.Presenter;
import com.bwie.myeryuekaoti.url.ApiUrl;
import com.bwie.myeryuekaoti.url.RetrofitUtil;
import com.bwie.myeryuekaoti.view.MyView;
import com.bwie.myeryuekaoti.view.SetClicked;
import com.bwie.myeryuekaoti.zidingyi.MyCreateLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements MyView {

    @BindView(R.id.myCreate_layout)
    MyCreateLayout myCreateLayout;
    @BindView(R.id.recycler_miaosha)
    RecyclerView recyclerMiaosha;
    @BindView(R.id.recycler_tuijian)
    RecyclerView recyclerTuijian;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new Presenter(this);
        presenter.attachView(this);
        Map<String, String> map = new HashMap<>();
        presenter.getUrl(RetrofitUtil.getSerVice().doGet(ApiUrl.shouye, map));

    }

    @Override
    public void getResponseBody(ResponseBody responseBody) {
        try {
            //Log.d("+++++++",responseBody.string());
            ShouYeBean shouYeBean = new Gson().fromJson(responseBody.string()
                    , ShouYeBean.class);
            final List<ShouYeBean.DataBean> data = shouYeBean.getData();
            myCreateLayout.setImage(data);
            //设置点击事件
            myCreateLayout.setClickListner(new MyCreateLayout.OnClickLisner() {
                @Override
                public void onItemClick(int position) {
                    if (data.get(position).getType() == 0) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("url", data.get(position).getUrl());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "我要跳转到商品详情页", Toast.LENGTH_SHORT).show();

                    }
                }
            });


            recyclerMiaosha.setLayoutManager(new LinearLayoutManager(this
                    ,LinearLayoutManager.HORIZONTAL,false));
            MyMiaoShaAdapter adapter = new MyMiaoShaAdapter(this, shouYeBean);
            recyclerMiaosha.setAdapter(adapter);
            adapter.setonclick(new SetClicked() {
                @Override
                public void onClicked(int position) {
                    Intent intent = new Intent(MainActivity
                            .this, GouWuCheActivity.class);
                    startActivity(intent);
                }
            });

            recyclerTuijian.setLayoutManager(new GridLayoutManager(this
                    ,2,GridLayoutManager.VERTICAL,false));
            MyTuiJianAdapter adapter1 = new MyTuiJianAdapter(this, shouYeBean);
            recyclerTuijian.setAdapter(adapter1);
            adapter1.setonclick(new SetClicked() {
                @Override
                public void onClicked(int position) {
                    Intent intent = new Intent(MainActivity
                            .this, GouWuCheActivity.class);
                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void getError(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.getJieYue();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
