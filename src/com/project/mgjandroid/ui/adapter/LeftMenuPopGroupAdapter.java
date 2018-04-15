package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CategoryLeftBean;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.List;

/**
 * Created by User_Cjh on 2016/3/24.
 */
public class LeftMenuPopGroupAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryLeftBean> list;
    private View.OnClickListener listener;

    public LeftMenuPopGroupAdapter(Context context, List<CategoryLeftBean> list,View.OnClickListener listener) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_left_menu_group, null);
            holder.rlGroup = (RelativeLayout) view.findViewById(R.id.group_category);
            holder.leftMenuGroupIcon = (ImageView) view.findViewById(R.id.left_menu_group_icon);
            holder.leftMenuGroupName = (TextView) view.findViewById(R.id.left_menu_group_name);
            holder.leftMenuGroupArrow= (ImageView) view.findViewById(R.id.left_menu_group_arrow);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.rlGroup.setTag(i);
        holder.rlGroup.setOnClickListener(listener);
        holder.leftMenuGroupName.setText(list.get(i).getName());
        if (!TextUtils.isEmpty(list.get(i).getIcon()))
            ImageUtils.loadBitmap(context , list.get(i).getIcon(), holder.leftMenuGroupIcon, R.drawable.ic_launcher , "");

        if(list.get(i).isChecked()){
            holder.leftMenuGroupName.setTextColor(context.getResources().getColor(R.color.left_menu_group_checked));
            holder.leftMenuGroupArrow.setImageResource(R.drawable.left_menu_arrow_checked);
            view.setBackgroundColor(context.getResources().getColor(R.color.white));
        }else{
            holder.leftMenuGroupName.setTextColor(context.getResources().getColor(R.color.left_menu_group_unchecked));
            holder.leftMenuGroupArrow.setImageResource(R.drawable.left_menu_arrow_unchecked);
            view.setBackgroundColor(context.getResources().getColor(R.color.gray_bg));
        }

        return view;
    }

    public List<CategoryLeftBean> getList() {
        return list;
    }

    public void setList(List<CategoryLeftBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView leftMenuGroupName;
        ImageView leftMenuGroupIcon,leftMenuGroupArrow;
        RelativeLayout rlGroup;
    }
}
