package com.zhjl.qihao.address.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.activity.userlogin.StringHelper;
import com.zhjl.qihao.chooseCity.api.ChooseCityInterface;
import com.zhjl.qihao.chooseCity.bean.ChooseCityBean;
import com.zhjl.qihao.entity.Person;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressListFragment extends VolleyBaseFragment {

    @BindView(R.id.lv_city)
    ListView lvCity;
    Unbinder unbinder;
    private View view;
    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    private String[] indexStr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Map<Character, List<Person>> cityMap;
    private List<Person> newPersons;
    private Map<Integer, Boolean> map = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_address_list, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ChooseCityInterface cityInterface = ApiHelper.getInstance().buildRetrofit(getContext()).createService(ChooseCityInterface.class);
        RequestBody body = ParamForNet.put(new HashMap<String, Object>());
        Call<ResponseBody> call = cityInterface.getCityList(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String string = response.body().string();
                        if (!string.equals("")) {
                            JSONObject parse = new JSONObject(string);
                            String message = parse.getString("message");
                            String result = parse.getString("code");
                            if (result.equals("200")) {
                                Gson gson = new Gson();
                                ChooseCityBean cityBean = gson.fromJson(string, ChooseCityBean.class);
                                communitySort(cityBean.getData());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    class AddressCityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newPersons.size();
        }

        @Override
        public Object getItem(int position) {
            return newPersons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = newPersons.get(position).getName();
            CityViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_item,
                        null);
                holder = new CityViewHolder();
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holder.tvCityName = (TextView) convertView.findViewById(R.id.tv_city_name);
                convertView.setTag(holder);
            } else {
                holder = (CityViewHolder) convertView.getTag();
            }
            if (item.length() == 1) {
                holder.tvName.setText(item);
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvCityName.setVisibility(View.VISIBLE);
                holder.tvCityName.setText(newPersons.get(position + 1).getName());
                map.put(position + 1, true);
            } else {
                if (map.get(position).booleanValue()) {
                    holder.tvName.setVisibility(View.GONE);
                    holder.tvCityName.setVisibility(View.GONE);
                } else {
                    holder.tvName.setText("A");
                    holder.tvName.setVisibility(View.INVISIBLE);
                    holder.tvCityName.setVisibility(View.VISIBLE);
                    holder.tvCityName.setText(newPersons.get(position).getName());
                }
            }
            return convertView;
        }
    }

    private class CityViewHolder {
        TextView tvName;
        TextView tvCityName;
    }


    public void communitySort(List<ChooseCityBean.DataBean> array) {
        cityMap = new HashMap<Character, List<Person>>();
        for (int i = 0; i < array.size(); i++) {
            String areaName = array.get(i).getAreaName();
            String pingying = StringHelper.getPingYin(areaName);
            Person person = new Person(areaName);
            person.setId(array.get(i).getAreId());
            person.setPinYinName(pingying);
            char firstChar = pingying.charAt(0);
            List<Person> tempList = null;
            if (cityMap.containsKey(firstChar)) {
                List<Person> persons = cityMap.get(firstChar);
                persons.add(person);
                cityMap.put(firstChar, persons);
            } else {
                tempList = new ArrayList<Person>();
                tempList.add(person);
                cityMap.put(firstChar, tempList);
            }
        }

        newPersons = new ArrayList<Person>();
        Set keys1 = cityMap.keySet();
        Object[] keys = keys1.toArray();
        Arrays.sort(keys);  //进行排序
        if (selector == null) selector = new HashMap<String, Integer>();
        for (int i = 0; i < keys.length; i++) {
            Character key = (Character) keys[i];
            Person person = new Person(String.valueOf(Character.toUpperCase(key)));
            selector.put(String.valueOf(Character.toUpperCase(key)), newPersons.size());
            newPersons.add(person);
            newPersons.addAll(cityMap.get(key));
        }
        for (int i = 0; i < newPersons.size(); i++) {
            map.put(i, false);
        }
        lvCity.setAdapter(new AddressCityAdapter());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
