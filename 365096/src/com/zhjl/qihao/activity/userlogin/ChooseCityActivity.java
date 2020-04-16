package com.zhjl.qihao.activity.userlogin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.entity.Person;
import com.zhjl.qihao.util.AbStrUtil;
import com.zhjl.qihao.util.AbViewUtil;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.util.RequestPermissionUtils;

import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

/**
 * 选择城市
 * 根据身份Id查找城市
 *
 * @author sunp
 *         2014-11-11
 *         选择城市列表
 */

public class ChooseCityActivity extends VolleyBaseActivity {
    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    EditText et_search_city;
    private LinearLayout layoutIndex;
    private ListView listView;
    private TextView tv_show, tv_title;
    TextView LocalCity;
    private ListViewAdapter adapter;
    private String[] indexStr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private List<Person> persons = null;
    private List<Person> newPersons = new ArrayList<Person>();
    private int height;// 字体高度
    private boolean flag = false;
    String pId = "";
    String pName = "";

    Map<Character, List<Person>> cityMap;
    JSONArray cityArray;
    NewHeaderBar mHeader;

//    public LocationService locationService = null;
    private static final int FINE_LOCATION_CODE=11110;

    private String citystring;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            citystring = (String) msg.obj;
            if (citystring != null && !citystring.equals("")) {
                LocalCity.setText("当前城市:" + citystring);
            } else {
                LocalCity.setText("当前城市:" + "定位失败");
            }

            //LocalCity.setText("当前城市:"+citystring);
        }
    };
    private RelativeLayout location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openshop);


//        StatusBarutil.StatusBarLightMode(this, StatusBarutil.StatusBarLightMode(this));
//        NewStatusBarUtils.setStatusBarColor(this, R.color.app_color);
        pId = getIntent().getStringExtra("id");
        //String tourist = getIntent().getStringExtra("Tourist");
        if (getIntent().getStringExtra("Tourist") != null) {
            mSession.clear();
        }
        initTopView();
        initViewAndEvent();
        // -----------location config ------------
//        locationService = ((ZHJLApplication) getApplication()).locationService;
//        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        //注册监听
//        int type = getIntent().getIntExtra("from", 0);
//        if (type == 0) {
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
//            locationService.setLocationOption(locationService.getOption());
//        }
//        if (RequestPermissionUtils.checkPermission(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})){
//            locationService.start();// 定位SDK
//        }else {
//            RequestPermissionUtils.requestPermission(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_CODE);
//        }

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
          /*    *//*  StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");*//*
                *//**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                /*latlng = new LatLng(location.getLatitude(), location.getLongitude());
                LogUtils.e("NewNear", latlng.toString()+"---------"+location.getCity());*/
                //LocalCity.setText("当前城市:"+location.getCity());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = location.getCity();
                handler.sendMessage(msg);
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //locationService.start();// 定位SDK
    }

    @Override
    public void onStop() {
        super.onStop();
//        locationService.unregisterListener(mListener); //注销掉监听
//        locationService.stop(); //停止定位服务
        super.onStop();
    }

    private void initTopView() {
        location = (RelativeLayout) findViewById(R.id.ll_location);
        //mHeader = HeaderBar.createCommomBack(this, "选择城市", getString(R.string.back), this);
        mHeader = NewHeaderBar.createCommomBack(this, "选择城市", this);
        et_search_city = (EditText) findViewById(R.id.et_search_city);
        et_search_city.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                AbViewUtil.colseVirtualKeyboard(ChooseCityActivity.this);
                if (AbStrUtil.isEmpty(v.getText().toString())) {
                    ToastUtils.showToast(mContext, "搜索内容不能为空");
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    JSONArray array = findCityArray(v.getText().toString());
                    communitySort(array);
                }
                return false;
            }
        });
        LocalCity = (TextView) this.findViewById(R.id.localCity);
        //	LocalCity.setText("当前城市:"+mSession.getCity());
        location.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = null;
