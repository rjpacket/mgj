package com.project.mgjandroid.ui.activity.education;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationInformation;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.EducationDetailModel;
import com.project.mgjandroid.model.MyPublishEducationModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.adapter.MyEducationPublishListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPublishEducationActivity extends BaseActivity implements PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener, CustomDialog.onBtnClickListener {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.tv_type_1)
    private TextView tvType1;
    @InjectView(R.id.tv_type_2)
    private TextView tvType2;
    @InjectView(R.id.line_1)
    private View line1;
    @InjectView(R.id.line_2)
    private View line2;
    public MLoadingDialog mMLoadingDialog;

    @InjectView(R.id.my_publish_second_listview)
    private PullToRefreshListView listView;
    private int type = 1;
    private static final int maxResults = 10;
    private int start = 0;
    private MyEducationPublishListAdapter adapter;
    private CustomDialog stickyDialog;
    private CustomDialog mCustomDialog;
    private int mDeleteTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_education);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        tvTitle.setText("我的发布");
        ivBack.setOnClickListener(this);
        tvType1.setOnClickListener(this);
        tvType2.setOnClickListener(this);

        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        adapter = new MyEducationPublishListAdapter(R.layout.item_my_publish_education_list_view, mActivity , this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        View emptyView = mInflater.inflate(R.layout.empty_view_publish, null);
        TextView textView = (TextView) emptyView.findViewById(R.id.tv_no_data);
        textView.setText("您还没有发布");
        listView.setEmptyView(emptyView);

        mMLoadingDialog = new MLoadingDialog();
        start = 0;
        getData(false);
        mCustomDialog = new CustomDialog(mActivity, this, "确定", "取消", "提示", "确定删除吗？");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
//            case R.id.tv_publish:
//                Intent intent = new Intent(this, JobMessageActivity.class);
//                startActivity(intent);
//                break;
            case R.id.tv_type_1:
                if (type == 2) return;
                start = 0;
                type = 2;
                line2.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                tvType1.setTextColor(getResources().getColor(R.color.title_bar_bg));
                line1.setBackgroundColor(getResources().getColor(R.color.white));
                tvType2.setTextColor(getResources().getColor(R.color.color_6));
                getData(false);
                break;
            case R.id.tv_type_2:
                if (type == 1) return;
                start = 0;
                type = 1;
                line2.setBackgroundColor(getResources().getColor(R.color.white));
                tvType1.setTextColor(getResources().getColor(R.color.color_6));
                line1.setBackgroundColor(getResources().getColor(R.color.title_bar_bg));
                tvType2.setTextColor(getResources().getColor(R.color.title_bar_bg));
                getData(false);
                break;

            case R.id.tv_refresh:
                adapter.hidePopup();
                int tag = (Integer) v.getTag();
                refreshSecondInfo((long) adapter.getData().get(tag).getId(),tag);
                break;
            case R.id.tv_sticky:
                adapter.hidePopup();
                showStickyDialog();
                break;
            case R.id.tv_delete:
                adapter.hidePopup();
                mDeleteTag = (Integer) v.getTag();
                mCustomDialog.show();
                break;
            case R.id.item_cover:
                int position = (int) v.getTag();
                Intent intent = new Intent(this, EducationDetailActivity.class);
                intent.putExtra("messageId",adapter.getData().get(position).getId().intValue());
                startActivity(intent);
                break;
            case R.id.item_state_delete:
                mDeleteTag = (Integer) v.getTag();
                mCustomDialog.show();
                break;
        }
    }

    private void showStickyDialog() {
        if (stickyDialog != null) {
            stickyDialog.show();
            return;
        }
        stickyDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
            @Override
            public void onSure() {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse(String.format(mActivity.getString(R.string.withdraw_phone), mActivity.getString(R.string.sale_phone))));
                mActivity.startActivity(intent);
                stickyDialog.dismiss();
            }
            @Override
            public void onExit() {
                stickyDialog.dismiss();
            }
        }, "呼叫", "取消", "申请排序请拨打客服电话", mActivity.getString(R.string.sale_phone));

        stickyDialog.show();
    }

    private void refreshSecondInfo(Long id, final int position) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<EducationDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_REFRESH_EDUCATION_MESSAGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    if(((EducationDetailModel) obj).getValue() != null){
                        List<EducationInformation> mDatas = adapter.getData();
                        mDatas.remove(position);
                        mDatas.add(0, ((EducationDetailModel) obj).getValue());
                        adapter.setData(mDatas);
                    }
                    ToastUtils.displayMsg("刷新成功", mActivity);
                }
            }
        }, EducationDetailModel.class);
    }

    private void deleteSecondInfo(Long id, final int position) {
        mMLoadingDialog.show(mActivity.getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        VolleyOperater<EducationDetailModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DELETE_EDUCATION_MESSAGE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    List<EducationInformation> mDatas = adapter.getData();
                    mDatas.remove(position);
                    adapter.setData(mDatas);
                    ToastUtils.displayMsg(R.string.delete_success, mActivity);
                }
            }
        }, EducationDetailModel.class);
    }

    private void getData(final boolean isLoadMore) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", start);
        map.put("size", maxResults);
        map.put("type", type);
        VolleyOperater<MyPublishEducationModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_USER_EDUCATION_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    if (obj instanceof String) {
                        return;
                    }
                    MyPublishEducationModel recruitListModel = (MyPublishEducationModel) obj;
                    List<EducationInformation> mlist = new ArrayList<>();
                    mlist.addAll(recruitListModel.getValue());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            List<EducationInformation> mlistOrg = adapter.getData();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setData(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setData(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        } else {
                            adapter.setData(mlist);
                        }
                    }
                }
            }
        }, MyPublishEducationModel.class);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        start = 0;
        getData(false);
    }

    @Override
    public void onPullDownValue(PullToRefreshBase refreshView, int value) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        start = adapter.getDataCount();
        getData(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EducationDetailActivity.class);
        intent.putExtra("messageId",adapter.getData().get(position - 1).getId().intValue());
        startActivity(intent);
    }

    @Override
    public void onSure() {
        deleteSecondInfo((long) adapter.getData().get(mDeleteTag).getId(), mDeleteTag);
        mCustomDialog.dismiss();
    }

    @Override
    public void onExit() {
        mCustomDialog.dismiss();
    }
}
