package com.simpleweather.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simpleweather.app.R;
import com.simpleweather.app.util.HttpCallbackListener;
import com.simpleweather.app.util.HttpUtil;
import com.simpleweather.app.util.Utility;

import org.w3c.dom.Text;

/**
 * Created by xiaobao on 2016/10/6.
 */

public class WeatherActivity extends Activity implements View.OnClickListener {
    private LinearLayout weatherInfoLinearLayout;
    private TextView cityNameTextView;
    private TextView publishTimeTextView;
    private TextView weatherDesTextView;
    private TextView temp1TextView;
    private TextView temp2TextView;
    private TextView currentDateTextView;
    private Button switchCityBtn;
    private Button refreshWeatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        weatherInfoLinearLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameTextView = (TextView) findViewById(R.id.city_name_text_view);
        publishTimeTextView = (TextView) findViewById(R.id.publish_text);
        weatherDesTextView = (TextView) findViewById(R.id.weather_desp);
        temp1TextView = (TextView) findViewById(R.id.temp1);
        temp2TextView = (TextView) findViewById(R.id.temp2);
        currentDateTextView = (TextView) findViewById(R.id.current_date);
        switchCityBtn = (Button) findViewById(R.id.switch_city_btn);
        refreshWeatherBtn = (Button) findViewById(R.id.refresh_weather_btn);
        switchCityBtn.setOnClickListener(this);
        refreshWeatherBtn.setOnClickListener(this);
        String countyCode = getIntent().getStringExtra("county_code");
        if (!TextUtils.isEmpty(countyCode)) {
            publishTimeTextView.setText("synchronizing...");
            weatherInfoLinearLayout.setVisibility(View.INVISIBLE);
            cityNameTextView.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        } else {
            showWeather();
        }

    }
    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");
    }
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");
    }
    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if ("countyCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        queryWeatherInfo(array[1]);
                    }
                } else if ("weatherCode".equals(type)) {
                    Utility.handlerWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTimeTextView.setText("Failed to synchronized.");
                    }
                });
            }
        });
    }
    private void showWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameTextView.setText(sharedPreferences.getString("city_name", ""));
        temp1TextView.setText(sharedPreferences.getString("temp1", ""));
        temp2TextView.setText(sharedPreferences.getString("temp2", ""));
        weatherDesTextView.setText(sharedPreferences.getString("weather_desp", ""));
        publishTimeTextView.setText("Published at " + sharedPreferences.getString("publish_time", ""));
        currentDateTextView.setText(sharedPreferences.getString("current_date", ""));
        weatherInfoLinearLayout.setVisibility(View.VISIBLE);
        cityNameTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city_btn:
                Intent intent = new Intent(WeatherActivity.this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather_btn:
                publishTimeTextView.setText("Synchronizing...");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                if (!TextUtils.isEmpty(preferences.getString("weather_code", ""))) {
                    queryWeatherInfo(preferences.getString("weather_code", ""));
                }
                break;
            default: break;
        }
    }
}
