package com.project.mgjandroid.chooseimage;

import java.util.ArrayList;

/**
 * Created by yuandi on 2016/4/23.
 *
 */
public class ChoosePhotoModel {

    private static ChoosePhotoModel mInstance;

    private ArrayList<String> mCurrentPhotoList = new ArrayList<>();

    private ArrayList<UploadPhoto> mUploadPhotoList = new ArrayList<>();

    private ArrayList<String> mCameraPhotoList = new ArrayList<>();

    private Class<?> currentActivity;

    private String from = "";

    private int maxCount;

    private ChoosePhotoModel() {
    }

    public static ChoosePhotoModel getInstance() {
        if (mInstance == null) {
            synchronized (ChoosePhotoModel.class) {
                if (mInstance == null) {
                    mInstance = new ChoosePhotoModel();
                }
            }
        }
        return mInstance;
    }

    public ArrayList<String> getmCurrentPhotoList() {
        return mCurrentPhotoList;
    }


    public void setmCurrentPhotoList(ArrayList<String> mCurrentPhotoList) {
        this.mCurrentPhotoList = mCurrentPhotoList;
    }

    public void addmCurrentPhotoList(ArrayList<String> photoList) {
        for(String photo:photoList){
            mCurrentPhotoList.add(photo);
        }
    }

    public void delCameraPhotoList() {
        for(String photo:mCameraPhotoList){
            if(mCurrentPhotoList.contains(photo)) mCurrentPhotoList.remove(photo);
        }
    }

    public ArrayList<UploadPhoto> getmUploadPhotoList() {
        return mUploadPhotoList;
    }

    public void updateCurrentPhotoList(UploadPhoto uploadPhoto) {
        if (mCurrentPhotoList.contains(uploadPhoto.getPath())){
            mCurrentPhotoList.remove(uploadPhoto.getPath());
            if(mCameraPhotoList.contains(uploadPhoto.getPath())){
                mCameraPhotoList.remove(uploadPhoto.getPath());
            }
        }
    }

    public ArrayList<UploadPhoto> updateUploadPhotoList() {
        if(mCurrentPhotoList.size() == 0) {
            mUploadPhotoList.clear();
            return mUploadPhotoList;
        }
        if(mUploadPhotoList.size()>0){
            for(int i = mUploadPhotoList.size()-1; i>=0; i--){
                if (!mCurrentPhotoList.contains(mUploadPhotoList.get(i).getPath())){
                    mUploadPhotoList.remove(i);
                }
            }
        }
        ArrayList<String> paths = new ArrayList<>();
        for (UploadPhoto uploadPhoto:mUploadPhotoList){
            paths.add(uploadPhoto.getPath());
        }
        for (String path:mCurrentPhotoList){
            if(!paths.contains(path)) {
                 mUploadPhotoList.add(new UploadPhoto(path));
            }
        }
        return mUploadPhotoList;
    }

    public ArrayList<String> getmCameraPhotoList() {
        return mCameraPhotoList;
    }

    public void setmCameraPhotoList(ArrayList<String> mCameraPhotoList) {
        this.mCameraPhotoList = mCameraPhotoList;
    }

    public void addPhoto(String s){
        mCurrentPhotoList.add(0,s);
    }

    public Class<?> getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Class<?> currentActivity) {
        this.currentActivity = currentActivity;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void clear(){
        currentActivity = null;
        mCurrentPhotoList.clear();
        mUploadPhotoList.clear();
        mCameraPhotoList.clear();
        from = "";
        maxCount = 0;
    }
}