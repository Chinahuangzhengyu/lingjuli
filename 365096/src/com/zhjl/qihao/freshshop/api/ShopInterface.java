package com.zhjl.qihao.freshshop.api;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.zhjl.qihao.util.UrlChangeUtils.PHP_API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.PHT_PORT_NUMBER;

public interface ShopInterface {

    //商品类别
    String SHOP_TYPE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/category/getrecommend";
    //商品更多类别
    String SHOP_MORE_TYPE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/category/getgroupcategorybyparentid";
    //商品类别更多
    String SHOP_TYPE_MORE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/category/getlastcategory";
    //商品列表
    String SHOP_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/getproductlist";
    //商品详情
    String SHOP_DETAIL = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/getproduct";
    //商品评价
    String SHOP_EVALUATE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/getlist";
    //商品添加购物车
    String ADD_SHOP_CAR = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcart/addtocart";
    //商品订单确认
    String SHOP_ORDER_SURE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/order";
    //获取收货地址
    String GET_HOUSE_ADDRESS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/gethouse";
    //获取小区自提地址
    String GET_COLLECT_ADDRESS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/communitycollect/getcollect";
    //获取线下店地址
    String GET_SHOP_ADDRESS = PHP_API_HOST + PHT_PORT_NUMBER + "/api/shop/getshops";
    //订单创建
    String SHOP_ORDER_CREATE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/makeorder";
    //订单支付
    String ORDER_PAY = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getpay";
    //购物车列表
    String SHOP_CAR_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcart/getlist";
    //购物车商品移除
    String SHOP_CAR_REMOVE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcart/removecartitems";
    //获得购物车商品数量
    String GET_SHOP_CAR_NUM = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcart/getnumber";
    //订单所有状态数量
    String ALL_ORDER_STATUS_NUMBER = PHP_API_HOST + PHT_PORT_NUMBER + "/api/order/getallorderstatus";
    //获取推荐商品
    String GET_PRODUCT_SHOP = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/getrecommendproduct";
    //举报类型
    String REPORTING_TYPE = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/getreporttype";
    //评论举报
    String COMMENT_REPORTING = PHP_API_HOST + PHT_PORT_NUMBER + "/api/productcomment/report";
    //获取商品热搜词
    String GET_SHOP_SEARCH = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/gethotkey";
    //获取搜索列表
    String GET_SHOP_SEARCH_LIST = PHP_API_HOST + PHT_PORT_NUMBER + "/api/product/search";
    //余额支付
    String BALANCE_PAY = PHP_API_HOST+PHT_PORT_NUMBER+"/api/payment/yuepay";

    @POST(SHOP_TYPE)
    Call<ResponseBody> shopType(@Body RequestBody body);

    @POST(SHOP_MORE_TYPE)
    Call<ResponseBody> shopMoreType(@Body RequestBody body);

    @POST(SHOP_TYPE_MORE)
    Call<ResponseBody> shopTypeMore(@Body RequestBody body);

    @POST(SHOP_LIST)
    Call<ResponseBody> shopList(@Body RequestBody body);

    @POST(SHOP_DETAIL)
    Call<ResponseBody> shopDetail(@Body RequestBody body);

    @POST(SHOP_EVALUATE)
    Call<ResponseBody> shopEvaluate(@Body RequestBody body);

    @POST(ADD_SHOP_CAR)
    Call<ResponseBody> addShopCar(@Body RequestBody body);

    @POST(SHOP_ORDER_SURE)
    Call<ResponseBody> shopOrderSure(@Body RequestBody body);

    @POST(GET_HOUSE_ADDRESS)
    Call<ResponseBody> getHouseAddress(@Body RequestBody body);

    @POST(GET_COLLECT_ADDRESS)
    Call<ResponseBody> getCollectAddress(@Body RequestBody body);

    @POST(GET_SHOP_ADDRESS)
    Call<ResponseBody> getShopAddress(@Body RequestBody body);

    @POST(SHOP_ORDER_CREATE)
    Call<ResponseBody> shopOrderCreate(@Body RequestBody body);

    @POST(SHOP_CAR_LIST)
    Call<ResponseBody> shopCarList(@Body RequestBody body);

    @POST(SHOP_CAR_REMOVE)
    Call<ResponseBody> shopCarRemove(@Body RequestBody body);

    @POST(ALL_ORDER_STATUS_NUMBER)
    Call<ResponseBody> allOrderStatusNumber(@Body RequestBody body);

    @POST(ORDER_PAY)
    Call<ResponseBody> orderPay(@Body RequestBody body);

    @POST(GET_PRODUCT_SHOP)
    Call<ResponseBody> getProductShop(@Body RequestBody body);

    @POST(REPORTING_TYPE)
    Call<ResponseBody> reportingType(@Body RequestBody body);

    @POST(COMMENT_REPORTING)
    Call<ResponseBody> commentReporting(@Body RequestBody body);

    @POST(GET_SHOP_SEARCH)
    Call<ResponseBody> getShopSearch(@Body RequestBody body);

    @POST(GET_SHOP_SEARCH_LIST)
    Call<ResponseBody> getShopSearchList(@Body RequestBody body);

    @POST(GET_SHOP_CAR_NUM)
    Call<ResponseBody> getShopCarNum(@Body RequestBody body);

    @POST(BALANCE_PAY)
    Call<ResponseBody> balancePay(@Body RequestBody body);
}
