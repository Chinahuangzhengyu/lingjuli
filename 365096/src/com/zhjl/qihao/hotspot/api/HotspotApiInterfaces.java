package com.zhjl.qihao.hotspot.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

/**
 * 热点接口
 */
public interface HotspotApiInterfaces {
    //热点列表接口
    String HOTSPOT_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/hotspot";
    //获取历史热点
    String HISTORY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/historyHotSpot";
    //分类排序
    String TYPE_LIST_SORT = API_HOST + JAVA_PORT_NUMBER + "/mobileInterface/donuts/cate/detail";
    //帖子列表
    String NOTE_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/list";

    @POST(HOTSPOT_LIST)
    Call<ResponseBody> hotspotList(@Body RequestBody body);


    @POST(HISTORY_LIST)
    Call<ResponseBody> historyList(@Body RequestBody body);

    @POST(TYPE_LIST_SORT)
    Call<ResponseBody> typeListSort(@Body RequestBody body);
    @POST(NOTE_LIST)
    Call<ResponseBody> noteList(@Body RequestBody body);
}
