package com.zhjl.qihao;

import android.os.Environment;


/****
 * 常量类
 * @author 黄南榆
 *
 */
public class Constants {

    public static final String M_TOKEN = "new_token";

    /**
     * 限制时间段
     */
    public static final String LIMITED_TIME = "limited_time";

    /**
     * 最大的邀请人员数
     */
    public static final String MAX_INVITE = "max_invite";

    /**
     * 确定手机的唯一性，使用alipayutdid.jar阿里库
     */
    public static final String NEWDEVICEID = "deviceId";
    /**
     * 正式与测试的判断参数
     */
    public static final String SERVE_NUM = "serve_num";
    /**
     * 第一次开门引导
     */
    public static final String ROOM_NUM = "room_num";
    /**
     * 第一次开门引导
     */
    public static final String FIRST_OPENDOOR = "first_opendoor";
    /**
     * 情感状况
     */
    //是否第一次运行
    public static final String FIRST_OPEN = "first_open";

    public static final String YUN_TEST_IP = "191.167.3.93";
    public static final String ROOMNAME_TEST = "01010201";
    public static final String SMALLCODE_TEST = "0003001";
    public static final boolean DEBUG_YUN = false;

    public static final String APP_TAG = "zhjl";

    /**
     * 零距离根目录
     */
    public static final String ROOT_DIR = "/zhjl";
    /**
     * 零距离图片缓存目录
     */
    public static final String IMAGE_CACHE_DIR = "/.cache";

    public static final String SDPATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public static final String APPSDPATH = SDPATH + ROOT_DIR;

    public static final String SNAPSHOT = "/Snapshot";

    public static final String CRASH = APPSDPATH + "/crash";

    public static final int YES = 1;
    public static final int NO = 0;
    public static final String YES_STRING = "1";
    public static final String NO_STRING = "0";

    /**
     * 成功
     */
    public static final String REQUEST_SUCCESS = "0";
//	public static final int REQUEST_FAIL = 1;


    public static final String RESULT = "result";
    public static final String DATA = "data";

    public static final String CITY_NAME = "cityName";

    public static final String DISTRICT_NAME = "area";

    public static final String HOUSE_RENT_LIST_FILE = "HouseRentList";

    public static final String POSITION = "position";
    /**
     * 用户类型
     * 0 游客类型Visitor
     * 1业主类型Owner
     * 2租客类型Tenant
     * 3家人类型Family
     */
    public static final String STR_USER_TYPE = "userType";
    public static final int USER_VISITOR = 0;
    public static final int USER_OWNER = 1;
    public static final int USER_TENANT = 2;
    public static final int USER_FAMILY = 3;

    /***
     * 登陆类型
     */
    public static final String LOGIN_TYPE = "loginType";
    public static final String PASSWORD_LOGIN = "1";
    public static final String MESSAGE_LOGIN = "2";


    /**
     * 微信QQ登陆登陆认证所需要的
     */

    public static final String APP_ID_WX = "wx240402235150a062";
    public static final String APP_SECRET = "34a6ce47e62c0f3b02c9704695d141f7";


    public static final String APP_QQ_ID = "1109246292";
    public static final String APP_QQ_SECRET = "JzKN8EFHUSiUXrw0";

    /**
     * 支付宝APPid
     */
    public static final String APP_ID_ZFB = "";

    /**
     * 支付宝商户ID
     **/
    public static final String PARTNER = "";
    /**
     * 支付宝收款帐号
     **/
    public static final String SELLER_ID = "";
    /**
     * 支付宝pkcs8密钥
     **/
    public static final String RSA_PRIVATE ="";

    /**
     * 数据库名称
     */

    public static final String DATABASE_NAME = "nearby";
    /**
     * 是否第一次使用
     */
    public static final String ENTER_TIMES = "enter_times";
    public static final String VERSIONCEODE = "versioncode";

    /**
     * 数据库版本
     */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CITY_NAME = "CloudCity.db";
    public static final int DBVERSION = 20;

