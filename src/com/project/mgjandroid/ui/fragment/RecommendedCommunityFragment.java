package com.project.mgjandroid.ui.fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.CommunityBean;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RecommendedCommunityModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.MyCommunityListAdapter;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshBase;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedCommunityFragment extends BaseFragment implements View.OnClickListener {


    private PopupWindow mPopupWindow;
    private View mTopLine;
    private ImageView mImgMoreTag;
    private TextView mTvMoreText;
    private int MAX_SIZE = 20;
    private int mCurrentPosition = 0;
    private PopupWindow mSharePopupWindow;
    private MyCommunityListAdapter mMyCommunityListAdapter;
    private PullToRefreshListView mMyCommunityListView;

    public RecommendedCommunityFragment() {
        // Required empty public constructor
    }

    private static RecommendedCommunityFragment fragment;

    public static RecommendedCommunityFragment newInstance(){
        if(fragment == null){
            fragment = new RecommendedCommunityFragment();
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recommended_community, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mMyCommunityListView = (PullToRefreshListView) view.findViewById(R.id.recommended_community_list_view);
        mMyCommunityListAdapter = new MyCommunityListAdapter(R.layout.item_my_community_list_view, mActivity , this , false);
        mMyCommunityListView.setAdapter(mMyCommunityListAdapter);

        LinearLayout llMoreTag = (LinearLayout) view.findViewById(R.id.com_more_tag);
        mImgMoreTag = (ImageView) view.findViewById(R.id.com_more_tag_down);
        mTvMoreText = (TextView) view.findViewById(R.id.com_more_text);
        llMoreTag.setOnClickListener(this);
        mTopLine = view.findViewById(R.id.com_gray_line);
        initPopupWindow();

        getRecommendedList(-1);

        mMyCommunityListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                getRecommendedList(-1);
            }

            @Override
            public void onPullDownValue(PullToRefreshBase<ListView> refreshView, int value) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
    }

    private void initPopupWindow() {
        View view = mInflater.inflate(R.layout.layout_recommended_community_popup, null);
        mPopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
    }

    private void initSharePopupWindow(){
        View view = mInflater.inflate(R.layout.layout_setting_popup_window, null);
        mSharePopupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        ColorDrawable colorDrawable = new ColorDrawable(0x000000);
        mSharePopupWindow.setBackgroundDrawable(colorDrawable);
        mSharePopupWindow.setOutsideTouchable(true);
        mSharePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1f);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.com_more_tag:
                imageAnimation();
                break;

            case R.id.com_zan:

                break;

            case R.id.com_more:
                showSharePopupWindow(v);
                break;

            case R.id.com_content_icon:
                int tag = (int) v.getTag();
                CommunityBean communityBean = mMyCommunityListAdapter.getData().get(tag);
                communityBean.setIsOpen(!communityBean.isOpen());
                mMyCommunityListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void showSharePopupWindow(View view) {
        if(mSharePopupWindow == null){
            initSharePopupWindow();
            showSharePopupWindow(view);
        }else{
            if(mSharePopupWindow.isShowing()){
                mSharePopupWindow.dismiss();
            }else{
                mSharePopupWindow.showAsDropDown(view);
                setAlpha(0.5f);
            }
        }
    }

    private void setAlpha(float v) {
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.alpha = v;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    private void imageAnimation() {
        Animation animation;
        if(!mPopupWindow.isShowing()) {
            animation = AnimationUtils.loadAnimation(mActivity, R.anim.com_rotate_0_to_180);
        }else{
            animation = AnimationUtils.loadAnimation(mActivity, R.anim.com_ratate_180_to_360);
        }
        mImgMoreTag.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!mPopupWindow.isShowing()) {
                    mImgMoreTag.setImageResource(R.drawable.com_open);
                    mTvMoreText.setTextColor(getResources().getColor(R.color.org_yellow));
                } else {
                    mImgMoreTag.setImageResource(R.drawable.com_close);
                    mTvMoreText.setTextColor(getResources().getColor(R.color.mine_number_color_grey));
                }
                showPopupWindow();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showPopupWindow() {
        if(mPopupWindow == null){
            initPopupWindow();
            showPopupWindow();
        }else{
            if(mPopupWindow != null && mPopupWindow.isShowing()){
                mPopupWindow.dismiss();
            }else{
                mPopupWindow.showAsDropDown(mTopLine);
            }
        }
    }

    public void hiddenPopupWindow(){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    /**
     * 获取推荐社区
     * @param categoryId 默认传-1
     */
    private void getRecommendedList(long categoryId) {
        Map<String, Object> map = new HashMap<>();
        if(categoryId != -1) {
            map.put("categoryId", categoryId);
        }
        map.put("start",mCurrentPosition);
        map.put("size",MAX_SIZE);
        VolleyOperater<RecommendedCommunityModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_ALL_BBS_INFOMATION_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                mMyCommunityListView.onRefreshComplete();
                if (isSucceed && obj != null) {
                    RecommendedCommunityModel recommendedCommunityModel = (RecommendedCommunityModel) obj;
                    mMyCommunityListAdapter.setData(recommendedCommunityModel.getValue());
                }
            }
        }, RecommendedCommunityModel.class);
    }
}
