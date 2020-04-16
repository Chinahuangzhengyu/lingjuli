package com.zhjl.qihao.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abutil.LogUtils;
import com.zhjl.qihao.wxapi.MD5;

import org.apache.http.NameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Utils {
    public static PopupWindow loginPopWindow = new PopupWindow();
    public static PopupWindow callPopWindow = new PopupWindow();

    //	public static int dpToPx(float dp, Resources resources){
//		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
//		return (int) px;
//	}

    /**
     * 描述：px转换为dip
     *
     * @param context
     * @param pxValue
     * @return
     * @throws
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 描述：dip转换为px
     *
     * @param context
     * @param dipValue
     * @return
     * @throws
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /***
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @param resources
     * @return
     */
    public static int sp2px(float spValue, Resources resources) {
        final float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /***
     * 获取屏幕宽高
     * @param context
     * @return
     */
    public static Point getScreenSize(Context context) {
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(point);
        return point;
    }

    /**
     * scrollview嵌套listview显示不全解决
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 改变背景颜色
     */
    public static void darkenBackground(Float bgcolor, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);

    }

    /**
     * 参数leftdip， rightDip是设置tab左右的padding值，就是间距大小
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        LinearLayout llTab = null;
        try {
            //mTabStrip
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            llTab = (LinearLayout) tabStrip.get(tabs);
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void setTabLayoutTextBold(TabLayout tabLayout, String... str) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(R.layout.tab_item);
            TextView tabItemText = tabLayout.getTabAt(i).getCustomView().findViewById(R.id.tab_item_text);
            if (i == 0) {
                tabItemText.setText(str[0]);
                tabItemText.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tabItemText.setText(str[i]);
                tabItemText.setTypeface(Typeface.DEFAULT);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTypeface(Typeface.DEFAULT);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 隐藏键盘
     */
    public static void hideInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar = false;
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    /**
     * 隐藏软键盘(有输入框)
     *
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText) {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 调用系统方法 强制隐藏软键盘
     *
     * @param activity
     */
    public static void hideSystemSoftKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 打开虚拟键盘
     *
     * @param replyEdt
     * @return
     */
    public static void openKeyboard(EditText replyEdt) {
        InputMethodManager m = (InputMethodManager) replyEdt.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        m.showSoftInput(replyEdt,0);
        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static int[] settingWidthHeight(Activity activity, int imageWidth, int imageHeight) {
        int[] point = new int[2];
        int[] size = ScreenUtil.getRawScreenSize(activity);
        int maxWidth = size[0];
//        int maxHeight = session.getDisplayMetrics().heightPixels;
        int imageScaleWidth = (int) (imageWidth * 0.5);
        int imageScaleHeight = (int) (imageHeight * 0.5);

        if (imageScaleWidth >= maxWidth) {
            imageScaleWidth = (int) (imageScaleWidth * 0.4);
            imageScaleHeight = (int) (imageScaleHeight * 0.4);
        }
        if (imageScaleWidth >= maxWidth) {
            imageScaleWidth = (int) (imageScaleWidth * 0.5);
            imageScaleHeight = (int) (imageScaleHeight * 0.5);
        }

        if (imageScaleWidth > 600) {
            imageScaleHeight = (int) (imageScaleHeight * 0.5);
            imageScaleWidth = (int) (imageScaleWidth * 0.5);
        }
        point[0] = imageScaleWidth;
        point[1] = imageScaleHeight;
        if (imageScaleWidth <= 0 || imageScaleHeight <= 0) {
            point[0] = 0;
            point[1] = 0;
        }
        return point;
    }

    public static int[] pictureWidthHeight(Activity activity, int imageWidth, int imageHeight, int[] margin) {
        int[] point = new int[2];
        int left = margin[0];
        int right = margin[1];
        int[] size = ScreenUtil.getRawScreenSize(activity);
        int maxWidth = size[0];
        int showWidth = maxWidth - left - right;
        if (showWidth <= 0) {
            return point;
        }
        BigDecimal showWidthBigDecimal = new BigDecimal(showWidth).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal imageWidthBigDecimal = new BigDecimal(imageWidth).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal imageHeightBigDecimal = new BigDecimal(imageHeight).setScale(2, BigDecimal.ROUND_HALF_UP);
        int showHeight = showWidthBigDecimal.divide(imageWidthBigDecimal,2,BigDecimal.ROUND_HALF_UP).multiply(imageHeightBigDecimal).intValue();
        point[0] = showWidth;
        point[1] = showHeight;
        return point;
    }

    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /** */
    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            if (i != bArray.length - 1) {
                sb.append(sTemp.toUpperCase() + " ");
            } else {
                sb.append(sTemp.toUpperCase());
            }

        }
        return sb.toString();
    }

    public static void phpIsLogin(Activity context, int type, String message) {
        if (type == 0) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else if (type == 1) {
            Utils.loginPopWindow(context);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            context.finish();
        }
    }

    public static void loginPopWindow(final Activity activity) {
        View popView = LayoutInflater.from(activity).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView not = (TextView) popView.findViewById(R.id.no);
        loginPopWindow.setWidth(Utils.dip2px(activity, 256));
        loginPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        loginPopWindow.setContentView(popView);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.get(activity).clear();
                JPushInterface.deleteAlias(activity, 111);
                ZHJLApplication.getInstance().finishAll();
                Intent intent = new Intent(activity, UserLoginActivity.class);
                activity.startActivity(intent);
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPopWindow.dismiss();
            }
        });
        //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
        loginPopWindow.setFocusable(true);
        loginPopWindow.setOutsideTouchable(false);