    /**
     * 友盟点击事件key--------star
     */
    public static final String LOGIN_KEY = "login";//登录
    public static final String ADVERTISEMENT_KEY = "advertisement";//广告点击
    public static final String SHOP_KEY = "shop";//周边
    public static final String PROPERTY_BILL_KEY = "propertyBill";//特来账单
    public static final String REPAIR_SERVICE_KEY = "repairService";//维修服务
    public static final String CONTACT_PROPERTY_KEY = "contactProperty";//联系物业
    public static final String ADD_SWEETCIRCLE_KEY = "addSweetCircle";//发甜甜圈
    public static final String REPLY_SWEETCIRCLE_KEY = "replySweetCircle";//回复甜圈
    public static final String COMMENT_SWEETCIRCLE_KEY = "commentSweetCircle";//评论甜圈
    public static final String RENTAL_HOUSING_KEY = "rentalHousing";//房租出租
    public static final String LEGAL_ADVICE_KEY = "legalAdvice";//法律咨询
    public static final String CUSTOMER_SERVICE_KEY = "customerService";//客服
    public static final String NOTIFY_VIEW_KEY = "notifyView";//查看消息
    public static final String MY_SERVICE_KEY = "myService";//我的服务
    public static final String MY_ORDER_KEY = "myOrder";//我的订单
    public static final String MY_INTEGRAL_KEY = "myIntegral";//我的积分
    public static final String MY_PROPERTY_KEY = "myProperty";//我的物业
    public static final String MY_MESSAGE_KEY = "myMessage";//我的消息
    public static final String MY_INVITATION_KEY = "myInvitation";//我的邀请
    public static final String MY_AUTHORIZATION_KEY = "myAuthorization";//我的授权
    public static final String APPLICATIONAUTHORIZATION_KEY = "applicationAuthorization";//申请授权
    public static final String LOGIN_PASSWORD_MODIFY_KEY = "loginPasswordModify";//登录密码修改
    public static final String VIEW_PUSH_INFORMATION_KEY = "viewPushInformation";//查看推送消息
    public static final String VIEW_COURSE_KEY = "viewCourse";//查看教程
    public static final String ABOUT_US_KEY = "aboutUs";//关于我们
    public static final String CONTACT_US_KEY = "contactUs";//聊系我们
    public static final String USER_AGREEMENT_KEY = "userAgreement";//用户协议
    public static final String VERSION_UPDATE_KEY = "versionUpdate";//版本更新
    public static final String DIALTTELEPHONE_KEY = "dialTelephone";//打电话
    public static final String PERSONAL_DATA_KEY = "personalData";//个人资料
    public static final String RECHARGE_KEY = "recharge";//充值
    public static final String DRAWING_KEY = "drawing";//转出
    public static final String DRAW_CLICK_KEY = "drawClick";//抽奖点击
    public static final String DRAW_USE_KEY = "drawUse";//抽奖使用
    public static final String CONVENIENCE_PEOPLE_KEY = "conveniencePeople";//便民
    public static final String PUBLIC_SERVICE_KEY = "publicService";//公共服务
    public static final String LEISURELY_LIFE_KEY = "leisurelyLife";//生活悠闲
    public static final String HOME_REQUIRED_KEY = "homeRequired";//居家必备
    public static final String MY_WALLET_RECHARGE_KEY = "myWalletRecharge";//我的钱包充值
    public static final String MY_WALLET_DRAWING_KEY = "myWalletDrawing";//我的钱包转出
    public static final String MY_WALLET_AUTOMATIC_PAYMENT_OPEN_KEY = "myWalletAutomaticPaymentOpen";//我的钱包自动缴费开
    public static final String MY_WALLET_AUTOMATIC_PAYMENT_CLOSE_KEY = "myWalletAutomaticPaymentClose";//我的钱包自动缴费关
    public static final String PROPERTY_BILL_STORED_KEY = "propertyBillStored";//物业账单预存
    public static final String SOFTARTICLE_IS_SHOW = "softarticle";//判断软文显示隐藏
    public static final String REFUND_CUSTOMER_SERVICE = "refundCustomerService";//退款售后
    public static final String FAST_DELIVERY_BALANCE = "fastDeliveryBalance";//快送结算
    public static final String HOUSE_SERVICE = "servicesReservation";//家政服务
    public static final String FAST_DELIVERY_TYPE = "fastDeliveryType"; //1为便利店，2为小区快送

