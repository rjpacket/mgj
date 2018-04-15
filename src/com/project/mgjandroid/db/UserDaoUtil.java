package com.project.mgjandroid.db;

import java.util.ArrayList;


import android.content.Context;
import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;
  
public class UserDaoUtil {  
    private UserDao mUserDao;  
    private AppUserEntity mUser;
      
    public UserDaoUtil(Context context){  
        mUserDao=new UserDao(context);  
        mUser=new AppUserEntity();
    }  
      
    public void saveNewUser(AppUserEntity appUserEntity){
        mUser.setId(appUserEntity.getId());
        mUser.setCreateTime(appUserEntity.getCreateTime());
        mUser.setModifyTime(appUserEntity.getModifyTime());
        mUser.setName(appUserEntity.getName());
        mUser.setMobile(appUserEntity.getMobile());
        mUser.setPwd(appUserEntity.getPwd());
        mUser.setHeaderImg(appUserEntity.getHeaderImg());
        mUser.setToken(appUserEntity.getToken());
        mUser.setRegTime(appUserEntity.getRegTime());
        mUser.setLastLoginTime(appUserEntity.getLastLoginTime());
        mUser.setChannel(appUserEntity.getChannel());
        mUser.setUserToken(appUserEntity.getUserToken());
        mUserDao.add(mUser);
        mUserDao.close();  
    }  
      
    public AppUserEntity getFirstUser(){
        ArrayList<AppUserEntity> users=new ArrayList<AppUserEntity>();
        users=mUserDao.select();  
        if(users !=null && users.size() > 0){
            mUser=users.get(0);
        }  
        return mUser;  
          
    }  
}  