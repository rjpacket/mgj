package com.project.mgjandroid.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.project.mgjandroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalListAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<SuggestionResult.SuggestionInfo> list;
	private boolean isMapSearch;

	public LocalListAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<SuggestionResult.SuggestionInfo>();
		this.mInflater = LayoutInflater.from(context);
	}

	public List<SuggestionResult.SuggestionInfo> getList() {
		return list;
	}

	public void setList(List<SuggestionResult.SuggestionInfo> list) {
		this.list = list;
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

		SuggestionResult.SuggestionInfo suggestionInfo = list.get(position);
		holder.tvName.setText(suggestionInfo.key);
		String city = suggestionInfo.city;
		String district = suggestionInfo.district;
		StringBuffer sb = new StringBuffer();
		if(city != null){
			sb.append(city);
		}
		if(district != null){
			sb.append(district);
		}
		holder.tvDes.setText(sb.toString());
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
