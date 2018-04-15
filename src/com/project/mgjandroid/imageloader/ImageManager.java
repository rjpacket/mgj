package com.project.mgjandroid.imageloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class ImageManager{
	private static final int DEFAULT_IMAGE_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_IMAGE_READ_TIMEOUT = 10000;
	
	 /**
     * @param url  图片地址
     * @param file 要保存到的文件
     * @return
     */
    public static boolean download(String url, File file) {
        if (url == null || file == null) {
            return false;
        }
        HttpGet httpGet = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            httpGet = new HttpGet(url);
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, DEFAULT_IMAGE_CONNECT_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParameters, DEFAULT_IMAGE_READ_TIMEOUT);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            BasicHttpResponse httpResponse = (BasicHttpResponse) httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            int responseCode = httpResponse.getStatusLine().getStatusCode();
            if (responseCode == HttpStatus.SC_NOT_FOUND) {
                return false;
            } else if (responseCode != HttpStatus.SC_OK) {
                return false;
            }
            is = entity.getContent();
            fos = new FileOutputStream(file);
            byte[] buff = new byte[4096];
            int n = -1;
            while ((n = is.read(buff)) != -1) {
                fos.write(buff, 0, n);
            }
            fos.flush();
            buff = null;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (httpGet != null) {
                    httpGet.abort();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//		Log.i("pp", ""+file.length()/1000+"------"+url);
        return (file.exists() && file.length() > 0);
    }


}
