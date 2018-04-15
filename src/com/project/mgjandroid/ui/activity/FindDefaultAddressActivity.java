package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.DefaultAddressModel;
import com.project.mgjandroid.model.DefaultAddressModel.DefaultAddress;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.DefaultAddressAdapter;
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
 * Created by yuandi on 2016/5/3.
 */
public class FindDefaultAddressActivity extends BaseActivity{

    @InjectView(R.id.find_default_address_act_back)
    private ImageView ivBack;
    @InjectView(R.id.find_default_address_act_listView)
    private PullToRefreshListView listView;

    private DefaultAddressAdapter adapter;
    private int currentStartResult = 0;
    private int maxResults = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_default_address);
        Injector.get(this).inject();
        initView();
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
    }

    private void initListView() {

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new DefaultAddressAdapter(mActivity);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setActivityResult(adapter.getList().get(position - 1));
            }
        });
    }

    private void setActivityResult(DefaultAddress defaultAddress) {
        PreferenceUtils.saveLocation(defaultAddress.getLatitude() + "", defaultAddress.getLongitude() + "", this);
        PreferenceUtils.saveAddressName(defaultAddress.getAddress(), this);
        PreferenceUtils.saveAddressDes("", this);
        setResult(RESULT_OK);
        back();
    }

    private void getDate(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", currentStartResult);
        map.put("size", maxResults);
        VolleyOperater<DefaultAddressModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_DEFAULT_ADDRESS_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    ArrayList<DefaultAddress> mlist = ((DefaultAddressModel) obj).getValue();
                    if(!isLoadMore) {
                        if (CheckUtils.isEmptyList(mlist)) {

                        }
                    }
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<DefaultAddress> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            if (mlist.size() < maxResults) {
                                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                            }
                            ArrayList<DefaultAddress> mlistOrg = adapter.getList();
                            mlistOrg.clear();
                            mlistOrg.addAll(mlist);
                            adapter.setList(mlistOrg);
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
        }, DefaultAddressModel.class);
    }
}
