package com.project.mgjandroid.chooseimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.project.mgjandroid.R;

import java.util.ArrayList;
import java.util.List;


public class ImgsAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> data;
	ArrayList<String> hasChoosedData;
	public Bitmap bitmaps[];
	ImgUtils util;
	OnItemClickListener onItemClickListener;
	private int index = -1;

	List<View> holderlist;

	public ImgsAdapter(Context context, ArrayList<String> data, ArrayList<String> hasChoosedData, OnItemClickListener onItemClickListener) {
		this.context = context;
		this.data = data;
		this.hasChoosedData = hasChoosedData;
		this.onItemClickListener = onItemClickListener;
		util = new ImgUtils(context);
		holderlist = new ArrayList<>();
	}

	@Override
	public int getCount() {
		if (data != null) {
			bitmaps = new Bitmap[data.size()];
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if (arg0 != index && arg0 > index) {
			index = arg0;
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_imags, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) arg1.findViewById(R.id.image_view);
			holder.checkBox = (CheckBox) arg1.findViewById(R.id.check_box);
			holder.imge_selected = (ImageView) arg1.findViewById(R.id.imgs_item_img_selected);
			arg1.setTag(holder);
			holderlist.add(arg1);
		} else {
			holder = (ViewHolder) holderlist.get(arg0).getTag();
			arg1 = holderlist.get(arg0);
		}
		if (bitmaps[arg0] == null) {
			util.imgExcute(holder.imageView, new ImgCallBackListener(arg0),
					data.get(arg0));
		} else {
			holder.imageView.setImageBitmap(bitmaps[arg0]);
		}
		if(hasChoosedData.contains(data.get(arg0))) {
			holder.checkBox.setChecked(true);
		}else{
			holder.checkBox.setChecked(false);
		}
		if (holder.checkBox.isChecked()) {
			holder.imge_selected.setVisibility(View.VISIBLE);
			holder.imageView.setColorFilter(Color.parseColor("#80000000"));
		}else{
			holder.imge_selected.setVisibility(View.GONE);
			holder.imageView.setColorFilter(Color.parseColor("#00000000"));
		}

		arg1.setOnClickListener(new OnPhotoClick(arg0, holder.checkBox, holder.imge_selected, holder.imageView));

		return arg1;
	}

	class ViewHolder {
		ImageView imageView;
		CheckBox checkBox;
		ImageView imge_selected;
	}

	public class ImgCallBackListener implements ImgCallBack {
		int num;

		public ImgCallBackListener(int num) {
			this.num = num;
		}

		@Override
		public void resultImgCall(ImageView imageView, Bitmap bitmap) {
			bitmaps[num] = bitmap;
			imageView.setImageBitmap(bitmap);
		}
	}

	class OnPhotoClick implements OnClickListener {
		int position;
		CheckBox checkBox;
		ImageView imagSelected = null;
		ImageView image = null;

		public OnPhotoClick(int position, CheckBox checkBox) {
			this.position = position;
			this.checkBox = checkBox;
		}
		
		public OnPhotoClick(int position, CheckBox checkBox, ImageView imge_selected, ImageView image) {
			this.position = position;
			this.checkBox = checkBox;
			this.imagSelected = imge_selected;
			this.image = image;
		}

		@Override
		public void onClick(View v) {
			if (data != null && onItemClickListener != null) {
				onItemClickListener.OnItemClick(v, position, checkBox, imagSelected, image);
			}
		}
	}

}
