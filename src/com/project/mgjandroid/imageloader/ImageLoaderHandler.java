package com.project.mgjandroid.imageloader;

import com.project.mgjandroid.base.ImageCache;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public abstract class ImageLoaderHandler extends Handler implements ImageLoaderCallback {
    public static final int HANDLER_MESSAGE_ID = 0x11;
    private String imageUrl;

    public ImageLoaderHandler(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void sendLoadedMessage(Bitmap bm) {
        ImageCache.getInstance().cacheToMemory(imageUrl, bm);
        sendMessage(Message.obtain(this, ImageLoaderHandler.HANDLER_MESSAGE_ID, bm));
    }

    @Override
    public final void handleMessage(Message msg) {
        if (msg.what == HANDLER_MESSAGE_ID) {
            if (msg.obj != null) {
                handleImageLoaded((Bitmap) msg.obj);
            }
        }
    }

    @Override
    public boolean handleImageLoaded(Bitmap bmp) {
        if (imageUrl != null && bmp != null) {
            return true;
        }
        return false;
    }
    
    public void getFilePath(String path){
    }
}