    /**
     * 友盟点击事件key--------end
     */
    /**
     * 手机请求服务器消息地址
     */
    //public static final String PHONE_REQ_MESSAGE = "/bcportal/phoneReqMessage.html";
    public static final String PHONE_REQ_MESSAGE = "/mobileInterface/newMessage/searchNewMessage";
    /**
     * 手机登陆信息注册地址
     */
    public static final String PHONE_REGEDIT = "/bcportal/phoneRegedit.html";

    /**
     * 服务下单地址
     */
    public final static String ORDER_SAVE_URL = "/bcportal/saveOrder.html";
    /**
     * 获取物业服务
     */
//	public final static String SERVICE_TYPE_URL="/bcportal/getOrderServiceType.html";
    /**
     * 获取物业服务
     */
    public final static String ORDER_SERVICE_URL = "/psms/phone/getAllServiceInfo.action";

    /**
     * 小区通知消息地址
     */
    public final static String MESSAGE_SERVICE_URL = "/bcportal/phoneOnlineMessage!list.html";

    /**
     * 小区电话
     */
//	public static final String SMALLCOMMUNITYPHONE="smallcommunityPhone";
    public static final String SMALLCOMMUNITYPHONE = "phonenumber";

    /**
     * 物业服务查询下单地址
     */
    public final static String ORDERLS_SERVICE_URL = "/psms/phone/searchOrderList.action";

    /**
     * 物业服务查询下单地址
     */
    public final static String NOTE_ADD_URL = "/psms/note!toAdd.action";
    /**
     * 上传多张图片地址
     */
//	public final static String UPLOAD_MANY_FILE_URL="/psms/upfile!uploadfilesome.action";
    public final static String UPLOAD_MANY_FILE_URL = "/psms/upfile!uploadFiles.action";
    public final static String UPLOAD_MANY_FILE_URL_3_0 = "/mobileInterface/upFileAction/uploadFiles";
    public final static String UPLOAD_REFUNDS_CREDENTIALS = "/upload_image.php?target_dir=apply";

    /**
     * 上传单张图片地址
     */
    public final static String UPLOAD_SINGLE_FILE_URL = "/psms/upfile!uploadfile.action";
    /**
     * 上传音频地址
     */
    public final static String UPLOAD_AUDIO_URL = "/psms/upfile!uploadaudio.action";


    public static final String PHONE_REGISTER_BYCODE = "/bcportal/phoneRegisterByCode.html";
    public static final String LOGIN_BYHAND = "/bcportal/phone/loginByHand.html";

    /**
     * 物业账单的地址
     */
    public static final String UTILITY_BILLS = "/psms/phone/searchList!view.action?token=";


    /**
     * 申请授权地址
     */
    public static final String APPLY_AUTHORIZATION_URL = "/mobileInterface/authorize/userApplyAuthorize/add";
    /**
     * 申请授权列表
     */
    public static final String APPLY_AUTHORIZATION_LIST_URL = "/mobileInterface/authorize/userApplyAuthorize/list";
    /**
     * 申请授权详细
     */
    public static final String APPLY_AUTHORIZATION_DETAIL_URL = "/mobileInterface/authorize/userApplyAuthorize/listDetails";


    /**
     * 验证用户身分
     */
    public static final String CHECK_USER_HAS_PWD = "/mobileInterface/shop/identity/check";


    /**
     * 添加业主房间认证
     */
    public static final String ONWER_ROOM_AUTHORIZATION = "/mobileInterface/user/info/residentAttestation";

    /**
     * 验证用户姓名和身份证号是否为首次使用钱包时的身份证和号码
     */
    public static final String VERIFY_USER_NAME_AND_ID = "/mobileInterface/shop/identity/checkInfo";

