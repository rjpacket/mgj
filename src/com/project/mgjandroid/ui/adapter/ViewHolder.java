package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 通用的ViewHolder
 */
public class ViewHolder {



	private SparseArray<View> mViews ;

	private int mPosition ;
	//
	private View mConvertView ;
	
	/**
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 */
	public ViewHolder(int position, View convertView, ViewGroup parent, int layoutId){
		this.mPosition = position ;
		this.mViews = new SparseArray<View>() ;

		mConvertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
//		AutoUtils.autoSize(convertView);
		mConvertView.setTag(this) ;
	}
	
	/**
	 *
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @return
	 */
	public static ViewHolder get(int position, View convertView, ViewGroup parent,int layoutId){
		if(convertView==null){
			return new ViewHolder(position, convertView, parent,layoutId) ;
		}else{
			ViewHolder holder = (ViewHolder) convertView.getTag() ;
			return holder ;
		}
	}

	/**
	 *
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId) ;
		if(view == null){
			view = mConvertView.findViewById(viewId) ;
			mViews.put(viewId, view) ;
		}
		
		return (T) view ;
	}
	
	/**
	 *
	 * @return
	 */
	public View getConvertView() {
		return mConvertView;
	}
	
	/**
	 *
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId,String text){
		TextView tv = getView(viewId) ;
		tv.setText(text) ;
		return this ;
	}
	/**
	 *
	 * @param viewId
	 * @param colorId
	 * @return
	 */
	public ViewHolder setTextColor(int viewId,int colorId){
		TextView tv = getView(viewId) ;
		tv.setTextColor(colorId) ;
		return this ;
	}
	
	public <T extends View> T setLayoutParam(int viewId,Activity activity){
		DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int currentWidth = (width-30)/2;
        LayoutParams parims = getView(viewId).getLayoutParams();
		parims.width = currentWidth;
		parims.height = (int) (currentWidth/1.57);
		getView(viewId).setLayoutParams(parims);
		return getView(viewId);
	}
	
	public ViewHolder onClickHolder(int viewId,OnClickListener listener){
		LinearLayout ll = getView(viewId);
		ll.setOnClickListener(listener);
		return this;
	}
	


	/**
	 *
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId,int resId){
		ImageView iv = getView(viewId) ;
		iv.setImageResource(resId) ;
		return this ;
	}
	
	
	
	/**
	 *
	 * @param viewId
	 * @return
	 */
	public ViewHolder setImageBitamp(int viewId,Bitmap bitmap){
		ImageView iv = getView(viewId) ;
		iv.setImageBitmap(bitmap) ;
		return this ;
	}

}
