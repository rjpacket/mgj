package com.project.mgjandroid.database;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description: 所有数据库操作的操作封装类，在此可以封装单次的插入操作，也可以封装大量数据的一次插入，
 * 其他类只能通过helper的子类进行数据库的操作，不能直接操作数据库，每一个数据库对应一个数据库helper，
 * 这样就能够保证数据库访问的统一性，以便以后的数据库修改，继承自该helper类之后也可增加方法去操作
 * {@link #mDb}
 *
 */
public abstract class BaseDBHelper {
    protected BaseDB mDb;
    protected IBaseDBTable mTable;

    /** 初始化插入数据库 */
    protected abstract void initInsertDB();
    /** 初始化删除数据库 */
    protected abstract void initDeleteDB();
    /** 初始化更改数据库 */
    protected abstract void initUpdateDB();
    /** 初始化查询数据库 */
    protected abstract void initQueryDB();

    /**
     * 单次插入
     * @param map 单次插入的一行数据
     */
    protected long insert(HashMap<String, String> map, boolean replace){
        initInsertDB();
        if (mDb == null)
            return 0;
        long count = -1;
        try {
            mDb.beginTransaction();
            count = mDb.insert(map, replace);
            mDb.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
            mDb.close();
        }
        return count;
    }

    /**
     * 大量数据的插入
     * @param maps 需要插入的数据集
     * @return 成功插入的数量
     */
    protected long insertAll(ArrayList<HashMap<String, String>> maps, boolean replace){
        initInsertDB();
        if (mDb == null)
            return 0;
        long count = 0;
        try {
            mDb.beginTransaction();
            for (HashMap<String, String> map : maps) {
                count ++;
                mDb.insert(map, replace);
            }
            mDb.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
            mDb.close();
        }
        return count;
    }

    /**
     * 删除
     */
    protected long delete(String selection, String[] selectionArgs){
        initDeleteDB();
        if (mDb == null)
            return 0;
        long count = -1;
        try {
            mDb.beginTransaction();
            count = mDb.delete(selection, selectionArgs);
            mDb.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
            mDb.close();
        }
        return count;
    }

    /**
     * 修改
     */
    protected long update(HashMap<String, String> maps, String whereClause, String[] whereArgs){
        initUpdateDB();
        if (mDb == null)
            return 0;
        long count = -1;
        try {
            mDb.beginTransaction();
            count = mDb.update(maps, whereClause, whereArgs);
            mDb.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
            mDb.close();
        }
        return count;
    }

    protected ArrayList<HashMap<String, String>> query(String selection, String[] selectionArgs,
                                                                String groupBy, String having, String orderBy, String limit){
        initQueryDB();
        if (mDb == null)
            return null;
        ArrayList<HashMap<String, String>> result = null;
        try {
            mDb.beginTransaction();
            result = mDb.query(selection, selectionArgs, groupBy, having, orderBy, limit);
            mDb.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mDb.endTransaction();
            mDb.close();
        }
        return result;
    }
}