    /**
     * 手机登陆令牌
     */
    public static final String KEY_TONE = "key_tone";
    /**
     * 系统设置
     */
    public static final String NEARBYSETTING = "nearbySetting";
    /**
     * 手机序列号
     */
    public static final String DEVICEID = "DeviceId";

    /**
     * 房间号
     */
    public static final String ROOMCODE = "roomCode";

    public static final String NEW_ROOM_CODE = "newRoomCode";
    public static final String ROOM_ID = "roomId";

    /**
     * 房间名
     */
    public static final String ROOMNAME = "roomName";

    /**
     * 小区号
     */
    public static final String SMALLCOMMUNITYCODE = "smallCommunityCode";
    public static final String NEWSMALLCOMMUNITYCODE = "newSmallCommunityCode";
    /**
     * 小区名
     */
    public static final String SMALLCOMMUNITYNAME = "smallCommunityName";
    /**
     * 消息数量
     */
    public static final String MESSAGE_COUNT = "messageCount";
    public static final String MESSAGE_ONE = "platformMessageCount";
    public static final String MESSAGE_TWO = "propertyMessageCount";

    /**
     * 手机号
     */
    public static final String REGISTERMOBILE = "registerMobile";
    /**
     * 登录类型
     */
    public static final String LOGINTYPE = "loginType";

    /**
     * 业主手机号码
     */
    public static final String REGISTERMOBILE2 = "registerMobile";

    /**
     * 物业电话
     */

    /**
     * @param NICK_NAME 昵称
     */
    public static final String NICK_NAME = "nickName";

    /*
     *  @param SEX 性别
     * */
    public static final String SEX = "sex";

    /**
     * @param USER_ACCOUNT 账户
     */
    public static final String USER_ACCOUNT = "userAccount";
//	public static final String ACCOUNT="Account";

    public static final String ACCOUNT = "account";

    /**
     * @param NOTE 特别说明
     */
    public static final String NOTE = "note";

    /**
     * @param SIGN 个性签名
     */
    public static final String SIGN = "sign";

    /**
     * @param ADDRESS 地址
     */
    public static final String ADDRESS = "address";
    /**
     * @param SINE 账号
     */
    public static final String SINE = "sine";
    /**
     * @param HOBBY 爱好
     */
    public static final String HOBBY = "hobby";
    /**
     * 职业
     */
    public static final String PROFESSION = "profession";
    /**
     * 小头像地址
     */
    public static final String SMALLHEADPICPATH = "smallHeadPicPath";
    /**
     * 头像地址
     */
    public static final String HEADPICPATH = "headPicPath";
    /**
     * 情感状况
     */
    public static final String AFFECTIVE = "affective";
    /**
     * 年龄
     */
    public static final String AGE = "age";
    /**
     * 图片ID
     */
    public static final String PICTURE_ID = "pictureId";
    /**
     * 拨打的电话号码(JS调用)
     */
    public static final String PHONE = "phone";

    /**
     * 用户类型
     * 0 游客类型 visitor
     * 1业主类型
     * 2租客类型
     * 3家人类型
     */
    public static final String USERTYPE = "userType";
    public static final String VISITOR = "0";
    public static final String OWNER = "1";
    public static final String TENANT = "2";
    public static final String FAMILY = "3";
    /**
     * 做权限操作的时候需要这个字段
     * 当前用户类型
     * 0 游客类型l
     * 1业主类型
     * 2租客类型
     * 3家人类型
     */
    public static final String CUR_USERTYPE = "curusertype";


    /**
     * 消息开关状态
     * 系统信息设置0关，1开
     * 物业信息设置0关，1开
     * 甜甜圈信息设置0关，1开
     * 法律咨询信息设置0关，1开
     */
    public static final String ALLPUSH = "allpush";
    public static final String SYSMSGREMIND = "sysMsgRemind";
    public static final String PSMSGREMIND = "psMsgRemind";
    public static final String SERVICEMSGREMIND = "serviceMsgRemind";
    public static final String LEGGALMSGREMIND = "legalMsgRemind";

