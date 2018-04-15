package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.OrderEvaluateModel;
import com.project.mgjandroid.model.OrderFragmentModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.view.MLoadingDialog;
import com.project.mgjandroid.ui.view.RatingBarView;
import com.project.mgjandroid.ui.view.WheelView;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.ImageUtils;
import com.project.mgjandroid.utils.ToastUtils;
import com.project.mgjandroid.utils.ViewFindUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener, RatingBarView.OnRatingListener, View.OnFocusChangeListener {
    @InjectView(R.id.evaluate_back)
    private ImageView evaluateBack;
    @InjectView(R.id.to_evaluate)
    private TextView tvEvaluate;
    @InjectView(R.id.evaluate_list_view)
    private ScrollView mScrollView;
    @InjectView(R.id.all_text_view)
    private EditText etAllEvaluate;
    @InjectView(R.id.driver_text_view)
    private EditText etDriverEvaluate;
    @InjectView(R.id.all_rat_score)
    private RatingBarView rbAll;
    @InjectView(R.id.driver_rat_score)
    private RatingBarView rbDriver;
    @InjectView(R.id.evaluate_container)
    private LinearLayout itemContainer;
    private String orderId;
    private WheelView wheelView;
    private TextView tvSelected;

    private OrderFragmentModel.ValueEntity valueEntityEvaluate;
    @InjectView(R.id.evaluate_merchant_name)
    private TextView evaluateMerchantName;
    @InjectView(R.id.evaluate_icon)
    private ImageView evaluateMerchantIcon;
    @InjectView(R.id.driver_evaluate_layout)
    private RelativeLayout evaluateDriverLayout;
    private List<OrderFragmentModel.ValueEntity.OrderItemsEntity> orderItems;

    private List<Map<String,Object>> list;
    private int diliveryCost = 10;

    private boolean hasDriverEvaluate=true;
    private int prePosition = -1;
    private String preContent;
    private List<EditText> editTexts;
    private MLoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        Injector.get(this).inject();
        initView();
    }

    private void initView() {
        loadingDialog = new MLoadingDialog();
        editTexts = new ArrayList<>();
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("orderId")){
            orderId = intent.getStringExtra("orderId");
            valueEntityEvaluate= (OrderFragmentModel.ValueEntity) intent.getSerializableExtra("valueEntity");
            orderItems=valueEntityEvaluate.getOrderItems();
            hasDriverEvaluate=intent.getBooleanExtra("hasDriver",false);
        }

        evaluateBack.setOnClickListener(this);
        tvEvaluate.setOnClickListener(this);
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());
//        data.add(new Entity());

        if (!TextUtils.isEmpty(valueEntityEvaluate.getMerchant().getLogo()))
            ImageUtils.loadBitmap(this,valueEntityEvaluate.getMerchant().getLogo(), evaluateMerchantIcon, R.drawable.ic_launcher , Constants.PRIMARY_CATEGORY_IMAGE_URL_END_THUMBNAIL_USER);
        evaluateMerchantName.setText(valueEntityEvaluate.getMerchant().getName());
        if(hasDriverEvaluate)
            evaluateDriverLayout.setVisibility(View.VISIBLE);
        else{
            evaluateDriverLayout.setVisibility(View.GONE);
        }

