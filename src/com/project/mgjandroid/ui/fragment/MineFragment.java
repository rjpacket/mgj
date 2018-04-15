package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.AddressManageActivity;
import com.project.mgjandroid.ui.activity.BalanceOperateActivity;
import com.project.mgjandroid.ui.activity.MerchantCollectionActivity;
import com.project.mgjandroid.ui.activity.MoreSettingActivity;
import com.project.mgjandroid.ui.activity.MyBankCardActivity;
import com.project.mgjandroid.ui.activity.MyPublishCategoryActivity;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.activity.NoticeActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.activity.UserInfoActivity;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的
 *
 * @author jian
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private static final int LOGIN_IN = 303;
    public static final int LOGIN_IN_SUCCESS = 304;
    private static final int TO_MORE_SETTING = 305;
    public static final int EXIT_APP_SUCCESS = 306;
    private static final int TO_EVALUATE = 307;
    private static final int TO_BALANCE_OPERATE = 308;
    protected Activity mActivity;
    protected View view;
    private RoundImageView userAvatar;
    private TextView tvName;
    private TextView tvBalance;
    private TextView tvBalanceUnit;
    private TextView tvRedbag;
    private TextView tvRedBagUnit;
    private TextView tvBankCard;
    private TextView tvBankCardUnit;
    private TextView tvMobile;
    private boolean firstIn;
    private ImageView mobileIcon;

    private int second = 60;
    private CustomDialog dialog;
    private static MineFragment fragment;

    public static MineFragment newInstance(){
        if(fragment == null){
            fragment = new MineFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.mine_fragment, container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshPage();
    }

    private void initView() {
        RelativeLayout tvAddressManage = ViewFindUtils.find(view, R.id.mine_fragment_address_manager);
        RelativeLayout rlUserInfo = ViewFindUtils.find(view, R.id.mine_fragment_layout_to_user_info);
        userAvatar = ViewFindUtils.find(view, R.id.mine_fragment_avatar);
        TextView tvLogin = ViewFindUtils.find(view, R.id.mine_fragment_tv_login);
        tvName = ViewFindUtils.find(view, R.id.mine_fragment_username);
        tvBalance = ViewFindUtils.find(view, R.id.mine_fragment_balance);
        tvBalanceUnit = ViewFindUtils.find(view, R.id.mine_fragment_balance_unit);
        tvRedbag = ViewFindUtils.find(view, R.id.mine_fragment_red_bag_count);
        tvRedBagUnit = ViewFindUtils.find(view, R.id.mine_fragment_red_bag_unit);
        tvBankCard = ViewFindUtils.find(view, R.id.mine_fragment_bank_card_count);
        tvBankCardUnit = ViewFindUtils.find(view, R.id.mine_fragment_bank_card_unit);
        tvMobile = ViewFindUtils.find(view, R.id.mine_fragment_mobile);
        mobileIcon = ViewFindUtils.find(view,R.id.mine_fragment_mobile_icon);
        RelativeLayout moreSetting = ViewFindUtils.find(view, R.id.mine_fragment_setting);
        ImageView notice = ViewFindUtils.find(view, R.id.mine_fragment_notice);
        RelativeLayout rlEvaluate = ViewFindUtils.find(view, R.id.mine_fragment_score);
        RelativeLayout rlFeedBack = ViewFindUtils.find(view,R.id.mine_fragment_feed_back);
        LinearLayout llBalanceOperate = ViewFindUtils.find(view, R.id.mine_fragment_balance_operate);
        LinearLayout llRedPackage = ViewFindUtils.find(view,R.id.red_package);
        LinearLayout llMyCards = ViewFindUtils.find(view, R.id.mine_cards);
        RelativeLayout rlMySave = ViewFindUtils.find(view, R.id.mine_fragment_my_save);
        RelativeLayout rlServerCenter = ViewFindUtils.find(view, R.id.mine_fragment_serve_center);
        RelativeLayout rlAboutUs = ViewFindUtils.find(view, R.id.mine_fragment_about);
        RelativeLayout rlPublish = ViewFindUtils.find(view, R.id.mine_fragment_my_publish);

//        tvLogin.setOnClickListener(this);
        rlUserInfo.setOnClickListener(this);
        tvAddressManage.setOnClickListener(this);
        moreSetting.setOnClickListener(this);
        notice.setOnClickListener(this);
        rlEvaluate.setOnClickListener(this);
        llBalanceOperate.setOnClickListener(this);
        llRedPackage.setOnClickListener(this);
        llMyCards.setOnClickListener(this);
        rlMySave.setOnClickListener(this);
        rlServerCenter.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlFeedBack.setOnClickListener(this);
        rlPublish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_fragment_address_manager:
                if (CommonUtils.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, AddressManageActivity.class));
                }
                break;
            case R.id.mine_fragment_tv_login:
                startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), LOGIN_IN);
                break;
            case R.id.mine_fragment_layout_to_user_info:
                if (CommonUtils.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, UserInfoActivity.class));
                }
                break;
            case R.id.mine_fragment_setting:
                startActivityForResult(new Intent(mActivity, MoreSettingActivity.class), TO_MORE_SETTING);
                break;
            case R.id.mine_fragment_notice:
                if(CommonUtils.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, NoticeActivity.class));
                }
                break;
            case R.id.mine_fragment_score:
