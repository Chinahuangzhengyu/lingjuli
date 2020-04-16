package com.zhjl.qihao.propertyRepresentations.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface ExposureInterface {
    //获取物业曝光类型
    String EXPOSURE_TYPE = API_HOST + JAVA_PORT_NUMBER + "/api/manageInfo/type";

    //获取物业曝光消息列表
    String EXPOSURE_MESSAGE_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/manageInfo/list";

    //获取曝光详情
    String EXPOSURE_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/manageInfo/detail";

    @GET(EXPOSURE_TYPE)
    Call<ResponseBody> exposureType(@QueryMap Map<String, Object> map);

    @GET(EXPOSURE_MESSAGE_LIST)
    Call<ResponseBody> exposureMessageList(@QueryMap Map<String, Object> map);

    @GET(EXPOSURE_DETAIL)
    Call<ResponseBody> exposureDetail(@QueryMap Map<String, Object> map);
}
