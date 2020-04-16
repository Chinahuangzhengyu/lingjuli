package com.zhjl.qihao.opendoor.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface OpenInterface {
    //获取用户所在小区门禁列表
    String LOCK_GROUP_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/maiChiLock/lockGroupList";
    //获取某个门禁机的开锁二维码
    String GET_OPEN_DOOR_CODE = API_HOST + JAVA_PORT_NUMBER + "/api/maiChiLock/qrCode";

    @POST(LOCK_GROUP_LIST)
    Call<ResponseBody> lockGroupList(@Body RequestBody body);
    @POST(GET_OPEN_DOOR_CODE)
    Call<ResponseBody> getOpenDoorCode(@Body RequestBody body);
}
