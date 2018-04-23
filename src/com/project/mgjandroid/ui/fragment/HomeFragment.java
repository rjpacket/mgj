package com.project.mgjandroid.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Banner;
import com.project.mgjandroid.bean.CategoryLeftBean;
import com.project.mgjandroid.bean.CategoryRightBean;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.model.CommercialListModel;
import com.project.mgjandroid.model.FindCategoryModel;
import com.project.mgjandroid.model.HomeBannerModel;
import com.project.mgjandroid.model.MerchantFilterModel;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.MerchantPropertyListEntity;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.PromotionListEntity;
import com.project.mgjandroid.model.MerchantFilterModel.ValueEntity.ShipmentListEntity;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.model.PrimaryCategoryModel;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.activity.Banner2WebActivity;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.FindDefaultAddressActivity;
import com.project.mgjandroid.ui.activity.LocationActivity;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
import com.project.mgjandroid.ui.activity.SearchActivity;
import com.project.mgjandroid.ui.adapter.HomeRestaurantAdapter;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopChildAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopGroupAdapter;
import com.project.mgjandroid.ui.view.AlwaysMarqueeTextView;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.SegmentedGroup;
import com.project.mgjandroid.ui.view.autoscrollviewpager.AutoScrollViewPager;
import com.project.mgjandroid.ui.view.autoscrollviewpager.indicator.CirclePageIndicator;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.OnBannerItemClickListener;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首頁
 *
 * @author jian
 */
public class HomeFragment extends BaseFragment implements OnClickListener, OnBannerItemClickListener, RadioGroup.OnCheckedChangeListener {
    public static final int IS_LINK = 1;
    public static final int IS_CATEGORY = 2;
    public static final int IS_MERCHANT = 3;
    protected BaseActivity mActivity;
    protected View view;
    protected boolean refreshFlag = true;
    private PullToRefreshListView listView;
    private LinearLayout listHeaderButton;
    private CirclePageIndicator navigatorIndicator;
    private LinearLayout menuBar;
    private TextView tvMenu1;
    private TextView tvMenu2;
    private TextView tvMenu3;
    private TextView tvLVMenu1;
    private TextView tvLVMenu2;
    private TextView tvLVMenu3;
    List<String> imageIdList;
    List<Banner> mBannerList;
    private HomeRestaurantAdapter adapter;
    private AlwaysMarqueeTextView tvAdress;
    private static final int maxResults = 20;
    private int currentResultPage = 0;
    private List<View> viewList = new ArrayList<>();
    private MyBanner myBanner;
    private PopupWindow leftMenuWindow;
    private PopupWindow midMenuWindow;
    private PopupWindow rightMenuWindow;
    private Drawable rightDrawableOrange;
    private Drawable rightDrawableGray;
    private LinearLayout hasNoNet;
    private LinearLayout locateFail;
    private String[] names = new String[]{"智能排序", "距离最近", "销量最高", "起送价最低", "配送速度最快", "评分最高"};
    private int[] heads = new int[]{R.drawable.head_01, R.drawable.head_02, R.drawable.head_03,
            R.drawable.head_04, R.drawable.head_05, R.drawable.head_06};
    private int[] sortIds = new int[]{1,2,3,4,5,6};
    private MerchantFilterModel.ValueEntity filterValue;
    private static final int LOCATION_SUCCESS = 233;
    private static final int LOCATION_FAIL = 234;
    private static final int NO_NET = 500;
    private static final int LOCATION_NO_MERCHANT = 501;
    private int tagId = -1;
    private int tagParentId = -1;
    private SegmentedGroup shipmentLinear;
    private FlowLayout propertyLinear;
    private LinearLayout promotionLinear;
    private HomeSortAdapter homeSortAdapter;
    private int midPrePosition = -1;

    private int leftMenuCurrentGroup=-1,leftMenuCurrentChild=-1;
    private ListView groupListView;
    private ListView childListView;
    private TextView reload,hasNoNetMsg;
    private ImageView hasNoNetIcon;
    private CustomDialog dialog;

    private ImageView ivSearch;
    private View titleBarBg;
    private View titleBarBg1;
    private boolean ivSearchVisible;
    private RelativeLayout navigatorLayout;
    private View navigatorDivider;
    private LoadingDialog mLoadingDialog;
    private AutoScrollViewPager navigatorViewPager;
    private LeftMenuPopGroupAdapter mCategoryLeftAdapter;
    private int mPrePosition;
    private int mSelectPosition;
    private int mSelectChildPosition = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.home_fragment, container, false);
        LocationManager.getIManager().registeLocation(mActivity, listener);
        mLoadingDialog = new LoadingDialog(mActivity);
        initData();
        initViews();
        checkNet();
        return view;
    }

    private void checkNet() {
        hasNoNet = (LinearLayout) view.findViewById(R.id.home_fragment_no_net);
        reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
        hasNoNetIcon= (ImageView) hasNoNet.findViewById(R.id.home_fragment_img_nonet);
        hasNoNetMsg= (TextView) hasNoNet.findViewById(R.id.home_fragment_msg_nonet);
        hasNoNet.setOnClickListener(this);
        if (!NetworkUtils.isConnected(mActivity)) {
            hasNoNet.setVisibility(View.VISIBLE);
            hasNoNetIcon.setImageResource(R.drawable.has_no_net);
            hasNoNetMsg.setText("未能连接到互联网");
            reload.setText("刷新重试");
            reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isConnected(mActivity)) {
                        return;
                    }
                    hasNoNet.setVisibility(View.GONE);
                    handler.sendEmptyMessage(0);
                }
            });
        }else{
            mLoadingDialog.show();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (listView.isRefreshing()) {
                listView.onRefreshComplete();
            }
            switch (msg.what) {
                case 0:
                    titleBarBg.setAlpha(0);
                    tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                    ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                    mLoadingDialog.show();
                    LocationManager.getIManager().registeLocation(mActivity, listener);
                    break;
                case LOCATION_SUCCESS:
                    locateFail.setVisibility(View.GONE);
                    break;
                case LOCATION_FAIL:
                    titleBarBg.setAlpha(1);
                    tvAdress.setBackgroundResource(0);
                    ivSearch.setBackgroundResource(0);
                    mLoadingDialog.dismiss();
                    locateFail.setVisibility(View.VISIBLE);
//                    ToastUtils.displayMsg("定位失败，请检查网络或定位是否打开", mActivity);
                    hasNoNet.setVisibility(View.GONE);
                    TextView locateByOneself = (TextView) locateFail.findViewById(R.id.home_fragment_locate_by_oneself);
                    TextView locateReload = (TextView) locateFail.findViewById(R.id.home_fragment_locate_reload);
                    locateByOneself.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mActivity, LocationActivity.class);
                            startActivityForResult(intent, ActRequestCode.LOCATION);
                            mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                        }
                    });
                    locateReload.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LocationManager.getIManager().registeLocation(mActivity, listener);