//                if(hasAnyMarketInstalled(mActivity)) {
                    Intent intent1 = new Intent("android.intent.action.VIEW");
                    intent1.setData(Uri.parse("market://details?id=" + App.getContext().getPackageName()));
                    startActivity(intent1);
//                }else{
//                    ToastUtils.displayMsg("手机上没有应用市场",mActivity);
//                }
                break;
            case R.id.mine_fragment_feed_back:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + ActivitySchemeManager.URL_FEED_BACK);
                break;
            case R.id.mine_fragment_balance_operate:
//				因为需判定登录状态
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, BalanceOperateActivity.class);
                    startActivityForResult(intent, TO_BALANCE_OPERATE);
                }
                break;
            case R.id.mine_fragment_my_save:
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MerchantCollectionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_serve_center:
                showDialog();
                break;
            case R.id.red_package:
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyRedBagActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_about://我是商家
                ToastUtils.displayMsg("暂不支持，敬请期待", mActivity);
                break;
            case R.id.mine_cards:
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyBankCardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_fragment_my_publish:
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, MyPublishCategoryActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void showDialog() {
        if (dialog != null) {
            dialog.show();
            return;
        }
        dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(getString(R.string.withdraw_phone), getString(R.string.sale_phone))));
                startActivity(intent);
                dialog.dismiss();
            }
            @Override
            public void onExit() {
                dialog.dismiss();
            }
        },"呼叫","取消","联系客服","请拨打客服电话：" + getString(R.string.sale_phone));

        dialog.show();
    }

    /**
     * 获取余额、红包、银行卡信息
     */
    private void getExtraMoney() {
        VolleyOperater<UserAccountModel> operater = new VolleyOperater<UserAccountModel>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_CENTER, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    UserAccountModel userAccountModel = (UserAccountModel) obj;
                    UserAccountModel.ValueEntity value = userAccountModel.getValue();
                    setAccountView(value);
                }
            }
        }, UserAccountModel.class);
    }

    private void setAccountView(UserAccountModel.ValueEntity value) {
        if(value == null) return;

        if (value.getBalance() != null && value.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            tvBalance.setTextColor(getResources().getColor(R.color.mine_number_color_red));
            tvBalanceUnit.setTextColor(getResources().getColor(R.color.mine_number_color_red));
        } else {
            tvBalance.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
            tvBalanceUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
        }
        if (value.getUserBankCount() > 0) {
            tvBankCard.setTextColor(getResources().getColor(R.color.mine_number_color_red));
            tvBankCardUnit.setTextColor(getResources().getColor(R.color.mine_number_color_red));
        }else {
            tvBankCard.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
            tvBankCardUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
        }
        if (value.getRedBagCount() > 0) {
            tvRedbag.setTextColor(getResources().getColor(R.color.mine_number_color_red));
            tvRedBagUnit.setTextColor(getResources().getColor(R.color.mine_number_color_red));
        }else {
            tvRedbag.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
            tvRedBagUnit.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
        }

        if(value.getBalance() != null){
            tvBalance.setText(StringUtils.BigDecimal2Str(value.getBalance()));
        }else {
            tvBalance.setText("0");
        }
        tvBankCard.setText(String.valueOf(value.getUserBankCount()));
        tvRedbag.setText(String.valueOf(value.getRedBagCount()));
    }

    public void refreshPage() {
        if (App.isLogin()) {
//            tvLogin.setVisibility(View.INVISIBLE);
//            moreSetting.setVisibility(View.VISIBLE);
//            notice.setVisibility(View.VISIBLE);
            AppUserEntity userInfo = App.getUserInfo();
            String name = userInfo.getName();
            String mobile = userInfo.getMobile();
            if (name == null) {
                tvName.setText(mobile);
            } else {
                tvName.setText(name);
            }
            if (!TextUtils.isEmpty(mobile))
                tvMobile.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
            mobileIcon.setVisibility(View.VISIBLE);
            String headerImg = userInfo.getHeaderImg();

            userAvatar.setImageResource(R.drawable.user_avatar);
            if (headerImg != null) {
                ImageUtils.loadBitmap(mActivity , headerImg + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER, userAvatar, R.drawable.user_avatar , "");
            }
            getExtraMoney();
        } else {
//            tvLogin.setVisibility(View.VISIBLE);
//            moreSetting.setVisibility(View.GONE);
//            notice.setVisibility(View.GONE);
            tvMobile.setText("");
            mobileIcon.setVisibility(View.GONE);
            tvName.setText("请先登录");
            tvBalance.setText("0");
            tvRedbag.setText("0");
            userAvatar.setImageResource(R.drawable.user_avatar);
            setAccountView(new UserAccountModel.ValueEntity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case LOGIN_IN_SUCCESS:

                break;
            case EXIT_APP_SUCCESS:
                refreshPage();
                break;
        }
    }

    public static boolean hasAnyMarketInstalled(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("market://details?id=android.browser"));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0 != list.size();
    }
}
