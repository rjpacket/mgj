package com.project.mgjandroid.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.project.mgjandroid.R;
import com.project.mgjandroid.bean.UserAddress;
import com.project.mgjandroid.constants.Constants;
import com.project.mgjandroid.manager.LocationManager;
import com.project.mgjandroid.model.AddressManageModel;
import com.project.mgjandroid.net.VolleyOperater;
import com.project.mgjandroid.ui.adapter.AddressManagerListAdapter;
import com.project.mgjandroid.ui.adapter.LocalListAdapter;
import com.project.mgjandroid.utils.CheckUtils;
import com.project.mgjandroid.utils.CommonUtils;
import com.project.mgjandroid.utils.PreferenceUtils;
import com.project.mgjandroid.utils.inject.InjectView;
import com.project.mgjandroid.utils.inject.Injector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 定位页面
 * 
 * @author jian
 * 
 */
public class LocationActivity extends BaseActivity implements OnClickListener {
	@InjectView(R.id.location_act_edt_search)
	private EditText edtSearch;
	@InjectView(R.id.location_act_iv_back)
	private ImageView btnBack;
	@InjectView(R.id.location_act_tv_search)
	private TextView tvSearch;
	@InjectView(R.id.location_act_tv_current)
	private TextView tvCurrentLoc;
	@InjectView(R.id.location_act_progress_current)
	private ProgressBar progressCurrent;
	@InjectView(R.id.location_act_layout_location)
	private RelativeLayout layoutCurrent;
	@InjectView(R.id.location_act_tv_my_address)
	private TextView tvAddress;
	@InjectView(R.id.location_act_listView)
	private ListView addressListView;
	@InjectView(R.id.location_act_layout_list)
	private RelativeLayout layoutList;
	@InjectView(R.id.location_act_layout_current)
	private RelativeLayout locationCurrent;
	@InjectView(R.id.location_act_progress_list)
	private ProgressBar progressList;
	@InjectView(R.id.location_act_list)
	private ListView listview;
	@InjectView(R.id.location_act_tv_search_fail)
	private TextView tvFail;
	@InjectView(R.id.root_view)
	private View rootView;
	private BDLocationListener listener;
	private PoiSearch mPoiSearch;
	private LocalListAdapter adapter;
	private AddressManagerListAdapter addressManagerListAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.location_act);
		Injector.get(this).inject();
		adapter = new LocalListAdapter(LocationActivity.this);
		addressManagerListAdapter = new AddressManagerListAdapter(this);
		addressManagerListAdapter.setCanModify(false);
		setListener();
		getAddressList();
	}

	private void setListener() {
		listener = new BDLocationListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onReceiveLocation(BDLocation location) {
				if (location != null) {
					PreferenceUtils.saveLocation(location.getLatitude() + "", location.getLongitude() + "", LocationActivity.this);
					PreferenceUtils.saveAddressName(location.getAddrStr(), LocationActivity.this);
					if (CheckUtils.isNoEmptyList(location.getPoiList())) {
						List<Poi> list = location.getPoiList();
						PreferenceUtils.saveAddressDes(list.get(0).getName(), LocationActivity.this);
					}
					if(location.getAddress() != null && location.getAddress().city != null){
						PreferenceUtils.saveAddressCity(location.getAddress().city, mActivity);
					}
					if(location.getAddress() != null && location.getAddress().cityCode != null){
						PreferenceUtils.saveAddressCityCode(location.getAddress().cityCode, mActivity);
					}
					setResult(RESULT_OK);
				}
				showAddress();
				LocationManager.getIManager().stopLocation();
				if (location != null) {
					back();
					if (mPoiSearch != null) {
						mPoiSearch.destroy();
					}
				}
			}
		};
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					layoutList.setVisibility(View.VISIBLE);
					progressList.setVisibility(View.VISIBLE);
					poiSearch(edtSearch.getText() + "");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					tvSearch.setVisibility(View.VISIBLE);
					layoutList.setVisibility(View.VISIBLE);
					listview.setVisibility(View.VISIBLE);
				} else {
					tvSearch.setVisibility(View.GONE);
					layoutList.setVisibility(View.GONE);
					listview.setVisibility(View.GONE);
				}
			}

		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				List<SuggestionResult.SuggestionInfo> list = adapter.getList();
				if (CheckUtils.isNoEmptyList(list) && list.size() > position) {
					SuggestionResult.SuggestionInfo info = list.get(position);
					if (info != null) {
						LatLng pt = info.pt;
						if (pt != null) {
							PreferenceUtils.saveAddressName(info.key, LocationActivity.this);
							PreferenceUtils.saveAddressDes("", LocationActivity.this);
							PreferenceUtils.saveLocation(Double.toString(pt.latitude), Double.toString(info.pt.longitude), LocationActivity.this);
							setResult(RESULT_OK);
							back();
							if (mPoiSearch != null) {
								mPoiSearch.destroy();
							}
						}
					}
				}
			}
		});

		rootView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				CommonUtils.hideKeyBoard2(edtSearch);
				return false;
			}
		});

		addressListView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				CommonUtils.hideKeyBoard2(edtSearch);
				return false;
			}
		});

		tvSearch.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		layoutCurrent.setOnClickListener(this);
		addressListView.setAdapter(addressManagerListAdapter);

		addressListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				List<UserAddress> userAddressList = addressManagerListAdapter.getList();
				if (CheckUtils.isNoEmptyList(userAddressList) && userAddressList.size() > position) {
					UserAddress info = userAddressList.get(position);
					if (info != null) {
						PreferenceUtils.saveAddressName(info.getAddress(), LocationActivity.this);
						PreferenceUtils.saveAddressDes(info.getHouseNumber(), LocationActivity.this);
						PreferenceUtils.saveLocation(Double.toString(info.getLatitude()), Double.toString(info.getLongitude()), LocationActivity.this);
						setResult(RESULT_OK);
						back();
						if (mPoiSearch != null) {
							mPoiSearch.destroy();
						}
					}
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.location_act_layout_location:
			CommonUtils.hideKeyBoard2(edtSearch);
			tvCurrentLoc.setText(R.string.search);
			layoutCurrent.setEnabled(false);
			progressCurrent.setVisibility(View.VISIBLE);
			LocationManager.getIManager().registeLocation(getApplicationContext(), listener);
			break;
		case R.id.location_act_tv_search:
			edtSearch.setText("");
			break;
		case R.id.location_act_iv_back:
			if (layoutList.getVisibility() == View.VISIBLE) {
				layoutList.setVisibility(View.GONE);
			} else {
				back();
				if (mPoiSearch != null) {
					mPoiSearch.destroy();
				}
			}
			break;

		default:
			break;
		}
	}

	private void poiSearch(String keyword) {
		SuggestionSearch mSearch = SuggestionSearch.newInstance();
		mSearch.setOnGetSuggestionResultListener(suggestionListener);
		String latitude = PreferenceUtils.getLocation(LocationActivity.this)[0];
		String longitude = PreferenceUtils.getLocation(LocationActivity.this)[1];
		LatLng lng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
		SuggestionSearchOption option = new SuggestionSearchOption();
		mSearch.requestSuggestion(option.keyword(keyword).city("").location(lng));
	}

	OnGetSuggestionResultListener suggestionListener = new OnGetSuggestionResultListener() {
		@Override
		public void onGetSuggestionResult(SuggestionResult result) {
			progressList.setVisibility(View.GONE);
			// 获取POI检索结果
			if (result != null) {
				List<SuggestionResult.SuggestionInfo> list = result.getAllSuggestions();
				if (CheckUtils.isNoEmptyList(list)) {
					listview.setVisibility(View.VISIBLE);
					tvFail.setVisibility(View.GONE);
					listview.setAdapter(adapter);
					adapter.setList(list);
					adapter.notifyDataSetChanged();
				} else {
					tvFail.setVisibility(View.VISIBLE);
					listview.setVisibility(View.GONE);
				}
			} else {
				tvFail.setVisibility(View.VISIBLE);
				listview.setVisibility(View.GONE);
			}
		}
	};

