package com.project.mgjandroid.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.CategoryLeftBean;
import com.project.mgjandroid.bean.CategoryRightBean;
import com.project.mgjandroid.bean.HomeBean;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.CommercialListModel;
import com.project.mgjandroid.model.FindCategoryModel;
import com.project.mgjandroid.model.MerchantFilterModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.HomeRestaurantAdapter;
import com.project.mgjandroid.ui.adapter.HomeSortAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopChildAdapter;
import com.project.mgjandroid.ui.adapter.LeftMenuPopGroupAdapter;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.SegmentedGroup;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/3/25.
 */
public class PrimaryCategoryActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    @InjectView(R.id.primary_category_act_back)
    private ImageView ivBack;
    @InjectView(R.id.primary_category_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.primary_category_act_menu_bar)
    private LinearLayout menuBar;
    @InjectView(R.id.primary_category_act_menu_layout_1)
    private LinearLayout layoutMenu1;
    @InjectView(R.id.primary_category_act_menu_layout_2)
    private LinearLayout layoutMenu2;
    @InjectView(R.id.primary_category_act_menu_layout_3)
    private LinearLayout layoutMenu3;
    @InjectView(R.id.primary_category_act_menu_tv_1)
    private TextView tvMenu1;
    @InjectView(R.id.primary_category_act_menu_tv_2)
    private TextView tvMenu2;
    @InjectView(R.id.primary_category_act_menu_tv_3)
    private TextView tvMenu3;
    @InjectView(R.id.primary_category_act_list_view)
    private PullToRefreshListView listView;

    protected boolean refreshFlag = true;
    private static final int maxResults = 20;
    private int currentResultPage = 0;
    private HomeRestaurantAdapter adapter;
    private PopupWindow leftMenuWindow;
    private PopupWindow midMenuWindow;
    private PopupWindow rightMenuWindow;
    private Drawable rightDrawableOrange;
    private Drawable rightDrawableGray;

    private long tagCategoryId;
    private long tagCategorySecondId;
    private int tagCategoryType;
    private MerchantFilterModel.ValueEntity filterValue;
    private int tagId = -1;
    private int tagParentId = -1;
    private String[] names = new String[]{"智能排序", "距离最近", "销量最高", "起送价最低", "配送速度最快", "评分最高"};
    private int[] heads = new int[]{R.drawable.head_01, R.drawable.head_02, R.drawable.head_03,
            R.drawable.head_04, R.drawable.head_05, R.drawable.head_06};
    private int[] sortIds = new int[]{1,2,3,4,5,6};
    private HomeSortAdapter homeSortAdapter;
    private SegmentedGroup shipmentLinear;
    private FlowLayout propertyLinear;
    private LinearLayout promotionLinear;
    private int midPrePosition = -1;

    private ListView groupListView;
    private ListView childListView;
    private String title;
    private LeftMenuPopGroupAdapter mCategoryLeftAdapter;
    private LeftMenuPopChildAdapter mCategoryRightAdapter;
    private int mPrePosition;
    private int mSelectPosition;
    private int mSelectChildPosition = -1;
    //    private View listEmptyView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_primary_category);
        Injector.get(this).inject();
        initView();
