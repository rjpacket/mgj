package com.project.mgjandroid.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.CommercialCommentAdapter;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvaluateFragment extends HeaderViewPagerFragment implements OnClickListener, RadioGroup.OnCheckedChangeListener {
    protected BaseActivity mActivity;
    protected View view;
    private PullToRefreshListView listView;
    private CommercialCommentAdapter adapter;
    private static final int maxResults = 5;
    private LinearLayout headerView;
    private TextView tvScore, tvServiceScore, tvEvaluateScore, tvHigher, tvUnEmpty;
    private RadioButton tvAll, tvSatisfy, tvYawp;
    private RatingBar serviceScoreBar, evaluateScoreBar;
    protected boolean refreshFlag = true;
    /**
     * 全部
     */
    private static final int TYPE_ALL = 0;
    /**
     * 满意
     */
    private static final int TYPE_SATISFY = 1;
    /**
     * 不满意
     */
    private static final int TYPE_YAWP = 2;

    private int currentType;
    private boolean isShowUnEmpty = false;
    private int merchantId;
    private int start = 0;
    private ListView refreshableView;
    private int currentState = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.evaluate_fragment, container, false);
//        initNoListView(view);
        currentType = TYPE_ALL;
        initListView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//		getData(true, currentType, false, isShowUnEmpty);
    }

    public void setHeader(Merchant merchant) {
        if (merchant != null) {
//			tvScore.setText(merchant.getRate() + "");
//			tvServiceScore.setText(merchant.getServiceRate() + "分");
//			serviceScoreBar.setRating(merchant.getServiceRate());
//			tvEvaluateScore.setText(merchant.getProductRate() + "分");
//			evaluateScoreBar.setRating(merchant.getProductRate());
//			tvHigher.setText("高于周边商家" + merchant.getCompetiveRate() + "%");
//			tvAll.setText("全部(" + merchant.getCommentCount() + ")");
//			tvSatisfy.setText("满意(" + merchant.getSatisfiedCommentCount() + ")");
//			tvYawp.setText("不满意(" + merchant.getUnsatisfiedCommentCount() + ")");
        }
    }

    private void initListView() {
        listView = (PullToRefreshListView) view.findViewById(R.id.evaluate_fragment_listView);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
//        listView.setAddMoreCountText(maxResults);
        adapter = new CommercialCommentAdapter(mActivity);
//        refreshableView = listView.getRefreshableView();
        headerView = (LinearLayout) mActivity.getLayoutInflater().inflate(R.layout.layout_shop_evaluate_header_view, null);
        tvScore = (TextView) headerView.findViewById(R.id.evaluate_fragment_tv_score);
        tvServiceScore = (TextView) headerView.findViewById(R.id.evaluate_fragment_tv_service_score);
        tvHigher = (TextView) headerView.findViewById(R.id.evaluate_tv_higher);
        tvEvaluateScore = (TextView) headerView.findViewById(R.id.evaluate_fragment_tv_evaluate_score);
        serviceScoreBar = (RatingBar) headerView.findViewById(R.id.evaluate_fragment_rat_service_score);
        evaluateScoreBar = (RatingBar) headerView.findViewById(R.id.evaluate_fragment_rat_evaluate_score);
        RadioGroup rgLabel = (RadioGroup) headerView.findViewById(R.id.select_bar);
        rgLabel.setOnCheckedChangeListener(this);
        tvAll = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_all);
        tvAll.setChecked(true);
        tvSatisfy = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_satisfy);
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp = (RadioButton) headerView.findViewById(R.id.evaluate_fragment_yawp);
        tvYawp.setTextColor(Color.parseColor("#ffdc550f"));

        tvUnEmpty = (TextView) headerView.findViewById(R.id.evaluate_fragment_show_un_empty);
        tvUnEmpty.setOnClickListener(this);
        listView.getRefreshableView().addHeaderView(headerView);
        listView.setAdapter(adapter);
//        View noDataView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_list_no_data, null);
//        listView.setEmptyView(noDataView);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    start += maxResults;
                    getMerchantEvaluate();
                }
            }
        });
        getMerchantEvaluate();
    }

    public void setData(Merchant merchant) {
        BigDecimal averageScore = merchant.getMerchantScore();
        evaluateScoreBar.setRating(averageScore.floatValue());
        BigDecimal shipScore = merchant.getShipScore();
        serviceScoreBar.setRating(shipScore.floatValue());
        tvEvaluateScore.setText(CommonUtils.BigDecimal2Str(merchant.getMerchantScore()));
        tvServiceScore.setText(CommonUtils.BigDecimal2Str(merchant.getShipScore()));
        tvScore.setText(CommonUtils.BigDecimal2Str(merchant.getAverageScore()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluate_fragment_show_un_empty:
                tvUnEmpty.setSelected(!tvUnEmpty.isSelected());
                //TODO 刷新列表
                break;

            default:
                break;
        }
    }

    @Override
    public View getScrollableView() {
        return listView.getRefreshableView();
    }

    /**
     * 获取商家评价
     */
    private void getMerchantEvaluate() {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<>();
        map.put("merchantId", merchantId);
        map.put("start", start);
        map.put("size", maxResults);
        VolleyOperater<MerchantEvaluateModel> operater = new VolleyOperater<MerchantEvaluateModel>(mActivity);
        operater.doRequest(Constants.URL_MERCHANT_EVALUATE, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    MerchantEvaluateModel evaluateModel = (MerchantEvaluateModel) obj;
                    ArrayList<MerchantEvaluateModel.ValueEntity> mlist = evaluateModel.getValue();

                    if (CheckUtils.isNoEmptyList(mlist)) {
//                        if (mlist.size() < maxResults) {
//                            ToastUtils.displayMsg("到底了", mActivity);
//                        }
                        ArrayList<MerchantEvaluateModel.ValueEntity> mListOrg = adapter.getList();
                        if (mListOrg != null) {
                            mListOrg.addAll(mlist);
                            adapter.setList(mListOrg);
                            setRadioGroup();
                        }
                    }
                }
            }
        }, MerchantEvaluateModel.class);
    }

    private void setRadioGroup() {
        ArrayList<MerchantEvaluateModel.ValueEntity> mListOrg = adapter.getList();
        tvAll.setText("全部(" + mListOrg.size() + ")");
    }

    public void setMerchant(int merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.evaluate_fragment_all:
                changeTextColor(tvAll, tvSatisfy, tvYawp);
                break;
            case R.id.evaluate_fragment_satisfy:
                changeTextColor(tvSatisfy, tvAll, tvYawp);
                break;
            case R.id.evaluate_fragment_yawp:
                changeTextColor(tvYawp, tvSatisfy, tvAll);
                break;
        }
    }

    private void changeTextColor(RadioButton tvAll, RadioButton tvSatisfy, RadioButton tvYawp) {
        tvAll.setTextColor(getResources().getColor(R.color.white));
        tvSatisfy.setTextColor(Color.parseColor("#ffdc550f"));
        tvYawp.setTextColor(Color.parseColor("#ffdc550f"));
    }
}
