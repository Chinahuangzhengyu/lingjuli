package com.zhjl.qihao.chooseCity.activty;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.zhjl.qihao.Constants;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.ablogin.api.LoginInterface;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.activity.userlogin.StringHelper;
import com.zhjl.qihao.chooseCity.api.ChooseCityInterface;
import com.zhjl.qihao.chooseCity.bean.LoginSwitchBean;
import com.zhjl.qihao.entity.BillMainBean;
import com.zhjl.qihao.entity.Person;
import com.zhjl.qihao.systemsetting.api.SettingInterface;
import com.zhjl.qihao.systemsetting.bean.AllCommunityBean;
import com.zhjl.qihao.util.AbStrUtil;
import com.zhjl.qihao.util.AbViewUtil;
import com.zhjl.qihao.util.HeaderBar;
import com.zhjl.qihao.util.JSONObjectUtil;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import retrofit2.Callback;

/**
 * 根据身份Id查找城市
 * 选择小区
 *
 * @author south
 * 2014-11-11
 */

public class ChoosePropertyActivity extends VolleyBaseActivity {
    public static final String PROPERTY_SELECT = "property_select";
    public static final String PROPERTY_SWITCH = "property_switch";
    public static final String PROPERTY_MODE = "propertyMode";
    public String propertyMode = "";

    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    private LinearLayout layoutIndex;
    EditText et_search_community;
    private ListView listView;
    private TextView tv_show, tv_title;
    TextView LocalCity;
    private boolean[] checks; // 用于保存checkBox的选择状态
    private PropertyListViewAdapter propertyAdapter;
    private String[] indexStr = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private HashMap<Character, List<Person>> propertyMap;
    List<AllCommunityBean.DataBean> communityArray;

    List<AllCommunityBean.DataBean> newCommunityArray;    //存放搜索出的数据
    private List<Person> persons = null;
    private List<Map<String, String>> mlist = new ArrayList<Map<String, String>>();
    private List<Person> newPersons = new ArrayList<Person>();
    private int height;// 字体高度
    private boolean flag = false;
    TextView tv_empty;
    String pId = "";
    String pName = "";

    JSONArray propertyArray;
    Map<String, JSONArray> propertMap;

    FinalDb fd;

    HeaderBar mHeaderBar;
    public boolean isOffMap;
    public String city;
    boolean isSelect;
    private ChooseCityInterface chooseCityInterface;
    private LinearLayout llSearchContent;
    private LinearLayout llSearchNoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chooseproperty);
//		StatusBarutil.StatusBarLightMode(this, StatusBarutil.StatusBarLightMode(this));
//        NewStatusBarUtils.setStatusBarColor(this, R.color.app_color);
        propertyMode = getIntent().getStringExtra(PROPERTY_MODE);
        propertyMode = PROPERTY_SELECT.equals(propertyMode) ? propertyMode : PROPERTY_SWITCH;

        pName = getIntent().getStringExtra(Constants.NAME);
        pId = getIntent().getStringExtra(Constants.ID);
        initTopView();
        initViewAndEvent();