//                            showAddress();
                        }
                    });
                    break;
                case NO_NET:
                    titleBarBg.setAlpha(1);
                    tvAdress.setBackgroundResource(0);
                    ivSearch.setBackgroundResource(0);
                    hasNoNet.setVisibility(View.VISIBLE);
                    hasNoNetIcon.setImageResource(R.drawable.has_no_net);
                    hasNoNetMsg.setText("未能连接到互联网");
                    reload.setText("刷新重试");
                    reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
                    reload.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!NetworkUtils.isConnected(mActivity)) {
                                return;
                            }
                            hasNoNet.setVisibility(View.GONE);
                            handler.sendEmptyMessage(0);
                        }
                    });
                    break;
                case LOCATION_NO_MERCHANT:
//                    hasNoNet.setVisibility(View.VISIBLE);
//                    hasNoNetIcon.setImageResource(R.drawable.has_no_merchant);
//                    hasNoNetMsg.setText("附近没有外卖商家");
//                    reload.setText("切换地址");
//                    Log.e("merchant", hasNoNetMsg.getText() + "");
//                    reload.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(mActivity, LocationActivity.class);
//                            startActivityForResult(intent, ActRequestCode.LOCATION);
//                            mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
//                        }
//                    });
                    showNavigateDialog();
                    break;
            }
        }
    };

    private void showNavigateDialog() {
        if(dialog == null){
            dialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener(){
                @Override
                public void onSure() {
                    Intent intent = new Intent(mActivity, FindDefaultAddressActivity.class);
                    startActivityForResult(intent, ActRequestCode.LOCATION);
                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                    dialog.dismiss();
                }

                @Override
                public void onExit() {
                    dialog.dismiss();
//                    hasNoNet.setVisibility(View.GONE);
                }
            }, "切换", "取消", "温馨提示", "抱歉，您所在的区域正加急开通商家，请切换到已开通商家的区域。");
        }
        dialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        for (Merchant merchant : adapter.getList()) {
            merchant.setPickGoodsCount(0);
        }
        if (CheckUtils.isNoEmptyList(PickGoodsModel.getInstance().getMerchantPickGoodsList())) {
            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                for (Merchant merchant : adapter.getList()) {
                    if (merchant.getId() == merchantPickGoods.getMerchantId()) {
                        merchant.setPickGoodsCount(merchantPickGoods.getGoodsCount());
                        break;
                    }
                }
            }
        }
        adapter.setList(adapter.getList());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        imageIdList = new ArrayList<>();
        mBannerList = new ArrayList<>();
    }

    private void initViews() {
        RelativeLayout titleLayout = (RelativeLayout) view.findViewById(R.id.home_fragment_title_bar);
        titleLayout.setOnClickListener(this);
        ivSearch = (ImageView) view.findViewById(R.id.home_fragment_iv_search);
        ivSearch.setOnClickListener(this);
        tvAdress = (AlwaysMarqueeTextView) view.findViewById(R.id.home_fragment_tv_address);
        titleBarBg = view.findViewById(R.id.home_fragment_title_bar_bg);
        titleBarBg1 = view.findViewById(R.id.home_fragment_title_bar_bg_1);
        menuBar = (LinearLayout) view.findViewById(R.id.home_fragment_menu_bar);
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.button_layout_1);
        LinearLayout linearLayout2 = (LinearLayout) view.findViewById(R.id.button_layout_2);
        LinearLayout linearLayout3 = (LinearLayout) view.findViewById(R.id.button_layout_3);
        locateFail = (LinearLayout) view.findViewById(R.id.home_fragment_locate_fail);
        locateFail.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        tvMenu1 = (TextView) view.findViewById(R.id.tv_1);
        tvMenu2 = (TextView) view.findViewById(R.id.tv_2);
        tvMenu3 = (TextView) view.findViewById(R.id.tv_3);
        initListView();
        initListHeaderView();
        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        if (rightDrawableOrange != null) {
            rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        }
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        if (rightDrawableGray != null) {
            rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());
        }
    }

    /**
     * 广告栏
     */
    private void initListHeaderView() {
        LinearLayout listHeaderView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_list_header_layout, null);
        listHeaderButton = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_list_header_button, null);
        listHeaderButton.setVisibility(View.INVISIBLE);
        myBanner = (MyBanner) listHeaderView.findViewById(R.id.slideshowView);
        myBanner.setOnBannerItemClickListener(this);
        navigatorViewPager = (AutoScrollViewPager) listHeaderView.findViewById(R.id.home_list_header_navigator_view_pager);
        navigatorLayout = (RelativeLayout) listHeaderView.findViewById(R.id.home_list_header_navigator_flipperParent);
        navigatorDivider = listHeaderView.findViewById(R.id.home_list_header_navigator_divider);
        navigatorViewPager.setNeedOnMeasure(true);
//        LinearLayout bar = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.search_bar, null);
//        LinearLayout searchBar = (LinearLayout) bar.findViewById(R.id.search_bar);
//        searchBar.setOnClickListener(this);
        navigatorIndicator = (CirclePageIndicator) listHeaderView.findViewById(R.id.home_list_header_navigator_indicator);
        LinearLayout linearLayout1 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_1);
        LinearLayout linearLayout2 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_2);
        LinearLayout linearLayout3 = (LinearLayout) listHeaderButton.findViewById(R.id.button_layout_listview_3);
        tvLVMenu1 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_1);
        tvLVMenu2 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_2);
        tvLVMenu3 = (TextView) listHeaderButton.findViewById(R.id.tv_listview_3);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);

