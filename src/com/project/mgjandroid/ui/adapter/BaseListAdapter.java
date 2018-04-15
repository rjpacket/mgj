package com.project.mgjandroid.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.project.mgjandroid.model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter的基类
 *
 * @param <T>
 * @author Administrator
 */
public abstract class BaseListAdapter<T extends Entity> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected List<T> mDatas = new ArrayList<T>();
    protected int layoutId;
    protected String from;
    protected Activity mActivity;

    /**
     *
     */
    public BaseListAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        getLayoutInflater(mActivity);
    }


    public BaseListAdapter(int layoutId, Activity mActivity) {
        this.layoutId = layoutId;
        this.mActivity = mActivity;
        getLayoutInflater(mActivity);
    }

    public BaseListAdapter(int layoutId, Activity activity, String str) {
        this.layoutId = layoutId;
        this.mActivity = activity;
        this.from = str;
        getLayoutInflater(mActivity);
    }

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public int getDataCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int arg0) {
        if (mDatas.size() > arg0) {
            return mDatas.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public void setData(List<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T obj) {
        if (mDatas != null) {
            mDatas.add(obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = ViewHolder.get(position, convertView, parent, layoutId);

        getRealView(holder, mDatas.get(position), position, convertView, parent);

        return holder.getConvertView();
    }

    public void notifyDataSetChanged(List<T> list) {
        if (mDatas == null) {
            mDatas = new ArrayList<T>();
        }
        mDatas.addAll(list);
        super.notifyDataSetChanged();
    }

    abstract protected void getRealView(ViewHolder holder, T bean, int position, View convertView, ViewGroup parent);

}
