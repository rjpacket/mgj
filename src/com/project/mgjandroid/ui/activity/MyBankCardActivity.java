package com.project.mgjandroid.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.UserBankCardsModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.MyBankCardAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenu;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuCreator;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuItem;
import com.project.mgjandroid.ui.view.swipemenulistview.SwipeMenuListView;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.DipToPx;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuandi on 2016/5/24.
 */
public class MyBankCardActivity extends BaseActivity {

    @InjectView(R.id.my_bankcard_act_back)
    private ImageView ivBack;
    @InjectView(R.id.my_bankcard_act_listView)
    private SwipeMenuListView listView;
    @InjectView(R.id.tv_no_bank_card)
    private TextView tvNoBankCard;

    private MyBankCardAdapter adapter;
    private MLoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_card);
        Injector.get(this).inject();
        initView();
        getData();
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        dialog = new MLoadingDialog();
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mActivity);
                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.red_weibo)));
                deleteItem.setWidth(DipToPx.dip2px(mActivity, 60));
//                deleteItem.setIcon(R.drawable.address_icon_delete);
                deleteItem.setTitle(R.string.delete);
                deleteItem.setTitleColor(getResources().getColor(R.color.white));
                deleteItem.setTitleSize(16);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    doDeleteItem(position);
                }
                return false;
            }
        });
        adapter = new MyBankCardAdapter(R.layout.item_my_bank_card, mActivity);
        listView.setAdapter(adapter);
    }

    private void doDeleteItem(final int position) {
        dialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("id", adapter.getItem(position).getId());
        VolleyOperater<Object> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_DEL_USER_DRAW_CASH_BANK, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    adapter.getData().remove(position);
                    adapter.notifyDataSetChanged();
                    ToastUtils.displayMsg(R.string.delete_success, mActivity);
                    if (adapter.getData().size() == 0){
                        tvNoBankCard.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, Object.class);
    }

    private void getData() {
        dialog.show(getFragmentManager(), "");
        VolleyOperater<UserBankCardsModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_DRAW_CASH_BANK_LIST, null, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                dialog.dismiss();
                if(isSucceed && obj!=null){
                    if (obj instanceof String) {
                        ToastUtils.displayMsg(obj.toString(), mActivity);
                        return;
                    }
                    UserBankCardsModel userBankCardsModel = (UserBankCardsModel) obj;
                    if(userBankCardsModel.getCode() == 0 && CheckUtils.isNoEmptyList(userBankCardsModel.getValue())) {
                        adapter.setData(userBankCardsModel.getValue());
                    }else {
                        tvNoBankCard.setVisibility(View.VISIBLE);
                    }
                }
            }
        }, UserBankCardsModel.class);
    }
}
