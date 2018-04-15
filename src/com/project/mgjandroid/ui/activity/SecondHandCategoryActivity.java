package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.SecondHandCategoryBean;
import com.project.mgjandroid.bean.SecondhandServiceCategory;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.SecondhandServiceCategoryListModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.SecondHandCategoryAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.List;

/**
 * Created by thinkpad on 2016/7/9.
 *
 */
public class SecondHandCategoryActivity extends BaseActivity{

    @InjectView(R.id.list_view)
    private ListView mTagListView;
    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;

    private SecondHandCategoryAdapter categoryAdapter;
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

        categoryAdapter = new SecondHandCategoryAdapter(R.layout.item_full_time_list_view, mActivity , this);
        mTagListView.setAdapter(categoryAdapter);

        ivBack.setOnClickListener(this);
        tvTitle.setText("选择二手商品类别");

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

                List<SecondhandServiceCategory> data = categoryAdapter.getData();
                for (SecondhandServiceCategory bean : data) {
                    List<SecondHandCategoryBean> categoryList = bean.getSecondhandCategoryList();
                    for (SecondHandCategoryBean catbean : categoryList) {
                        catbean.setCheck(false);
                    }
                }

                SecondhandServiceCategory bean = data.get(parentPos);
                SecondHandCategoryBean categoryBean = bean.getSecondhandCategoryList().get(pos);
                categoryBean.setCheck(true);
                categoryAdapter.setData(data);

                if(mFrom != null && mFrom.equals(JobMessageActivity.FROM_JOB_MESSAGE)){
                    Intent intent1 = new Intent();
                    intent1.putExtra("positionCategory",categoryBean.getId());
                    intent1.putExtra("name",categoryBean.getName());
                    setResult(JobMessageActivity.GET_JOB_TYPE_SUCCESS,intent1);
                    finish();
                }

                if(mFrom != null && mFrom.equals(SecondHandMarketActivity.IS_FROM_SECOND_HAND_MARKET_ACTIVITY)){
                    Intent intent = new Intent(mActivity,SecondHandMarketActivity.class);
                    intent.putExtra(SecondHandMarketActivity.CATEGORY_NAME, categoryBean.getName());
                    intent.putExtra(SecondHandMarketActivity.CATEGORY_ID, categoryBean.getId());
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
        VolleyOperater<SecondhandServiceCategoryListModel> operater = new VolleyOperater<>(SecondHandCategoryActivity.this);
        operater.doRequest(Constants.URL_FIND_SECOND_HAND_CATEGORY_LIST, null, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    SecondhandServiceCategoryListModel model = (SecondhandServiceCategoryListModel) obj;
                    categoryAdapter.setData(model.getValue());
                }
            }
        }, SecondhandServiceCategoryListModel.class);
    }
}