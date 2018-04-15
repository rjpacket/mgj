package com.project.mgjandroid.chooseimage;

/**
 * Created by yuandi on 2016/7/8.
 *
 */
public class UploadPhoto{
    /**
     * 本地路径
     */
    private String path;
    /**
     * qiniu地址
     */
    private String url;
    /**
     * 是否显示加载失败
     * 0:未上传
     * 1：正在上传
     * 2：上传失败
     * 3：上传成功
     */
    private boolean uploaded = true;

    private boolean uploadFinish = false;

    public UploadPhoto(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public boolean isUploadFinish() {
        return uploadFinish;
    }

    public void setUploadFinish(boolean uploadFinish) {
        this.uploadFinish = uploadFinish;
    }
}