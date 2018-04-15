package com.project.mgjandroid.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.MerchantPickGoods;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.AddressManageModel;
import com.project.mgjandroid.model.DeleteAddressModel;
import com.project.mgjandroid.model.PickGoodsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.AddressManagerListAdapter;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenu;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuCreator;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuItem;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DeviceParameter;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressManageActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @InjectView(R.id.address_manage_act_back)
    private ImageView ivBack;
    @InjectView(R.id.address_manage_act_tv_add)
    private LinearLayout tvAddAddress;
    @InjectView(R.id.address_manage_act_listView)
    private SwipeMenuListView lvAddress;

    private AddressManagerListAdapter listAdapter;
    private long merchantId;
    private long userAddressId;
    private List<UserAddress> userAddressList;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.address_manage_act);
        Injector.get(this).inject();
        merchantId = getIntent().getLongExtra("MERCHANT_ID", -1);
        userAddressId = getIntent().getLongExtra("USER_ADDRESS_ID", -1);
        initView();
    }

    @Override
    protected void onResume(){
        super.onResume();
        getAddressList();
    }

    private void initView(){
        ivBack.setOnClickListener(this);
        tvAddAddress.setOnClickListener(this);
        listAdapter = new AddressManagerListAdapter(this);
        if(merchantId != -1) {
            listAdapter.setIsPickAddress(true, userAddressId);
        }
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red_1)));
                deleteItem.setWidth(DipToPx.dip2px(AddressManageActivity.this, 70));
//                deleteItem.setIcon(R.drawable.address_icon_delete);
                deleteItem.setTitle(R.string.delete);
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                deleteItem.setTitleSize(18);
                menu.addMenuItem(deleteItem);
            }
        };
        lvAddress.setMenuCreator(creator);
        lvAddress.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
//                    showDeleteDialog(position);
                    doDeleteAddress(position);
                }
                return false;
            }
        });
        lvAddress.setAdapter(listAdapter);
        lvAddress.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_manage_act_back:
                back();
                break;
            case R.id.address_manage_act_tv_add:
                Intent intent = new Intent(AddressManageActivity.this, AddAddressActivity.class);
                if(merchantId != -1){
                    intent.putExtra("MERCHANT_ID", merchantId);
                }
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getAddressList() {
        Map<String, Object> map = new HashMap<>();
        if(merchantId != -1){
            map.put("merchantId", merchantId);
        }
        VolleyOperater<AddressManageModel> operater = new VolleyOperater<>(AddressManageActivity.this);
        operater.doRequest(Constants.URL_GET_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed && obj!=null){
                    userAddressList = ((AddressManageModel) obj).getValue();
                    if(!CheckUtils.isNoEmptyList(userAddressList)){
                        userAddressList = new ArrayList<>();
                    }
                    listAdapter.setList(userAddressList);
                    listAdapter.notifyDataSetChanged();
                }
            }
        }, AddressManageModel.class);
    }

    private void doDeleteAddress(final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userAddressList.get(position).getId());
        VolleyOperater<DeleteAddressModel> operater = new VolleyOperater<>(AddressManageActivity.this);
        operater.doRequest(Constants.URL_DELETE_ADDRESS, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed && obj!=null){
                    DeleteAddressModel deleteAddressModel = (DeleteAddressModel) obj;
                    if(deleteAddressModel.getCode() == 0){
                        userAddressList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        ToastUtils.displayMsg(R.string.delete_success, AddressManageActivity.this);
                    }
                }
            }
        }, DeleteAddressModel.class);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(merchantId != -1){
            UserAddress userAddress = userAddressList.get(position);
            if(userAddress.getOverShipping()==0){
                Intent intent = new Intent();
                intent.putExtra("address", userAddress);
                setResult(ConfirmOrderActivity.RESPONSE_GET_ADDRESS, intent);
                back();
            }else{
                showDialog(userAddress);
            }
        }
    }

    private void showDialog(final UserAddress userAddress){
        final Dialog dialog = new Dialog(this, R.style.chooseSpecDialog);
        LinearLayout contentView = (LinearLayout) getLayoutInflater().inflate(R.layout.unreach_address_pick_dialog, null);
        TextView tvAddressChange = (TextView) contentView.findViewById(R.id.dialod_address_change);
        TextView btnConfirm = (TextView) contentView.findViewById(R.id.dialod_address_confirm);
        TextView btnCancel = (TextView) contentView.findViewById(R.id.dialod_address_cancel);

        String tip = getText(R.string.dialog_address_unreach_change).toString();
        tvAddressChange.setText(Html.fromHtml(String.format(tip, userAddress.getAddress())));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveAddressName(userAddress.getAddress(), AddressManageActivity.this);
                PreferenceUtils.saveAddressDes("", AddressManageActivity.this);
                PreferenceUtils.saveLocation(Double.toString(userAddress.getLatitude()), Double.toString(userAddress.getLongitude()), AddressManageActivity.this);
                Intent intent = new Intent(AddressManageActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setAction(HomeActivity.CHANGE_PICKED_ADDRESS);
                startActivity(intent);
                dialog.dismiss();
                PickGoodsModel.getInstance().setMerchantPickGoodsList(new ArrayList<MerchantPickGoods>());
                back();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView, new LinearLayout.LayoutParams(DeviceParameter.getIntScreenWidth() - DipToPx.dip2px(this, 40), LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
