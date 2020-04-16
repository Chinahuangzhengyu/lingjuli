package com.zhjl.qihao.integration.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.PHP_API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.PHT_PORT_NUMBER;

public interface IntegralInterface {
    //获取N币
    String GET_INTEGRAL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/phoneuser/getnbit";

    //获取图形验证码
    String GET_PHOTO_SMS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/common/getauthcode";

    //发送短信
    String SEND_SMS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/common/sendauthsms";

    //会员卡详情
    String MEMBERSHIP_CARD_DETAIL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/getcarddetail";

    //会员卡绑定
    String MEMBERSHIP_CARD_BUILDING = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/bindcard";

    //会员卡解绑
    String MEMBERSHIP_CARD_REMOVE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/unbindcard";

    //会员卡列表
    String MEMBERSHIP_CARD_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/getcardlist";

    //重置密码短信
    String FORGET_PASSWORD_SMS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/common/sendresetpasswordsms";

    //忘记支付密码短信
    String CHECK_FORGET_PASSWORD_SMS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/authresetpasswordsms";

    //重置支付密码
    String RESET_PAY_PASSWORD = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/resetpaypassword";

    //修改支付密码
    String UPDATE_PAY_PASSWORD = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/authpaypassword";

    //设置默认会员卡
    String SETTING_DEFAULT_CARD = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/setdefaultcard";

    //默认会员卡详情
    String DEFAULT_CARD_DETAIL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/usercard/getspenddetail";

    @POST(GET_INTEGRAL)
    Call<ResponseBody> getIntegral(@Body RequestBody body);

    @POST(GET_PHOTO_SMS)
    Call<ResponseBody> getPhotoSms(@Body RequestBody body);

    @POST(SEND_SMS)
    Call<ResponseBody> sendSms(@Body RequestBody body);

    @POST(MEMBERSHIP_CARD_DETAIL)
    Call<ResponseBody> membershipCardDetail(@Body RequestBody body);

    @POST(MEMBERSHIP_CARD_BUILDING)
    Call<ResponseBody> membershipCardBuilding(@Body RequestBody body);

    @POST(MEMBERSHIP_CARD_REMOVE)
    Call<ResponseBody> membershipCardRemove(@Body RequestBody body);

    @POST(MEMBERSHIP_CARD_LIST)
    Call<ResponseBody> membershipCardList(@Body RequestBody body);

    @POST(FORGET_PASSWORD_SMS)
    Call<ResponseBody> forgetPasswordSms(@Body RequestBody body);

    @POST(CHECK_FORGET_PASSWORD_SMS)
    Call<ResponseBody> checkForgetPasswordSms(@Body RequestBody body);

    @POST(RESET_PAY_PASSWORD)
    Call<ResponseBody> resetPayPassword(@Body RequestBody body);

    @POST(UPDATE_PAY_PASSWORD)
    Call<ResponseBody> updatePayPassword(@Body RequestBody body);

    @POST(SETTING_DEFAULT_CARD)
    Call<ResponseBody> settingDefaultCard(@Body RequestBody body);

    @POST(DEFAULT_CARD_DETAIL)
    Call<ResponseBody> defaultCardDetail(@Body RequestBody body);

}
