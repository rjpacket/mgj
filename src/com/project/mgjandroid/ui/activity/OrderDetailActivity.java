package com.project.mgjandroid.ui.activity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.PaymentMode;
import com.project.mgjandroid.constants.ShipmentMode;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.GetRedPackageModel;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.model.SubmitOrderModel.ValueEntity;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.fragment.OrderListFragment;
import com.project.mgjandroid.ui.view.CommonDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.TimeTextView;
import com.project.mgjandroid.ui.view.pullableview.PullToRefreshLayout;
import com.project.mgjandroid.ui.view.pullableview.PullToRefreshLayout.OnRefreshListener;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情
 * 
 * @author jian
 * 
 */
@SuppressLint("NewApi")
@Router("orderdetail/:orderId")
public class OrderDetailActivity extends BaseActivity implements OnClickListener {
	public static final String ORDER_ID = "orderId";
	public static final String ORDER_LIST_ENTITY = "order_list_entity";
	public static final String SUBMIT_ORDER_ENTITY = "submit_order_entity";
	/**
	 * 取消订单
	 */
	private static final int STATE_CANCEL = -1;
	/**
	 * 订单创建
	 */
	private static final int STATE_INIT = 0;
	/**
	 * 等待付款
	 */
	private static final int STATE_WAIT_PAY = 1;
	/**
	 * 等待商家确认
	 */
	private static final int STATE_WAIT_CONFIRM = 2;
	/**
	 * 商家已接单
	 */
	private static final int STATE_ACCEPED = 3;
	/**
	 * 配送员取货中
	 */
	private static final int STATE_WAIT_TAKE = 4;
	/**
	 * 配送员已取货
	 */
	private static final int STATE_HAS_TAKE = 5;
	/**
	 * 等待送达
	 */
	private static final int STATE_WAIT_DELIVERY = 6;
	/**
	 * 完成
	 */
	private static final int STATE_DONE = 7;

	@InjectView(R.id.order_detail_act_layout)
	private LinearLayout layoutDetail;
	@InjectView(R.id.order_detail_act_iv_back)
	private ImageView imgBack;
	@InjectView(R.id.order_detail_act_iv_iphone)
	private ImageView imgPhone;
	@InjectView(R.id.order_detail_act_tv_title)
	private TextView tvTitle;
	@InjectView(R.id.order_detail_act_pull_layout)
	private PullToRefreshLayout refreshLayout;
	@InjectView(R.id.order_detail_order_state_img_state)
	private ImageView imgState;
	@InjectView(R.id.order_detail_order_state_tv_state)
	private TextView tvState;
	@InjectView(R.id.order_detail_order_state_tv_des)
	private TextView tvStateDes;
	@InjectView(R.id.order_detail_order_state_tv_commit)
	private TextView tvStateLeft;
	@InjectView(R.id.order_detail_order_state_img_commit)
	private ImageView imgStateLeft;
	@InjectView(R.id.order_detail_order_state_tv_receive)
	private TextView tvStateMidL;
	@InjectView(R.id.order_detail_order_state_img_receive)
	private ImageView imgStateMidL;
	@InjectView(R.id.order_detail_order_state_tv_take_goods)
	private TextView tvStateMidR;
	@InjectView(R.id.order_detail_order_state_img_take_goods)
	private ImageView imgStateMidR;
	@InjectView(R.id.order_detail_order_state_tv_arrive)
	private TextView tvStateRight;
	@InjectView(R.id.order_detail_order_state_img_arrive)
	private ImageView imgStateRight;
	@InjectView(R.id.order_detail_order_state_layout_mid_l)
	private RelativeLayout layoutStateMidL;
	@InjectView(R.id.order_detail_order_state_layout_mid_r)
	private RelativeLayout layoutStateMidR;
	@InjectView(R.id.order_detail_ad_image)
	private ImageView ivAdIamge;
	@InjectView(R.id.order_detail_commercial_layout)
	private RelativeLayout layoutCommercial;
	@InjectView(R.id.order_detail_commercial_img)
	private ImageView imgCommercial;
	@InjectView(R.id.order_detail_commercial_name)
	private TextView tvCommercialName;
	@InjectView(R.id.order_detail_commercial_meal_layout)
	private LinearLayout layoutMeal;
	@InjectView(R.id.order_detail_commercial_ship_layout)
	private LinearLayout layoutShip;
	@InjectView(R.id.order_detail_commercial_campaigns_layout)
	private LinearLayout layoutCampaigns;
	@InjectView(R.id.order_detail_commercial_buy_again)
	private TextView tvBuyAgain;
	@InjectView(R.id.order_detail_commercial_meal_all_money)
	private TextView tvAllMoney;
	@InjectView(R.id.order_detail_tv_shipping_style)
	private TextView tvShippingStyle;
	@InjectView(R.id.order_detail_detail_tv_order_num)
	private TextView tvOrderNum;
	@InjectView(R.id.order_detail_detail_name)
	private TextView tvPersonName;
	@InjectView(R.id.order_detail_detail_phone)
	private TextView tvPersonPhone;
	@InjectView(R.id.order_detail_detail_address)
	private TextView tvPersonAddress;
	@InjectView(R.id.order_detail_detail_paytype)
	private TextView tvPersonPayType;
	@InjectView(R.id.order_detail_detail_caution)
	private TextView tvOrderCaution;
	@InjectView(R.id.order_detail_detail_buy_time)
	private TextView tvOrderTime;
	@InjectView(R.id.order_detail_detail_arrive_time)
	private TextView tvArriveTime;

