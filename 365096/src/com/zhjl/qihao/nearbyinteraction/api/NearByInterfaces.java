package com.zhjl.qihao.nearbyinteraction.api;


import com.android.volley.Response;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface NearByInterfaces {

    //邻里互动分类列表分类标题
    String NEARBY_TYPE_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumTopic/list";
    //获取上传文件签名
    String GET_FILE_SIGN = API_HOST + JAVA_PORT_NUMBER + "/api/oss/policy";
    //帖子搜索
    String SEARCH_NOTES = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/search";
    //帖子删除
    String NOTE_DELETE = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/delete";
    //帖子评论删除
    String NOTE_REPLY_DELETE = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/delete";
    //帖子评论删除
    String NOTE_REPLY_REPORT = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/report";
    //热门搜索关键字
    String NOTE_HOT_SEARCH = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/hotSearchKeyword";

    @GET(NEARBY_TYPE_LIST)
    Call<ResponseBody> nearbyTypeList(@QueryMap Map<String, Object> body);

    @GET(GET_FILE_SIGN)
    Call<ResponseBody> getFileSign(@QueryMap Map<String, Object> body);

    @POST(SEARCH_NOTES)
    Call<ResponseBody> searchNotes(@Body RequestBody body);

    @POST(NOTE_DELETE)
    Call<ResponseBody> noteDelete(@Body RequestBody body);

    @POST(NOTE_REPLY_DELETE)
    Call<ResponseBody> noteReplyDelete(@Body RequestBody body);

    @POST(NOTE_REPLY_REPORT)
    Call<ResponseBody> noteReplyReport(@Body RequestBody body);

    @GET(NOTE_HOT_SEARCH)
    Call<ResponseBody> notHotSearch(@QueryMap Map<String,Object> body);
}
