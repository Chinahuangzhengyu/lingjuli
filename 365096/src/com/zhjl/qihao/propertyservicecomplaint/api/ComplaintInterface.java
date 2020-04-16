package com.zhjl.qihao.propertyservicecomplaint.api;

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

public interface ComplaintInterface {
    //获取小区投诉列表
    String COMPLAINT_RECORD_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumComplaint/list";

//    //获取投诉书处理回复信息
//    String COMPLAINT_RECORD_REPLY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumComplaint/replyList";

    //创建投诉/建议
    String CREATE_COMPLAINT = API_HOST + JAVA_PORT_NUMBER + "/api/communityComplaint/create";

    //获取投诉列表
    String GET_COMPLAINT_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/communityComplaint/myComplaint";

    //查看某一条投诉/建议
    String SELECT_ONE_COMPLAINT = API_HOST + JAVA_PORT_NUMBER + "/api/communityComplaint/detail";

    //获取当前小区热点投诉
    String GET_COMMUNITY_HOTS_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/communityComplaint/list";

    //获取我的投诉
    String GET_MY_COMPLAINT = API_HOST + JAVA_PORT_NUMBER + "/api/communityComplaint/myComplaint";

    @POST(COMPLAINT_RECORD_LIST)
    Call<ResponseBody> complaintRecordList(@Body RequestBody body);

    //    @POST(COMPLAINT_RECORD_REPLY_LIST)
//    Call<ResponseBody> complaintRecordReplyList(@Body RequestBody body);
    @POST(CREATE_COMPLAINT)
    Call<ResponseBody> createComplaint(@Body RequestBody body);

    @POST(GET_COMPLAINT_LIST)
    Call<ResponseBody> getComplaintList(@Body RequestBody body);

    @POST(SELECT_ONE_COMPLAINT)
    Call<ResponseBody> selectOneComplaint(@Body RequestBody body);

    @GET(GET_COMMUNITY_HOTS_LIST)
    Call<ResponseBody> getCommunityHotsList(@QueryMap Map<String,Object> body);

    @POST(GET_MY_COMPLAINT)
    Call<ResponseBody> getMyComplaint(@Body RequestBody body);

}
