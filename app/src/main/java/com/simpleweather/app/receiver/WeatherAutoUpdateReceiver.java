package com.simpleweather.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.simpleweather.app.service.WeatherAutoUpdateService;

/**
 * Created by xiaobao on 2016/10/6.
 */

public class WeatherAutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, WeatherAutoUpdateService.class);
        context.startService(i);
    }
}
