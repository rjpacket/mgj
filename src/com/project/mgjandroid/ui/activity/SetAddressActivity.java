package com.project.mgjandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.project.mgjandroid.R;
import com.project.mgjandroid.model.BaiduGeocoderModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.LocalPoiListAdapter;
import com.project.mgjandroid.ui.adapter.LocalSuggestListAdapter;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuandi on 2016/3/12.
 */
public class SetAddressActivity extends BaseActivity implements View.OnClickListener{

    public static final int SUGGESTION_RESULT = 20;
    @InjectView(R.id.set_address_act_tv_cancel)
    private TextView tvCancel;
    @InjectView(R.id.set_address_act_iv_back)
    private ImageView ivBack;
    @InjectView(R.id.set_address_act_edt_search)
    private EditText etSearch;
    @InjectView(R.id.set_address_act_iv_delete)
    private ImageView ivDelete;
    @InjectView(R.id.set_address_act_map)
    private LinearLayout llMap;
    @InjectView(R.id.set_address_act_map_view)
    private MapView mapView;
    @InjectView(R.id.set_address_act_search)
    private FrameLayout flSearch;
    @InjectView(R.id.set_address_act_tv_search_fail)
    private TextView tvSearchFail;
    @InjectView(R.id.set_address_act_list_view)
    private ListView searchLV;
    @InjectView(R.id.set_address_act_location)
    private ImageView ivLocation;
    @InjectView(R.id.set_address_act_map_list_view)
    private ListView listView;

