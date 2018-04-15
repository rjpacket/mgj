package com.project.mgjandroid.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RedBag;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RedBagListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.MyRedBagActivity;
import com.project.mgjandroid.ui.adapter.RedBagAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/5/30.
 */
public class RedBagFragment extends BaseFragment implements PullToRefreshListView.OnRefreshListener2, View.OnClickListener {

    private PullToRefreshListView listView;
    private RelativeLayout noDataLayout;
    private TextView tvLvTransaction;

    private RedBagAdapter adapter;
    private MLoadingDialog loadingDialog;
    private static final int maxResults = 10;
    private int start = 0;
    private boolean canUse;

    private double longitude;
    private double latitude;
    private double itemsPrice;
    private Long merchantId;
    private String promoInfoJson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        canUse = getArguments().getBoolean("canUse");
        latitude = getArguments().getDouble("latitude", -1);
        longitude = getArguments().getDouble("longitude", -1);
        itemsPrice = getArguments().getDouble("itemsPrice", -1);
        merchantId = getArguments().getLong("merchantId", -1);
        promoInfoJson = getArguments().getString("PromoInfoJson");
        View view = inflater.inflate(R.layout.fragment_red_bag, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingDialog.show(mActivity.getFragmentManager(), "");
        if(canUse && merchantId != -1){
            listView.setMode(PullToRefreshBase.Mode.DISABLED);
            getDataAvailable();
        } else {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
            listView.setOnRefreshListener(this);
            getData(false);
        }
    }

    private void initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.red_bag_list);
        noDataLayout = (RelativeLayout) view.findViewById(R.id.no_data);
        TextView tvTransaction = (TextView) view.findViewById(R.id.tv_transaction);
        View lvTransaction = mInflater.inflate(R.layout.red_bag_footer_view, null);
        tvLvTransaction = (TextView) lvTransaction.findViewById(R.id.tv_transaction);
        tvTransaction.setOnClickListener(this);
        tvLvTransaction.setOnClickListener(this);
        loadingDialog = new MLoadingDialog();

        if (!canUse) {
            tvLvTransaction.setTextColor(0xffff9900);
            tvTransaction.setTextColor(0xffff9900);
            tvLvTransaction.setText("<< 查看可用红包");
            tvTransaction.setText("<< 查看可用红包");
        }

        listView.getRefreshableView().addFooterView(lvTransaction);
        if(merchantId != -1){
            adapter = new RedBagAdapter(R.layout.item_red_package, mActivity, true, canUse);
        } else {
            adapter = new RedBagAdapter(R.layout.item_red_package, mActivity, false, canUse);
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        if (canUse) {
            map.put("isDisabled", 0);
        } else {
            map.put("isDisabled", 1);
        }
        VolleyOperater<RedBagListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_RED_BAG_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = (RedBagListModel) obj;
                    List<RedBag> mlist = new ArrayList<>();
                    mlist.addAll(redBagListModel.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<RedBag> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            tvLvTransaction.setVisibility(View.VISIBLE);
                            noDataLayout.setVisibility(View.GONE);
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                            noDataLayout.setVisibility(View.VISIBLE);
                            tvLvTransaction.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        }, RedBagListModel.class);
    }

    private void getDataAvailable() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("itemsPrice", itemsPrice);
        map.put("merchantId", merchantId);
        map.put("promoInfoJson", promoInfoJson);
        VolleyOperater<RedBagListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FILTER_USABLE_RED_BAG_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                if (loadingDialog.isVisible()) {
                    loadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RedBagListModel redBagListModel = (RedBagListModel) obj;
                    List<RedBag> mlist = new ArrayList<>();
                    mlist.addAll(redBagListModel.getValue());
                    adapter.setData(mlist);
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        noDataLayout.setVisibility(View.INVISIBLE);
                        tvLvTransaction.setVisibility(View.VISIBLE);
                    } else {
                        noDataLayout.setVisibility(View.VISIBLE);
                        tvLvTransaction.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }, RedBagListModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_transaction) {
            ((MyRedBagActivity) mActivity).doTransaction(canUse);
        }
    }
}
