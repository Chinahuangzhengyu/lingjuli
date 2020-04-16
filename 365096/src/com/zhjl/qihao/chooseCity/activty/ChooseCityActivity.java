package com.zhjl.qihao.chooseCity.activty;

import android.annotation.SuppressLint;
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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.ToastUtils;
import com.zhjl.qihao.activity.userlogin.ListViewAdapter;
import com.zhjl.qihao.activity.userlogin.StringHelper;
import com.zhjl.qihao.chooseCity.api.ChooseCityInterface;
import com.zhjl.qihao.chooseCity.bean.ChooseCityBean;
import com.zhjl.qihao.entity.Person;
import com.zhjl.qihao.util.AbStrUtil;
import com.zhjl.qihao.util.AbViewUtil;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.RequestPermissionUtils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.util.RequestPermissionUtils.REQUEST_PERMISSION_SETTING;

/**
 * 选择城市
 * 根据身份Id查找城市
 *
 * @author sunp
 * 2019-10-24
 * 选择城市列表
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
    private boolean isSelect;

    Map<Character, List<Person>> cityMap;
    List<ChooseCityBean.DataBean> cityArray;
    List<ChooseCityBean.DataBean> newCityArray; //搜索的城市列表
    NewHeaderBar mHeader;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private static final int FINE_LOCATION_CODE = 11110;
    private LinearLayout llSearchContent;
    private LinearLayout llSearchNoContent;

    private String citystring;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            citystring = (String) msg.obj;
            if (citystring != null && !citystring.equals("")) {
                LocalCity.setText(citystring);
            } else {
                LocalCity.setText("定位失败");
            }
        }
    };
    private RelativeLayout location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openshop);


        pId = getIntent().getStringExtra("id");
//        if (getIntent().getStringExtra("Tourist") != null) {
//            mSession.clear();
//        }
        initTopView();
        initViewAndEvent();
        initAreadata();
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //注册监听函数
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            mLocationClient.setLocOption(option);
        } else if (type == 1) {
            mLocationClient.setLocOption(option);
        }
        if (RequestPermissionUtils.checkPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})) {
            mLocationClient.start();// 定位SDK
        } else {
            RequestPermissionUtils.requestPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = location.getCity();
                handler.sendMessage(msg);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //locationService.start();// 定位SDK
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationClient.unRegisterLocationListener(myListener); //注销掉监听
        mLocationClient.stop(); //停止定位服务
        super.onStop();
    }

    private void initTopView() {
        location = (RelativeLayout) findViewById(R.id.ll_location);
        mHeader = NewHeaderBar.createCommomBack(this, "选择城市", this);
        et_search_city = (EditText) findViewById(R.id.et_search_city);
        llSearchContent = findViewById(R.id.ll_search_content);
        llSearchNoContent = findViewById(R.id.ll_search_no_content);
        et_search_city.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                AbViewUtil.colseVirtualKeyboard(ChooseCityActivity.this);
                if (AbStrUtil.isEmpty(v.getText().toString())) {
                    ToastUtils.showToast(mContext, "搜索内容不能为空");
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (cityArray != null && cityArray.size() != 0) {
                        newCityArray = new ArrayList<>();
                        for (int i = 0; i < cityArray.size(); i++) {
                            if (cityArray.get(i).getAreaName().contains(v.getText().toString().trim())) {
                                ChooseCityBean.DataBean dataBean = new ChooseCityBean.DataBean();
                                dataBean.setAreaName(cityArray.get(i).getAreaName());
                                dataBean.setAreId(cityArray.get(i).getAreId());
                                newCityArray.add(dataBean);
                                continue;
                            }
                            if (i == cityArray.size() - 1) {
                                communitySort(newCityArray);
                                isSelect = true;
                            }
                        }
                        if (isSelect && newCityArray.size() == 0) {
                            llSearchContent.setVisibility(View.GONE);
                            llSearchNoContent.setVisibility(View.VISIBLE);
                        }else {
                            llSearchNoContent.setVisibility(View.GONE);
                            llSearchContent.setVisibility(View.VISIBLE);
                        }
                    }
                }
                return false;
            }
        });
        LocalCity = (TextView) this.findViewById(R.id.localCity);
        LocalCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        location.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = null;
                if (citystring.equals("") || citystring.contains("失败")) {
                    Toast.makeText(mContext, "定位失败，请手动选择！", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (ChoosePropertyActivity.PROPERTY_SWITCH.equals(getIntent().getStringExtra(ChoosePropertyActivity.PROPERTY_MODE))) {
                    if (cityArray != null && cityArray.size() != 0) {
                        newCityArray = new ArrayList<>();
                        for (int i = 0; i < cityArray.size(); i++) {
                            if (cityArray.get(i).getAreaName().contains(citystring)) {
                                ChooseCityBean.DataBean dataBean = new ChooseCityBean.DataBean();
                                dataBean.setAreaName(cityArray.get(i).getAreaName());
                                dataBean.setAreId(cityArray.get(i).getAreId());
                                newCityArray.add(dataBean);
                                break;
                            }
                            if (i == cityArray.size() - 1) {
                                isSelect = true;
                            }
                        }
                        if (!isSelect && newCityArray.size() == 0) {
                            Toast.makeText(mContext, "定位城市未找到！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    intent = new Intent(ChooseCityActivity.this, ChoosePropertyActivity.class);
                    intent.putExtra(ChoosePropertyActivity.PROPERTY_MODE, ChoosePropertyActivity.PROPERTY_SWITCH);
                    intent.putExtra(Constants.ID, newCityArray.get(0).getAreId());
                    intent.putExtra(Constants.NAME, citystring);
                    if (getIntent().getStringExtra("Tourist") != null) {
                        intent.putExtra("Tourist", "tourist");
                    }
                    startActivity(intent);
//                } else {
//                    intent = getIntent();
//                    intent.putExtra("id", "");
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
    @SuppressLint("ClickableViewAccessibility")
    public void getIndexView() {
        LayoutParams params = new LayoutParams(
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setTextSize(14);
            tv.setPadding(10, 0, 10, 0);
            //tv.setTextColor(getResources().getColor(R.color.top_color));
            tv.setTextColor(getResources().getColor(R.color.ff999999));
            layoutIndex.addView(tv);
        }
        layoutIndex.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                int index = (int) (y / height);
                int pos = 0;
                if (index > -1 && index < indexStr.length) {// 防止越界
                    String key = indexStr[index];
                    if (null != selector && selector.containsKey(key)) {
                        pos = selector.get(key);
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
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        layoutIndex.setBackgroundColor(Color.parseColor("#F5F6FA"));
//                        layoutIndex.getBackground().setAlpha(160);
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e("+++++++++++++++++", pos + "这是第几个");
//                        for (int j = 0; j < layoutIndex.getChildCount(); j++) {
//                            if (j == pos % indexStr.length) {
//                                TextView tv = (TextView) layoutIndex.getChildAt(j);
//                                tv.setTextColor(Color.parseColor("#FF23AC38"));
//                            } else {
//                                TextView tv = (TextView) layoutIndex.getChildAt(j);
//                                tv.setTextColor(Color.parseColor("#FF999999"));
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        layoutIndex.setBackgroundColor(Color.parseColor("#F5F6FA"));
//                        layoutIndex.getBackground().setAlpha(160);
//                        tv_show.setVisibility(View.GONE);
//                        break;
//                }
                return true;
            }
        });
    }


    protected void initViewAndEvent() {
        //初始化控件
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
//		layoutIndex.setBackgroundColor(Color.parseColor("#606060"));
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
                if (ChoosePropertyActivity.PROPERTY_SWITCH.equals(getIntent().getStringExtra(ChoosePropertyActivity.PROPERTY_MODE))) {
                    intent = new Intent(ChooseCityActivity.this, ChoosePropertyActivity.class);
                    intent.putExtra(ChoosePropertyActivity.PROPERTY_MODE, ChoosePropertyActivity.PROPERTY_SWITCH);
                    if (getIntent().getStringExtra("Tourist") != null) {
                        intent.putExtra("Tourist", "tourist");
                    }
                    intent.putExtra("id", cityId);
                    intent.putExtra("name", Name);
                    startActivity(intent);
                } else {         //  回调
                    intent = getIntent();
                    intent.putExtra("id", cityId);
                    intent.putExtra("name", Name);
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });
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

    private void initAreadata() {
        showprocessdialog();
        ChooseCityInterface cityInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ChooseCityInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = cityInterface.getCityList(body);
        activityRequestData(call, ChooseCityBean.class, new RequestResult<ChooseCityBean>() {
            @Override
            public void success(ChooseCityBean result, String message) {
                if (result.getData().size() != 0) {
                    persons = new ArrayList<Person>();
                    cityArray = result.getData();
                    communitySort(cityArray);
                } else {
                    Toast.makeText(mContext, "暂无数据！", Toast.LENGTH_SHORT).show();
                }
                dismissdialog();
            }

            @Override
            public void fail() {
                dismissdialog();
            }
        });
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
        initViewAndEvent();
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
                        RequestPermissionUtils.showShortCut(this, "开启定位需要位置信息权限！", llSearchContent);
                    }
                }
            }
            if (isAllGranted) {
                mLocationClient.start();// 定位SDK
            } else {
                Toast.makeText(this, "开启定位需要位置信息权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (RequestPermissionUtils.checkPermission(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION})) {
                mLocationClient.start();// 定位SDK
            } else {
                Toast.makeText(mContext, "设置位置信息权限失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



