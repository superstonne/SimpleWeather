package com.simpleweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpleweather.app.model.City;
import com.simpleweather.app.model.County;
import com.simpleweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobao on 2016/10/6.
 */

public class SimpleWeatherDB {
    /*
    Database name
     */
    private static final String DB_NAME = "simple_weather";
    /*
    Database version
     */
    private static final int DB_VERSION = 1;

    private static SimpleWeatherDB simpleWeatherDB;

    private SQLiteDatabase database;

    private SimpleWeatherDB(Context context) {
        SimpleWeatherDBOpenHelper dbOpenHelper = new SimpleWeatherDBOpenHelper(context, DB_NAME, null, DB_VERSION);
        database = dbOpenHelper.getWritableDatabase();
    }
    public static synchronized SimpleWeatherDB getInstance(Context context) {
        if (simpleWeatherDB == null) {
            simpleWeatherDB = new SimpleWeatherDB(context);
        }
        return simpleWeatherDB;
    }
    public void saveProvince(Province province) {
        if (province == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("province_name", province.getProvinceName());
        values.put("province_code", province.getProvinceCode());
        database.insert("province", null, values);
    }
    public List<Province> loadProvinces() {
        List<Province> provinceList = new ArrayList<Province>();
        Cursor cursor = database.query("province", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Province province = new Province();
            province.setId(cursor.getInt(cursor.getColumnIndex("id")));
            province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
            province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
            provinceList.add(province);
        }
        return provinceList;
    }
    public void saveCity(City city) {
        if (city == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("city_name", city.getCityName());
        values.put("city_code", city.getCityCode());
        values.put("province_id", city.getProvinceId());
        database.insert("city", null, values);
    }
    public List<City> loadCities(int provinceId) {
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = database.query("city", null, "province_id = ?", new String[] { String.valueOf(provinceId)}, null, null, null, null);
        while (cursor.moveToNext()) {
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(provinceId);
            cityList.add(city);
        }
        return cityList;
    }
    public void saveCounty(County county) {
        if (county == null) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put("county_name", county.getCountyName());
        values.put("county_code", county.getCountyCode());
        values.put("city_id", county.getCityId());
        database.insert("county", null, values);
    }
    public List<County> loadCounties(int cityId) {
        List<County> countyList = new ArrayList<County>();
        Cursor cursor = database.query("county", null, "city_id = ?", new String[] { String.valueOf(cityId)}, null, null, null, null);
        while (cursor.moveToNext()) {
            County county = new County();
            county.setId(cursor.getInt(cursor.getColumnIndex("id")));
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            county.setCityId(cityId);
            countyList.add(county);
        }
        return countyList;
    }
 }