//        listView.getRefreshableView().addHeaderView(bar);
        listView.getRefreshableView().addHeaderView(listHeaderView);
        listView.getRefreshableView().addHeaderView(listHeaderButton);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 2) {
                    menuBar.setVisibility(View.VISIBLE);
                }
//                else {
//                    if (!((leftMenuWindow != null && leftMenuWindow.isShowing())
//                            || (midMenuWindow != null && midMenuWindow.isShowing())
//                            || (rightMenuWindow != null && rightMenuWindow.isShowing()))) {
//                        menuBar.setVisibility(View.GONE);
//                    }
//                }
                if (firstVisibleItem == 1) {
//                    MLog.e("头部滚动距离第一项：" + getScrollY() + "高度" + listView.getRefreshableView().getChildAt(0).getTop());
                    if(checkBarNeedVisible()) {
                        menuBar.setVisibility(View.VISIBLE);
                    } else {
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                }
//                if (firstVisibleItem >= 2) {
//                    if (!ivSearchVisible) {
//                        ivSearch.setVisibility(View.VISIBLE);
//                        AnimatorUtils.scaleIn(ivSearch);
//                    }
//                    ivSearchVisible = true;
//                } else {
//                    if (ivSearchVisible) {
//                        AnimatorUtils.scaleOut(ivSearch);
//                    }
//                    ivSearchVisible = false;
//                }
                if(firstVisibleItem == 1) {
                    if (getScrollY() < -30) {
                        tvAdress.setBackgroundResource(0);
                        ivSearch.setBackgroundResource(0);
                    } else {
                        tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                        ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                    }
                    titleBarBg.setAlpha(0.3f + Math.abs(getScrollY()) / DipToPx.dip2px(mActivity, 150)*0.7f);
                }
                if (firstVisibleItem == 0) {
                    titleBarBg.setAlpha(0);
                    menuBar.setVisibility(View.INVISIBLE);
                    tvAdress.setBackgroundResource(R.drawable.home_title_bg);
                    ivSearch.setBackgroundResource(R.drawable.home_title_bg);
                } else if (firstVisibleItem > 1){
                    titleBarBg.setAlpha(1);
                }
            }
        });
        navigatorViewPager.setAdapter(mPageAdapter);
        navigatorIndicator.setViewPager(navigatorViewPager);
        navigatorIndicator.setRadius(getResources().getDimension(R.dimen.x3));
        navigatorIndicator.setOrientation(LinearLayout.HORIZONTAL);
        navigatorIndicator.setStrokeWidth(getResources().getDimension(R.dimen.x1));
        navigatorIndicator.setStrokeColor(0xffc9c9c9);
        navigatorIndicator.setSnap(false);
        navigatorIndicator.setFillColor(0xffff9900);
        navigatorViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
        navigatorViewPager.stopAutoScroll();
        navigatorViewPager.setCurrentItem(0);
        navigatorViewPager.setCycle(false);
        navigatorViewPager.setBorderAnimation(true);
    }

    public float getScrollY() {
        View c = listView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return 0;
        }
        return (float) c.getTop();
    }

    public float getHeaderScrollY(){
        View v1 = listView.getRefreshableView().getChildAt(1);
        return v1.getHeight() - DipToPx.dip2px(mActivity,48);
    }

    public boolean checkBarNeedVisible() {
        View c = listView.getRefreshableView().getChildAt(0);
        if (c == null) {
            return false;
        }
//        MLog.e("头部第一项剩余高度：" + (c.getHeight() + c.getTop()));
        return (c.getHeight() + c.getTop()) < getResources().getDimensionPixelSize(R.dimen.title_bar_height);
    }

    @Override
    public void onItemClick(int position) {
        //轮播图点击的位置
        Banner bannerItem = mBannerList.get(position);
        int bannerType = bannerItem.getBannerType();
        switch (bannerType){
            case IS_LINK:
                Intent intent = new Intent(mActivity, Banner2WebActivity.class);
                intent.putExtra(Banner2WebActivity.URL, bannerItem.getUrl() + "?longitude=" + PreferenceUtils.getLocation(mActivity)[1] + "&latitude=" + PreferenceUtils.getLocation(mActivity)[0]);
                intent.putExtra("name", mBannerList.get(position).getName());
                startActivity(intent);
                break;
            case IS_CATEGORY:
                Intent intent1 = new Intent(mActivity, PrimaryCategoryActivity.class);
                intent1.putExtra("tagCategoryId", bannerItem.getFirstCategoryId());
                intent1.putExtra("tagCategorySecondId", bannerItem.getSecondCategoryId());
                intent1.putExtra("tagCategoryType", bannerItem.getCategoryType());
                intent1.putExtra("categoryName", bannerItem.getName());
                startActivity(intent1);
                break;
            case IS_MERCHANT:
                Routers.open(mActivity, ActivitySchemeManager.SCHEME + "merchant/" + bannerItem.getMerchantId());
                break;
        }
    }

    private MyPageAdapter mPageAdapter = new MyPageAdapter();

    private class MyPageAdapter extends PagerAdapter {

        private List<View> views = new ArrayList<>();
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void setViews(List<View> views) {
            this.views = views;
            notifyDataSetChanged();
        }

        private int mChildCount = 0;

        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object)   {
            if ( mChildCount > 0) {
                mChildCount --;
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

    }

    private void initNavigatorViewPager(List<PrimaryCategory> primaryCategoryList) {
        navigatorDivider.setVisibility(View.VISIBLE);
        navigatorLayout.setVisibility(View.VISIBLE);
        for(View view:viewList){
            ((LinearLayout) view).removeAllViews();
        }
        viewList.clear();
        LinearLayout linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(llp);

        int rowLine = 1;
        if(primaryCategoryList.size() > 5 ){
            rowLine = 2;
        }
        for (int i = 0; i < rowLine; i++) {
            LinearLayout row = new LinearLayout(mActivity);
            row.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(llp3);
            if (i == 0) {
                for (int j = 0; j < (5 > primaryCategoryList.size() ? primaryCategoryList.size() : 5); j++) {
                    LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                    row.addView(mLayout);
                }
            } else {
                for (int j = 5; j < (10 > primaryCategoryList.size() ? primaryCategoryList.size() : 10); j++) {
                    LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                    row.addView(mLayout);
                }
            }
            linearLayout.addView(row);
        }
        viewList.add(linearLayout);

        if (primaryCategoryList.size() > 10) {
            LinearLayout linearLayout2 = new LinearLayout(mActivity);
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout2.setLayoutParams(llp2);

            for (int i = 0; i < 2; i++) {
                if (primaryCategoryList.size() == 15 && i == 1) break;
                LinearLayout row = new LinearLayout(mActivity);
                row.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams llp3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(llp3);
                if (i == 0) {
                    for (int j = 10; j < (15 > primaryCategoryList.size() ? primaryCategoryList.size() : 15); j++) {
                        LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                        row.addView(mLayout);
                    }
                } else {
                    for (int j = 15; j < (20 > primaryCategoryList.size() ? primaryCategoryList.size() : 20); j++) {
                        LinearLayout mLayout = createNavigator(primaryCategoryList.get(j));
                        row.addView(mLayout);
                    }
                }
                linearLayout2.addView(row);
            }
            viewList.add(linearLayout2);
        }

        mPageAdapter.setViews(viewList);
//        navigatorViewPager.setAdapter(mPageAdapter);

        if (primaryCategoryList.size() <= 10) {
            navigatorIndicator.setVisibility(View.GONE);
        } else {
            navigatorIndicator.setVisibility(View.VISIBLE);
        }
    }

    private LinearLayout createNavigator(final PrimaryCategory primaryCategory) {
        LinearLayout mLayout = new LinearLayout(mActivity);
        mLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(DeviceParameter.getDisplayMetrics().widthPixels / 5,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, DipToPx.dip2px(mActivity, 20f));
        mLayout.setLayoutParams(llp);
        mLayout.setGravity(Gravity.CENTER);
        mLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (primaryCategory.getGraySwitch() == 0) {
                    if(primaryCategory.getGotoType() == 2){
                        Intent intent = new Intent(mActivity, PrimaryCategoryActivity.class);
                        intent.putExtra("tagCategoryId", primaryCategory.getTagCategoryId());
                        intent.putExtra("tagCategoryType", primaryCategory.getTagCategoryType());
                        intent.putExtra("categoryName", primaryCategory.getName());
                        startActivity(intent);
                    }else if(primaryCategory.getGotoType() == 1){
                        if(primaryCategory.getGotoUrl().startsWith("maguanjia://")){
                            Routers.open(mActivity, ActivitySchemeManager.SCHEME + primaryCategory.getGotoUrl().replace("maguanjia://", ""));
                        }else if(primaryCategory.getGotoUrl().startsWith("http")){
                            Intent intent = new Intent(mActivity, Banner2WebActivity.class);
                            intent.putExtra(Banner2WebActivity.URL, primaryCategory.getGotoUrl());
                            startActivity(intent);
                        }
                    }
                } else {
                    ToastUtils.displayMsg("尚未开通，敬请期待", mActivity);
                }
            }
        });

        ImageView iv = new ImageView(mActivity);
        LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(DipToPx.dip2px(mActivity, 44f),
                DipToPx.dip2px(mActivity, 44f));
        iv.setLayoutParams(ivlp);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(R.drawable.category_default);
        if (primaryCategory.getGraySwitch() == 0) {
            ImageUtils.loadBitmap(mActivity , primaryCategory.getPicUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, iv, R.drawable.category_default , "");
        } else {
            ImageUtils.loadBitmap(mActivity , primaryCategory.getGrayUrl() + Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL, iv,  R.drawable.category_default , "");
        }

        TextView tv = new TextView(mActivity);
        tv.setText(primaryCategory.getName());
        tv.setTextColor(getResources().getColor(R.color.color_3));
        tv.setTextSize(12);
        tv.setGravity(Gravity.CENTER);
        tv.setIncludeFontPadding(false);
        LinearLayout.LayoutParams tvlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tvlp.setMargins(0, getResources().getDimensionPixelSize(R.dimen.x10), 0, 0);
        tv.setLayoutParams(tvlp);
        mLayout.addView(iv);
        mLayout.addView(tv);
        return mLayout;
    }

    /**
     * 列表
     */
    private void initListView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.home_fragment_listView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
