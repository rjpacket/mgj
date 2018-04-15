package com.project.mgjandroid.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.net.MyWebViewClient;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by User_Cjh on 2016/4/9.
 */
public class UserServiceActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(R.id.user_service_act_back)
    private ImageView ivBack;
    @InjectView(R.id.user_service_webview)
    private WebView webview;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_userservice_protocol);
        Injector.get(this).inject();

        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
//        webview.getSettings().setUseWideViewPort(true);
//        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webview.loadUrl("file:///android_asset/agreementText.html");
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_service_act_back:
                finish();
                break;
        }
    }
}
