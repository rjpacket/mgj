package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.DefaultAddressModel.DefaultAddress;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/5/3.
 */
public class DefaultAddressAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DefaultAddress> list;
    private LayoutInflater mInflater;

    public DefaultAddressAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = new ArrayList<>();
    }

    public ArrayList<DefaultAddress> getList() {
        return list;
    }

    public void setList(ArrayList<DefaultAddress> list) {
        if(list == null){
            list = new ArrayList<>();
        }
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.default_address_tv, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_default_address_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
            holder.tvName.setText(list.get(position).getAddress());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
    }


}