//        listView.setAddMoreCountText(maxResults);
        adapter = new HomeRestaurantAdapter(mActivity);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                checkRefresh(refreshView);
                if (refreshFlag) {
                    currentResultPage = 0;
                    getDate(false, false);
                    if (imageIdList == null || imageIdList.size() == 0) {
                        getBanner();
                    } else {
                        myBanner.setUrls(imageIdList, true, true);
                    }
                    if (viewList == null || viewList.size() == 0) {
                        getPrimaryCategory();
                    }
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {
//                MLog.e("头部下拉刷新：" + value);
                if (value < 0) {
                    tvAdress.setVisibility(View.INVISIBLE);
                    ivSearch.setVisibility(View.INVISIBLE);
                } else if ((refreshView.getState() == null || refreshView.getState() != PullToRefreshBase.State.REFRESHING) && value >= 0) {
                    tvAdress.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                checkRefresh(refreshView);
                if (refreshFlag) {
                    currentResultPage += maxResults;
                    getDate(false, true);
                }
            }
        });
    }

    private void checkRefresh(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        if (!NetworkUtils.isConnected(mActivity)) {
            hasNoNet.setVisibility(View.VISIBLE);
            hasNoNetIcon.setImageResource(R.drawable.has_no_net);
            hasNoNetMsg.setText("未能连接到互联网");
            reload.setText("刷新重试");
//                    listView.onRefreshComplete();
            TextView reload = (TextView) hasNoNet.findViewById(R.id.home_fragment_reload);
            reload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetworkUtils.isConnected(mActivity)) {
                        return;
                    }
                    hasNoNet.setVisibility(View.GONE);
                    showAddress();
                }
            });
        }
    }

    /**
     * 显示定位信息，并刷新列表
     */
    public void showAddress() {
        String address = PreferenceUtils.getAddressName(mActivity);
        initPopupMenu();
        if (CheckUtils.isNoEmptyStr(address)) {
            tvAdress.setText(address);
            currentResultPage = 0;
            getCategory();
            getFilter();
            getBanner();
            getPrimaryCategory();
            listView.resetCurrentMode();
            listView.autoRefresh();
            handler.sendEmptyMessage(LOCATION_SUCCESS);
        } else {
            tvAdress.setText("未知位置");
            //定位失败
            doWhileLocationFail();
        }

    }

    private void initPopupMenu() {
        midPrePosition=-1;
        leftMenuCurrentChild=-1;
        leftMenuCurrentGroup=-1;
        if(filterValue!=null&&!isClearFilter())
            clearFilter();
        leftMenuWindow=null;
        midMenuWindow=null;
        rightMenuWindow=null;
        tvLVMenu1.setText("分类");
        tvMenu1.setText("分类");
        tvLVMenu2.setText("排序");
        tvMenu2.setText("排序");
        tagId = -1;
        tagParentId = -1;
    }

    private void doWhileLocationFail() {
        if (!NetworkUtils.isConnected(mActivity)) {
            handler.sendEmptyMessage(NO_NET);
        } else {
            handler.sendEmptyMessage(LOCATION_FAIL);
        }
    }

    //注册一个定位监听
    BDLocationListener listener = new BDLocationListener() {
        @SuppressWarnings("unchecked")
        @Override
        public void onReceiveLocation(BDLocation location) {
            LocationManager.getIManager().stopLocation();
            if (location != null) {
                PreferenceUtils.saveLocation("28.244892495444", "117.04362045428407", mActivity);
                PreferenceUtils.saveAddressName("江西省鹰潭市月湖区", mActivity);
                if (CheckUtils.isNoEmptyList(location.getPoiList())) {
                    List<Poi> list = location.getPoiList();
                    PreferenceUtils.saveAddressDes(list.get(0).getName(), mActivity);
                }
                if(location.getAddress() != null && location.getAddress().city != null){
                    PreferenceUtils.saveAddressCity(location.getAddress().city, mActivity);
                }
                if(location.getAddress() != null && location.getAddress().cityCode != null){
                    PreferenceUtils.saveAddressCityCode(location.getAddress().cityCode, mActivity);
                }
                showAddress();
            }else{
                handler.obtainMessage(LOCATION_FAIL).sendToTarget();
            }
        }
    };


    private void addActivityImages(List<Banner> bannerList) {
        imageIdList.clear();
        mBannerList.clear();
        for (Banner banner : bannerList) {
            imageIdList.add(banner.getPicUrl());
            mBannerList.add(banner);
        }
        myBanner.setUrls(imageIdList, true, true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_fragment_title_bar:
                Intent intent = new Intent(mActivity, LocationActivity.class);
                startActivityForResult(intent, ActRequestCode.LOCATION);
                mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                break;

            case R.id.button_layout_listview_1:

                if(menuBar.getVisibility() == View.INVISIBLE){
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_1:
                checkListCount();

                tvMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (leftMenuWindow == null) {
                    showLeftMenuPop();
                } else if (leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                    resetListView();
                    if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    List<CategoryLeftBean> list = mCategoryLeftAdapter.getList();
                    list.get(mPrePosition).setIsChecked(false);
                    list.get(mSelectPosition).setIsChecked(true);
                    List<CategoryRightBean> list1 = list.get(mSelectPosition).getChildTagCategoryList();
                    if(list1 != null) {
                        mCategoryRightAdapter.setList(list1);
                    }else{
                        mCategoryRightAdapter.setList(new ArrayList<CategoryRightBean>());
                    }
                    leftMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
//                    resetListView();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.button_layout_listview_2:

                if(menuBar.getVisibility() == View.INVISIBLE){
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_2:
                checkListCount();

                tvMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (midMenuWindow == null) {
                    showMidMenuPop();
                } else if (midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                    resetListView();
                    if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                        menuBar.setVisibility(View.INVISIBLE);
                    }
                } else {
                    midMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
//                    resetListView();
                }
                if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
                    rightMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.button_layout_listview_3:
                if(menuBar.getVisibility() == View.INVISIBLE){
                    menuBar.setVisibility(View.VISIBLE);
                }
                tvLVMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvLVMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
            case R.id.button_layout_3:
                checkListCount();
                if (filterValue != null) {
                    setFilterState();
                }
                tvMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                        resetListView();
                        if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                            menuBar.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        rightMenuWindow.showAsDropDown(menuBar, 0, 0);
                    }
                }
//                else {
//                    getFilter();
//                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
//                    resetListView();
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
//                    resetListView();
                }
                break;
            case R.id.home_fragment_menu_left_cover_view:
                leftMenuWindow.dismiss();
                resetListView();
                if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_mid_cover_view:
                midMenuWindow.dismiss();
                resetListView();
                if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_right_cover_view:
                rightMenuWindow.dismiss();
                resetListView();
                if(menuBar.getVisibility() == View.VISIBLE && listView.getRefreshableView().getFirstVisiblePosition() < 3){
                    menuBar.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.home_fragment_menu_right_clear:
                rightMenuWindow.dismiss();
                resetListView();
                if(menuBar.getVisibility() == View.VISIBLE){
                    menuBar.setVisibility(View.INVISIBLE);
                }
                clearFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;
            case R.id.home_fragment_menu_right_confirm:
                rightMenuWindow.dismiss();
                resetListView();
                if(menuBar.getVisibility() == View.VISIBLE){
                    menuBar.setVisibility(View.INVISIBLE);
                }
                confirmFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;
            case R.id.home_fragment_locate_fail:
                break;
            case R.id.home_fragment_no_net:
                break;
            case R.id.clear_filter_condition:
                initPopupMenu();
                getDate(true, false);
                break;
            case R.id.search_bar:
            case R.id.home_fragment_iv_search:
                startActivity(new Intent(mActivity, SearchActivity.class));
                break;
            case R.id.group_category:

                break;
            default:
                break;
        }
    }

    private void checkListCount() {
        if(adapter.getCount() > 4) {
            listView.getRefreshableView().setSelection(2);
        }else {
            listView.scrollTo(0, (int) getHeaderScrollY());
            titleBarBg1.setVisibility(View.VISIBLE);
        }
    }

    private void resetListView(){
        titleBarBg1.setVisibility(View.GONE);
        listView.scrollTo(0, 0);
    }

    private void confirmFilter() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            if (ship.isCheck()) {
                ship.setIsConfirm(true);
            } else {
                ship.setIsConfirm(false);
            }
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if (merchantProperty.isCheck()) {
                merchantProperty.setIsConfirm(true);
            } else {
                merchantProperty.setIsConfirm(false);
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            if (promotion.isCheck()) {
                promotion.setIsConfirm(true);
            } else {
                promotion.setIsConfirm(false);
            }
        }
    }

    private void clearFilter() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            ship.setIsCheck(false);
            ship.setIsConfirm(false);
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            merchantProperty.setIsCheck(false);
            merchantProperty.setIsConfirm(false);
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            promotion.setIsCheck(false);
            promotion.setIsConfirm(false);
        }
    }

    private boolean isClearFilter() {
        if(filterValue==null){
            return true;
        }
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (ShipmentListEntity ship : shipmentList) {
            if(ship.isConfirm()){
                return false;
            }
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if(merchantProperty.isConfirm()){
                return false;
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (PromotionListEntity promotion : promotionList) {
            if(promotion.isConfirm()){
                return false;
            }
        }
        return true;
    }

    /**
     * 设置按钮3的界面状态
     */
    private void setFilterState() {
        List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        int temp = 0;
        for (int i = 0; i < shipmentList.size(); i++) {
            ShipmentListEntity shipmentListEntity = shipmentList.get(i);
            if (shipmentListEntity.isConfirm()) {
                ((RadioButton) shipmentLinear.getChildAt(i)).setChecked(true);
            } else {
                temp++;
                ((RadioButton) shipmentLinear.getChildAt(i)).setChecked(false);
            }
        }
        if (temp == shipmentList.size()) {
            shipmentLinear.clearCheck();
        }

        List<MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (int i = 0; i < merchantPropertyList.size(); i++) {
            MerchantPropertyListEntity merchantPropertyListEntity = merchantPropertyList.get(i);
            if (merchantPropertyListEntity.isConfirm()) {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }

        List<PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (int i = 0; i < promotionList.size(); i++) {
            PromotionListEntity promotionListEntity = promotionList.get(i);
            if (promotionListEntity.isConfirm()) {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }
    }

    private void getBanner() {
        VolleyOperater<HomeBannerModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if(mActivity!=null&&PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_TBANNER, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (CheckUtils.isNoEmptyList(((HomeBannerModel) obj).getValue())) {
                        addActivityImages(((HomeBannerModel) obj).getValue());
                    }
                }
            }
        }, HomeBannerModel.class);
    }

    private void getPrimaryCategory() {
        VolleyOperater<PrimaryCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if(mActivity!=null&&PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_PRIMARY_CATEGORY_LIST, map, new ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (CheckUtils.isNoEmptyList(((PrimaryCategoryModel) obj).getValue())) {
                        initNavigatorViewPager(((PrimaryCategoryModel) obj).getValue());
                    } else {
                        navigatorDivider.setVisibility(View.GONE);
                        navigatorLayout.setVisibility(View.GONE);
                    }
                }
            }
        }, PrimaryCategoryModel.class);
    }

    /**
     * 获取列表数据
     *
     * @param isAutoRefresh 加载新地址后自动刷新
     * @param isLoadMore    加载更多
     */
    private void getDate(final boolean isAutoRefresh, final boolean isLoadMore) {
        refreshFlag = false;
        final Map<String, Object> map = new HashMap<>();
        if (App.isLogin()) {
            map.put("userId", App.getUserInfo().getId());
        }
        map.put("queryType", getSortParam());
        map.put("start", currentResultPage);
        map.put("size", maxResults);
        if (tagId != -1) {
            map.put("tagId", tagId);
        }
        if (tagParentId != -1) {
            map.put("tagParentId", tagParentId);
        }
        if(mActivity!=null&&PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        String shipParams = getShipParams();
        if (shipParams.length() > 0) {
            map.put("shipFilter", shipParams);
        }
        String propertyParams = getPropertyParams();
        if (propertyParams.length() > 0) {
            map.put("propertyFilter", propertyParams);
        }
        String promotionParams = getPromotionParams();
        if (promotionParams.length() > 0) {
            map.put("promotionFilter", promotionParams);
        }
        VolleyOperater<CommercialListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_fIND_TAKE_AWAY_MERCHANT, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(mLoadingDialog != null && mLoadingDialog.isShowing()){
                    mLoadingDialog.dismiss();
                }
                //TODO　开关
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    ArrayList<Merchant> mlist = ((CommercialListModel) obj).getValue();
                    if(!isLoadMore) {
                        if (mlist.size() == 0 && midPrePosition == -1 && leftMenuCurrentGroup == -1 && leftMenuCurrentChild == -1 && isClearFilter()) {
                            adapter.setList(new ArrayList<Merchant>());
                            handler.sendEmptyMessage(LOCATION_NO_MERCHANT);
                            return;
                        }
//                        else {
//                            hasNoNet.setVisibility(View.GONE);
//                        }
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (CheckUtils.isNoEmptyList(PickGoodsModel.getInstance().getMerchantPickGoodsList()))
                            for (MerchantPickGoods merchantPickGoods : PickGoodsModel.getInstance().getMerchantPickGoodsList()) {
                                for (Merchant merchant : mlist) {
                                    if (merchant.getId() == merchantPickGoods.getMerchantId()) {
                                        merchant.setPickGoodsCount(merchantPickGoods.getGoodsCount());
                                        break;
                                    }
                                }
                            }
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
//                                listView.setMode(PullToRefreshBase.Mode.);
                            }
                            ArrayList<Merchant> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if(listHeaderButton.getVisibility() == View.INVISIBLE){
                                listHeaderButton.setVisibility(View.VISIBLE);
                            }
                            ArrayList<Merchant> mlistOrg = adapter.getList();
                            mlistOrg.clear();
                            mlistOrg.addAll(mlist);
                            adapter.setList(mlistOrg);
//                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        }else{
                            adapter.setList(mlist);
                        }
                    }
                }
            }
        }, CommercialListModel.class);

    }

    private int getSortParam() {
        if(homeSortAdapter != null) {
            List<HomeBean> data = homeSortAdapter.getData();
            for (HomeBean bean : data) {
                if (bean.isCheck()) {
                    return bean.getId();
                }
            }
        }
        return 1;
    }

    private String getShipParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<ShipmentListEntity> shipmentList = filterValue.getShipmentList();
            for (int i = 0; i < shipmentList.size(); i++) {
                ShipmentListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    sb.append(ship.getId());
                }
            }
            return sb.toString();
        }
        return "";
    }

    private String getPropertyParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<MerchantPropertyListEntity> shipmentList = filterValue.getMerchantPropertyList();
            for (int i = 0; i < shipmentList.size(); i++) {
                MerchantPropertyListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    if (i == shipmentList.size() - 1) {
                        sb.append(ship.getId());
                    } else {
                        sb.append(ship.getId() + ",");
                    }
                }
            }
            return sb.toString();
        }
        return "";
    }

    private String getPromotionParams() {
        if (filterValue != null) {
            StringBuilder sb = new StringBuilder();
            List<PromotionListEntity> shipmentList = filterValue.getPromotionList();
            for (int i = 0; i < shipmentList.size(); i++) {
                PromotionListEntity ship = shipmentList.get(i);
                if (ship.isConfirm()) {
                    if (i == shipmentList.size() - 1) {
                        sb.append(ship.getId());
                    } else {
                        sb.append(ship.getId() + ",");
                    }
                }
            }
            return sb.toString();
        }
        return "";
    }


    private void showLeftMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_left, null);
        groupListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_group);
        childListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_child);
        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_left_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
