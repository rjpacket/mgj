package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.BottomCartListAdapter;
import com.project.mgjandroid.ui.fragment.GoodsDetailFragment;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.HeaderViewPagerLayout;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.AutoScrollViewPager;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 *
 * @author jian
 */
public class GoodsDetailActivity extends BaseActivity implements OnClickListener, BottomCartListener {
    @InjectView(R.id.goods_detail_act_back)
    private ImageView imgBack;
    @InjectView(R.id.goods_detail_act_back_default)
    private ImageView ivBack;
    @InjectView(R.id.goods_detail_act_iv_share)
    private ImageView imgShare;
    @InjectView(R.id.goods_img)
    private RelativeLayout image_container;
    @InjectView(R.id.detail_banner)
    private MyBanner detailBanner;
    @InjectView(R.id.goods_detail_top_bar)
    private RelativeLayout topBar;
    @InjectView(R.id.goods_detail_price_bar)
    private RelativeLayout priceBar;
    @InjectView(R.id.goods_item_tv_price)
    private TextView tv_barPrice;
    @InjectView(R.id.commercial_act_cart)
    private ImageView img_cart;
    @InjectView(R.id.cart_num)
    private TextView tv_num;
    @InjectView(R.id.commercial_act_bottom_money)
    private TextView tv_allMoney;
    @InjectView(R.id.commercial_act_bottom_shipping_and_box)
    private RelativeLayout rlCartShipingAndBox;
    @InjectView(R.id.commercial_act_bottom_shipping)
    private TextView tv_cart_shipping;
    @InjectView(R.id.commercial_act_bottom_package)
    private TextView tv_cart_package;
    @InjectView(R.id.commercial_act_bottom_qisong)
    private TextView tv_cart_qisong;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomLayout;
    @InjectView(R.id.linear_cover)
    private LinearLayout linearCover;
    @InjectView(R.id.commercial_act_go_account)
    private TextView tv_goAccount;
    @InjectView(R.id.commercial_act_bottom_car)
    private RelativeLayout bottomCart;
    @InjectView(R.id.goods_item_img_add)
    private ImageView act_add;
    @InjectView(R.id.goods_item_tv_buy_count)
    private TextView tvBuyCount;
    @InjectView(R.id.goods_item_img_minus)
    private ImageView imgMinus;
    @InjectView(R.id.buy_count_hide)
    private RelativeLayout rlHideBuyCount;

    @InjectView(R.id.buy_count_hide_spec)
    private RelativeLayout rlSpecLabel;
    @InjectView(R.id.goods_item_img_minus_spec)
    private ImageView specMinus;
    @InjectView(R.id.goods_item_tv_buy_count_spec)
    private TextView specCount;
    @InjectView(R.id.goods_item_choose_spec)
    private TextView tvChooseSpec;
    @InjectView(R.id.goods_item_merchant_sleep)
    private TextView tvSleep;
    @InjectView(R.id.goods_detail_view_pager)
    private ViewPager mViewPager;
    @InjectView(R.id.scrollableLayout)
    private HeaderViewPagerLayout mHeaderViewPagerLayout;

    private Goods goods;

    @InjectView(R.id.goods_detail_name)
    private TextView tv_goodsName;
    @InjectView(R.id.goods_detail_rat_score)
    private RatingBar rb_star;
    @InjectView(R.id.goods_detail_judge)
    private TextView tv_judge;
    @InjectView(R.id.goods_detail_count)
    private TextView tv_saleNumber;
    @InjectView(R.id.goods_deatil_food_name)
    private TextView tvTitle;

    private PopupWindow mPopWindow;
    private List<PickGoods> mCartProducts;
    private ListView bottomListView;
    private BottomCartListAdapter bottomAdapter;
    private RelativeLayout relativeCenter;
    private Merchant merchant;
    private ViewGroup anim_mask_layout;
    private GoodsSpec goodsSpec;
    private Context mContext;
    private LayoutInflater inflater;
    private int currentType = -1;

