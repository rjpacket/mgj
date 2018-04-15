package com.project.mgjandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.PlanNode;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.Merchant;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

public class LocationMapActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.map_back)
    private ImageView img_back;
    @InjectView(R.id.map_title)
    private TextView tv_title;
    @InjectView(R.id.baidu_map)
    private MapView mapView;
    private Merchant merchant;
    private Double longitude;
    private Double latitude;
    private BaiduMap baiduMap;
    private boolean isFirstIn = false;
    private LocationClient mLocationClient;
    private BDLocationListener bdLocationListener;
    private MarkerOptions locationBitmap;
    private BitmapDescriptor mCurrentMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_map);
        Injector.get(this).inject();

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("merchant")){
            merchant = (Merchant) intent.getSerializableExtra("merchant");
            tv_title.setText(merchant.getName());
            longitude = merchant.getLongitude();
            latitude = merchant.getLatitude();
        }
        img_back.setOnClickListener(this);
        baiduMap = mapView.getMap();
        isFirstIn = true;
        baiduMap.setMyLocationEnabled(true);
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.takeout_ic_map_location);
        locationCurrentSite(latitude,longitude);

        //定位
        mLocationClient = new LocationClient(this);
        initLocation();
        bdLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                System.out.println("-----loc---->" + bdLocation.getLatitude() + ";" + bdLocation.getLongitude());
                if (baiduMap != null) {
                    LatLng point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    locationBitmap = new MarkerOptions()
                            .position(point)
                            .icon(mCurrentMarker);
                    baiduMap.clear();
                    baiduMap.addOverlay(locationBitmap);
                }
            }
        };
        mLocationClient.registerLocationListener(bdLocationListener);
        mLocationClient.start();
    }

    /**
     * 初始化定位配置信息
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_back:
                finish();
                break;
        }
    }

    /**
     * 定位当前地位置
     *
     * @param
     */
    private void locationCurrentSite(double lat, double lon) {

        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(bdLocation.getRadius())
//                .direction(bdLocation.getDirection())
                .latitude(lat)
                .longitude(lon)
                .build();
        baiduMap.setMyLocationData(locData);

        if (isFirstIn) {
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(18).build()));
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.takeout_icon_poi_position));
            baiduMap.setMyLocationConfigeration(config);
            isFirstIn = false;
        } else {
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null);
            baiduMap.setMyLocationConfigeration(config);
        }
    }
}
