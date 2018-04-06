package com.bwie.myeryuekaoti.presenter;

import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/31.
 */

public interface PresenterPort {
    void getResponseBody(ResponseBody responseBody);
    void getError(Throwable throwable);
}
