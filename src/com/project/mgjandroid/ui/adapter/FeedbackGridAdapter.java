package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.utils.BitmapUtil;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/4/23.
 */
public class FeedbackGridAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mFiles;
    private LayoutInflater mInflater;
    private int maxSize;
    public FeedbackGridAdapter(Context context, List<String> files, int size){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mFiles = files;
        maxSize = size;
    }

    @Override
    public int getCount() {
        if(mFiles.size() >= maxSize){
            return maxSize;
        }
        return mFiles.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_feedback_grid_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
//        int screenWidth = DeviceParameter.getIntScreenWidth();
//        ViewGroup.LayoutParams layoutParams = holder.imgIcon.getLayoutParams();
//        if(maxSize>3){
////            layoutParams.height = layoutParams.width = (screenWidth - DipToPx.dip2px(mContext, 20)) / 3 ;
//        }else{
//            layoutParams.height = layoutParams.width = (screenWidth - DipToPx.dip2px(mContext, 50)) / 4 ;
//        }

        if(position == mFiles.size()){
            holder.imgIcon.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.add_icon));
            holder.imgDel.setVisibility(View.GONE);
        }else{
            Bitmap bitmap = BitmapUtil.compressBitmap(mFiles.get(position), 720);
            holder.imgIcon.setImageBitmap(bitmap);
            holder.imgDel.setVisibility(View.VISIBLE);
            holder.imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFiles.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
        return convertView;
    }

    public void setData(ArrayList<String> selectFiles) {
        mFiles = selectFiles;
        notifyDataSetChanged();
    }

    class ViewHolder{
        ImageView imgIcon;
        ImageView imgDel;

        public ViewHolder(View view){
            imgIcon = (ImageView) view.findViewById(R.id.feed_back_icon);
            imgDel = (ImageView) view.findViewById(R.id.feed_back_del);
        }
    }
}
