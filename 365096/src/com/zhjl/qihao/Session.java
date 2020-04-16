package com.zhjl.qihao;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.zhjl.qihao.ablogin.bean.LoginBean;
import com.zhjl.qihao.chooseCity.bean.LoginSwitchBean;
import com.zhjl.qihao.util.JSONObjectUtil;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.util.Tools;
import com.zhjl.qihao.util.Utils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Observable;

public class Session extends Observable {

    /**
     * “1”当天时间段有效
     * “2”必须选定当天指定时间段
     * “3”可选当天或者指定时间段
     */
    private String limitedTime;

    /**
     * 最大的邀请人员数，APP获取此值后，由APP控制最大数目并提示
     */
    private String maxInvite;
    /**
     * 正式与测试的判断参数
     */
    private int serveNumb;
    /**
     * 拥有的房间数量
     */
    private int roomNumb;

    /**
     * 第一次开门引导
     */
    private String firstOpenDoor;

    /**
     * Log tag
     */
    private final static String TAG = "Session";

    /**
     * Application Context
     */
    private Context mContext;

    private int width;
    private int height;

    /**
     * The Application Version Code
     */
    private int versionCode;
    /**
     * 记录版本，可以判断软件是否更新
     */
    private String recordVersionCode;
    /**
     * The Application package name
     */
    private String packageName;

    /**
     * 当前版本The Application version name
     */
    private String versionName;

    /**
     * The Application version name
     */
    private String appName;

    /**
     * The mobile IMEI code
     */
    private String imei;

    /**
     * The mobile sim code
     */
    private String sim;

    /**
     * The mobile mac address
     */
    private String macAddress;

    /**
     * The mobile model such as "Nexus One" Attention: some model type may have
     * illegal characters
     */
    private String model;

    /**
     * The user-visible version string. E.g., "1.0"
     */
    private String buildVersion;

    private int osVersion;

    /**
     * User login name
     */
    private String userName;

    /**
     * User login password
     */
    private String password;

    private String pictureId;
    private String history;
    private String softarticleIsShow;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 性别
     */
    private String sex;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 职业
     */
    private String profession;
    /**
     * 情感
     */
    private String Affective;
    /**
     * 年龄
     */
    private String age;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String headPicPath;
    /**
     * The singleton instance
     */
    private static Session mInstance;//实例

    /**
     * 用户ID
     */
    private String userId;
    /**
     * 小区号
     */
    private String smallCommunityCode;
    /**
     * 登陆令牌
     */
    private String toneKey;
    /**
     * 房间号
     */
    private String roomCode;

    /**
     * 房间名称
     */
    private String roomName;
    /**
     * 用户帐号
     */
    private String userAccount;
    private String sign;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 小区名称
     */
    private String smallCommunityName;
    /**
     * 小区电话号码
     */
    private String smallCommunityPhone;

    /**
     * 手机号
     */
    private String registerMobile;
    /**
     * 业主手机号码
     */
    private String registerMobile2;

    /**
     * 用户昵称
     */
    private String userNick;

    private String note;

    /**
     * 定位省份
     */
    private String province;
    /**
     * 定位城市
     */
    private String city;
    /**
     * 定位区域
     */
    private String district;

    private DisplayMetrics displayMetrics = new DisplayMetrics();

    /**
     * 是否第一次使用
     */
    private String enterTimer;

    private String shopUserId;

    //头像地址
    private String smallHeadPicPath;
    public Tools tools;

    //消息数量
    private int messageCount;
    private int platformMessageCount;
    private int propertyMessageCount;


