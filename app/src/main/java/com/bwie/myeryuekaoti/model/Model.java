package com.bwie.myeryuekaoti.model;

import android.util.Log;

import com.bwie.myeryuekaoti.presenter.Presenter;
import com.bwie.myeryuekaoti.presenter.PresenterPort;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/31.
 */

public class Model {
    private PresenterPort presenterPort;
    private Disposable d;
    public Model(PresenterPort presenterPort) {

        this.presenterPort = presenterPort;
    }

    public void getUrl(Observable<ResponseBody> responseBodyObservable) {
        responseBodyObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                        Model.this.d = d;
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        presenterPort.getResponseBody(value);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getJieYue() {
        d.dispose();
    }
}