	@InjectView(R.id.order_detail_act_bottom_un_pay)
	private LinearLayout unPayBottonLayout;
	@InjectView(R.id.order_detail_act_bottom_wait_arrive)
	private LinearLayout waitArriveLayout;

	@InjectView(R.id.order_detail_act_complain)
	private TextView tvComplain;
	@InjectView(R.id.order_detail_act_evaluate)
	private TextView tvEvaluate;
	@InjectView(R.id.order_detail_act_buy_again)
	private TextView tvBuyBottomAgain;
	@InjectView(R.id.order_detail_act_un_pay_cancel)
	private TextView tvUnPayCancel;
	@InjectView(R.id.order_detail_act_un_pay_go_pay)
	private TimeTextView tvUnPayGoPay;
	@InjectView(R.id.order_detail_act_bottom_cancel)
	private TextView tvBottomCancel;
	@InjectView(R.id.order_detail_act_wait_arrive_complain)
	private TextView tvWaitComplain;
	@InjectView(R.id.order_detail_act_wait_arrive_cuidan)
	private TextView tvWaitCuiDan;
	@InjectView(R.id.order_detail_act_wait_arrive_quit)
	private TextView tvWaitQuit;
	@InjectView(R.id.order_detail_act_wait_arrive_sure)
	private TextView tvWaitSure;
	@InjectView(R.id.driver_map)
	private MapView mapView;
	@InjectView(R.id.map_cache)
	private ImageView mapCache;
	@InjectView(R.id.order_detail_act_bottom_bar)
	private RelativeLayout rlBottomBar;

