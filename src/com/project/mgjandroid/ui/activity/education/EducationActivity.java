package com.project.mgjandroid.ui.activity.education;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationInformation;
import com.project.mgjandroid.bean.SecondHandBannerBean;
import com.project.mgjandroid.bean.SecondHandCategoryBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.constants.InformationType;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.EducationModel;
import com.project.mgjandroid.model.SecondHandBannerModel;
import com.project.mgjandroid.model.SecondHandCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.ui.activity.PublishEducationInfoActivity;
import com.project.mgjandroid.ui.activity.RecruitActivity;
import com.project.mgjandroid.ui.activity.renthouse.RentHouseActivity;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.CircleIndicator;
import com.project.mgjandroid.ui.view.scrollloopviewpager.widget.MyBanner;
import com.project.mgjandroid.utils.AnimatorUtils;
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

public class EducationActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener{
    public static final String IS_FROM_EDUCATION_ACTIVITY = "education_activity";
    private int HEADER_SIZE = 1;
    @InjectView(R.id.common_back)
    private ImageView mBack;
    @InjectView(R.id.common_title)
    private TextView mTitle;
    @InjectView(R.id.tv_publish)
    private TextView tvCity;
    @InjectView(R.id.education_list_view)
    private PullToRefreshListView mListView;
    @InjectView(R.id.education_container)
    private LinearLayout recruitPositionLayout;
    @InjectView(R.id.tv_to_publish)
    private TextView tvPublish;
    private MLoadingDialog mMLoadingDialog;
    private long categoryId = -1;

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    private int START = 0;
    private int PAGE_SIZE = 20;
    //1.收购 2.出售
    private int type;
    private EducationListAdapter mAdapter;
    private MyBanner mMyBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        Injector.get(this).inject();
        initView();
        initData();
    }

    private void initView() {
        type = ChooseCityModel.getInstance().getSecondHandType();
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(CATEGORY_ID)) {
            categoryId = intent.getLongExtra(CATEGORY_ID, -1);
            String name = intent.getStringExtra(CATEGORY_NAME);
            mTitle.setText(name);
        }else {
            if(type == 2) {
                mTitle.setText("家教信息");
            }else{
                mTitle.setText("教育培训");
            }
        }

        mBack.setOnClickListener(this);
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
        tvCity.setOnClickListener(this);

        if(categoryId == -1 ) {
            getBanner();
        }
        mAdapter = new EducationListAdapter(R.layout.item_education_list_view, mActivity);
        mListView.setAdapter(mAdapter);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setOnItemClickListener(this);
        mListView.setOnRefreshListener(this);
        tvPublish.setOnClickListener(this);

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_no_data);
        if (type == 2) {
            tvEmpty.setText("暂无家教信息");
        } else if (type == 1) {
            tvEmpty.setText("暂无教育信息");
        }
        mListView.setEmptyView(emptyView);
    }

    private void getBanner() {
        Map<String, Object> map = new HashMap<>();
        map.put("informationType", InformationType.Education.getValue());
        if (ChooseCityModel.getInstance().getCityCode() != 0) map.put("cityCode", ChooseCityModel.getInstance().getCityCode());
        if (ChooseCityModel.getInstance().getProvinceId() != 0) map.put("province", ChooseCityModel.getInstance().getProvinceId());
        if (ChooseCityModel.getInstance().getCityId() != 0) map.put("city", ChooseCityModel.getInstance().getCityId());
        if (ChooseCityModel.getInstance().getDistrictId() != 0)map.put("district", ChooseCityModel.getInstance().getDistrictId());
        VolleyOperater<SecondHandBannerModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_INFOMATION_PUBLISH_BANNER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    SecondHandBannerModel secondHandBannerModel = (SecondHandBannerModel) obj;
                    List<SecondHandBannerBean> bannerBeen = secondHandBannerModel.getValue();
                    ArrayList<String> list = new ArrayList<String>();
                    for (SecondHandBannerBean bean : bannerBeen) {
                        list.add(bean.getPicUrl());
                    }
                    if(list.size() > 0){
                        addBanner(list);
                    }
                }
            }
        }, SecondHandBannerModel.class);
    }

    private void addBanner(ArrayList<String> list) {
        mMyBanner = (MyBanner) mInflater.inflate(R.layout.my_banner, null);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DipToPx.dip2px(mActivity, 96));
        CircleIndicator circleIndicator = (CircleIndicator) mMyBanner.findViewById(R.id.pageIndexor);
        circleIndicator.setVisibility(View.GONE);
        mMyBanner.setLayoutParams(layoutParams);
        mMyBanner.setPadding(DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15), DipToPx.dip2px(mActivity, 15));
        ListView refreshableView = mListView.getRefreshableView();
        refreshableView.addHeaderView(mMyBanner);
        HEADER_SIZE = 2;
        mMyBanner.setUrls(list,false,false);
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
        mMLoadingDialog = new MLoadingDialog();
        mMLoadingDialog.show(getFragmentManager(), "");
        if(categoryId == -1){
            getTag();
        }
        getData();
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
            START = 0;
            mMLoadingDialog.show(getFragmentManager(), "");
            getData();
            ChooseCityModel.getInstance().setHasChanged(false);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_publish:
                Intent intent1 = new Intent(mActivity, ChooseCityActivity.class);
                intent1.putExtra("from", RecruitActivity.IS_FROM_RECRUIT_ACTIVITY);
                startActivity(intent1);
                break;
            case R.id.tv_to_publish:
                if(CommonUtils.checkLogin(mActivity)){
                    Intent publish = new Intent(mActivity, PublishEducationInfoActivity.class);
                    startActivity(publish);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EducationDetailActivity.class);
        intent.putExtra("messageId",mAdapter.getData().get(position - HEADER_SIZE).getId().intValue());
        startActivity(intent);
    }

    /**
     * 获取tag
     */
    public void getTag() {
        VolleyOperater<SecondHandCategoryModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_EDUCATION_HOT_CATEGORY_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        showTag(new ArrayList<SecondHandCategoryBean>());
                        return;
                    }
                    SecondHandCategoryModel model = (SecondHandCategoryModel) obj;
                    if(model.getValue() != null){
                        showTag(model.getValue());
                    }
                }else{
                    showTag(new ArrayList<SecondHandCategoryBean>());
                }
            }
        }, SecondHandCategoryModel.class);
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("start", START);
        map.put("size", PAGE_SIZE);
        map.put("type",type);
        if (categoryId != -1) map.put("educationCategoryId", categoryId);
        if (ChooseCityModel.getInstance().getCityCode() != 0) map.put("cityCode", ChooseCityModel.getInstance().getCityCode());
        if (ChooseCityModel.getInstance().getProvinceId() != 0) map.put("province", ChooseCityModel.getInstance().getProvinceId());
        if (ChooseCityModel.getInstance().getCityId() != 0) map.put("city", ChooseCityModel.getInstance().getCityId());
        if (ChooseCityModel.getInstance().getDistrictId() != 0)map.put("district", ChooseCityModel.getInstance().getDistrictId());
        VolleyOperater<EducationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_EDUCATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                mListView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    EducationModel recruitListModel = (EducationModel) obj;
                    List<EducationInformation> mlist = new ArrayList<>();
                    mlist.addAll(recruitListModel.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (START != 0) {
                            if (mlist.size() < PAGE_SIZE) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<EducationInformation> mlistOrg = mAdapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                mAdapter.setData(mlistOrg);
//                                mAdapter.notifyDataSetChanged();
                            }
                        } else {
                            mAdapter.setData(mlist);
//                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(mListView, mActivity);
                        }
                    } else {
                        if (START != 0) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            mAdapter.setData(mlist);
                        }
                    }
                }
            }
        }, EducationModel.class);
    }

    private void showTag(List<SecondHandCategoryBean> list) {
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
//                        ChooseCityModel.getInstance().setType(type);
                        Intent intent = new Intent(mActivity, EducationActivity.class);
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
                        Intent allTag = new Intent(mActivity, EducationCategoryActivity.class);
                        allTag.putExtra("from", EducationActivity.IS_FROM_EDUCATION_ACTIVITY);
                        startActivity(allTag);
                    }
                });
            }
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        START = 0;
        getData();
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        START += PAGE_SIZE;
        getData();
    }
}
