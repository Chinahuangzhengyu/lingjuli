package com.zhjl.qihao.zq;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * time   :  2018/11/7
 * author :  Z
 * des    :  配合 Retrofit 使用的 接口 ，暂时就不使用 RxJava2 了
 * <p>
 * //... 自己个生成一个 json串
 * final RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
 */
public interface ApiInterfaces {
    //    @Streaming //大文件时要加不然会OOM
//    @GET
//    Call<ResponseBody> downloadFile(@Url String fileUrl);


    String SWITCH = "/appInterface.php?m=good&s=switch&version=3.0";

    // 维修列表;
    String SERVICELIST = "/mobileInterface/service/special/list";

    //维修详情
    String SERVICEDETAIL = "/mobileInterface/service/special/detailedInfo";


    // 退单;
    String CHARGEBACK = "/psms/serviceSpecial!cancel.action";


    // 查看完成情况
    String FINISHDETAIL = "/mobileInterface/property/management/dealInfo";

    //通过支付类型查找对应的支付方式信息
    String GETPAYTYPEINFO = "/psms/phone/costInfo/getOnLinePayTypeInfo";
//    @POST(SWITCH)
//    Call<ResponseBody> test(@Body RequestBody body);


    /**
     * 维修列表
     *
     * @param body
     * @return
     */
    @POST(SERVICELIST)
    Call<ResponseBody> serviceList(@Body RequestBody body);

    /**
     * 维修详情;
     *
     * @param body
     * @return
     */
    @POST(SERVICEDETAIL)
    Call<ResponseBody> serviceDetail(@Body RequestBody body);

    /**
     * 退单
     *
     * @param body
     * @return
     */
    @POST(CHARGEBACK)
    Call<ResponseBody> chargeBack(@Body RequestBody body);


    /**
     * 查看完成情况
     *
     * @param body
     * @return
     */
    @POST(FINISHDETAIL)
    Call<ResponseBody> finishDetail(@Body RequestBody body);


    /**
     * 通过支付类型查找对应的支付方式信息
     *
     * @param body
     * @return
     */
    @POST(GETPAYTYPEINFO)
    Call<ResponseBody> getPayTypeInfo(@Body RequestBody body);
}
