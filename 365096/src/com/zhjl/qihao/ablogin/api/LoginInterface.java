package com.zhjl.qihao.ablogin.api;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

/**
 * 登录接口请求
 */
public interface LoginInterface {
    //请求登录
    String LOGIN = API_HOST + JAVA_PORT_NUMBER + "/api/user/login";

    //用户注册
    String REGISTER_LOGIN = API_HOST + JAVA_PORT_NUMBER + "/api/user/register";

    //游客登录
    String TOURIST_LOGIN = API_HOST + JAVA_PORT_NUMBER + "/api/user/tourist";

    //检查登录
    String CHECK_LOGIN = API_HOST + JAVA_PORT_NUMBER + "/api/user/checkLogin";

    //图片验证检查
    String PICTURE_CHECK = API_HOST + JAVA_PORT_NUMBER + "/api/sms/picture";

    //获取验证码
    String GET_SMS_CHECK = API_HOST + JAVA_PORT_NUMBER + "/api/sms/sendVerifyCode";

    //修改密码
    String UPDATE_PASSWORD = API_HOST + JAVA_PORT_NUMBER + "/api/user/updatePassword";

    //切换房间
    String SWITCH_CHANGE_ROOM = API_HOST + JAVA_PORT_NUMBER + "/api/user/switchRoom";

    @POST(LOGIN)
    Call<ResponseBody> getLogin(@Body RequestBody body);

    @POST(CHECK_LOGIN)
    Call<ResponseBody> checkLogin(@Body RequestBody body);

    @POST(REGISTER_LOGIN)
    Call<ResponseBody> registerLogin(@Body RequestBody body);

    @POST(TOURIST_LOGIN)
    Call<ResponseBody> touristLogin(@Body RequestBody body);

    @POST(PICTURE_CHECK)
    Call<ResponseBody> pictureCheck(@Body RequestBody body);

    @POST(GET_SMS_CHECK)
    Call<ResponseBody> getSmsCheck(@Body RequestBody body);

    @POST(UPDATE_PASSWORD)
    Call<ResponseBody> updatePassword(@Body RequestBody body);

    @POST(SWITCH_CHANGE_ROOM)
    Call<ResponseBody> switchChangeRoom(@Body RequestBody body);
}
