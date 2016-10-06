package com.simpleweather.app.util;

/**
 * Created by xiaobao on 2016/10/6.
 */

public interface HttpCallbackListener {
    public void onFinish(String response);
    public void onError(Exception e);
}
