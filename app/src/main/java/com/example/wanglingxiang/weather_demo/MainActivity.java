package com.example.wanglingxiang.weather_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wlx.dao.TianQiXingxi;
import com.wlx.util.DataJiexi;
import com.wlx.util.OkHttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ScrollView mScrollViewMain;
    private ImageView mImgaddMain;
    private TextView tv_city_main;
    private TextView tv_wendu_main;
    private TextView tv_ztai_main;
    private TextView tv_xiangqingwendu_main;
    private TextView tv_xiangqingshidu_main;
    private TextView tv_xiangqingaqi_main;
    private TextView tv_xiangqingfongxiang_main;
    private TextView tv_yubaodate1_main;
    private ImageView img_yubaoztai1_main;
    private TextView tv_yubaoztai1_main;
    private TextView tv_yubaowendu1_main;
    private TextView tv_yubaodate2_main;
    private ImageView img_yubaoztai2_main;
    private TextView tv_yubaoztai2_main;
    private TextView tv_yubaowendu2_main;
    private TextView tv_yubaodate3_main;
    private ImageView img_yubaoztai3_main;
    private TextView tv_yubaoztai3_main;
    private TextView tv_yubaowendu3_main;
    private TextView tv_yubaodate4_main;
    private ImageView img_yubaoztai4_main;
    private TextView tv_yubaoztai4_main;
    private TextView tv_yubaowendu4_main;
    private TextView tv_yubaodate5_main;
    private ImageView img_yubaoztai5_main;
    private TextView tv_yubaoztai5_main;
    private TextView tv_yubaowendu5_main;
    private ScrollView scrollView_main;
    private ImageView imgadd_main;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Map<String, Integer> mImgMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        imgMaps();
        mSharedPreferences = getSharedPreferences("city", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        if (MyApplication.sfFirst) {
            String city = mSharedPreferences.getString("city", "长沙");
            OkHttpUtil okHttpUtil = new OkHttpUtil(MainActivity.this, city);
            okHttpUtil.get(true, new OkHttpUtil.CallBack() {
                @Override
                public void onFinish(String[] resultList) {
                    DataJiexi dataJiexi = new DataJiexi(MainActivity.this);
                    boolean jiexi = dataJiexi.jiexi(resultList[0]);
                    if (jiexi){
                        setKongjian();
                    }
                }
            });
            MyApplication.sfFirst = false;
        } else {
            setKongjian();
        }
    }

    private void setKongjian() {
        tv_city_main.setText(MyApplication.city);
        tv_wendu_main.setText(MyApplication.temperature + "℃");
        tv_ztai_main.setText(MyApplication.info);

        tv_xiangqingwendu_main.setText("温度\n" + MyApplication.temperature+"℃");
        tv_xiangqingshidu_main.setText("湿度\n" + MyApplication.humidity);
        tv_xiangqingaqi_main.setText("AQI\n" + MyApplication.aqi);
        tv_xiangqingfongxiang_main.setText("风向\n" + MyApplication.direct);

        ArrayList<TianQiXingxi> tianQiXingxiArrayList = MyApplication.tianQiXingxiArrayList;

        tv_yubaodate1_main.setText(tianQiXingxiArrayList.get(0).getDate());
        if (mImgMap.get(tianQiXingxiArrayList.get(0).getWeather()) != null){
            img_yubaoztai1_main.setBackgroundResource(mImgMap.get(tianQiXingxiArrayList.get(0).getWeather()));
        }
        tv_yubaoztai1_main.setText(tianQiXingxiArrayList.get(0).getWeather());
        tv_yubaowendu1_main.setText(tianQiXingxiArrayList.get(0).getTemperature());
        tv_yubaodate2_main.setText(tianQiXingxiArrayList.get(1).getDate());

        if (mImgMap.get(tianQiXingxiArrayList.get(1).getWeather()) != null){
            img_yubaoztai2_main.setBackgroundResource(mImgMap.get(tianQiXingxiArrayList.get(1).getWeather()));
        }
        tv_yubaoztai2_main.setText(tianQiXingxiArrayList.get(1).getWeather());
        tv_yubaowendu2_main.setText(tianQiXingxiArrayList.get(1).getTemperature());

        tv_yubaodate3_main.setText(tianQiXingxiArrayList.get(2).getDate());
        if (mImgMap.get(tianQiXingxiArrayList.get(2).getWeather())!=null){
            img_yubaoztai3_main.setBackgroundResource(mImgMap.get(tianQiXingxiArrayList.get(2).getWeather()));
        }
        tv_yubaoztai3_main.setText(tianQiXingxiArrayList.get(2).getWeather());
        tv_yubaowendu3_main.setText(tianQiXingxiArrayList.get(2).getTemperature());

        tv_yubaodate4_main.setText(tianQiXingxiArrayList.get(3).getDate());
        if (mImgMap.get(tianQiXingxiArrayList.get(3).getWeather()) != null){
            img_yubaoztai4_main.setBackgroundResource(mImgMap.get(tianQiXingxiArrayList.get(3).getWeather()));
        }
        tv_yubaoztai4_main.setText(tianQiXingxiArrayList.get(3).getWeather());
        tv_yubaowendu4_main.setText(tianQiXingxiArrayList.get(3).getTemperature());

        tv_yubaodate5_main.setText(tianQiXingxiArrayList.get(4).getDate());
        if (mImgMap.get(tianQiXingxiArrayList.get(4).getWeather()) != null){
            img_yubaoztai5_main.setBackgroundResource(mImgMap.get(tianQiXingxiArrayList.get(4).getWeather()));
        }
        tv_yubaoztai5_main.setText(tianQiXingxiArrayList.get(4).getWeather());
        tv_yubaowendu5_main.setText(tianQiXingxiArrayList.get(4).getTemperature());
    }

    private void initView() {
        mScrollViewMain = (ScrollView) findViewById(R.id.scrollView_main);
        mImgaddMain = (ImageView) findViewById(R.id.imgadd_main);

        mImgaddMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SosuoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_city_main = (TextView) findViewById(R.id.tv_city_main);
        tv_wendu_main = (TextView) findViewById(R.id.tv_wendu_main);
        tv_ztai_main = (TextView) findViewById(R.id.tv_ztai_main);
        tv_xiangqingwendu_main = (TextView) findViewById(R.id.tv_xiangqingwendu_main);
        tv_xiangqingshidu_main = (TextView) findViewById(R.id.tv_xiangqingshidu_main);
        tv_xiangqingaqi_main = (TextView) findViewById(R.id.tv_xiangqingaqi_main);
        tv_xiangqingfongxiang_main = (TextView) findViewById(R.id.tv_xiangqingfongxiang_main);

        tv_yubaodate1_main = (TextView) findViewById(R.id.tv_yubaodate1_main);
        img_yubaoztai1_main = (ImageView) findViewById(R.id.img_yubaoztai1_main);
        tv_yubaoztai1_main = (TextView) findViewById(R.id.tv_yubaoztai1_main);
        tv_yubaowendu1_main = (TextView) findViewById(R.id.tv_yubaowendu1_main);

        tv_yubaodate2_main = (TextView) findViewById(R.id.tv_yubaodate2_main);
        img_yubaoztai2_main = (ImageView) findViewById(R.id.img_yubaoztai2_main);
        tv_yubaoztai2_main = (TextView) findViewById(R.id.tv_yubaoztai2_main);
        tv_yubaowendu2_main = (TextView) findViewById(R.id.tv_yubaowendu2_main);

        tv_yubaodate3_main = (TextView) findViewById(R.id.tv_yubaodate3_main);
        img_yubaoztai3_main = (ImageView) findViewById(R.id.img_yubaoztai3_main);
        tv_yubaoztai3_main = (TextView) findViewById(R.id.tv_yubaoztai3_main);
        tv_yubaowendu3_main = (TextView) findViewById(R.id.tv_yubaowendu3_main);

        tv_yubaodate4_main = (TextView) findViewById(R.id.tv_yubaodate4_main);
        img_yubaoztai4_main = (ImageView) findViewById(R.id.img_yubaoztai4_main);
        tv_yubaoztai4_main = (TextView) findViewById(R.id.tv_yubaoztai4_main);
        tv_yubaowendu4_main = (TextView) findViewById(R.id.tv_yubaowendu4_main);

        tv_yubaodate5_main = (TextView) findViewById(R.id.tv_yubaodate5_main);
        img_yubaoztai5_main = (ImageView) findViewById(R.id.img_yubaoztai5_main);
        tv_yubaoztai5_main = (TextView) findViewById(R.id.tv_yubaoztai5_main);
        tv_yubaowendu5_main = (TextView) findViewById(R.id.tv_yubaowendu5_main);

        scrollView_main = (ScrollView) findViewById(R.id.scrollView_main);
        imgadd_main = (ImageView) findViewById(R.id.imgadd_main);
    }

    private void imgMaps(){
        mImgMap = new HashMap<>();
        mImgMap.put("晴",R.drawable.weather_00);
        mImgMap.put("多云",R.drawable.weather_01);
        mImgMap.put("阴",R.drawable.weather_02);
        mImgMap.put("阵雨",R.drawable.weather_03);
        mImgMap.put("雷阵雨",R.drawable.weather_04);
        mImgMap.put("雷阵雨伴有冰雹",R.drawable.weather_05);
        mImgMap.put("雨夹雪",R.drawable.weather_06);
        mImgMap.put("小雨",R.drawable.weather_07);
        mImgMap.put("中雨",R.drawable.weather_08);
        mImgMap.put("大雨",R.drawable.weather_09);
        mImgMap.put("暴雨",R.drawable.weather_10);
        mImgMap.put("大暴雨",R.drawable.weather_11);
        mImgMap.put("特大暴雨",R.drawable.weather_12);
        mImgMap.put("阵雪",R.drawable.weather_13);
        mImgMap.put("小雪",R.drawable.weather_14);
        mImgMap.put("中雪",R.drawable.weather_15);
        mImgMap.put("大雪",R.drawable.weather_16);
        mImgMap.put("暴雪",R.drawable.weather_17);
        mImgMap.put("雾",R.drawable.weather_18);
        mImgMap.put("冻雨",R.drawable.weather_19);
        mImgMap.put("沙尘暴",R.drawable.weather_20);
        mImgMap.put("小雨转中雨",R.drawable.weather_21);
        mImgMap.put("中雨转大雨",R.drawable.weather_22);
        mImgMap.put("大雨转暴雨",R.drawable.weather_23);
        mImgMap.put("暴雨转大暴雨",R.drawable.weather_24);
        mImgMap.put("大暴雨转特大暴雨",R.drawable.weather_25);
        mImgMap.put("小雪转中雪",R.drawable.weather_26);
        mImgMap.put("中雪转大雪",R.drawable.weather_27);
        mImgMap.put("大雪转暴雪",R.drawable.weather_28);
        mImgMap.put("浮尘",R.drawable.weather_29);
        mImgMap.put("扬沙",R.drawable.weather_30);
        mImgMap.put("强沙尘暴",R.drawable.weather_31);
        mImgMap.put("霾",R.drawable.weather_53);
    }
}
