package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.bean.PositionCategoryBean;
import com.project.mgjandroid.bean.RecruitBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.RecruitHotPositionCategoryModel;
import com.project.mgjandroid.model.RecruitListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.RecruitInfomationAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuandi on 2016/6/21.
 *
 */
@Router(value = "jobHunting/:type" , intExtra = "type")
public class RecruitActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2<ListView> {

    public final static String CATEGORY_ID = "category_id";
    public final static String CATEGORY_NAME = "category_name";
    public final static String IS_FROM_RECRUIT_ACTIVITY = "is_from_recruit_activity";

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvCity;
    @InjectView(R.id.recruit_position_layout)
    private LinearLayout recruitPositionLayout;
    @InjectView(R.id.list_view)
    private PullToRefreshListView listView;
    @InjectView(R.id.tv_to_publish)
    private TextView tvPublish;

    private RecruitInfomationAdapter adapter;
    private MLoadingDialog mMLoadingDialog;
    private static final int maxResults = 10;
    private int start = 0;

    private long categoryId;
    private String categoryName;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        Injector.get(this).inject();
        categoryId = getIntent().getLongExtra(CATEGORY_ID, -1);
        categoryName = getIntent().getStringExtra(CATEGORY_NAME);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("type")){
            type = intent.getIntExtra("type", 2);
        } else {
            type = ChooseCityModel.getInstance().getType();
        }
        initView();
        initData();
    }

    private void initView() {
        if (categoryName != null) {
            tvTitle.setText(categoryName);
        } else {
            if (type == 1) {
                tvTitle.setText("个人求职");
            } else if (type == 2) {
                tvTitle.setText("招聘信息");
            }
        }
        if (type == 1) {
            tvPublish.setText(getString(R.string.full_time_publish));
        } else if (type == 2) {
            tvPublish.setText(getString(R.string.full_time_publish_1));
        }
        tvCity.setVisibility(View.VISIBLE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            tvCity.setBackgroundDrawable(null);
        } else {
            tvCity.setBackground(null);
        }

        tvCity.setPadding(0, 0, 0, 0);
        tvCity.setTextSize(12);
        Drawable arrow = getResources().getDrawable(R.drawable.down_arrow);
        if (arrow != null) {
            arrow.setBounds(0, 0, arrow.getMinimumWidth(), arrow.getMinimumHeight());
            tvCity.setCompoundDrawables(null, null, arrow, null);
            tvCity.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.x4));
        }

        ivBack.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        tvPublish.setOnClickListener(this);

        mMLoadingDialog = new MLoadingDialog();

        View view = new View(this);
        ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x10));
        view.setLayoutParams(layoutParams);
        listView.getRefreshableView().addFooterView(view);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new RecruitInfomationAdapter(R.layout.recruit_item, mActivity, false);
        listView.setAdapter(adapter);

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        if (type == 1) {
            tvEmpty.setText("暂无求职信息");
        } else if (type == 2) {
            tvEmpty.setText("暂无招聘信息");
        }
        listView.setEmptyView(emptyView);
    }

    private void initData() {
            ChooseCityModel.getInstance().setCityName(PreferenceUtils.getAddressCity(mActivity));
            try {
                long cityCode = Long.parseLong(PreferenceUtils.getAddressCityCode(mActivity));
                ChooseCityModel.getInstance().setCityCode(cityCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        if (!TextUtils.isEmpty(ChooseCityModel.getInstance().getCityName())) {
            tvCity.setText(ChooseCityModel.getInstance().getCityName());
        } else {
            tvCity.setText("切换城市");
        }
        mMLoadingDialog.show(getFragmentManager(), "");
        if(categoryId == -1) getTag();
        getData(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ChooseCityModel.getInstance().isHasChanged()){
            if (!TextUtils.isEmpty(ChooseCityModel.getInstance().getCityName())) {
                tvCity.setText(ChooseCityModel.getInstance().getCityName());
            } else {
                tvCity.setText("切换城市");
            }
            start = 0;
            mMLoadingDialog.show(getFragmentManager(), "");
            getData(false);
            ChooseCityModel.getInstance().setHasChanged(false);
        }
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("type", type);
        if (categoryId != -1) map.put("positionCategoryId", categoryId);
        if (ChooseCityModel.getInstance().getCityCode() != 0) map.put("cityCode", ChooseCityModel.getInstance().getCityCode());
        if (ChooseCityModel.getInstance().getProvinceId() != 0) map.put("province", ChooseCityModel.getInstance().getProvinceId());
        if (ChooseCityModel.getInstance().getCityId() != 0) map.put("city", ChooseCityModel.getInstance().getCityId());
        if (ChooseCityModel.getInstance().getDistrictId() != 0)map.put("district", ChooseCityModel.getInstance().getDistrictId());
        VolleyOperater<RecruitListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_POSITION_RECRUIT_INFORMATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                listView.onRefreshComplete();
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
            case R.id.tv_publish:
                Intent intent1 = new Intent(mActivity, ChooseCityActivity.class);
                intent1.putExtra("from", IS_FROM_RECRUIT_ACTIVITY);
                startActivity(intent1);
                break;
            case R.id.tv_to_publish:
                if(App.isLogin()) {
                    ChooseCityModel.getInstance().setType(type);
                    Intent intent = new Intent(this, JobMessageActivity.class);
                    startActivity(intent);
                }else{
                    startActivity(new Intent(this,SmsLoginActivity.class));
                }
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

    /**
     * 获取tag
     */
    public void getTag() {
        VolleyOperater<RecruitHotPositionCategoryModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_FIND_RECRUIT_HOT_POSITION_CATEGORY_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        showTag(new ArrayList<PositionCategoryBean>());
                        return;
                    }
                    RecruitHotPositionCategoryModel model = (RecruitHotPositionCategoryModel) obj;
                    if(model.getValue() != null){
                        showTag(model.getValue());
                    }
                }else{
                    showTag(new ArrayList<PositionCategoryBean>());
                }
            }
        }, RecruitHotPositionCategoryModel.class);
    }

    private void showTag(List<PositionCategoryBean> list) {
        recruitPositionLayout.setVisibility(View.VISIBLE);
        recruitPositionLayout.removeAllViews();
        for(int i = 0; i < 5 ; i++) {
            final TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.color_6));
            textView.setTextSize(13);
            if(i == 4) {
                View view = new View(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, 20, 0, 20);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(getResources().getColor(R.color.color_d2));
                recruitPositionLayout.addView(view);
            }
            recruitPositionLayout.addView(textView);
            if(i < 4 && list.size() > i){
                textView.setText(list.get(i).getName());
                textView.setTag(list.get(i).getId());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChooseCityModel.getInstance().setType(type);
                        Intent intent = new Intent(mActivity, RecruitActivity.class);
                        intent.putExtra(CATEGORY_NAME, textView.getText());
                        intent.putExtra(CATEGORY_ID, (Long) textView.getTag());
                        startActivity(intent);
                    }
                });
                if(i < 3){
                    View view = new View(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(0, 20, 0, 20);
                    view.setLayoutParams(layoutParams);
                    view.setBackgroundColor(getResources().getColor(R.color.color_d2));
                    recruitPositionLayout.addView(view);
                }
            }else if(i == 4) {
                textView.setText("更多");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent allTag = new Intent(mActivity, FulltimeJobActivity.class);
                        allTag.putExtra("from", IS_FROM_RECRUIT_ACTIVITY);
                        startActivity(allTag);
                    }
                });
            }
        }
    }
}
