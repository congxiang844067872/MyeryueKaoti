package com.bwie.myeryuekaoti.presenter;

import com.bwie.myeryuekaoti.MainActivity;
import com.bwie.myeryuekaoti.model.Model;
import com.bwie.myeryuekaoti.view.MyView;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/31.
 */

public class Presenter extends BasePresenter implements PresenterPort{

    private MyView myView;
    private final Model model;

    public Presenter(MyView myView) {
        model = new Model(this);
        this.myView = myView;
    }

    public void getUrl(Observable<ResponseBody> responseBodyObservable) {
        model.getUrl(responseBodyObservable);
    }

    @Override
    public void getResponseBody(ResponseBody responseBody) {
        myView.getResponseBody(responseBody);
    }

    @Override
    public void getError(Throwable throwable) {
        myView.getError(throwable);
    }

    public void getJieYue() {
        model.getJieYue();
    }
}
