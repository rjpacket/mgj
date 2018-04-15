package com.project.mgjandroid.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.AppVersion;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.download.FileDownloadManager;
import com.project.mgjandroid.model.AppLaunchModel;
import com.project.mgjandroid.model.SmsLoginModel;
import com.project.mgjandroid.model.UpdateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.HomePagerAdapter;
import com.project.mgjandroid.ui.fragment.BaseFragment;
import com.project.mgjandroid.ui.fragment.CommunityFragment;
import com.project.mgjandroid.ui.fragment.HomeFragment;
import com.project.mgjandroid.ui.fragment.MineFragment;
import com.project.mgjandroid.ui.fragment.OrderListFragment;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.ui.view.CustomViewPager;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 主页
 *
 * @author jian
 */
@Router("home")
public class HomeActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {
    private final int INDEX_HOME = 0;
    private final int INDEX_ORDER = 1;
//    private final int INDEX_COMMUNITY = 2;
    private final int INDEX_MINE = 2;
    public final static String CHANGE_PICKED_ADDRESS = "com.project.mgjandroid.HomeActivity.CHANGE_PICKED_ADDRESS";

    @InjectView(R.id.home_act_layout_home)
    private LinearLayout homeLayout;
    @InjectView(R.id.home_act_layout_order)
    private LinearLayout orderLayout;
    @InjectView(R.id.home_act_layout_community)
    private LinearLayout communityLayout;
    @InjectView(R.id.home_act_layout_mine)
    private LinearLayout mineLayout;
    @InjectView(R.id.home_act_img_home)
    private ImageView homeImg;
    @InjectView(R.id.home_act_img_order)
    private ImageView orderImg;
    @InjectView(R.id.home_act_img_community)
    private ImageView communityImg;
    @InjectView(R.id.home_act_img_mine)
    private ImageView mineImg;
    @InjectView(R.id.home_act_tv_home)
    private TextView homeTv;
    @InjectView(R.id.home_act_tv_order)
    private TextView orderTv;
    @InjectView(R.id.home_act_tv_community)
    private TextView communityTv;
    @InjectView(R.id.home_act_tv_mine)
    private TextView mineTv;
    @InjectView(R.id.home_act_pager)
    private CustomViewPager pager;

    private HomePagerAdapter homePagerAdapter;
    private ArrayList<BaseFragment> fragments;
    private HomeFragment homeFragment;
    private OrderListFragment orderListFragment;
    private CommunityFragment communityFragment;
    private MineFragment mineFragment;
    private Context mContext;
    private int exitFlag = 0;
    private FileDownloadManager mManager;
    private CommonDialog mUpdateDialog;
    private ProgressBar mProgressBar;
    private Button mTvCancel;
    private Button mTvStart;
    private TextView mTvTip;
    private TextView mTvTip1;
    private LinearLayout mLlButtonLabel;
    private LinearLayout mLlUpdatingLabel;
    private ImageView mImgDelete;
    private long mMTotalSize;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.home_act);

        mContext = this;
        Injector.get(this).inject();
        homePagerAdapter = new HomePagerAdapter(this.getSupportFragmentManager());
        fragments = homePagerAdapter.getFragments();
        pager.setAdapter(homePagerAdapter);
        pager.setOffscreenPageLimit(3);
        pager.setScanScroll(false);
        setListener();
        addFragments();
        //TODO 默认登录
