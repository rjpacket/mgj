package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.constants.ShipmentMode;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.LocationMapActivity;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.util.List;

public class MerchantsFragment extends HeaderViewPagerFragment implements View.OnClickListener {
	protected BaseActivity mActivity;
	protected View view;
	private TextView tvCollection, tvName, tvScoreNum,  tvAddress, tvTime, tvPhone, tvIntroduction;
	private RatingBar ratingScore;
	private LinearLayout infoLayout, activesLayout, activeLayout, introductLayout;
	private ScrollView scrollView;
	private RelativeLayout relativeLocation;
	private Merchant merchant;
	private TextView tvQiSong,tvPeiSong,tvAvgTime,tvMonthNum;
	private TextView tvNotice;
	private TextView tvMerchantShipment;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = (BaseActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.merchants_fragment, container, false);
		initViews();
		return view;
	}

	private void initViews() {
		tvMerchantShipment = (TextView)view.findViewById(R.id.merchant_shipment_type);
		tvCollection = (TextView) view.findViewById(R.id.merchants_fragment_tv_collection);
		tvName = (TextView) view.findViewById(R.id.merchants_fragment_tv_name);
		ratingScore = (RatingBar) view.findViewById(R.id.merchants_fragment_score);
		tvScoreNum = (TextView) view.findViewById(R.id.merchants_fragment_tv_score_num);
		tvQiSong = (TextView) view.findViewById(R.id.merchant_qisong);
		tvPeiSong = (TextView) view.findViewById(R.id.merchant_peisong);
		tvAvgTime = (TextView) view.findViewById(R.id.mine_fragment_balance);
		tvMonthNum = (TextView) view.findViewById(R.id.merchants_fragment_tv_sale_num);
		infoLayout = (LinearLayout) view.findViewById(R.id.merchants_fragment_info_layout);
		tvAddress = (TextView) view.findViewById(R.id.merchants_fragment_tv_address);
		tvTime = (TextView) view.findViewById(R.id.merchants_fragment_tv_time);
		tvPhone = (TextView) view.findViewById(R.id.merchants_fragment_tv_phone);
		activesLayout = (LinearLayout) view.findViewById(R.id.merchants_fragment_actives_layout);
		activeLayout = (LinearLayout) view.findViewById(R.id.merchants_fragment_active_layout);
		introductLayout = (LinearLayout) view.findViewById(R.id.merchants_fragment_introduct_layout);
		tvIntroduction = (TextView) view.findViewById(R.id.merchants_fragment_tv_introduction);
		scrollView = (ScrollView) view.findViewById(R.id.merchants_scroll_view);
		relativeLocation = (RelativeLayout) view.findViewById(R.id.merchants_location);
		tvNotice = (TextView) view.findViewById(R.id.merchant_notice);
		relativeLocation.setOnClickListener(this);
	}

	public void getData(Merchant merchant) {
		this.merchant = merchant;
		if (merchant != null) {
			if(CheckUtils.isNoEmptyStr(merchant.getName()))
				tvName.setText(merchant.getName());

			if(merchant.getAverageScore()!=null){
				ratingScore.setRating(merchant.getAverageScore().floatValue());
				tvScoreNum.setText("(" + CommonUtils.BigDecimal2Str(merchant.getAverageScore()) + ")");
			}

			//TODO tvMerchantShipment
			int shipments = merchant.getShipmentMode();
			tvMerchantShipment.setText(ShipmentMode.getShipmentModeByValue(shipments).getMemo());

			tvMonthNum.setText("月售" + merchant.getMonthSaled() + "单");
			// tvDes.setText("");
			// tvPaisong.setText("");
			tvAddress.setText("商家地址：" + (merchant.getAddress() == null ? "":merchant.getAddress()));
			tvTime.setText("营业时间：" + (merchant.getWorkingTime() == null ? "":merchant.getWorkingTime()));
			tvPhone.setText("联系方式：" + (merchant.getContacts() == null ? "":merchant.getContacts()));

			String broadcast = merchant.getBroadcast();
			if(broadcast != null && !"".equals(broadcast)) {
				tvNotice.setText(broadcast);
			}else{
				tvNotice.setText("商家暂无公告");
			}

			if(CheckUtils.isNoEmptyStr(String.valueOf(merchant.getMinPrice())))
				tvQiSong.setText(StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "");
			if(CheckUtils.isNoEmptyStr(String.valueOf(merchant.getShipFee())))
				tvPeiSong.setText(StringUtils.BigDecimal2Str(merchant.getShipFee()) + "");
			if(CheckUtils.isNoEmptyStr(String.valueOf(merchant.getAvgDeliveryTime())))
				tvAvgTime.setText(merchant.getAvgDeliveryTime() + "");
			List<PromotionActivity> promotions = merchant.getPromotionActivityList();
			if (CheckUtils.isNoEmptyList(promotions)) {
				activesLayout.setVisibility(View.VISIBLE);
				activeLayout.removeAllViews();
				for (PromotionActivity promotion : promotions) {
					addPromotion(activeLayout, promotion);
				}

			} else {
				activesLayout.setVisibility(View.GONE);
			}
		}
	}
	private void addPromotion(LinearLayout layout, PromotionActivity promotion) {
		LinearLayout childLayout = new LinearLayout(mActivity);
		childLayout.setOrientation(LinearLayout.HORIZONTAL);
		childLayout.setGravity(Gravity.CENTER_VERTICAL);
		if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
			ImageView image = new ImageView(mActivity);
			ImageUtils.loadBitmap(mActivity,promotion.getPromoImg(), image, R.drawable.jian , "");
			LayoutParams params = new LayoutParams(DipToPx.dip2px(mActivity, 16), DipToPx.dip2px(mActivity, 16));
			childLayout.addView(image, params);
		}
		if (CheckUtils.isNoEmptyStr(promotion.getPromoName())) {
			TextView tv = new TextView(mActivity);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = DipToPx.dip2px(mActivity, 4);
			tv.setText(promotion.getPromoName());
			tv.setTextColor(mActivity.getResources().getColor(R.color.common_black_text));
			tv.setTextSize(14);
			childLayout.addView(tv, params);
		}
		childLayout.setPadding(DipToPx.dip2px(mActivity, 24), DipToPx.dip2px(mActivity, 10), 0, DipToPx.dip2px(mActivity, 10));
		childLayout.setBackgroundColor(getResources().getColor(R.color.white));
		LayoutParams paramsChild = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		paramsChild.topMargin = DipToPx.dip2px(mActivity, 1);
		layout.addView(childLayout, paramsChild);
	}

	@Override
	public View getScrollableView() {
		return scrollView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.merchants_location:
				Intent intent = new Intent(getActivity(), LocationMapActivity.class);
				if(merchant != null){
					intent.putExtra("merchant",merchant);
				}
				startActivity(intent);
				break;
		}
	}
}