    public final static int USER_TYPE_TENEMENT = 1;
    /**
     * 物管集团工作人员
     **/
    public final static int USER_TYPE_WGJT = 2;
    /**
     * 小区工作人员
     **/
    public final static int USER_TYPE_XQ = 3;
    /**
     * 商户
     */
    public final static int USER_TYPE_BUSINESS = 4;
    /**
     * 社区运营人员
     */
    public final static int USER_TYPE_SQYY = 5;
    /**
     * 用户ID
     */
    public static final String USERID = "userId";

    /**
     * 商城用户ID
     */
    public static final String SHOP_USER_ID = "shopUserId";
    public static final String TIE_COUNT = "tie_count";
    //登录状态
    public static final String LOGIN_STATUS = "login_status";
    public static final String PAY_STATUS = "payStatus";
    public static final String IS_TEST = "isTest";

    //屏幕亮度
    public static final String SCREEN_BRIGHTNESS = "screenBrightness";

    /**
     * 用于判断是否是进入应用的第一次进入首页
     **/
    public static boolean isfirst = true;


    /*public static final String SHARE = "localinfo";
    public static final String KEY_ISSAVE = "savepass" ;
    public static final String KEY_PASS = "pass";
    public static final String KEY_INPUTPASS = "inputpass";

    public static final String KEY_SELSIP = "selsip";
    public static final String KEY_DOORNUM = "doornum";
    public static final String KEY_DOORNAME = "doorname";
    public static final String KEY_DOORSIP = "doorsip";

    public static final float  MAX_EXPOSURE_COMPENSATION = 1.5f;
    public static final float  MIN_EXPOSURE_COMPENSATION = 0.0f;

    public static final long   AUTO_FOCUS_INTERVAL_MS = 2000L;
    public static final Collection<String> FOCUS_MODES_CALLING_AF;
      static {
        FOCUS_MODES_CALLING_AF = new ArrayList<String>(2);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_AUTO);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_MACRO);
      }
    public static final int   MIN_PREVIEW_PIXELS = 470 * 320; // normal screen
    public static final int   MAX_PREVIEW_PIXELS = 1280 * 800;
    public static final Pattern COMMA_PATTERN = Pattern.compile(",");*/
    public static final String MAIN_COPY_URL = "/data/data/com.zhjl.qihao.activity/";
    public static final String TOUC_URL = "touchstyle";
    public static final String COPY_URL = "/data/data/com.zhjl.qihao.activity/touchstyle/";
    public static final String MORE_URL = "phonehtml";
    public static final String COPYMOR_URL = "/data/data/com.zhjl.qihao.activity/phonehtml/";
    public static final String INDEX_URL = "index";
    public static final String COPYINDEX_URL = "/data/data/com.zhjl.qihao.activity/index/";

    public static final String JAVASCRIPT_URL = "javascripts";
    public static final String COPYJAVASCRIPT_URL = "/data/data/com.zhjl.qihao.activity/javascripts/";

    public static final String SENDDISARMORDER_URL = "/bcportal/sendDisarmOrder.html";

    public static final String test_ip = "http://192.168.2.53:8080";

    public static final String ORDERSEARCHMSG = "orderSearchMsg";

    public static final String GETALLADDRESS_URL = "/appInterface.php?m=user&s=getMyAddress&version=3.0.1";
    public static final String DELETEADDRESS_URL = "/appInterface.php?m=user&s=getMyAddress&version=3.0.1";
    public static final String ADDADDRESS_URL = "/appInterface.php?m=user&s=add_udp_Address&version=3.0";
    public static final String GROUPBUY_URL = "/appInterface.php?m=groupbuy&s=index&version=3.0.1";
    public static final String GROUPBUY_FEEDBACK_URL = "/appInterface.php?m=groupbuy&s=group_feedback&version=3.0.1";
    public static final String GROUPBUY_GOODS_DETAIL_URL = "/appInterface.php?m=groupbuy&s=group_detail&version=3.0.1";
    public static final String GROUPBUY_COLLECT_GOODS_URL = "/appInterface.php?m=sns&s=collectGoods&version=3.0.1";
    public static final String GROUPBUY_GOODS_ORDER_URL = "/appInterface.php?m=order&s=group_confirm_order&version=3.0.1";

