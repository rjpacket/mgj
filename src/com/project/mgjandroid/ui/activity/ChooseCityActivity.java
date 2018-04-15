package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.AreaBean;
import com.project.mgjandroid.bean.CityBean;
import com.project.mgjandroid.bean.HotCityBean;
import com.project.mgjandroid.bean.ProvinceBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.model.ChooseCityModel;
import com.project.mgjandroid.model.HotCityModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.HotCityGridAdapter;
import com.project.mgjandroid.ui.fragment.ChoosePopupWindow;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseCityActivity extends BaseActivity implements AreaClick {
    public static final String HOT_CITY = "hot_city";
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String AREA = "area";
    public static final String CITY_CODE = "cityCode";
    public static final String CITY_NAME = "cityName";

    @InjectView(R.id.common_back) private ImageView ivBack;
    @InjectView(R.id.common_title) private TextView tvTitle;
    @InjectView(R.id.choose_city_label) private RelativeLayout rlChooseLabel;
    @InjectView(R.id.hot_city_grid_view) private GridView gvHotCity;
    @InjectView(R.id.city_location) private TextView tvLocationCity;
    @InjectView(R.id.history_tip) private TextView tvHistoryTip;

    @InjectView(R.id.history_city_grid_view) private NoScrollGridView gvHistoryCity;

    public static List<ProvinceBean> mProvinces;
    public static List<CityBean> mCitys = new ArrayList<>();
    public static List<AreaBean> mAreas = new ArrayList<>();
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private ChoosePopupWindow mPopupWindow;
    private ProvinceBean mSelectedProvince;
    private CityBean mSelectedCity;
    private AreaBean mSelectedArea;
    private String mFrom;
    private HotCityGridAdapter mHotCityGridAdapter;
    private HotCityGridAdapter mHistoryCityAdapter;
    private List<HotCityBean> mHistoryBean;
    private String mCitys1;
    //标记  1代表是直辖市，特殊处理
    private int mType = -1;
    private int mTypeRecruit = -1;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("from")){
            mFrom = intent.getStringExtra("from");
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText("选择城市");

        rlChooseLabel.setOnClickListener(this);
        mCitys1 = CommonUtils.readFileFromAssets(mActivity, "city.json");
        parseCityJson(mCitys1);

        mHotCityGridAdapter = new HotCityGridAdapter(R.layout.hot_city_item, mActivity);
        gvHotCity.setAdapter(mHotCityGridAdapter);
        gvHotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotCityBean cityBean = mHotCityGridAdapter.getData().get(position);
//                HotCityBean hotCityBean = new HotCityBean();
//                hotCityBean.setProvince(cityBean.getProvince());
//                hotCityBean.setCity(cityBean.getCity());
//                hotCityBean.setDistrict(cityBean.getDistrict());
//                hotCityBean.setCityName(cityBean.getCityName());
                onGridViewClick(cityBean);
            }
        });

        mHistoryCityAdapter = new HotCityGridAdapter(R.layout.hot_city_item, mActivity);
        gvHistoryCity.setAdapter(mHistoryCityAdapter);
        gvHistoryCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotCityBean cityBean = mHistoryCityAdapter.getData().get(position);
                onGridViewClick(cityBean);
            }
        });

        final String addressCity = PreferenceUtils.getAddressCity(mActivity);
        final String addressCityCode = PreferenceUtils.getAddressCityCode(mActivity);
        if(!TextUtils.isEmpty(addressCity) && !TextUtils.isEmpty(addressCityCode)){
            tvLocationCity.setText(addressCity);
            tvLocationCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HotCityBean hotCityBean = new HotCityBean();
                    hotCityBean.setCityCode(addressCityCode);
                    hotCityBean.setCityName(addressCity);
                    if (mHistoryBean.contains(hotCityBean)) {
                        mHistoryBean.remove(hotCityBean);
                    }
                    mHistoryBean.add(0, hotCityBean);

                    if (mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)) {
                        Intent intent = new Intent();
                        intent.putExtra(CITY_NAME, addressCity);
                        intent.putExtra(CITY_CODE, addressCityCode);
                        setResult(JobMessageActivity.GET_CITY_CODE_SUCCESS, intent);
                    } else if (mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY)) {
                        try {
                            long cityCode = Long.parseLong(addressCityCode);
                            ChooseCityModel.getInstance().setCityCode(cityCode);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        ChooseCityModel.getInstance().setCityName(addressCity);
                        ChooseCityModel.getInstance().setHasChanged(true);
                    }
                    finish();
                }
            });
        }

        mFragments = new ArrayList<>();
        initPopupWindow();

        initHistoryLabel();

        getHotCity();
    }

    private void onGridViewClick(HotCityBean cityBean) {
        if (mHistoryBean.contains(cityBean)) {
            mHistoryBean.remove(cityBean);
        }
        mHistoryBean.add(0, cityBean);

        if (mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)) {
            Intent intent = new Intent();
            intent.putExtra(HOT_CITY, cityBean);
            setResult(JobMessageActivity.GET_HOT_SUCCESS, intent);
        } else if (mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY)) {
            ChooseCityModel.getInstance().setProvinceId(cityBean.getProvince());
            ChooseCityModel.getInstance().setCityId(cityBean.getCity());
            ChooseCityModel.getInstance().setDistrictId(cityBean.getDistrict());
            ChooseCityModel.getInstance().setCityName(cityBean.getCityName());
            ChooseCityModel.getInstance().setHasChanged(true);
        }
        finish();
    }

    private void initHistoryLabel() {
        mHistoryBean = getHistoryBean();
        if(mHistoryBean != null && mHistoryBean.size() > 0){
            mHistoryCityAdapter.setData(mHistoryBean);
        }else{
            mHistoryBean = new ArrayList<>();
            gvHistoryCity.setVisibility(View.GONE);
            tvHistoryTip.setVisibility(View.GONE);
        }
    }

    private void saveHistoryBean(List<HotCityBean> hotCityBeans) {
        Gson gson = new Gson();
        String json = gson.toJson(hotCityBeans, new TypeToken<List<HotCityBean>>() {
        }.getType());
        PreferenceUtils.saveStringPreference("historyCity", json , mActivity);
    }

    private List<HotCityBean> getHistoryBean(){
        String historyCity = PreferenceUtils.getStringPreference("historyCity", "", mActivity);
        Gson gson = new Gson();
        List<HotCityBean> o = gson.fromJson(historyCity, new TypeToken<List<HotCityBean>>() {
        }.getType());
        return o;
    }

    private void parseCityJson(String citys) {
        Gson gson = new Gson();
        mProvinces = gson.fromJson(citys, new TypeToken<List<ProvinceBean>>(){}.getType());
    }

    private void initPopupWindow() {
        mPopupWindow = new ChoosePopupWindow();
        mPopupWindow.setListener(this);
        mPopupWindow.setOnClickListener(this);
    }



    private void setAlpha(float v) {
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = v;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    public void hiddenPopupWindow(){
        if(mPopupWindow != null && !mPopupWindow.isHidden()){
            mPopupWindow.dismiss();
            setAlpha(1.0f);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.choose_city_label:
                mPopupWindow.show(getSupportFragmentManager(), "");
                setAlpha(0.5f);
                break;

            case R.id.choose_cancel:
                hiddenPopupWindow();
                break;
        }
    }

    @Override
    public void onAreaClick(int currentItem , int pos) {

        switch (currentItem){
            case 0:
                parseCityJson(mCitys1);
                mSelectedProvince = mProvinces.get(pos);
                mCitys = mSelectedProvince.getChildCityList();
                if(mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY)){
                    if(mCitys.size() == 1){
                        mType = 1;
                        CityBean cityBean1 = new CityBean();
                        cityBean1.setName(mSelectedProvince.getName() + "(全部)");
                        if(!mCitys.get(0).getName().equals(cityBean1.getName())) {
                            mCitys.add(0, cityBean1);
                        }

                        mSelectedCity = mSelectedProvince.getChildCityList().get(1);
                        mAreas = mSelectedCity.getChildCityList();
                        for (AreaBean area : mAreas) {
                            CityBean cityBean = new CityBean();
                            cityBean.setId(area.getParentId());
                            cityBean.setName(area.getName());
                            cityBean.setChildCityList(null);
                            cityBean.setParentId(area.getId());
                            mCitys.add(cityBean);
                        }

                        mCitys.remove(1);
                    }else{
                        mType = -1;
                        CityBean cityBean = new CityBean();
                        cityBean.setName(mSelectedProvince.getName() + "(全部)");
                        if(!mCitys.get(0).getName().equals(cityBean.getName())) {
                            mCitys.add(0, cityBean);
                        }
                    }
                }else if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)){
                    if(mCitys.size() == 1){
                        mType = 1;
                        CityBean cityBean1 = new CityBean();
                        cityBean1.setName(mSelectedProvince.getName() + "(全部)");
                        if(!mCitys.get(0).getName().equals(cityBean1.getName())) {
                            mCitys.add(0, cityBean1);
                        }

                        mSelectedCity = mSelectedProvince.getChildCityList().get(1);
                        mAreas = mSelectedCity.getChildCityList();
                        for (AreaBean area : mAreas) {
                            CityBean cityBean = new CityBean();
                            cityBean.setId(area.getParentId());
                            cityBean.setName(area.getName());
                            cityBean.setChildCityList(null);
                            cityBean.setParentId(area.getId());
                            mCitys.add(cityBean);
                        }

                        mCitys.remove(1);
                    }else{
                        mType = -1;
                    }
                }

                mPopupWindow.setCurrentItem(currentItem + 1, pos);
                break;
            case 1:
                if (mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY) && pos == 0) {
                    //省级热门城市不保存
                    if(mType == 1) {
                        HotCityBean hotCityBean = new HotCityBean();
                        hotCityBean.setProvince(mSelectedProvince.getId());
                        hotCityBean.setCity(mSelectedCity.getId());
                        hotCityBean.setCityName(mSelectedProvince.getName());
                        if (mHistoryBean.contains(hotCityBean)) {
                            mHistoryBean.remove(hotCityBean);
                        }
                        mHistoryBean.add(0, hotCityBean);
                    }

                    ChooseCityModel.getInstance().setProvinceId(mSelectedProvince.getId());
                    ChooseCityModel.getInstance().setCityName(mSelectedProvince.getName());
                    ChooseCityModel.getInstance().setHasChanged(true);
                    finish();
                    return;
                }else if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE) && mType == 1 && pos == 0){
                    HotCityBean hotCityBean = new HotCityBean();
                    hotCityBean.setProvince(mSelectedProvince.getId());
                    hotCityBean.setCity(mSelectedCity.getId());
                    hotCityBean.setCityName(mSelectedCity.getName());
                    if (mHistoryBean.contains(hotCityBean)) {
                        mHistoryBean.remove(hotCityBean);
                    }
                    mHistoryBean.add(0, hotCityBean);


                    Intent intent = new Intent();
                    intent.putExtra(PROVINCE,mSelectedProvince);
                    intent.putExtra(CITY,mSelectedCity);
                    setResult(JobMessageActivity.GET_JOB_AREA_SUCCESS,intent);
                    finish();
                    return ;
                }

                if(!CheckUtils.isEmptyList(mCitys.get(1).getChildCityList())) {
                    mSelectedCity = mCitys.get(pos);
                    mAreas = mSelectedCity.getChildCityList();
                    AreaBean areaBean = new AreaBean();
                    areaBean.setName(mSelectedCity.getName() + "(全部)");
                    if (!mAreas.get(0).getName().equals(areaBean.getName())) {
                        mAreas.add(0, areaBean);
                    }
                    mPopupWindow.setCurrentItem(currentItem + 1, pos);
                }else{
                    clickArea(pos - 1 , true);
                }
                break;
            case 2:
                clickArea(pos , false);
                break;
        }
    }

    private void clickArea(int pos, boolean clickArea) {
        mSelectedArea = mAreas.get(pos);
        HotCityBean hotCityBean = new HotCityBean();
        hotCityBean.setProvince(mSelectedProvince.getId());
        hotCityBean.setCity(mSelectedCity.getId());
        hotCityBean.setCityName(mSelectedCity.getName());
        if(pos != 0){
            hotCityBean.setDistrict(mSelectedArea.getId());
            hotCityBean.setCityName(mSelectedArea.getName());
        }
        if(clickArea){
            hotCityBean.setDistrict(mSelectedArea.getId());
            hotCityBean.setCityName(mSelectedArea.getName());
        }
        if (mHistoryBean.contains(hotCityBean)) {
            mHistoryBean.remove(hotCityBean);
        }
        mHistoryBean.add(0, hotCityBean);

        if(mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY)){
            ChooseCityModel.getInstance().setProvinceId(mSelectedProvince.getId());
            ChooseCityModel.getInstance().setCityId(mSelectedCity.getId());
            ChooseCityModel.getInstance().setCityName(mSelectedCity.getName());
            ChooseCityModel.getInstance().setHasChanged(true);
            if (pos != 0) {
                ChooseCityModel.getInstance().setDistrictId(mAreas.get(pos).getId());
                ChooseCityModel.getInstance().setCityName(mAreas.get(pos).getName());
            }
            if(clickArea){
                ChooseCityModel.getInstance().setDistrictId(mAreas.get(pos).getId());
                ChooseCityModel.getInstance().setCityName(mAreas.get(pos).getName());
            }
            finish();
            return;
        }


        Intent intent = new Intent();
        intent.putExtra(PROVINCE,mSelectedProvince);
        intent.putExtra(CITY,mSelectedCity);
        if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE) && pos != 0){
            intent.putExtra(AREA,mSelectedArea);
        }
        if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE) && clickArea && pos == 0){
            intent.putExtra(AREA,mSelectedArea);
        }
        setResult(JobMessageActivity.GET_JOB_AREA_SUCCESS,intent);
        finish();
    }

    /**
     * 获取热门城市
     */
    private void getHotCity() {
        Map<String, Object> map = new HashMap<>();
        VolleyOperater<HotCityModel> operater = new VolleyOperater<>(this);
        operater.doRequest(Constants.URL_HOT_CITY, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    HotCityModel hotCityModel = (HotCityModel) obj;
                    mHotCityGridAdapter.setData(hotCityModel.getValue());
                }
            }
        }, HotCityModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mHistoryBean != null && mHistoryBean.size() > 0){
            if(mHistoryBean.size() <= 3){
                saveHistoryBean(mHistoryBean);
            }else{
                saveHistoryBean(mHistoryBean.subList(0, 3));
            }
        }
    }
}
