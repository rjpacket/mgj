package com.project.mgjandroid.ui.activity.renthouse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.HouseLeaseInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.RentHouseDetailModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.TipOffActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.MLog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Router(value = "leaseHousesDetail/:messageId" , intExtra = "messageId")
public class RentHouseDetailActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @InjectView(R.id.rent_house_view_pager)
    private ViewPager mViewPager;
    @InjectView(R.id.common_back)
    private ImageView mBack;
    @InjectView(R.id.common_title)
    private TextView mTitle;
    @InjectView(R.id.com_share)
    private ImageView mShare;

    @InjectView(R.id.rent_house_name) private TextView tvName;
    @InjectView(R.id.rent_house_tag) private TextView tvTag;
    @InjectView(R.id.rent_house_price) private TextView tvPrice;
    @InjectView(R.id.rent_house_detail_time) private TextView tvTime;
    @InjectView(R.id.rent_house_jubao) private TextView tvReport;
    @InjectView(R.id.rent_house_detail_content) private TextView tvContent;
    @InjectView(R.id.rent_house_detail_contact) private TextView tvContact;
    @InjectView(R.id.rent_house_page_count) private TextView tvPageCount;
    @InjectView(R.id.top_banner) private RelativeLayout rlTop;
    @InjectView(R.id.rent_house_type) private TextView tvType;
    @InjectView(R.id.rent_house_location) private TextView tvLocation;
    @InjectView(R.id.rent_house_area_tip) private TextView tvAreaTip;
    @InjectView(R.id.rent_house_area) private TextView tvArea;

    private List<String> mImageUrls;
    private PhotoPagerAdapter mAdapter;
    private int mLength;
    private CustomDialog dialog;
    private HouseLeaseInformation mSecondHandBean;
    private PopupWindow mPopupWindow;
    private MLoadingDialog mMLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_house_detail);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        int secondHandType = ChooseCityModel.getInstance().getSecondHandType();
        if(secondHandType == 1){
            rlTop.setVisibility(View.GONE);
            tvAreaTip.setVisibility(View.GONE);
            tvArea.setVisibility(View.GONE);
        }

        mBack.setOnClickListener(this);
        mTitle.setText("详情");
        mShare.setVisibility(View.VISIBLE);
        mImageUrls = new ArrayList<>();
        mAdapter = new PhotoPagerAdapter();
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("messageId")){
            int beanId = intent.getIntExtra("messageId",-1);
            mMLoadingDialog = new MLoadingDialog();
            mMLoadingDialog.show(getFragmentManager(), "");
            getData(beanId);
        }
    }

    private void getData(long beanId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", beanId);
        VolleyOperater<RentHouseDetailModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_RENT_HOUSE_DETAIL, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    RentHouseDetailModel secondHandDetailModel = (RentHouseDetailModel) obj;
                    mSecondHandBean = secondHandDetailModel.getValue();
                    setUIData(mSecondHandBean);
                }
            }
        }, RentHouseDetailModel.class);
    }

    private void setUIData(HouseLeaseInformation bean) {
        String imgs = bean.getImgs();
        if(!TextUtils.isEmpty(imgs)){
            String[] split = imgs.split(";");
            mLength = split.length;
            tvPageCount.setText("1/" + mLength);
            for (int i = 0; i < split.length; i++) {
                mImageUrls.add(split[i]);
            }
            mAdapter.notifyDataSetChanged();
        }else{
            rlTop.setVisibility(View.GONE);
        }
        tvName.setText(bean.getTitle());
        tvTag.setText(bean.getHouseLeaseCategoryName());
        tvPrice.setText(bean.getShowAmt());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        tvTime.setText("发布时间：" + format.format(bean.getCreateTime()));
        tvContent.setText(bean.getDescription());
        tvReport.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        mShare.setOnClickListener(this);

        tvType.setText(bean.getHouseType());
        tvLocation.setText(bean.getAddress());
        tvArea.setText(bean.getHouseArea() + "㎡");
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tvPageCount.setText((position + 1) + "/" + mLength);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class PhotoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            String imageView1 = mSecondHandBean.getImageView();
            ImageUtils.loadBitmap(mActivity,mImageUrls.get(position),imageView,R.drawable.photo_default, "?" + imageView1);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void showDialog(final String mobile) {
        if (dialog != null) {
            dialog.show();
            return;
        }
        dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(getString(R.string.withdraw_phone), mobile)));
                mActivity.startActivity(intent);
                dialog.dismiss();
            }
            @Override
            public void onExit() {
                dialog.dismiss();
            }
        },"呼叫","取消","联系电话",mobile);

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.rent_house_detail_contact:
                showDialog(mSecondHandBean.getMobile());
                break;
            case R.id.rent_house_jubao:
                if(CommonUtils.checkLogin(mActivity)) {
                    Intent intent = new Intent(mActivity, TipOffActivity.class);
                    intent.putExtra("second_hand_info_id", mSecondHandBean.getId());
                    intent.putExtra("url", Constants.URL_REPORT_RENT_HOUSE_MESSAGE);
                    startActivity(intent);
                }
                break;
            case R.id.com_share:
                if(mPopupWindow == null){
                    initPopup();
                }
                changePopupState(0.5f,true);
                break;

            case R.id.second_hand_qq:
                onClickShareToQQ(mSecondHandBean.getTitle(),mSecondHandBean.getDescription(), mSecondHandBean.getShareUrl(), getString(mSecondHandBean.getImgs()));
                break;
            case R.id.second_hand_wechat:
                onClickShareToWechat(0,mSecondHandBean.getTitle(),mSecondHandBean.getDescription(),mSecondHandBean.getShareUrl(),getString(mSecondHandBean.getImgs()));
                break;

            case R.id.second_hand_friend:
                onClickShareToWechat(1,mSecondHandBean.getTitle(),mSecondHandBean.getDescription(),mSecondHandBean.getShareUrl(),getString(mSecondHandBean.getImgs()));
                break;

            case R.id.second_hand_cancel:
                hidePopup();
                break;
        }
    }

    private String getString(String imgs) {
        if(TextUtils.isEmpty(imgs)){
            imgs = "";
        }else{
            imgs = imgs.split(";")[0];
        }
        return imgs;
    }

    private void hidePopup() {
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    private void initPopup() {
        View view = mInflater.inflate(R.layout.popup_second_hand_share, null);
        LinearLayout llQQ = (LinearLayout) view.findViewById(R.id.second_hand_qq);
        LinearLayout llWechat = (LinearLayout) view.findViewById(R.id.second_hand_wechat);
        LinearLayout llFriend = (LinearLayout) view.findViewById(R.id.second_hand_friend);
        TextView tvCancel = (TextView) view.findViewById(R.id.second_hand_cancel);
        tvCancel.setOnClickListener(this);
        llQQ.setOnClickListener(this);
        llWechat.setOnClickListener(this);
        llFriend.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable cd = new ColorDrawable(0x000000);
        mPopupWindow.setBackgroundDrawable(cd);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changePopupState(1.0f , false);
            }
        });
    }

    private void changePopupState(float v, boolean b) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = v;
        mActivity.getWindow().setAttributes(lp);
        if(b && !mPopupWindow.isShowing()){
            mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
        }
    }


    /**
     * param flag 0 好友，1 朋友圈
     *
     */
    private void onClickShareToWechat(int flag, String title, String summary, String tagUrl, String imgUrl) {
        if (!App.getApi().isWXAppInstalled()) {
            ToastUtils.displayMsg("请先安装微信客户端", RentHouseDetailActivity.this);
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = tagUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);

        msg.title = title;
        msg.description = summary;
        Bitmap thumb = null;
        if(CheckUtils.isNoEmptyStr(imgUrl)){
            if(imgUrl.contains(";")){
                String[] strings = imgUrl.split(";");
                imgUrl = strings[0];
            }
            thumb = ImageUtils.downLoadImageUrl(imgUrl);
            if(ImageUtils.getBmpSize(thumb) > 32*1024) { //weixin要求图片小于32K
                thumb = ImageUtils.compressImg(thumb, 32);
            }
        }
        if (thumb == null) {
            thumb = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
        }
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag;
        App.getApi().sendReq(req);
    }

    private void onClickShareToQQ(String title, String summary, String tagUrl, String imgUrl) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  summary);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  tagUrl);
        if(CheckUtils.isNoEmptyStr(imgUrl) && imgUrl.contains(";")){
            String[] strings = imgUrl.split(";");
            imgUrl = strings[0];
        }
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));

        App.getmTencent().shareToQQ(mActivity, params, listener);
    }

    private BaseUiListener listener = new BaseUiListener();
    /**
     * QQ分享回调
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            MLog.i("onComplete:" + response.toString());
            ToastUtils.displayMsg("分享成功", RentHouseDetailActivity.this);
            mActivity.finish();
        }

        @Override
        public void onError(UiError e) {
            MLog.i("onError:"+"code:" + e.errorCode + ", msg:"
                    + e.errorMessage + ", detail:" + e.errorDetail);
            ToastUtils.displayMsg("分享失败", RentHouseDetailActivity.this);
        }

        @Override
        public void onCancel() {
            MLog.i("onCancel");
            mActivity.finish();
//            ToastUtils.displayMsg("分享已取消", ShareActivity.this);
        }
    }
}
