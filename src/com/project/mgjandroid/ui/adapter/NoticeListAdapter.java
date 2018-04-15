package com.project.mgjandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.model.NoticeModel;
import com.project.mgjandroid.utils.CheckUtils;

import java.util.ArrayList;
import java.util.List;

import static com.project.mgjandroid.R.id.notice_item_order_layout;

/**
 * Created by User_Cjh on 2016/3/26.
 */
public class NoticeListAdapter extends BaseAdapter{

    private List<NoticeModel> list;
    private Context context;

    public NoticeListAdapter(Context context){
        this.context=context;
    }

    public void setList(ArrayList<NoticeModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item_notice_list,null);
            holder= new ViewHolder();
            holder.typePrompt= (TextView) view.findViewById(R.id.notice_item_type_prompt);
            holder.firstText= (TextView) view.findViewById(R.id.notice_item_first_text);
            holder.orderLayout= (LinearLayout) view.findViewById(notice_item_order_layout);
            holder.time= (TextView) view.findViewById(R.id.notice_item_time);
            holder.orderNum= (TextView) view.findViewById(R.id.notice_item_order_num);
            holder.icon= (ImageView) view.findViewById(R.id.notice_item_icon);
            holder.readMark= (ImageView) view.findViewById(R.id.notice_item_read_mark);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
//        等待接口数据再进行此处的操作
//        if (CheckUtils.isNoEmptyList(list) && list.size() > i) {
//            showItem(view, list.get(i), holder);
//        }
        //模拟展示
        show(i,view,holder);
        return view;
    }

    private void show(int i,View view,ViewHolder holder) {
        switch (i){
            case 0:
                holder.icon.setImageResource(R.drawable.notice_order);
                holder.readMark.setVisibility(View.GONE);
                holder.typePrompt.setText("您成功取消了订单");
                holder.firstText.setText("您在[机不可失（交大店）]下的订单");
                holder.orderNum.setText("12345678987456321");
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.notice_order);
                holder.readMark.setVisibility(View.VISIBLE);
                holder.typePrompt.setText("您成功取消了订单");
                holder.firstText.setText("您在[机不可失（交大店）]下的订单");
                holder.orderNum.setText("00000000000000000");
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.notice_redbag);
                holder.readMark.setVisibility(View.GONE);
                holder.typePrompt.setText("您获得一个10.0元天降红包");
                holder.orderLayout.setVisibility(View.GONE);
                holder.firstText.setText("红包金额10.0，在线支付可抵折扣餐费。（点击查看详情）");
                break;
        }
    }

    public static class ViewHolder{
        public TextView typePrompt,firstText,time,orderNum;
        public ImageView icon,readMark;
        public LinearLayout orderLayout;
    }
}
