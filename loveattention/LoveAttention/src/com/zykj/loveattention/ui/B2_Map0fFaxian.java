package com.zykj.loveattention.ui;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
/**
 * 发现中的地图页面
 * @author zhuyikun
 *
 */
public class B2_Map0fFaxian extends BaseActivity implements LocationSource,AMapLocationListener {

	private ImageButton map_back;//返回按钮
	private ImageButton search_it_map;//搜索按钮
	private LinearLayout ll_all_map,ll_some_map;//全部商家，返佣商家
	private View line1_map,line2_map;//下划线
	//地图对象声明
	private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAmaplocationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b2_map);
		mapView  = (MapView) findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);//地图组件，必须重写
		initMap();
		initUI();
		setListener(map_back,search_it_map,ll_all_map,ll_some_map);
	}
	//初始化地图组件
	private void initMap() {
		// TODO Auto-generated method stub
		if (aMap == null) {
			aMap = mapView.getMap();
			setUpMap();
		}
	}
	/**
	 *  设置一些aMap属性
	 */
	private void setUpMap() {
		// TODO Auto-generated method stub
		aMap.setLocationSource(this);
		aMap.getUiSettings().setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.map_back:
			this.finish();
			break;
		case R.id.search_it_map:
			Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
			break;
		case R.id.ll_all_map:
			line1_map.setVisibility(View.VISIBLE);
			line2_map.setVisibility(View.INVISIBLE);
			break;
		case R.id.ll_some_map:
			line1_map.setVisibility(View.INVISIBLE);
			line2_map.setVisibility(View.VISIBLE);
			
			break;

		default:
			break;
		}
	}
	/**
	 * 控件绑定
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		map_back = (ImageButton) findViewById(R.id.map_back);
		search_it_map = (ImageButton) findViewById(R.id.search_it_map);
		ll_all_map = (LinearLayout) findViewById(R.id.ll_all_map);
		ll_some_map = (LinearLayout) findViewById(R.id.ll_some_map);
		line1_map = (View) findViewById(R.id.line1_map);
		line2_map = (View) findViewById(R.id.line2_map);
		
		
	}
	/**
	 * 必须重写
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	/**
	 * 必须重写
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
		deactivate();
	}
	/**
	 * 必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}
	/**
	 * 必须重写
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapView.onDestroy();
	}
	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mAmaplocationManager == null) {
			mAmaplocationManager = LocationManagerProxy.getInstance(this);
			mAmaplocationManager.requestLocationData(LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
		}
	}
	/**
	 * 停止定位
	 */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener= null;
		if (mAmaplocationManager!=null) {
			mAmaplocationManager.removeUpdates(this);
			mAmaplocationManager.destory();
		}
		mAmaplocationManager = null;
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 定位成功后回调函数
	 */
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null&&amapLocation !=null) {
			if (amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);//显示系统小蓝点
			}
			
		}
	}
}
