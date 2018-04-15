package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.GoodsDetailActivity;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.CornerImageView;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.PinnedHeaderListView.SectionedBaseAdapter;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsSectionHeaderAdapter extends SectionedBaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<Menu> menuList;
	private List<Menu> menuListTemp;
	private BottomCartListener listener;
	private Merchant merchant;
	private CustomDialog dialog;

	public GoodsSectionHeaderAdapter(Context context,Merchant merchant) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.menuList = new ArrayList<Menu>();
		this.menuListTemp = new ArrayList<Menu>();
		this.merchant = merchant;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		menuListTemp = menuList;
		if(menuList != null) {
			deal(menuList);
		}
		notifyDataSetChanged();
	}

	private List<Menu> deal(List<Menu> menuList) {
		this.menuList = new ArrayList<Menu>();
		for (Menu menu : menuList) {
			if(menu.getGoodsList().size() > 0){
				this.menuList.add(menu);
			}
		}
		return menuList;
	}

	@Override
	public Object getItem(int section, int position) {
		return menuList.get(section).getGoodsList().get(position);
	}

	@Override
	public long getItemId(int section, int position) {
		return position;
	}

	@Override
	public int getSectionCount() {
		return menuList.size();
	}

	@Override
	public int getCountForSection(int section) {
		return menuList.get(section).getGoodsList().size();
	}

	@Override
	public View getItemView(int section, int position, View convertView, ViewGroup parent) {
		ItemViewHolder holder = null;
		if (convertView == null) {
			holder = new ItemViewHolder();
			convertView = inflater.inflate(R.layout.goods_item, null);
			holder.img = (CornerImageView) convertView.findViewById(R.id.goods_item_img);
			holder.tvName = (TextView) convertView.findViewById(R.id.goods_item_tv_name);
			holder.tvDes = (TextView) convertView.findViewById(R.id.goods_item_tv_des);
			holder.imgLayout = (LinearLayout) convertView.findViewById(R.id.goods_item_img_layout);
			holder.barScore = (RatingBar) convertView.findViewById(R.id.goods_item_img_rat_score);
			holder.tvCommentCount = (TextView) convertView.findViewById(R.id.goods_item_tv_comment_count);
			holder.tvSellCount = (TextView) convertView.findViewById(R.id.goods_item_tv_sell_count);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.goods_item_tv_price);
			holder.imgAdd = (ImageView) convertView.findViewById(R.id.goods_item_img_add);
			holder.tvBuyCount = (TextView) convertView.findViewById(R.id.goods_item_tv_buy_count);
			holder.imgMinus = (ImageView) convertView.findViewById(R.id.goods_item_img_minus);
			holder.specMinus = (ImageView) convertView.findViewById(R.id.goods_item_img_minus_spec);
			holder.rlHideBuyCount = (RelativeLayout) convertView.findViewById(R.id.buy_count_hide);
			holder.tvChooseSpec = (TextView) convertView.findViewById(R.id.goods_item_choose_spec);
			holder.specCount = (TextView) convertView.findViewById(R.id.goods_item_tv_buy_count_spec);
			holder.tvSleep = (TextView) convertView.findViewById(R.id.merchant_sleep);
			holder.divideLine = convertView.findViewById(R.id.divider_line);

			convertView.setTag(holder);
		} else {
			holder = (ItemViewHolder) convertView.getTag();
		}
		showItem(section, position, convertView, holder);
		return convertView;
	}

	@Override
	public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
		HeaderViewHolder holder = null;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.goods_select_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.goods_select_item_tv_name);
			holder.tvDes = (TextView) convertView.findViewById(R.id.goods_select_item_tv_des);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		holder.tvName.setText(menuList.get(section).getName());
		holder.tvDes.setText(menuList.get(section).getDescription());
		return convertView;
	}

	public void setListener(BottomCartListener listener) {
		this.listener = listener;
	}

	static class ItemViewHolder {
		CornerImageView img;
		TextView tvName;
		TextView tvDes;
		LinearLayout imgLayout;
		RatingBar barScore;
		TextView tvCommentCount;
		TextView tvSellCount;
		TextView tvPrice;
		ImageView imgAdd;
		TextView tvBuyCount,specCount;
		ImageView imgMinus,specMinus;
		RelativeLayout rlHideBuyCount;
		TextView tvChooseSpec,tvSleep;
		View divideLine;
	}

	static class HeaderViewHolder {
		TextView tvName;
		TextView tvDes;
	}

	private void showItem(int section, int position, View convertView, final ItemViewHolder holder) {
		if (CheckUtils.isNoEmptyList(menuList) && menuList.size() > section) {
			Menu menu = menuList.get(section);
			if (menu != null) {
				List<Goods> goodsList = menu.getGoodsList();
				if (CheckUtils.isNoEmptyList(goodsList) && goodsList.size() > position) {
					if (menuList.size() - 1 > section && position == goodsList.size() - 1){
						holder.divideLine.setVisibility(View.INVISIBLE);
					}else{
						holder.divideLine.setVisibility(View.VISIBLE);
					}
					final Goods goods = goodsList.get(position);
					if (goods != null) {
						if(CheckUtils.isNoEmptyStr(goods.getImgs())){
							String[] strings = goods.getImgs().split(";");
							String imgUrl = strings[0];
							holder.img.setImageResource(R.drawable.home_default);
							if (CheckUtils.isNoEmptyStr(imgUrl)) {
								holder.img.setVisibility(View.VISIBLE);
								ImageUtils.loadBitmap(context , imgUrl, holder.img, R.drawable.home_default , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL);
							}
						}else{
							holder.img.setImageResource(R.drawable.home_default);
						}
						if(CheckUtils.isNoEmptyStr(goods.getName()))
							holder.tvName.setText(goods.getName());
						if(CheckUtils.isNoEmptyStr(goods.getDescription())) {
							holder.tvDes.setVisibility(View.VISIBLE);
							holder.tvDes.setText(goods.getDescription());
						}else{
							holder.tvDes.setVisibility(View.GONE);
						}
						if(CheckUtils.isNoEmptyList(goods.getGoodsSpecList())){
							holder.tvPrice.setText(StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(0).getPrice()));
						}
						if(goods.getCommentScore()!=null)
							holder.barScore.setRating(goods.getCommentScore().floatValue());
						holder.tvSellCount.setText("月售" + goods.getMonthSaled() + "份");
						if(goods.getCommentScore()!=null)
							holder.tvCommentCount.setText(goods.getCommentNum() + "评价");
						showBuyView(goods, holder);
						convertView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(context, GoodsDetailActivity.class);
								intent.putExtra("Goods", goods);
								intent.putExtra("Merchant", merchant);
								((Activity) context).startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
								((Activity) context).overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
							}
						});
					}
				}
			}
		}
	}

    private void showBuyView(final Goods goods, final ItemViewHolder holder) {
		if(merchant.getStatus() == 0){
			holder.tvSleep.setVisibility(View.VISIBLE);
			holder.rlHideBuyCount.setVisibility(View.GONE);
			holder.tvChooseSpec.setVisibility(View.GONE);
			holder.specCount.setVisibility(View.GONE);
			holder.specMinus.setVisibility(View.GONE);
			return;
		}

		if(goods.getStatus() == 0){
			holder.tvSleep.setVisibility(View.VISIBLE);
			holder.tvSleep.setText("商品已售罄");
			holder.rlHideBuyCount.setVisibility(View.GONE);
			holder.tvChooseSpec.setVisibility(View.GONE);
			holder.specCount.setVisibility(View.GONE);
			holder.specMinus.setVisibility(View.GONE);
		}else {
			holder.tvSleep.setVisibility(View.GONE);
			if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
				holder.rlHideBuyCount.setVisibility(View.VISIBLE);
				holder.tvChooseSpec.setVisibility(View.GONE);
				holder.specCount.setVisibility(View.GONE);
				holder.specMinus.setVisibility(View.GONE);
				final GoodsSpec goodsSpec = goods.getGoodsSpecList().get(0);
				List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
				for (PickGoods pickGoods : pickGoodsList) {
					if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
						goodsSpec.setBuyCount(pickGoods.getPickCount());
						break;
					}
				}
				holder.tvBuyCount.setText(goodsSpec.getBuyCount() + "");
				if (goodsSpec.getBuyCount() > 0) {
					holder.imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
					holder.tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
				} else {
					holder.imgMinus.setTranslationX(0f);
					holder.tvBuyCount.setTranslationX(0f);
				}

				holder.imgAdd.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int count = goodsSpec.getBuyCount();
						if (count == 0) {
							count++;
							goodsSpec.setBuyCount(count);
							holder.tvBuyCount.setText(count + "");
							AnimatorUtils.leftTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
							AnimatorUtils.leftTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
						} else {
							count++;
							holder.tvBuyCount.setText(count + "");
							goodsSpec.setBuyCount(count);
						}
						//只要点击了就去更新购物车
						listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, true);

						int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
						v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
						ImageView ball = new ImageView(context);// buyImg是动画的图片
						ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
						ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
						params.width = 15;
						params.height = 15;
						ball.setLayoutParams(params);
						((CommercialActivity) context).setAnim(ball, startLocation);// 开始执行动画
					}
				});

				holder.imgMinus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int count = goodsSpec.getBuyCount();
						if (count == 1) {
							count--;
							goodsSpec.setBuyCount(count);
							holder.tvBuyCount.setText(count + "");
							AnimatorUtils.rightTranslationRotating(holder.imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, context));
							AnimatorUtils.rightTranslationRotating(holder.tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, context));
							//只要点击了就去更新购物车
							listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false);
						} else {
							if (count > 0) {
								count--;
								holder.tvBuyCount.setText(count + "");
								goodsSpec.setBuyCount(count);
								//只要点击了就去更新购物车
								listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
							}
						}
					}
				});
			} else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1) {
				holder.rlHideBuyCount.setVisibility(View.GONE);
				holder.tvChooseSpec.setVisibility(View.VISIBLE);
				holder.specCount.setVisibility(View.VISIBLE);
				holder.specMinus.setVisibility(View.VISIBLE);
				int num = 0;
				for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
					GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
					List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
					for (PickGoods pickGoods : pickGoodsList) {
						if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
							num += pickGoods.getPickCount();
							break;
						}
					}
				}

				holder.specCount.setText(num + "");
				if (num > 0) {
					holder.specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
					holder.specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
				} else {
					holder.specMinus.setTranslationX(0f);
					holder.specCount.setTranslationX(0f);
				}

				holder.tvChooseSpec.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(goods, holder);
					}
				});
				BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
				for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
					if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
						price = goods.getGoodsSpecList().get(i).getPrice();
					}
				}
				holder.tvPrice.setText(StringUtils.BigDecimal2Str(price) + "起");

				holder.specMinus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
