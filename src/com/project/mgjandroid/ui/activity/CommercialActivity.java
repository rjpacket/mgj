package com.project.mgjandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.MerchantTakeAwayMenu;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.bean.PromotionActivity;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.GoodsListModel;
import com.project.mgjandroid.model.MerchantEvaluateTopModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.BottomCartListAdapter;
import com.project.mgjandroid.ui.fragment.EvaluateFragment;
import com.project.mgjandroid.ui.fragment.GoodsFragment;
import com.project.mgjandroid.ui.fragment.MerchantsFragment;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.HeaderViewPagerLayout;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RoundImageView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
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
 * 商家
 *
 * @author jian
 */
@Router(value = "merchant/:merchantId" , intExtra = "merchantId")
public class CommercialActivity extends BaseActivity implements OnClickListener, OnPageChangeListener, BottomCartListener{

    private static final int INDEX_GOODS = 0;
    private static final int INDEX_EVALUATE = 1;
    private static final int INDEX_MERCHANTS = 2;
//    public static final String MERCHANT = "merchant";
    public static final String MERCHANT_ID = "merchantId";
    @InjectView(R.id.commercial_all)
    private RelativeLayout allRoot;
    @InjectView(R.id.commercial_act_iv_back)
    private ImageView imgBack;
    @InjectView(R.id.commercial_act_iv_search)
    private ImageView imgSearch;
    @InjectView(R.id.commercial_act_iv_share)
    private ImageView imgShare;
    @InjectView(R.id.commercial_act_iv_favor)
    private ImageView imgFavor;
    @InjectView(R.id.commercial_act_iv_pin)
    private ImageView imgPin;
    @InjectView(R.id.commercial_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.commercial_act_tab_goods)
    private TextView tvGoods;
    @InjectView(R.id.commercial_act_tab_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.commercial_act_tab_merchants)
    private TextView tvMerchants;
    @InjectView(R.id.commercial_act_tab_blue_line)
    private View indicatorView;
    @InjectView(R.id.commercial_act_pager)
    private ViewPager pager;
    @InjectView(R.id.commercial_act_bottom)
    private RelativeLayout bottomLayout;
    @InjectView(R.id.commercial_act_bottom_car)
    private RelativeLayout bottomCart;
    @InjectView(R.id.commercial_act_bottom_money)
    private TextView tv_allMoney;
    @InjectView(R.id.cart_num)
    private TextView tv_num;
//    @InjectView(R.id.commercial_act_cart)
//    private ImageView img_cart;
    @InjectView(R.id.commercial_act_bottom_shipping_and_box)
    private RelativeLayout rlCartShipingAndBox;
    @InjectView(R.id.commercial_act_bottom_shipping)
    private TextView tv_cart_shipping;
    @InjectView(R.id.commercial_act_bottom_package)
    private TextView tv_cart_package;
    @InjectView(R.id.commercial_act_bottom_qisong)
    private TextView tv_cart_qisong;
    @InjectView(R.id.linear_cover)
    private LinearLayout linearCover;
    @InjectView(R.id.commercial_act_go_account)
    private TextView tv_goAccount;
    @InjectView(R.id.shop_message)
    private RelativeLayout shopMessage;
    @InjectView(R.id.commercial_act_title_bar)
    private RelativeLayout topBar;
    @InjectView(R.id.shop_icon)
    private RoundImageView imgShopIcon;
    @InjectView(R.id.shop_name)
    private TextView tvShopName;
    @InjectView(R.id.shop_desc)
    private TextView tvShopDesc;
    @InjectView(R.id.shop_adv_container)
    private LinearLayout linearAdvContainer;
    @InjectView(R.id.commercial_broadcast)
    private LinearLayout linearBroadcast;
    @InjectView(R.id.tv_broadcast)
    private TextView tvBroadcast;

//    private CommercialPagerAdapter commercialAdapter;
    private ArrayList<HeaderViewPagerFragment> fragments;
    private GoodsFragment goodsFragment;
    private EvaluateFragment evaluateFragment;
    private MerchantsFragment merchantsFragment;
    private int currentIndex;

