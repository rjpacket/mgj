package com.project.mgjandroid.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yuandi on 2016/3/28.
 */
public class TimeTextView extends TextView {

    private long Time;
    private boolean run = true;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(0);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (run) {
                        long mTime = Time;
                        if (mTime > 1) {
                            TimeTextView.this.setText("去支付(还剩" + getTimeString(mTime / 1000) + ")");
                            handler.sendEmptyMessageDelayed(0, 1000);
                            Time = Time - 1000;
                        }else{
                            TimeTextView.this.setText("订单已取消");
                            handler.sendEmptyMessageDelayed(0, 1000);
                            Time = Time - 1000;
                        }
//                        if(mTime < 60000){
//                            TimeTextView.this.setTextColor(0xFFF74343);
//                        }
                    } else {
                        TimeTextView.this.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };

    private String getTimeString(long time) {
        StringBuffer sb = new StringBuffer();
        if(time / 60 > 0){
            sb.append(time / 60 + "分");
        }
        sb.append(time % 60 + "秒");
        return sb.toString();
    }

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("NewApi")
    public void setTimes(long mT) {
        Time = mT;
        handler.removeMessages(0);
        handler.sendEmptyMessage(0);
    }

    public void stop() {
        run = false;
    }
}