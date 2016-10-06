package com.simpleweather.app.util;

import android.text.TextUtils;

import com.simpleweather.app.db.SimpleWeatherDB;
import com.simpleweather.app.model.City;
import com.simpleweather.app.model.County;
import com.simpleweather.app.model.Province;

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
}