//        rbAll.setOnRatingListener(this);
//        rbDriver.setOnRatingListener(this);
        setEvaluateListener();
        wheelView = ViewFindUtils.find(mDecorView,R.id.wheel_view);
        wheelView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        tvSelected = ViewFindUtils.find(mDecorView,R.id.time_select);
        tvSelected.setOnClickListener(this);
        wheelView.setOffset(1);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            list.add(showWheelViewTime(10 * i));
        }
        wheelView.setItems(list);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                diliveryCost = selectedIndex * 10;
                if (diliveryCost > 0) {
                    tvSelected.setText(item + "( " + getSongDaTime(diliveryCost).substring(11, 16) + "送达)");
                }
            }
        });

        for (int i = 0; i < orderItems.size(); i++) {
            View itemView = mInflater.inflate(R.layout.item_evaluate_list_view, null);
            TextView tvName = (TextView) itemView.findViewById(R.id.list_item_name);
            final RatingBarView rbvScore = (RatingBarView) itemView.findViewById(R.id.driver_rat_score);
            final EditText etContent = (EditText) itemView.findViewById(R.id.evaluate_edit_text);
            editTexts.add(etContent);
            OrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(i);
            tvName.setText(orderItemsEntity.getName());
            rbvScore.setRating(orderItemsEntity.getRating());
            rbvScore.setBindObject(i);
            etContent.setTag(i);
            etContent.setText(orderItemsEntity.getContent());
            etContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int tag = (int) v.getTag();
                    orderItems.get(tag).setContent(etContent.getText().toString().trim());
                }
            });
            rbvScore.setOnRatingListener(new RatingBarView.OnRatingListener() {
                @Override
                public void onRating(Object bindObject, int RatingScore, String content) {
                    if (etAllEvaluate.getVisibility() == View.VISIBLE) {
                        etAllEvaluate.setVisibility(View.GONE);
                    }
                    if (etDriverEvaluate.getVisibility() == View.VISIBLE) {
                        etDriverEvaluate.setVisibility(View.GONE);
                    }
                    if (wheelView.getVisibility() == View.VISIBLE) {
                        wheelView.setVisibility(View.GONE);
                    }

                    etContent.setVisibility(View.VISIBLE);
                    etContent.setSelection(etContent.length());
                    etContent.requestFocus();
                    CommonUtils.showKeyBoard(etContent);
                    if (prePosition != -1 && prePosition != (int) bindObject) {
                        EditText editText = editTexts.get(prePosition);
                        editText.setVisibility(View.GONE);
                    }

                    if (RatingScore > 3) {
                        rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_weel));
                        etContent.setHint("说说哪里满意，帮大家选择");
                    } else if (RatingScore > 1) {
                        rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_normal));
                        etContent.setHint("说说哪里不满意，帮商家改进");
                    } else {
                        rbvScore.setStarFillDrawable(mActivity.getResources().getDrawable(R.drawable.evaluate_goods_bad));
                        etContent.setHint("说说哪里不满意，帮商家改进");
                    }
                    rbvScore.setStar(RatingScore);
                    orderItems.get((int) bindObject).setRating(RatingScore);
                    prePosition = (int) bindObject;
                }
            });
            itemContainer.addView(itemView);
        }
    }

    private String showWheelViewTime(int time) {
        if(time == 120){
            return "2小时以上";
        }else if(time > 60){
            return time / 60 + "小时" + time % 60 + "分钟";
        }else if(time == 60){
            return "1小时";
        }
        return time % 60 + "分钟";
    }

    private void setEvaluateListener() {
        rbAll.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore,String content) {
                if(wheelView.getVisibility() == View.VISIBLE){
                    wheelView.setVisibility(View.GONE);
                }
                if(prePosition != -1){
                    editTexts.get(prePosition).setVisibility(View.GONE);
                    prePosition = -1;
                }
                etAllEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.setVisibility(View.GONE);
                etAllEvaluate.setSelection(etAllEvaluate.length());
                etAllEvaluate.requestFocus();
                CommonUtils.showKeyBoard(etAllEvaluate);
                if(RatingScore>3){
                    etAllEvaluate.setHint("说说哪里满意，帮大家选择");
                }else if(RatingScore>1){
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }else{
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }
            }
        });
        rbDriver.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore,String content) {
                if(wheelView.getVisibility() == View.VISIBLE){
                    wheelView.setVisibility(View.GONE);
                }
                if(prePosition != -1){
                    editTexts.get(prePosition).setVisibility(View.GONE);
                    prePosition = -1;
                }
                etAllEvaluate.setVisibility(View.GONE);
                etDriverEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.requestFocus();
                etDriverEvaluate.setSelection(etDriverEvaluate.length());
                CommonUtils.showKeyBoard(etDriverEvaluate);
                if(RatingScore>3){
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                }else if(RatingScore>1){
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                }else{
                    etDriverEvaluate.setHint("说说你对骑手的印象");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(wheelView.getVisibility() == View.VISIBLE){
            wheelView.setVisibility(View.GONE);
        }
        switch (v.getId()){
            case R.id.evaluate_back:
                back();
                break;
            case R.id.to_evaluate:
                itemContainer.clearFocus();
                if(Float.compare(rbAll.getRating(),0.0f)<=0 || etAllEvaluate.getText().toString().trim().length() == 0) {
                    ToastUtils.displayMsg("未作总体评价", EvaluateActivity.this);
                    break;
                }
                if(hasDriverEvaluate) {
                    if (Float.compare(rbDriver.getRating(), 0.0f) <= 0) {
                        ToastUtils.displayMsg("未作骑士评价评价", EvaluateActivity.this);
                        break;
                    }
                }
                if(diliveryCost<10){
                    ToastUtils.displayMsg("请选择送达时间", EvaluateActivity.this);
                    break;
                }
                boolean isReady=true;
                for(int i=0;i<orderItems.size();i++){
                    if(orderItems.get(i).getRating() <= 0){
                        ToastUtils.displayMsg(orderItems.get(i).getName()+"未作评价", EvaluateActivity.this);
                        isReady=false;
                        break;
                    }
                }
                if(!isReady){
                    break;
                }
                evaluateOrder();
                break;
            case R.id.time_select:
                CommonUtils.hideKeyBoard(mActivity);
                tvSelected.setTextColor(getResources().getColor(R.color.orange_ff));
                tvSelected.setText("10分钟( " + getSongDaTime(diliveryCost).substring(11, 16) + "送达)");
                diliveryCost=0;
                wheelView.setVisibility(View.VISIBLE);
                break;

        }
    }

    private String getSongDaTime(int time) {
        String createTime = valueEntityEvaluate.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonUtils.yyyy_MM_dd_HH_mm_ss);
        try {
            long timeStr = sdf.parse(createTime).getTime() + time * 60 * 1000 ;
            return CommonUtils.formatTime(timeStr,CommonUtils.yyyy_MM_dd_HH_mm_ss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*@Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()){
            case R.id.all_rat_score:
                etAllEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.setVisibility(View.GONE);
                etAllEvaluate.setSelection(etAllEvaluate.length());
                etAllEvaluate.requestFocus();
                CommonUtils.showKeyBoard(etAllEvaluate);
                if(rating>3){
                    etAllEvaluate.setHint("说说哪里满意，帮大家选择");
                }else if(rating>1){
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }else{
                    etAllEvaluate.setHint("说说哪里不满意，帮商家改进");
                }
                break;
            case R.id.driver_rat_score:
                etAllEvaluate.setVisibility(View.GONE);
                etDriverEvaluate.setVisibility(View.VISIBLE);
                etDriverEvaluate.requestFocus();
                etDriverEvaluate.setSelection(etDriverEvaluate.length());
                CommonUtils.showKeyBoard(etDriverEvaluate);
                if(rating>3){
                    etDriverEvaluate.setHint("说说哪里满意，帮大家选择");
                }else if(rating>1){
                    etDriverEvaluate.setHint("说说哪里不满意，帮商家改进");
                }else{
                    etDriverEvaluate.setHint("说说哪里不满意，帮商家改进");
                }
                break;
        }
    }*/

    /**
     * 生成评价
     */
    private void evaluateOrder() {
        loadingDialog.show(getFragmentManager(), "");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("merchantScore", rbAll.getRating());
        map.put("merchantComments", etAllEvaluate.getText().toString().trim());
        map.put("deliveryCost", diliveryCost);
        map.put("deliverymanScore", rbDriver.getRating());
        map.put("deliverymanComments", etDriverEvaluate.getText().toString().trim());
        map.put("goodsComments", getGoodsEvaluate());
        VolleyOperater<OrderEvaluateModel> operater = new VolleyOperater<OrderEvaluateModel>(EvaluateActivity.this);
        operater.doRequest(Constants.URL_EVALUATE_ORDER, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed){
                    setResult(RESULT_OK,new Intent());
                    CommonUtils.hideKeyBoard(EvaluateActivity.this);
                    finish();
                }
                loadingDialog.dismiss();
            }
        }, OrderEvaluateModel.class);
    }

    public String getGoodsEvaluate() {
        list= new ArrayList<>();
        for(int i=0;i<orderItems.size();i++){
            Map<String,Object> map = new HashMap<>();
            map.put("goodsId",orderItems.get(i).getGoodsId());
            map.put("goodsScore",orderItems.get(i).getRating());
            map.put("goodsComments",orderItems.get(i).getContent());
            list.add(map);
        }
        return JSON.toJSONString(list);
    }

    @Override
    public void onRating(Object bindObject, int RatingScore,String content) {
//        if(prePosition != -1) {
//            orderItems.get(prePosition).setContent(preContent);
//        }
        if(etAllEvaluate.getVisibility() == View.VISIBLE){
            etAllEvaluate.setVisibility(View.GONE);
        }
        if(etDriverEvaluate.getVisibility() == View.VISIBLE){
            etDriverEvaluate.setVisibility(View.GONE);
        }
        if(wheelView.getVisibility() == View.VISIBLE){
            wheelView.setVisibility(View.GONE);
        }
        final int tag =(int) bindObject;

        for (OrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity : orderItems) {
            orderItemsEntity.setIsShow(false);
        }
        OrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(tag);
        orderItemsEntity.setIsShow(true);
        orderItemsEntity.setRating(RatingScore);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int tag =(int) v.getTag();
        OrderFragmentModel.ValueEntity.OrderItemsEntity orderItemsEntity = orderItems.get(tag);
        orderItemsEntity.setContent(((EditText) v).getText().toString().trim());
    }
}
