package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.ui.adapter.HomeRestaurantAdapter;
import com.project.mgjandroid.ui.adapter.NoticeListAdapter;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;
import com.ta.utdid2.android.utils.NetworkUtils;

/**
 * Created by User_Cjh on 2016/3/26.
 */
public class NoticeActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.notice_act_back)
    private ImageView noticeActBack;
    @InjectView(R.id.notice_act_list_view)
    private PullToRefreshListView noticeActListView;
    @InjectView(R.id.notice_act_empty_view)
    private RelativeLayout noticeActEmptyView;
    @InjectView(R.id.notice_act_no_net)
    private LinearLayout noticeActNoNet;

    private TextView noticeActReload;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_notice);
        Injector.get(this).inject();
        initView();

        setListener();
    }

    private void initView() {
        noticeActReload= (TextView) noticeActNoNet.findViewById(R.id.notice_act_reload);
        noticeActListView.setMode(PullToRefreshBase.Mode.BOTH);
//        noticeActListView.setAddMoreCountText(20);
        noticeActListView.setAdapter(new NoticeListAdapter(NoticeActivity.this));
        noticeActListView.getRefreshableView().setEmptyView(noticeActEmptyView);
        if(!NetworkUtils.isConnected(NoticeActivity.this)){
            noticeActNoNet.setVisibility(View.VISIBLE);
        }
    }

    private void setListener() {
        noticeActBack.setOnClickListener(this);
        noticeActReload.setOnClickListener(this);
        noticeActListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(NoticeActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                /*
                此处模拟刷新 以后用请求替代
                 */
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        noticeActListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView readMark= (ImageView) view.findViewById(R.id.notice_item_read_mark);
                readMark.setVisibility(View.GONE);
            }
        });
    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int num=msg.what;
            switch (num) {
                case 0:
                    noticeActListView.onRefreshComplete();
                    break;
                case NOTICE_RELOAD:

                    break;
            }
            if(noticeActListView.isRefreshing()){
                noticeActListView.onRefreshComplete();
            }
        }
    };

    private static final int NOTICE_RELOAD=101;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notice_act_back:
                finish();
                break;
            case R.id.notice_act_reload:
                if(!NetworkUtils.isConnected(NoticeActivity.this)) {
                    return;
                }
                noticeActNoNet.setVisibility(View.GONE);
                handler.sendEmptyMessage(NOTICE_RELOAD);
                break;
        }
    }
}
