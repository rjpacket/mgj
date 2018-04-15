package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.HistoryEntity;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.HotSearchModel;
import com.project.mgjandroid.model.SearchModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.HistoryListAdapter;
import com.project.mgjandroid.ui.adapter.SearchListAdapter;
import com.project.mgjandroid.ui.view.FlowLayout;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener, TextWatcher,PullToRefreshBase.OnRefreshListener2, View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int PAGE_SIZE = 20;
    @InjectView(R.id.search_list_view) private PullToRefreshListView mListView;
    @InjectView(R.id.history_list_view) private ListView historyListView;
    @InjectView(R.id.search_text) private EditText mSearchText;
    @InjectView(R.id.login_back) private ImageView back;
    @InjectView(R.id.home_fragment_no_net) private LinearLayout hasNoNet;
    @InjectView(R.id.home_fragment_reload) private TextView tvReload;
    @InjectView(R.id.iv_delete) private ImageView ivSearchTxtClean;
    @InjectView(R.id.root_view) private View mRootView;

    private List<String> labels;
    private SearchListAdapter mSearchListAdapter;
    private int mCurrentPosition = 0;
    private boolean isRefresh = false;
    private HistoryListAdapter mHistoryListAdapter;
    private View mFooterView;
    private ArrayList<HistoryEntity> mHistoryEntities;
    private LinearLayout mHistoryLabel;
    private RelativeLayout mHotLabel;
    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Injector.get(mActivity).inject();

        labels = new ArrayList<>();

        initHeader();
        initView();
        if(checkNetwork()){
            getHotSearch();
        }
    }

    private boolean checkNetwork() {
        if(CommonUtils.isNetworkConnected()){
            hasNoNet.setVisibility(View.GONE);
            return true;
        }else{
            hasNoNet.setVisibility(View.VISIBLE);
        }
        return false;
    }

    private void initHeader() {
        View headerView = mInflater.inflate(R.layout.layout_history_list_view_header, null);
        mFlowLayout = (FlowLayout) headerView.findViewById(R.id.hot_search);
        mHistoryLabel = (LinearLayout) headerView.findViewById(R.id.history_label);
        mHotLabel = (RelativeLayout) headerView.findViewById(R.id.hot_label);

        addHotSearch();

        historyListView.addHeaderView(headerView);
        mFooterView = mInflater.inflate(R.layout.layout_history_list_footer_view, null);
        TextView tvClear = (TextView) mFooterView.findViewById(R.id.clear_history);
        tvClear.setOnClickListener(this);
        historyListView.addFooterView(mFooterView);
        ivSearchTxtClean.setOnClickListener(this);
        mHistoryListAdapter = new HistoryListAdapter(R.layout.item_history_list_view, mActivity);
        historyListView.setAdapter(mHistoryListAdapter);
        historyListView.setOnItemClickListener(this);
        historyListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        CommonUtils.hideKeyBoard(mActivity);
                        break;
                }
                return false;
            }
        });
        refreshHistorySearch();
    }

    private void addHotSearch() {
        mFlowLayout.removeAllViews();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DipToPx.dip2px(mActivity, 12), DipToPx.dip2px(mActivity, 10), 0, 0);// 设置边距
        for (int i = 0; i < labels.size(); i++) {
            final TextView textView = new TextView(mActivity);
            textView.setTag(i);
            textView.setTextSize(14);
            textView.setText(labels.get(i));
            textView.setPadding(DipToPx.dip2px(mActivity,12), DipToPx.dip2px(mActivity,6), DipToPx.dip2px(mActivity,12), DipToPx.dip2px(mActivity,6));
            textView.setTextColor(getResources().getColor(R.color.gray_text_3));
            textView.setBackgroundResource(R.drawable.lable_item_bg_normal);
            mFlowLayout.addView(textView, layoutParams);
            // 标签点击事件
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(), labels.get((int) textView.getTag()), Toast.LENGTH_SHORT).show();
                    String search = labels.get((int) textView.getTag());
                    mSearchText.setText(search);
                    doSearch(search);
                }
            });
        }
    }

    private void refreshHistorySearch() {
        String historySearch = PreferenceUtils.getStringPreference("history_search", "", mActivity);
        if(historySearch != null && !"".equals(historySearch)){
            mFooterView.setVisibility(View.VISIBLE);
            mHistoryLabel.setVisibility(View.VISIBLE);
            String[] split = historySearch.split(",");
            mHistoryEntities = new ArrayList<>();
            for (String str : split) {
                HistoryEntity entity = new HistoryEntity();
                entity.setName(str);
                mHistoryEntities.add(entity);
            }
            mHistoryListAdapter.setData(mHistoryEntities);
        }else{
            mHistoryListAdapter.clear();
            mFooterView.setVisibility(View.GONE);
            mHistoryLabel.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mListView.setVisibility(View.GONE);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mSearchListAdapter = new SearchListAdapter(R.layout.item_search_merchant,mActivity);
        mSearchListAdapter.setData(new ArrayList<Merchant>());
        mListView.setAdapter(mSearchListAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CommonUtils.hideKeyBoard2(mSearchText);
                return false;
            }
        });

        mSearchText.setOnEditorActionListener(this);
        mSearchText.addTextChangedListener(this);

        back.setOnClickListener(this);
        tvReload.setOnClickListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_SEARCH){
            String search = mSearchText.getText().toString().trim();
            if(search != null && !"".equals(search)) {
                doSearch(search);
            }
            return true;
        }
        return false;
    }

    private void doSearch(String search) {
        if(checkNetwork()) {
            goSearchMerchant(search, false, false);
        }
    }

    private void savePreference(String search) {
        String historySearch = PreferenceUtils.getStringPreference("history_search", "", mActivity);
        if(historySearch != null && !"".equals(historySearch)){
            String[] split = historySearch.split(",");
            boolean isFind = false;
            for (String str : split) {
                if(search.equals(str)){
                    isFind = true;
                }
            }
            if(!isFind) {
                PreferenceUtils.saveStringPreference("history_search", historySearch + "," + search, mActivity);
            }
        }else{
            PreferenceUtils.saveStringPreference("history_search", search, mActivity);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.length() == 0){
            ivSearchTxtClean.setVisibility(View.GONE);
            historyListView.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
            mSearchListAdapter.clear();
            refreshHistorySearch();
        }else{
            ivSearchTxtClean.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() > 0){
            goSearchMerchant(s.toString().trim(), false, true);
        }
    }

    /**
     * 搜索参数
     */
    private void goSearchMerchant(String param, final boolean isAddMore, final boolean isAutoLoad) {
        isRefresh = true;
        Map<String, Object> params = new HashMap<>();
        params.put("start", mCurrentPosition);
        params.put("size", PAGE_SIZE);
        String lon = PreferenceUtils.getStringPreference(PreferenceUtils.LONGITUDE, "", mActivity);
        if(lon != null && !"".equals(lon)){
            params.put("longitude", Double.parseDouble(lon));
        }
        String lat = PreferenceUtils.getStringPreference(PreferenceUtils.LATITUDE, "", mActivity);
        if(lat != null && !"".equals(lat)){
            params.put("latitude", Double.parseDouble(lat));
        }
        params.put("searchParam", param);
        VolleyOperater<SearchModel> operater = new VolleyOperater<>(SearchActivity.this);
        operater.doRequest(Constants.URL_SEARCH, params, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (!isAutoLoad) CommonUtils.hideKeyBoard(mActivity);
                if(mListView.isRefreshing()) {
                    mListView.onRefreshComplete();
                }
                isRefresh = false;
                if (isSucceed && obj != null) {
                    //有数据的时候，改变布局
                    historyListView.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);

                    SearchModel searchModel = (SearchModel) obj;
                    List<Merchant> value = searchModel.getValue();
                    if(isAddMore){
                        if(value.size() > 0) {
                            mSearchListAdapter.getData().addAll(value);
                        }else{
                            ToastUtils.displayMsg("到底了",mActivity);
                        }
                    }else {
                        if(value.size() > 0) {
                            ArrayList<Merchant> list = new ArrayList<Merchant>();
                            list.addAll(value);
                            mSearchListAdapter.setData(list);
                        }else{
                            mSearchListAdapter.clear();
                            if (!isAutoLoad) ToastUtils.displayMsg("没有搜索到商家",mActivity);
                        }
                    }
                }
            }
        }, SearchModel.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        if(!isRefresh) {
            mCurrentPosition = 0;
            goSearchMerchant(mSearchText.getText().toString().trim(), false, false);
        }
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if(!isRefresh) {
            mCurrentPosition += PAGE_SIZE;
            goSearchMerchant(mSearchText.getText().toString().trim(), true, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_history:
                PreferenceUtils.removePreference("history_search");
                refreshHistorySearch();
                break;
            case R.id.login_back:
                onBackPressed();
                break;
            case R.id.iv_delete:
                mSearchText.setText("");
                break;
            case R.id.home_fragment_reload:
                String str = mSearchText.getText().toString().trim();
                if(TextUtils.isEmpty(str)) {
                    if (checkNetwork()) {
                        getHotSearch();
                    }
                }else{
                    if (checkNetwork()) {
                        goSearchMerchant(str, false, false);
                    }
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()){
            case R.id.history_item:
                HistoryEntity entity = mHistoryListAdapter.getData().get(position - 1);
                mSearchText.setText(entity.getName());
                doSearch(entity.getName());
                break;
            case R.id.search_item:
                savePreference(mSearchText.getText().toString().trim());
                Intent intent = new Intent(this,CommercialActivity.class);
                intent.putExtra(CommercialActivity.MERCHANT_ID, mSearchListAdapter.getData().get(position - 1).getId().intValue());
                startActivity(intent);
                break;
        }
    }

    /**
     * 请求热门搜索
     */
    private void getHotSearch() {
        Map<String, Object> map = new HashMap<>();
        map.put("longitude", PreferenceUtils.getStringPreference(PreferenceUtils.LONGITUDE,"",mActivity));
        map.put("latitude", PreferenceUtils.getStringPreference(PreferenceUtils.LATITUDE, "", mActivity));
        VolleyOperater<HotSearchModel> operater = new VolleyOperater<>(SearchActivity.this);
        operater.doRequest(Constants.URL_HOT_SEARCH, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    HotSearchModel hotSearchModel = (HotSearchModel) obj;
                    List<HotSearchModel.ValueEntity> value = hotSearchModel.getValue();
                    if(CheckUtils.isNoEmptyList(value)){
                        mHotLabel.setVisibility(View.VISIBLE);
                        for (HotSearchModel.ValueEntity entity : value) {
                            labels.add(entity.getName());
                        }
                        addHotSearch();
                    }
                }
            }
        }, HotSearchModel.class);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonUtils.hideKeyBoard2(mSearchText);
        return super.onTouchEvent(event);
    }
}
