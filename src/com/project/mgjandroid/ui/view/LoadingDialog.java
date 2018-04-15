package com.project.mgjandroid.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.project.mgjandroid.R;

/**
 * Created by ning on 2016/5/7.
 */
public class LoadingDialog extends Dialog {
    private ImageView mImageView;
    private AnimationDrawable mDrawable;
    private int position = 0;
    private final static int TIME = 500;

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除默认的头部标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_dialog);
        setCanceledOnTouchOutside(false);

        mImageView = (ImageView) findViewById(R.id.show_loading_anim);

        float translationX = mImageView.getTranslationX();
        final ObjectAnimator transY1 = ObjectAnimator.ofFloat(mImageView, "translationY", translationX, -60);
        transY1.setDuration(TIME);
        transY1.setInterpolator(new DecelerateInterpolator());
//        transY1.start();
        final ObjectAnimator transY2 = ObjectAnimator.ofFloat(mImageView, "translationY", translationX - 60, translationX);
        transY2.setDuration(TIME);
        transY2.setInterpolator(new AccelerateInterpolator());
//        transY2.start();
        ImageView shadowImage = (ImageView) findViewById(R.id.shadow_anim);
        final ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(shadowImage, "scaleX", 1f, 0.5f);
        final ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(shadowImage, "scaleY", 1f, 0.5f);
        scaleX1.setDuration(TIME);
        scaleY1.setDuration(TIME);
//        animatorX.start();
        final ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(shadowImage, "scaleX", 0.5f, 1f);
        final ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(shadowImage, "scaleY", 0.5f, 1f);
        scaleX2.setDuration(TIME);
        scaleY2.setDuration(TIME);
//        animatorY.start();

        transY2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                position ++;
                changeBackground();
                transY1.start();
                scaleX1.start();
                scaleY1.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        transY1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                transY2.start();
                scaleX2.start();
                scaleY2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
//        transY1.start();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(transY1).with(scaleX1).with(scaleY1);
        animatorSet.start();

        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.5f;
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        params.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(params);
    }

    private void changeBackground() {
        position = position % 3;
        switch (position) {
            case 0:
                mImageView.setImageResource(R.drawable.loading_group_11);
                break;
            case 1:
                mImageView.setImageResource(R.drawable.loading_group_13);
                break;
            case 2:
                mImageView.setImageResource(R.drawable.loading_group_21);
                break;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