	String orderId;
	private boolean refreshFlag = true;
	private ValueEntity submitOrderEntity;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private final static int ONE_MINUTE = 60 * 1000;
	private BaiduMap baiduMap;
	private CustomDialog dialog;
	private Dialog avatarDialog;
	private OrderFragmentModel.ValueEntity valueEntity;
	private SimpleDateFormat sdf;
	private CommonDialog mRedDialog;
	private MLoadingDialog loadingDialog;
	private String errorMsg;
	private CustomDialog goodsDialog;
	private JSONObject previewJsonData;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.order_detail_act);
		Injector.get(this).inject();
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		loadingDialog = new MLoadingDialog();
		init();
	}

	private void init() {
		orderId = getIntent().getStringExtra(ORDER_ID);
		valueEntity = (OrderFragmentModel.ValueEntity) getIntent().getSerializableExtra(ORDER_LIST_ENTITY);
		submitOrderEntity = (ValueEntity) getIntent().getSerializableExtra(SUBMIT_ORDER_ENTITY);
		if(orderId == null){
			orderId = submitOrderEntity.getOrderItems().get(0).getOrderId();
		}
		if(submitOrderEntity != null){
			if(submitOrderEntity.getMerchant()!=null){
				tvTitle.setText(submitOrderEntity.getMerchant().getName());
			}
			showDetails(submitOrderEntity);
		}else{
			getData(true);
		}
		imgBack.setOnClickListener(this);
		imgPhone.setOnClickListener(this);
		ivAdIamge.setOnClickListener(this);
		tvUnPayCancel.setOnClickListener(this);
		tvUnPayGoPay.setOnClickListener(this);
		tvBuyBottomAgain.setOnClickListener(this);
		tvBottomCancel.setOnClickListener(this);
		tvWaitComplain.setOnClickListener(this);
		tvWaitCuiDan.setOnClickListener(this);
		tvWaitQuit.setOnClickListener(this);
		tvWaitSure.setOnClickListener(this);
		tvComplain.setOnClickListener(this);
		tvEvaluate.setOnClickListener(this);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				if (refreshFlag) {
					getData(false);
				}
			}

			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

			}
		});
		baiduMap = mapView.getMap();
		hideBaiduMapChildView();
		baiduMap.setMyLocationEnabled(true);

		Intent intent = getIntent();
		if(intent != null && intent.hasExtra("hasRedPackage")){
			boolean hasRedPackage =  intent.getBooleanExtra("hasRedPackage", false);
			if(hasRedPackage){
				checkHasRedPackage(orderId);
			}
		}
	}

	private void checkHasRedPackage(String orderId) {
		Map<String, Object> map = new HashMap<>();
		map.put("orderId", orderId);
		VolleyOperater<GetRedPackageModel> operater = new VolleyOperater<>(OrderDetailActivity.this);
		operater.doRequest(Constants.URL_GET_EXTRA_RED_BAG, map, new VolleyOperater.ResponseListener() {

			@Override
			public void onRsp(boolean isSucceed, Object obj) {
				if (isSucceed && obj != null) {
					GetRedPackageModel getRedPackageModel = (GetRedPackageModel) obj;
					initRedPackage(getRedPackageModel.getValue().getAmt());
				}
			}
		}, GetRedPackageModel.class);
	}

	private void initRedPackage(BigDecimal amt) {
		View view = mInflater.inflate(R.layout.dialog_red_package,null);
		TextView tvMoney = (TextView) view.findViewById(R.id.red_package_money);
		tvMoney.setText(String.valueOf(amt.intValue()));
		TextView tvToSee = (TextView) view.findViewById(R.id.red_package_show);
		tvToSee.setOnClickListener(this);
		ImageView imgDelete = (ImageView) view.findViewById(R.id.red_package_delete);
		imgDelete.setOnClickListener(this);
		mRedDialog = new CommonDialog(mActivity,view);
		mRedDialog.setCanceledOnTouchOutside(false);
		mRedDialog.show();
		scaleIn(view);
	}

	public void scaleIn(View view) {
		ObjectAnimator animatorSX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
		animatorSX.setDuration(1200);
		animatorSX.setRepeatCount(0);
		animatorSX.setInterpolator(new AccelerateInterpolator());
		ObjectAnimator animatorSY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
		animatorSY.setDuration(1200);
		animatorSY.setRepeatCount(0);
		animatorSY.setInterpolator(new AccelerateInterpolator());
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorSX).with(animatorSY);
		animSet.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_detail_act_iv_back:
			onBackPressed();
			break;
		case R.id.order_detail_act_iv_iphone:
			initAvatarDialog();
