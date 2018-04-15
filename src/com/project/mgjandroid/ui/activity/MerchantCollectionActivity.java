package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.UserFavorites;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.UserFavoritesModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.UserFavoritesAdapter;
import com.project.mgjandroid.ui.view.LoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/4/26.
 */
public class MerchantCollectionActivity extends BaseActivity {

    @InjectView(R.id.merchant_collection_act_back)
    private ImageView ivBack;
    @InjectView(R.id.merchant_collection_listView)
    private PullToRefreshListView listView;
    @InjectView(R.id.layout_no_favorites)
    private LinearLayout layoutNoFavorites;

    private UserFavoritesAdapter adapter;
    private LoadingDialog mLoadingDialog;

    private int currentStartResult = 0;
    private int maxResults = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_collection);
        Injector.get(this).inject();
        initView();
        mLoadingDialog.show();
        getDate(false);
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initListView();
        mLoadingDialog = new LoadingDialog(mActivity);
    }


    private void initListView() {

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new UserFavoritesAdapter(mActivity);
        adapter.setClickListener(new MyLongClickListener());
        listView.setAdapter(adapter);

        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentStartResult = 0;
                getDate(false);
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentStartResult = adapter.getCount();
                getDate(true);
            }
        });

    }

    private void getDate(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", currentStartResult);
        map.put("size", maxResults);
        if(PreferenceUtils.getLocation(mActivity)[0]!=null&&PreferenceUtils.getLocation(mActivity)[1]!=null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        VolleyOperater<UserFavoritesModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (mLoadingDialog !=null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                if (isSucceed && obj != null) {
                    ArrayList<UserFavorites> mlist = ((UserFavoritesModel) obj).getValue();
                    if (!isLoadMore) {
                        if (CheckUtils.isEmptyList(mlist)) {

                        }
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<UserFavorites> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if (mlist.size() < maxResults) {
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<UserFavorites> mlistOrg = adapter.getList();
                            mlistOrg.clear();
                            mlistOrg.addAll(mlist);
                            adapter.setList(mlistOrg);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            layoutNoFavorites.setVisibility(View.VISIBLE);
                            listView.setMode(PullToRefreshBase.Mode.DISABLED);
                            adapter.setList(mlist);
                        }
                    }
                }
            }
        }, UserFavoritesModel.class);

    }

    private class MyLongClickListener implements UserFavoritesAdapter.LongClickListener {
        @Override
        public void doClick(int position) {
            sendFavorMerchantRequest(position);
        }
    }

    private void sendFavorMerchantRequest(final int position) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("merchantId", adapter.getList().get(position).getMerchantId());
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_CANCEL_USER_FAVORITES, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj!= null) {
                    ToastUtils.displayMsg("已取消收藏", mActivity);
                    ArrayList<UserFavorites> userFavorites = adapter.getList();
                    userFavorites.remove(position);
                    adapter.setList(userFavorites);
                    if(CheckUtils.isEmptyList(userFavorites)) {
                        layoutNoFavorites.setVisibility(View.VISIBLE);
                        listView.setMode(PullToRefreshBase.Mode.DISABLED);
                    }
                }
            }
        }, Object.class);
    }
}
