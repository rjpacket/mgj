package com.project.mgjandroid.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.project.mgjandroid.imageloader.CacheHelper;
import com.project.mgjandroid.utils.FileUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.support.v4.util.LruCache;

/**
 * 图片缓存
 *
 * @author Administrator
 */
public class ImageCache {
    private LruCache<String, Bitmap> memoryCache;
    private static ImageCache instance;
    private App appContext;

    private ImageCache() {
        appContext = App.getInstance();
        //系统内存大小
        int size = PreferenceUtils.getIntPreference(PreferenceUtils.SYSTEM_MAX_MEMORY, -1, App.getInstance());
        if (size == -1) {
            size = ((ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
            PreferenceUtils.saveIntPreference(PreferenceUtils.SYSTEM_MAX_MEMORY, size);
        }

        appContext.saveSystemMaxMemory(size);

        if (size <= 16) {
            size = 1;
        } else if (size <= 32) {
            size = 3;
        } else if (size <= 64) {
            size = size / 8;
        } else {
            size = size / 4;
        }
        memoryCache = new LruCache<String, Bitmap>(1024 * 1024 * size) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return ImageUtils.getBitmapSize(bitmap);
            }
        };
    }

    public synchronized static ImageCache getInstance() {
        if (instance == null) {
            instance = new ImageCache();
            instance.enableDiskCache();
        }
        return instance;
    }

    private void enableDiskCache() {
        String diskCacheDirectory = getDiskCacheDirectory();
        File outFile = new File(diskCacheDirectory);
        outFile.mkdirs();
    }

    private String getDiskCacheDirectory() {
        return FileCache.getAppImageCacheDirectory();
    }

    public boolean containsBitmapInMemory(String key) {
        return (key != null && memoryCache != null && memoryCache.get(key) != null);
    }

    public void clearMemory() {
        if (memoryCache != null) {
            memoryCache.evictAll();
            memoryCache = null;
            instance = null;
        }
    }

    /**
     * 从本地文件获取URL
     *
     * @param imageUrl
     * @return
     */
    public synchronized Uri getFileUriInDisk(String imageUrl) {
        String fileName = getFileNameForKey(imageUrl);
        String filePath = getDiskCacheDirectory() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return Uri.fromFile(file);
        }
        return null;
    }

    /**
     * 根据url获取路径
     *
     * @param imageUrl
     * @return
     */
    public synchronized String getFilePathInDisk(String imageUrl) {
        String fileName = getFileNameForKey(imageUrl);
        String filePath = getDiskCacheDirectory() + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return filePath;
        }
        return null;
    }

    /**
     * 先从内存中获取图片，如果不存在再到文件中查找
     *
     * @param imageUrl 图片地址
     * @return 返回bitmap，如果不存在返回null
     */
    public Bitmap getBitmap(String imageUrl, boolean fromMemoryOnly) {
        Bitmap bitmap = memoryCache.get(imageUrl);
        if (bitmap == null && !fromMemoryOnly) {
            bitmap = getBitmapFromDisk(imageUrl);
            cacheToMemory(imageUrl, bitmap);
        }
        return bitmap;
    }

    public Bitmap getBitmapFromMemory(String imageUrl) {
        if (memoryCache != null) {
            return memoryCache.get(imageUrl);
        } else {
            return null;
        }
    }

    public void cacheToMemory(String key, Bitmap bitmap) {
        if (memoryCache != null && memoryCache.get(key) == null && bitmap != null) {
            try {
                memoryCache.put(key, bitmap);
            } catch (Exception e) {
            }
        }
    }

    public void recycleBitmap(String imageUrl) {
        if (memoryCache != null) {
            Bitmap bitmap = memoryCache.get(imageUrl);
            if (bitmap != null) {
                memoryCache.remove(imageUrl);
                // bitmap.recycle();
                bitmap = null;
            }
        }
    }

    public Bitmap getBitmapFromCache(String imageUrl, int reqWidth, int reqHeight) {
        Bitmap bitmap = null;
        bitmap = getBitmapFromMemory(imageUrl);
        if (bitmap != null) {
            return bitmap;
        }
        File file = getFileForKey(imageUrl);
        if (file.exists()) {
            bitmap = ImageUtils.decodeSampledBitmapFromFile(file.getPath(), reqWidth, reqHeight);
            if (bitmap == null) {
                file.delete();
            } else {
                file.setLastModified(System.currentTimeMillis());
                return bitmap;
            }
        }
        return bitmap;
    }

    public Bitmap getBitmapFromDisk(String imageUrl) {
        File file = getFileForKey(imageUrl);
        if (file.exists()) {
            Bitmap bitmap = ImageUtils.readImageFromFile(file);
            if (bitmap == null) {
                file.delete();
            } else {
                file.setLastModified(System.currentTimeMillis());
                return bitmap;
            }
        }
        return null;
    }

    @Deprecated
    public void saveToDisk(String key, Bitmap bitmap) {
        if (null == bitmap) {
            return;
        }
        BufferedOutputStream ostream = null;
        CompressFormat cFormat = CompressFormat.JPEG;
        if (key.contains(".png")) {
            cFormat = CompressFormat.PNG;
        }
        String path = getDiskCacheDirectory() + getFileNameForKey(key);
        try {
            ostream = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(cFormat, 80, ostream);
            ostream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ostream != null) {
                try {
                    ostream.close();
                    bitmap = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveToDisk(String url, byte[] b) {
        String path = getDiskCacheDirectory() + getFileNameForKey(url);
        BufferedOutputStream ostream = null;
        try {
            ostream = new BufferedOutputStream(new FileOutputStream(path));
            ostream.write(b);
            ostream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ostream != null) {
                try {
                    ostream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteBitmapFromDisk(String key) {
        File file = new File(getDiskCacheDirectory() + getFileNameForKey(key));
        file.delete();
    }

    /**
     * 图片是否存储在SDCard上
     */
    public boolean containsInDisk(String imageUrl) {
        if (imageUrl == null) {
            return false;
        }
        if (containsBitmapInMemory(imageUrl)) {
            return true;
        }
        File file = getFileForKey(imageUrl);
        if (file.exists() && file.length() > 0) {
            return true;
        }
        return false;
    }

    private String getFileNameForKey(String imageUrl) {
        return CacheHelper.getFileNameFromUrl(imageUrl);
    }

    public File getFileForKey(String imageUrl) {
        return new File(getDiskCacheDirectory() + getFileNameForKey(imageUrl));
    }

    public byte[] readImageByte(String imageUrl) {
        return FileUtils.readFile(getFileForKey(imageUrl));
    }
}