//        initPropertyData();
//        if (AbStrUtil.isEmpty(pId) && AbStrUtil.isEmpty(pName)) {
//            city = mSession.getCity();
//            LocalCity.setText(city);
//            initAreadata(city, "");
//        } else if (!AbStrUtil.isEmpty(pId)) {
////			pId = getIntent().getStringExtra("id");
//            initAreadata("", pId);
//        } else if (!AbStrUtil.isEmpty(pName)) {
//        }
        initAreadata();
        if (mSession.getSmallCommunityName() != null && !mSession.getSmallCommunityName().equals("")) {
            LocalCity.setVisibility(View.VISIBLE);
            LocalCity.setText(mSession.getSmallCommunityName());
        } else {
            LocalCity.setVisibility(View.GONE);
        }
    }

    private void initTopView() {

        //mHeaderBar = HeaderBar.createCommomBack(this, "选择小区", getString(R.string.back), this);
        NewHeaderBar.createCommomBack(this, "选择小区", this);
        et_search_community = (EditText) findViewById(R.id.et_search_community);
        llSearchContent = findViewById(R.id.ll_search_content);
        llSearchNoContent = findViewById(R.id.ll_search_no_content);
        et_search_community.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                AbViewUtil.colseVirtualKeyboard(ChoosePropertyActivity.this);
                if (AbStrUtil.isEmpty(v.getText().toString())) {
                    Toast.makeText(mContext, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                } else if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (communityArray != null && communityArray.size() != 0) {
                        newCommunityArray = new ArrayList<>();
                        for (int i = 0; i < communityArray.size(); i++) {
                            if (communityArray.get(i).getSmallCommunityName().contains(v.getText().toString().trim())) {
                                AllCommunityBean.DataBean dataBean = new AllCommunityBean.DataBean();
                                dataBean.setSmallCommunityName(communityArray.get(i).getSmallCommunityName());
                                dataBean.setSmallCommunityCode(communityArray.get(i).getSmallCommunityCode());
                                newCommunityArray.add(dataBean);
                                continue;
                            }
                            if (i == communityArray.size() - 1) {
                                communitySort(newCommunityArray);
                                isSelect = true;
                            }
                        }
                        if (isSelect && newCommunityArray.size() == 0) {
                            llSearchContent.setVisibility(View.GONE);
                            llSearchNoContent.setVisibility(View.VISIBLE);
                        } else {
                            llSearchNoContent.setVisibility(View.GONE);
                            llSearchContent.setVisibility(View.VISIBLE);
                        }
                    }
                }
                return false;
            }
        });
        LocalCity = (TextView) this.findViewById(R.id.localCity);
        LocalCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("Tourist") != null&&mSession.getSmallCommunityCode()!=null) {
                    requestTouristLogin(mSession.getSmallCommunityCode());
                } else {
                    if (mSession.getSmallCommunityCode() != null) {
                        Intent intent = new Intent(ChoosePropertyActivity.this, RefactorMainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        if (!AbStrUtil.isEmpty(pName)) {
            LocalCity.setText("当前城市:" + pName);
        }
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText("选择小区");

    }

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
                LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setTextSize(16);
            tv.setPadding(10, 0, 10, 0);
            tv.setTextColor(ContextCompat.getColor(mContext,R.color.ff333333));
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
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
                            layoutIndex.setBackgroundColor(ContextCompat.getColor(mContext,R.color.background_color));
                            layoutIndex.getBackground().setAlpha(160);
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(ContextCompat.getColor(mContext,R.color.background_color));
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
        // 初始化控件

        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
        layoutIndex.setBackgroundColor(ContextCompat.getColor(mContext,R.color.background_color));
        layoutIndex.getBackground().setAlpha(160);
        listView = (ListView) findViewById(R.id.listView);
        tv_empty = (TextView) this.findViewById(R.id.tv_empty);
        listView.setEmptyView(tv_empty);


        tv_show = (TextView) findViewById(R.id.tv);
        tv_show.setVisibility(View.GONE);

        propertyAdapter = new PropertyListViewAdapter(this, newPersons);

        listView.setAdapter(propertyAdapter);

        // TODO 在这里 要记录一下 选择了其他的小区 更改的小区编号；
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) propertyAdapter.getItem(position);
                boolean isTourist = true;

                if (!AbStrUtil.isEmpty(person.getId())) {

                    //如果是选择小区就获取到数据直接后退如果是切换小区就要做切换小区的操作
                    if (PROPERTY_SELECT.equals(propertyMode)) {
                        Intent data = getIntent();
                        data.putExtra(Constants.ID, person.getId());
                        data.putExtra(Constants.NAME, person.getName());

                        data.putExtra(Constants.USERTYPE, "0");
                        mSession.setSmallCommunityCode(person.getId());
                        mSession.setSmallCommunityName(person.getName());
                        ChoosePropertyActivity.this.setResult(RESULT_OK, data);
                        ChoosePropertyActivity.this.finish();
                    } else {

                        if (getIntent().getStringExtra("Tourist") != null) {
                            requestTouristLogin(person.getId());
                        } else {
                            if (mSession.getSmallCommunityCode() != null) {
                                if (mSession.getSmallCommunityCode().equals(person.getId())) {
                                    Intent intent = new Intent(ChoosePropertyActivity.this, RefactorMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    requestSwitchCommunity(person.getId());
                                }
                            } else {
                                requestSwitchCommunity(person.getId());
                            }

                        }
//                        if (propertMap != null && propertMap.containsKey(person.getId())) {
//                            JSONArray jsonArray = propertMap.get(person.getId());
//                            //首页业主切换小区 ，如果业主在此小区只有一个物业就直接切换物业   如果也有两个或者两个以上的物业则弹框
//                            if (jsonArray.length() > 1) {
//                                //proMap中一个角色下存取对应的一个物业
//                                Map<String, JSONObject> proMap = new HashMap<String, JSONObject>();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject jsonObject = jsonArray.optJSONObject(i);
//                                    proMap.put(jsonObject.optString("userType"), jsonObject);
//                                }
//                                //切换物业的时候取权限最大的，，权限   业主1>家人3>租客3
//                                if (proMap.containsKey("1")) {
//                                    house_information(proMap.get("1"), true);
//                                } else if (proMap.containsKey("3")) {
//                                    house_information(proMap.get("3"), true);
//                                } else if (proMap.containsKey("2")) {
//                                    house_information(proMap.get("2"), true);
//                                }
//                            } else if (jsonArray.length() == 1) {
//                                house_information(jsonArray.optJSONObject(0), true);
//                            }
//                        } else {
//                            mSession.setRoomCode("", false);
//                            mSession.setUserType("0");
//                            mSession.setSmallCommunityCode(person.getId());
//                            mSession.setSmallCommunityName(person.getName());
//                            JSONObjectUtil property = new JSONObjectUtil();
//                            try {
//                                property.put("mobile", mSession.getRegisterMobile2());
//                                property.put("smallCommunityCode", person.getId());
//                                property.put("roomCode", "");
//                                property.put("userType", "0");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            house_information(property, false);
//
////							Intent intent =  new Intent(ChoosePropertyActivity.this,MainActivity.class);
////							startActivity(intent);
//                        }
                    }

                }
            }
        });
    }

    private void requestSwitchCommunity(String id) {
        chooseCityInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ChooseCityInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("smallCommunityCode", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = chooseCityInterface.getSwitchCommunity(body);
        activityRequestData(call, LoginSwitchBean.class, new RequestResult<LoginSwitchBean>() {
            @Override
            public void success(LoginSwitchBean result, String message) {
                mSession.setChangeBasic(result);
                if (result.getCode() == 200) {
                    Intent intent = new Intent();
                    intent.setClass(ChoosePropertyActivity.this, RefactorMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestTouristLogin(String id) {
        LoginInterface loginInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(LoginInterface.class);
        final Map<String, Object> map = new HashMap<>();
        map.put("smallCommunityCode", id);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = loginInterface.touristLogin(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        mSession.clear();
                        String string = response.body().string();
                        JSONObject json = new JSONObject(string);
                        JSONObject data = json.getJSONObject("data");
                        JSONObject userInfo = data.getJSONObject("userInfo");
                        String token = data.getString("token");
                        mSession.setmToken(token);
                        mSession.setUserType(String.valueOf(userInfo.getInt("userType")));
                        mSession.setSmallCommunityName(userInfo.getString("smallCommunityName"));
                        mSession.setSmallCommunityCode(userInfo.getString("smallCommunityCode"));
                        Intent intent = new Intent(ChoosePropertyActivity.this, RefactorMainActivity.class);
                        intent.putExtra("Tourist",true);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ChoosePropertyActivity.this, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ChoosePropertyActivity.this, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });
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
        SettingInterface chooseCityInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(SettingInterface.class);
        Map<String, Object> map = new HashMap<>();
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = chooseCityInterface.getAllCommunityList(body);
        activityRequestData(call, AllCommunityBean.class, new RequestResult<AllCommunityBean>() {
            @Override
            public void success(AllCommunityBean result, String message) {
                if (result.getData().size() != 0) {
                    persons = new ArrayList<Person>();
                    communityArray = result.getData();
                    communitySort(communityArray);
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


    public void communitySort(List<AllCommunityBean.DataBean> array) {
        propertyMap = new HashMap<Character, List<Person>>();
        for (int i = 0; i < array.size(); i++) {
            String pingying = StringHelper.getPingYin(array.get(i).getSmallCommunityName());
            Person person = new Person(array.get(i).getSmallCommunityName());
            person.setId(array.get(i).getSmallCommunityCode());
            person.setPinYinName(pingying);
//            person.setFlag(object.optString("objec"));

            char firstChar = pingying.charAt(0);
            List<Person> tempList = null;
            if (propertyMap.containsKey(firstChar)) {
                List<Person> persons = propertyMap.get(firstChar);
                persons.add(person);
                propertyMap.put(firstChar, persons);
            } else {
                tempList = new ArrayList<Person>();
                tempList.add(person);
                propertyMap.put(firstChar, tempList);
            }
        }

        newPersons = new ArrayList<Person>();
        Set keys1 = propertyMap.keySet();
        Object[] keys = keys1.toArray();
        Arrays.sort(keys);  //进行排序
        if (selector == null) selector = new HashMap<String, Integer>();
        for (int i = 0; i < keys.length; i++) {
            Character key = (Character) keys[i];
            Person person = new Person(String.valueOf(Character.toUpperCase(key)));
            selector.put(String.valueOf(Character.toUpperCase(key)), newPersons.size());
            newPersons.add(person);
            newPersons.addAll(propertyMap.get(key));
        }
        if (newPersons.size() == 0) {
            tv_empty.setText("暂无小区");
        }
        initViewAndEvent();
    }


    public class PropertyListViewAdapter extends BaseAdapter {
        private Context context;
        private List<Person> list;
        private ViewHolder viewHolder;

        public PropertyListViewAdapter(Context context, List<Person> list) {
            this.context = context;
            this.list = list;
            checks = new boolean[list.size()];
        }


        public List<Person> getList() {
            return list;
        }


        public void setList(List<Person> list) {
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean isEnabled(int position) {
            // TODO Auto-generated method stub
            if (list.get(position).getName().length() == 1)//
                return false;
            return super.isEnabled(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            String item = list.get(position).getName();
            viewHolder = new ViewHolder();
            if (item.length() == 1) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.index, null);
                viewHolder.indexTv = (TextView) convertView
                        .findViewById(R.id.indexTv);
            } else {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item, null);
                viewHolder.itemTv = (TextView) convertView
                        .findViewById(R.id.itemTv);
                viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                viewHolder.itemTv2 = (TextView) convertView.findViewById(R.id.itemTv2);
                viewHolder.itemTv2.setVisibility(View.VISIBLE);
                viewHolder.gridView = (GridView) convertView.findViewById(R.id.gv_property_list);
            }
            if (item.length() == 1) {
                viewHolder.indexTv.setText(list.get(position).getName());
            } else {
                Person person = list.get(position);
                viewHolder.itemTv.setText(person.getName());
                viewHolder.itemTv2.setText(person.getAddress());
                final int pos = position; // pos必须声明为final
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView indexTv;
            private TextView itemTv;
            private TextView itemTv2;
            private GridView gridView;
            private RelativeLayout rl_item;
        }
    }

    /**
     * 清空上一个物业遗留下的缓存
     */
    public void clearPreviousPropertyCache() {


        /**
         * 周边缓存
         */

        fd.deleteAll(BillMainBean.class);

        /**
         * 快送缓存
         */


        /**
         * 清空物业账单缓存
         */
        fd.deleteAll(BillMainBean.class);


        /**
         * 清空家政服务缓存
         */

        /**
         * 清空房屋出售缓存
         */


        /**
         * 清空社区活动缓存
         */
    }

    private JSONObject getroomInfo(JSONObject property) {
        JSONObjectUtil jsonObject = new JSONObjectUtil();

        try {
            jsonObject.put(Constants.MOBILE, mSession.getRegisterMobile2());
            jsonObject.put(Constants.SMALLCOMMUNITYCODE, property.optString(Constants.SMALLCOMMUNITYCODE));
            jsonObject.put(Constants.ROOMCODE, property.optString(Constants.ROOMCODE));
            jsonObject.put(Constants.USERTYPE, property.optString(Constants.USERTYPE));
            return jsonObject;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_head_left:
                finish();
        }
    }
}
