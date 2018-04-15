package com.project.mgjandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.ui.activity.AddAddressActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ning on 2016/3/12.
 */
public class AddressManagerListAdapter extends BaseAdapter {
    public static final int EDIT_ADDRESS = 606;
    public static final int EDITED_ADDRESS = 607;
    private Activity context;
    private LayoutInflater mInflater;
    private List<UserAddress> list;
    private boolean canModify = true;
    private boolean isPickAddress = false;
    private long addressId = 0;

    public AddressManagerListAdapter(Activity context) {
        this.context = context;
        this.list = new ArrayList<UserAddress>();
        this.mInflater = LayoutInflater.from(context);
    }

    public List<UserAddress> getList() {
        return list;
    }

    public void setList(List<UserAddress> list) {
        this.list = list;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public void setIsPickAddress(boolean isPickAddress, long addressId) {
        this.isPickAddress = isPickAddress;
        this.addressId = addressId;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_address_manager_list_view, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.address_name);
            holder.tvGender = (TextView) convertView.findViewById(R.id.address_sex);
            holder.tvMobile = (TextView) convertView.findViewById(R.id.address_mobile);
            holder.tvAddress = (TextView) convertView.findViewById(R.id.address_description);
            holder.ivModify = (ImageView) convertView.findViewById(R.id.address_iv_edit);
            holder.ivPick = (ImageView) convertView.findViewById(R.id.address_iv_choose);
            holder.llUnreach = (LinearLayout) convertView.findViewById(R.id.ll_address_unreach);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final UserAddress userAddress = list.get(position);
        holder.tvName.setText(userAddress.getName());
        holder.tvGender.setText(userAddress.getGender());
        String optionPhone = "";
        if(!TextUtils.isEmpty(userAddress.getBackupMobile())){
            optionPhone = "," + userAddress.getBackupMobile();
        }
        holder.tvMobile.setText(userAddress.getMobile() + optionPhone);
        holder.tvAddress.setText(userAddress.getAddress() + " " + userAddress.getHouseNumber());
        if(canModify){
            holder.ivModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddAddressActivity.class);
                    intent.putExtra("USER_ADDRESS", userAddress);
                    context.startActivityForResult(intent, EDIT_ADDRESS);
                }
            });
        }else{
            holder.ivModify.setVisibility(View.GONE);
        }
        if(isPickAddress){
            if(userAddress.getOverShipping()==0){
                holder.ivPick.setVisibility(View.VISIBLE);
                holder.llUnreach.setVisibility(View.GONE);
                holder.ivModify.setVisibility(View.VISIBLE);
                if(userAddress.getId() == addressId){
                    holder.ivPick.setImageResource(R.drawable.check_on);
                }else{
                    holder.ivPick.setImageResource(R.drawable.check_off);
                }
            }else {
                holder.ivPick.setVisibility(View.INVISIBLE);
                holder.llUnreach.setVisibility(View.VISIBLE);
                holder.ivModify.setVisibility(View.GONE);
            }
        }else{
            holder.ivPick.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvName, tvGender, tvMobile, tvAddress;
        ImageView ivModify, ivPick;
        LinearLayout llUnreach;
    }

}