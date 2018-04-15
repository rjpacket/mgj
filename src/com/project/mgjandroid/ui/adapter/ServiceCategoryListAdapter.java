package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.mzule.activityrouter.router.Routers;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.PrimaryCategory;
import com.project.mgjandroid.constants.ActivitySchemeManager;
import com.project.mgjandroid.model.MorePrimaryCategoryModel.ServiceCategory;
import com.project.mgjandroid.ui.activity.Banner2WebActivity;
import com.project.mgjandroid.ui.activity.PrimaryCategoryActivity;
import com.project.mgjandroid.ui.view.NoScrollGridView;
import com.project.mgjandroid.utils.ToastUtils;

/**
 * Created by yuandi on 2016/6/22.
 *
 */
public class ServiceCategoryListAdapter extends BaseListAdapter<ServiceCategory>{

    public ServiceCategoryListAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, ServiceCategory bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.service_primary_name);
        tvName.setText(bean.getName());
        TextView tvDesc = holder.getView(R.id.service_primary_description);
        tvDesc.setText(bean.getDescription());

        final NoScrollGridView noScrollGridView = holder.getView(R.id.grid_view);
        PrimaryCategoryListAdapter tagGridAdapter = new PrimaryCategoryListAdapter(R.layout.primary_category_grid_view_item, mActivity);
        tagGridAdapter.setData(bean.getPrimaryCategoryList());
        noScrollGridView.setAdapter(tagGridAdapter);
        noScrollGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position > ((PrimaryCategoryListAdapter) parent.getAdapter()).getDataCount() - 1) return;
                PrimaryCategory primaryCategory = (PrimaryCategory) noScrollGridView.getAdapter().getItem(position);
                if (primaryCategory.getGraySwitch() == 0) {
                    if(primaryCategory.getGotoType() == 2){
                        Intent intent = new Intent(mActivity, PrimaryCategoryActivity.class);
                        intent.putExtra("tagCategoryId", primaryCategory.getTagCategoryId());
                        intent.putExtra("tagCategoryType", primaryCategory.getTagCategoryType());
                        intent.putExtra("categoryName", primaryCategory.getName());
                        mActivity.startActivity(intent);
                    }else if(primaryCategory.getGotoType() == 1){
                        if(primaryCategory.getGotoUrl().startsWith("maguanjia://")){
                            Routers.open(mActivity, ActivitySchemeManager.SCHEME + primaryCategory.getGotoUrl().replace("maguanjia://", ""));
                        }else if(primaryCategory.getGotoUrl().startsWith("http")){
                            Intent intent = new Intent(mActivity, Banner2WebActivity.class);
                            intent.putExtra(Banner2WebActivity.URL, primaryCategory.getGotoUrl());
                            mActivity.startActivity(intent);
                        }
                    }
                } else {
                    ToastUtils.displayMsg("尚未开通，敬请期待", mActivity);
                }
            }
        });
    }
}
