package com.project.mgjandroid.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import org.apache.http.util.ByteArrayBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class StreamUtils {

    /**
     * 从输入流读取数据
     *
     * @param is 输入流
     * @return byte[] 输入流所代表的字节数组
     * @throws java.io.IOException
     * @throws Exception
     */
    public static byte[] readByteArrayFromInputStream(InputStream is) throws Exception {
        return readByteArrayFromInputStream(is, 0);
    }

    public static byte[] readByteArrayFromInputStream(InputStream is, int contentLength) throws Exception {
        if (is == null) {
            return null;
        }
        byte[] data = null;
        try {
            int bufferSize = (is.available() < 32) ? 32 : is.available();
            if (contentLength > 0) {
                bufferSize = contentLength;
            }
            ByteArrayBuffer buffer = new ByteArrayBuffer(bufferSize);
            byte[] tmp = new byte[4096];
            int l;
            while ((l = is.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            data = buffer.toByteArray();
        } catch (IOException e) {
            throw new Exception("网络异常，请重试！");
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }

        return data;
    }

    /**
     * 将Bitmap转换成byte数组
     *
     * @param bm
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 从输入流读取图片数据
     *
     * @param is                     要进行处理的图片流
     * @param isUseFilterInputStream 用于网络获取图片时返回bitmap，主要针对网络状况不好的情况
     * @return
     */
    public static Bitmap readBitmapFromInputStream(InputStream is, boolean isUseFilterInputStream) {
        if (is == null) {
            return null;
        }
        Bitmap bitmap = null;
        if (isUseFilterInputStream) {
            // Bug on slow connections, fixed in future release.
            is = new FlushedInputStream(is);
        }
        bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    /**
     * 用于网络状况极不好情况下进行获取流资源
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break; // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    public static void copyFileToAimFile(File source, File dest) {

        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(source).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        } catch (Exception e) {
        } finally {
            try {
                if (inChannel != null) {
                    inChannel.close();
                }
            } catch (IOException e) {
            }
            try {
                if (outChannel != null) {
                    outChannel.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
