package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.EducationTeacherTypeOrTutorshipStage;

/**
 * Created by yuandi on 2016/7/22.
 *
 */
public class EduChooseAdapter extends BaseListAdapter<EducationTeacherTypeOrTutorshipStage> {

    public EduChooseAdapter(int layoutId, Activity mActivity) {
        super(layoutId, mActivity);
    }

    @Override
    protected void getRealView(ViewHolder holder, EducationTeacherTypeOrTutorshipStage bean, int position, View convertView, ViewGroup parent) {
        TextView tvName = holder.getView(R.id.text_view);
        tvName.setText(bean.getName());
    }
}

