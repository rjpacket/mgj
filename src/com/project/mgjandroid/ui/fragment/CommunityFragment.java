package com.project.mgjandroid.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.activity.PublishInfomationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends BaseFragment implements View.OnClickListener{

    private static CommunityFragment fragment;

    private ImageView ivPublish;
    private View mTabLine;
    private int currentIndex;
    private int screenWidth;
    private TextView tvRecommended;
    private TextView tvMy;
    private ViewPager mViewPager;
    private List<Fragment> mFragments;

    public CommunityFragment() {
        // Required empty public constructor
    }

    public static CommunityFragment getInstance(){
        if(fragment == null){
            fragment = new CommunityFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ivPublish = (ImageView) view.findViewById(R.id.community_fragment_iv_publish);
        ivPublish.setOnClickListener(this);
        mFragments = new ArrayList<>();
        mFragments.add(RecommendedCommunityFragment.newInstance());
        mFragments.add(MyCommunityFragment.newInstance());
        tvRecommended = (TextView) view.findViewById(R.id.com_tab_recommended);
        tvMy = (TextView) view.findViewById(R.id.com_tab_my);
        tvRecommended.setOnClickListener(this);
        tvMy.setOnClickListener(this);
        mTabLine = view.findViewById(R.id.tab_line);
        initTabLineWidth();
        mViewPager = (ViewPager) view.findViewById(R.id.com_view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return 2;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex * (screenWidth / 2));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 2) + currentIndex * (screenWidth / 2));

                }
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int i) {
                currentIndex = i;
                changeTabUI();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    private void changeTabUI() {
        switch (currentIndex){
            case 0:
                tvRecommended.setTextColor(getResources().getColor(R.color.orange_ff));
                tvMy.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
                break;
            case 1:
                tvMy.setTextColor(getResources().getColor(R.color.orange_ff));
                tvRecommended.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
                ((RecommendedCommunityFragment)mFragments.get(0)).hiddenPopupWindow();
                break;
        }
    }

    /**
     * 设置滑动条的宽度为屏幕的1/2(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        mActivity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = screenWidth / 2;
        mTabLine.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        //只要popup显示的，都隐藏
        ((RecommendedCommunityFragment)mFragments.get(0)).hiddenPopupWindow();

        switch (v.getId()) {
            case R.id.community_fragment_iv_publish:
                Intent publish = new Intent(mActivity, PublishInfomationActivity.class);
                startActivity(publish);
                break;

            case R.id.com_tab_recommended:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.com_tab_my:
                mViewPager.setCurrentItem(1);
                break;

            default:
                break;
        }
    }
}
