package com.project.mgjandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserHelper extends SQLiteOpenHelper{

    private final static String DB_NAME="User.db";
    private final static int DB_VERSION=1;

    public UserHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mgj_user(" +
                "id nvarchar(20)," +
                "createTime nvarchar(50)," +
                "modifyTime nvarchar(50)," +
                "name nvarchar(20)," +
                "mobile nvarchar(20)," +
                "pwd nvarchar(20)," +
                "headerImg nvarchar(120)," +
                "token nvarchar(50)," +
                "regTime nvarchar(50)," +
                "lastLoginTime nvarchar(50)," +
                "channel nvarchar(20)," +
                "ip nvarchar(20)," +
                "client nvarchar(20)," +
                "clientId nvarchar(50)," +
                "apnsProduction nvarchar(50)," +
                "app nvarchar(50)," +
                "imei nvarchar(50)," +
                "clientVersion nvarchar(20)," +
                "regionId nvarchar(20))"
                );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 自动升级的方法

    }

}
