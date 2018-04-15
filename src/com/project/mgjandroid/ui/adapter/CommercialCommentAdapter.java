package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.LeafComment;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.MerchantEvaluateModel;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ImageUtils;

import java.util.ArrayList;

public class CommercialCommentAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<MerchantEvaluateModel.ValueEntity> list;

	public CommercialCommentAdapter(Context context) {
		this.context = context;
		list = new ArrayList<MerchantEvaluateModel.ValueEntity>();
		this.inflater = LayoutInflater.from(context);

	}

	public ArrayList<MerchantEvaluateModel.ValueEntity> getList() {
		return list;
	}

	public void setList(ArrayList<MerchantEvaluateModel.ValueEntity> list) {
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
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.commercial_comment_item, null);
			holder.score = (RatingBar) convertView.findViewById(R.id.commercial_comment_item_score);
			holder.tvArrive = (TextView) convertView.findViewById(R.id.commercial_comment_item_arrive_time);
			holder.tvDate = (TextView) convertView.findViewById(R.id.commercial_comment_tv_date_time);
			holder.tvName = (TextView) convertView.findViewById(R.id.commercial_user_name);
			holder.imgHeader = (ImageView) convertView.findViewById(R.id.commercial_comment_item_header);
			holder.tvContent = (TextView) convertView.findViewById(R.id.commercial_comment_tv_comment);
			holder.layoutFirstLeaf = (LinearLayout) convertView.findViewById(R.id.commercial_comment_layout_first_leaf);
			holder.layoutProducts = (LinearLayout) convertView.findViewById(R.id.commercial_comment_item_layout_leafs);
			holder.tvCheckMore = (TextView) convertView.findViewById(R.id.commercial_comment_item_check_more);
			holder.tvReply = (TextView) convertView.findViewById(R.id.tv_merchant_reply);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
			MerchantEvaluateModel.ValueEntity comment = list.get(position);
			if (comment != null) {
				showItem(holder, comment);
			}
		}
		return convertView;
	}

	private void showItem(final ViewHolder holder, final MerchantEvaluateModel.ValueEntity comment) {
		holder.score.setRating(comment.getMerchantScore());
		holder.tvArrive.setText(comment.getDeliveryCost() + "分钟送达");
		holder.tvDate.setText(comment.getCreateTime());
		String des = comment.getMerchantComments();
		if (CheckUtils.isNoEmptyStr(des)) {
			holder.tvContent.setVisibility(View.VISIBLE);
			holder.tvContent.setText(des);
			holder.tvContent.setTextColor(context.getResources().getColor(R.color.black));
		} else {
//			holder.tvContent.setVisibility(View.GONE);
			holder.tvContent.setText("该用户没有做具体评价哦！");
			holder.tvContent.setTextColor(context.getResources().getColor(R.color.gray_4));
		}
		if (comment.getAppUser() != null) {
			String name = comment.getAppUser().getName();
			if(name == null){
				name = comment.getAppUser().getMobile();
			}
			if(name.length() <= 1){
				name = name.substring(0,1) + "***";
			}else if(name.length() > 1){
				name = name.substring(0,1) + "***" + name.substring(name.length() - 1,name.length());
			}
			holder.tvName.setText(name);
			ImageUtils.loadBitmap(context , comment.getAppUser().getHeaderImg() , holder.imgHeader, R.drawable.comment_defaut_head , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL);
		}
		if (CheckUtils.isNoEmptyStr(comment.getReplyContent())) {
			holder.tvReply.setVisibility(View.VISIBLE);
			holder.tvReply.setText("商家回复：" + comment.getReplyContent());
		} else {
			holder.tvReply.setVisibility(View.GONE);
		}
//		ArrayList<LeafComment> comments = comment.getLeafComments();
//		if (CheckUtils.isNoEmptyList(comments)) {
//			boolean isChecked = comment.isChecked();
//			holder.layoutFirstLeaf.setVisibility(View.VISIBLE);
//			showLeaf(isChecked, holder, comments);
//		} else {
//			holder.layoutFirstLeaf.setVisibility(View.GONE);
//			holder.tvCheckMore.setVisibility(View.GONE);
//			holder.layoutProducts.setVisibility(View.GONE);
//		}
		holder.tvCheckMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (comment.isChecked()) {
//					comment.setChecked(false);
//					showCheckMore(false, holder);
//				} else {
//					comment.setChecked(true);
//					showCheckMore(true, holder);
//				}
			}
		});

	}

	private void showCheckMore(boolean isChecked, ViewHolder holder) {
		if (isChecked) {
			holder.tvCheckMore.setText("收起");
			Drawable drawable = context.getResources().getDrawable(R.drawable.nabla_red);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			holder.tvCheckMore.setCompoundDrawables(drawable, null, null, null);
			holder.tvCheckMore.setCompoundDrawablePadding(DipToPx.dip2px(context, 4));
			holder.layoutProducts.setVisibility(View.VISIBLE);
		} else {
			holder.tvCheckMore.setText("查看更多");
			Drawable drawable = context.getResources().getDrawable(R.drawable.nabla_black);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
			holder.tvCheckMore.setCompoundDrawables(drawable, null, null, null);
			holder.tvCheckMore.setCompoundDrawablePadding(DipToPx.dip2px(context, 4));
			holder.layoutProducts.setVisibility(View.GONE);
		}
	}

	private void showLeaf(boolean isChecked, ViewHolder holder, ArrayList<LeafComment> comments) {
		if (comments.size() > 1) {
			holder.tvCheckMore.setVisibility(View.VISIBLE);
			showCheckMore(isChecked, holder);
			holder.layoutFirstLeaf.removeAllViews();
			holder.layoutProducts.removeAllViews();
			for (int i = 0; i < comments.size(); i++) {
				LeafComment comment = comments.get(i);
				if (comment != null) {
					LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.commercial_comment_leaf, null);
					TextView tvName = (TextView) layout.findViewById(R.id.commercial_comment_leaf_tv_name);
					TextView tvSatisfaction = (TextView) layout.findViewById(R.id.commercial_comment_leaf_tv_satisfaction);
					TextView tvDes = (TextView) layout.findViewById(R.id.commercial_comment_leaf_tv_comment);
					if (comment.getGoods() != null) {
						tvName.setText(comment.getGoods().getName());
					}
					tvSatisfaction.setText(comment.getSatisfaction());
					String des = comment.getDescription();
					if (CheckUtils.isEmptyStr(des)) {
						tvDes.setVisibility(View.GONE);
					} else {
						tvDes.setVisibility(View.VISIBLE);
					}
					if (i == 0) {
						holder.layoutFirstLeaf.addView(layout);
					} else {
						holder.layoutProducts.addView(layout);
					}
				}
			}
		} else {
			holder.tvCheckMore.setVisibility(View.GONE);
			holder.layoutProducts.setVisibility(View.GONE);
		}
	}

	static class ViewHolder {
		RatingBar score;
		TextView tvArrive;
		TextView tvName;
		ImageView imgHeader;
		TextView tvContent;
		TextView tvDate;
		LinearLayout layoutProducts;
		TextView tvCheckMore;
		LinearLayout layoutFirstLeaf;
		TextView tvReply;
	}

}
