package com.zhjl.qihao.complaint.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

//投诉详情接口
public interface ComplaintApiInterfaces {
    //查看帖子详情
    String COMPLAINT_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/detail";

    //讨论详情接口
    String COMPLAINT_REPLY_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/list";

    //帖子点赞接口
    String ADD_AGREE = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/praise";

    //帖子回复点赞接口
    String REPLY_ADD_AGREE = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/praise";

    //回复帖子接口
    String REPLY_CONTENT = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/reply";

    //评论历史记录
    String COMMENT_HISTORY_RECORD = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/detail";

    //帖子回复列表
    String NOTE_LIST_DISCUSS = API_HOST + JAVA_PORT_NUMBER + "/api/forumDiscuss/listDiscuss";

    //消息发布
    String SUBMIT_MESSAGE = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/publish";

    //举报帖子
    String REPORT_CONTENT = API_HOST + JAVA_PORT_NUMBER + "/api/forumNote/report";

    //投诉分类
    String COMPLAINT_TYPE = API_HOST + JAVA_PORT_NUMBER + "/mobileInterface/customer/complaint/cateList";

    //物业处理进度点赞接口
    String RECORD_ADD_AGREE = API_HOST + JAVA_PORT_NUMBER + "/mobileInterface/customer/complaint/reply/praise";

    //物业处理进度列表接口
    String PROPERTY_PROGRESS_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/forumComplaint/replyList";

    @POST(COMPLAINT_DETAIL)
    Call<ResponseBody> getComplaintDetail(@Body RequestBody body);

    @POST(ADD_AGREE)
    Call<ResponseBody> addAgree(@Body RequestBody body);

    @POST(REPLY_ADD_AGREE)
    Call<ResponseBody> replyAddAgree(@Body RequestBody body);

    @POST(REPLY_CONTENT)
    Call<ResponseBody> addReply(@Body RequestBody body);

    @POST(COMMENT_HISTORY_RECORD)
    Call<ResponseBody> getCommentHistoryRecord(@Body RequestBody body);

    @POST(SUBMIT_MESSAGE)
    Call<ResponseBody> sendSubmitMessage(@Body RequestBody body);

    @POST(REPORT_CONTENT)
    Call<ResponseBody> reportContent(@Body RequestBody body);

    @POST(COMPLAINT_TYPE)
    Call<ResponseBody> getComplaintType(@Body RequestBody body);

    @POST(RECORD_ADD_AGREE)
    Call<ResponseBody> addRecordAgree(@Body RequestBody body);

    @POST(PROPERTY_PROGRESS_LIST)
    Call<ResponseBody> getPropertyProgressList(@Body RequestBody body);

    @POST(COMPLAINT_REPLY_DETAIL)
    Call<ResponseBody> getComplaintReplyDetail(@Body RequestBody body);

    @POST(NOTE_LIST_DISCUSS)
    Call<ResponseBody> noteListDiscuss(@Body RequestBody body);

}
