package com.zhjl.qihao.abrefactor.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;
import static com.zhjl.qihao.util.UrlChangeUtils.PHP_API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.PHT_PORT_NUMBER;

/**
 * 首页 数据请求
 */
public interface MainApiInterfaces {
    //首页邻里分类
    String NEARBY_TYPE = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/type";

    //投诉列表
    String COMPLAINT_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumComplaint/list";

    //用户登出
    String USER_EXIT = API_HOST + JAVA_PORT_NUMBER + "/api/user/logout";

    //轮播图
    String GET_INFO_IMG = PHP_API_HOST + PHT_PORT_NUMBER + "/api/advertisement/getrecommend";

    //App检查更新
    String CHECK_APP = API_HOST + JAVA_PORT_NUMBER + "/api/app/version";

    //获取首页推广消息
    String GET_MAIN_MESSAGE = API_HOST + JAVA_PORT_NUMBER + "/api/notify/popularize";

    //下载
    @Streaming
    @GET
    Call<ResponseBody> downLoad(@Url String url);

    @POST(NEARBY_TYPE)
    Call<ResponseBody> nearbyType(@Body RequestBody body);

    @POST(COMPLAINT_LIST)
    Call<ResponseBody> getComplaintList(@Body RequestBody body);

    @POST(USER_EXIT)
    Call<ResponseBody> userExit(@Body RequestBody body);

    @POST(GET_INFO_IMG)
    Call<ResponseBody> getInfoImg(@Body RequestBody body);

    @POST(CHECK_APP)
    Call<ResponseBody> checkApp(@Body RequestBody body);

    @POST(GET_MAIN_MESSAGE)
    Call<ResponseBody> getMainMessage(@Body RequestBody body);
}
