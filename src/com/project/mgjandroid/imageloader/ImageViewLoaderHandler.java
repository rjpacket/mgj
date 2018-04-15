package com.project.mgjandroid.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageViewLoaderHandler extends ImageLoaderHandler {
    private ImageView imageView;
    protected String imgUrl;

    public ImageViewLoaderHandler(ImageView imageView, String imgUrl) {
        super(imgUrl);
        this.imageView = imageView;
        this.imgUrl = imgUrl;
        if (imageView != null) {
            imageView.setTag(imgUrl);
        }
    }

    @Override
    public boolean handleImageLoaded(Bitmap bmp) {
        boolean isSucess = super.handleImageLoaded(bmp);
        if (!isSucess || imageView == null) {
            isSucess = false;
        } else {
            String forUrl = (String) imageView.getTag();
            if (bmp != null && imgUrl.equals(forUrl)) {
                imageView.setImageBitmap(bmp);
                isSucess = true;
            }
        }
        imageView = null;
        bmp = null;
        return isSucess;
    }
}