package com.bwie.myeryuekaoti.view;

import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/3/31.
 */

public interface MyView {
    void getResponseBody(ResponseBody responseBody);
    void getError(Throwable throwable);
}