//        messagePopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        Utils.darkenBackground(0.8f, activity);
        if (!loginPopWindow.isShowing()) {
            loginPopWindow.showAtLocation(activity.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        loginPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, activity);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    public static String getPackage(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            if (i != params.size() - 1) {
                sb.append('&');
            }

        }
        return sb.toString();
    }

    // 对商户微信秘钥 本地签名
    public static String getWXSign(List<NameValuePair> params) {

//		LogUtils.e("params "+value);
//		LogUtils.e("sign is "+packageSign);
        return "";
    }

    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
                .getBytes());
    }


    public static long getTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    public static String toXml(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");


            sb.append("<![CDATA[");
            sb.append(params.get(i).getValue());
            sb.append("]]>");
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        LogUtils.d("xml is " + sb.toString());
        return new String(sb.toString().getBytes(), "ISO8859-1");
    }

    /**
     * 获取到开门指令
     * 这里是 通过 sip 账号 + @ + 小区号 + @ +时间戳
     *
     * @param context
     * @return
     */
    public static String getOpenCmd(Context context) {
        Session mSession = Session.get(context);
        String sipNumber = mSession.getSipNumber();
        if (TextUtils.isEmpty(sipNumber)) {
            return null;
        }
//		if (mSession.getRoomName().startsWith("00")) {
//			sipNumber = mSession.getRoomName().substring(2);
//		} else {
//			sipNumber = mSession.getRoomName();
//		}
        String code2 = sipNumber + "@" + mSession.getSmallCommunityCode() + "@" + (System.currentTimeMillis() / 60);

        String cmd = "";
        try {

            cmd = AEC.Encrypt(code2, "italklingjuli206");
            return cmd;

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("utils ", " 蓝牙开门指令 " + cmd);
        return null;
    }

    //根据Wifi信息获取本地Mac
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    //选择图片
    public static void rotateAnim(boolean isClick, ImageView img) {
        Animation anim = null;
        if (isClick) {
            anim = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setFillAfter(true);
        anim.setDuration(100);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.setFillAfter(true);
        img.startAnimation(anim);
    }

    /**
     * 判断手机号是否符合规范
     *
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }

    public static void callPerson(final Activity activity, String contentStr, final String phone) {
        View popView = LayoutInflater.from(activity).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView not = (TextView) popView.findViewById(R.id.no);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        tvMessageTitle.setText(contentStr);
        callPopWindow.setWidth(Utils.dip2px(activity, 256));
        callPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        callPopWindow.setContentView(popView);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                activity.startActivity(intent);
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPopWindow.dismiss();
            }
        });
        //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
        callPopWindow.setFocusable(true);
        callPopWindow.setOutsideTouchable(false);
//        messagePopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        Utils.darkenBackground(0.8f, activity);
        if (!callPopWindow.isShowing()) {
            callPopWindow.showAtLocation(activity.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        callPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, activity);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }


    public static class GetAccessTokenResult {

        private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetAccessTokenResult";

        public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
        public String accessToken;
        public int expiresIn;
        public int errCode;
        public String errMsg;

        public void parseFrom(String content) {

            if (content == null || content.length() <= 0) {
                LogUtils.e(TAG, "parseFrom fail, content is null");
                localRetCode = LocalRetCode.ERR_JSON;
                return;
            }

            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xmlPullParser = factory.newPullParser();
                xmlPullParser.setInput(new ByteArrayInputStream(content.getBytes()), "UTF-8");
                int eventType = xmlPullParser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xmlPullParser.getName().equals("return_code")) {

                        } else if (xmlPullParser.getName().equals("return_msg")) {

                        } else if (xmlPullParser.getName().equals("appid")) {

                        } else if (xmlPullParser.getName().equals("mch_id")) {

                        } else if (xmlPullParser.getName().equals("nonce_str")) {

                        } else if (xmlPullParser.getName().equals("sign")) {

                        } else if (xmlPullParser.getName().equals("result_code")) {

                            xmlPullParser.nextToken();
                            localRetCode = LocalRetCode.ERR_OK;
                        } else if (xmlPullParser.getName().equals("prepay_id")) {
                            xmlPullParser.nextToken();
                            LogUtils.e(xmlPullParser.getName() + " is " + xmlPullParser.getText());
                            accessToken = xmlPullParser.getText();
                        } else if (xmlPullParser.getName().equals("trade_type")) {

                        }
                    }

                    eventType = xmlPullParser.next();
                }


            } catch (Exception e) {
                localRetCode = LocalRetCode.ERR_JSON;
            }
        }
    }


    public static enum LocalRetCode {
        ERR_OK, ERR_HTTP, ERR_JSON, ERR_OTHER
    }

    public static String onTimeTranslate(String startHour, String startMin, String validTime) {
        String mTime = null;
        int min = 0, hour = 0;
        String sHour = null, sMin = null;
        String[] time = validTime.split(" ");
        // String[] time2 = time[0].split(".");
        String[] time2 = {time[0].substring(0, time[0].length() - 2), time[0].substring(time[0].length() - 1)};
        if (time2[1].equals("5")) {
            min = Integer.valueOf(startMin) + 30;
            if (min >= 60) {
                min = min % 60;
                hour = 1;
            }
        } else {
            min = Integer.valueOf(startMin);
        }
        if (hour == 1) {
            hour = Integer.valueOf(startHour) + Integer.valueOf(time2[0]) + 1;
        } else {
            hour = Integer.valueOf(startHour) + Integer.valueOf(time2[0]);
        }
        if (min < 10) {
            sMin = "0" + min;
        } else {
            sMin = min + "";
        }
        if (hour < 10) {
            sHour = "0" + hour;
        } else {
            sHour = hour + "";
        }
        if (hour >= 24) {
            return mTime = "2359";
        }
        return mTime = sHour + sMin;
    }

    /**
     * 判断是否存在NavigationBar
     *
     * @param context：上下文环境
     * @return：返回是否存在(true/false)
     */
    public static boolean checkDeviceHasNavigationBar(Activity context, RelativeLayout relativeLayout) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                //不存在虚拟按键
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                //存在虚拟按键
                hasNavigationBar = true;
                //手动设置控件的margin
                //linebutton是一个linearlayout,里面包含了两个Button
                RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                //setMargins：顺序是左、上、右、下
                layout.setMargins(0, 0, 0, getNavigationBarHeight(context) + 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    /**
     * 测量底部导航栏的高度
     *
     * @param mActivity:上下文环境
     * @return：返回测量出的底部导航栏高度
     */
    private static int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static String getDeviceId(Context context) {
        String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        if (androidId == null || androidId.equals("")) {
            BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
            m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            String m_szBTMAC = m_BluetoothAdapter.getAddress();
            return m_szBTMAC;
        }
        return androidId;
    }
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//
//        String deviceId = null, androidId = null, mac = null, serial = null;
//
//
//        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//
//
//        try {
//
//            if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
//
//                    .checkPermission(Manifest.permission.READ_PHONE_STATE,
//
//                            context.getPackageName())) {
//
//
//                deviceId = tm.getDeviceId();
//
//
//            }
//
//            androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
//
//                    android.provider.Settings.Secure.ANDROID_ID);
//
//
//            mac = wifiMan.getConnectionInfo().getMacAddress();
//
//            Class<?> c = Class.forName("android.os.SystemProperties");
//            Method get = c.getMethod("get", String.class, String.class);
//            serial = (String) get.invoke(c, "ro.serialno", "unknown");
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }
//
//        if (TextUtils.isEmpty(deviceId) && TextUtils.isEmpty(androidId)
//
//                && TextUtils.isEmpty(mac) && TextUtils.isEmpty(serial)) {
//
//            return UUID.randomUUID().toString();
//
//        }
//
//
//        UUID deviceUuid = new UUID(androidId.hashCode(), deviceId.hashCode() | mac.hashCode() | serial.hashCode());
//
//        String uniqueId = deviceUuid.toString();
//
//
//        return uniqueId;
//    }
}