//        doLogin();
//        XiaomiUpdateAgent.update(this);
        initUpdateDialog();
        checkUpdate();
    }

    private void initUpdateDialog() {
        View view = mInflater.inflate(R.layout.layout_update_dialog,null);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mTvCancel = (Button) view.findViewById(R.id.update_cancel);
        mTvCancel.setOnClickListener(this);
        mTvStart = (Button) view.findViewById(R.id.update_start);
        mTvStart.setOnClickListener(this);

        mTvTip = (TextView) view.findViewById(R.id.update_tip);
        mTvTip1 = (TextView) view.findViewById(R.id.update_tip1);
        mLlButtonLabel = (LinearLayout) view.findViewById(R.id.button_label);
        mImgDelete = (ImageView) view.findViewById(R.id.update_delete);
        mImgDelete.setOnClickListener(this);
        mLlUpdatingLabel = (LinearLayout) view.findViewById(R.id.updating_label);

        mUpdateDialog = new CommonDialog(mActivity,view,this);
        mUpdateDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String token = PreferenceUtils.getStringPreference("token", "", this);
        if(!"".equals(token)){
            //TODO 自动登录
            doLogin();
        }
    }

    private void setListener() {
        homeLayout.setOnClickListener(this);
        orderLayout.setOnClickListener(this);
        communityLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        pager.setOnPageChangeListener(this);
    }

    private void addFragments() {
        homeFragment = new HomeFragment();
        orderListFragment = OrderListFragment.newInstance();
//        communityFragment = CommunityFragment.getInstance();
        mineFragment = MineFragment.newInstance();
        fragments.add(homeFragment);
        fragments.add(orderListFragment);
//        fragments.add(communityFragment);
        fragments.add(mineFragment);
        homePagerAdapter.notify(fragments);
    }

    private void changeTabUi(int index) {
        switch (index) {
            case INDEX_HOME:
                homeImg.setImageResource(R.drawable.icon_home_selected);
                homeTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                orderImg.setImageResource(R.drawable.icon_list);
                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
                communityImg.setImageResource(R.drawable.community_unselected);
                communityTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my);
                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
                break;
            case INDEX_ORDER:
                homeImg.setImageResource(R.drawable.icon_home);
                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderImg.setImageResource(R.drawable.icon_list_selected);
                orderTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                communityImg.setImageResource(R.drawable.community_unselected);
                communityTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my);
                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderListFragment.refreshList();
                break;
//            case INDEX_COMMUNITY:
//                homeImg.setImageResource(R.drawable.icon_home);
//                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
//                orderImg.setImageResource(R.drawable.icon_list);
//                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
//                communityImg.setImageResource(R.drawable.community_selected);
//                communityTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
//                mineImg.setImageResource(R.drawable.icon_my);
//                mineTv.setTextColor(getResources().getColor(R.color.gray_txt));
//                break;
            case INDEX_MINE:
                homeImg.setImageResource(R.drawable.icon_home);
                homeTv.setTextColor(getResources().getColor(R.color.gray_txt));
                orderImg.setImageResource(R.drawable.icon_list);
                orderTv.setTextColor(getResources().getColor(R.color.gray_txt));
                communityImg.setImageResource(R.drawable.community_unselected);
                communityTv.setTextColor(getResources().getColor(R.color.gray_txt));
                mineImg.setImageResource(R.drawable.icon_my_selected);
                mineTv.setTextColor(getResources().getColor(R.color.title_bar_bg));
                mineFragment.refreshPage();
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_act_layout_home:
                if (pager.getCurrentItem() != INDEX_HOME) {
                    pager.setCurrentItem(INDEX_HOME,false);
                }
                break;
            case R.id.home_act_layout_order:
                if (pager.getCurrentItem() != INDEX_ORDER) {
                    pager.setCurrentItem(INDEX_ORDER,false);
                }
                break;
