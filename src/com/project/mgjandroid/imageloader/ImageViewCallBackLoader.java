package com.project.mgjandroid.imageloader;

import com.project.mgjandroid.utils.ImageUtils.ImageCallback;

import android.graphics.Bitmap;


public class ImageViewCallBackLoader extends ImageLoaderHandler{
	protected String imageUrl;
	protected ImageCallback callBack;
	
	public ImageViewCallBackLoader(String url,ImageCallback callback) {
		super(url);
		this.imageUrl=url;
		this.callBack=callback;
	}
	
	
	@Override
	public boolean handleImageLoaded(Bitmap bmp) {
		boolean isSucess=super.handleImageLoaded(bmp);
		if(!isSucess){
			isSucess=false;
		}else{
			if(callBack!=null){
				callBack.imageLoaded(bmp, 0, 0);
			}
			isSucess=true;
		}
		return isSucess;
	}

}
