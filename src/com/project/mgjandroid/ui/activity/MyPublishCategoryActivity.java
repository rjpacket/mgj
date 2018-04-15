package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SecondCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.education.MyPublishEducationActivity;
import com.project.mgjandroid.ui.activity.homemaking.MyPublishHomemakingActivity;
import com.project.mgjandroid.ui.activity.renthouse.MyPublishRentHouseActivity;
import com.project.mgjandroid.ui.activity.repair.MyPublishRepairActivity;
import com.project.mgjandroid.ui.adapter.PrimaryCategoryListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class MyPublishCategoryActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.my_publish_category_grid_view)
    GridView mGridView;
    private MLoadingDialog mMLoadingDialog;
    private PrimaryCategoryListAdapter mCategoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publish_category);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("我的发布");
        mMLoadingDialog = new MLoadingDialog();
        getSecondTag();
        mCategoryListAdapter = new PrimaryCategoryListAdapter(R.layout.primary_category_grid_view_item, mActivity);
        mGridView.setAdapter(mCategoryListAdapter);
        mGridView.setOnItemClickListener(this);
    }

    /**
     * 获取二手所有的tag
     */
    public void getSecondTag() {
        mMLoadingDialog.show(getFragmentManager(),"");
        VolleyOperater<SecondCategoryModel> operater = new VolleyOperater<>(MyPublishCategoryActivity.this);
        operater.doRequest(Constants.URL_USER_PUBLISH, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    SecondCategoryModel secondCategoryModel = (SecondCategoryModel) obj;
                    mCategoryListAdapter.setData(secondCategoryModel.getValue());
                }
            }
        }, SecondCategoryModel.class);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PrimaryCategory primaryCategory = mCategoryListAdapter.getData().get(position);
        Long id1 = primaryCategory.getId();
        switch (id1.intValue()){
            case 1:
                Intent intent = new Intent(this,MyPublishInfoActivity.class);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this,MyPublishRentHouseActivity.class);
                startActivity(intent2);
                break;

            case 6:
                Intent intent6 = new Intent(this,MyPublishSecondActivity.class);
                startActivity(intent6);
                break;
            case 4:
                Intent intent4 = new Intent(this,MyPublishEducationActivity.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(this,MyPublishHomemakingActivity.class);
                startActivity(intent5);
                break;
            case 3:
                Intent intent3 = new Intent(this,MyPublishRepairActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
