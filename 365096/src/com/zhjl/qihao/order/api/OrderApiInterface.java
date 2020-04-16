package com.zhjl.qihao.order.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.PHP_API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.PHT_PORT_NUMBER;

public interface OrderApiInterface {

    //订单列表
    String ORDER_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getlist";
    //订单处理（确认、取消、删除）
    String ORDER_HANDLE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/dealorder";
    //订单商品详情
    String ORDER_SHOP_DETAIL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/detail";
    //选择售后方式
    String CHOOSE_SERVICE_TYPE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getorderitem";
    //上传售后图片
    String UPLOAD_SERVICE_IMG = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/uploadaftersaleimages";
    //删除售后图片
    String DELETE_SERVICE_IMG = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/deleteaftersaleimage";
    //提交售后申请
    String SUBMIT_SERVICE_REQUEST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/submititemaftersale";
    //获取唯一码
    String GET_ONE_NUMBER = PHP_API_HOST + PHT_PORT_NUMBER + "/api/common/getpostkey";
    //获取售后列表
    String GET_SERVICE_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getaftersalelist";
    //售后详情
    String SERVICE_LIST_DETAIL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getaftersaledetail";
    //上传商品评论图片
    String UPLOAD_SHOP_EVALUATE_IMG = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/uploadimages";
    //上传商品评论图片
    String SUBMIT_COMMENT = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/submitcomment";
    //删除商品评论图片
    String DELETE_SHOP_EVALUATE_IMG = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/deletecommentimage";
    //售后已读
    String READ_AFTER_SALE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/readaftersale";

    @POST(ORDER_LIST)
    Call<ResponseBody> orderList(@Body RequestBody body);
    @POST(ORDER_HANDLE)
    Call<ResponseBody> orderHandle(@Body RequestBody body);
    @POST(ORDER_SHOP_DETAIL)
    Call<ResponseBody> orderShopDetail(@Body RequestBody body);
    @POST(CHOOSE_SERVICE_TYPE)
    Call<ResponseBody> chooseServiceType(@Body RequestBody body);
    @POST(UPLOAD_SERVICE_IMG)
    Call<ResponseBody> upLoadServiceImg(@Body RequestBody body);
    @POST(DELETE_SERVICE_IMG)
    Call<ResponseBody> deleteServiceImg(@Body RequestBody body);
    @POST(GET_ONE_NUMBER)
    Call<ResponseBody> getOneNumber(@Body RequestBody body);
    @POST(SUBMIT_SERVICE_REQUEST)
    Call<ResponseBody> submitServiceRequest(@Body RequestBody body);
    @POST(GET_SERVICE_LIST)
    Call<ResponseBody> getServiceList(@Body RequestBody body);
    @POST(SERVICE_LIST_DETAIL)
    Call<ResponseBody> serviceListDetail(@Body RequestBody body);
    @POST(UPLOAD_SHOP_EVALUATE_IMG)
    Call<ResponseBody> uploadShopEvaluateImg(@Body RequestBody body);
    @POST(SUBMIT_COMMENT)
    Call<ResponseBody> submitComment(@Body RequestBody body);
    @POST(DELETE_SHOP_EVALUATE_IMG)
    Call<ResponseBody> deleteShopEvaluateImg(@Body RequestBody body);
    @POST(READ_AFTER_SALE)
    Call<ResponseBody> readAfterSale(@Body RequestBody body);
}
