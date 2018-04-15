package com.project.mgjandroid.imageloader;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import com.project.mgjandroid.base.App;
import com.project.mgjandroid.base.ImageCache;
import com.project.mgjandroid.utils.ImageUtils.ImageCallback;

import android.widget.ImageView;
public class ImageLoader {

    private static App appContext;
    private static ThreadPoolExecutor imageLoadExecutor;
    private static ImageCache imageCache;

    public synchronized static void initialize() {
        if (appContext == null) {
            appContext = App.getInstance();
        }
        if (imageCache == null) {
            imageCache = ImageCache.getInstance();
        }
        if (imageLoadExecutor == null) {
            int cpuNums = Runtime.getRuntime().availableProcessors();
            //根据系统CPU核心数定义线程池大小
            imageLoadExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(cpuNums);
        }
    }
    
    public static void start(String imgUrl){
    	start(imgUrl,null,false,-1,-1);
    }
    
    public static void start(String imgUrl,ImageCallback callBack) {
        start(imgUrl, new ImageViewCallBackLoader(imgUrl,callBack), false, -1, -1);
    }

    public static void start(String imgUrl, ImageView imageView) {
        start(imgUrl, new ImageViewLoaderHandler(imageView, imgUrl), false, -1, -1);
    }

    public static void start(String imgUrl, ImageView imageView, int reqWidth, int reqHeight) {
        start(imgUrl, new ImageViewLoaderHandler(imageView, imgUrl), false, reqWidth, reqHeight);
    }

    public static void startFromCacheOnly(String imgUrl, ImageView imageView) {
        start(imgUrl, new ImageViewLoaderHandler(imageView, imgUrl), true, -1, -1);
    }

    public static void startFromCacheOnly(String imgUrl, ImageLoaderCallback callback) {
        start(imgUrl, callback, true, -1, -1);
    }

    public static void start(String imgUrl, ImageLoaderCallback callback) {
        start(imgUrl, callback, false, -1, -1);
    }

    public static void start(String imgUrl, ImageLoaderCallback callback, int reqWidth, int reqHeight) {
        start(imgUrl, callback, false, reqWidth, reqHeight);
    }

    private synchronized static void start(String imgUrl, ImageLoaderHandler callback, boolean cacheOnly) {
        if (imgUrl == null) {
            return;
        }
        initialize();
        if (imageCache.containsBitmapInMemory(imgUrl)) {
            if (callback != null) {
                callback.handleImageLoaded(imageCache.getBitmap(imgUrl, true));
            }
            return;
        }
        try {
            imageLoadExecutor.execute(new ImageLoaderTask(imgUrl, callback, cacheOnly, -1, -1));
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    private synchronized static void start(String imgUrl, ImageLoaderCallback callback, boolean cacheOnly, int reqWidth, int reqHeight) {
        if (imgUrl == null) {
            return;
        }
        initialize();
        try {
            imageLoadExecutor.execute(new ImageLoaderTask(imgUrl, callback, cacheOnly, reqWidth, reqHeight));
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void clearLoadImagesTask() {

        if (imageLoadExecutor != null) {
            imageLoadExecutor.shutdownNow();
            imageLoadExecutor = null;
        }
    }

    public synchronized static void stopLoadAllImages() {

        clearLoadImagesTask();

        if (imageCache != null) {
            imageCache.clearMemory();
        }
    }

}