    private LocalPoiListAdapter adapter;
    private BaiduMap baiduMap;
    private LatLng locationPoint;
    private LocalSuggestListAdapter searchAdapter;
    private PoiSearch mPoiSearch;
    private BitmapDescriptor mCurrentMarker;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.set_address_act);
        Injector.get(this).inject();
        initView();
        mPoiSearch = PoiSearch.newInstance();
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.takeout_ic_map_location);
        baiduMap.setMyLocationEnabled(true);
        String[] lat = PreferenceUtils.getLocation(mActivity);
        if(lat != null) {
            locationCurrentSite(Double.parseDouble(lat[0]), Double.parseDouble(lat[1]));
            locationPoint = new LatLng(Double.parseDouble(lat[0]),Double.parseDouble(lat[1]));
            poiSearch(locationPoint);
        }
    }

    /**
     * 定位当前地位置
     */
    private void locationCurrentSite(double lat, double lon) {

        MyLocationData locData = new MyLocationData.Builder()
                .latitude(lat)
                .longitude(lon)
                .build();
        baiduMap.setMyLocationData(locData);

        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        baiduMap.setMyLocationConfigeration(config);
    }

    private void initView(){
        searchAdapter = new LocalSuggestListAdapter(this);
        searchLV.setAdapter(searchAdapter);
        searchLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SuggestionResult.SuggestionInfo poiInfo = ((LocalSuggestListAdapter) searchLV.getAdapter()).getList().get(position);
                Intent intent = new Intent();
                intent.putExtra("POI_INFO", poiInfo);
                setResult(SUGGESTION_RESULT, intent);
                back();
            }
        });

        adapter = new LocalPoiListAdapter(this);
        adapter.setIsMapSearch(true);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaiduGeocoderModel.ResultBean.PoisBean poiInfo = ((LocalPoiListAdapter) listView.getAdapter()).getList().get(position);
                Intent intent = new Intent();
                intent.putExtra("POI_INFO", poiInfo);
                setResult(RESULT_OK, intent);
                back();
            }
        });

        tvCancel.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivLocation.setOnClickListener(this);

        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llMap.setVisibility(View.GONE);
                    flSearch.setVisibility(View.VISIBLE);
                } else {
                    llMap.setVisibility(View.VISIBLE);
                    flSearch.setVisibility(View.GONE);
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    llMap.setVisibility(View.GONE);
                    flSearch.setVisibility(View.VISIBLE);
                    ivDelete.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.GONE);
                } else {
                    llMap.setVisibility(View.VISIBLE);
                    flSearch.setVisibility(View.GONE);
                    ivDelete.setVisibility(View.GONE);
                    tvCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    poiSearch(s.toString());
                }
            }
        });

        baiduMap = mapView.getMap();
        baiduMap.setMaxAndMinZoomLevel(20, 12);

        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            public void onMapStatusChangeStart(MapStatus status) {
            }

            public void onMapStatusChange(MapStatus status) {
            }

            public void onMapStatusChangeFinish(MapStatus status) {
                if (status != null) {
                    LatLng ll = status.target;
                    if (ll != null) {
                        poiSearch(ll);
                    }
                }

            }
        });

        hideBaiduMapChildView();
    }

    private void hideBaiduMapChildView(){
        // 隐藏缩放控件和比例尺
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);

        // 隐藏指南针
        UiSettings mUiSettings = baiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);

        // 删除百度地图logo
        mapView.removeViewAt(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_address_act_tv_cancel:
                if(etSearch.isFocusable()){
//                    etSearch.setFocusable(false);
                    flSearch.setVisibility(View.GONE);
                    llMap.setVisibility(View.VISIBLE);
                    CommonUtils.hideKeyBoard(SetAddressActivity.this);
                }else{
                    tvCancel.setVisibility(View.GONE);
                }
                break;
            case R.id.set_address_act_iv_back:
                back();
                break;
            case R.id.set_address_act_iv_delete:
                etSearch.setText("");
                break;
            case R.id.set_address_act_location:
                if(locationPoint!=null){
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(locationPoint, 16);
                    baiduMap.animateMapStatus(u);
                    poiSearch(locationPoint);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy(){
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
        }
        super.onDestroy();
    }

    private void poiSearch(LatLng ll) {
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=BE94604732413aefd52917b186d69f72&location=" + ll.latitude + "," + ll.longitude + "&output=json&pois=1";
        VolleyOperater<BaiduGeocoderModel> operater = new VolleyOperater<>(this);
        operater.doRequest(url, new VolleyOperater.ResponseListener() {
            @Override
            public void onRsp(boolean isSucceed, Object obj) {
                if(isSucceed && obj != null){
                    if(obj instanceof String) {
                        adapter.setList(new ArrayList<BaiduGeocoderModel.ResultBean.PoisBean>());
                        return;
                    }
                    BaiduGeocoderModel baiduGeocoderModel = (BaiduGeocoderModel) obj;
                    if(baiduGeocoderModel.getStatus() == 0){
                        List<BaiduGeocoderModel.ResultBean.PoisBean> poisBeans = new ArrayList<>();
                        //当前定位点设为第一项
                        BaiduGeocoderModel.ResultBean.PoisBean poisBean = new BaiduGeocoderModel.ResultBean.PoisBean();
                        poisBean.setAddr(baiduGeocoderModel.getResult().getFormatted_address());
                        if(baiduGeocoderModel.getResult().getSematic_description() != null){
                            poisBean.setName(baiduGeocoderModel.getResult().getSematic_description());
                        }else{
                            poisBean.setName("");
                        }
                        BaiduGeocoderModel.ResultBean.PoisBean.PointBean pointBean = new BaiduGeocoderModel.ResultBean.PoisBean.PointBean();
                        pointBean.setX(baiduGeocoderModel.getResult().getLocation().getLng());
                        pointBean.setY(baiduGeocoderModel.getResult().getLocation().getLat());
                        poisBean.setPoint(pointBean);

                        poisBeans.add(poisBean);
                        poisBeans.addAll(baiduGeocoderModel.getResult().getPois());
                        adapter.setList(poisBeans);
                    }
                } else {
                    adapter.setList(new ArrayList<BaiduGeocoderModel.ResultBean.PoisBean>());
                }
            }
        }, BaiduGeocoderModel.class);
    }

    private void poiSearch(String keyword) {
        SuggestionSearch mSearch = SuggestionSearch.newInstance();
        mSearch.setOnGetSuggestionResultListener(suggestionListener);
        String latitude = PreferenceUtils.getLocation(mActivity)[0];
        String longitude = PreferenceUtils.getLocation(mActivity)[1];
        LatLng lng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        SuggestionSearchOption option = new SuggestionSearchOption();
        mSearch.requestSuggestion(option.keyword(keyword).city("").location(lng));
    }

    OnGetSuggestionResultListener suggestionListener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult result) {
            if (result != null) {
                List<SuggestionResult.SuggestionInfo> list = result.getAllSuggestions();
                if (CheckUtils.isNoEmptyList(list)) {
                    tvSearchFail.setVisibility(View.INVISIBLE);
                    searchAdapter.setList(list);
                    searchAdapter.notifyDataSetChanged();
                } else {
                    searchAdapter.setList(new ArrayList<SuggestionResult.SuggestionInfo>());
                    searchAdapter.notifyDataSetChanged();
                    tvSearchFail.setVisibility(View.VISIBLE);
                }
            } else {
                searchAdapter.setList(new ArrayList<SuggestionResult.SuggestionInfo>());
                searchAdapter.notifyDataSetChanged();
                tvSearchFail.setVisibility(View.VISIBLE);
            }
        }
    };

}