    ArrayList<GoodsEvaluateModel.ValueEntity> data = new ArrayList<GoodsEvaluateModel.ValueEntity>();
    private String errorMsg;
    private List<PickGoods> pickGoods;
    private JSONObject previewJsonData;
    private double latitude;
    private double longitude;
    private ConfirmOrderModel confirmOrderModel;
    private TextView tvEvaluate;
    private CustomDialog dialog;
    private List<HeaderViewPagerFragment> mFragments;
    private MLoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.goods_detail_act);
        Injector.get(this).inject();
        mLoadingDialog = new MLoadingDialog();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image_container.getLayoutParams();
        layoutParams.height = CommonUtils.getScreenWidth(mActivity.getWindowManager());
        image_container.setLayoutParams(layoutParams);

        mContext = this;
        inflater = LayoutInflater.from(mContext);
        goods = (Goods) getIntent().getExtras().getSerializable("Goods");
        merchant = (Merchant) getIntent().getExtras().getSerializable("Merchant");
        initViews();
        if (merchant.getShipFee().compareTo(BigDecimal.ZERO) == 1) {
            tv_cart_shipping.setText("另需配送费¥" + StringUtils.BigDecimal2Str(merchant.getShipFee()));
            tv_cart_package.setTextSize(10);
        } else {
            tv_cart_shipping.setVisibility(View.GONE);
            rlCartShipingAndBox.setVisibility(View.GONE);
            tv_cart_package.setTextSize(14);
        }
        tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
        setPageData(goods);
        initPopWindow();
        if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
            //TODO 一种规格
            currentType = 1;
            rlHideBuyCount.setVisibility(View.VISIBLE);
            tvChooseSpec.setVisibility(View.GONE);
            rlSpecLabel.setVisibility(View.GONE);
            goodsSpec = goods.getGoodsSpecList().get(0);
            if (goodsSpec.getBuyCount() > 0) {
                imgMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                tvBuyCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                tvBuyCount.setText(goodsSpec.getBuyCount() + "");
            } else {
                imgMinus.setTranslationX(0f);
                tvBuyCount.setTranslationX(0f);
            }
        } else if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1) {
            //TODO 多种规格
            currentType = 2;
            rlHideBuyCount.setVisibility(View.GONE);
            tvChooseSpec.setVisibility(View.VISIBLE);
            tvChooseSpec.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(goods);
                }
            });
            rlSpecLabel.setVisibility(View.VISIBLE);

            int num = 0;
            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                        num += pickGoods.getPickCount();
                        break;
                    }
                }
            }

            specCount.setText(num + "");
            if (num > 0) {
                specMinus.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                specCount.setTranslationX(PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            } else {
                specMinus.setTranslationX(0f);
                specCount.setTranslationX(0f);
            }

            BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
            for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                    price = goods.getGoodsSpecList().get(i).getPrice();
                }
            }
        }
        if (merchant.getStatus() == 0) {
            rlSpecLabel.setVisibility(View.GONE);
            tvSleep.setVisibility(View.VISIBLE);
            rlHideBuyCount.setVisibility(View.INVISIBLE);
            return;
        }
        if (goods.getStatus() == 0) {
            rlSpecLabel.setVisibility(View.GONE);
            tvSleep.setVisibility(View.VISIBLE);
            tvSleep.setText("商品已售罄");
            rlHideBuyCount.setVisibility(View.INVISIBLE);
            return;
        }
    }

    private void showDialog(final Goods goods) {
        final Dialog chooseSpecDialog = new Dialog(mContext, R.style.chooseSpecDialog);
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.goods_spec_choose_dialog, null);
        TextView tvName = (TextView) contentView.findViewById(R.id.tv_goods_name);
        final TextView tvChoosedSpec = (TextView) contentView.findViewById(R.id.tv_choosed_goods_spec);
        final TextView tvGoodsPrice = (TextView) contentView.findViewById(R.id.tv_goods_price);
        final FlowLayout flGoodsSpec = (FlowLayout) contentView.findViewById(R.id.goods_spec_flow_layout);
        Button btnConfirm = (Button) contentView.findViewById(R.id.btn_confirm_goods_spec);

        tvName.setText(goods.getName());
        final List<View> viewList = new ArrayList<>();
        for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.spec_checkbox, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, DipToPx.dip2px(mContext, 15), DipToPx.dip2px(mContext, 5));
            checkBox.setLayoutParams(layoutParams);
            if (i == 0) {
                checkBox.setChecked(true);
                checkBox.setTextColor(mContext.getResources().getColor(R.color.org_light));
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
                    ((CheckBox) v).setTextColor(mContext.getResources().getColor(R.color.org_light));
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
                    List<PickGoods> pickGoodsList = mCartProducts;
                    for (PickGoods pickGoods : pickGoodsList) {
                        if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                            specNum += 1;
                            break;
                        }
                    }
                }
                for (int i = 0; i < viewList.size(); i++) {
                    if (((CheckBox) viewList.get(i)).isChecked()) {
                        GoodsSpec goodsSpec = (GoodsSpec) viewList.get(i).getTag();
                        int count = goodsSpec.getBuyCount();
                        if (count == 0 && specNum == 0) {
                            AnimatorUtils.leftTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mActivity));
                            AnimatorUtils.leftTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mActivity));
                        }
                        //购买之前查询单例里是否存在
                        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
                        for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                            List<PickGoods> pickGoods = merchantPickGoods.getPickGoods();
                            for (PickGoods pickGood : pickGoods) {
                                if (goods.getMerchantId() == merchantPickGoods.getMerchantId() &&
                                        goods.getCategoryId() == pickGood.getMenuId() &&
                                        goods.getId() == pickGood.getGoodsId() &&
                                        goodsSpec.getId() == pickGood.getGoodsSpecId()) {
                                    count = pickGood.getPickCount();
                                }
                            }
                        }
                        count++;
                        goodsSpec.setBuyCount(count);
                        productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), count, false, false);
                        break;
                    }
                }
                chooseSpecDialog.dismiss();
            }
        });
        chooseSpecDialog.setContentView(contentView, new LinearLayout.LayoutParams(DeviceParameter.getIntScreenWidth() - DipToPx.dip2px(mContext, 40), LinearLayout.LayoutParams.WRAP_CONTENT));
        chooseSpecDialog.setCanceledOnTouchOutside(true);
        chooseSpecDialog.show();
    }

    /**
     * 初始化底部弹出框
     */
    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_commercial_bottom_cart, null);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(view);
        mPopWindow.setHeight(DipToPx.dip2px(mActivity, 250));
        mPopWindow.setOutsideTouchable(true);
        TextView textView = (TextView) view.findViewById(R.id.tv_clear_goods);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1) {
                    goodsSpec.setBuyCount(0);
                }else{
                    for (GoodsSpec spec : goods.getGoodsSpecList()) {
                        spec.setBuyCount(0);
                    }
                }
                PickGoodsModel.getInstance().setIsRemove(true);
                mCartProducts.clear();
                bottomAdapter.notifyDataSetChanged();
                setCart();
                mPopWindow.dismiss();

                linearCover.setVisibility(View.INVISIBLE);
                AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, mContext));
                AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mContext));
                AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, mContext));
                AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mContext));
                tvBuyCount.setText("0");
                specCount.setText("0");
            }
        });

        PickGoodsModel.getInstance().getMerchantPickGoodsList();
        initCartProducts();
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            setCart();
        }
        relativeCenter = (RelativeLayout) view.findViewById(R.id.linear_center);
        bottomListView = (ListView) view.findViewById(R.id.commercial_cart_list_view);
        bottomAdapter = new BottomCartListAdapter(this, mCartProducts, this);
        bottomListView.setAdapter(bottomAdapter);
    }

    private void initCartProducts() {
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if (CheckUtils.isNoEmptyList(merchantPickGoodsList)) {
            boolean contain = false;
            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                if (merchantPickGoods.getMerchantId() == merchant.getId()) { //TODO modify 商家id 有可能出错= =
                    if (merchantPickGoods.getPickGoods() != null) {
                        mCartProducts = merchantPickGoods.getPickGoods();
                    } else {
                        mCartProducts = new ArrayList<>();
                    }
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                mCartProducts = new ArrayList<>();
            }
        } else {
            mCartProducts = new ArrayList<>();
        }
    }

    private void setCart() {
        if (mCartProducts != null && mCartProducts.size() > 0) {
            //TODO 计算价格
            calculatePrice();
        } else {
            tv_num.setVisibility(View.INVISIBLE);
            tv_allMoney.setText("¥0");
            tv_cart_package.setVisibility(View.GONE);
            tv_cart_shipping.setTextSize(14);
            tv_cart_qisong.setVisibility(View.VISIBLE);
            tv_goAccount.setVisibility(View.GONE);
            tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
            if (tv_cart_shipping.getVisibility() == View.GONE) {
                rlCartShipingAndBox.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 检查购物车是否空了
     */
    private void checkCart() {
        if (mCartProducts.isEmpty()) {
            if (mPopWindow != null && mPopWindow.isShowing()) {
                mPopWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 计算价格/数量
     */
    private void calculatePrice() {
        BigDecimal num = BigDecimal.ZERO;
        BigDecimal num_package = BigDecimal.ZERO;
        int count = 0;
        if (mCartProducts != null && mCartProducts.size() > 0) {
            for (PickGoods pro : mCartProducts) {
                for (GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()) {
                    if (goodsSpec.getId() == pro.getGoodsSpecId()) {
                        num = num.add(goodsSpec.getPrice().multiply(BigDecimal.valueOf((long) pro.getPickCount())));
                        num_package = num_package.add(goodsSpec.getBoxPrice().multiply(BigDecimal.valueOf(goodsSpec.getBoxNum())).multiply(BigDecimal.valueOf(pro.getPickCount())));
                        break;
                    }
                }
                count += pro.getPickCount();
            }
            tv_num.setVisibility(View.VISIBLE);
            if(count > 99){
                tv_num.setTextSize(10);
            }else{
                tv_num.setTextSize(14);
            }
            tv_num.setText(count + "");
            tv_allMoney.setText("¥" + StringUtils.BigDecimal2Str(num));
            if (num_package.compareTo(BigDecimal.ZERO) == 1) {
                tv_cart_package.setText("包装费¥" + StringUtils.BigDecimal2Str(num_package));
                if (tv_cart_shipping.getVisibility() == View.GONE) {
                    rlCartShipingAndBox.setVisibility(View.VISIBLE);
                }
                tv_cart_package.setVisibility(View.VISIBLE);
                tv_cart_shipping.setTextSize(10);
            } else {
                tv_cart_package.setVisibility(View.GONE);
                if (tv_cart_shipping.getVisibility() == View.GONE) {
                    rlCartShipingAndBox.setVisibility(View.GONE);
                }
                tv_cart_shipping.setTextSize(14);
            }
            if (num.compareTo(merchant.getMinPrice()) != -1) {
                tv_cart_qisong.setVisibility(View.GONE);
                tv_goAccount.setVisibility(View.VISIBLE);
            } else {
                tv_cart_qisong.setVisibility(View.VISIBLE);
                tv_goAccount.setVisibility(View.GONE);
                tv_cart_qisong.setText("还差¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice().subtract(num)));
            }
        }
    }

    /**
     * 设置页面数据
     *
     * @param goods
     */
    private void setPageData(Goods goods) {
        if (goods != null) {
            /**
             * 此处请求商品评价 成功再添加数据到适配器
             */
            tvTitle.setText(goods.getName());
            tv_goodsName.setText(goods.getName());
            rb_star.setRating(goods.getCommentScore().floatValue());
            tv_judge.setText(goods.getCommentNum() + "评价");
            tv_saleNumber.setText("月售" + goods.getMonthSaled() + "份");

            List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
            if (goodsSpecList != null && goodsSpecList.size() == 1) {
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                tv_barPrice.setText(StringUtils.BigDecimal2Str(price));
            } else if (goodsSpecList != null && goodsSpecList.size() > 1) {
                BigDecimal price = goods.getGoodsSpecList().get(0).getPrice();
                for (int i = 1; i < goods.getGoodsSpecList().size(); i++) {
                    if (price.compareTo(goods.getGoodsSpecList().get(i).getPrice()) == 1) {
                        price = goods.getGoodsSpecList().get(i).getPrice();
                    }
                }
                tv_barPrice.setText(StringUtils.BigDecimal2Str(price) + "起");
            }
            if (CheckUtils.isNoEmptyStr(goods.getImgs())) {
                String[] strings = goods.getImgs().split(";");
                ArrayList<String> list = new ArrayList<>();
                for (String str : strings) {
                    list.add(str);
                }
                detailBanner.setUrls(list, false, false);
                    if(list.size() == 0){
                        ArrayList<Integer> list1 = new ArrayList<>();
                        list1.add(R.drawable.goods_detail);
                        detailBanner.setResources(list1);
                    }
            }else{
                ArrayList<Integer> list1 = new ArrayList<>();
                list1.add(R.drawable.goods_detail);
                detailBanner.setResources(list1);
            }
        }
    }

    private void initViews() {
        imgBack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        bottomCart.setOnClickListener(this);
        act_add.setOnClickListener(this);
        imgMinus.setOnClickListener(this);
        linearCover.setOnClickListener(this);
        tv_goAccount.setOnClickListener(this);
        specMinus.setOnClickListener(this);

        detailBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        mFragments = new ArrayList<>();
        GoodsDetailFragment goodsDetailFragment = new GoodsDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable("goods",goods);
        goodsDetailFragment.setArguments(arguments);
        mFragments.add(goodsDetailFragment);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        mHeaderViewPagerLayout.setCurrentScrollableContainer(mFragments.get(0));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mHeaderViewPagerLayout.setCurrentScrollableContainer(mFragments.get(position));
            }
        });
        mHeaderViewPagerLayout.setOnScrollListener(new HeaderViewPagerLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                topBar.setAlpha(currentY * 1.0f / maxY);
                if(currentY + DipToPx.dip2px(mActivity,48) >= maxY){
                    topBar.setVisibility(View.VISIBLE);
                    ivBack.setVisibility(View.GONE);
                }else{
                    topBar.setVisibility(View.GONE);
                    ivBack.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goods_detail_act_back:
            case R.id.goods_detail_act_back_default :
                onBackPressed();
                break;
            case R.id.goods_detail_act_iv_share :
                Intent intent = new Intent(mActivity, ShareActivity.class);
                intent.putExtra("Goods", goods);
                intent.putExtra("MerchantName", merchant.getName());
                startActivity(intent);
                break;
            case R.id.commercial_act_bottom_car://底部购物车的点击事件
                if (mCartProducts == null || mCartProducts.size() == 0) {
                    return;
                }
                if (mPopWindow != null) {
                    if (!mPopWindow.isShowing()) {
                        //设置popwindow显示位置
                        bottomCart.measure(0, 0);
                        int measuredHeight = bottomCart.getMeasuredHeight();
                        if(mCartProducts.size() >= 4){
                            bottomListView.setPadding(0,0,0,DipToPx.dip2px(mActivity,42));
                        }else{
                            bottomListView.setPadding(0,0,0,0);
                        }
                        mPopWindow.showAtLocation(bottomCart, Gravity.BOTTOM, 0, measuredHeight);
                        linearCover.setVisibility(View.VISIBLE);
                        AnimatorUtils.showBottom(relativeCenter, this);
                        AnimatorUtils.showBottom(bottomListView, this);
                        AnimatorUtils.alphaIn(linearCover, this);
                    } else {
                        mPopWindow.dismiss();
                        linearCover.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.linear_cover:
                //点击覆盖层相当于点击了一次购物车
                onClick(bottomCart);
                break;
            case R.id.goods_item_img_add:
            case R.id.goods_item_img_add1:
                int count = goodsSpec.getBuyCount();
                PickGoodsModel.getInstance().setIsRemove(false);
                if (count == 0) {
                    count++;
                    goodsSpec.setBuyCount(count);
                    tvBuyCount.setText(count + "");
                    AnimatorUtils.leftTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.leftTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                } else {
                    count++;
                    tvBuyCount.setText(count + "");
                    goodsSpec.setBuyCount(count);
                }
                //只要点击了就去更新购物车
                productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, true);

                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                ImageView ball = new ImageView(this);// buyImg是动画的图片，我的是一个小球（R.drawable.sign）
                ball.setImageResource(R.drawable.cart_point);// 设置buyImg的图片
                setAnim(ball, startLocation);// 开始执行动画
                break;
            case R.id.goods_item_img_minus:
            case R.id.goods_item_img_minus1:
                int count1 = goodsSpec.getBuyCount();
                if (count1 == 1) {
                    count1--;
                    goodsSpec.setBuyCount(count1);
                    tvBuyCount.setText(count1 + "");
                    AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                    //只要点击了就去更新购物车
                    productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), true, false);

                } else {
                    if (count1 > 0) {
                        count1--;
                        tvBuyCount.setText(count1 + "");
                        goodsSpec.setBuyCount(count1);
                        //只要点击了就去更新购物车
                        productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                    }
                }
                break;
            case R.id.goods_item_img_minus_spec:
            case R.id.goods_item_img_minus_spec1:
                GoodsSpec goodsSpec1 = null;
                int num = 0;
                int specNum = 0;
                for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec goodsSpec = goods.getGoodsSpecList().get(i);
                    List<PickGoods> pickGoodsList = mCartProducts;
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
                        dialog = new CustomDialog(mActivity, onBtnClickListener, "确定", "", "提示", "多种规格，请去购物车里删减");
                        dialog.show();
                    }else{
                        dialog.show();
                    }
                } else {
                    if (num == 1) {
                        num--;
                        tvBuyCount.setText(num + "");
                        goodsSpec1.setBuyCount(num);
                        AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, mActivity));
                        AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, mActivity));
                        //只要点击了就去更新购物车
                        productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), true, false);
                    } else {
                        if (num > 0) {
                            num--;
                            goodsSpec1.setBuyCount(num);
                            productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec1.getId(), goodsSpec1.getBuyCount(), false, false);
                        }
                    }

                }
                break;
            case R.id.commercial_act_go_account:
                if(!App.isLogin()){
                    Intent intent1 = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent1);
                    return;
                }
                getOrderPreview();
                break;
            case R.id.goods_detail_to_previous:
                AutoScrollViewPager vp = detailBanner.getViewPager();
                vp.setCurrentItem(vp.getCurrentItem()-1);
                break;
            case R.id.goods_detail_to_next:
                AutoScrollViewPager vp1 = detailBanner.getViewPager();
                vp1.setCurrentItem(vp1.getCurrentItem()+1);
                break;
            default:
                break;
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

    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 开启动画
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        if (anim_mask_layout == null)
            anim_mask_layout = createAnimLayout();
        else
            anim_mask_layout.removeAllViews();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_num.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1] - 40;// 动画位移的y坐标

        ObjectAnimator translateXAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, endX);
        translateXAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(view, "translationY", 0, endY);
        translateYAnimator.setInterpolator(new AnticipateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(translateXAnimator).with(translateYAnimator);
        set.setDuration(500);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //TODO 结束的逻辑代码
                setCart();
                v.setVisibility(View.INVISIBLE);
                anim_mask_layout.removeView(v);
            }
        });
    }

    /**
     * 购买商品回调接口
     *
     * @param goods
     */
    @Override
    public void productHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim) {
        PickGoodsModel.getInstance().setIsRemove(isRemove);
        PickGoods changePickGoods = null;
        if (isRemove) {
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId) {
                    pickGoods.setPickCount(0);
                    changePickGoods = pickGoods;
                    mCartProducts.remove(pickGoods);
                    break;
                }
            }
            checkCart();

            if(this.goods.getGoodsSpecList().size() > 1)
                for (int i = 0; i < this.goods.getGoodsSpecList().size(); i++) {
                    GoodsSpec goodsSpec = this.goods.getGoodsSpecList().get(i);
                    if (goodsSpec.getId() == goodsSpecId) {
                        goodsSpec.setBuyCount(0);
                        break;
                    }
                }

        } else {
            //如果改变的商品已经存在，那么就改变商品的数目，否则添加进购物车
            boolean mCartProductsContain = false;
            for (PickGoods pickGoods : mCartProducts) {
                if (pickGoods.getGoodsId() == goodsId && pickGoods.getGoodsSpecId() == goodsSpecId) {
                    pickGoods.setGoods(goods);
                    pickGoods.setPickCount(pickCount);
                    mCartProductsContain = true;
                    changePickGoods = pickGoods;
                    break;
                }
            }
            if (!mCartProductsContain) {
                PickGoods pickGoods = new PickGoods();
                pickGoods.setPickCount(pickCount);
                pickGoods.setGoods(goods);
                pickGoods.setGoodsId(goodsId);
                pickGoods.setGoodsSpecId(goodsSpecId);
                pickGoods.setMenuId(categoryId);
                changePickGoods = pickGoods;
                mCartProducts.add(pickGoods);
            }
        }
        //刷新PopWindow
        bottomAdapter.setData(mCartProducts);
        if(mCartProducts.size() >= 4){
            bottomListView.setPadding(0,0,0,DipToPx.dip2px(mActivity,42));
        }else{
            bottomListView.setPadding(0,0,0,0);
        }

        if (!isSetAnim) {
            setCart();
        }
        savePickGoodsInfo();
        if (currentType == 1) {
            if (goodsSpec.getGoodsId() == goodsId && goodsSpec.getId() == goodsSpecId) {
                tvBuyCount.setText(pickCount + "");
                goodsSpec.setBuyCount(pickCount);
                if (pickCount == 0) {
                    AnimatorUtils.rightTranslationRotating(imgMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MINUS_TRANSLATION_X, 0, this));
                    AnimatorUtils.rightTranslationRotating(tvBuyCount, PreferenceUtils.getFloatPreference(PreferenceUtils.COUNT_TRANSLATION_X, 0, this));
                }
            }
        } else if (currentType == 2) {
            int num = 0;
            for (int i = 0; i < goods.getGoodsSpecList().size(); i++) {
                GoodsSpec goodsSpec1 = goods.getGoodsSpecList().get(i);
                List<PickGoods> pickGoodsList = mCartProducts;
                for (PickGoods pickGoods : pickGoodsList) {
                    if (pickGoods.getGoodsId() == goods.getId() && pickGoods.getGoodsSpecId() == goodsSpec1.getId()) {
                        num += pickGoods.getPickCount();
                        break;
                    }
                }
            }
            if (num == 0) {
                AnimatorUtils.rightTranslationRotating(specMinus, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_MINUS, 0, this));
                AnimatorUtils.rightTranslationRotating(specCount, PreferenceUtils.getFloatPreference(PreferenceUtils.MY_COUNT, 0, this));
            }
            specCount.setText(num + "");
        }
    }

    private void savePickGoodsInfo() {
        if (CheckUtils.isNoEmptyList(mCartProducts)) {
            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
            PickGoodsModel.getInstance().setHasChange(true);
            if (merchantPickGoodsList != null) {
                boolean contain = false;
                for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                    if (merchantPickGoods.getMerchantId() == merchant.getId()) {
                        merchantPickGoods.setPickGoods(mCartProducts);
                        int count = 0;
                        for (PickGoods pro : mCartProducts) {
                            count += pro.getPickCount();
                        }
                        merchantPickGoods.setGoodsCount(count);
                        contain = true;
                        break;
                    }
                }
                if (!contain) {
                    MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                    merchantPickGoods.setPickGoods(mCartProducts);
                    merchantPickGoods.setMerchantId(merchant.getId());
                    int count = 0;
                    for (PickGoods pro : mCartProducts) {
                        count += pro.getPickCount();
                    }
                    merchantPickGoods.setGoodsCount(count);
                    merchantPickGoodsList.add(merchantPickGoods);
                }
            } else {
                merchantPickGoodsList = new ArrayList<>();
                MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                merchantPickGoods.setPickGoods(mCartProducts);
                merchantPickGoods.setMerchantId(merchant.getId());
                int count = 0;
                for (PickGoods pro : mCartProducts) {
                    count += pro.getPickCount();
                }
                merchantPickGoods.setGoodsCount(count);
                merchantPickGoodsList.add(merchantPickGoods);
            }
        } else {
            PickGoodsModel.getInstance().getMerchantPickGoodsList().clear();
            PickGoodsModel.getInstance().setHasChange(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK, new Intent());
    }

    /**
     * 订单预览
     */
    private void getOrderPreview() {
        mLoadingDialog.show(getFragmentManager(), "");
        List<MerchantPickGoods> merchantPickGoodses = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchant.getId());
        if(App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodses) {
            long mId = merchantPickGoods.getMerchantId();
            if (mId == merchant.getId()) {
                pickGoods = merchantPickGoods.getPickGoods();
            }
        }
        for (PickGoods goods : pickGoods) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("id", goods.getGoodsId());
            m.put("quantity", goods.getPickCount());
            m.put("specId", goods.getGoodsSpecId());
            orderItems.add(m);
        }
        map.put("orderItems", orderItems);
        previewJsonData = new JSONObject(map);
        latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(GoodsDetailActivity.this);
        operater.doRequest(Constants.URL_GET_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        confirmOrderModel = (ConfirmOrderModel) obj;
                        if (confirmOrderModel.isSuccess()) {
                            Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                            intent.putExtra("confirmOrderModel", confirmOrderModel);
                            intent.putExtra("onceMoreOrder", previewJsonData);
                            startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                            finish();
                        } else {
                            ToastUtils.displayMsg("商家无法下单，请联系商家", mActivity);
                        }
                    }
                }
                mLoadingDialog.dismiss();
            }
        }, ConfirmOrderModel.class);
    }
}
