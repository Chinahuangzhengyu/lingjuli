package com.zhjl.qihao.homemanage.api;

import com.zhjl.qihao.util.UrlChangeUtils;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface HomeManageInterface {
    //业主房间入住申请列表
    String APPLY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/applyList";
    //业主审核入住申请
    String RESIDENT_APPLY = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/verifyApply";

    @GET(APPLY_LIST)
    Call<ResponseBody> applyList(@QueryMap Map<String, Object> map);

    @POST(RESIDENT_APPLY)
    Call<ResponseBody> residentApply(@Body RequestBody body);
}
