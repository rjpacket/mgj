package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RecruitBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RecruitListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.RecruitInfomationAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/6/20.
 *
 */
public class MyPublishInfoActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView>{

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvPublish;
    @InjectView(R.id.tv_type_1)
    private TextView tvType1;
    @InjectView(R.id.line_1)
    private View line1;
    @InjectView(R.id.tv_type_2)
    private TextView tvType2;
    @InjectView(R.id.line_2)
    private View line2;
    @InjectView(R.id.my_publish_info_listview)
    private PullToRefreshListView listView;

    private RecruitInfomationAdapter adapter;
    public MLoadingDialog loadingDialog;
    private static final int maxResults = 10;
    private int start = 0;
    private int type = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_info);
        Injector.get(this).inject();
        initView();
        initData();
    }

    private void initView() {
//        tvPublish.setVisibility(View.VISIBLE);
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            tvPublish.setBackgroundDrawable(null);
//        } else {
//            tvPublish.setBackground(null);
//        }
//        tvPublish.setPadding(0, 0, 0, 0);
//        tvPublish.setOnClickListener(this);
//        tvPublish.setText(getString(R.string.full_time_publish));
        tvTitle.setText("求职招聘");
        ivBack.setOnClickListener(this);
        tvType1.setOnClickListener(this);
        tvType2.setOnClickListener(this);

        View view = new View(this);
        ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x10));
        view.setLayoutParams(layoutParams);
        listView.getRefreshableView().addFooterView(view);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new RecruitInfomationAdapter(R.layout.recruit_item, mActivity, true);
        adapter.setListener(new AdapterListener());
        listView.setAdapter(adapter);
        listView.getRefreshableView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return hideMoreSetLayout();
            }
        });

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView textView = (TextView) emptyView.findViewById(R.id.tv_no_data);
        textView.setText("您还没有发布");
        listView.setEmptyView(emptyView);

        loadingDialog = new MLoadingDialog();
    }

    private boolean hideMoreSetLayout(){
        for (int i = listView.getRefreshableView().getFirstVisiblePosition(),
             size = listView.getRefreshableView().getLastVisiblePosition(); i <= size; i++) {
            if(listView.getRefreshableView().getChildAt(i) != null){
                View view = listView.getRefreshableView().getChildAt(i).findViewById(R.id.setting_layout);
                if (view != null && view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                    return true;
                }
            }
        }
        return false;
    }

    private void initData() {
        loadingDialog.show(getFragmentManager(), "");
        getData(false);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("type", type);
        VolleyOperater<RecruitListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_POSITION_RECRUIT_INFORMATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                loadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    RecruitListModel recruitListModel = (RecruitListModel) obj;
                    List<RecruitBean> mlist = new ArrayList<>();
                    mlist.addAll(recruitListModel.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<RecruitBean> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, RecruitListModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
//            case R.id.tv_publish:
//                Intent intent = new Intent(this, JobMessageActivity.class);
//                startActivity(intent);
//                break;
            case R.id.tv_type_1:
                if (type == 1) return;
                start = 0;
                type = 1;
                line2.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                tvType1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                line1.setBackgroundColor(getResources().getColor(R.color.white));
                tvType2.setTextColor(getResources().getColor(R.color.color_6));
                getData(false);
                break;
            case R.id.tv_type_2:
                if (type == 2) return;
                start = 0;
                type = 2;
                line2.setBackgroundColor(getResources().getColor(R.color.white));
                tvType1.setTextColor(getResources().getColor(R.color.color_6));
                line1.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                tvType2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                getData(false);
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    private class AdapterListener implements RecruitInfomationAdapter.HandleMoreSetLayoutListener {
        @Override
        public boolean hide() {
            return hideMoreSetLayout();
        }
    }
}