    private String mToken;
    private String roomId;
    private boolean isLogin;
    private boolean isCarPay;
    private boolean isTest;
    //屏幕亮度
    private int screenBrightness;

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
        tools.putValue(Constants.IS_TEST, test);
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        if (!TextUtils.isEmpty(mToken)) {
            this.mToken = mToken;
            tools.putValue(Constants.M_TOKEN, mToken);
        }
    }

    public void setmToken(String mToken,boolean isCheck) {
        if (!TextUtils.isEmpty(mToken)||!isCheck) {
            this.mToken = mToken;
            tools.putValue(Constants.M_TOKEN, mToken);
        }
    }

    /**
     * 手环是否连接
     */
    private boolean wristbandIsConnect = false;
    /***/
    private String wristbandAddress;

    public static Session get(Context context) {
        if (mInstance == null) {
            mInstance = new Session(context);
        }
        return mInstance;
    }

    private Session(Context context) {
        synchronized (this) {
            mContext = ZHJLApplication.getContext();
            tools = Tools.getInstance(context);
            osVersion = Build.VERSION.SDK_INT;//SDK版本号
            buildVersion = Build.VERSION.RELEASE;//
            try {
                model = URLEncoder.encode(Build.MODEL, "UTF-8");//版本
            } catch (UnsupportedEncodingException e) {

            }
            readSettings();
        }
    }


    public void readSettings() {
        LogUtils.e("readSettings start");
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                limitedTime = tools.getValue(Constants.LIMITED_TIME);
                maxInvite = tools.getValue(Constants.MAX_INVITE);

                smallCommunityCode = tools.getValue(Constants.SMALLCOMMUNITYCODE);
                userId = tools.getValue(Constants.USERID);
                toneKey = tools.getValue(Constants.KEY_TONE);
                roomCode = tools.getValue(Constants.NEW_ROOM_CODE);
                roomName = tools.getValue(Constants.ROOMNAME);
                userAccount = tools.getValue(Constants.USER_ACCOUNT);
                userType = tools.getValue(Constants.USERTYPE);
                smallCommunityName = tools.getValue(Constants.SMALLCOMMUNITYNAME);
                messageCount = tools.getValue_int(Constants.MESSAGE_COUNT);
                platformMessageCount = tools.getValue_int(Constants.MESSAGE_ONE);
                propertyMessageCount = tools.getValue_int(Constants.MESSAGE_TWO);
                mToken = tools.getValue(Constants.M_TOKEN);
                roomId = tools.getValue(Constants.ROOM_ID);
                isLogin = tools.getValue_boolean(Constants.LOGIN_STATUS, false);
                isCarPay = tools.getValue_boolean(Constants.PAY_STATUS, false);
                isTest = tools.getValue_boolean(Constants.IS_TEST, false);
                screenBrightness = tools.getValue_int(Constants.SCREEN_BRIGHTNESS);
                if (TextUtils.isEmpty(smallCommunityName)) {
                    smallCommunityName = mContext.getResources().getString(R.string.cloud_city);
                }
                nick = tools.getValue(Constants.NICK_NAME);
                sex = tools.getValue(Constants.SEX);

                hobby = tools.getValue(Constants.HOBBY);

                profession = tools.getValue(Constants.PROFESSION);

                Affective = tools.getValue(Constants.AFFECTIVE);

                age = tools.getValue(Constants.AGE);

                address = tools.getValue(Constants.ADDRESS);

                headPicPath = tools.getValue(Constants.HEADPICPATH);

                smallCommunityPhone = tools.getValue(Constants.SMALLCOMMUNITYPHONE);
                registerMobile2 = tools.getValue(Constants.REGISTERMOBILE2);
                userNick = tools.getValue(Constants.NICK_NAME);
                smallHeadPicPath = tools.getValue(Constants.SMALLHEADPICPATH);
                registerMobile = tools.getValue(Constants.REGISTERMOBILE);

                note = tools.getValue(Constants.NOTE);
                sign = tools.getValue(Constants.SIGN);
                province = tools.getValue(Constants.PROVINCENAME);
                city = tools.getValue(Constants.CITY_NAME);
                district = tools.getValue(Constants.DISTRICT_NAME);
                smallHeadPicPath = tools.getValue(Constants.SMALLHEADPICPATH);

                enterTimer = tools.getValue(Constants.ENTER_TIMES);
                recordVersionCode = tools.getValue(Constants.VERSIONCEODE);

                firstOpenDoor = tools.getValue(Constants.FIRST_OPENDOOR);

                roomNumb = tools.getValue_int(Constants.ROOM_NUM);

                serveNumb = tools.getValue_int(Constants.SERVE_NUM);

                shopUserId = tools.getValue(Constants.SHOP_USER_ID);

                wristbandIsConnect = tools.getValue_boolean(Constants.HEALTH_TT_WRISTBAND_CONNECT, false);
                wristbandAddress = tools.getValue(Constants.HEALTH_LY_WRISTBAND_ADDRESS, "");
                getApplicationInfo();
            }
        }).start();
        LogUtils.e("readSettings end");
    }

    private void getApplicationInfo() {//获取应用信息
        final PackageManager pm = (PackageManager) mContext.getPackageManager();
        try {
            final PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            versionName = pi.versionName;//
            versionCode = pi.versionCode;

            final ApplicationInfo ai = pm.getApplicationInfo(mContext.getPackageName(),
                    PackageManager.GET_META_DATA);
            appName = String.valueOf(ai.loadLabel(pm));
            packageName = mContext.getPackageName();

            TelephonyManager telMgr = (TelephonyManager) mContext
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imei = telMgr.getDeviceId();//手机标识符(和mac略有区别)
            sim = telMgr.getSimSerialNumber();//sim卡号
        } catch (NameNotFoundException e) {
            Log.d(TAG, "met some error when get application info");
        } catch (SecurityException se) {
            Log.d(TAG, "SecurityException error");
        }
    }

    /***
     * 保存基本数据
     * @param json
     */
    public void setBasic(JSONObject json) {
        String smallCommunityCode = JSONObjectUtil.getJSONValueTrim(
                Constants.NEWSMALLCOMMUNITYCODE, json);

        String roomCode = JSONObjectUtil.getJSONValueTrim(Constants.NEW_ROOM_CODE, json);
        String smallCommunityName = JSONObjectUtil.getJSONValueTrim(
                Constants.SMALLCOMMUNITYNAME, json);
        String mobile = JSONObjectUtil.getJSONValueTrim(Constants.MOBILE, json);
//        String userType = JSONObjectUtil.getJSONValueTrim(Constants.USERTYPE, json);
        String userId = JSONObjectUtil.getJSONValueTrim(Constants.USERID, json);
        String token = JSONObjectUtil.getJSONValueTrim(Constants.TOKEN, json);
        String roomName = JSONObjectUtil.getJSONValueTrim(Constants.ROOMNAME, json);

        setSmallCommunityCode(smallCommunityCode, true);
        // 小区号
        setRoomCode(roomCode);
        // 小区名
        setSmallCommunityName(smallCommunityName, true);
        // 手机号码
        setRegisterMobile(mobile, true);
        setRegisterMobile2(mobile, true);
        // 用户类型 0 游客类型 1业主类型 2租客类型 3家人类型
        setUserType(userType, true);
        // 用户ID
        setUserId(userId, true);
        // 手机登陆令牌
        setToneKey(token, true);

        setRoomName(roomName, true);
        // ---------------------------------------------------------------------------------------------------------------------//
        // ---------------------------------------------------个人信息-------------------------------------------------------------//
        // ---------------------------------------------------------------------------------------------------------------------//
        String userAccount = JSONObjectUtil.getJSONValueTrim(
                Constants.ACCOUNT, json);
        setUserAccount(userAccount, true);

        String nickName = JSONObjectUtil.getJSONValueTrim(
                Constants.NICK_NAME, json);
        setNick(nickName, true);

        String sex = JSONObjectUtil.getJSONValueTrim(Constants.SEX, json);
        setSex(sex, true);

        String note = JSONObjectUtil.getJSONValueTrim(Constants.NOTE, json);
        setNote(note, true);

        String sign = JSONObjectUtil.getJSONValueTrim(Constants.SIGN, json);
        setSign(sign, true);

        String hobby = JSONObjectUtil.getJSONValueTrim(Constants.HOBBY, json);
        setHobby(hobby, true);

        String profession = JSONObjectUtil.getJSONValueTrim(Constants.PROFESSION, json);
        setProfession(profession, true);

        String affective = JSONObjectUtil.getJSONValueTrim(Constants.AFFECTIVE, json);
        setAffective(affective, true);

        String age = JSONObjectUtil.getJSONValueTrim(Constants.AGE, json);
        setAge(age, true);

        String address = JSONObjectUtil.getJSONValueTrim(Constants.ADDRESS, json);
        setAddress(address, true);

        String smallHeadPicPath = JSONObjectUtil.getJSONValueTrim(
                Constants.SMALLHEADPICPATH, json);
        setSmallHeadPicPath(smallHeadPicPath, true);

        String headPicPath = JSONObjectUtil.getJSONValueTrim(
                Constants.HEADPICPATH, json);
        setHeadPicPath(headPicPath, true);
    }

    /***
     * 保存改变数据
     * @param json
     */
    public <T> void setChangeBasic(LoginSwitchBean json) {
        String smallCommunityCode = json.getData().getSmallCommunityCode();
        String roomCode = json.getData().getRoomCode();
        String smallCommunityName = json.getData().getSmallCommunityName();
        String userType = String.valueOf(json.getData().getUserType());
        String roomName = json.getData().getRoomName();

        setSmallCommunityCode(smallCommunityCode, true);
        // 小区号
        setRoomCode(roomCode);
        // 小区名
        setSmallCommunityName(smallCommunityName, true);
        // 用户类型 0 游客类型 1业主类型 2租客类型 3家人类型
        setUserType(userType, true);
        // 用户ID
        setUserId(userId, true);

        setRoomName(roomName, true);
    }

    /***
     * 保存基本数据
     * @param json
     */
    public <T> void setNewBasic(LoginBean json) {
        String smallCommunityCode = json.getData().getUserInfo().getSmallCommunityCode();
        String roomCode = json.getData().getUserInfo().getRoomCode();
        String roomId = "";
        if (json.getData().getUserInfo().getUserRooms().size() > 0) {
            roomId = json.getData().getUserInfo().getUserRooms().get(0).getRoomId();
        }else {
            roomId = "";
        }
        String smallCommunityName = json.getData().getUserInfo().getSmallCommunityName();
        String mobile = json.getData().getUserInfo().getMobileNumber();
        String userType = String.valueOf(json.getData().getUserInfo().getUserType());
        String userId = json.getData().getUserInfo().getId();
        String token = json.getData().getToken();
        String roomName = json.getData().getUserInfo().getRoomName();

        setSmallCommunityCode(smallCommunityCode, true);
        // 小区号
        setRoomCode(roomCode);
        //设置是否登录
        setIsLogin(true);
        setRoomId(roomId);
        // 小区名
        setSmallCommunityName(smallCommunityName, true);
        // 手机号码
        setRegisterMobile(mobile, true);
        setRegisterMobile2(mobile, true);
        // 用户类型 0 游客类型 1业主类型 2租客类型 3家人类型
        setUserType(userType, true);
        // 用户ID
        setUserId(userId, true);
        // 手机登陆令牌
        setmToken(token);

        setRoomName(roomName, true);
        // ---------------------------------------------------------------------------------------------------------------------//
        // ---------------------------------------------------个人信息-------------------------------------------------------------//
        // ---------------------------------------------------------------------------------------------------------------------//
        String nickName = json.getData().getUserInfo().getNickname();
        setNick(nickName, true);

//        String sex = String.valueOf(json.getData().getUserInfo().get());
//        setSex(sex, true);

//        String note = json.getData().getUserInfo().getNote();
//        setNote(note, true);

        String sign = json.getData().getUserInfo().getSign();
        setSign(sign, true);

//        String hobby = json.getData().getUserInfo().get();
//        setHobby(hobby, true);

//        String profession = json.getData().getUserInfo().getProfession();
//        setProfession(profession, true);
//
//        String affective = String.valueOf(json.getData().getUserInfo().getAffective());
//        setAffective(affective, true);
//
//        String age = String.valueOf(json.getData().getUserInfo().getAge());
//        setAge(age, true);

        if (json.getData().getUserInfo().getAvatar() != null) {
            String smallHeadPicPath = json.getData().getUserInfo().getAvatar().getSmallPicturePath();
            setSmallHeadPicPath(smallHeadPicPath, true);

            String headPicPath = json.getData().getUserInfo().getAvatar().getPicturePath();
            setHeadPicPath(headPicPath, true);
        }
    }

    public void setIsLogin(boolean isLogin) {

        this.isLogin = isLogin;
        tools.putValue(Constants.LOGIN_STATUS, isLogin);

    }

    public boolean isLogin() {
        return isLogin;
    }

    public boolean isCarPay() {
        return isCarPay;
    }

    public void setCarPay(boolean carPay) {
        isCarPay = carPay;
        tools.putValue(Constants.PAY_STATUS, carPay);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        if (!TextUtils.isEmpty(roomId)) {
            this.roomId = roomId;
            tools.putValue(Constants.ROOM_ID, roomId);
        }
    }
    public void setRoomId(String roomId,boolean isCheck) {
        if (!TextUtils.isEmpty(roomId)||!isCheck) {
            this.roomId = roomId;
            tools.putValue(Constants.ROOM_ID, roomId);
        }
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public int getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(int osVersion) {
        this.osVersion = osVersion;
    }

    public String getUserName() {
        return userName;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
        tools.putValue(Constants.MESSAGE_COUNT, messageCount);
    }

    public int getPlatformMessageCount() {
        return platformMessageCount;
    }

    public void setPlatformMessageCount(int platformMessageCount) {
        this.platformMessageCount = platformMessageCount;
        tools.putValue(Constants.MESSAGE_ONE, platformMessageCount);
    }

    public int getPropertyMessageCount() {
        return propertyMessageCount;
    }

    public void setPropertyMessageCount(int propertyMessageCount) {
        this.propertyMessageCount = propertyMessageCount;
        tools.putValue(Constants.MESSAGE_TWO, propertyMessageCount);
    }

    public void setUserName(String userName) {

        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        if (!TextUtils.isEmpty(userId)) {
            this.userId = userId;
            tools.putValue(Constants.USERID, userId);
        }

    }

    public void setUserId(String userId, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(userId)) {
            this.userId = userId;
            tools.putValue(Constants.USERID, userId);
        }
    }

    public String getSmallCommunityCode() {
        return smallCommunityCode;
    }

    public void setSmallCommunityCode(String smallCommunityCode) {
        if (!TextUtils.isEmpty(smallCommunityCode)) {
            tools.putValue(Constants.SMALLCOMMUNITYCODE, smallCommunityCode);
            this.smallCommunityCode = smallCommunityCode;
        }
    }

    public void setSmallCommunityCode(String smallCommunityCode, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(smallCommunityCode)) {
            tools.putValue(Constants.SMALLCOMMUNITYCODE, smallCommunityCode);
            this.smallCommunityCode = smallCommunityCode;
        }
    }

    public String getToneKey() {
        return toneKey;
    }

    public void setToneKey(String toneKey) {
        if (!TextUtils.isEmpty(toneKey)) {
            tools.putValue(Constants.KEY_TONE, toneKey);
            this.toneKey = toneKey;
        }

    }

    public void setToneKey(String toneKey, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(toneKey)) {
            tools.putValue(Constants.KEY_TONE, toneKey);
            this.toneKey = toneKey;
        }
    }


    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        if (!TextUtils.isEmpty(roomCode)) {
            tools.putValue(Constants.NEW_ROOM_CODE, roomCode);
            this.roomCode = roomCode;
        }

    }

    public String getSipNumber() {
        if (TextUtils.isEmpty(roomCode) || roomCode.length() < 9) {
            return null;
        }
        String sipNumber = roomCode.substring(roomCode.length() - 9, roomCode.length() - 3) + roomCode.substring(roomCode.length() - 2);
        return sipNumber;
    }

    public void setRoomCode(String roomCode, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(roomCode)) {
            tools.putValue(Constants.NEW_ROOM_CODE, roomCode);
            this.roomCode = roomCode;
        }
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        tools.putValue(Constants.STR_USER_TYPE, userType);
        this.userType = userType;

    }

    public void setUserType(String userType, boolean isCheck) {
        tools.putValue(Constants.STR_USER_TYPE, userType);
        this.userType = userType;
    }

    public String getSmallCommunityName() {
        return smallCommunityName;
    }

    public void setSmallCommunityName(String smallCommunityName) {
        if (!TextUtils.isEmpty(smallCommunityName)) {
            this.smallCommunityName = smallCommunityName;
            tools.putValue(Constants.SMALLCOMMUNITYNAME, smallCommunityName);
        }

    }

    public void setSmallCommunityName(String smallCommunityName, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(smallCommunityName)) {
            this.smallCommunityName = smallCommunityName;
            tools.putValue(Constants.SMALLCOMMUNITYNAME, smallCommunityName);
        }
    }

    public String getSmallCommunityPhone() {
        return smallCommunityPhone;
    }

    public String getRegisterMobile2() {
        return registerMobile2;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getSmallHeadPicPath() {
        return smallHeadPicPath;
    }

    public void setSmallHeadPicPath(String smallHeadPicPath) {
        if (!TextUtils.isEmpty(smallHeadPicPath)) {
            tools.putValue(Constants.SMALLHEADPICPATH, smallHeadPicPath);
            this.smallHeadPicPath = smallHeadPicPath;
        }

    }

    public void setSmallHeadPicPath(String smallHeadPicPath, boolean isCheck) {
        if (!TextUtils.isEmpty(smallHeadPicPath) || !isCheck) {
            tools.putValue(Constants.SMALLHEADPICPATH, smallHeadPicPath);
            this.smallHeadPicPath = smallHeadPicPath;
        }

    }

    public String getRegisterMobile() {
        return registerMobile;
    }

    public void setRegisterMobile(String registerMobile) {
        if (!TextUtils.isEmpty(registerMobile)) {
            tools.putValue(Constants.REGISTERMOBILE, registerMobile);
            this.registerMobile = registerMobile;
        }

    }

    public void setRegisterMobile(String registerMobile, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(registerMobile)) {
            tools.putValue(Constants.REGISTERMOBILE, registerMobile);
            this.registerMobile = registerMobile;
        }

    }


    public void setSmallCommunityPhone(String smallCommunityPhone) {
        this.smallCommunityPhone = smallCommunityPhone;
    }

    public void setRegisterMobile2(String registerMobile2) {
        if (!TextUtils.isEmpty(registerMobile2)) {
            tools.putValue(Constants.REGISTERMOBILE2, registerMobile2);
            this.registerMobile2 = registerMobile2;
        }

    }

    public void setRegisterMobile2(String registerMobile2, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(registerMobile2)) {
            tools.putValue(Constants.REGISTERMOBILE2, registerMobile2);
            this.registerMobile2 = registerMobile2;
        }

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        if (!TextUtils.isEmpty(roomName)) {
            tools.putValue(Constants.ROOMNAME, roomName);
            this.roomName = roomName;
        }
    }

    public void setRoomName(String roomName, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(roomName)) {
            tools.putValue(Constants.ROOMNAME, roomName);
            this.roomName = roomName;
        }
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        if (!TextUtils.isEmpty(userAccount)) {
            tools.putValue(Constants.USER_ACCOUNT, userAccount);
            this.userAccount = userAccount;
        }
    }

    public void setUserAccount(String userAccount, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(userAccount)) {
            tools.putValue(Constants.USER_ACCOUNT, userAccount);
            this.userAccount = userAccount;
        }
    }

    public String getNick() {
        return nick;
    }

    public String getNickOrMobile() {
        if (TextUtils.isEmpty(nick)) {
            return registerMobile;
        }
        return nick;
    }

    public void setNick(String nick) {
        if (!TextUtils.isEmpty(nick)) {
            tools.putValue(Constants.NICK_NAME, nick);
            this.nick = nick;
        }

    }

    public void setNick(String nick, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(nick)) {
            tools.putValue(Constants.NICK_NAME, nick);
            this.nick = nick;
        }

    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if (!TextUtils.isEmpty(sex)) {
            tools.putValue(Constants.SEX, sex);
            this.sex = sex;
        }

    }

    public void setSex(String sex, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(sex)) {
            tools.putValue(Constants.SEX, sex);
            this.sex = sex;
        }

    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        if (!TextUtils.isEmpty(note)) {
            tools.putValue(Constants.NOTE, note);
            this.note = note;
        }

    }

    public void setNote(String note, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(note)) {
            tools.putValue(Constants.NOTE, note);
            this.note = note;
        }

    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        if (!TextUtils.isEmpty(sign)) {
            tools.putValue(Constants.SIGN, sign);
            this.sign = sign;
        }

    }

    public void setSign(String sign, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(sign)) {
            tools.putValue(Constants.SIGN, sign);
            this.sign = sign;
        }

    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        if (!TextUtils.isEmpty(hobby)) {
            tools.putValue(Constants.HOBBY, hobby);
            this.hobby = hobby;
        }

    }

    public void setHobby(String hobby, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(hobby)) {
            tools.putValue(Constants.HOBBY, hobby);
            this.hobby = hobby;
        }
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        if (!TextUtils.isEmpty(profession)) {
            tools.putValue(Constants.PROFESSION, profession);
            this.profession = profession;
        }

    }

    public void setProfession(String profession, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(profession)) {
            tools.putValue(Constants.PROFESSION, profession);
            this.profession = profession;
        }

    }

    public String getAffective() {
        return Affective;
    }

    public void setAffective(String affective) {
        if (!TextUtils.isEmpty(affective)) {
            tools.putValue(Constants.AFFECTIVE, affective);
            Affective = affective;
        }
    }

    public void setAffective(String affective, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(affective)) {
            tools.putValue(Constants.AFFECTIVE, affective);
            Affective = affective;
        }
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        if (!TextUtils.isEmpty(age)) {
            tools.putValue(Constants.AGE, age);
            this.age = age;
        }

    }

    public void setAge(String age, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(age)) {
            tools.putValue(Constants.AGE, age);
            this.age = age;
        }

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!TextUtils.isEmpty(address)) {
            tools.putValue(Constants.ADDRESS, address);
            this.address = address;
        }
    }

    public void setAddress(String address, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(address)) {
            tools.putValue(Constants.ADDRESS, address);
            this.address = address;
        }
    }

    public void setHeadPicPath(String headPicPath, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(headPicPath)) {
            tools.putValue(Constants.HEADPICPATH, headPicPath);
            this.headPicPath = headPicPath;
        }
    }

    public String getEnterTimer() {
        return enterTimer;
    }

    public void setEnterTimer(String enterTimer) {
        tools.putValue(Constants.ENTER_TIMES, enterTimer);
        this.enterTimer = enterTimer;
    }

    public String getRecordVersionCode() {
        return recordVersionCode;
    }

    public void setRecordVersionCode(String recordVersionCode) {
        tools.putValue(Constants.VERSIONCEODE, recordVersionCode);
        this.recordVersionCode = recordVersionCode;
    }


    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(pictureId)) {
            this.pictureId = pictureId;
            tools.putValue(Constants.PICTURE_ID, null);
        }

    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history, boolean isCheck) {
        if (mContext != null && (!isCheck || !TextUtils.isEmpty(history))) {
            SharedPreferences sp = mContext.getSharedPreferences(Constants.HISTORY_STR, 0);
            sp.edit().putString(Constants.HISTORY, history).commit();
            this.history = history;
        }
    }

    public String getSoftarticleIsShow() {
        return softarticleIsShow;
    }

    public void setSoftarticleIsShow(String softarticleIsShow, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(softarticleIsShow)) {
            this.softarticleIsShow = softarticleIsShow;
            tools.putValue(Constants.SOFTARTICLE_IS_SHOW, Constants.TURE);
        }

    }


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public String getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(String shopUserId, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(shopUserId)) {
            this.shopUserId = shopUserId;
            tools.putValue(Constants.SHOP_USER_ID, shopUserId);
        }

    }

    public void setWristbandIsConnect(boolean wristbandIsConnect) {
        tools.putValue(Constants.HEALTH_TT_WRISTBAND_CONNECT, wristbandIsConnect);
        this.wristbandIsConnect = wristbandIsConnect;
    }

    public boolean isWristbandIsConnect() {
        return wristbandIsConnect;
    }

    public String getWristbandAddress() {
        return wristbandAddress;
    }

    public void setWristbandAddress(String wristbandAddress) {
        this.wristbandAddress = wristbandAddress;
        if (wristbandAddress == null) {
            tools.removeValue(Constants.HEALTH_LY_WRISTBAND_ADDRESS);
        } else {
            tools.putValue(Constants.HEALTH_LY_WRISTBAND_ADDRESS, wristbandAddress);
        }

    }

    public void clear() {

        LogUtils.d("清空设置");
        //退出不能关闭，不然切换用户，离线状态（主要是因为mExecutorService关闭了），
        // YunUtils.stopHeartbeat(mContext);
        setLimitedTime(null);
        setMaxInvite(null);

        setRoomNumb(0);
        setServeNumb(0);
        setUserType(null, false);
        setSmallCommunityCode(null, false);
        setRegisterMobile(null, false);
        setRegisterMobile2(null, false);
        setUserId(null, false);
        setToneKey(null, false);
        setRoomCode(null, false);
        setNick(null, false);
        setSex(null, false);
        setNote(null, false);
        setSign(null, false);
        setHobby(null, false);
        setProfession(null, false);
        setAffective(null, false);
        setAge(null, false);
        setPictureId(null, false);
        setSmallCommunityName(null, false);
        setRoomName(null, false);
        setSmallHeadPicPath(null, false);
        setHistory("", false);
        setSoftarticleIsShow(Constants.TURE, false);
        setmToken(null,false);
        setUserNick(null);
        setHeadPicPath(null);
        setRoomId(null,false);
        tools.editor.clear().commit();
    }

    public void setScreenSize() {
        // TODO Auto-generated method stub
        Point point = Utils.getScreenSize(mContext);
        width = point.x;
        height = point.y;
    }

    public float getValue_float(String key, float defaultValue) {
        return tools.getValue_float(key, defaultValue);
    }

    public int getValue_int(String key, int defaultValue) {
        return tools.getValue_int(key, defaultValue);
    }

    public String getValue(String key, String defaulValuet) {
        return tools.getValue(key, defaulValuet);
    }


    public void putValue(String key, String value) {
        tools.putValue(key, value);
    }

    public void putValue(String key, int value) {
        tools.putValue(key, value);
    }

    public void putValue(String key, boolean value) {
        tools.putValue(key, value);
    }

    public void putValue(String key, float value) {
        tools.putValue(key, value);
    }


    public String getHeadPicPath() {
        return headPicPath;
    }

    public void setHeadPicPath(String headPicPath) {
        tools.putValue(Constants.HEADPICPATH, headPicPath);
        this.headPicPath = headPicPath;
    }

    public String getFirstOpenDoor() {
        return firstOpenDoor;
    }

    public void setFirstOpenDoor(String firstOpenDoor, boolean isCheck) {
        if (!isCheck || !TextUtils.isEmpty(firstOpenDoor)) {
            tools.putValue(Constants.FIRST_OPENDOOR, firstOpenDoor);
            this.firstOpenDoor = firstOpenDoor;
        }
    }

    public int getRoomNumb() {
        return roomNumb;
    }

    public void setRoomNumb(int roomNumb) {
        tools.putValue(Constants.ROOM_NUM, roomNumb);
        this.roomNumb = roomNumb;
    }

    public int getServeNumb() {
        return serveNumb;
    }

    public void setServeNumb(int serveNumb) {
        tools.putValue(Constants.SERVE_NUM, serveNumb);
        this.serveNumb = serveNumb;
    }


    public String getLimitedTime() {
        return limitedTime;
    }

    public void setLimitedTime(String limitedTime) {
        tools.putValue(Constants.LIMITED_TIME, limitedTime);
        this.limitedTime = limitedTime;
    }

    public String getMaxInvite() {
        return maxInvite;
    }

    public void setMaxInvite(String maxInvite) {
        tools.putValue(Constants.MAX_INVITE, maxInvite);
        this.maxInvite = maxInvite;
    }

    public int getScreenBrightness() {
        return screenBrightness;
    }

    public void setScreenBrightness(int screenBrightness) {
        tools.putValue(Constants.SCREEN_BRIGHTNESS, screenBrightness);
        this.screenBrightness = screenBrightness;
    }
}
