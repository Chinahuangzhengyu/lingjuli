package com.zhjl.qihao.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.zhjl.qihao.Constants;
import com.zhjl.qihao.abutil.LogUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017-07-06.
 */

public class ZFUtils {

    boolean mbPaying = false;



    /***
     * 支付宝进行支付
     * @param strOrderInfo 订单
     * @param callback 回调
     * @param myWhat 回调动作
     * @param activity
     * @return
     */
    public boolean pay(final String strOrderInfo, final Handler callback,
                       final int myWhat, final Activity activity) {

        if (mbPaying)
            return false;
        mbPaying = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

//					// 构造PayTask 对象
                    PayTask alipay = new PayTask(activity);
//					// 调用支付接口，获取支付结果
//					String result = alipay.pay(strOrderInfo, true);
                    Map<String, String> result = alipay.payV2(strOrderInfo, true);
                    mbPaying = false;
                    // send the result back to caller.
                    Message msg = callback.obtainMessage(myWhat, result);

                    callback.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();

                    LogUtils.exception(e);
                    Message msg = callback.obtainMessage(myWhat, e.toString());
                    callback.sendMessage(msg);
                }
            }
        }).start();

        return true;
    }


    public static String getZFBOrderInfo(String outTradeNo, String subject, String body,
                                         String price, String notify_url) {
        return getZFBOrderInfo(outTradeNo, subject, body, price, notify_url, "");
    }

    /***
     * 获取封装好的支付宝订单信息
     * @param outTradeNo  订单号
     * @param subject 商品名称
     * @param body  商品内容
     * @param price 商品价格
     * @param notify_url 通知地址
     * @param extra_common_param 定义参数  支付宝原装返回
     * @return
     */
    public static String getZFBOrderInfo(String outTradeNo, String subject, String body,
                                         String price, String notify_url, String extra_common_param) {

//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            StringBuffer orderBuffer = new StringBuffer();
            String special = "\"";
        /**
         * 基本参数
         */
        orderBuffer.append("partner=").append(special).append(Constants.PARTNER).append(special);        // 合作者身份ID
        orderBuffer.append("&service=").append(special).append("mobile.securitypay.pay").append(special);// 接口名称， 固定值
        orderBuffer.append("&_input_charset=").append(special).append("utf-8").append(special);        // 参数编码， 固定值
        orderBuffer.append("&notify_url=").append(special).append(notify_url).append(special);        // 服务器异步通知页面路径
//		orderBuffer.append("&notify_id").append(special).append(new Request("notify_id")).append(special);		//  通知校验 ID。
        orderBuffer.append("&appenv=").append(special).append("system=android^version=3.0.1.2").append(special);        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderBuffer.append("&return_url=").append(special).append("www.baidu.com").append(special);        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        /**
         * 业务参数
         */
        orderBuffer.append("&extra_common_param=").append(special).append(extra_common_param).append(special);    // 自定义参数  支付宝原装返回
        orderBuffer.append("&seller_id=").append(special).append(Constants.PARTNER).append(special);    // 卖家支付宝账号
        orderBuffer.append("&out_trade_no=").append(special).append(outTradeNo).append(special);// 商户网站唯一订单号
        orderBuffer.append("&payment_type=").append(special).append("1").append(special);        // 支付类型， 固定值
        orderBuffer.append("&subject=").append(special).append(subject).append(special);        // 商品名称
        orderBuffer.append("&body=").append(special).append(body).append(special);                // 商品详情
        orderBuffer.append("&total_fee=").append(special).append(price).append(special);        // 总金额

        /**
         * 设置未付款交易的超时时间
         * 默认30分钟，一旦超时，该笔交易就会自动被关闭。
         * 取值范围：1m～15d。
         * m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
         * 该参数数值不接受小数点，如1.5h，可转换为90m。
         */
        orderBuffer.append("&it_b_pay=\"").append("30m").append("\"");



//        try {
//            LogUtils.e("getOrderInfo---------");
//            Map<String, String> biz_content = new HashMap<String, String>();
//            biz_content.put("timeout_express", "30m");
//            biz_content.put("product_code", "QUICK_MSECURITY_PAY");
//            biz_content.put("seller_id", Constants.PARTNER);
//            biz_content.put("out_trade_no", outTradeNo);
//            biz_content.put("subject", subject);
//            biz_content.put("body", body);
//            biz_content.put("total_amount", filterAlphabet(price));
//            if (!TextUtils.isEmpty(extra_common_param)) {
//                biz_content.put("extra_common_param", extra_common_param);
//            }
//            Map<String, String> keyValues = new HashMap<String, String>();
//            keyValues.put("app_id", Constants.APP_ID_ZFB);
//
//            String biz_content_str = GsonUtil.toJsonString(biz_content);
//            keyValues.put("biz_content", biz_content_str);
//
//            keyValues.put("charset", "utf-8");
//
//            keyValues.put("method", "alipay.trade.app.pay");
//
//            keyValues.put("sign_type", Constants.SIGN_TYPE);
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            keyValues.put("timestamp", format.format(new Date()));
//            String[] strings = notify_url.split("\\?");
//            String string = strings[1];
//            String encode = URLEncoder.encode("?"+string, "utf-8");
//            keyValues.put("notify_url", strings[0]+encode);
////            keyValues.put("notify_url", URLEncoder.encode(notify_url, "UTF-8"));
//            Log.e("nnotify_url", notify_url);
//            Log.e("nnotify_url", URLEncoder.encode(notify_url, "utf-8"));
//            keyValues.put("version", "1.0");
//
//
//            String orderParam = buildOrderParam(keyValues);
//            String sign = OrderInfoUtil2_0.getSign(keyValues, Constants.RSA_PRIVATE, false);
//            return orderParam + "&" + sign;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;

            String sign = sign(orderBuffer.toString());
            try {
                // 仅需对sign 做URL编码
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String payInfo = orderBuffer.toString() + "&sign=\"" + sign + "\"&" + getSignType();
            return payInfo;

    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, Constants.RSA_PRIVATE);
    }

    public static String filterAlphabet(String alph) { // 過濾器
        alph = alph.replaceAll("[,]", "");
        return alph;

    }

    /**
     * 要求外部订单号必须唯一。
     *
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }


    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return
     */
    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            if (!key.equals("notify_url")){
                sb.append(buildKeyValue(key, value, true));
            }else {
                sb.append(buildKeyValue(key, value, false));
            }
            sb.append("&");

        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }


    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (Exception e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }


}
