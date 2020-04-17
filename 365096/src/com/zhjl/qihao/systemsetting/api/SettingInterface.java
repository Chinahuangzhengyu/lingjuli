package com.zhjl.qihao.systemsetting.api;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface SettingInterface {
    //更新用户信息
    String UPDATE_INFO = API_HOST + JAVA_PORT_NUMBER + "/api/user/updateInfo";
    //获取所有小区列表
    String GET_ALL_COMMUNITY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/community/listAll";
    //获取某个小区的楼栋列表
    String GET_ONE_COMMUNITY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/partition/list";
    //获取某个楼栋的房间列表
    String GET_ONE_ROOM_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/room/list";
    //用户入住
    String USER_RESIDENT = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/resident";
    //平台反馈
    String FEEDBACK_SEND = API_HOST + JAVA_PORT_NUMBER + "/api/platform/feedback";
    //单张图片上传
    String UPLOAD_PHOTO = API_HOST + JAVA_PORT_NUMBER + "/psms/upfile!uploadfile.action";
    //多张图片上传
    String UPLOAD_PHOTOS = API_HOST + JAVA_PORT_NUMBER + "/psms/upfile!uploadFiles.action";
    //解绑房间
    String UNBUILDING_ROOM = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/unResident";
    //新帖子图片上传接口
//    String NEW_NOTE_UPLOAD_PHOTO = "https://7hao.oss-cn-beijing.aliyuncs.com";
    //获取入住详情
    String GET_RESIDENT_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/detail";

    @POST(UPDATE_INFO)
    Call<ResponseBody> updateInfo(@Body RequestBody body);

    @POST(GET_ALL_COMMUNITY_LIST)
    Call<ResponseBody> getAllCommunityList(@Body RequestBody body);

    @POST(GET_ONE_COMMUNITY_LIST)
    Call<ResponseBody> getOneCommunityList(@Body RequestBody body);

    @POST(GET_ONE_ROOM_LIST)
    Call<ResponseBody> getOneRoomList(@Body RequestBody body);

    @POST(USER_RESIDENT)
    Call<ResponseBody> userResident(@Body RequestBody body);

    @POST(FEEDBACK_SEND)
    Call<ResponseBody> feedbackSend(@Body RequestBody body);

    @POST(UNBUILDING_ROOM)
    Call<ResponseBody> unBuildingRoom(@Body RequestBody body);

    @Multipart
    @POST(UPLOAD_PHOTO)
    Call<ResponseBody> upLoadFile(@Part("versionTag") RequestBody body, @Part MultipartBody.Part file);

    @Multipart
    @POST(UPLOAD_PHOTOS)
    Call<ResponseBody> upLoadFiles(@Part("versionTag") RequestBody body, @Part List<MultipartBody.Part> file);

    @Multipart
    @POST
    Call<ResponseBody> newNoteUploadPhoto(@Url String url, @Part List<MultipartBody.Part> partLis);

    @GET(GET_RESIDENT_DETAIL)
    Call<ResponseBody> getResidentDetail(@QueryMap Map<String,Object> map);
}