    private Merchant merchant;
    private int merchantId;
	private MerchantTakeAwayMenu merchantTakeAwayMenu;
    private MerchantEvaluateTopModel.ValueEntity.ShareInfoEntity shareInfo;
    private boolean favorite = false;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    private PopupWindow mPopWindow;
    private CustomDialog dialog;
    private List<PickGoods> mCartProducts;
    private BottomCartListAdapter mAdapter;
    private ViewGroup anim_mask_layout;
    private ListView mListView;
    private RelativeLayout relativeCenter;
    private HeaderViewPagerLayout scrollableLayout;
    private PopupWindow mBroadcast;
    private List<PickGoods> pickGoods;
    private JSONObject previewJsonData;
    private String errorMsg;
    private ConfirmOrderModel confirmOrderModel;
    private LoadingDialog mLoadingDialog;
    private MLoadingDialog loadingDialog;
    private boolean isAgainOrder = false;
    private JSONObject object;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.commercial_act);
        Injector.get(this).inject();
//        commercialAdapter = new CommercialPagerAdapter(this.getSupportFragmentManager());
//
//        fragments = commercialAdapter.getFragments();
//        pager.setAdapter(commercialAdapter);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        allRoot.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("merchantId")){
            merchantId = intent.getIntExtra("merchantId",-1);
        }
        if(intent != null && intent.hasExtra("againOrder")){
            isAgainOrder = intent.getBooleanExtra("againOrder", false);
        }
        if(isAgainOrder){
            object = new JSONObject((Map<String, Object>) intent.getSerializableExtra("onceMoreOrder"));
//            Log.i("merchantId1", object.toString());
//            JSONArray a = object.getJSONArray("goodsJson");
//            for(int i = 0;i<a.size();i++){
//                int id = a.getJSONObject(i).getInteger("id");
//                int specId = a.getJSONObject(i).getInteger("specId");
//                int quantity = a.getJSONObject(i).getInteger("quantity");
//                int categoryId = a.getJSONObject(i).getInteger("categoryId");
//            }
        }
        mLoadingDialog = new LoadingDialog(this);
        mLoadingDialog.show();
        loadingDialog = new MLoadingDialog();
        if(merchantId != -1) {
            getMerchantTopEvaluate(merchantId);
        }
    }

    private void initThreeFragments(Merchant merchant) {
        fragments = new ArrayList<>();
        addFragments(merchant);
        MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager());
        pager.setAdapter(mAdapter);
        pager.setOffscreenPageLimit(2);
        setListener();
        initTabLineWidth();
        tvGoods.setTextColor(getResources().getColor(R.color.title_bar_bg));
        tvEvaluate.setTextColor(getResources().getColor(R.color.gray_txt));
        tvMerchants.setTextColor(getResources().getColor(R.color.gray_txt));
        getData();
        tvTitle.setVisibility(View.GONE);
        scrollableLayout = (HeaderViewPagerLayout) findViewById(R.id.scrollableLayout);
        scrollableLayout.setCurrentScrollableContainer(fragments.get(0));
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                scrollableLayout.setCurrentScrollableContainer(fragments.get(position));
            }
        });
        scrollableLayout.setOnScrollListener(new HeaderViewPagerLayout.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                shopMessage.setTranslationY(currentY / 2);
                shopMessage.setAlpha((maxY - currentY) * 1.0f / maxY);
                if (tvTitle.getVisibility() == View.GONE) {
                    tvTitle.setVisibility(View.VISIBLE);
                }
                tvTitle.setAlpha(currentY * 1.0f / maxY);

                if(currentY == maxY){
                    topBar.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                }else{
                    topBar.setBackgroundColor(Color.parseColor("#001c2b51"));
                }
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    private void initShop(Merchant merchant) {
        imgShopIcon.setImageResource(R.drawable.home_default);
        ImageUtils.loadBitmap(this,merchant.getLogo(), imgShopIcon, R.drawable.home_default , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        tvShopName.setText(merchant.getName());
        StringBuilder sb = new StringBuilder();
        BigDecimal minPrice = merchant.getMinPrice();
        BigDecimal shipFee = merchant.getShipFee();
        Integer avgDeliveryTime = merchant.getAvgDeliveryTime();
        if(avgDeliveryTime != null && avgDeliveryTime > 0){
            sb.append("| ").append(avgDeliveryTime).append("分钟送达");
        }
        tvShopDesc.setText(StringUtils.BigDecimal2Str(minPrice) + "元起送 | " + StringUtils.BigDecimal2Str(shipFee) + "元配送费 " + sb.toString());

        String broadcast = merchant.getBroadcast();
        if(broadcast != null && !"".equals(broadcast)){
            tvBroadcast.setText(broadcast);
        }else{
            tvBroadcast.setText("商家暂无公告");
        }
    }

    /**
     * 查询商家详情
     * @param merchantId
     */
    private void getMerchantTopEvaluate(int merchantId) {
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        if(PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", 0);
            map.put("longitude", 0);
        }
        VolleyOperater<MerchantEvaluateTopModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_SHOW_EVALUATE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mLoadingDialog.dismiss();
                getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);

                if (isSucceed && obj != null) {
                    if (obj instanceof MerchantEvaluateTopModel) {
                        allRoot.setVisibility(View.VISIBLE);
                        MerchantEvaluateTopModel merchantEvaluateTopModel = (MerchantEvaluateTopModel) obj;
                        merchant = merchantEvaluateTopModel.getValue().getMerchant();
                        //顶部数据
                        initShop(merchant);
                        //底部数据
                        init(merchant);
                        initThreeFragments(merchant);

                        shareInfo = merchantEvaluateTopModel.getValue().getShareInfo();
                        favorite = merchantEvaluateTopModel.getValue().isFavorite();
                        if (favorite) imgFavor.setImageResource(R.drawable.favored);
                        if (evaluateFragment != null) {
                            evaluateFragment.setData(merchant);
                        }
                        List<PromotionActivity> promotions = merchant.getPromotionActivityList();
                        if(promotions != null && promotions.size() > 0) {
                            for (PromotionActivity promotion : promotions) {
                                addPromotion(linearAdvContainer, promotion);
                            }
                        }
                    }
                }
            }
        }, MerchantEvaluateTopModel.class);
    }

    private void addPromotion(LinearLayout layout, PromotionActivity promotion) {
        LinearLayout childLayout = new LinearLayout(this);
        childLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        if (CheckUtils.isNoEmptyStr(promotion.getPromoImg())) {
            ImageView image = new ImageView(this);
            ImageUtils.loadBitmap(this,promotion.getPromoImg(), image, R.drawable.jian , "");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 14), DipToPx.dip2px(mActivity, 14));
            childLayout.addView(image, params);
        }
        if (CheckUtils.isNoEmptyStr(promotion.getPromoName())) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = DipToPx.dip2px(this, 4);
            tv.setText(promotion.getPromoName());
            tv.setSingleLine();
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setTextColor(this.getResources().getColor(R.color.white));
            tv.setTextSize(12);
            childLayout.addView(tv, params);
        }
        LinearLayout.LayoutParams paramsChild = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsChild.topMargin = DipToPx.dip2px(this, 4);
        layout.addView(childLayout, paramsChild);
    }

    private void init(Merchant merchant) {
        tvTitle.setText(merchant.getName());
        if(merchant.getShipFee().compareTo(BigDecimal.ZERO) == 1){
            tv_cart_shipping.setText("另需配送费¥" + StringUtils.BigDecimal2Str(merchant.getShipFee()));
            tv_cart_package.setTextSize(10);
        }else{
            tv_cart_shipping.setVisibility(View.GONE);
            rlCartShipingAndBox.setVisibility(View.GONE);
            tv_cart_package.setTextSize(14);
        }
        tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
        initPopWindow();
    }

    /**
     * 初始化底部弹出框
     */
    private void initPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_commercial_bottom_cart, null);
        mPopWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setContentView(view);
        mPopWindow.setHeight(DipToPx.dip2px(mActivity,250));
        mPopWindow.setOutsideTouchable(true);
        TextView textView = (TextView) view.findViewById(R.id.tv_clear_goods);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsFragment.clearList(mCartProducts);
                mCartProducts.clear();
                clearPickGoods();
                mAdapter.notifyDataSetChanged();
                setCart();
                mPopWindow.dismiss();
                linearCover.setVisibility(View.INVISIBLE);

            }
        });
        PickGoodsModel.getInstance().getMerchantPickGoodsList();
        initCartProducts();
        if(CheckUtils.isNoEmptyList(mCartProducts)){
            setCart();
        }
