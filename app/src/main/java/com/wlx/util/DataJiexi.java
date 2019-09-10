package com.wlx.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.wanglingxiang.weather_demo.MyApplication;
import com.wlx.dao.TianQiXingxi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangzhixian on 2019/4/26.
 */

public class DataJiexi {
    private static Context mContext;

    public DataJiexi(Context context) {
        mContext = context;
    }

    //解析数据
    public static boolean jiexi(String data){
        boolean sfcg = false;
        Log.e("aa", "jiexi: "+data );
        try {
            JSONObject jsonObject = new JSONObject(data);
            String reason = jsonObject.getString("reason");
            if (jsonObject.getString("reason").equals("查询成功!")||jsonObject.getString("reason").equals("查询成功")){
                sfcg = true;
                JSONObject result = jsonObject.getJSONObject("result");
                String city = result.getString("city");
                MyApplication.city=city;
                JSONObject realtime = result.getJSONObject("realtime");

                String temperature = realtime.getString("temperature");
                MyApplication.temperature = temperature;

                String info = realtime.getString("info");
                MyApplication.info = info;

                String humidity = realtime.getString("humidity");
                MyApplication.humidity = humidity;

                String direct = realtime.getString("direct");
                MyApplication.direct = direct;

                String aqi = realtime.getString("aqi");
                MyApplication.aqi = aqi;

                JSONArray future = result.getJSONArray("future");
                for (int i = 0; i < future.length(); i++) {
                    JSONObject js = future.getJSONObject(i);
                    String date = js.getString("date");
                    String temperature1 = js.getString("temperature");
                    String weather = js.getString("weather");
                    MyApplication.tianQiXingxiArrayList.add(i,new TianQiXingxi(date,temperature1,weather));
                }
            }else{
                Toast.makeText(mContext,"获取天气预报信息超时，请检查网络连接状态", Toast.LENGTH_SHORT).show();
                sfcg=false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sfcg;
    }
}
