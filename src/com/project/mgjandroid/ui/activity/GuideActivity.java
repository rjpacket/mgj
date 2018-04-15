package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.view.MyCircleIndicator;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/5/4.
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    @InjectView(R.id.activity_guide_viewpager)
    private ViewPager viewPager;
    @InjectView(R.id.activity_guide_indicator)
    private MyCircleIndicator indicator;
    @InjectView(R.id.activity_guide_step_over)
    private ImageView tvStepOver;
    @InjectView(R.id.activity_guide_tv_to_home_page)
    private ImageView tvToHomePage;

    private int[] imgs = {R.drawable.guide_img_1, R.drawable.guide_img_2, R.drawable.guide_img_3, R.drawable.guide_img_4};
    private List<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        Injector.get(this).inject();
        initViews();
    }

    private void initViews() {
        tvStepOver.setOnClickListener(this);
        tvToHomePage.setOnClickListener(this);

        initViewPager();
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(this);
    }

    private void initViewPager() {
        ViewGroup.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        for (int img : imgs) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(img);
            viewList.add(imageView);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
    }


    private void toHomeActivity() {
        PreferenceUtils.saveVersionCode(CommonUtils.getCurrentVersionCode(), this);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_guide_step_over :

            case R.id.activity_guide_tv_to_home_page :
                toHomeActivity();
                break;

            default:
                break;

        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == viewList.size() - 1) {
            tvToHomePage.setVisibility(View.VISIBLE);
            tvToHomePage.clearAnimation();
            AnimatorUtils.alphaIn(tvToHomePage);
            tvStepOver.setVisibility(View.GONE);
        } else {
            tvToHomePage.setVisibility(View.GONE);
            tvStepOver.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class ViewPagerAdapter extends PagerAdapter {

        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(views.get(arg1));
        }

        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(views.get(arg1), 0);
            return views.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}
