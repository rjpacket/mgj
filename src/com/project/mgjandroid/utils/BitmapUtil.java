package com.project.mgjandroid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.project.mgjandroid.base.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具类
 */
public class BitmapUtil {
    private static Context mContext = App.getContext();

    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */

    public static Bitmap compressBitmap(String path, int maxValue) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); // 获取图片宽高,图片对象是不读取到内存

        int be = 1;
        int width = options.outWidth;
        int height = options.outHeight;
        if (height >= width) {
            if (height > maxValue) {
                be = height / maxValue;
                width = (int) (width * (Float.parseFloat(maxValue + "") / height));
                height = maxValue;
            }
        } else if (width > height) {
            if (width > maxValue) {
                be = width / maxValue;
                height = (int) (height * (Float.parseFloat(maxValue + "") / width));
                width = maxValue;
            }
        }
        if (be < 1) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        return createScaledBitmap(bitmap, width, height);
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param src       source bitmap
     * @param dstWidth  specified width
     * @param dstHeight specified height
     * @return The new scaled bitmap or the source bitmap if no scaling is required.
     */
    private static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight) {

        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false); // filter决定是否平滑，如果是缩小图片，filter无影响
        if (src != dst) { // 如果没有缩放，那么不回收，有缩放释放Bitmap的native像素数组
            recycleBitmap(src);
        }
        return dst;
    }

    /**
     * 回收图片
     *
     * @param bitmap 图片对象
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 获取图片的旋转角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap Bytes2Bimap(byte[] data) {
        if (data.length != 0) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        } else {
            return null;
         }
    }

    /**
     * 将图片按照指定的角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree) {
        if(bitmap == null) return null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static boolean saveBitmapToFileSuccess(Bitmap bmp, File file) {
        if(file == null || bmp == null) return false;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            recycleBitmap(bmp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean needCompressBitmap(String path, int maxValue) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options); // 获取图片宽高,图片对象是不读取到内存

        int width = options.outWidth;
        int height = options.outHeight;
        if(width >= height && width <= maxValue){
            return false;
        }else if(height > width && height <= maxValue){
            return false;
        }
        return true;
    }
}
