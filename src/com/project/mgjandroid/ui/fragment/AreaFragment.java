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
import com.project.mgjandroid.ui.adapter.AreaChooseListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AreaFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private ListView mListView;
    private static AreaFragment fragment;
    private AreaClick mListener;
    private AreaChooseListAdapter mAdapter;

    public AreaFragment() {
        // Required empty public constructor
    }



    public static AreaFragment newInstance(){
        if(fragment == null){
            fragment = new AreaFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.area_list_view);
        mAdapter = new AreaChooseListAdapter(R.layout.item_common_choose_area, mActivity);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onAreaClick(2, position);
    }

    public void setListener(AreaClick listener) {
        mListener = listener;
    }

    public void notifyList() {
        if(mAdapter != null) {
            mAdapter.setData(ChooseCityActivity.mAreas);
        }
    }
}
