package com.project.mgjandroid.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mgjandroid.R;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.RecommendedCommunityModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.MyCommunityListAdapter;
import com.project.mgjandroid.ui.view.newpulltorefresh.PullToRefreshListView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCommunityFragment extends BaseFragment implements View.OnClickListener {


    private int mCurrentPosition;
    private int MAX_SIZE = 20;
    private MyCommunityListAdapter mMyCommunityListAdapter;

    public MyCommunityFragment() {
        // Required empty public constructor
    }

    private static MyCommunityFragment fragment;

    public static MyCommunityFragment newInstance(){
        if(fragment == null){
            fragment = new MyCommunityFragment();
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_community, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        PullToRefreshListView myCommunityListView = (PullToRefreshListView) view.findViewById(R.id.my_community_list_view);
        mMyCommunityListAdapter = new MyCommunityListAdapter(R.layout.item_my_community_list_view, mActivity , this , true);
        myCommunityListView.setAdapter(mMyCommunityListAdapter);

        getMyList(-1);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取推荐社区
     * @param categoryId 默认传-1
     */
    private void getMyList(long categoryId) {
        Map<String, Object> map = new HashMap<>();
        if(categoryId != -1) {
            map.put("categoryId", categoryId);
        }
        map.put("start",mCurrentPosition);
        map.put("size",MAX_SIZE);
        VolleyOperater<RecommendedCommunityModel> operater = new VolleyOperater<>(mActivity);
        operater.doRequest(Constants.URL_FIND_USER_BBS_INFOMATION_LIST, map, new VolleyOperater.ResponseListener() {

            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    RecommendedCommunityModel recommendedCommunityModel = (RecommendedCommunityModel) obj;
                    mMyCommunityListAdapter.setData(recommendedCommunityModel.getValue());
                }
            }
        }, RecommendedCommunityModel.class);
    }
}
