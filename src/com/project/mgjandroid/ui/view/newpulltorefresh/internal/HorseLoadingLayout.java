package com.project.mgjandroid.ui.view.newpulltorefresh.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;

/**
 * Created by rjp on 2016/6/2.
 * Email:rjpgoodjob@gmail.com
 */
public class HorseLoadingLayout extends LoadingLayout {


    private Animation mFirstAnim;
    private Animation mSecondAnim;
    private AnimationDrawable mHorseAnim;

    public HorseLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);

        //初始化
        mFirstImage.setImageResource(R.drawable.bg_header_view);
        mFirstAnim = AnimationUtils.loadAnimation(context, R.anim.from_0_to_100);
        mFirstImage.setAnimation(mFirstAnim);
        mSecondImage.setImageResource(R.drawable.bg_header_view);
        mSecondAnim = AnimationUtils.loadAnimation(context, R.anim.from_100_to_0);
        mSecondImage.setAnimation(mSecondAnim);
        mHorseImage.setImageResource(R.drawable.horse_anim);
    }

    public void startAnim(){
        mFirstAnim.start();
        mSecondAnim.start();
        mHorseAnim = (AnimationDrawable) mHorseImage.getDrawable();
        mHorseAnim.start();
    }

    public void stopAnim(){
        if(mFirstAnim != null) {
            mFirstAnim.cancel();
        }
        if(mSecondAnim != null) {
            mSecondAnim.cancel();
        }
        if(mHorseAnim != null) {
            mHorseAnim.stop();
        }
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.default_ptr_rotate;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        if(scaleOfLayout <= 1){
            mHorseImage.setScaleX(scaleOfLayout);
            mHorseImage.setScaleY(scaleOfLayout);
        }
    }

    @Override
    protected void pullToRefreshImpl() {
        stopAnim();
    }

    @Override
    protected void refreshingImpl() {
        startAnim();
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {
        stopAnim();
    }
}