//			if(submitOrderEntity != null && submitOrderEntity.getMerchant() != null && !TextUtils.isEmpty(submitOrderEntity.getMerchant().getContacts())){
//				Uri uri = Uri.parse("tel:" + submitOrderEntity.getMerchant().getContacts());
//				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//				startActivity(intent);
//			}
			break;
		case R.id.order_detail_ad_image:
			break;
		case R.id.order_detail_commercial_layout:
			if(submitOrderEntity!=null){
				Intent intent = new Intent(this, CommercialActivity.class);
				intent.putExtra(CommercialActivity.MERCHANT_ID, submitOrderEntity.getMerchant().getId().intValue());
				startActivity(intent);
			}
			break;
		case R.id.order_detail_commercial_buy_again:
		case R.id.order_detail_act_buy_again:
			HashMap<String,Object> map = new HashMap<>();
			map.put("loginToken", App.getUserInfo().getToken());
			map.put("userId", App.getUserInfo().getId());
			map.put("orderId", submitOrderEntity.getId());
			double latitude = Double.parseDouble(PreferenceUtils.getLocation(OrderDetailActivity.this)[0]);
			double longitude = Double.parseDouble(PreferenceUtils.getLocation(OrderDetailActivity.this)[1]);
			map.put("longitude", longitude);
			map.put("latitude", latitude);
			VolleyOperater<ConfirmOrderModel> operaterOnce = new VolleyOperater<>(OrderDetailActivity.this);
			loadingDialog.show(getFragmentManager(), "");
			operaterOnce.doRequest(Constants.URL_AGAIN_ORDER_PREVIEW, map, new VolleyOperater.ResponseListener() {
				@Override
				public void onRsp(boolean isSucceed, Object obj) {
					if (isSucceed && obj != null) {
						if (obj instanceof String) {
							errorMsg = (String) obj;
							ToastUtils.displayMsg(errorMsg, mActivity);
						} else {
							ConfirmOrderModel confirmOrderModel = (ConfirmOrderModel) obj;
							if (confirmOrderModel.isSuccess()) {
								Integer type = confirmOrderModel.getValue().getAgainOrderStatus();
								if(type != null && type == 0){//商家已下线
									ToastUtils.displayMsg(confirmOrderModel.getValue().getAgainOrderTip(), mActivity);
								}else if(type != null && type == 1){//商品已下线
									final int id = (int) confirmOrderModel.getValue().getMerchantId();
									List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> list = confirmOrderModel.getValue().getOrderItems();
									ArrayList<Map<String, Object>> map = new ArrayList<>();
									for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : list) {
										HashMap<String, Object> m = new HashMap<>();
										m.put("id", goods.getId());
										m.put("quantity", goods.getQuantity());
										m.put("specId", goods.getSpecId());
										m.put("categoryId", goods.getCategoryId());
										map.add(m);
									}
									Map<String, Object> jsonMap = new HashMap<>();
									jsonMap.put("goodsJson",map);
									final JSONObject goodsJson = new JSONObject(jsonMap);
									goodsDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
										@Override
										public void onSure() {
											Intent intent1 = new Intent(mActivity, CommercialActivity.class);
											intent1.putExtra(CommercialActivity.MERCHANT_ID,id);
											intent1.putExtra("againOrder",true);
											intent1.putExtra("onceMoreOrder", goodsJson);
											startActivity(intent1);
											goodsDialog.dismiss();
										}

										@Override
										public void onExit() {
											goodsDialog.dismiss();
										}
									},"好的","知道了","提示",confirmOrderModel.getValue().getAgainOrderTip());
									goodsDialog.show();
								}else{
									List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> pickGoods = confirmOrderModel.getValue().getOrderItems();
									String caution = confirmOrderModel.getValue().getCaution();
									ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
									for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : pickGoods) {
										HashMap<String, Object> m = new HashMap<>();
										m.put("id", goods.getId());
										m.put("quantity", goods.getQuantity());
										m.put("specId", goods.getSpecId());
										orderItems.add(m);
									}
									Map<String, Object> jsonMap = new HashMap<>();
									jsonMap.put("merchantId", confirmOrderModel.getValue().getMerchantId());
									jsonMap.put("loginToken", App.getUserInfo().getToken());
									jsonMap.put("userId", App.getUserInfo().getId());
									jsonMap.put("orderItems", orderItems);
									previewJsonData = new JSONObject(jsonMap);
									Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
									intent.putExtra("confirmOrderModel", confirmOrderModel);
									intent.putExtra("onceMoreOrder", previewJsonData);
									if(caution != null && !caution.isEmpty()) {
										intent.putExtra("caution", caution);
									}
									startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
								}
							} else {
								ToastUtils.displayMsg("商户加载失败", mActivity);
							}
						}
					}
					loadingDialog.dismiss();
				}
			}, ConfirmOrderModel.class);
			break;

		case R.id.order_detail_act_complain:
		case R.id.order_detail_act_wait_arrive_complain:
			dialog = new CustomDialog(OrderDetailActivity.this, new CustomDialog.onBtnClickListener() {
				@Override
				public void onSure() {
					//拨打电话
					Intent intent = new Intent();
					intent.setAction("android.intent.action.DIAL");
					intent.setData(Uri.parse("tel:" + getResources().getString(R.string.sale_phone)));
					OrderDetailActivity.this.startActivity(intent);
					dialog.dismiss();
				}
				@Override
				public void onExit() {
					dialog.dismiss();
				}
			},"呼叫","取消","提示","请拨打客服" + getResources().getString(R.string.sale_phone));
			dialog.show();
			break;
		case R.id.order_detail_act_evaluate://完成时评价
			Intent intent = new Intent(mActivity, EvaluateActivity.class);
			intent.putExtra("orderId",orderId);
			intent.putExtra("valueEntity",valueEntity);
			if(submitOrderEntity.getDeliveryTask() != null){
				intent.putExtra("hasDriver",true);
			}else{
				intent.putExtra("hasDriver",false);
			}
			startActivityForResult(intent, 1);
			break;

		case R.id.order_detail_act_un_pay_go_pay:
			Intent intent1 = new Intent(this, OnlinePayActivity.class);
			intent1.putExtra("orderId", valueEntity.getId());
			startActivityForResult(intent1, OrderListFragment.REFRESH);
			break;
		case R.id.order_detail_act_bottom_cancel:
		case R.id.order_detail_act_un_pay_cancel:
			break;

		case R.id.order_detail_act_wait_arrive_cuidan:
			break;
		case R.id.order_detail_act_wait_arrive_quit:
			break;
		case R.id.order_detail_act_wait_arrive_sure:
			break;
			case R.id.btn_take_photo:
				dialog = new CustomDialog(OrderDetailActivity.this, new CustomDialog.onBtnClickListener() {
					@Override
					public void onSure() {
						//拨打电话
						Intent intent = new Intent();
						intent.setAction("android.intent.action.DIAL");
						//submitOrderEntity.getMerchant().getContacts() 商家电话
						intent.setData(Uri.parse("tel:"+submitOrderEntity.getMerchant().getContacts()));
						OrderDetailActivity.this.startActivity(intent);
						dialog.dismiss();
					}
					@Override
					public void onExit() {
						dialog.dismiss();
					}
				},"呼叫","取消","提示","请拨打商家电话"+submitOrderEntity.getMerchant().getContacts());
				dialog.show();
				avatarDialog.dismiss();
				break;
			case R.id.btn_pick_photo:
				dialog = new CustomDialog(OrderDetailActivity.this, new CustomDialog.onBtnClickListener() {
					@Override
					public void onSure() {
						//拨打电话
						Intent intent = new Intent();
						intent.setAction("android.intent.action.DIAL");
						intent.setData(Uri.parse("tel:" + getResources().getString(R.string.sale_phone)));
						OrderDetailActivity.this.startActivity(intent);
						dialog.dismiss();
					}
					@Override
					public void onExit() {
						dialog.dismiss();
					}
				},"呼叫","取消","提示","请拨打客服电话" + getResources().getString(R.string.sale_phone));
				dialog.show();
				avatarDialog.dismiss();
				break;

			case R.id.red_package_show:
				startActivity(new Intent(this,MyRedBagActivity.class));
				break;

			case R.id.red_package_delete:
				if(mRedDialog != null && mRedDialog.isShowing()){
					mRedDialog.dismiss();
				}
				break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data != null){
			switch (resultCode){
				case RESULT_OK:
					getData(true);
					break;
			}
		}
	}

	private void getData(final boolean isAutoRefresh) {
		refreshFlag = false;
		Map<String, Object> map = new HashMap<>();
		map.put(ORDER_ID, orderId);
		VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<>(OrderDetailActivity.this);
		operater.doRequest(Constants.URL_ORDER_DETAIL, map, new ResponseListener() {

			@Override
			public void onRsp(boolean isSucceed, Object obj) {
				if (!isAutoRefresh) {
					refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
				refreshFlag = true;
				if (isSucceed && obj != null) {
					SubmitOrderModel orderDetail = (SubmitOrderModel) obj;
					submitOrderEntity = orderDetail.getValue();
					showDetails(orderDetail.getValue());
				} else {

				}
			}
		}, SubmitOrderModel.class);
	}

	/**
	 * 显示详情
	 *
	 */
	private void showDetails(ValueEntity submitOrderEntity) {
		if (submitOrderEntity != null) {
			layoutDetail.setVisibility(View.VISIBLE);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fade_fade_in);
				animator.setTarget(layoutDetail);
				animator.start();
			}
			String expectDeliveryTime = submitOrderEntity.getExpectArrivalTime();
			if (CheckUtils.isNoEmptyStr(expectDeliveryTime)) {
				if (expectDeliveryTime.equals("1")) {
					tvStateDes.setText("预计送达时间: 立即送达");
				} else {
					tvStateDes.setText("预计送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime),CommonUtils.yyyy_MM_dd_HH_mm_ss));
				}
			}
			showState(submitOrderEntity);
			showCommercial(submitOrderEntity);
			showDelivery(submitOrderEntity);
			showDetail(submitOrderEntity);
		} else {
			layoutDetail.setVisibility(View.GONE);
		}
	}

	/**
	 * 订单状态
	 *
	 */
	private void showState(ValueEntity submitOrderEntity) {
		switch (submitOrderEntity.getOrderFlowStatus()) {
		case STATE_CANCEL:
			tvState.setText("取消订单");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("订单已提交");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidL.setVisibility(View.GONE);
			layoutStateMidR.setVisibility(View.GONE);
			tvStateRight.setText("订单取消");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.GONE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBuyAgain.setVisibility(View.VISIBLE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.GONE);
			rlBottomBar.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.GONE);
			break;
		case STATE_WAIT_PAY:
			tvState.setText("等待付款");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("订单已提交");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_blue);
			tvStateMidL.setText("等待商家接单");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidR.setText("商家已接单");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidL.setVisibility(View.VISIBLE);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("配送员取货中");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.VISIBLE);
			tvComplain.setVisibility(View.GONE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.VISIBLE);
			String paymentExpireTime = valueEntity.getPaymentExpireTime();
			if (paymentExpireTime != null) {
				tvUnPayGoPay.setTimes(getTimeBetween(valueEntity.getServerTime(), paymentExpireTime));
			} else {
				tvUnPayGoPay.setVisibility(View.GONE);
			}
			break;
		case STATE_WAIT_CONFIRM:
			tvState.setText("等待商家确认");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("订单已提交");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidL.setText("等待商家接单");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_blue);
			layoutStateMidL.setVisibility(View.VISIBLE);
			tvStateMidR.setText("商家已接单");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("配送员取货中");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.VISIBLE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBottomCancel.setVisibility(View.VISIBLE);
			waitArriveLayout.setVisibility(View.GONE);
			rlBottomBar.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.GONE);
			break;
		case STATE_ACCEPED:
			tvState.setText("商家已接单");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("等待商家接单");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidL.setText("商家已接单");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_blue);
			layoutStateMidL.setVisibility(View.VISIBLE);
			tvStateMidR.setText("配送员取货中");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("配送员已取货");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.GONE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBuyAgain.setVisibility(View.VISIBLE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.VISIBLE);
			rlBottomBar.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.GONE);
			break;
		case STATE_WAIT_TAKE:
			tvState.setText("配送员取货中");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("商家已接单");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidL.setText("配送员取货中");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_blue);
			layoutStateMidL.setVisibility(View.VISIBLE);
			tvStateMidR.setText("等待送达");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("完成");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.GONE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBuyAgain.setVisibility(View.VISIBLE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.VISIBLE);
			ValueEntity.DeliveryTaskEntity deliveryTask = submitOrderEntity.getDeliveryTask();
			if(deliveryTask != null) {
				initMap(submitOrderEntity);
			}
			rlBottomBar.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.GONE);
			break;
		case STATE_HAS_TAKE:
		case STATE_WAIT_DELIVERY:
			tvState.setText("等待送达");