//            case R.id.home_act_layout_community:
//                if (pager.getCurrentItem() != INDEX_COMMUNITY) {
//                    pager.setCurrentItem(INDEX_COMMUNITY,false);
//                }
//                break;
            case R.id.home_act_layout_mine:
                if (pager.getCurrentItem() != INDEX_MINE) {
                    pager.setCurrentItem(INDEX_MINE,false);
                }
                break;

            case R.id.update_cancel:
                mManager.start();
                mTvTip.setVisibility(View.INVISIBLE);
                mTvTip1.setVisibility(View.INVISIBLE);
                mLlButtonLabel.setVisibility(View.INVISIBLE);
                mLlUpdatingLabel.setVisibility(View.VISIBLE);
                mImgDelete.setVisibility(View.VISIBLE);
                break;

            case R.id.update_start:
                hiddenUpdateDialog();
                break;

            case R.id.update_delete:
                mManager.stop();
                hiddenUpdateDialog();
                break;

            default:
                break;
        }

    }

    private void hiddenUpdateDialog() {
        if(mUpdateDialog != null && mUpdateDialog.isShowing()){
            mUpdateDialog.dismiss();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        changeTabUi(arg0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case RESULT_OK:
                if (pager != null && pager.getCurrentItem() == 0 && homeFragment != null) {
                    homeFragment.showAddress();
                }
                if(orderListFragment!=null){
                    orderListFragment.refreshList();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction() != null && intent.getAction().equals(CHANGE_PICKED_ADDRESS) &&
                pager != null && homeFragment != null) {
            if (pager.getCurrentItem() != INDEX_HOME) {
                pager.setCurrentItem(INDEX_HOME);
            }
            homeFragment.showAddress();
        }
    }

    /**
     * 验证码登录操作
     */
    private void doLogin() {
        VolleyOperater<AppLaunchModel> operater = new VolleyOperater<AppLaunchModel>(HomeActivity.this);
        operater.doRequest(Constants.URL_INIT_APP, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    AppLaunchModel appLaunchModel = (AppLaunchModel) obj;
                    SmsLoginModel.ValueEntity.AppUserEntity appUserEntity = new SmsLoginModel.ValueEntity.AppUserEntity();
                    AppLaunchModel.ValueEntity valueEntity = appLaunchModel.getValue();
                    appUserEntity.setId(valueEntity.getId());
                    appUserEntity.setCreateTime(valueEntity.getCreateTime());
                    appUserEntity.setModifyTime(valueEntity.getModifyTime());
                    appUserEntity.setName(valueEntity.getName());
                    appUserEntity.setMobile(valueEntity.getMobile());
                    appUserEntity.setPwd(valueEntity.getPwd());
                    appUserEntity.setHeaderImg(valueEntity.getHeaderImg());
                    appUserEntity.setRegTime(valueEntity.getRegTime());
                    appUserEntity.setLastLoginTime(valueEntity.getLastLoginTime());
                    appUserEntity.setChannel(valueEntity.getChannel());
                    appUserEntity.setToken(valueEntity.getToken());
                    App.setUserInfo(appUserEntity);
                    App.setIsLogin(true);
                }
            }
        }, AppLaunchModel.class);
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        VolleyOperater<UpdateModel> operater = new VolleyOperater<>(HomeActivity.this);
        operater.doRequest(Constants.URL_UPDATE_APP, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj!= null) {
                    UpdateModel updateModel = (UpdateModel) obj;
                    if(updateModel.getCode() == 0){
                        AppVersion value = updateModel.getValue();
                        String clientVersion = value.getClientVersion();
                        if(Integer.parseInt(clientVersion) > CommonUtils.getCurrentVersionCode()){
                            mManager = new FileDownloadManager(value.getDownloadUrl(), getResources().getString(R.string.app_name) + ".apk");
                            mManager.setListener(mListener);
                            mUpdateDialog.show();
                        }
                    }
                }
            }
        }, UpdateModel.class);
    }

    private FileDownloadManager.IDownloadProgressChangedListener mListener = new FileDownloadManager.IDownloadProgressChangedListener() {
        @Override
        public void onProgressChanged(long completeSize, long totalSize) {
            Log.d("HOME","----size---->" + completeSize + ";----total---->" + totalSize);
//            mMTotalSize = totalSize;
            mProgressBar.setMax((int) totalSize);
            mProgressBar.setProgress((int) completeSize);
        }

        @Override
        public void onStateChanged(int state) {
            Log.d("STATE" , "---->" + state);
            if(state == FileDownloadManager.STATE_FINISH ){//&& mMTotalSize != 0
                mUpdateDialog.dismiss();
                CommonUtils.openFile(mActivity,new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getResources().getString(R.string.app_name) + ".apk"));
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == INDEX_HOME && homeFragment != null && homeFragment.isPopupWindowShowing()) {
            homeFragment.hidePopupWindow();
            return;
        }
        switch (exitFlag++){
            case 0:
                ToastUtils.displayMsg("再按一次，退出程序", mActivity);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        exitFlag = 0;
                    }
                }, 3 * 1000);
                break;
            case 1:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止程序退出下载thread继续跑
        if(mManager != null){
            mManager.stop();
        }
    }
}
