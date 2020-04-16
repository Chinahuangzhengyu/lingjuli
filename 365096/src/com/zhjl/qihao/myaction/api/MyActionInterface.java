package com.zhjl.qihao.myaction.api;

import com.zhjl.qihao.util.UrlChangeUtils;

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

public interface MyActionInterface {
    //我的评论列表
    String MY_REPLY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/myDiscuss";
    //获取我的帖子
    String GET_MY_NOTE = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/myForum";
    //获取某一条讨论详情
    String GET_NOE_NOTE_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/detail";

    @GET(MY_REPLY_LIST)
    Call<ResponseBody> myReplyList(@QueryMap Map<String,Object> body);

    @GET(GET_MY_NOTE)
    Call<ResponseBody> getMyNote(@QueryMap Map<String,Object> body);

    @GET(GET_NOE_NOTE_DETAIL)
    Call<ResponseBody> getNoeNoteDetail(@QueryMap Map<String,Object> body);
}