//        leftMenuWindow.setFocusable(true);
        leftMenuWindow.showAsDropDown(menuBar, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu1.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu1.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
        mCategoryLeftAdapter = new LeftMenuPopGroupAdapter(mActivity, leftPopMenuGroup, groupListener);
        groupListView.setAdapter(mCategoryLeftAdapter);
        mCategoryRightAdapter = new LeftMenuPopChildAdapter(mActivity, leftPopMenuChild, childListener);
        childListView.setAdapter(mCategoryRightAdapter);
    }

    private LeftMenuPopChildAdapter mCategoryRightAdapter;
    private OnClickListener groupListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (int) v.getTag();
            List<CategoryLeftBean> list = mCategoryLeftAdapter.getList();
            CategoryLeftBean categoryLeftBean1 = list.get(mPrePosition);
            categoryLeftBean1.setIsChecked(false);
            CategoryLeftBean categoryLeftBean2 = list.get(mSelectPosition);
            categoryLeftBean2.setIsChecked(false);
            CategoryLeftBean categoryLeftBean = list.get(i);
            categoryLeftBean.setIsChecked(true);
            mCategoryLeftAdapter.setList(list);
            mPrePosition = i;

            leftPopMenuChild = leftPopMenuGroup.get(i).getChildTagCategoryList();
            if(leftPopMenuChild != null) {
                mCategoryRightAdapter.setList(leftPopMenuChild);
            }else{
                leftMenuCurrentGroup=i;
                tvLVMenu1.setText(list.get(i).getName());
                tvMenu1.setText(list.get(i).getName());
                tagId = list.get(i).getId();
                tagParentId = list.get(i).getParentId();
                currentResultPage = 0;
                getDate(true, false);
                leftMenuWindow.dismiss();
                leftPopMenuChild = new ArrayList<>();
                mCategoryRightAdapter.setList(leftPopMenuChild);

                if(mSelectChildPosition != -1) {
                    List<CategoryLeftBean> list1 = mCategoryLeftAdapter.getList();
                    List<CategoryRightBean> childTagCategoryList = list1.get(mSelectPosition).getChildTagCategoryList();
                    if(childTagCategoryList != null) {
                        CategoryRightBean categoryRightBean = childTagCategoryList.get(mSelectChildPosition);
                        categoryRightBean.setIsChecked(false);
                    }
                }
                mSelectPosition = i;
                mSelectChildPosition = -1;
            }
        }
    };

    private OnClickListener childListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (int) v.getTag();
            if(mSelectPosition != 0) {
                List<CategoryLeftBean> list1 = mCategoryLeftAdapter.getList();
                List<CategoryRightBean> childTagCategoryList = list1.get(mSelectPosition).getChildTagCategoryList();
                CategoryRightBean categoryRightBean = childTagCategoryList.get(mSelectChildPosition);
                categoryRightBean.setIsChecked(false);
            }

            List<CategoryRightBean> list = mCategoryRightAdapter.getList();
            list.get(i).setIsChecked(true);

            mCategoryRightAdapter.setList(list);

            tvLVMenu1.setText(leftPopMenuChild.get(i).getName());
            tvMenu1.setText(leftPopMenuChild.get(i).getName());
            tagId = leftPopMenuChild.get(i).getId();
            tagParentId = leftPopMenuChild.get(i).getParentId();
            currentResultPage = 0;
            getDate(true, false);
            leftMenuCurrentChild=i;
            leftMenuWindow.dismiss();

            mSelectPosition = mPrePosition;
            mSelectChildPosition = i;
        }
    };

    private void showMidMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_mid, null);
        ListView listView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_mid_list);
        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_mid_cover_view);
        coverView.setOnClickListener(this);
        homeSortAdapter = new HomeSortAdapter(R.layout.layout_home_category, mActivity,listenerMid);
        ArrayList<HomeBean> data = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            HomeBean bean = new HomeBean();
            bean.setIcon(heads[i]);
            bean.setName(names[i]);
            bean.setId(sortIds[i]);
            data.add(bean);
        }
        homeSortAdapter.setData(data);
        listView.setAdapter(homeSortAdapter);
        listView.setItemsCanFocus(true);

        midMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        midMenuWindow.setOutsideTouchable(true);