//        mPopWindow.setAnimationStyle(R.style.MenuDialogAnimation);
        relativeCenter = (RelativeLayout) view.findViewById(R.id.linear_center);
        mListView = (ListView) view.findViewById(R.id.commercial_cart_list_view);
        mAdapter = new BottomCartListAdapter(this, mCartProducts, this);
        mListView.setAdapter(mAdapter);
    }

    private void clearPickGoods() {
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if(CheckUtils.isNoEmptyList(merchantPickGoodsList)){
            for (MerchantPickGoods merchantPickGoods : merchantPickGoodsList) {
                merchantPickGoods.setGoodsCount(0);
            }
        }
    }

    private void initCartProducts(){
        List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        if(CheckUtils.isNoEmptyList(merchantPickGoodsList)){
            boolean contain = false;
            for(MerchantPickGoods merchantPickGoods:merchantPickGoodsList){
                if(merchantPickGoods.getMerchantId() == merchantId){
                    if(merchantPickGoods.getPickGoods()!=null){
                        mCartProducts = merchantPickGoods.getPickGoods();
                    }else{
                        mCartProducts = new ArrayList<>();
                    }
                    contain = true;
                    break;
                }
            }
            if(!contain) {
                mCartProducts = new ArrayList<>();
            }
        }else{
            mCartProducts = new ArrayList<>();
        }
    }

    private void setListener() {
        imgBack.setOnClickListener(this);
        imgPin.setOnClickListener(this);
        imgFavor.setOnClickListener(this);
        imgShare.setOnClickListener(this);
        imgSearch.setOnClickListener(this);
        tvGoods.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
        tvMerchants.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
        bottomCart.setOnClickListener(this);
        linearCover.setOnClickListener(this);
        tv_goAccount.setOnClickListener(this);
        linearBroadcast.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public List<PickGoods> getCartProducts(){
        return mCartProducts;
    }

    private void addFragments(Merchant merchant) {
        goodsFragment = new GoodsFragment();
        goodsFragment.setListener(this);
        goodsFragment.setMerchant(merchant);
        evaluateFragment = new EvaluateFragment();
        evaluateFragment.setMerchant(merchantId);
        merchantsFragment = new MerchantsFragment();
        fragments.add(goodsFragment);
        fragments.add(evaluateFragment);
        fragments.add(merchantsFragment);
//        commercialAdapter.notify(fragments);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LayoutParams lp = (LayoutParams) indicatorView.getLayoutParams();
        lp.width = screenWidth / 3;
        indicatorView.setLayoutParams(lp);
    }

    private void changeTabUi(int index) {
        switch (index) {
            case INDEX_GOODS:
                tvGoods.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvEvaluate.setTextColor(getResources().getColor(R.color.gray_txt));
                tvMerchants.setTextColor(getResources().getColor(R.color.gray_txt));
                AnimatorUtils.showBottom(bottomLayout, CommercialActivity.this);
                //0704修改 对应用没有影响
//                if (merchantTakeAwayMenu != null) {
//                    goodsFragment.getData(merchantTakeAwayMenu);
//                }
                break;
            case INDEX_EVALUATE:
                tvGoods.setTextColor(getResources().getColor(R.color.gray_txt));
                tvEvaluate.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMerchants.setTextColor(getResources().getColor(R.color.gray_txt));
                if (currentIndex == INDEX_GOODS) {
                    AnimatorUtils.hideBottom(bottomLayout, CommercialActivity.this);
                }
//                if (merchant != null) {
//                    evaluateFragment.setHeader(merchant);
//                }

                break;
            case INDEX_MERCHANTS:
                tvGoods.setTextColor(getResources().getColor(R.color.gray_txt));
                tvEvaluate.setTextColor(getResources().getColor(R.color.gray_txt));
                tvMerchants.setTextColor(getResources().getColor(R.color.title_bar_bg));
                if (currentIndex == INDEX_GOODS) {
                    AnimatorUtils.hideBottom(bottomLayout, CommercialActivity.this);
                }
                if (merchant != null) {
                    merchantsFragment.getData(merchant);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        LayoutParams lp = (LayoutParams) indicatorView.getLayoutParams();

        /**
         * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
         * 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1; 1->0
         */

        if (currentIndex == 0 && position == 0)// 0->1
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 0) // 1->0
        {
            lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

        } else if (currentIndex == 1 && position == 1) // 1->2
        {
            lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
        } else if (currentIndex == 2 && position == 1) // 2->1
        {
            lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
        }
        indicatorView.setLayoutParams(lp);

    }

    @Override
    public void onPageSelected(int arg0) {
        changeTabUi(arg0);
        currentIndex = arg0;
        if(arg0!=0&&mPopWindow.isShowing()){
            mPopWindow.dismiss();
            linearCover.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commercial_act_iv_back:
                back();
                break;
            case R.id.commercial_act_iv_search:
                break;
            case R.id.commercial_act_iv_share:
                if(merchant != null && shareInfo != null){
                    Intent intent = new Intent(mActivity, ShareActivity.class);
                    intent.putExtra("Merchant", merchant);
                    intent.putExtra("shareInfo", shareInfo);
                    startActivity(intent);
                }
                break;
            case R.id.commercial_act_iv_favor:
                favorMerchant();
                break;
            case R.id.commercial_act_iv_pin:

                break;
            case R.id.commercial_act_tab_goods:
                if (pager.getCurrentItem() != INDEX_GOODS) {
                    pager.setCurrentItem(INDEX_GOODS);
                }
                break;
            case R.id.commercial_act_tab_evaluate:
                if (pager.getCurrentItem() != INDEX_EVALUATE) {
                    pager.setCurrentItem(INDEX_EVALUATE);
                }
                break;
            case R.id.commercial_act_tab_merchants:
                if (pager.getCurrentItem() != INDEX_MERCHANTS) {
                    pager.setCurrentItem(INDEX_MERCHANTS);
                }
                break;
            case R.id.commercial_act_bottom_car://底部购物车的点击事件
                if(mCartProducts == null || mCartProducts.size() == 0){
                    return;
                }
                if (mPopWindow != null) {
                    if (!mPopWindow.isShowing()) {
                        //设置popwindow显示位置
                        bottomCart.measure(0, 0);
                        int measuredHeight = bottomCart.getMeasuredHeight();
                        mPopWindow.showAtLocation(bottomCart, Gravity.BOTTOM, 0, measuredHeight);
                        linearCover.setVisibility(View.VISIBLE);
                        AnimatorUtils.showBottom(relativeCenter, this);
                        AnimatorUtils.showBottom(mListView,this);
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
            case R.id.commercial_act_go_account:
                if(mPopWindow.isShowing()){
                    mPopWindow.dismiss();
                    linearCover.setVisibility(View.INVISIBLE);
                }
                if(!App.isLogin()){
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    return;
                }
                getOrderPreview();
                break;
            case R.id.commercial_broadcast:
                if(mBroadcast == null){
                    View view = LayoutInflater.from(this).inflate(R.layout.layout_shop_cover, null);
                    ImageView ivDismiss = (ImageView) view.findViewById(R.id.cover_dismiss);
                    ivDismiss.setOnClickListener(this);
                    TextView tvBroadcast = (TextView) view.findViewById(R.id.broadcast_content);
                    String broadcast = merchant.getBroadcast();
                    if(broadcast != null && !"".equals(broadcast)){
                        tvBroadcast.setText(broadcast);
                    }else{
                        tvBroadcast.setText("商家暂无公告");
                    }
                    mBroadcast = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    mBroadcast.setContentView(view);
                    mBroadcast.setOutsideTouchable(true);
                }else{
                    if(mBroadcast.isShowing()){
                        mBroadcast.dismiss();
                    }else{
                        mBroadcast.showAtLocation(mDecorView,Gravity.NO_GRAVITY,0,0);
                    }
                }
                break;
            case R.id.cover_dismiss:
                mBroadcast.dismiss();
                break;
            case R.id.commercial_act_bottom:
                break;
            default:
                break;
        }

    }

    private void favorMerchant() {
        if(!favorite){
            if (App.isLogin()) {
                sendFavorMerchantRequest();
            } else {
                showMyDialog();
            }
        }else{
            sendCancelFavorMerchantRequest();
        }
    }

    private void showMyDialog() {
        if (dialog == null){
            dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    Intent intent = new Intent(mActivity, SmsLoginActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
                }
            }, getString(R.string.favor_login),getString(R.string.favor_cancel),
                    getString(R.string.favor_title), getString(R.string.favor_msg));
            dialog.show();
        }else {
            dialog.show();
        }
    }

    private void sendFavorMerchantRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CREAT_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj!= null) {
                    ToastUtils.displayMsg("收藏成功", mActivity);
                    favorite = true;
                    imgFavor.setImageResource(R.drawable.favored);
                }
            }
        }, Object.class);
    }

    private void sendCancelFavorMerchantRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj!= null) {
                    ToastUtils.displayMsg("已取消收藏", mActivity);
                    favorite = false;
                    imgFavor.setImageResource(R.drawable.favor);
                }
            }
        }, Object.class);
    }

    @Override
    public void onBackPressed() {
        if(mBroadcast != null && mBroadcast.isShowing()){
            mBroadcast.dismiss();
        }else {
            super.onBackPressed();
        }
    }

    /**
     * 订单预览
     */
    private void getOrderPreview() {
        loadingDialog.show(getFragmentManager(), "");
        List<MerchantPickGoods> merchantPickGoodses = PickGoodsModel.getInstance().getMerchantPickGoodsList();
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        if(App.isLogin()) {
            map.put("loginToken", App.getUserInfo().getToken());
            map.put("userId", App.getUserInfo().getId());
        }
        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
        for (MerchantPickGoods merchantPickGoods : merchantPickGoodses) {
            long mId = merchantPickGoods.getMerchantId();
            if (mId == merchantId) {
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
        double latitude = Double.parseDouble(PreferenceUtils.getLocation(this)[0]);
        double longitude = Double.parseDouble(PreferenceUtils.getLocation(this)[1]);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("data", previewJsonData.toString());
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        VolleyOperater<ConfirmOrderModel> operater = new VolleyOperater<>(CommercialActivity.this);
        operater.doRequest(Constants.URL_GET_ORDER_PREVIEW, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        errorMsg = (String) obj;
                        ToastUtils.displayMsg(errorMsg, mActivity);
                    } else {
                        confirmOrderModel = (ConfirmOrderModel) obj;
                        if(confirmOrderModel.isSuccess()) {
                            Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                            intent.putExtra("confirmOrderModel", confirmOrderModel);
                            intent.putExtra("onceMoreOrder", previewJsonData);
                            startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                        }else{
                            ToastUtils.displayMsg("结算失败",mActivity);
                        }
//                        finish();
                    }
                }
                loadingDialog.dismiss();
            }
        }, ConfirmOrderModel.class);
    }

	private void getData(){
		Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
		VolleyOperater<GoodsListModel> operater = new VolleyOperater<>(CommercialActivity.this);
		operater.doRequest(Constants.URL_SHOW_MERCHANT_TAKE_AWAY_MENU, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    merchantTakeAwayMenu = ((GoodsListModel) obj).getValue();
                    if (merchantTakeAwayMenu != null) {
                        if (currentIndex == INDEX_GOODS) {
                            if(isAgainOrder && object != null && object.getJSONArray("goodsJson").size() > 0){
                                goodsFragment.getDataAgain(merchantTakeAwayMenu,object);
                            }else {
                                goodsFragment.getData(merchantTakeAwayMenu);
                            }
                        }
                    }
                }
            }
        }, GoodsListModel.class);
	}


    /**
     * 购买商品回调接口
     */
    @Override
    public void productHasChange(Goods goods, long categoryId, long goodsId, long goodsSpecId, int pickCount, boolean isRemove, boolean isSetAnim) {
        PickGoods changePickGoods = null;
        if(isRemove){
            for(PickGoods pickGoods:mCartProducts){
                if(pickGoods.getGoodsId()==goodsId && pickGoods.getGoodsSpecId()==goodsSpecId){
                    pickGoods.setPickCount(0);
                    changePickGoods = pickGoods;
                    mCartProducts.remove(pickGoods);
                    break;
                }
            }
            checkCart();
        }else {
            //如果改变的商品已经存在，那么就改变商品的数目，否则添加进购物车
            boolean mCartProductsContain = false;
            for(PickGoods pickGoods:mCartProducts){
                if(pickGoods.getGoodsId()==goodsId){
                    if(pickGoods.getGoodsSpecId()==goodsSpecId){
                        pickGoods.setGoods(goods);
                        pickGoods.setPickCount(pickCount);
                        mCartProductsContain = true;
                        changePickGoods = pickGoods;
                    }
                }
            }
            if(!mCartProductsContain){
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
        mAdapter.setData(mCartProducts);
        if(mCartProducts.size() >= 4){
            mListView.setPadding(0,0,0,DipToPx.dip2px(mActivity,42));
        }else{
            mListView.setPadding(0,0,0,0);
        }
        goodsFragment.notifyList(changePickGoods);
        if(!isSetAnim){
            setCart();
        }
        savePickGoodsInfo();
    }

    private void setCart(){
        if(mCartProducts != null && mCartProducts.size() > 0){
            calculatePrice();
        }else{
            tv_num.setVisibility(View.INVISIBLE);
            tv_allMoney.setText("¥0");
            tv_cart_package.setVisibility(View.GONE);
            tv_cart_shipping.setTextSize(14);
            tv_cart_qisong.setVisibility(View.VISIBLE);
            tv_goAccount.setVisibility(View.GONE);
            tv_cart_qisong.setText("¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice()) + "起送");
            if(tv_cart_shipping.getVisibility() == View.GONE){
                rlCartShipingAndBox.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 检查购物车是否空了
     */
    private void checkCart() {
        if(mCartProducts.isEmpty()){
            if(mPopWindow != null && mPopWindow.isShowing()){
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
        if(mCartProducts != null && mCartProducts.size() > 0){
            for (PickGoods pro : mCartProducts){
                for(GoodsSpec goodsSpec : pro.getGoods().getGoodsSpecList()){
                    if(goodsSpec.getId() == pro.getGoodsSpecId()){
                        num = num.add(goodsSpec.getPrice().multiply(BigDecimal.valueOf((long) pro.getPickCount())));
                        num_package = num_package.add(goodsSpec.getBoxPrice().multiply(BigDecimal.valueOf(pro.getPickCount())));
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
            tv_num.setText(String.valueOf(count));
            tv_allMoney.setText("¥" + StringUtils.BigDecimal2Str(num));
            if(num_package.compareTo(BigDecimal.ZERO) == 1){
                tv_cart_package.setText("包装费¥" + StringUtils.BigDecimal2Str(num_package));
                tv_cart_package.setVisibility(View.VISIBLE);
                tv_cart_shipping.setTextSize(10);
                if(tv_cart_shipping.getVisibility() == View.GONE){
                    rlCartShipingAndBox.setVisibility(View.VISIBLE);
                }
            }else{
                tv_cart_package.setVisibility(View.GONE);
                if(tv_cart_shipping.getVisibility() == View.GONE){
                    rlCartShipingAndBox.setVisibility(View.GONE);
                }
                tv_cart_shipping.setTextSize(14);
            }
            if(num.compareTo(merchant.getMinPrice()) != -1){
                tv_cart_qisong.setVisibility(View.GONE);
                tv_goAccount.setVisibility(View.VISIBLE);
            }else{
                tv_cart_qisong.setVisibility(View.VISIBLE);
                tv_goAccount.setVisibility(View.GONE);
                tv_cart_qisong.setText("还差¥" + StringUtils.BigDecimal2Str(merchant.getMinPrice().subtract(num)));
            }
        }
    }

    /**
     * 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout( final View view,
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
     */
    public void setAnim(final View v, int[] startLocation) {
        if(anim_mask_layout == null)
            anim_mask_layout = createAnimLayout();
        else
            anim_mask_layout.removeAllViews();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout( v,
                startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_num.getLocationInWindow(endLocation);// shopCart是那个购物车

        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1] - 40;// 动画位移的y坐标

        ObjectAnimator translateXAnimator=ObjectAnimator.ofFloat(view, "translationX", 0,endX);
        translateXAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator translateYAnimator=ObjectAnimator.ofFloat(view, "translationY", 0,endY);
        translateYAnimator.setInterpolator(new AnticipateInterpolator());

        AnimatorSet set=new AnimatorSet();
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

    @Override
    protected void onDestroy(){
        if(mPopWindow!=null&&mPopWindow.isShowing()){
            mPopWindow.dismiss();
        }
        super.onDestroy();
    }

    private void savePickGoodsInfo(){
        if(CheckUtils.isNoEmptyList(mCartProducts)){
            List<MerchantPickGoods> merchantPickGoodsList = PickGoodsModel.getInstance().getMerchantPickGoodsList();
            PickGoodsModel.getInstance().setHasChange(true);
            if(merchantPickGoodsList!=null){
                boolean contain = false;
                for(MerchantPickGoods merchantPickGoods:merchantPickGoodsList){
                    if(merchantPickGoods.getMerchantId() == merchantId){
                        merchantPickGoods.setPickGoods(mCartProducts);
                        int count = 0;
                        for (PickGoods pro : mCartProducts){
                            count += pro.getPickCount();
                        }
                        merchantPickGoods.setGoodsCount(count);
                        contain = true;
                        break;
                    }
                }
                if(!contain){
                    MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                    merchantPickGoods.setPickGoods(mCartProducts);
                    merchantPickGoods.setMerchantId(merchantId);
                    int count = 0;
                    for (PickGoods pro : mCartProducts){
                        count += pro.getPickCount();
                    }
                    merchantPickGoods.setGoodsCount(count);
                    merchantPickGoodsList.add(merchantPickGoods);
                }
            }else{
                merchantPickGoodsList = new ArrayList<>();
                MerchantPickGoods merchantPickGoods = new MerchantPickGoods();
                merchantPickGoods.setPickGoods(mCartProducts);
                merchantPickGoods.setMerchantId(merchantId);
                int count = 0;
                for (PickGoods pro : mCartProducts){
                    count += pro.getPickCount();
                }
                merchantPickGoods.setGoodsCount(count);
                merchantPickGoodsList.add(merchantPickGoods);
            }
        }else{
            PickGoodsModel.getInstance().getMerchantPickGoodsList().clear();
            PickGoodsModel.getInstance().setHasChange(true);
            for(MerchantPickGoods merchantPickGoods:PickGoodsModel.getInstance().getMerchantPickGoodsList()){
                if(merchantPickGoods.getMerchantId() == merchantId){
                    merchantPickGoods.setGoodsCount(0);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case ActRequestCode.GOODS_DETAIL:
                refreshPage();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refreshPage();
    }

    private void refreshPage() {
        if(PickGoodsModel.getInstance().isRemove()){
            goodsFragment.clearList();
            PickGoodsModel.getInstance().setHasChange(true);
            for(MerchantPickGoods merchantPickGoods:PickGoodsModel.getInstance().getMerchantPickGoodsList()){
                if(merchantPickGoods.getMerchantId() == merchantId){
                    merchantPickGoods.setGoodsCount(0);
                }
            }
            PickGoodsModel.getInstance().setIsRemove(false);
        }else{
            goodsFragment.clearList(mCartProducts);
        }
        initCartProducts();
        mAdapter.setData(mCartProducts);
        setCart();
    }

}
