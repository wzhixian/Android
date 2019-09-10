package com.wlx.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by cqw on 2018/11/17.
 */

public class OkHttpUtil {
    Context context;
    String url = "http://192.168.0.254:80/transport/api/%s";
    Handler handler = new Handler();
    OkHttpClient client = null;
    String city;

    public OkHttpUtil(Context context,String city) {
        this.context = context;
        this.city = city;
        if (client == null) {
            synchronized (OkHttpUtil.class) {
                if (client == null) {
                    client = new OkHttpClient();
                }
            }
        }
    }

    public interface CallBack {
        void onFinish(String[] resultList);
    }

    private void send(Callback callback) {
        String cityurl = "";
        try {
            cityurl = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://apis.juhe.cn/simpleWeather/query?city="+cityurl+"&key=e57e1e806b66fbc359be0f3333250fb9";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void get( boolean flag, final CallBack callBack) {
        final ProgressDialog dialog = new ProgressDialog(context);
        if (flag == true) {
            dialog.setMessage("正在进行网络请求...");
            dialog.show();
        }
        final String[] resultList = new String[1];
        final List<Integer> check = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            final int finalI = i;
            send(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dialog.dismiss();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    resultList[finalI] = response.body().string();
                    check.add(finalI);
                    if (check.size() == resultList.length) {
                        dialog.dismiss();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFinish(resultList);
                            }
                        });
                    }
                }
            });
        }
    }
}
