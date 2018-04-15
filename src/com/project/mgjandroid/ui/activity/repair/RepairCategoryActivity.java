package com.project.mgjandroid.ui.activity.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.RepairCategory;
import com.project.mgjandroid.bean.RepairServiceCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RepairServiceCategoryListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.activity.BaseActivity;
import com.project.mgjandroid.ui.activity.JobMessageActivity;
import com.project.mgjandroid.ui.adapter.RepairCategoryAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.List;

/**
 * Created by yuandi on 2016/7/23.
 *
 */
public class RepairCategoryActivity extends BaseActivity {

    @InjectView(R.id.list_view)
    private ListView mTagListView;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;

    private RepairCategoryAdapter categoryAdapter;
    private MLoadingDialog mMLoadingDialog;
    private String mFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_category);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("from")){
            mFrom = intent.getStringExtra("from");
        }

        categoryAdapter = new RepairCategoryAdapter(R.layout.item_full_time_list_view, mActivity , this);
        mTagListView.setAdapter(categoryAdapter);

        ivBack.setOnClickListener(this);
        tvTitle.setText("选择维修类别");

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

                List<RepairServiceCategory> data = categoryAdapter.getData();
                for (RepairServiceCategory bean : data) {
                    List<RepairCategory> categoryList = bean.getRepairCategoryList();
                    for (RepairCategory catbean : categoryList) {
                        catbean.setCheck(false);
                    }
                }

                RepairServiceCategory bean = data.get(parentPos);
                RepairCategory categoryBean = bean.getRepairCategoryList().get(pos);
                categoryBean.setCheck(true);
                categoryAdapter.setData(data);

                if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)){
                    Intent intent1 = new Intent();
                    intent1.putExtra("positionCategory",categoryBean.getId());
                    intent1.putExtra("name",categoryBean.getName());
                    setResult(JobMessageActivity.GET_JOB_TYPE_SUCCESS,intent1);
                    finish();
                }

                if(mFrom != null && mFrom.equals(RepairActivity.IS_FROM_REPAIR_ACTIVITY)){
                    Intent intent = new Intent(mActivity,RepairActivity.class);
                    intent.putExtra(RepairActivity.CATEGORY_NAME, categoryBean.getName());
                    intent.putExtra(RepairActivity.CATEGORY_ID, categoryBean.getId());
                    startActivity(intent);
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
        VolleyOperater<RepairServiceCategoryListModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_REPAIR_MORE_CATEGORY_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    RepairServiceCategoryListModel model = (RepairServiceCategoryListModel) obj;
                    categoryAdapter.setData(model.getValue());
                }
            }
        }, RepairServiceCategoryListModel.class);
    }
}
