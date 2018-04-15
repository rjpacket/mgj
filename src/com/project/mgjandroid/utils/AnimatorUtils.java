package com.project.mgjandroid.utils;

import com.project.mgjandroid.R;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;

/**
 * 动画工具类
 * 
 * @author jian
 * 
 */
public class AnimatorUtils {
	/**
	 * 向左旋转平移动画
	 * 
	 * @param view
	 * @param translationX
	 */
	public static void leftTranslationRotating(View view, float translationX) {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", 0f, translationX);
		ObjectAnimator animatorR = ObjectAnimator.ofFloat(view, "rotation", 0f, -360f);
		animatorX.setDuration(300);
		animatorX.setRepeatCount(0);
		animatorR.setDuration(300);
		animatorR.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorX).with(animatorR);
		animSet.start();
	}

	/**
	 * 向右旋转平移动化
	 * 
	 * @param view
	 * @param translationX
	 */
	public static void rightTranslationRotating(View view, float translationX) {
		ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", translationX, 0f);
		ObjectAnimator animatorR = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
		animatorX.setDuration(300);
		animatorX.setRepeatCount(0);
		animatorR.setDuration(300);
		animatorR.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorX).with(animatorR);
		animSet.start();
	}

	public static void scaleIn(View view) {
		ObjectAnimator animatorSX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1.0f);
		animatorSX.setDuration(150);
		animatorSX.setRepeatCount(0);
		ObjectAnimator animatorSY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1.0f);
		animatorSY.setDuration(150);
		animatorSY.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorSX).with(animatorSY);
		animSet.start();
	}

	public static void scaleOut(final View view) {
		ObjectAnimator animatorSX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.5f);
		animatorSX.setDuration(150);
		animatorSX.setRepeatCount(0);
		ObjectAnimator animatorSY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.5f);
		animatorSY.setDuration(150);
		animatorSY.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorSX).with(animatorSY);
		animSet.start();
		animSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				view.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				view.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
	}

	/**
	 * 旋转180度
	 *
	 * @param view
	 */
	public static void rotating(View view, float start, float end) {
		ObjectAnimator animatorR = ObjectAnimator.ofFloat(view, "rotation", start, end);
		animatorR.setDuration(300);
		animatorR.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animatorR);
		animSet.start();
	}

	/**
	 * 淡入
	 * 
	 * @param view
	 * @param context
	 */
	public static void fadeFadeIn(View view, Context context) {
		ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.fade_fade_in);
		animator.setTarget(view);
		animator.start();
	}

	public static void alphaIn(View view, Context context) {
		ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.alpha);
		animator.setTarget(view);
		animator.start();
	}

	/**
	 * 隐藏底部状态栏
	 * 
	 * @param view
	 * @param context
	 */
	public static void hideBottom(View view, Context context) {
		ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.hide_bottom);
		animator.setTarget(view);
		animator.start();
	}

	/**
	 * 显示底部状态栏
	 * 
	 * @param view
	 * @param context
	 */
	public static void showBottom(View view, Context context) {
		ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, R.animator.show_bottom);
		animator.setTarget(view);
		animator.start();
	}

	public static void alphaIn(View view) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1.0f);
		animator.setDuration(1000);
		animator.setRepeatCount(0);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(animator);
		animSet.start();
	}

}
