package com.project.mgjandroid.chooseimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ImgFolderListAdapter extends BaseAdapter {

	private Context context;
	private final static String filecount = "filecount";
	private final static String filename = "filename";
	private final static String imgpath = "imgpath";
	private List<HashMap<String, String>> listdata;
	private ImgUtils util;
	private Bitmap[] bitmaps;
	private int index = -1;
	private List<View> holderlist;

	public ImgFolderListAdapter(Context context,
			List<HashMap<String, String>> listdata) {
		this.context = context;
		this.listdata = listdata;
		bitmaps = new Bitmap[listdata.size()];
		util = new ImgUtils(context);
		holderlist = new ArrayList<>();
	}

	@Override
	public int getCount() {
		if (listdata != null) {
			return listdata.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		return listdata.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View arg1, ViewGroup arg2) {
		Holder holder;
		if (arg0 != index && arg0 > index) {
			holder = new Holder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.img_folder_adapter, null);
			holder.photo_imgview = (ImageView) arg1.findViewById(R.id.filephoto_imgview);
			holder.filecount_textview = (TextView) arg1.findViewById(R.id.tv_filecount);
			holder.filename_textView = (TextView) arg1.findViewById(R.id.tv_filename);
			arg1.setTag(holder);
			holderlist.add(arg1);
		} else {
			holder = (Holder) holderlist.get(arg0).getTag();
			arg1 = holderlist.get(arg0);
		}

		holder.filename_textView.setText(listdata.get(arg0).get(filename));
		holder.filecount_textview.setText(listdata.get(arg0).get(filecount));
		if (bitmaps[arg0] == null) {
			util.imgExcute(holder.photo_imgview, new ImgCallBack() {
				@Override
				public void resultImgCall(ImageView imageView, Bitmap bitmap) {
					bitmaps[arg0] = bitmap;
					imageView.setImageBitmap(bitmap);
				}
			}, listdata.get(arg0).get(imgpath));
		} else {
			holder.photo_imgview.setImageBitmap(bitmaps[arg0]);
		}

		return arg1;
	}

	class Holder {
		public ImageView photo_imgview;
		public TextView filecount_textview;
		public TextView filename_textView;
	}

}
