package com.project.mgjandroid.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Goods;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.model.GoodsEvaluateModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.GoodsDetailListAdapter;
import com.project.mgjandroid.ui.view.HeaderViewPagerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends HeaderViewPagerFragment implements AbsListView.OnScrollListener {


    private ListView mListView;
    private GoodsDetailListAdapter mListAdapter;
    private int currentSection = 0;
    private int maxResults = 20;
    private ArrayList<GoodsEvaluateModel.ValueEntity> data;
    private Goods mGoods;
    private boolean hasMoreData = false;

    public GoodsDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        mListView = (ListView) view.findViewById(R.id.goods_detail_list_view);
        mListAdapter = new GoodsDetailListAdapter(R.layout.item_goods_detail_list_view, getActivity());
        View headerView = inflater.inflate(R.layout.header_view_goods_detail_list_view, null);
        TextView tvDes = (TextView) headerView.findViewById(R.id.goods_detail_content);
        mListView.addHeaderView(headerView);
        data = new ArrayList<>();
        mListView.setAdapter(mListAdapter);
        mListView.setOnScrollListener(this);

        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey("goods")){
            mGoods = (Goods) arguments.getSerializable("goods");
            tvDes.setText(mGoods.getDescription());
            getGoodsEvaluate();
        }

        return view;
    }

    @Override
    public View getScrollableView() {
        return mListView;
    }

    private void getGoodsEvaluate() {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsId", mGoods.getId());
        map.put("start", currentSection);
        map.put("size", maxResults);
        VolleyOperater<GoodsEvaluateModel> operater = new VolleyOperater<>(getActivity());
        operater.doRequest(Constants.URL_GOODS_EVALUATE, map, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if (isSucceed && obj != null) {
                    GoodsEvaluateModel goodsEvaluateModel = (GoodsEvaluateModel) obj;
                    List<GoodsEvaluateModel.ValueEntity> list = goodsEvaluateModel.getValue();
                    if (list.size() < maxResults) {
                        hasMoreData = false;
                    } else {
                        hasMoreData = true;
                    }
                    data.addAll(list);
                    mListAdapter.setData(data);
//                    footerView.setVisibility(View.GONE);
//                    tvEvaluate.setText("商品评价(" + data.size() + ")");
                }
            }
        }, GoodsEvaluateModel.class);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(mListView.getLastVisiblePosition() == mListView.getCount() - 1){
            if(hasMoreData) {
                currentSection += maxResults;
                getGoodsEvaluate();
            }
        }
    }
}