//        midMenuWindow.setFocusable(true);
        midMenuWindow.showAsDropDown(menuBar, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu2.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu2.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
    }

    private OnClickListener listenerMid = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (midPrePosition != -1) {
                homeSortAdapter.getData().get(midPrePosition).setIsCheck(false);
            }
            HomeBean bean = homeSortAdapter.getData().get(position);
            bean.setIsCheck(!bean.isCheck());
            midPrePosition = position;
            homeSortAdapter.notifyDataSetChanged();
            if (midMenuWindow.isShowing()) {
                midMenuWindow.dismiss();
            }
            currentResultPage = 0;
            getDate(true, false);
            tvMenu2.setText(bean.getName());
            tvLVMenu2.setText(bean.getName());
        }
    };

    private void showRightMenuPop(List<ShipmentListEntity> shipmentList, final List<MerchantPropertyListEntity> merchantPropertyList, final List<PromotionListEntity> promotionList) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_right, null);
        RelativeLayout rlShipment = (RelativeLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_shipment_layout);
        shipmentLinear = (SegmentedGroup) linearLayout.findViewById(R.id.home_fragment_menu_right_segment_group);
        LinearLayout llMerchant = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_merchant_layout);
        propertyLinear = (FlowLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_merchant_flow);
        LinearLayout llPromotion = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_promotion_layout);
        promotionLinear = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_list);
        TextView clear = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_clear);
        clear.setOnClickListener(this);
        TextView confirm = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_confirm);
        confirm.setOnClickListener(this);

        for (ShipmentListEntity shipment : shipmentList) {
            RadioButton radioButton1 = (RadioButton) mActivity.getLayoutInflater().inflate(R.layout.radio_button, null);
            radioButton1.setText(shipment.getName());
            shipmentLinear.addView(radioButton1);
        }
        shipmentLinear.updateBackground();
        ((RadioButton) shipmentLinear.getChildAt(0)).setChecked(false);
        shipmentLinear.setOnCheckedChangeListener(this);

        float width = (DeviceParameter.getScreenWidth() - DipToPx.dip2px(mActivity, 48)) / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, DipToPx.dip2px(mActivity, 25));
        params.setMargins(0, 0, DipToPx.dip2px(mActivity, 8), DipToPx.dip2px(mActivity, 5));
        for (int i = 0; i < merchantPropertyList.size(); i++) {
            final LinearLayout linearLayout1 = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.right_menu_merchant_feature, null);
            linearLayout1.setTag(i);
            linearLayout1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout1.getChildAt(2).setSelected(!linearLayout1.getChildAt(2).isSelected());
                    merchantPropertyList.get((int) v.getTag()).setIsCheck(linearLayout1.getChildAt(2).isSelected());
                }
            });
            ImageView ivMerchant = (ImageView) linearLayout1.findViewById(R.id.right_menu_item_iv);
            TextView tvMerchant = (TextView) linearLayout1.findViewById(R.id.right_menu_item_tv);
            ImageUtils.loadBitmap(mActivity , merchantPropertyList.get(i).getIcon(), ivMerchant, 0 , "");
            tvMerchant.setText(merchantPropertyList.get(i).getName());
            linearLayout1.setLayoutParams(params);
            propertyLinear.addView(linearLayout1);
        }

        for (int i = 0; i < promotionList.size(); i++) {
            final LinearLayout linearLayout2 = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.item_home_filter_promotion, null);
            linearLayout2.setTag(i);
            ImageView ivPromotion = (ImageView) linearLayout2.findViewById(R.id.promotion_icon);
            TextView tvPromotion = (TextView) linearLayout2.findViewById(R.id.promotion_text);
            linearLayout2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout2.getChildAt(2).setSelected(!linearLayout2.getChildAt(2).isSelected());
                    promotionList.get((int) v.getTag()).setIsCheck(linearLayout2.getChildAt(2).isSelected());
                }
            });
            ImageUtils.loadBitmap(mActivity , promotionList.get(i).getIcon(), ivPromotion, 0 , "");
            tvPromotion.setText(promotionList.get(i).getName());
