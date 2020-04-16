package com.zhjl.qihao.mymessage.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface MessageInterface {
    //获取通知列表
    String MESSAGE_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/notify/list";

    //查询是否有新消息
    String NEW_READ_MESSAGE = API_HOST + JAVA_PORT_NUMBER + "/api/notify/searchNewMessage";

    //查看通知详情
    String MESSAGE_DETAIL_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/notify/detail";

    //全部设置已读
    String MESSAGE_ALL_READ = API_HOST + JAVA_PORT_NUMBER + "/api/notify/allRead";

    @POST(MESSAGE_LIST)
    Call<ResponseBody> messageList(@Body RequestBody body);

    @POST(NEW_READ_MESSAGE)
    Call<ResponseBody> newReadMessage(@Body RequestBody body);

    @POST(MESSAGE_DETAIL_LIST)
    Call<ResponseBody> messageDetailList(@Body RequestBody body);

    @POST(MESSAGE_ALL_READ)
    Call<ResponseBody> messageAllRead(@Body RequestBody body);
}
