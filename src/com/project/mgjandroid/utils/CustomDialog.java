package com.project.mgjandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;

/**
 * Created by ning on 2016/3/26.
 */
public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {
    private onBtnClickListener onBtnClickListener;
    private Context context;

    private String sure;
    private String cancle;
    private String appTitle;
    private String tips;

    private TextView tv_dele_sure;
    private TextView tv_dele_cancle;
    private TextView tv_appTitle;
    private TextView tv_tips;

    private RelativeLayout re_tip_dialog;

    public CustomDialog(Context context,
                          onBtnClickListener onBtnClickListener, String sure, String cancle,
                          String appTitle, String tips) {
        super(context);
        this.onBtnClickListener = onBtnClickListener;
        this.context = context;
        this.appTitle = appTitle;
        this.tips = tips;
        this.sure = sure;
        this.cancle = cancle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除默认的头部标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dele_allcontacts_dialog);
        tv_dele_sure = (TextView) findViewById(R.id.sure);
        tv_dele_cancle = (TextView) findViewById(R.id.cancel);
        tv_appTitle = (TextView) findViewById(R.id.appTitle);
        tv_tips = (TextView) findViewById(R.id.tips);
        re_tip_dialog = (RelativeLayout) findViewById(R.id.re_tip_dialog);

        tv_dele_cancle.setOnClickListener(this);
        tv_dele_sure.setOnClickListener(this);

        Window window = this.getWindow();
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // 去除四角黑色背景
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置周围的暗色系数
        params.dimAmount = 0.5f;
        WindowManager manager =window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        params.width = (int) (display.getWidth()*0.8);
        window.setAttributes(params);

        // 为各个textview赋值
        tv_dele_sure.setText(sure);
        tv_dele_cancle.setText(cancle);
        tv_appTitle.setText(appTitle);
        tv_tips.setText(tips);
    }

    public interface onBtnClickListener {
        public void onSure();

        public void onExit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:

                onBtnClickListener.onExit();

//                alertDialogExitAnim();
                break;
            case R.id.sure:
                onBtnClickListener.onSure();
//                alertDialogExitAnim();
                break;

            default:
                break;
        }
    }

    private void alertDialogExitAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f,
                1.0f, 0.0f, Animation.ABSOLUTE,
                re_tip_dialog.getWidth() / 2, Animation.ABSOLUTE,
                re_tip_dialog.getHeight() / 2);
        scaleAnimation.setDuration(1000);
        re_tip_dialog.startAnimation(scaleAnimation);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                CustomDialog.this.dismiss();
            }
        });
    }

}