//			linearLayout2.setLayoutParams(params2);
            promotionLinear.addView(linearLayout2);
        }

        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_right_cover_view);
        coverView.setOnClickListener(this);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        rightMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        rightMenuWindow.setOutsideTouchable(true);
//        rightMenuWindow.showAsDropDown(menuBar, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu3.setTextColor(getResources().getColor(R.color.color_3));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);
                tvLVMenu3.setTextColor(getResources().getColor(R.color.color_3));
                tvLVMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton childAt = (RadioButton) group.getChildAt(i);
            filterValue.getShipmentList().get(i).setIsCheck(childAt.isChecked());
        }
    }

    private List<CategoryLeftBean> leftPopMenuGroup = new ArrayList<>();
    private List<CategoryRightBean> leftPopMenuChild = new ArrayList<>();

    /**
     * 获取商家分类
     */
    private void getCategory() {
        VolleyOperater<FindCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if(mActivity!=null&&PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_GET_CATEGORY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed) {
                    leftPopMenuGroup = ((FindCategoryModel) obj).getValue();
                    leftPopMenuGroup.get(0).setIsChecked(true);
                    mSelectPosition = 0;
                    mSelectChildPosition = -1;
                }
            }
        }, FindCategoryModel.class);
    }

    /**
     * 获取筛选
     */
    private void getFilter() {
        VolleyOperater<MerchantFilterModel> operater = new VolleyOperater<MerchantFilterModel>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if(mActivity!=null&&PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FILTER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    MerchantFilterModel merchantFilterModel = (MerchantFilterModel) obj;
                    filterValue = merchantFilterModel.getValue();
                    if (filterValue != null) {
//                        menuBar.setVisibility(View.VISIBLE);
                        showRightMenuPop(filterValue.getShipmentList(), filterValue.getMerchantPropertyList(), filterValue.getPromotionList());
                    }
                }
            }
        }, MerchantFilterModel.class);
    }

    public boolean isPopupWindowShowing() {
        if ((leftMenuWindow != null && leftMenuWindow.isShowing()) || (midMenuWindow != null && midMenuWindow.isShowing()) ||
                (rightMenuWindow != null && rightMenuWindow.isShowing())) {
            return true;
        }
        return false;
    }

    public void hidePopupWindow() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            leftMenuWindow.dismiss();
        } else if (midMenuWindow != null && midMenuWindow.isShowing()) {
            midMenuWindow.dismiss();
        } else if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            rightMenuWindow.dismiss();
        }
    }
}
