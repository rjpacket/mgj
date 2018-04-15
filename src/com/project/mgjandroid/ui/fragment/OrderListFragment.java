package com.project.mgjandroid.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.constants.ActRequestCode;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.ConfirmOrderModel;
import com.project.mgjandroid.model.DeleteOrderModel;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.model.SubmitOrderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.net.VolleyOperater.ResponseListener;
import com.project.mgjandroid.ui.activity.CommercialActivity;
import com.project.mgjandroid.ui.activity.ConfirmOrderActivity;
import com.project.mgjandroid.ui.activity.EvaluateActivity;
import com.project.mgjandroid.ui.activity.OnlinePayActivity;
import com.project.mgjandroid.ui.activity.OrderDetailActivity;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;
import com.project.mgjandroid.ui.adapter.OrderListAdapter;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;
import com.project.mgjandroid.utils.AnimatorUtils;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.CustomDialog;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.ta.utdid2.android.utils.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单列表
 *
 * @author jian
 */
public class OrderListFragment extends BaseFragment implements OnClickListener, CustomDialog.onBtnClickListener {
    //登录和联网状态
    private static final int ORDER_FRAGMRNT_NO_NET=400;
    private static final int ORDER_FRAGMRNT_NO_LOGIN=101;
    private static final int ORDER_FRAGMRNT_LOGIN=100;
    private static final int LOGIN_IN=200;
    private static final int RELOAD=1000;
    public static final int REFRESH = 2000;
    protected Activity mActivity;
    protected View view;
    private PullToRefreshListView listView;
    private RelativeLayout layoutTip;
    private ImageView imgClose;
    private OrderListAdapter adapter;
    protected boolean refreshFlag = true;
    private boolean isFirstIn = true;
    private static final int maxResults = 20;
    private int page = 0;
    private SimpleDateFormat sdf;

    private LinearLayout orderListStateAbnormal;
    private TextView orderListStateMsg,orderListStateDeal;
    private ImageView orderListStateImage;
    //当前状态判断 未登录/无网
    private static int state=ORDER_FRAGMRNT_NO_LOGIN;
    private CustomDialog dialog;
    private int deleteTag = -1;
    private View mNoDataView;
    private MLoadingDialog mLoadingDialog;
    private static OrderListFragment fragment;
    private String errorMsg;
    private MLoadingDialog loadingDialog;
    private JSONObject previewJsonData;
    private CustomDialog goodsDialog;

