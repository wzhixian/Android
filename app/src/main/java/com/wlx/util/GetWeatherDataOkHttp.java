package com.wlx.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanglingxiang on 2019/4/25.
 */
//网络请求获取天气预报数据
public class GetWeatherDataOkHttp {
    private static Context context;
    public GetWeatherDataOkHttp() {
    }

    public GetWeatherDataOkHttp(Context context) {
        this.context = context;
    }

    public interface CallBack {
        void onFinish(String sfcg);
    }
    //网络请求获取天气预报数据
    public static String getWeatherData(String city, final CallBack callBack){
        String cityurl = "";
        try {
            cityurl = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String[] strWeatherData = new String[1];
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("正在进行网络请求...");
        dialog.show();
        String url = "http://apis.juhe.cn/simpleWeather/query?city="+cityurl+"&key=e57e1e806b66fbc359be0f3333250fb9";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //超时或没有网络连接
                dialog.dismiss();
                Toast.makeText(context,"获取天气预报信息超时，请检查网络连接状态",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                strWeatherData[0] = response.body().string();
                dialog.dismiss();
                callBack.onFinish(strWeatherData[0]);
            }
        });

        return strWeatherData[0];
    }


}
