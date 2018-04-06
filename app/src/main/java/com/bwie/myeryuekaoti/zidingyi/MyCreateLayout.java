package com.bwie.myeryuekaoti.zidingyi;

import android.app.Fragment;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bwie.myeryuekaoti.R;
import com.bwie.myeryuekaoti.bean.ShouYeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/31.
 */

public class MyCreateLayout extends FrameLayout {
    private ViewPager vp;
    private LinearLayout linear_layout;
    private ArrayList<ImageView> listc;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                int vpCurrentItem = vp.getCurrentItem();
                vp.setCurrentItem(vpCurrentItem+1);
                handler.sendEmptyMessageDelayed(0,2000);
            }
        }
    };
    private OnClickLisner clickListner;
    public MyCreateLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public MyCreateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCreateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_banner, this);
        vp = view.findViewById(R.id.vp);
        linear_layout = view.findViewById(R.id.linear_layout);
    }
    public void setImage(List<ShouYeBean.DataBean> list){
        MyPagerAdapter adapter = new MyPagerAdapter(getContext(), list);
        vp.setAdapter(adapter);
        vp.setCurrentItem(list.size()*10000);
        handler.sendEmptyMessageDelayed(0,2000);

        listc = new ArrayList<>();
        linear_layout.removeAllViews();

        for (int i=0;i<list.size();i++){
            ImageView docImage = new ImageView(getContext());
            if (i==0){
                docImage.setImageResource(R.drawable.shape_01);
            }else {
                docImage.setImageResource(R.drawable.shape_02);
            }
            listc.add(docImage);

            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(5,0,5,0);
            linear_layout.addView(docImage,params);
        }

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i=0;i<listc.size();i++){
                    if (position%listc.size()==i){
                        listc.get(i).setImageResource(R.drawable.shape_01);
                    }else {
                        listc.get(i).setImageResource(R.drawable.shape_02);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void setClickListner(OnClickLisner clickListner) {
        this.clickListner = clickListner;
    }

    private class MyPagerAdapter extends PagerAdapter {

        private final Context context;
        private final List<ShouYeBean.DataBean> list;

        public MyPagerAdapter(Context context, List<ShouYeBean.DataBean> list) {

            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load(list.get(position%list.size()).getIcon()).into(imageView);

            //点击事件
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //触发
                    clickListner.onItemClick(position%list.size());
                }
            });


            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(0,2000);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(0,2000);
                            break;

                    }

                    return false;
                }
            });

            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    public interface OnClickLisner{
        void onItemClick(int position);
    }


}
