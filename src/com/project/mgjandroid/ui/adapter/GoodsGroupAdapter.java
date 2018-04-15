package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.GroupBean;
import com.project.mgjandroid.bean.PickGoods;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;

import java.util.ArrayList;
import java.util.List;

public class GoodsGroupAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<GroupBean> list;
	private ArrayList<Long> idList;

	public GoodsGroupAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.list = new ArrayList<>();
		this.idList = new ArrayList<>();
	}

	public ArrayList<GroupBean> getList() {
		return list;
	}

	public void setList(ArrayList<GroupBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void setIdList(ArrayList<Long> list) {
		this.idList = list;
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
			convertView = inflater.inflate(R.layout.goods_group_item, null);
			holder.name = (TextView) convertView.findViewById(R.id.goods_group_item_tv);
			holder.menuCount = (TextView) convertView.findViewById(R.id.menu_num);
			holder.leftLine = convertView.findViewById(R.id.goods_group_item_left_line);
			holder.item = convertView.findViewById(R.id.item_group);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (CheckUtils.isNoEmptyStr(list.get(position).getName())) {
			holder.menuCount.setVisibility(View.INVISIBLE);
//			if (list.get(position).getName().equals("热销榜") && position <= 1) {
//				Drawable drawable = context.getResources().getDrawable(R.drawable.icon_hot);
//				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//				holder.name.setCompoundDrawables(drawable, null, null, null);
//				holder.name.setCompoundDrawablePadding(DipToPx.dip2px(context, 4));
//			} else if (list.get(position).getName().equals("销量排行") && position <= 1) {
//				Drawable drawable = context.getResources().getDrawable(R.drawable.icon_parise);
//				drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//				holder.name.setCompoundDrawables(drawable, null, null, null);
//				holder.name.setCompoundDrawablePadding(DipToPx.dip2px(context, 4));
//			} else {
//				holder.name.setCompoundDrawables(null, null, null, null);
//				holder.name.setCompoundDrawablePadding(DipToPx.dip2px(context, 0));
				List<PickGoods> pickGoodsList = ((CommercialActivity) context).getCartProducts();
				if(CheckUtils.isNoEmptyList(pickGoodsList)){
					int count = 0;
					for(PickGoods pickGoods: pickGoodsList){
						if(idList.get(position)!=null && pickGoods.getMenuId() == idList.get(position) && pickGoods.getPickCount()>0){
							count += pickGoods.getPickCount();
						}
					}
					if(count > 0){
						holder.menuCount.setVisibility(View.VISIBLE);
						holder.menuCount.setText(String.valueOf(count));
					}
				}
//			}
			holder.name.setText(list.get(position).getName());
		}

		GroupBean groupBean = list.get(position);
		if(groupBean.isClick()){
			holder.item.setBackgroundResource(R.color.white);
			holder.leftLine.setVisibility(View.VISIBLE);
		}else{
			holder.item.setBackgroundResource(R.color.gray_bg);
			holder.leftLine.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView name;
		TextView menuCount;
		View leftLine;
		View item;
	}

}
