package com.zhjl.qihao.chooseCity.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface ChooseCityInterface {

    //获取城市列表
    String GET_CITY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/community/acquireCity";

    //获取城市下的小区列表
    String GET_SMALL_COMMUNITY_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/community/smallCommunityList";

    //用户小区切换
    String GET_SWITCH_COMMUNITY = API_HOST + JAVA_PORT_NUMBER + "/api/user/switchSmallCommunity";


    @POST(GET_CITY_LIST)
    Call<ResponseBody> getCityList(@Body RequestBody body);

    @POST(GET_SMALL_COMMUNITY_LIST)
    Call<ResponseBody> getSmallCommunityList(@Body RequestBody body);


    @POST(GET_SWITCH_COMMUNITY)
    Call<ResponseBody> getSwitchCommunity(@Body RequestBody body);

}
