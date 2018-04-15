package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mzule.activityrouter.annotation.Router;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MorePrimaryCategoryModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.ServiceCategoryListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/6/21.
 *
 */
@Router("moreCategory")
public class MorePrimaryCategoryActivity extends BaseActivity{

    @InjectView(R.id.common_back)
    private ImageView ivBack;
    @InjectView(R.id.common_title)
    private TextView tvTitle;
    @InjectView(R.id.list_view)
    private ListView listView;

    private ServiceCategoryListAdapter adapter;
    private MLoadingDialog mMLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_primary_category);
        Injector.get(this).inject();

        initView();
        getData();
    }

    private void initView() {
        ivBack.setOnClickListener(this);
        tvTitle.setText("更多");
        adapter = new ServiceCategoryListAdapter(R.layout.more_primary_category_item, mActivity);
        listView.setAdapter(adapter);
        View view = new View(this);
        ViewGroup.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.x15));
        view.setLayoutParams(layoutParams);
        listView.addFooterView(view);
        mMLoadingDialog = new MLoadingDialog();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    public void getData() {
        mMLoadingDialog.show(getFragmentManager(), "");
        VolleyOperater<MorePrimaryCategoryModel> operater = new VolleyOperater<>(mActivity);
        Map<String, Object> map = new HashMap<>();
        if(mActivity!=null&& PreferenceUtils.getLocation(mActivity)[0] != null && PreferenceUtils.getLocation(mActivity)[1] != null) {
            map.put("latitude", PreferenceUtils.getLocation(mActivity)[0]);
            map.put("longitude", PreferenceUtils.getLocation(mActivity)[1]);
        }else{
            map.put("latitude", "");
            map.put("longitude", "");
        }
        operater.doRequest(Constants.URL_FIND_MORE_PRIMARY_CATEGORY_LIST, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMLoadingDialog.dismiss();
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        toast("获取数据失败");
                        return;
                    }
                    MorePrimaryCategoryModel categoryModel = (MorePrimaryCategoryModel) obj;
                    if (CheckUtils.isNoEmptyList(categoryModel.getValue())) {
                        adapter.setData(categoryModel.getValue());
                    }
                }
            }
        }, MorePrimaryCategoryModel.class);
    }
}
