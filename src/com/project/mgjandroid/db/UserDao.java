package com.project.mgjandroid.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.mgjandroid.model.SmsLoginModel.ValueEntity.AppUserEntity;

public class UserDao {
    private UserHelper mhelper;
    private SQLiteDatabase mdb;
    private Cursor cursor;

    public UserDao(Context context){
        mhelper=new UserHelper(context);
        mdb=mhelper.getWritableDatabase();
    }

    public void add(AppUserEntity user){
        mdb.execSQL("insert into mgj_user values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{
                user.getId(),
                user.getCreateTime(),
                user.getModifyTime(),
                user.getName(),
                user.getMobile(),
                user.getPwd(),
                user.getHeaderImg(),
                user.getToken(),
                user.getRegTime(),
                user.getLastLoginTime(),
                user.getChannel(),
                user.getUserToken()
        });
    }

    public ArrayList<AppUserEntity> select(){
        AppUserEntity user=new AppUserEntity();
        ArrayList<AppUserEntity> users=new ArrayList<AppUserEntity>();
        cursor=mdb.rawQuery("select * from mgj_user",null);
        if (cursor.moveToNext()){
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
            user.setModifyTime(cursor.getString(cursor.getColumnIndex("modifyTime")));
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
            user.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
            user.setHeaderImg(cursor.getString(cursor.getColumnIndex("headerImg")));
            user.setToken(cursor.getString(cursor.getColumnIndex("token")));
            user.setRegTime(cursor.getString(cursor.getColumnIndex("regTime")));
            user.setLastLoginTime(cursor.getString(cursor.getColumnIndex("lastLoginTime")));
            user.setChannel(cursor.getString(cursor.getColumnIndex("channel")));
            user.setUserToken(cursor.getString(cursor.getColumnIndex("userToken")));
            user.setRegTime(cursor.getString(cursor.getColumnIndex("regionId")));
            users.add(user);
            cursor.moveToNext();
        }
        return users;
    }

    public void close(){
        mdb.close();
    }
}