package com.project.mgjandroid.constants;

import android.os.Environment;

import com.project.mgjandroid.BuildConfig;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.utils.DipToPx;

import java.io.File;

public class Constants {
    public static final String WE_CHAT_APP_ID = App.getInstance().getString(R.string.wechat_appid);

    public static final String URL_HOME_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/merchant/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_SECOND_HAND_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/secondhand/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_RENT_HOUSE_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/houselease/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_EDUCATION_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/education/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_HOME_MAKING_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/homemaking/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_REPAIR_MAIN = "http://" + (BuildConfig.IS_DEBUG ? "192.168.2.212:7777" : "123.56.15.86") + "/repair/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_BBS_MAIN = BuildConfig.IS_DEBUG ? "http://120.24.16.64/bbs/userClient?m=" : "http://123.56.15.86/bbs/userClient?m=";
    //	public static final String URL_HOME_MAIN = "http://" + "192.168.199.212:8080" + "/merchant/userClient?m="; //120.24.16.64正式123.56.15.86
    public static final String URL_IMAGE_FRONT = "http://7xpvkm.com1.z0.glb.clouddn.com/";

    public static final String URL_fIND_TAKE_AWAY_MERCHANT = URL_HOME_MAIN + "findTakeAwayMerchant";
    public static final String URL_SHOW_MERCHANT_TAKE_AWAY_MENU = URL_HOME_MAIN + "showMerchantTakeAwayMenu";
    //	public static final String URL_FIND_MERCHANT_TCOMENTS = URL_HOME_MAIN + "findMerchantTComents";
    //	public static final String URL_ORDER_LIST = URL_HOME_MAIN + "getOrderList";
    public static final String URL_ORDER_DETAIL = URL_HOME_MAIN + "findTOrderById";
    //	public static final String URL_COMMERCIAL = URL_HOME_MAIN + "getCommercialMainPage";
    //	public static final String URL_COMMERCIAL_COMMENT_LIST = URL_HOME_MAIN + "getCommercialCommentList";
    //	public static final String URL_GOODS_DETAIL = URL_HOME_MAIN + "getProductDetail";
    //发送登录验证码
    public static final String URL_GET_MSG_CODE = URL_HOME_MAIN + "sendLoginSms";
    //验证码登录
    public static final String URL_SMS_LOGIN = URL_HOME_MAIN + "checkLoginCode";
    //获取个人地址
    public static final String URL_GET_ADDRESS = URL_HOME_MAIN + "findUserAddress";
    //保存个人地址
    public static final String URL_EDIT_ADDRESS = URL_HOME_MAIN + "editUserAddress";
    //订单预览
    public static final String URL_GET_ORDER_PREVIEW = URL_HOME_MAIN + "orderPreview";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "maguanjia";
    public static final String IMG_PATH = BASE_PATH + File.separator + "image";
    public static final String IMAGE_URL_END_THUMBNAIL = "?imageView2/2/h/" + DipToPx.dip2px(App.getInstance(), 150) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 44) + "/h/" + DipToPx.dip2px(App.getInstance(), 44) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 88) + "/h/" + DipToPx.dip2px(App.getInstance(), 88) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_EVALUATE = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 30) + "/h/" + DipToPx.dip2px(App.getInstance(), 30) + "/interlace/1";
    public static final String PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_BANNER = "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), 360) + "/h/" + DipToPx.dip2px(App.getInstance(), 280) + "/interlace/1";

    public static String getEndThumbnail(int width, int height) {
        return "?imageView2/2/w/" + DipToPx.dip2px(App.getInstance(), width) + "/h/" + DipToPx.dip2px(App.getInstance(), height) + "/interlace/1";
    }

    //删除地址
    public static final String URL_DELETE_ADDRESS = URL_HOME_MAIN + "delUserAddress";
    //订单提交
    public static final String URL_SUBMIT_ORDER = URL_HOME_MAIN + "orderSubmit";
    //查询订单
    public static final String URL_GET_ORDER_LIST = URL_HOME_MAIN + "findUserTOrders";
    //查询用户余额
    public static final String URL_CHECK_EXTRA_MONEY = URL_HOME_MAIN + "findUserAccount";
    //查询用户账户
    public static final String URL_FIND_USER_CENTER = URL_HOME_MAIN + "findUserCenter";
    //更改用户名
    public static final String URL_CHANGE_USERNAME = URL_HOME_MAIN + "updateUserName";
    //更改用户头像
    public static final String URL_CHANGE_HEAD = URL_HOME_MAIN + "updateUserHeaderImg";
    //获取七牛Token
    public static final String URL_GET_QINIU_TOKEN = URL_HOME_MAIN + "obtainQiniuUploadToken";
    //删除订单
    public static final String URL_DELETE_ORDER = URL_HOME_MAIN + "delTOrderById";
    //app初始化
    public static final String URL_INIT_APP = URL_HOME_MAIN + "findAppUserByToken";
    //退出登录
    public static final String URL_EXIT_OUT = URL_HOME_MAIN + "logout";
    //查询订单详情
    //	public static final String URL_FIND_ORDER_DETAIL = URL_HOME_MAIN + "findTOrderById";
    //首页分类筛选
    public static final String URL_GET_CATEGORY = URL_HOME_MAIN + "findTagCategory";
    //首页轮播图
    public static final String URL_FIND_TBANNER = URL_HOME_MAIN + "findTBanner";
    //首页分类导航
    public static final String URL_FIND_PRIMARY_CATEGORY_LIST = URL_HOME_MAIN + "findPrimaryCategoryList";
    //更多分类导航
    public static final String URL_FIND_MORE_PRIMARY_CATEGORY_LIST = URL_HOME_MAIN + "findMorePrimaryCategoryList";
    //筛选按钮
    public static final String URL_FILTER = URL_HOME_MAIN + "findMerchantFilter";
    //第三方登录
    public static final String URL_THIRD_PARTY_LOGIN = URL_HOME_MAIN + "thirdLogin";
    //查询用户第三方绑定情况
    public static final String URL_CHECK_THIRD_LOGIN = URL_HOME_MAIN + "findUserThirdLogin";
    //解绑第三方
    public static final String URL_UNBIND_THIRD_LOGIN = URL_HOME_MAIN + "thirdLoginUnBind";
    //绑定第三方
    public static final String URL_BIND_THIRD_LOGIN = URL_HOME_MAIN + "thirdLoginBind";
    //支付宝支付
    //	public static final String URL_ALIPAY = URL_HOME_MAIN + "alipayApp";
    //余额支付
    public static final String URL_EXTRA = URL_HOME_MAIN + "balancePay";
    //获取能用的支付方式
    public static final String URL_PAY_WAYS = URL_HOME_MAIN + "findChargeTypes";
    //评价订单
    public static final String URL_EVALUATE_ORDER = URL_HOME_MAIN + "createOrderComments";
    //商家评价
    public static final String URL_MERCHANT_EVALUATE = URL_HOME_MAIN + "findMerchantComments";
    //查询商品评价
    public static final String URL_GOODS_EVALUATE = URL_HOME_MAIN + "findGoodsComments";
    //展示商家评价
    public static final String URL_SHOW_EVALUATE = URL_HOME_MAIN + "findMerchantInfo";
    //ping++ 充值
    public static final String URL_ALIPAY_CHARGE = URL_HOME_MAIN + "pingxxCharge";
    //ping++ 支付
    public static final String URL_PING_PAY = URL_HOME_MAIN + "pingxxPay";
    //查询收支明细
    public static final String URL_ACCOUNT_DETAILS = URL_HOME_MAIN + "findUserAccountDetails";
    //appLaunch
    public static final String URL_APP_LAUNCH = URL_HOME_MAIN + "appLaunch";
    /**
     * 收藏商家
     */
    public static final String URL_CREAT_USER_FAVORITES = URL_HOME_MAIN + "createUserFavorites";
    /**
     * 查询收藏商家列表
     */
    public static final String URL_FIND_USER_FAVORITES = URL_HOME_MAIN + "findUserFavorites";
    /**
     * 取消收藏
     */
    public static final String URL_CANCEL_USER_FAVORITES = URL_HOME_MAIN + "cancelUserFavorites";
    /**
     * 查询默认地址
     */
    public static final String URL_FIND_DEFAULT_ADDRESS_LIST = URL_HOME_MAIN + "findDefaultAddressList";
    //搜索
    public static final String URL_SEARCH = URL_HOME_MAIN + "searchTakeAwayMerchant";
    //热搜
    public static final String URL_HOT_SEARCH = URL_HOME_MAIN + "findHotSearch";
    //用户反馈
    public static final String URL_FEED_BACK = URL_HOME_MAIN + "userFeedback";
    /**
     * 查询用户银行卡信息
     */
    public static final String URL_FIND_USER_DRAW_CASH_BANK_LIST = URL_HOME_MAIN + "findUserDrawCashBankList";
    /**
     * 删除用户银行卡信息
     */
    public static final String URL_DEL_USER_DRAW_CASH_BANK = URL_HOME_MAIN + "delUserDrawCashBank";
    /**
     * 查询开户行类型
     */
    public static final String URL_FIND_BANK_LIST = URL_HOME_MAIN + "findTBankList";
    //申请提现
    public static final String URL_DRAW_CASH = URL_HOME_MAIN + "userDrawCash";
    //提现限制
    public static final String URL_CASH_LIMIT = URL_HOME_MAIN + "findUserDrawCashConfine";
    /**
     * 查询我的未使用红包列表
     *
     * date 当前时间(不传查未过期的，传查已过期的)
     */
    public static final String URL_FIND_USER_RED_BAG_LIST = URL_HOME_MAIN + "findUserRedBagList";
    /**
     * 用户下单查询用户可用红包
     */
    public static final String URL_FILTER_USABLE_RED_BAG_LIST = URL_HOME_MAIN + "filterUsableRedBagList";

    //所有banner图
    public static final String URL_INFOMATION_PUBLISH_BANNER = URL_HOME_MAIN + "findBannerLocations";

    /**
     * 获取社区发布信息短信验证码
     */
    public static final String URL_SEND_RELEASE_INFOMATION_SMS = URL_BBS_MAIN + "sendReleaseInformationSms";
    /**
     * 验证社区发布信息短信验证码
     */
    public static final String URL_CHECK_RELEASE_INFOMATION_SMS = URL_BBS_MAIN + "checkReleaseInformationCode";
    /**
     * 获取推荐社区所有信息列表
     */
    public static final String URL_FIND_ALL_BBS_INFOMATION_LIST = URL_BBS_MAIN + "findAllBbsInformationList";
    /**
     * 社区发布信息
     */
    public static final String URL_RELAESE_INFOMATION = URL_BBS_MAIN + "releaseInformation";
    /**
     * 社区删除信息
     */
    public static final String URL_DELETE_INFOMATION = URL_BBS_MAIN + "deleteInformation";
    /**
     * 获取我的社区所有信息列表
     */
    public static final String URL_FIND_USER_BBS_INFOMATION_LIST = URL_BBS_MAIN + "findUserBbsInformationList";
    /**
     * 获取社区标签列表
     */
    public static final String URL_FIND_BBS_CATEGORY_LIST = URL_BBS_MAIN + "findBbsCategoryList";
    /**
     * 获取社区七牛token
     */
    public static final String URL_OBTIAN_QINIU_TOKEN = URL_BBS_MAIN + "obtainQiniuUploadToken";
    /**
     * 社区举报
     */
    public static final String URL_INFORM_INFOMATION = URL_BBS_MAIN + "informInformation";

    public static final String URL_UPDATE_APP = URL_HOME_MAIN + "findMaxAppVersion";

    public static final String URL_FULL_TIME_ALL_TAG = URL_HOME_MAIN + "findRecruitPositionCategoryList";
    //发布招聘信息
    public static final String URL_CREATE_JOB = URL_HOME_MAIN + "createPositionRecruitInformation";

    /**
     * 获取短信验证码
     */
    public static final String URL_GET_CODE_JOB = URL_HOME_MAIN + "sendReleaseInformationSms";

    public static final String URL_FIND_RECRUIT_HOT_POSITION_CATEGORY_LIST = URL_HOME_MAIN + "findRecruitHotPositionCategoryList";
    /**
     * 查询招聘信息
     */
    public static final String URL_FIND_POSITION_RECRUIT_INFORMATION_LIST = URL_HOME_MAIN + "findPositionRecruitInformationList";
    /**
     * 查询用户招聘信息
     */
    public static final String URL_FIND_USER_POSITION_RECRUIT_INFORMATION_LIST = URL_HOME_MAIN + "findUserPositionRecruitInformationList";
    /**
     * 刷新招聘信息
     */
    public static final String URL_REFRESH_POSITION_RECRUIT_INFORMATION = URL_HOME_MAIN + "refreshPositionRecruitInformation";
    /**
     * 删除招聘信息
     */
    public static final String URL_DELETE_POSITION_RECRUIT_INFORMATION = URL_HOME_MAIN + "deletePositionRecruitInformation";

    public static final String URL_HOT_CITY = URL_HOME_MAIN + "findPositionRecruitHotCityList";

    //获得可转发的红包
    public static final String URL_GET_EXTRA_RED_BAG = URL_HOME_MAIN + "getMerchantRedBagByOrderId";
    /**
     * 领取商家红包（进店红包、新用户红包）
     */
    public static final String URL_GET_MERCHANT_RED_BAG = URL_HOME_MAIN + "getMerchantRedBag";

    //获取我的发布分类
    public static final String URL_USER_PUBLISH = URL_HOME_MAIN + "findInformationCategoryList";

    /**
     * 创建二手信息
     */
    public static final String URL_CREATE_SECOND_HAND_INFOMATION = URL_SECOND_HAND_MAIN + "createSecondhandInformation";
    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_OBTAIN_QINIU_TOKEN_SECOND_HAND = URL_SECOND_HAND_MAIN + "obtainQiniuUploadToken";
    /**
     * 发送验证码
     */
    public static final String URL_SEND_SMS_SECOND_HAND = URL_SECOND_HAND_MAIN + "sendReleaseInformationSms";

    //获取二手市场的分类
    public static final String URL_GET_HOT_SECOND_HAND_CATEGORY = URL_SECOND_HAND_MAIN + "findHotSecondhandCategoryList";
    //获取二手市场的列表
    public static final String URL_GET_SECOND_HAND_LIST = URL_SECOND_HAND_MAIN + "findSecondhandInformationList";
    /**
     * 查询二手分类
     */
    public static final String URL_FIND_SECOND_HAND_CATEGORY_LIST = URL_SECOND_HAND_MAIN + "findSecondhandCategoryList";

    //查询用户二手信息
    public static final String URL_SEARCH_USER_SECOND_HAND = URL_SECOND_HAND_MAIN + "findUserSecondhandInformationList";

    //根据id查询二手信息
    public static final String URL_SEARCH_SECOND_HAND_BY_ID = URL_SECOND_HAND_MAIN + "findSecondhandInformation";
    //刷新二手信息
    public static final String URL_REFRESH_SECOND_ITEM = URL_SECOND_HAND_MAIN + "refreshSecondhandInformation";
    //删除二手信息
    public static final String URL_DELETE_SECOND_ITEM = URL_SECOND_HAND_MAIN + "deleteSecondhandInformation";
    /**
     * 举报二手信息
     */
    public static final String URL_REPORT_SECOND_HAND_INFOMATION = URL_SECOND_HAND_MAIN + "reportSecondhandInformation";
    /**
     * 再来一单
     */
    public static final String URL_AGAIN_ORDER_PREVIEW = URL_HOME_MAIN + "againOrderPreview";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_RENT_HOUSE_OBTAIN_QINIU_TOKEN = URL_RENT_HOUSE_MAIN + "obtainQiniuUploadToken";
    //查询租房热门分类
    public static final String URL_RENT_HOUSE_HOT_CATEGORY_LIST = URL_RENT_HOUSE_MAIN + "findHotHouseLeaseCategoryList";
    //查询租房更多分类
    public static final String URL_RENT_HOUSE_MORE_CATEGORY_LIST = URL_RENT_HOUSE_MAIN + "findHouseLeaseCategoryList";
    //创建租房信息
    public static final String URL_CREATE_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "createHouseLeaseInformation";
    //查询租房信息
    public static final String URL_RENT_HOUSE_LIST = URL_RENT_HOUSE_MAIN + "findHouseLeaseInformationList";
    //查询用户租房信息
    public static final String URL_USER_RENT_HOUSE_LIST = URL_RENT_HOUSE_MAIN + "findUserHouseLeaseInformationList";
    //通过id查信息详情
    public static final String URL_RENT_HOUSE_DETAIL = URL_RENT_HOUSE_MAIN + "findHouseLeaseInformation";
    //刷新租房信息
    public static final String URL_REFRESH_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "refreshHouseLeaseInformation";
    //删除租房信息
    public static final String URL_DELETE_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "deleteHouseLeaseInformation";
    //举报租房信息
    public static final String URL_REPORT_RENT_HOUSE_MESSAGE = URL_RENT_HOUSE_MAIN + "reportHouseLeaseInformation";
    //发送租房验证码
    public static final String URL_SEND_CODE_RENT_HOUSE_DETAIL = URL_RENT_HOUSE_MAIN + "sendReleaseInformationSms";
    //查询租房热门城市
    public static final String URL_FIND_HOT_CITY_RENT_HOUSE = URL_RENT_HOUSE_MAIN + "findHouseLeaseHotCityList";


    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_EDUCATION_OBTAIN_QINIU_TOKEN = URL_EDUCATION_MAIN + "obtainQiniuUploadToken";
    //查询家教热门分类
    public static final String URL_EDUCATION_HOT_CATEGORY_LIST = URL_EDUCATION_MAIN + "findHotEducationCategoryList";
    //查询家教更多分类
    public static final String URL_EDUCATION_MORE_CATEGORY_LIST = URL_EDUCATION_MAIN + "findEducationCategoryList";
    //创建家教信息
    public static final String URL_CREATE_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "createEducationInformation";
    //查询家教信息
    public static final String URL_EDUCATION_LIST = URL_EDUCATION_MAIN + "findEducationInformationList";
    //查询用户家教信息
    public static final String URL_USER_EDUCATION_LIST = URL_EDUCATION_MAIN + "findUserEducationInformationList";
    //通过id查信息详情
    public static final String URL_EDUCATION_DETAIL = URL_EDUCATION_MAIN + "findEducationInformation";
    //刷新家教信息
    public static final String URL_REFRESH_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "refreshEducationInformation";
    //删除家教信息
    public static final String URL_DELETE_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "deleteEducationInformation";
    //举报家教信息
    public static final String URL_REPORT_EDUCATION_MESSAGE = URL_EDUCATION_MAIN + "reportEducationInformation";
    //发送家教验证码
    public static final String URL_SEND_CODE_EDUCATION_DETAIL = URL_EDUCATION_MAIN + "sendReleaseInformationSms";
    //查询家教教师身份
    public static final String URL_FIND_EDUCATION_TEACHER_TYPE = URL_EDUCATION_MAIN + "findEducationTeacherType";
    //查询家教辅导阶段
    public static final String URL_FIND_EDUCATION_TUTORSHIP_STAGE = URL_EDUCATION_MAIN + "findEducationTutorshipStage";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_HOME_MAKING_OBTAIN_QINIU_TOKEN = URL_HOME_MAKING_MAIN + "obtainQiniuUploadToken";
    //查询家政热门分类
    public static final String URL_HOME_MAKING_HOT_CATEGORY_LIST = URL_HOME_MAKING_MAIN + "findHotHomemakingCategoryList";
    //查询家政更多分类
    public static final String URL_HOME_MAKING_MORE_CATEGORY_LIST = URL_HOME_MAKING_MAIN + "findHomemakingCategoryList";
    //创建家政信息
    public static final String URL_CREATE_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "createHomemakingInformation";
    //查询家政信息
    public static final String URL_HOME_MAKING_LIST = URL_HOME_MAKING_MAIN + "findHomemakingInformationList";
    //查询用户家政信息
    public static final String URL_USER_HOME_MAKING_LIST = URL_HOME_MAKING_MAIN + "findUserHomemakingInformationList";
    //通过id查信息详情
    public static final String URL_HOME_MAKING_DETAIL = URL_HOME_MAKING_MAIN + "findHomemakingInformation";
    //刷新家政信息
    public static final String URL_REFRESH_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "refreshHomemakingInformation";
    //删除家政信息
    public static final String URL_DELETE_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "deleteHomemakingInformation";
    //举报家政信息
    public static final String URL_REPORT_HOME_MAKING_MESSAGE = URL_HOME_MAKING_MAIN + "reportHomemakingInformation";
    //发送家政验证码
    public static final String URL_SEND_CODE_HOME_MAKING_DETAIL = URL_HOME_MAKING_MAIN + "sendReleaseInformationSms";

    /**
     * 获取七牛上传文件所需要的uploadToken
     */
    public static final String URL_REPAIR_OBTAIN_QINIU_TOKEN = URL_REPAIR_MAIN + "obtainQiniuUploadToken";
    //查询维修热门分类
    public static final String URL_REPAIR_HOT_CATEGORY_LIST = URL_REPAIR_MAIN + "findHotrepairCategoryList";
    //查询维修更多分类
    public static final String URL_REPAIR_MORE_CATEGORY_LIST = URL_REPAIR_MAIN + "findRepairCategoryList";
    //创建维修信息
    public static final String URL_CREATE_REPAIR_MESSAGE = URL_REPAIR_MAIN + "createRepairInformation";
    //查询维修信息
    public static final String URL_REPAIR_LIST = URL_REPAIR_MAIN + "findRepairInformationList";
    //查询用户维修信息
    public static final String URL_USER_REPAIR_LIST = URL_REPAIR_MAIN + "findUserRepairInformationList";
    //通过id查信息详情
    public static final String URL_REPAIR_DETAIL = URL_REPAIR_MAIN + "findRepairInformation";
    //刷新维修信息
    public static final String URL_REFRESH_REPAIR_MESSAGE = URL_REPAIR_MAIN + "refreshRepairInformation";
    //删除维修信息
    public static final String URL_DELETE_REPAIR_MESSAGE = URL_REPAIR_MAIN + "deleteRepairInformation";
    //举报维修信息
    public static final String URL_REPORT_REPAIR_MESSAGE = URL_REPAIR_MAIN + "reportRepairInformation";
    //发送维修验证码
    public static final String URL_SEND_CODE_REPAIR_DETAIL = URL_REPAIR_MAIN + "sendReleaseInformationSms";

}