//                if (ChoosePropertyActivity.PROPERTY_SWITCH.equals(getIntent().getStringExtra(ChoosePropertyActivity.PROPERTY_MODE))) {
//                    intent = new Intent(ChooseCityActivity.this, ChoosePropertyActivity.class);
//                    intent.putExtra(ChoosePropertyActivity.PROPERTY_MODE, ChoosePropertyActivity.PROPERTY_SWITCH);
//                    intent.putExtra(Constants.ID, "");
//                    //intent.putExtra(Constants.NAME,city);
//                    intent.putExtra(Constants.NAME, citystring);
//                    startActivity(intent);
//                } else {
//                    intent = getIntent();
//                    intent.putExtra("id", "");
//                    //intent.putExtra("name",city);
//                    intent.putExtra("name", citystring);
//                    ChooseCityActivity.this.setResult(RESULT_OK, intent);
//                    ChooseCityActivity.this.finish();
//                }

            }
        });

        tv_title = mHeader.getTextViewTitle();
        tv_title.setText("选择城市");
    }

    /**
     * 定位所需属性
     */
    public Vibrator mVibrator;
    public boolean isOffMap;
    public String city;


    /**
     * 重新排序获得一个新的List集合
     *
     * @param allNames
     */
    private void sortList(String[] allNames) {
        newPersons.clear();
        for (int i = 0; i < allNames.length; i++) {
            if (allNames[i].length() != 1) {
                for (int j = 0; j < persons.size(); j++) {
                    if (allNames[i].equals(persons.get(j).getPinYinName())) {
                        Person p = new Person(persons.get(j).getName(), persons
                                .get(j).getPinYinName());
                        p.setId(persons.get(j).getId());
                        newPersons.add(p);
                    }
                }
            } else {
                newPersons.add(new Person(allNames[i]));
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // 在oncreate里面执行下面的代码没反应，因为oncreate里面得到的getHeight=0
        if (!flag) {// 这里为什么要设置个flag进行标记，我这里不先告诉你们，请读者研究，因为这对你们以后的开发有好处
            height = layoutIndex.getMeasuredHeight() / indexStr.length;
            getIndexView();
            flag = true;
        }
    }

    /**
     * 获取排序后的新数据
     *
     * @param persons
     * @return
     */
    public String[] sortIndex(List<Person> persons) {
        TreeSet<String> set = new TreeSet<String>();
        // 获取初始化数据源中的首字母，添加到set中
        for (Person person : persons) {
            set.add(StringHelper.getPinYinHeadChar(person.getName()).substring(
                    0, 1));
        }
        // 新数组的长度为原数据加上set的大小
        String[] names = new String[persons.size() + set.size()];
        int i = 0;
        for (String string : set) {
            names[i] = string;
            i++;
        }
        String[] pinYinNames = new String[persons.size()];
        for (int j = 0; j < persons.size(); j++) {
            persons.get(j).setPinYinName(
                    StringHelper
                            .getPingYin(persons.get(j).getName().toString()));
            pinYinNames[j] = StringHelper.getPingYin(persons.get(j).getName()
                    .toString());
        }
        // 将原数据拷贝到新数据中
        System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
        // 自动按照首字母排序
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    /**
     * 绘制索引列表
     */
    public void getIndexView() {
        LinearLayout.LayoutParams params = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setTextSize(16);
            tv.setPadding(10, 0, 10, 0);
            //tv.setTextColor(getResources().getColor(R.color.top_color));
            tv.setTextColor(getResources().getColor(R.color.ff333333));
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event)

                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.length) {// 防止越界
                        String key = indexStr[index];
                        if (null != selector && selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (listView.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                listView.setSelectionFromTop(
                                        pos + listView.getHeaderViewsCount(), 0);
                            } else {
                                listView.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(indexStr[index]);
                        }
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
					/*	layoutIndex.setBackgroundColor(Color.parseColor("#606060"));
						layoutIndex.setBackgroundColor(color.white);
						layoutIndex.setBackgroundColor(getResources().getColor(color.white));
						layoutIndex.getBackground().setAlpha(125);*/
                            layoutIndex.setBackgroundColor(Color.parseColor("#f2f2f2"));
                            layoutIndex.getBackground().setAlpha(160);
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
					/*	layoutIndex.setBackgroundColor(Color
								.parseColor("#606060"));
						layoutIndex.setBackgroundColor(color.white);
						layoutIndex.getBackground().setAlpha(125);*/
                            layoutIndex.setBackgroundColor(Color.parseColor("#f2f2f2"));
                            layoutIndex.getBackground().setAlpha(160);
                            tv_show.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }


    protected void initViewAndEvent() {
        //初始化控件
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
//		layoutIndex.setBackgroundColor(Color.parseColor("#606060"));
        layoutIndex.setBackgroundColor(Color.parseColor("#f2f2f2"));
        layoutIndex.getBackground().setAlpha(160);
        listView = (ListView) findViewById(R.id.listView);
        tv_show = (TextView) findViewById(R.id.tv);
        tv_show.setVisibility(View.GONE);
        adapter = new ListViewAdapter(this, newPersons);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Log.i("1111111", newPersons.get(arg2).getName());

                String cityId = newPersons.get(arg2).getId();
                String Name = newPersons.get(arg2).getName();
                Intent intent = null;
                //  在这里判断 如果是  switch 模式    跳转到选择小区的界面
//                if (ChoosePropertyActivity.PROPERTY_SWITCH.equals(getIntent().getStringExtra(ChoosePropertyActivity.PROPERTY_MODE))) {
//                    intent = new Intent(ChooseCityActivity.this, ChoosePropertyActivity.class);
//                    intent.putExtra(ChoosePropertyActivity.PROPERTY_MODE, ChoosePropertyActivity.PROPERTY_SWITCH);
//                    if (getIntent().getStringExtra("Tourist") != null) {
//                        intent.putExtra("Tourist", "tourist");
//                    }
//                    intent.putExtra("id", cityId);
//                    intent.putExtra("name", Name);
//                    startActivity(intent);
//                } else {         //  回调
//                    intent = getIntent();
//                    intent.putExtra("id", cityId);
//                    intent.putExtra("name", Name);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }

            }
        });
        //15652232275
        listView.setAdapter(adapter);
    }

    protected void initData() {

        String[] allNames = sortIndex(persons);
        sortList(allNames);
        selector = new HashMap<String, Integer>();
        for (int j = 0; j < indexStr.length; j++) {// 循环字母表，找出newPersons中对应字母的位置
            for (int i = 0; i < newPersons.size(); i++) {
                if (newPersons.get(i).getName().equals(indexStr[j])) {
                    selector.put(indexStr[j], i);
                }
            }

        }

    }


    public JSONArray findCityArray(String city) {
        JSONArray tempCityArray = new JSONArray();
        if (AbStrUtil.isEmpty(city)) {
            tempCityArray = cityArray;
        } else {
            if (cityArray != null) {
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject jsonCity = cityArray.optJSONObject(i);
                    if (jsonCity.optString("areaName").indexOf(city) >= 0) {
                        tempCityArray.put(jsonCity);
                    }
                }
            }

        }
        return tempCityArray;
    }

    public void communitySort(JSONArray array) {
        cityMap = new HashMap<Character, List<Person>>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            String pingying = StringHelper.getPingYin(object.optString("areaName"));
            Person person = new Person(object.optString("areaName"));
            person.setId(object.optString("areaId"));
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
        initViewAndEvent();
    }

    @Override
    public void requestError(VolleyError e, int action) {
        showErrortoast();
        dismissdialog();
    }

    @Override
    public void requestSuccess(JSONObject result, int action) {
        dismissdialog();
        if ("0".equals(result.optString("result"))) {
            try {
                persons = new ArrayList<Person>();
                cityArray = result.optJSONArray("citys");
                communitySort(cityArray);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            ToastUtils.showToast(mContext, result.optString("message"));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            for (int i = 0; i < grantResults.length; i++) {

                //判断权限的结果，如果点击不在提示
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        RequestPermissionUtils.showShortCut(this, "开启定位需要位置信息权限！",location);
                    }
                }
            }
            if (isAllGranted) {
//                locationService.start();// 定位SDK
            }else {
                Toast.makeText(this, "开启定位需要位置信息权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING){
            if (RequestPermissionUtils.checkPermission(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})){
//                locationService.start();// 定位SDK
            }else {
                Toast.makeText(mContext, "设置位置信息权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



