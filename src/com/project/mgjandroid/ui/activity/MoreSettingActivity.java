package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.LogoutModel;
import com.project.mgjandroid.model.UserAccountModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.fragment.MineFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class MoreSettingActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.more_setting_back)
    private ImageView settingBack;
    @InjectView(R.id.more_setting_exit)
    private TextView tvExit;
    @InjectView(R.id.setting_clear_cache)
    private RelativeLayout rlClearCache;
    @InjectView(R.id.setting_about)
    private RelativeLayout rlAbout;
    @InjectView(R.id.setting_size)
    private TextView tvCacheSize;
    @InjectView(R.id.setting_non_wifi)
    private RelativeLayout rlNonWifi;
    @InjectView(R.id.setting_wifi)
    private TextView imgQuality;

    private Context mContext;
    private Dialog avatarDialog;
    private MLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_setting);
        Injector.get(this).inject();
        mContext = this;
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        settingBack.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        rlClearCache.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        rlNonWifi.setOnClickListener(this);
        String autoCacheSize = FileUtils.getAutoCacheSize();
        tvCacheSize.setText(autoCacheSize);
        if(App.isLogin()){
            tvExit.setVisibility(View.VISIBLE);
        }else{
            tvExit.setVisibility(View.GONE);
        }
        String quality=PreferenceUtils.getStringPreference("ImageQuality","普通",mContext);
        imgQuality.setText(quality);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more_setting_back:
                onBackPressed();
                break;
            case R.id.more_setting_exit:
                exit();
                break;
            case R.id.setting_clear_cache:
                FileUtils.deleteAllCacheFile();
                tvCacheSize.setText("0B");
                break;
            case R.id.setting_about:
                startActivity(new Intent(mActivity, AboutActivity.class));
                break;
            case R.id.setting_non_wifi:
                initAvatarDialog();
                break;
            case R.id.btn_take_photo:
                PreferenceUtils.saveStringPreference("ImageQuality","普通",mContext);
                imgQuality.setText("普通");
                avatarDialog.dismiss();
                break;
            case R.id.btn_pick_photo:
                PreferenceUtils.saveStringPreference("ImageQuality","高质量",mContext);
                imgQuality.setText("高质量");
                avatarDialog.dismiss();
                break;
        }
    }

    /**
     * 登出
     */
    private void exit() {
        loadingDialog.show(getFragmentManager(), "");
        VolleyOperater<LogoutModel> operater = new VolleyOperater<LogoutModel>(this);
        operater.doRequest(Constants.URL_EXIT_OUT, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                loadingDialog.dismiss();
                if(isSucceed && obj != null){
                    LogoutModel logoutModel = (LogoutModel) obj;
                    if(logoutModel.isSuccess()){
                        App.setIsLogin(false);
                        App.setUserInfo(null);
                        PreferenceUtils.saveStringPreference("token", "", mContext);
                        setResult(MineFragment.EXIT_APP_SUCCESS,new Intent());
                        finish();
                    }
                }
            }
        }, LogoutModel.class);
    }

    /**
     * 选择图片质量
     */
    private void initAvatarDialog() {
        avatarDialog = new Dialog(this, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
        Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        dialog_bt_take_photo.setText("普通");
        dialog_bt_pick_photo.setText("高质量");
        dialog_bt_pick_photo.setOnClickListener(this);
        dialog_bt_take_photo.setOnClickListener(this);
        dialog_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatarDialog.dismiss();
            }
        });
        avatarDialog.setContentView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        avatarDialog.show();
    }
}
