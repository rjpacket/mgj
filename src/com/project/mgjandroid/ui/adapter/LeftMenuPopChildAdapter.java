package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CategoryRightBean;

import java.util.List;

/**
 * Created by User_Cjh on 2016/3/24.
 */
public class LeftMenuPopChildAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryRightBean> list;
    private View.OnClickListener listener;

    public LeftMenuPopChildAdapter(Context context, List<CategoryRightBean> list,View.OnClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_left_menu_child, null);
            holder.leftMenuChildName = (TextView) view.findViewById(R.id.left_menu_child_name);
            holder.rlChild = (RelativeLayout) view.findViewById(R.id.child_category);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.rlChild.setTag(i);
        holder.rlChild.setOnClickListener(listener);
        holder.leftMenuChildName.setText(list.get(i).getName());
        if(list.get(i).isChecked()){
            holder.leftMenuChildName.setTextColor(context.getResources().getColor(R.color.left_menu_group_checked));
        }else{
            holder.leftMenuChildName.setTextColor(context.getResources().getColor(R.color.left_menu_group_unchecked));
        }
        return view;
    }

    public void setList(List<CategoryRightBean> leftPopMenuChild) {
        this.list = leftPopMenuChild;
        notifyDataSetChanged();
    }

    public List<CategoryRightBean> getList() {
        return list;
    }

    static class ViewHolder {
        TextView leftMenuChildName;
        RelativeLayout rlChild;
    }
}
