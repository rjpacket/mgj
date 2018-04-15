package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.FulltimeCategoryBean;
import com.project.mgjandroid.bean.PositionCategoryBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.FulltimeListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.FulltimeJobListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FulltimeJobActivity extends BaseActivity {
    @InjectView(R.id.fulltime_job_list_view)
    private ListView mTagListView;
    @InjectView(R.id.tv_publish)
    private TextView tvPublish;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    private FulltimeJobListAdapter mJobListAdapter;
    private MLoadingDialog mMLoadingDialog;
    private String mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulltime_job);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("from")){
            mFrom = intent.getStringExtra("from");
        }

        mJobListAdapter = new FulltimeJobListAdapter(R.layout.item_full_time_list_view, mActivity , this);
        mTagListView.setAdapter(mJobListAdapter);

        ivBack.setOnClickListener(this);
        tvTitle.setText("选择职位");

        mMLoadingDialog = new MLoadingDialog();
        getAllTag();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.item_grid_label:
                String tag = (String) v.getTag();
                String[] split = tag.split(";");
                int parentPos = Integer.parseInt(split[0]);
                int pos = Integer.parseInt(split[1]);

                List<FulltimeCategoryBean> data = mJobListAdapter.getData();
                for (FulltimeCategoryBean fulltimebean : data) {
                    List<PositionCategoryBean> categoryList = fulltimebean.getPositionCategoryList();
                    for (PositionCategoryBean posbean : categoryList) {
                        posbean.setIsCheck(false);
                    }
                }

                FulltimeCategoryBean fulltimeCategoryBean = data.get(parentPos);
                PositionCategoryBean categoryBean = fulltimeCategoryBean.getPositionCategoryList().get(pos);
                categoryBean.setIsCheck(true);
                mJobListAdapter.setData(data);

                if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)){
                    Intent intent1 = new Intent();
                    intent1.putExtra("positionCategory",categoryBean.getId());
                    intent1.putExtra("name",categoryBean.getName());
                    setResult(JobMessageActivity.GET_JOB_TYPE_SUCCESS,intent1);
                    finish();
                }else if(mFrom != null && mFrom.equals(RecruitActivity.IS_FROM_RECRUIT_ACTIVITY)) {
                    Intent intent2 = new Intent(mActivity, RecruitActivity.class);
                    intent2.putExtra(RecruitActivity.CATEGORY_NAME, categoryBean.getName());
                    intent2.putExtra(RecruitActivity.CATEGORY_ID, categoryBean.getId());
                    startActivity(intent2);
                    finish();
                }
                break;
        }
    }

    /**
     * 获取所有的tag
     */
    public void getAllTag() {
        mMLoadingDialog.show(getFragmentManager(),"");
        Map<String, Object> map = new HashMap<>();
        map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        map.put("latitude",PreferenceUtils.getLocation(mActivity)[0]);
        VolleyOperater<FulltimeListModel> operater = new VolleyOperater<>(FulltimeJobActivity.this);
        operater.doRequest(Constants.URL_FULL_TIME_ALL_TAG, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    FulltimeListModel fulltimeListModel = (FulltimeListModel) obj;
                    mJobListAdapter.setData(fulltimeListModel.getValue());
                }
            }
        }, FulltimeListModel.class);
    }
}