//					int count = goodsSpec.getBuyCount();
						GoodsSpec goodsSpec1 = null;
						int num = 0;
						int specNum = 0;
						for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
							GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
							List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
							for (PickGoods pickGoods : pickGoodsList) {
								if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
									num += pickGoods.getPickCount();
									specNum += 1;
									goodsSpec1 = goodsSpec;
									break;
								}
							}
						}
						if (specNum > 1) {
							if(dialog == null) {
								dialog = new CustomDialog(context , onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
								dialog.show();
							}else{
								dialog.show();
							}
						} else {
							if (num == 1) {
								num--;
								holder.tvBuyCount.setText(num + "");
								goodsSpec1.setBuyCount(num);
								AnimatorUtils.rightTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
								AnimatorUtils.rightTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
								//只要点击了就去更新购物车
								listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
							} else {
								if (num > 0) {
									num--;
									goodsSpec1.setBuyCount(num);
									listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
								}
							}

						}
					}
				});
			}
		}
    }

	CustomDialog.onBtnClickListener onBtnClickListener = new CustomDialog.onBtnClickListener() {
		@Override
		public void onSure() {
			if(dialog != null && dialog.isShowing()){
				dialog.dismiss();
			}
		}

		@Override
		public void onExit() {

		}
	};

	private void showDialog(final Goods goods, final ItemViewHolder holder){
		final Dialog chooseSpecDialog = new Dialog(context, R.style.chooseSpecDialog);
		LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.goods_spec_choose_dialog, null);
		TextView tvName = (TextView) contentView.findViewById(R.id.tv_goods_name);
		final TextView tvChoosedSpec = (TextView) contentView.findViewById(R.id.tv_choosed_goods_spec);
		final TextView tvGoodsPrice = (TextView) contentView.findViewById(R.id.tv_goods_price);
		final FlowLayout flGoodsSpec = (FlowLayout) contentView.findViewById(R.id.goods_spec_flow_layout);
		Button btnConfirm = (Button) contentView.findViewById(R.id.btn_confirm_goods_spec);

		tvName.setText(goods.getName());
		final List<View> viewList = new ArrayList<>();
		for(int i=0; i<goods.getGoodsSpecList().size(); i++){
			CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(0, 0, DipToPx.dip2px(context, 15), DipToPx.dip2px(context, 5));
			checkBox.setLayoutParams(layoutParams);
			if(i==0){
				checkBox.setChecked(true);
				checkBox.setTextColor(context.getResources().getColor(R.color.org_light));
				tvChoosedSpec.setText(goods.getGoodsSpecList().get(i).getSpec());
				tvGoodsPrice.setText(StringUtils.BigDecimal2Str(goods.getGoodsSpecList().get(i).getPrice()));
			}
			checkBox.setText(goods.getGoodsSpecList().get(i).getSpec());
			checkBox.setTag(goods.getGoodsSpecList().get(i));
			checkBox.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int j = 0; j < viewList.size(); j++) {
						((CheckBox) viewList.get(j)).setTextColor(Color.BLACK);
						((CheckBox) viewList.get(j)).setChecked(false);
					}
					GoodsSpec goodsSpec = (GoodsSpec) v.getTag();
					tvChoosedSpec.setText(goodsSpec.getSpec());
					tvGoodsPrice.setText(StringUtils.BigDecimal2Str(goodsSpec.getPrice()));
					((CheckBox) v).setTextColor(context.getResources().getColor(R.color.org_light));
					((CheckBox) v).setChecked(true);
				}
			});
			viewList.add(checkBox);
			flGoodsSpec.addView(checkBox);
		}

		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int specNum = 0;
				for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
					GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
					List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
					for(PickGoods pickGoods:pickGoodsList){
						if(pickGoods.getGoodsId()==goods.getId()&&pickGoods.getGoodsSpecId()==goodsSpec.getId()){
							specNum += 1;
							break;
						}
					}
				}

				for(int i=0; i<viewList.size(); i++){
					if(((CheckBox) viewList.get(i)).isChecked()){
						GoodsSpec goodsSpec = (GoodsSpec) viewList.get(i).getTag();
						int count = goodsSpec.getBuyCount();
//						if(count == 0 && specNum == 0 ){
//							AnimatorUtils.leftTranslationRotating(holder.specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, context));
//							AnimatorUtils.leftTranslationRotating(holder.specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, context));
//						}
						//购买之前查询单例里是否存在
						List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
						for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
							List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
							for (PickGoods pickGood : pickGoods) {
								if(goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
										goods.getCategoryId() == pickGood.getMenuId() &&
										goods.getId() == pickGood.getGoodsId() &&
										goodsSpec.getId() == pickGood.getGoodsSpecId()){
									count = pickGood.getPickCount();
								}
							}
						}
						count++;
						goodsSpec.setBuyCount(count);
						listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), count, false, false);
						break;
					}
				}
				chooseSpecDialog.dismiss();
			}
		});
		chooseSpecDialog.setContentView(contentView, new LinearLayout.LayoutParams(DeviceParameter.getIntScreenWidth() - DipToPx.dip2px(context, 40), LinearLayout.LayoutParams.WRAP_CONTENT));
		chooseSpecDialog.setCanceledOnTouchOutside(true);
		chooseSpecDialog.show();
	}
}
