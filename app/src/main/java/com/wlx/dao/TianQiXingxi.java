package com.wlx.dao;

/**
 * Created by wangzhixian on 2019/4/25.
 */

public class TianQiXingxi {
    private String date;
    private String temperature;
    private String weather;

    public TianQiXingxi() {
    }

    public TianQiXingxi(String date, String temperature, String weather) {
        this.date = date;
        this.temperature = temperature;
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