//        getDate(true, false);
        getCategory();
    }

    private void initView(){
        tagCategoryId = getIntent().getLongExtra("tagCategoryId", 0);
        tagCategorySecondId = getIntent().getLongExtra("tagCategorySecondId",-1);
        tagCategoryType = getIntent().getIntExtra("tagCategoryType", 1);
        title = getIntent().getExtras().getString("categoryName");
        tvTitle.setText(title);

        ivBack.setOnClickListener(this);
        layoutMenu1.setOnClickListener(this);
        layoutMenu2.setOnClickListener(this);
        layoutMenu3.setOnClickListener(this);
        initListView();
        rightDrawableOrange = getResources().getDrawable(R.drawable.nabla_red);
        rightDrawableOrange.setBounds(0, 0, rightDrawableOrange.getMinimumWidth(), rightDrawableOrange.getMinimumHeight());
        rightDrawableGray = getResources().getDrawable(R.drawable.nabla_black);
        rightDrawableGray.setBounds(0, 0, rightDrawableGray.getMinimumWidth(), rightDrawableGray.getMinimumHeight());
    }

    private void initListView() {
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new HomeRestaurantAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    currentResultPage = 0;
                    getDate(false, false);
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentResultPage += maxResults;
                getDate(false, true);
            }
        });
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
        map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
        map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
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
        operater.doRequest(Constants.URL_fIND_TAKE_AWAY_MERCHANT, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (!isAutoRefresh) {
                    listView.onRefreshComplete();
                }
                refreshFlag = true;
                if (isSucceed && obj != null) {

                    ArrayList<Merchant> mlist = ((CommercialListModel) obj).getValue();
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
                            }
                            ArrayList<Merchant> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            ArrayList<Merchant> mlistOrg = adapter.getList();
                            mlistOrg.clear();
                            mlistOrg.addAll(mlist);
                            adapter.setList(mlistOrg);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setList(mlist);
                        }
                    }
                } else {

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
            StringBuffer sb = new StringBuffer();
            List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList = filterValue.getShipmentList();
            for (int i = 0; i < shipmentList.size(); i++) {
                MerchantFilterModel.ValueEntity.ShipmentListEntity ship = shipmentList.get(i);
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
            StringBuffer sb = new StringBuffer();
            List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> shipmentList = filterValue.getMerchantPropertyList();
            for (int i = 0; i < shipmentList.size(); i++) {
                MerchantFilterModel.ValueEntity.MerchantPropertyListEntity ship = shipmentList.get(i);
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
            StringBuffer sb = new StringBuffer();
            List<MerchantFilterModel.ValueEntity.PromotionListEntity> shipmentList = filterValue.getPromotionList();
            for (int i = 0; i < shipmentList.size(); i++) {
                MerchantFilterModel.ValueEntity.PromotionListEntity ship = shipmentList.get(i);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.primary_category_act_back:
                back();
                break;
            case R.id.primary_category_act_menu_layout_1:
                tvMenu1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if(leftMenuWindow==null){
                    showLeftMenuPop();
                }else if(leftMenuWindow.isShowing()){
                    leftMenuWindow.dismiss();
                }else {
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
                if(midMenuWindow!=null&&midMenuWindow.isShowing()){
                    midMenuWindow.dismiss();
                }
                if(rightMenuWindow!=null&&rightMenuWindow.isShowing()){
                    rightMenuWindow.dismiss();
                }
                break;
            case R.id.primary_category_act_menu_layout_2:
                tvMenu2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if(midMenuWindow==null){
                    showMidMenuPop();
                }else if(midMenuWindow.isShowing()){
                    midMenuWindow.dismiss();
                }else {
                    midMenuWindow.showAsDropDown(menuBar, 0, 0);
                }
                if(leftMenuWindow!=null&&leftMenuWindow.isShowing()){
                    leftMenuWindow.dismiss();
                }
                if(rightMenuWindow!=null&&rightMenuWindow.isShowing()){
                    rightMenuWindow.dismiss();
                }
                break;
            case R.id.primary_category_act_menu_layout_3:
                if (filterValue != null) {
                    setFilterState();
                }
                tvMenu3.setTextColor(getResources().getColor(R.color.title_bar_bg));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableOrange, null);
                if (rightMenuWindow != null) {
                    if (rightMenuWindow.isShowing()) {
                        rightMenuWindow.dismiss();
                    } else {
                        rightMenuWindow.showAsDropDown(menuBar, 0, 0);
                    }
                } else {
                    getFilter();
                }
                if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
                    leftMenuWindow.dismiss();
                }
                if (midMenuWindow != null && midMenuWindow.isShowing()) {
                    midMenuWindow.dismiss();
                }
                break;
            case R.id.home_fragment_menu_left_cover_view:
                leftMenuWindow.dismiss();
                break;
            case R.id.home_fragment_menu_mid_cover_view:
                midMenuWindow.dismiss();
                break;
            case R.id.home_fragment_menu_right_cover_view:
                rightMenuWindow.dismiss();
                break;
            case R.id.home_fragment_menu_right_clear:
                rightMenuWindow.dismiss();
                clearFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;
            case R.id.home_fragment_menu_right_confirm:
                rightMenuWindow.dismiss();
                confirmFilter();
                currentResultPage = 0;
                getDate(true, false);
                break;

            case R.id.clear_filter_condition:
//                initPopupMenu();
//                getDate(true,false);
                break;
            default:
                break;
        }
    }

    private void initPopupMenu() {
        midPrePosition=-1;
        if(filterValue!=null&&!isClearFilter())
            clearFilter();
        leftMenuWindow=null;
        midMenuWindow=null;
        rightMenuWindow=null;
        tvMenu1.setText("分类");
        tvMenu2.setText("排序");
        tagId = -1;
        tagParentId = -1;
        leftPopMenuChild= new ArrayList<>();
    }

    private boolean isClearFilter() {
        if(filterValue==null){
            return true;
        }
        List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (MerchantFilterModel.ValueEntity.ShipmentListEntity ship : shipmentList) {
            if(ship.isConfirm()){
                return false;
            }
        }

        List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantFilterModel.ValueEntity.MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if(merchantProperty.isConfirm()){
                return false;
            }
        }

        List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (MerchantFilterModel.ValueEntity.PromotionListEntity promotion : promotionList) {
            if(promotion.isConfirm()){
                return false;
            }
        }
        return true;
    }

    private void confirmFilter() {
        List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (MerchantFilterModel.ValueEntity.ShipmentListEntity ship : shipmentList) {
            if (ship.isCheck()) {
                ship.setIsConfirm(true);
            } else {
                ship.setIsConfirm(false);
            }
        }

        List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantFilterModel.ValueEntity.MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            if (merchantProperty.isCheck()) {
                merchantProperty.setIsConfirm(true);
            } else {
                merchantProperty.setIsConfirm(false);
            }
        }

        List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (MerchantFilterModel.ValueEntity.PromotionListEntity promotion : promotionList) {
            if (promotion.isCheck()) {
                promotion.setIsConfirm(true);
            } else {
                promotion.setIsConfirm(false);
            }
        }
    }

    private void clearFilter() {
        List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        for (MerchantFilterModel.ValueEntity.ShipmentListEntity ship : shipmentList) {
            ship.setIsCheck(false);
            ship.setIsConfirm(false);
        }

        List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (MerchantFilterModel.ValueEntity.MerchantPropertyListEntity merchantProperty : merchantPropertyList) {
            merchantProperty.setIsCheck(false);
            merchantProperty.setIsConfirm(false);
        }

        List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (MerchantFilterModel.ValueEntity.PromotionListEntity promotion : promotionList) {
            promotion.setIsCheck(false);
            promotion.setIsConfirm(false);
        }
    }

    /**
     * 设置按钮3的界面状态
     */
    private void setFilterState() {
        List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList = filterValue.getShipmentList();
        int temp = 0;
        for (int i = 0; i < shipmentList.size(); i++) {
            MerchantFilterModel.ValueEntity.ShipmentListEntity shipmentListEntity = shipmentList.get(i);
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

        List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> merchantPropertyList = filterValue.getMerchantPropertyList();
        for (int i = 0; i < merchantPropertyList.size(); i++) {
            MerchantFilterModel.ValueEntity.MerchantPropertyListEntity merchantPropertyListEntity = merchantPropertyList.get(i);
            if (merchantPropertyListEntity.isConfirm()) {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) propertyLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }

        List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList = filterValue.getPromotionList();
        for (int i = 0; i < promotionList.size(); i++) {
            MerchantFilterModel.ValueEntity.PromotionListEntity promotionListEntity = promotionList.get(i);
            if (promotionListEntity.isConfirm()) {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(true);
            } else {
                ((LinearLayout) promotionLinear.getChildAt(i)).getChildAt(2).setSelected(false);
            }
        }
    }

    private void showLeftMenuPop() {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_left, null);
        groupListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_group);
        childListView = (ListView) linearLayout.findViewById(R.id.home_fragment_menu_left_child);
        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_left_cover_view);
        coverView.setOnClickListener(this);

        leftMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        leftMenuWindow.setOutsideTouchable(true);
        leftMenuWindow.showAsDropDown(menuBar, 0, 0);

        leftMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu1.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu1.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
        mCategoryLeftAdapter = new LeftMenuPopGroupAdapter(mActivity, leftPopMenuGroup, groupListener);
        groupListView.setAdapter(mCategoryLeftAdapter);
        mCategoryRightAdapter = new LeftMenuPopChildAdapter(mActivity, leftPopMenuChild, childListener);
        childListView.setAdapter(mCategoryRightAdapter);
        groupListView.setSelection(mSelectPosition);
        childListView.setSelection(mSelectChildPosition);
    }

    private View.OnClickListener groupListener = new View.OnClickListener() {
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
            }else {
                tvMenu1.setText(list.get(i).getName());
                tagId = list.get(i).getId();
                tagParentId = list.get(i).getParentId();
                getDate(true, false);
                leftMenuWindow.dismiss();
                leftPopMenuChild = new ArrayList<>();
                mCategoryRightAdapter.setList(leftPopMenuChild);
                if (mSelectChildPosition != -1) {
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

    private View.OnClickListener childListener = new View.OnClickListener() {
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
            tvMenu1.setText(leftPopMenuChild.get(i).getName());
            tagId = leftPopMenuChild.get(i).getId();
            tagParentId = leftPopMenuChild.get(i).getParentId();
            currentResultPage = 0;
            getDate(true, false);
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

        midMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        midMenuWindow.setOutsideTouchable(true);
        midMenuWindow.showAsDropDown(menuBar, 0, 0);

        midMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu2.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu2.setCompoundDrawables(null, null, rightDrawableGray, null);
            }
        });
    }

    private View.OnClickListener listenerMid = new View.OnClickListener() {
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
        }
    };

    private void showRightMenuPop(List<MerchantFilterModel.ValueEntity.ShipmentListEntity> shipmentList, final List<MerchantFilterModel.ValueEntity.MerchantPropertyListEntity> merchantPropertyList, final List<MerchantFilterModel.ValueEntity.PromotionListEntity> promotionList) {
        LinearLayout linearLayout = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.home_fragment_menu_right, null);
        shipmentLinear = (SegmentedGroup) linearLayout.findViewById(R.id.home_fragment_menu_right_segment_group);
        propertyLinear = (FlowLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_merchant_flow);
        promotionLinear = (LinearLayout) linearLayout.findViewById(R.id.home_fragment_menu_right_list);
        TextView clear = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_clear);
        clear.setOnClickListener(this);
        TextView confirm = (TextView) linearLayout.findViewById(R.id.home_fragment_menu_right_confirm);
        confirm.setOnClickListener(this);

        for (MerchantFilterModel.ValueEntity.ShipmentListEntity shipment : shipmentList) {
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
            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout1.getChildAt(2).setSelected(!linearLayout1.getChildAt(2).isSelected());
                    merchantPropertyList.get((int) v.getTag()).setIsCheck(linearLayout1.getChildAt(2).isSelected());
                }
            });
            ImageView ivMerchant = (ImageView) linearLayout1.findViewById(R.id.right_menu_item_iv);
            TextView tvMerchant = (TextView) linearLayout1.findViewById(R.id.right_menu_item_tv);
            ImageUtils.loadBitmap(this , merchantPropertyList.get(i).getIcon(), ivMerchant, 0 , "");
            tvMerchant.setText(merchantPropertyList.get(i).getName());
            linearLayout1.setLayoutParams(params);
            propertyLinear.addView(linearLayout1);
        }

        for (int i = 0; i < promotionList.size(); i++) {
            final LinearLayout linearLayout2 = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.item_home_filter_promotion, null);
            linearLayout2.setTag(i);
            ImageView ivPromotion = (ImageView) linearLayout2.findViewById(R.id.promotion_icon);
            TextView tvPromotion = (TextView) linearLayout2.findViewById(R.id.promotion_text);
            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout2.getChildAt(2).setSelected(!linearLayout2.getChildAt(2).isSelected());
                    promotionList.get((int) v.getTag()).setIsCheck(linearLayout2.getChildAt(2).isSelected());
                }
            });
            ImageUtils.loadBitmap(this , promotionList.get(i).getIcon(), ivPromotion, 0 , "");
            tvPromotion.setText(promotionList.get(i).getName());
            promotionLinear.addView(linearLayout2);
        }

        View coverView = linearLayout.findViewById(R.id.home_fragment_menu_right_cover_view);
        coverView.setOnClickListener(this);
        clear.setOnClickListener(this);
        confirm.setOnClickListener(this);

        rightMenuWindow = new PopupWindow(linearLayout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        rightMenuWindow.setOutsideTouchable(true);
        rightMenuWindow.showAsDropDown(menuBar, 0, 0);

        rightMenuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                tvMenu3.setTextColor(getResources().getColor(R.color.gray_text_0));
                tvMenu3.setCompoundDrawables(null, null, rightDrawableGray, null);
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
        VolleyOperater<FindCategoryModel> operater = new VolleyOperater<FindCategoryModel>(mActivity);
        Map<String, Object> map = new HashMap<>();
        map.put("tagCategoryType", tagCategoryType);
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
                    for(int i=0;i<leftPopMenuGroup.size();i++){
                        if(tagCategoryId == leftPopMenuGroup.get(i).getId()){
                            mPrePosition = i;
                            mSelectPosition = i;
                            leftPopMenuGroup.get(i).setIsChecked(true);
                            leftPopMenuChild = leftPopMenuGroup.get(i).getChildTagCategoryList();
                            if(leftPopMenuChild != null) {
                                if(tagCategorySecondId == -1){
                                    asClickRightItem(0);
                                }else {
                                    for (int j = 0; j < leftPopMenuChild.size(); j++) {
                                        if (tagCategorySecondId == leftPopMenuChild.get(j).getId()) {
                                            asClickRightItem(j);
                                        }
                                    }
                                }
                            }else {
                                leftPopMenuChild = new ArrayList<>();
                            }
                            currentResultPage = 0;
                        }
                    }
                    getDate(true, false);
                }
            }
        }, FindCategoryModel.class);
    }

    /**
     * 进入类似点击了右边的item
     * @param position
     */
    private void asClickRightItem(int position) {
        leftPopMenuChild.get(position).setIsChecked(true);
        tagId = leftPopMenuChild.get(position).getId();
        tagParentId = leftPopMenuChild.get(position).getParentId();
        tvMenu1.setText(leftPopMenuChild.get(position).getName());
        mSelectChildPosition = position;
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
                        showRightMenuPop(filterValue.getShipmentList(), filterValue.getMerchantPropertyList(), filterValue.getPromotionList());
                    }
                }
            }
        }, MerchantFilterModel.class);
    }

    private boolean isPopupWindowShowing() {
        if ((leftMenuWindow != null && leftMenuWindow.isShowing()) || (midMenuWindow != null && midMenuWindow.isShowing()) ||
                (rightMenuWindow != null && rightMenuWindow.isShowing())) {
            return true;
        }
        return false;
    }

    private void hidePopupWindow() {
        if (leftMenuWindow != null && leftMenuWindow.isShowing()) {
            leftMenuWindow.dismiss();
        } else if (midMenuWindow != null && midMenuWindow.isShowing()) {
            midMenuWindow.dismiss();
        } else if (rightMenuWindow != null && rightMenuWindow.isShowing()) {
            rightMenuWindow.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (isPopupWindowShowing()) {
            hidePopupWindow();
            return;
        }
        back();
    }
}
