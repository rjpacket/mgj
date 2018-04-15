package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.bean.GoodsSpec;
import com.project.mgjandroid.bean.GroupBean;
import com.project.mgjandroid.bean.Menu;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.bean.MerchantRedBag;
import com.project.mgjandroid.bean.MerchantTakeAwayMenu;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantRedBagModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.GoodsGroupAdapter;
import com.project.mgjandroid.ui.adapter.GoodsSectionHeaderAdapter;
import com.project.mgjandroid.ui.listener.BottomCartListener;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.PinnedHeaderListView.PinnedHeaderListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户主页
 *
 * @author jian
 */
public class GoodsFragment extends HeaderViewPagerFragment implements View.OnClickListener {
    protected BaseActivity mActivity;
    protected View view;
    private LinearLayout listLayout;
    private ListView groupListView;
    private PinnedHeaderListView goodsListView;
    private ArrayList<GroupBean> goodsGroup;
    private GoodsGroupAdapter groupAdapter;
    private List<Menu> goodsList;
    private List<Menu> goodsListTemp;
    private GoodsSectionHeaderAdapter goodsAdapter;

    private boolean isScroll = true;
    private BottomCartListener listener;
    private Merchant merchant;
    private int touchWho = 2;
    private int oldSection = -1;
    private MLoadingDialog dialog;
    private LinearLayout linearLayout;
    private JSONObject object;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.goods_fragment, container, false);
        init();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init() {
        linearLayout = new LinearLayout(mActivity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        dialog = new MLoadingDialog();

        goodsGroup = new ArrayList<>();
        goodsList = new ArrayList<>();
        goodsListTemp = new ArrayList<>();
        groupAdapter = new GoodsGroupAdapter(mActivity);
        groupListView = (ListView) view.findViewById(R.id.goods_left_listview);
        groupListView.setAdapter(groupAdapter);
        goodsListView = (PinnedHeaderListView) view.findViewById(R.id.goods_pinned_listView);
        listLayout = (LinearLayout) view.findViewById(R.id.goods_fragment_list_layout);
        goodsAdapter = new GoodsSectionHeaderAdapter(mActivity, merchant);

        goodsAdapter.setListener(listener);

        goodsListView.addHeaderView(addRedBagHeadView());
        goodsListView.setAdapter(goodsAdapter);

        groupListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                int goodsSection = 0;
                int pos = 0;
                isScroll = true;

                for (int i = 0; i < position; i++) {
                    if (goodsListTemp.get(i).getGoodsList().size() > 0) {
                        goodsSection += goodsAdapter.getCountForSection(i - pos) + 1;
                    } else
                        pos++;

                }
                if (goodsListTemp.get(position).getGoodsList().size() > 0) {
                    ArrayList<GroupBean> list = groupAdapter.getList();
                    for (GroupBean gp : list) {
                        gp.setIsClick(false);
                    }
                    list.get(position).setIsClick(true);
                    groupAdapter.setList(list);

                    goodsListView.setSelection(goodsSection + goodsListView.getHeaderViewsCount());
                }
            }
        });


        goodsListView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isScroll) {
                    int newSection = goodsAdapter.getSectionForPosition(firstVisibleItem - goodsListView.getHeaderViewsCount());
                    if (newSection == oldSection) {
                        return;
                    }
                    oldSection = newSection;
                    int j = -1;
                    for (int i = 0; i < groupAdapter.getCount(); i++) {
                        if (i == goodsAdapter.getSectionForPosition(firstVisibleItem - goodsListView.getHeaderViewsCount())) {
                            j = i;
                            if (goodsListTemp.get(j).getGoodsList().size() > 0) {
                                ArrayList<GroupBean> list = groupAdapter.getList();
                                for (GroupBean gp : list) {
                                    gp.setIsClick(false);
                                }
                                list.get(j).setIsClick(true);
                                groupAdapter.setList(list);
                            } else {
                                boolean flag = true;
                                while (flag) {
                                    j++;
                                    if (j >= goodsListTemp.size()) {
                                        return;
                                    }
                                    if (goodsListTemp.get(j).getGoodsList().size() > 0) {
                                        flag = false;
                                        ArrayList<GroupBean> list = groupAdapter.getList();
                                        for (GroupBean gp : list) {
                                            gp.setIsClick(false);
                                        }
                                        list.get(j).setIsClick(true);
                                        groupAdapter.setList(list);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    isScroll = false;
                }
            }
        });
    }

    public void getData(MerchantTakeAwayMenu merchantTakeAwayMenu) {
        goodsGroup.clear();
        goodsList.clear();
        goodsListTemp.clear();
        ArrayList<Long> categoryIds = new ArrayList<>();
        List<Menu> menus = merchantTakeAwayMenu.getMenu();
        if (CheckUtils.isNoEmptyList(menus)) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String name = menu.getName();
                categoryIds.add(menu.getId());
                GroupBean groupBean = new GroupBean();
                groupBean.setName(name);
                if(i == 0){
                    groupBean.setIsClick(true);
                }
                goodsGroup.add(groupBean);
            }
            goodsList.addAll(menus);
            goodsListTemp.addAll(menus);
            goodsAdapter.setMenuList(goodsList);
        }
        groupAdapter.setIdList(categoryIds);
        groupAdapter.setList(goodsGroup);
        groupAdapter.notifyDataSetChanged();
        AnimatorUtils.fadeFadeIn(listLayout, mActivity);

    }

    public void getDataAgain(MerchantTakeAwayMenu merchantTakeAwayMenu,JSONObject object) {
        goodsGroup.clear();
        goodsList.clear();
        goodsListTemp.clear();
        ArrayList<Long> categoryIds = new ArrayList<>();
        List<Menu> menus = merchantTakeAwayMenu.getMenu();
        if (CheckUtils.isNoEmptyList(menus)) {
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                String name = menu.getName();
                categoryIds.add(menu.getId());
                GroupBean groupBean = new GroupBean();
                groupBean.setName(name);
                if(i == 0){
                    groupBean.setIsClick(true);
                }
                goodsGroup.add(groupBean);
            }
            goodsList.addAll(menus);
            goodsListTemp.addAll(menus);
            goodsAdapter.setMenuList(goodsList);
        }
        groupAdapter.setIdList(categoryIds);
        groupAdapter.setList(goodsGroup);
        groupAdapter.notifyDataSetChanged();
        AnimatorUtils.fadeFadeIn(listLayout, mActivity);
        this.object = object;
        for(int i = 0;i<merchantTakeAwayMenu.getMenu().size();i++){
            Menu menu = merchantTakeAwayMenu.getMenu().get(i);
            for(int j = 0;j< menu.getGoodsList().size();j++){
                Goods goods = menu.getGoodsList().get(j);
                if(goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() == 1){
                    GoodsSpec goodsSpec = goods.getGoodsSpecList().get(0);
                    JSONArray a = object.getJSONArray("goodsJson");
                    for(int k = 0;k<a.size();k++) {
                        int id = a.getJSONObject(k).getInteger("id");
                        int specId = a.getJSONObject(k).getInteger("specId");
                        int quantity = a.getJSONObject(k).getInteger("quantity");
                        if (id == goods.getId() && specId == goodsSpec.getId()) {
                            goodsSpec.setBuyCount(quantity);
                            listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                            break;
                        }
                    }
                }else if(goods.getGoodsSpecList() != null && goods.getGoodsSpecList().size() > 1){
                    for(int l = 0 ;l<goods.getGoodsSpecList().size();l++) {
                        GoodsSpec goodsSpec = goods.getGoodsSpecList().get(l);
                        JSONArray a = object.getJSONArray("goodsJson");
                        for (int m = 0; m < a.size(); m++) {
                            int id = a.getJSONObject(m).getInteger("id");
                            int specId = a.getJSONObject(m).getInteger("specId");
                            int quantity = a.getJSONObject(m).getInteger("quantity");
                            if (id == goods.getId() && specId == goodsSpec.getId()) {
                                goodsSpec.setBuyCount(quantity);
                                listener.productHasChange(goods, goods.getCategoryId(), goods.getId(), goodsSpec.getId(), goodsSpec.getBuyCount(), false, false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 接口回调传递监听器
     *
     * @param listener
     */
    public void setListener(BottomCartListener listener) {
        this.listener = listener;
    }

    public void notifyList() {
        goodsAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新List
     */
    public void notifyList(PickGoods changePickGoods) {
        for (Menu menu : goodsList) {
            for (Goods goods : menu.getGoodsList()) {
                if (goods.getId() == changePickGoods.getGoodsId()) {
                    List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                    for (GoodsSpec goodsSpec : goodsSpecList) {
                        if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                            goodsSpec.setBuyCount(changePickGoods.getPickCount());
                            break;
                        }
                    }
                    break;
                }
            }
        }
        groupAdapter.notifyDataSetChanged();
        goodsAdapter.notifyDataSetChanged();
    }

    /**
     * 清空购物数据
     */
    public void clearList(List<PickGoods> pickGoodsList) {
        for (PickGoods changePickGoods : pickGoodsList) {
            for (Menu menu : goodsList) {
                for (Goods goods : menu.getGoodsList()) {
                    if (goods.getId() == changePickGoods.getGoodsId()) {
                        List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                        for (GoodsSpec goodsSpec : goodsSpecList) {
                            if (changePickGoods.getGoodsId() == goods.getId() && changePickGoods.getGoodsSpecId() == goodsSpec.getId()) {
                                goodsSpec.setBuyCount(0);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        groupAdapter.notifyDataSetChanged();
        goodsAdapter.notifyDataSetChanged();
    }

    public void clearList() {
        for (Menu menu : goodsList) {
            for (Goods goods : menu.getGoodsList()) {
                List<GoodsSpec> goodsSpecList = goods.getGoodsSpecList();
                for (GoodsSpec goodsSpec : goodsSpecList) {
                    goodsSpec.setBuyCount(0);
                }
            }
        }
        groupAdapter.notifyDataSetChanged();
        goodsAdapter.notifyDataSetChanged();
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public View getScrollableView() {
        return goodsListView;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_group:
                int position = (int) v.getTag();

                break;
        }
    }


    private View addRedBagHeadView() {
        linearLayout.removeAllViews();
        if (merchant != null && CheckUtils.isNoEmptyList(merchant.getMerchantRedBagList())) {
            linearLayout.setPadding(getResources().getDimensionPixelSize(R.dimen.x10), getResources().getDimensionPixelSize(R.dimen.x10),
                    getResources().getDimensionPixelSize(R.dimen.x10), 0);
            for(int i = 0, size = merchant.getMerchantRedBagList().size(); i < size; i++) {
                final MerchantRedBag redBag = merchant.getMerchantRedBagList().get(i);
                final View view = LayoutInflater.from(mActivity).inflate(R.layout.merchant_redbag_layout, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x10));
                view.setLayoutParams(layoutParams);

                if (i % 2 == 0) {
                    view.setBackgroundResource(R.drawable.red_bag_bg_red);
                } else {
                    view.setBackgroundResource(R.drawable.red_bag_bg_yellow);
                }
                TextView tvAmt = (TextView) view.findViewById(R.id.tv_red_bag_amt);
                TextView tvName = (TextView) view.findViewById(R.id.tv_red_bag_name);
                TextView tvRestrict = (TextView) view.findViewById(R.id.tv_red_bag_restrict);
                TextView tvGetRedBag = (TextView) view.findViewById(R.id.tv_get_red_bag);

                if(redBag.getAmt() != null) tvAmt.setText(StringUtils.BigDecimal2Str(redBag.getAmt()) + "");
                if(CheckUtils.isNoEmptyStr(redBag.getName())) tvName.setText(redBag.getName());
                if(CheckUtils.isNoEmptyStr(redBag.getUseRule())) tvRestrict.setText(redBag.getUseRule());

                tvGetRedBag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(App.isLogin()){
                            getMerchantRedBag(view, redBag.getId());
                        }else{
                            mActivity.toast("登录之后才可以领取哦~~");
                        }
                    }
                });
                linearLayout.addView(view);
            }
        }
        return linearLayout;
    }

    private void getMerchantRedBag(final View view, long redBagId) {
        dialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", merchant.getId());
        params.put("id", redBagId);
        VolleyOperater<MerchantRedBagModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_MERCHANT_RED_BAG, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String) return;
                    if(((MerchantRedBagModel) obj).getCode() == 0) {
                        mActivity.toast("领取成功");
                        if(linearLayout.getChildCount() == 1){
                            linearLayout.setVisibility(View.GONE);
                            linearLayout.setPadding(0,0,0,0);
                        }else{
                            view.setVisibility(View.GONE);
                        }
                        linearLayout.removeView(view);
                    }
                }
            }
        }, MerchantRedBagModel.class);
    }
}
