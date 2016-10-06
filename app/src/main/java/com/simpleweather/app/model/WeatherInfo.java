package com.simpleweather.app.model;

/**
 * Created by xiaobao on 2016/10/6.
 */

public class WeatherInfo {
    private String cityName;
    private String weatherCode;
    private String temp1;
    private String temp2;
    private String weatherDes;
    private String publishTime;

    public WeatherInfo(String cityName, String weatherCode, String temp1, String temp2, String weatherDes, String publishTime) {
        this.cityName = cityName;
        this.weatherCode = weatherCode;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.weatherDes = weatherDes;
        this.publishTime = publishTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getWeatherDes() {
        return weatherDes;
    }

    public void setWeatherDes(String weatherDes) {
        this.weatherDes = weatherDes;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
