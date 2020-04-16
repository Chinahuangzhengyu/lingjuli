package com.zhjl.qihao.propertyservicepay.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public interface PropertyPayInterface {

    //获取物业缴费分类列表
    String GET_PROPERTY_PAY_TYPE_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/property/paymentCateList";

    //获取物业缴费信息
    String GET_PROPERTY_PAY_INFO = API_HOST + JAVA_PORT_NUMBER + "/api/property/info";

    //创建物业缴费订单
    String CREATE_PROPERTY_ORDER = API_HOST + JAVA_PORT_NUMBER + "/api/property/createOrder";

    //订单支付
    String ORDER_PAY = API_HOST + JAVA_PORT_NUMBER + "/api/property/payOrder";

    //计算物业服务费用
    String PROPERTY_CALCULATE = API_HOST + JAVA_PORT_NUMBER + "/api/property/calculate";

    //物业缴费记录列表
    String PROPERTY_RECORD_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/property/paymentRecord";

    //业主房间管理接口
    String USER_ROOM_LIST = API_HOST + JAVA_PORT_NUMBER + "/api/userRoom/list";

    @POST(GET_PROPERTY_PAY_TYPE_LIST)
    Call<ResponseBody> getPropertyPayTypeList(@Body RequestBody body);

    @POST(GET_PROPERTY_PAY_INFO)
    Call<ResponseBody> getPropertyPayInfo(@Body RequestBody body);

    @POST(CREATE_PROPERTY_ORDER)
    Call<ResponseBody> createPropertyOrder(@Body RequestBody body);

    @POST(ORDER_PAY)
    Call<ResponseBody> orderPay(@Body RequestBody body);

    @POST(PROPERTY_CALCULATE)
    Call<ResponseBody> propertyCalculate(@Body RequestBody body);

    @POST(PROPERTY_RECORD_LIST)
    Call<ResponseBody> propertyRecordList(@Body RequestBody body);

    @POST(USER_ROOM_LIST)
    Call<ResponseBody> userRoomList(@Body RequestBody body);
}
