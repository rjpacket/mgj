package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.model.BaiduGeocoderModel.ResultBean.PoisBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/4/11.
 */
public class LocalPoiListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater mInflater;
    private List<PoisBean> list;
    private boolean isMapSearch;

    public LocalPoiListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<PoisBean>();
        this.mInflater = LayoutInflater.from(context);
    }

    public List<PoisBean> getList() {
        return list;
    }

    public void setList(List<PoisBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setIsMapSearch(boolean isMapSearch) {
        this.isMapSearch = isMapSearch;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.location_list_item, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_address);
            holder.tvDes = (TextView) convertView.findViewById(R.id.tv_detail);
            holder.tvCurrent = (TextView) convertView.findViewById(R.id.tv_current);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PoisBean poisBean = list.get(position);
        holder.tvName.setText(poisBean.getName());
        holder.tvDes.setText(poisBean.getAddr());
        if(isMapSearch){
            if(position==0){
                holder.tvCurrent.setVisibility(View.VISIBLE);
            }else{
                holder.tvCurrent.setVisibility(View.GONE);
            }
        }else{
            holder.tvCurrent.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvName, tvDes, tvCurrent;
    }

}
