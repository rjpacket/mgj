package com.project.mgjandroid.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.utils.DipToPx;

import java.util.ArrayList;

/**
 * Created by rjp on 2016/6/23.
 * Email:rjpgoodjob@gmail.com
 */
public class ChoosePopupWindow extends DialogFragment {
    public static final int TAB_1 = 0;
    public static final int TAB_2 = 1;
    public static final int BACK_TAB_1 = -1;
    public static final int BACK_TAB_2 = -2;

    private ViewPager mViewPager;
    private ArrayList<BaseFragment> mFragments;
    private View.OnClickListener mListener;
    private AreaClick mAreaClick;
    private ProvinceFragment mProvinceFragment;
    private CityFragment mCityFragment;
    private AreaFragment mAreaFragment;
    private RadioGroup mLlContainer;
    private MyPagerAdapter mPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_choose_city_popup_window, null);
        initChooseAreaView(view);
        return view;
    }

    private void initChooseAreaView(View view) {
        mFragments = new ArrayList<>();
        ImageView ivCancel = (ImageView) view.findViewById(R.id.choose_cancel);
        ivCancel.setOnClickListener(mListener);
        mViewPager = (ViewPager) view.findViewById(R.id.choose_city_view_pager_pop);
        mViewPager.setOffscreenPageLimit(3);
        mLlContainer = (RadioGroup) view.findViewById(R.id.area_container);
        addNewTab("请选择", 3, R.color.red);
        mLlContainer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RadioButton childAt = (RadioButton) group.getChildAt(i);
                    if(childAt.isChecked()){
                        mViewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
//        mTvToChoose = (TextView) view.findViewById(R.id.area_to_choose);
//        mTvToChoose.setSelected(true);
//        mTvToChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int childCount = mLlContainer.getChildCount();
//                for (int i = 0; i < childCount; i++) {
//                    View childAt = mLlContainer.getChildAt(i);
//                    childAt.setSelected(false);
//                }
//                mViewPager.setCurrentItem(mLlContainer.getChildCount());
//                mTvToChoose.setSelected(true);
//            }
//        });

        mProvinceFragment = ProvinceFragment.newInstance();
        mProvinceFragment.setListener(mAreaClick);
        mFragments.add(mProvinceFragment);
        mCityFragment = CityFragment.newInstance();
        mCityFragment.setListener(mAreaClick);
//        mFragments.add(mCityFragment);
        mAreaFragment = AreaFragment.newInstance();
        mAreaFragment.setListener(mAreaClick);
//        mFragments.add(mAreaFragment);

        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                RadioButton childAt = (RadioButton) mLlContainer.getChildAt(i);
                if (childAt != null) {
                    childAt.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        RadioButton childAt = (RadioButton) mLlContainer.getChildAt(0);
        childAt.setChecked(true);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    public void setListener(AreaClick listener) {
        mAreaClick = listener;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
    }

    public void setCurrentItem(int i, int pos) {

        switch (i){
            case 1:
                mFragments.clear();
                mFragments.add(mProvinceFragment);
                mFragments.add(mCityFragment);
                mPagerAdapter.notifyDataSetChanged();
                mProvinceFragment.notifyList();
                mCityFragment.notifyList();
                mAreaFragment.notifyList();

                mLlContainer.removeAllViews();
                addNewTab(ChooseCityActivity.mProvinces.get(pos).getName() , TAB_1, R.color.color_3);
                addNewTab("请选择", 3, R.color.red);
                break;

            case 2:
                mFragments.clear();
                mFragments.add(mProvinceFragment);
                mFragments.add(mCityFragment);
                mFragments.add(mAreaFragment);
                mPagerAdapter.notifyDataSetChanged();
                mAreaFragment.notifyList();
                mCityFragment.notifyList();

                int childCount = mLlContainer.getChildCount();
                if(childCount == 3){
                    mLlContainer.removeViewAt(1);
                }
                addNewTab(ChooseCityActivity.mCitys.get(pos).getName(), TAB_2, R.color.color_3);
                break;

//            case 3://直辖市 特殊城市  两级列表
//                mFragments.clear();
//                mFragments.add(mProvinceFragment);
//                mFragments.add(mAreaFragment);
//                mPagerAdapter.notifyDataSetChanged();
//                mProvinceFragment.notifyList();
//                mAreaFragment.notifyList();
//
//                mLlContainer.removeAllViews();
//                addNewTab(ChooseCityActivity.mProvinces.get(pos).getName() , TAB_1, R.color.color_3);
//                addNewTab("请选择", 3, R.color.red);
//                break;
        }
        mViewPager.setCurrentItem(i);
    }

    private void addNewTab(String s , int pos , int colorId) {
        RadioButton tvName = new RadioButton(getActivity());
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DipToPx.dip2px(getActivity(),40));
        layoutParams.leftMargin = DipToPx.dip2px(getActivity(),15);
        tvName.setLayoutParams(layoutParams);
        tvName.setGravity(Gravity.CENTER);
        tvName.setTextSize(14);
        tvName.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvName.setTextColor(getResources().getColor(colorId));
        tvName.setBackgroundResource(R.drawable.bg_choose_area_tab);
        tvName.setText(s);
        if(pos == TAB_2) {
            mLlContainer.addView(tvName , TAB_2);
        }else{
            mLlContainer.addView(tvName);
        }
    }

}