    /**
     * 我的订单
     */
    //退款/申请售后
    public static final String AFTER_SALE_URL = "/appInterface.php?m=order&s=order_return_search&version=3.0";
    //全部订单
    public static final String TOTAL_ORDER_URL = "/appInterface.php?m=order&s=order_search&version=3.0";
    //申请退货退款
    public static final String APPLY_REFUND_GOODS_URL = "/appInterface.php?m=order&s=all_apply&version=3.0";
    //申请退款
    public static final String APPLY_REFUND_MONEY_URL = "/appInterface.php?m=order&s=all_apply&version=3.0";
    //查看物流
    public static final String CHECK_LOGISTICS_URL = "/apkInterface.php?m=product&s=show_logistics";
    //评价订单
    public static final String EVALUATE_ORDER_URL = "/appInterface.php?m=order&s=getOrderInfo&version=3.0";
    //提交评价订单
    public static final String SUBMIT_EVALUATE_ORDER_URL = "/appInterface.php?m=order&s=comment&version=3.0";
    //显示物流公司
    public static final String SHOW_LOGISTICS_COMPANY = "/apkInterface.php?m=workbench&s=logistics_name";
    //提交物流公司
    public static final String SUBMIT_LOGISTICS_COMPANY = "/appInterface.php?m=order&s=all_apply&version=3.0";
    //云团购提交评价
    public static final String GROUP_SUBMIT_LOGISTICS_COMPANY = "/appInterface.php?m=order&s=group_comment&version=3.0";
    //取消订单
    public static final String CANCEL_ORDER_URL = "/appInterface.php?m=order&s=order_search&version=3.0";
    //订单详情
    public static final String ORDER_DETAIL_URL = "/appInterface.php?m=order&s=order_search&version=3.0";
    //搜索订单
    public static final String SEARCH_ORDER_URL = "/appInterface.php?m=order&s=search&version=3.0";

    /**
     * 租售-服务器参数
     */

    public static final String SERVICE_CODE = "serviceCode";
    public static final String AREA_PATH = "areaPath";
    public static final String PAGE_INDEX = "pageIndex";
    public static final String ROOM = "room";
    public static final String PRICE = "price";
    public static final String SEARCH_KEY = "searchKey";
    public static final String CREATE_SOURCE = "createSource";

    public static final String PROVINCENAME = "provinceName";
    public static final String TOWNSHIP_NAME = "townshipName";
    public static final String PAGE_SIZE = "pageSize";
    /**
     * 便利店
     */
    public static final String USER = "user";
    public static final String TYPE = "type";

    /**
     * 常用
     */
    public static final String MESSAGE = "message";
    public static final String DATE = "date";
    public static final String TOTAL_COST = "totalCost";
    public static final String ON_LINE_PAYMENT = "onlinePayment";
    public static final String ROOM_COST_LIST = "roomCostList";
    public static final String IS_CURRENT_MONTH = "isCurrentMonth";
    public static final String XIAO_QU_NAME = "xiaoQuName";
    public static final String UN_PAY_AMOUNT = "unPayAmount";
    public static final String COST_VO_LIST = "costVoList";
    public static final String PAY_MENT_STATUS = "paymentStatus";
    public static final String CAN_PAY_MENT = "canPayment";
    public static final String MONTHS_LIST = "monthsList";
    public static final String MONTH = "month";
    public static final String PROVINCE_ID = "provinceId";
    public static final String LIST = "list";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String CITY_ID = "cityId";
    public static final String TITLE = "title";
    public static final String ICON = "icon";

    public static final String TIMER = "timer";
    public static final String DETAILS = "details";
    public static final String FINANCIAL_RECORDS = "financial_records";


