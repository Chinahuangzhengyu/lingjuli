package com.zhjl.qihao.integration.utils;

import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class RequestUtils {

    /**
     * php获取图形验证码
     */
    public static Call<ResponseBody> getPhotoSms(String token, String type, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", token);
        map.put("type", type);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.getPhotoSms(body);
        return call;
    }

    /**
     * 获取验证码
     *
     * @param userToken
     * @param number
     * @param photoSms
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> getSms(String userToken, String number, String photoSms, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("mobile", number);
        map.put("authcode", photoSms);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.sendSms(body);
        return call;
    }

    /**
     * 会员卡绑定
     *
     * @param userToken
     * @param cardId
     * @param password
     * @param payPassword
     * @param photoSms
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> membershipCardBuilding(String userToken, String cardId, String password, String phoneNumber, String payPassword, String photoSms, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        map.put("password", password);
        map.put("mobile", phoneNumber);
        map.put("pay_password", payPassword);
        map.put("authcode", photoSms);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.membershipCardBuilding(body);
        return call;
    }

    /**
     * 得到会员卡详情
     *
     * @param userToken
     * @param cardId
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> getCardDetail(String userToken, String cardId, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.membershipCardDetail(body);
        return call;
    }

    /**
     * 会员卡解绑
     *
     * @param userToken
     * @param cardId
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> unbindCard(String userToken, String cardId, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.membershipCardRemove(body);
        return call;
    }

    /**
     * 会员卡列表
     *
     * @param userToken
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> membershipCardList(String userToken, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.membershipCardList(body);
        return call;
    }

    /**
     * 忘记密码短信
     *
     * @param userToken
     * @param mobile
     * @param authcode
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> forgetPasswordSms(String userToken, String mobile, String authcode, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("mobile", mobile);
        map.put("authcode", authcode);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.forgetPasswordSms(body);
        return call;
    }

    /**
     * 验证忘记密码短信
     *
     * @param userToken
     * @param mobile
     * @param authcode
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> checkForgetPasswordSms(String userToken, String mobile, String authcode, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("mobile", mobile);
        map.put("authcode", authcode);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.checkForgetPasswordSms(body);
        return call;
    }

    /**
     * 重置支付密码
     *
     * @param userToken
     * @param cardId
     * @param payPassword
     * @param rePayPassword
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> resetPayPassword(String userToken, String cardId, String payPassword, String rePayPassword, String code, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        map.put("pay_password", payPassword);
        map.put("re_pay_password", rePayPassword);
        map.put("code", code);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.resetPayPassword(body);
        return call;
    }

    /**
     * 设置默认卡
     *
     * @param userToken
     * @param cardId
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> settingDefaultCard(String userToken, String cardId, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.settingDefaultCard(body);
        return call;
    }

    /**
     * 修改支付密码
     *
     * @param userToken
     * @param cardId
     * @param payPassword
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> updatePayPassword(String userToken, String cardId, String payPassword, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("card_id", cardId);
        map.put("pay_password", payPassword);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.updatePayPassword(body);
        return call;
    }

    /**
     * 会员卡默认卡详情
     *
     * @param userToken
     * @param pageIndex
     * @param integralInterface
     * @return
     */
    public static Call<ResponseBody> defaultCardDetail(String userToken, int pageIndex, IntegralInterface integralInterface) {
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", userToken);
        map.put("page", pageIndex);
        map.put("limit", 10);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = integralInterface.defaultCardDetail(body);
        return call;
    }

}