package com.project.mgjandroid.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.mgjandroid.R;
import com.project.mgjandroid.inter_face.AreaClick;
import com.project.mgjandroid.ui.activity.ChooseCityActivity;
import com.project.mgjandroid.ui.adapter.ProvinceChooseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvinceFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView mListView;

    private static ProvinceFragment fragment;
    private AreaClick mListener;
    private ProvinceChooseListAdapter mAdapter;

    public ProvinceFragment() {
        // Required empty public constructor
    }

    public static ProvinceFragment newInstance(){
        if(fragment == null){
            fragment = new ProvinceFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_province, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.province_list_view);
        mAdapter = new ProvinceChooseListAdapter(R.layout.item_common_choose_area, mActivity);
        mAdapter.setData(ChooseCityActivity.mProvinces);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onAreaClick(0, position);
    }


    public void setListener(AreaClick listener) {
        mListener = listener;
    }

    public void notifyList() {
        mAdapter.setData(ChooseCityActivity.mProvinces);
    }
}