//	OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
//		public void onGetPoiResult(PoiResult result) {
//			progressList.setVisibility(View.GONE);
//			// 获取POI检索结果
//			if (result != null) {
//				List<SuggestionResult.SuggestionInfo> list = result.getAllPoi();
//				if (CheckUtils.isNoEmptyList(list)) {
//					listview.setVisibility(View.VISIBLE);
//					tvFail.setVisibility(View.GONE);
//					listview.setAdapter(adapter);
//					adapter.setList(list);
//					adapter.notifyDataSetChanged();
//				} else {
//					tvFail.setVisibility(View.VISIBLE);
//				}
//			} else {
//				tvFail.setVisibility(View.VISIBLE);
//			}
//		}
//
//		public void onGetPoiDetailResult(PoiDetailResult result) {
//			// 获取Place详情页检索结果
//		}
//	};

	private void showAddress() {
		String address = PreferenceUtils.getAddressName(LocationActivity.this);
		String addressDes = PreferenceUtils.getAddressDes(LocationActivity.this);
		if (CheckUtils.isNoEmptyStr(address)) {
			if (CheckUtils.isNoEmptyStr(addressDes)) {
				tvCurrentLoc.setText(address + addressDes);
			} else {
				tvCurrentLoc.setText(address);
			}
		} else {
			tvCurrentLoc.setText(R.string.loc_current);
		}
		layoutCurrent.setEnabled(true);
		progressCurrent.setVisibility(View.GONE);
	}

	private void getAddressList() {
		Map<String, Object> map = new HashMap<String, Object>();
		VolleyOperater<AddressManageModel> operater = new VolleyOperater<AddressManageModel>(LocationActivity.this);
		operater.doRequest(Constants.URL_GET_ADDRESS, map, new VolleyOperater.ResponseListener() {
			@Override
			public void onRsp(boolean isSucceed, Object obj) {
				if(isSucceed && obj!=null){
					List<UserAddress> userAddressList = ((AddressManageModel) obj).getValue();
					if(CheckUtils.isNoEmptyList(userAddressList)){
						tvAddress.setVisibility(View.VISIBLE);
						addressManagerListAdapter.setList(userAddressList);
						addressManagerListAdapter.notifyDataSetChanged();
					}
				}
			}
		}, AddressManageModel.class);
	}
}
