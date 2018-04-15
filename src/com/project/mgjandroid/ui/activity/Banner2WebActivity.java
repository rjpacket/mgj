package com.project.mgjandroid.ui.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.net.MyWebViewClient;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

/**
 * Created by yuandi on 2016/3/24.
 */
public class Banner2WebActivity extends BaseActivity{
    public final static String URL = "url";
    @InjectView(R.id.web_view_act_back)
    private ImageView ivBack;
    @InjectView(R.id.web_view_act_tv_title)
    private TextView tvTitle;
    @InjectView(R.id.web_view_act_progress)
    private ProgressBar progressBar;
    @InjectView(R.id.web_view_act_web_view)
    private WebView webview;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_web_view);
        Injector.get(this).inject();
        String url = getIntent().getExtras().getString(URL);
        initView();
        webview.loadUrl(url);
    }


    private void initView(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

//        webview.getSettings().setDatabaseEnabled(true);
//        String dir = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        webview.getSettings().setGeolocationDatabasePath(dir);
        webview.getSettings().setGeolocationEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);


        webview.setWebViewClient(new MyWebViewClient(progressBar, mActivity) {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                tvTitle.setText(view.getTitle());
                tvTitle.setText(getIntent().getExtras().getString("name"));
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

    }

    @Override
    public void onDestroy() {
        if (webview != null) {
            try {
                webview.removeAllViews();
                webview.clearCache(true);
                webview.clearHistory();
                webview.destroy();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
