package com.example.wanglingxiang.weather_demo;

import android.app.Application;

import com.wlx.dao.TianQiXingxi;

import java.util.ArrayList;

/**
 * Created by wanglingxiang on 2019/4/25.
 */
/*
            "temperature":"24",
			"humidity":"87",
			"info":"晴",
			"wid":"00",
			"direct":"东北风",
			"power":"2级",
			"aqi":"55"
 */
public class MyApplication extends Application {
    public static boolean sfFirst = true;
    public static String city;
    public static String info;
    public static String temperature;
    public static String humidity;
    public static String direct;
    public static String aqi;

    public static ArrayList<TianQiXingxi> tianQiXingxiArrayList = new ArrayList<>();
}