    /*** 摇钱树 */
    /**
     * --
     */
    public static final int MONEY_TREE_INIT = 0;
    /**
     * 进行中
     */
    public static final int MONEY_TREE_PROCESSING = 1;
    /**
     * 审核中
     */
    public static final int MONEY_TREE_UNDER_REVIEW = 2;
    /**
     * 还款中
     */
    public static final int MONEY_TREE_REPAYMENT_OF = 3;
    /**
     * 已还完
     */
    public static final int MONEY_TREE_YET_FINISHED = 4;
    /**
     * 已撤销
     */
    public static final int MONEY_TREE_REVOKED = 5;

    public static final String VERSION_TYPE = "versionType";
    public static final String STATU = "statu";
    public static final String RESULT_DISC = "resultDisc";
    public static final String FILTER = "filter";
    public static final String MOBILE = "mobile";


    public static final String TOKEN = "token";

    public static final String PASSWORD = "password";

    public static final String CODE = "code";
    /**
     * 是否绑定用户
     */
    public static final String HAVE_BIND_USER = "haveBindUser";

    public static final String THIRD_PARTY_VO = "thirdpartyVo";

    public static final String HAVE_PASSWORD = "havePassword";

    public static final String NOPASSWORD = "NoPassWord";

    public static final String JUMPTYPE = "jumpType";

    public static final String AREA_ID = "areaId";

    public static final String CITYS = "citys";

    public static final String AREA_NAME = "areaName";

    public static final String COMMUNITY_NAME = "communityName";

    public static final String TURE = "ture";

    public static final String FALSE = "false";

    /**
     * 历史缓存记录
     */
    public static final String HISTORY_STR = "history_strs";

    public static final String HISTORY = "history";

    public static final String IS_READ = "isRead";

    public static final String SYSTEM = "system";

    public static final String FORUM_NOTE_ID = "forumNoteId";

    public static final String NOTE_SOURCE = "noteSource";

    public static final String FLAG = "flag";

    public static final String BACKGROUND_PICTURE_ID = "backgroundPictureId";

    public static final String LABLE_NAME = "labelName";

    public static final String NULL = "null";

    public static final String SHOP_ID = "shop_id";

    public static final String SHOP_NAME = "shop_name";

    public static final String IS_Suround = "isSuround";

    public static final String SHOP_INFO = "shopinfo";


    public static final String TOTAL_NUMBER = "totalNumber";


    public static String PAY_TYPE = "pay_type";


    public final static String HEATH_BIRTHDAY_YEAR = "heath_birthday_year";
    public final static String HEATH_BIRTHDAY_MONTH = "headth_birthday_month";
    public final static String HEATH_BIRTHDAY_DAY = "headth_birthday_day";
    public final static String HEATH_BLOOD_TYPE = "headth_blood_type";
    public final static String HEATH_WEIGTH = "headth_weight";
    public final static String HEATH_HEIGHT = "headth_height";
    public static final String STEP_AIMS = "step_aims";
    public static final String HEALTH_STEP = "health_step";
    public static final String HEALTH_STEP_UPDATE_TIME = "health_step_update_time";
    public static final String HEALTH_STEP_DISTANCE = "health_step_distance";
    public static final String HEALTH_STEP_CARY_ROAD = "health_step_cary_road";

    public static final String HEALTH_SLEEP_TOTAL = "health_sleep_total";
    public static final String HEALTH_SLEEP_DEEP = "health_sleep_deep";
    public static final String HEALTH_SLEEP_LIGHT = "health_sleep_light";
    public static final String HEALTH_SLEEP_AWAKE = "health_sleep_awake";
    public static final String HEALTH_SLEEP_TIME = "health_sleep_time";
    public static final String HEALTH_SLEEP_UPDATE_TIME = "health_sleep_update_time";

    public static final String HEALTH_TT_WRISTBAND_CONNECT = "health_tt_wristband_connect";

    public static final String HEALTH_LY_WRISTBAND_ADDRESS = "health_ly_wristband_address";

}
