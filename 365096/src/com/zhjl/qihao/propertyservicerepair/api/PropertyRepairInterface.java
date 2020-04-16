package com.zhjl.qihao.propertyservicerepair.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface PropertyRepairInterface {
    //物业维修服务信息
    public String PROPERTY_REPAIR_INFO = API_HOST + JAVA_PORT_NUMBER + "/api/propertyRepair/info";

    //物业报修
    public String PROPERTY_REPAIR_SUBMIT = API_HOST + JAVA_PORT_NUMBER + "/api/propertyRepair/repair";

    //物业报修记录列表
    public String PROPERTY_REPAIR_RECORD_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/propertyRepair/list";

    //提醒报修处理
    public String PROPERTY_REPAIR_REMIND = API_HOST + JAVA_PORT_NUMBER + "/api/propertyRepair/remind";

    //获取某一条报修记录详情
    public String PROPERTY_REPAIR_RECORD_DETAIL = API_HOST + JAVA_PORT_NUMBER + "/api/propertyRepair/detail";

    @POST(PROPERTY_REPAIR_INFO)
    Call<ResponseBody> propertyRepairInfo(@Body RequestBody body);

    @POST(PROPERTY_REPAIR_SUBMIT)
    Call<ResponseBody> propertyRepairSubmit(@Body RequestBody body);

    @POST(PROPERTY_REPAIR_RECORD_LIST)
    Call<ResponseBody> propertyRepairRecordList(@Body RequestBody body);

    @POST(PROPERTY_REPAIR_REMIND)
    Call<ResponseBody> propertyRepairRemind(@Body RequestBody body);

    @POST(PROPERTY_REPAIR_RECORD_DETAIL)
    Call<ResponseBody> propertyRepairRecordDetail(@Body RequestBody body);

}
