package com.simpleweather.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.simpleweather.app.db.SimpleWeatherDB;
import com.simpleweather.app.model.City;
import com.simpleweather.app.model.County;
import com.simpleweather.app.model.Province;
import com.simpleweather.app.model.WeatherInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xiaobao on 2016/10/6.
 */

public class Utility {
    public synchronized static boolean handleProvinceResponse(SimpleWeatherDB simpleWeatherDB, String response) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        String[] provinces = response.split(",");
        for (String provinceText : provinces) {
            String[] strs = provinceText.split("\\|");
            Province province = new Province();
            province.setProvinceCode(strs[0]);
            province.setProvinceName(strs[1]);
            simpleWeatherDB.saveProvince(province);
        }
        if (provinces.length > 0) {
            return  true;
        }
        return true;
    }
    public synchronized static boolean handleCityResponse(SimpleWeatherDB simpleWeatherDB, String response, int provinceId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        String[] cities = response.split(",");
        for (String cityText : cities) {
            String[] strs = cityText.split("\\|");
            City city = new City();
            city.setCityCode(strs[0]);
            city.setCityName(strs[1]);
            city.setProvinceId(provinceId);
            simpleWeatherDB.saveCity(city);
        }
        if (cities.length > 0) {
            return  true;
        }
        return true;
    }
    public synchronized static boolean handleCountyResponse(SimpleWeatherDB simpleWeatherDB, String response, int cityId) {
        if (TextUtils.isEmpty(response)) {
            return false;
        }
        String[] counties = response.split(",");
        for (String cityText : counties) {
            String[] strs = cityText.split("\\|");
            County county = new County();
            county.setCountyCode(strs[0]);
            county.setCountyName(strs[1]);
            county.setCityId(cityId);
            simpleWeatherDB.saveCounty(county);
        }
        if (counties.length > 0) {
            return  true;
        }
        return true;
    }
    public static void handlerWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDes = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            WeatherInfo weather = new WeatherInfo(cityName, weatherCode, temp1, temp2, weatherDes, publishTime);
            saveWeatherInfo(context, weather);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static void saveWeatherInfo(Context context, WeatherInfo weatherInfo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年m月d日", Locale.CANADA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", weatherInfo.getCityName());
        editor.putString("weather_code", weatherInfo.getWeatherCode());
        editor.putString("temp1", weatherInfo.getTemp1());
        editor.putString("temp2", weatherInfo.getTemp2());
        editor.putString("weather_desp", weatherInfo.getWeatherDes());
        editor.putString("publish_time", weatherInfo.getPublishTime());
        editor.putString("current_date", simpleDateFormat.format(new Date()));
        editor.commit();
    }
}
