package com.project.mgjandroid.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.project.mgjandroid.R;

/**
 * Created by ning on 2016/5/24.
 */
public class CommonDialog extends Dialog {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private LinearLayout mContainer;
    private View mContentView;
    private View.OnClickListener mListener;

    public CommonDialog(Context context,View contentView, View.OnClickListener listener){
        super(context);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mContentView = contentView;
        mListener = listener;
    }

    public CommonDialog(Context context, View contentView){
        this(context,contentView,null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除默认的头部标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_list_dialog);
        mContainer = (LinearLayout) findViewById(R.id.common_container);
        mContainer.addView(mContentView);

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

    public void setListener(View.OnClickListener listener) {
        mListener = listener;
    }
}