//			tvStateDes.setText("预计送达时间：" + showOrderTime(submitOrderEntity));
			tvStateLeft.setText("商家已接单");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidL.setText("配送员取货中");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidL.setVisibility(View.VISIBLE);
			tvStateMidR.setText("等待送达");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_blue);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("完成");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_gray);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.VISIBLE);
			tvEvaluate.setVisibility(View.GONE);
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBuyAgain.setVisibility(View.VISIBLE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.GONE);
			int shipmentType = submitOrderEntity.getShipmentType();
			if(shipmentType == 2) {
				initMap(submitOrderEntity);
			}
			tvUnPayGoPay.setVisibility(View.GONE);
			break;
		case STATE_DONE:
			tvState.setText("完成");
//			tvStateDes.setText("送达时间：" + CommonUtils.formatTime(Long.parseLong(submitOrderEntity.getExpectArrivalTime()), CommonUtils.yyyy_MM_dd_HH_mm_ss));
			tvStateLeft.setText("商家已接单");
			imgStateLeft.setBackgroundResource(R.drawable.icon_point_gray);
			tvStateMidL.setText("配送员取货中");
			imgStateMidL.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidL.setVisibility(View.VISIBLE);
			tvStateMidR.setText("等待送达");
			imgStateMidR.setBackgroundResource(R.drawable.icon_point_gray);
			layoutStateMidR.setVisibility(View.VISIBLE);
			tvStateRight.setText("完成");
			imgStateRight.setBackgroundResource(R.drawable.icon_point_blue);
			unPayBottonLayout.setVisibility(View.GONE);
			tvComplain.setVisibility(View.GONE);
			if(submitOrderEntity.getHasComments() != 1){
				tvEvaluate.setVisibility(View.VISIBLE);
			}else{
				rlBottomBar.setVisibility(View.GONE);
			}
			tvBuyBottomAgain.setVisibility(View.GONE);
			tvBuyAgain.setVisibility(View.VISIBLE);
			tvBottomCancel.setVisibility(View.GONE);
			waitArriveLayout.setVisibility(View.GONE);
			tvUnPayGoPay.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	private long getTimeBetween(String serverTime, String laterTime) {

		try {
			Date date1 = sdf.parse(serverTime);
			long time1 = date1.getTime();
			Date date2 = sdf.parse(laterTime);
			long time2 = date2.getTime();
			return time2 - time1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void onDestroy() {
		baiduMap.setMyLocationEnabled(false);
		baiduMap.clear();
		baiduMap = null;
		mapView.onDestroy();
		mapView = null;
		super.onDestroy();
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	private void initMap(ValueEntity submitOrderEntity) {
		mapView.setVisibility(View.VISIBLE);
		ValueEntity.DeliveryTaskEntity.DeliverymanEntity deliveryman = submitOrderEntity.getDeliveryTask().getDeliveryman();
		locationCurrentSite(deliveryman.getLatitude(), deliveryman.getLongitude());
//		mapCache.setImageBitmap(mapView.getDrawingCache());
//		mapView.setVisibility(View.GONE);
	}

	/**
	 * 定位当前地位置
	 *
	 * @param
	 */
	private void locationCurrentSite(double lat, double lon) {
		MyLocationData locData = new MyLocationData.Builder()
				.latitude(lat)
				.longitude(lon)
				.build();
		baiduMap.setMyLocationData(locData);

		baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(19).build()));
		MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.car));
		baiduMap.setMyLocationConfigeration(config);
	}

	private void hideBaiduMapChildView(){
		mapView.showScaleControl(false);
		mapView.showZoomControls(false);
		// 隐藏指南针
		UiSettings mUiSettings = baiduMap.getUiSettings();
		mUiSettings.setCompassEnabled(false);
		// 删除百度地图logo
		mapView.removeViewAt(1);
	}

	/**
	 * 商家信息
	 *
	 */
	private void showCommercial(ValueEntity submitOrderEntity) {
		ImageUtils.loadBitmap(this , submitOrderEntity.getMerchant().getLogo(), imgCommercial, R.drawable.pic_jixiang , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
		tvCommercialName.setText(submitOrderEntity.getMerchant().getName());
		layoutMeal.removeAllViews();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.topMargin = DipToPx.dip2px(OrderDetailActivity.this, 10);
		if (CheckUtils.isNoEmptyList(submitOrderEntity.getOrderItems())) {
			for (int i = 0; i < submitOrderEntity.getOrderItems().size(); i++) {
				ValueEntity.OrderItemsEntity orderItem = submitOrderEntity.getOrderItems().get(i);
				View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_meal_item, null);
				TextView tvMealName = (TextView) view.findViewById(R.id.order_detail_commercial_meal);
				TextView tvMealMoney = (TextView) view.findViewById(R.id.order_detail_commercial_meal_money);
				TextView tvMealCount = (TextView) view.findViewById(R.id.order_detail_commercial_meal_count);
				tvMealName.setText(orderItem.getName());
				tvMealMoney.setText("¥" + StringUtils.BigDecimal2Str(orderItem.getTotalPrice()));
				tvMealCount.setText("×" + orderItem.getQuantity());
				layoutMeal.addView(view, params);
			}
		}
		layoutShip.removeAllViews();
		if(submitOrderEntity.getBoxPrice() != null && submitOrderEntity.getBoxPrice().compareTo(BigDecimal.ZERO) > 0){
			View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
			TextView tvPackageName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
			TextView tvPackageMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
			tvPackageName.setText("餐盒费");
			tvPackageMoney.setText("¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getBoxPrice()));
			layoutShip.addView(view, params);
		}
		if(submitOrderEntity.getShippingFee() != null && submitOrderEntity.getShippingFee().compareTo(BigDecimal.ZERO) > 0){
			View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
			TextView tvShippingName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
			TextView tvShippingMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
			tvShippingName.setText("配送费");
			tvShippingMoney.setText("¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getShippingFee()));
			layoutShip.addView(view, params);
		}
		if(submitOrderEntity.getRedBagTotalAmt() != null && submitOrderEntity.getRedBagTotalAmt().compareTo(BigDecimal.ZERO) > 0){
			View view = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_package_and_shipping_item, null);
			TextView tvRedBagName = (TextView) view.findViewById(R.id.order_detail_commercial_shipping);
			TextView tvRedBagMoney = (TextView) view.findViewById(R.id.order_detail_commercial_shipping_money);
			tvRedBagName.setText("红包金额");
			tvRedBagMoney.setText("- ¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getRedBagTotalAmt()));
			layoutShip.addView(view, params);
		}
		layoutCampaigns.removeAllViews();
		if (CheckUtils.isNoEmptyList(submitOrderEntity.getPromoList())) {
			for (int i = 0, size = submitOrderEntity.getPromoList().size(); i < size; i++) {
				View view1 = LayoutInflater.from(OrderDetailActivity.this).inflate(R.layout.order_detail_campaigns_item, null);
				ImageView ivIcon = (ImageView) view1.findViewById(R.id.order_detail_commercial_promotion_icon);
				TextView tvLimit = (TextView) view1.findViewById(R.id.order_detail_commercial_campaign_limit);
				TextView tvAmount = (TextView) view1.findViewById(R.id.order_detail_commercial_meal_campaign_amount);
				ImageUtils.loadBitmap(mActivity , submitOrderEntity.getPromoList().get(i).getPromoImg(), ivIcon, R.drawable.jian , "");
				tvLimit.setText(submitOrderEntity.getPromoList().get(i).getPromoName());
				if (submitOrderEntity.getPromoList().get(i).getDiscountAmt() != null) {
					tvAmount.setText("- ¥" + submitOrderEntity.getPromoList().get(i).getDiscountAmt());
				}
				layoutCampaigns.addView(view1, params);
			}
			View viewLine = new View(this);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DipToPx.dip2px(this, 0.5f));
			layoutParams.setMargins(0,DipToPx.dip2px(this, 10f),0,0);
			viewLine.setLayoutParams(layoutParams);
			viewLine.setBackgroundColor(getResources().getColor(R.color.gray_bg));
			layoutCampaigns.addView(viewLine);
		}
		tvAllMoney.setText("合计：¥" + StringUtils.BigDecimal2Str(submitOrderEntity.getTotalPrice()));
		layoutCommercial.setOnClickListener(this);
		tvBuyAgain.setOnClickListener(this);
	}

	/**
	 * 配送方式
	 *
	 */
	private void showDelivery(ValueEntity submitOrderEntity) {
		tvShippingStyle.setText("配送方式："+ ShipmentMode.getShipmentModeByValue(submitOrderEntity.getShipmentType()).getMemo());
	}

	/**
	 * 详情展示
	 * 
	 * @param submitOrderEntity
	 */
	private void showDetail(ValueEntity submitOrderEntity) {
		tvOrderNum.setText("订单号: " + submitOrderEntity.getId());
		tvOrderTime.setText("下单时间: " + submitOrderEntity.getCreateTime());
		tvPersonName.setText("联系人: \u3000" + submitOrderEntity.getUserName() + " " + submitOrderEntity.getUserGender());
		tvPersonPhone.setText("联系电话: " + submitOrderEntity.getUserMobile());
		tvPersonAddress.setText("收货地址: " + submitOrderEntity.getUserAddress());
		tvPersonPayType.setText("支付方式: " + PaymentMode.getPaymentModeByValue(submitOrderEntity.getPaymentType()).getMemo());
		if(!TextUtils.isEmpty(submitOrderEntity.getCaution())) tvOrderCaution.setText(submitOrderEntity.getCaution());
		String expectDeliveryTime = submitOrderEntity.getExpectArrivalTime();
		if (CheckUtils.isNoEmptyStr(expectDeliveryTime)) {
			if (expectDeliveryTime.equals("1")) {
				tvArriveTime.setText("送达时间: 立即送达");
			} else {
				tvArriveTime.setText("送达时间: " + CommonUtils.formatTime(Long.parseLong(expectDeliveryTime),CommonUtils.yyyy_MM_dd_HH_mm_ss));
			}
		}

	}

	/**
	 * 下单时间
	 *
	 * @param submitOrderEntity
	 */
	private String showOrderTime(ValueEntity submitOrderEntity) {
		String time = "";
		try {
			Date date = dateFormat.parse(submitOrderEntity.getCreateTime());
//			Date expectDate = dateFormat.parse(submitOrderEntity.getExpectArrivalTime());
//			time += timeFormat.format(expectDate);
			if(new Date().getTime() - date.getTime() < 5 * ONE_MINUTE){
				time += "\u3000刚刚下单";
			}else{
				time += "\u3000下单" + (new Date().getTime() - date.getTime())/ONE_MINUTE + "分钟";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 投诉电话联系
	 */
	private void initAvatarDialog() {
		avatarDialog = new Dialog(this, R.style.fullDialog);
		RelativeLayout contentView = (RelativeLayout) View.inflate(this, R.layout.pick_or_take_photo_dialog, null);
		Button dialog_bt_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
		Button dialog_bt_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
		Button dialog_bt_cancel = (Button) contentView.findViewById(R.id.btn_cancel);
		dialog_bt_take_photo.setText("联系商家");
		dialog_bt_pick_photo.setText("联系客服");
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

	@Override
	public void onBackPressed() {
		Intent intentHome = new Intent(this, HomeActivity.class);
		intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intentHome);
		back();
	}
}
