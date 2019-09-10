package com.example.wanglingxiang.weather_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wlx.dao.CityData;
import com.wlx.util.DataJiexi;
import com.wlx.util.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by wanglingxiang on 2019/4/24.
 */

public class SosuoActivity extends AppCompatActivity {
    private ListView lv_sosuo;
    private ArrayList<CityData> mCityDataList = new ArrayList<>();
    private ListViewBaseAdapter mListViewBaseAdapter;
    private ImageView img_fanhui_sosuo;
    private TextView tv_title_sosuo;
    private EditText et_title_sosuo;
    private ImageView img_sosuo_sosuo;
    private boolean sfsosuo = false;
    private ArrayList<String> searchData = new ArrayList<>();
    private ArrayList<String> lockItems = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosuocity);
        initView();
        mSharedPreferences = getSharedPreferences("city",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        try {
            getCitylist();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        searchData.addAll(lockItems);  //设置初始结果显示为所有查询list
        mListViewBaseAdapter = new ListViewBaseAdapter();
        lv_sosuo.setAdapter(mListViewBaseAdapter);
    }

    private void initView() {
        lv_sosuo = (ListView) findViewById(R.id.lv_sosuo);

        //为EditText设置监听
        img_fanhui_sosuo = (ImageView) findViewById(R.id.img_fanhui_sosuo);
        tv_title_sosuo = (TextView) findViewById(R.id.tv_title_sosuo);
        et_title_sosuo = (EditText) findViewById(R.id.et_title_sosuo);
        img_sosuo_sosuo = (ImageView) findViewById(R.id.img_sosuo_sosuo);

        img_sosuo_sosuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_title_sosuo.setText("");
                sfsosuo = true;
                tv_title_sosuo.setVisibility(View.GONE);
                et_title_sosuo.setVisibility(View.VISIBLE);
            }
        });
        img_fanhui_sosuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sfsosuo){
                    sfsosuo = false;
                    et_title_sosuo.setVisibility(View.GONE);
                    tv_title_sosuo.setVisibility(View.VISIBLE);
                }else{
                    Intent intent = new Intent(SosuoActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        //实时监听内容改变
        et_title_sosuo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
                searchResetData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            }
        });

        //ListView点击事件
        lv_sosuo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] split = searchData.get(i).split("\\.");
                final String city = split[split.length - 1];
                OkHttpUtil okHttpUtil = new OkHttpUtil(SosuoActivity.this, city);
                okHttpUtil.get(true, new OkHttpUtil.CallBack() {
                    @Override
                    public void onFinish(String[] resultList) {
                        if (resultList!=null){
                            DataJiexi dataJiexi = new DataJiexi(SosuoActivity.this);
                            boolean jiexi = dataJiexi.jiexi(resultList[0]);
                            if (jiexi){
                                mEditor.putString("city",city);
                                mEditor.commit();
                                Intent intent = new Intent(SosuoActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                });
            }
        });
    }

    /**
     * 搜索数据
     *
     * @param s 搜索字符
     */
    public void searchResetData(String s) {
        searchData.clear();
        //如果为null，直接使用全部数据
        if (s.equals("")) {
            searchData.addAll(lockItems);
        } else {
            //否则，匹配相应的数据
            for (int i = 0; i < lockItems.size(); i++) {
                if (lockItems.get(i).indexOf(s) >= 0) {//这里可拓展自己想要的，甚至可以拆分搜索汉字来匹配
                    searchData.add(lockItems.get(i));
                }
            }
        }

        //刷新数据
        mListViewBaseAdapter.notifyDataSetChanged();
    }

    //读取城市列表
    private void getCitylist() throws IOException, JSONException {
        InputStream open = getResources().getAssets().open("citylist.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(open);
        String strCitylist = "";
        char[] chars = new char[1024];
        int len = 0;
        while ((len = inputStreamReader.read(chars)) != -1) {
            strCitylist = strCitylist + new String(chars, 0, len);
        }
        JSONObject jsonObject = new JSONObject(strCitylist);
        JSONArray result = jsonObject.getJSONArray("result");
        for (int i = 0; i < result.length(); i++) {
            JSONObject js = result.getJSONObject(i);
            CityData cityData = new CityData();
            String id = js.getString("id");
            cityData.setId(id);
            String province = js.getString("province");
            cityData.setProvince(province);
            String city = js.getString("city");
            cityData.setCity(city);
            String district = js.getString("district");
            cityData.setDistrict(district);
            mCityDataList.add(cityData);

            String strcity;
            if (province.equals(city)) {
                strcity = city;
            } else {
                strcity = province + "." + city;
            }
            if (!(city.equals(district))) {
                strcity = strcity + "." + district;
            }
            lockItems.add(strcity);
        }

    }
/*
    private void submit() {
        // validate
        String sosuo = et_title_sosuo.getText().toString().trim();
        if (TextUtils.isEmpty(sosuo)) {
            Toast.makeText(this, "search...", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }*/

    //适配器
    private class ListViewBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchData.size();
        }

        @Override
        public Object getItem(int i) {
            return searchData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(SosuoActivity.this).inflate(R.layout.citylist_item, null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_citylist_item.setText(searchData.get(i));
            return view;
        }

        private class ViewHolder {
            public View rootView;
            public TextView tv_citylist_item;

            private ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv_citylist_item = (TextView) rootView.findViewById(R.id.tv_citylist_item);
            }

        }
    }
}