    public static OrderListFragment newInstance(){
        if(fragment == null){
            fragment = new OrderListFragment();
        }
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.order_list_fragment, container, false);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initDialog();
        initViews();
        checkState();
        return view;
    }

    private void initDialog() {
        dialog = new CustomDialog(mActivity, this, "确定", "取消", "提示", "确定删除吗？");
        mLoadingDialog = new MLoadingDialog();
    }

    private void checkState() {
        orderListStateAbnormal.setVisibility(View.GONE);
        if(!App.isLogin()){//未登录
            state=ORDER_FRAGMRNT_NO_LOGIN;
            orderListStateAbnormal.setVisibility(View.VISIBLE);
            orderListStateImage.setImageResource(R.drawable.has_no_login);
            orderListStateMsg.setText("您还未登录");
            orderListStateDeal.setText("登录/注册");
        }else{
            if(!NetworkUtils.isConnected(mActivity)){
                state=ORDER_FRAGMRNT_NO_NET;
                orderListStateAbnormal.setVisibility(View.VISIBLE);
                orderListStateImage.setImageResource(R.drawable.has_no_net);
                orderListStateMsg.setText("未能连接到互联网");
                orderListStateDeal.setText("刷新重试");
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (isFirstIn) {
//            getDate(false);
//        }
//        isFirstIn = false;
    }

    private void initViews() {
        loadingDialog = new MLoadingDialog();
        layoutTip = (RelativeLayout) view.findViewById(R.id.orderlist_fragment_layout_tips);
        layoutTip.setOnClickListener(this);
        imgClose = (ImageView) view.findViewById(R.id.orderlist_fragment_close);
        imgClose.setOnClickListener(this);
        orderListStateAbnormal = (LinearLayout) view.findViewById(R.id.orderlist_fragment_state_abnormal);
        orderListStateMsg= (TextView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_msg);
        orderListStateDeal= (TextView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_deal);
        orderListStateImage= (ImageView) orderListStateAbnormal.findViewById(R.id.orderlist_fragment_state_img);
        orderListStateDeal.setOnClickListener(this);
        listView = (PullToRefreshListView) view.findViewById(R.id.orderlist_fragment_listView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
//        listView.setAddMoreCountText(maxResults);
        adapter = new OrderListAdapter(mActivity, this);
        listView.setAdapter(adapter);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshFlag) {
                    page = 0;
                    getDate(false);
                }
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshFlag) {
                    page += maxResults;
                    getDate(true);
                }
            }

        });
        mNoDataView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_list_no_data, null);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RELOAD:
                    orderListStateAbnormal.setVisibility(View.GONE);
                    refreshList();
                    break;
            }
            adapter.notifyDataSetChanged();
        }
    };

    private int getTimeBetween(String createTime) {

        try {
            Date date = sdf.parse(createTime);
            long time = date.getTime();
            return (int) ((time + 15 * 60 * 1000) - System.currentTimeMillis()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void getDate(final boolean isLoadMore) {
        refreshFlag = false;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", page);
        map.put("size", maxResults);
        VolleyOperater<OrderFragmentModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_GET_ORDER_LIST, map, new ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                listView.onRefreshComplete();
                refreshFlag = true;
                if (isSucceed && obj != null) {
                    if(obj instanceof String){
                        if(App.isLogin()) {
                            ToastUtils.displayMsg((String) obj, mActivity);
                        }
                        return;
                    }
                    OrderFragmentModel orderFragmentModel = (OrderFragmentModel) obj;
                    ArrayList<OrderFragmentModel.ValueEntity> mlist = new ArrayList<>();
                    mlist.addAll(orderFragmentModel.getValue());
                    initServerTime(mlist,orderFragmentModel.getServertime());
                    if (CheckUtils.isNoEmptyList(mlist)) {
                        listView.removeView(mNoDataView);
                        if (isLoadMore) {
                            if (mlist.size() < maxResults) {
                                ToastUtils.displayMsg("到底了", mActivity);
                            }
                            ArrayList<OrderFragmentModel.ValueEntity> mlistOrg = adapter.getList();
                            if (mlistOrg != null) {
                                mlistOrg.addAll(mlist);
                                adapter.setList(mlistOrg);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            adapter.setList(mlist);
                            adapter.notifyDataSetChanged();
                            AnimatorUtils.fadeFadeIn(listView, mActivity);
                        }
                    } else {
                        if (isLoadMore) {
                            ToastUtils.displayMsg("到底了", mActivity);
                        }else{
                            adapter.setList(mlist);
                            listView.setEmptyView(mNoDataView);
                        }
                    }
                } else {

                }
            }
        }, OrderFragmentModel.class);
    }

    private void initServerTime(ArrayList<OrderFragmentModel.ValueEntity> mlist, String servertime) {
        for (OrderFragmentModel.ValueEntity value : mlist) {
            value.setServerTime(servertime);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
//        if(isVisibleToUser){
//            refreshList();
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case MineFragment.LOGIN_IN_SUCCESS:
                state=ORDER_FRAGMRNT_LOGIN;
                orderListStateAbnormal.setVisibility(View.GONE);
                refreshList();
                break;
        }
        if(requestCode == REFRESH){
            refreshList();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.orderlist_fragment_layout_tips:

                break;
            case R.id.orderlist_fragment_close:
                layoutTip.setVisibility(View.GONE);
                break;
            case R.id.order_list_item_img_delet:
                //删除订单
                deleteTag = (int) v.getTag();
                dialog.show();

                break;
            case R.id.orderlist_fragment_state_deal:
                checkState();
                if(state==ORDER_FRAGMRNT_NO_LOGIN)
                    startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), LOGIN_IN);
                else if(state==ORDER_FRAGMRNT_NO_NET){
                    if(NetworkUtils.isConnected(mActivity))
                        return;
                    mHandler.sendEmptyMessage(RELOAD);
                }
                break;
            case R.id.order_state_go_pay:
                int tag1 = (int) v.getTag();
                OrderFragmentModel.ValueEntity valueEntity = adapter.getList().get(tag1);
                String paymentExpireTime = valueEntity.getPaymentExpireTime();
                try {
                    if(System.currentTimeMillis() > sdf.parse(paymentExpireTime).getTime() + 15 * 60 * 1000){
                        ToastUtils.displayMsg("订单已取消",mActivity);
                    } else {
                        Intent intent = new Intent(mActivity, OnlinePayActivity.class);
                        intent.putExtra("orderId", valueEntity.getId());
                        startActivityForResult(intent, REFRESH);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.order_state_more_one:
                int tag2 = (int) v.getTag();
                OrderFragmentModel.ValueEntity valueEntity1 = adapter.getList().get(tag2);
                HashMap<String,Object> map = new HashMap<>();
//                map.put("merchantId", valueEntity1.getMerchant().getId());
//                ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
//                List<OrderFragmentModel.ValueEntity.OrderItemsEntity> items = valueEntity1.getOrderItems();
//                for (OrderFragmentModel.ValueEntity.OrderItemsEntity item : items) {
//                    HashMap<String, Object> m = new HashMap<>();
//                    m.put("id", item.getGoodsId());
//                    m.put("quantity", item.getQuantity());
//                    m.put("specId", item.getGoodsSpecId());
//                    orderItems.add(m);
//                }
//                map.put("orderItems", orderItems);
                map.put("loginToken", App.getUserInfo().getToken());
                map.put("userId", App.getUserInfo().getId());
                map.put("orderId", valueEntity1.getId());
                double latitude = Double.parseDouble(PreferenceUtils.getLocation(getActivity())[0]);
                double longitude = Double.parseDouble(PreferenceUtils.getLocation(getActivity())[1]);
                map.put("longitude", longitude);
                map.put("latitude", latitude);
                VolleyOperater<ConfirmOrderModel> operaterOnce = new VolleyOperater<>(getActivity());
                loadingDialog.show(getActivity().getFragmentManager(), "");
                operaterOnce.doRequest(Constants.URL_AGAIN_ORDER_PREVIEW, map, new VolleyOperater.ResponseListener() {
                    @Override
                    public void onRsp(boolean isSucceed, Object obj) {
                        if (isSucceed && obj != null) {
                            if (obj instanceof String) {
                                errorMsg = (String) obj;
                                ToastUtils.displayMsg(errorMsg, mActivity);
                            } else {
                                ConfirmOrderModel confirmOrderModel = (ConfirmOrderModel) obj;
                                if (confirmOrderModel.isSuccess()) {
                                    Integer type = confirmOrderModel.getValue().getAgainOrderStatus();
                                    if(type != null && type == 0){//商家已下线
                                        ToastUtils.displayMsg(confirmOrderModel.getValue().getAgainOrderTip(), mActivity);
                                    }else if(type != null && type == 1){//商品已下线
                                        final int id = (int) confirmOrderModel.getValue().getMerchantId();
                                        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> list = confirmOrderModel.getValue().getOrderItems();
                                        ArrayList<Map<String, Object>> map = new ArrayList<>();
                                        for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : list) {
                                            HashMap<String, Object> m = new HashMap<>();
                                            m.put("id", goods.getId());
                                            m.put("quantity", goods.getQuantity());
                                            m.put("specId", goods.getSpecId());
                                            m.put("categoryId", goods.getCategoryId());
                                            map.add(m);
                                        }
                                        Map<String, Object> jsonMap = new HashMap<>();
                                        jsonMap.put("goodsJson",map);
                                        final JSONObject goodsJson = new JSONObject(jsonMap);
                                        goodsDialog = new CustomDialog(mActivity, new CustomDialog.onBtnClickListener() {
                                            @Override
                                            public void onSure() {
                                                Intent intent1 = new Intent(mActivity, CommercialActivity.class);
                                                intent1.putExtra(CommercialActivity.MERCHANT_ID,id);
                                                intent1.putExtra("againOrder",true);
                                                intent1.putExtra("onceMoreOrder", goodsJson);
                                                startActivityForResult(intent1, REFRESH);
                                                goodsDialog.dismiss();
                                            }

                                            @Override
                                            public void onExit() {
                                                goodsDialog.dismiss();
                                            }
                                        },"好的","知道了","提示",confirmOrderModel.getValue().getAgainOrderTip());
                                        goodsDialog.show();
                                    }else{
                                        List<ConfirmOrderModel.ValueEntity.OrderItemsEntity> pickGoods = confirmOrderModel.getValue().getOrderItems();
                                        String caution = confirmOrderModel.getValue().getCaution();
                                        ArrayList<Map<String, Object>> orderItems = new ArrayList<>();
                                        for (ConfirmOrderModel.ValueEntity.OrderItemsEntity goods : pickGoods) {
                                            HashMap<String, Object> m = new HashMap<>();
                                            m.put("id", goods.getId());
                                            m.put("quantity", goods.getQuantity());
                                            m.put("specId", goods.getSpecId());
                                            orderItems.add(m);
                                        }
                                        Map<String, Object> jsonMap = new HashMap<>();
                                        jsonMap.put("merchantId", confirmOrderModel.getValue().getMerchantId());
                                        jsonMap.put("loginToken", App.getUserInfo().getToken());
                                        jsonMap.put("userId", App.getUserInfo().getId());
                                        jsonMap.put("orderItems", orderItems);
                                        previewJsonData = new JSONObject(jsonMap);
                                        Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
                                        intent.putExtra("confirmOrderModel", confirmOrderModel);
                                        intent.putExtra("onceMoreOrder", previewJsonData);
                                        if(caution != null && !caution.isEmpty()) {
                                            intent.putExtra("caution", caution);
                                        }
                                        startActivityForResult(intent, ActRequestCode.GOODS_DETAIL);
                                    }
                                } else {
                                    ToastUtils.displayMsg("商户加载失败", mActivity);
                                }
                            }
                        }
                        loadingDialog.dismiss();
                    }
                }, ConfirmOrderModel.class);
//                Intent intent = new Intent(mActivity, ConfirmOrderActivity.class);
//                intent.putExtra("onceMoreOrder",map);
//                startActivityForResult(intent, REFRESH);
                break;
            case R.id.order_state_evaluate:
                mLoadingDialog.show(mActivity.getFragmentManager(),"");
                int tagEvaluate = (int) v.getTag();
                final OrderFragmentModel.ValueEntity valueEntityEvaluate = adapter.getList().get(tagEvaluate);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("orderId", valueEntityEvaluate.getId());
                VolleyOperater<SubmitOrderModel> operater = new VolleyOperater<SubmitOrderModel>(mActivity);
                operater.doRequest(Constants.URL_ORDER_DETAIL, map1, new ResponseListener() {

                    @Override
                    public void onRsp(boolean isSucceed, Object obj) {
                        if (isSucceed) {
                            SubmitOrderModel submitOrderModel= (SubmitOrderModel) obj;
                            Intent intentEvaluate = new Intent(mActivity, EvaluateActivity.class);
                            if(submitOrderModel.getValue().getDeliveryTask()!=null) {
                                intentEvaluate.putExtra("hasDriver",true);
                            }else{
                                intentEvaluate.putExtra("hasDriver",false);
                            }
                            intentEvaluate.putExtra("orderId", valueEntityEvaluate.getId());
                            intentEvaluate.putExtra("valueEntity", valueEntityEvaluate);
                            startActivityForResult(intentEvaluate, REFRESH);
                        }
                        mLoadingDialog.dismiss();
                    }
                }, SubmitOrderModel.class);
                break;
            case R.id.order_list_item_tv_name:
            case R.id.order_list_right_arrow:

                break;

            case R.id.go_to_detail:
            case R.id.order_state_confirm:
                int position = (int) v.getTag();
                OrderFragmentModel.ValueEntity order = adapter.getList().get(position);
                if (order != null) {
                    String orderId = String.valueOf(order.getId());
                    Intent intentDetail = new Intent(mActivity, OrderDetailActivity.class);
                    intentDetail.putExtra(OrderDetailActivity.ORDER_ID, orderId);
                    intentDetail.putExtra(OrderDetailActivity.ORDER_LIST_ENTITY, order);
                    startActivityForResult(intentDetail,REFRESH);
                    mActivity.overridePendingTransition(R.anim.common_in_from_right, R.anim.common_out_to_left);
                }
                break;

            case R.id.order_list_item_img_father:
                int tag = (int) v.getTag();
                OrderFragmentModel.ValueEntity valueEntity2 = adapter.getList().get(tag);
                Intent intent1 = new Intent(mActivity, CommercialActivity.class);
                intent1.putExtra(CommercialActivity.MERCHANT_ID,valueEntity2.getMerchantId());
                startActivityForResult(intent1, REFRESH);
                break;

            default:
                break;
        }
    }

    /**
     * 删除订单
     *
     * @param
     */
    private void deleteOrder() {
        OrderFragmentModel.ValueEntity valueEntity = adapter.getList().get(deleteTag);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", valueEntity.getId());
        VolleyOperater<DeleteOrderModel> operater = new VolleyOperater<DeleteOrderModel>(mActivity);
        operater.doRequest(Constants.URL_DELETE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    DeleteOrderModel deleteOrderModel = (DeleteOrderModel) obj;
                    if (deleteOrderModel.isSuccess()) {
                        adapter.getList().remove(deleteTag);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, DeleteOrderModel.class);
    }

    public void refreshList() {
        checkState();
        if (listView != null) {
            listView.resetCurrentMode();
            listView.setRefreshing(true);
        }
    }

    @Override
    public void onSure() {
        deleteOrder();
        dialog.dismiss();
    }

    @Override
    public void onExit() {
        dialog.dismiss();
    }
}
