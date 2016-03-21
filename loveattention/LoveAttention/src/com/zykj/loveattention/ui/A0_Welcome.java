package com.zykj.loveattention.ui;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;


/**
 * @author lss 2015年8月8日 欢迎页面
 *
 */
public class A0_Welcome extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_a0_welcome);
		mLocationManger = LocationManagerProxy.getInstance(this);
		// 进行一次定位
		mLocationManger.requestLocationData(LocationProviderProxy.AMapNetwork,
				-1, 15, mLocationListener);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				// Intent intent = new Intent(Welcome.this, MainActivity.class);
				// startActivity(intent);

				String is_intro = getSharedPreferenceValue(AppValue.IS_INTRO);
				boolean should_intro = false;
				int version = Tools.getAppVersion(A0_Welcome.this);
				String save_version = getSharedPreferenceValue(AppValue.VERSION);
				int save_version_int = save_version.equals("") ? -1 : Integer
						.parseInt(save_version);

				if (is_intro.length() > 0 && version == save_version_int) {// 已经进行过指引,且版本号符合
					should_intro = false;
				} else {
					should_intro = true;
				}

				if (should_intro) {
					Intent intent = new Intent(A0_Welcome.this,A1_IntroActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(A0_Welcome.this, B0_MainActivity.class);
					startActivity(intent);
				}
				finish();

			}
		};
		timer.schedule(task, 2000);
	}

	// 定位
	private LocationManagerProxy mLocationManger;
	private AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
		}

		@Override
		public void onLocationChanged(AMapLocation location) {
//			Tools.CURRENTCITY = location.getCity();
			if (location != null
					&& location.getAMapException().getErrorCode() == 0) {
				String lng = location.getLongitude() + "";
				String lat = location.getLatitude() + "";
				putSharedPreferenceValue("lng", lng);
				putSharedPreferenceValue("lat", lat);
				HttpUtils.getCityName(res_getCityName1, lng, lat);
				// getCityName
				// Toast.makeText(A0_Welcome.this,
				// "城市="+location.getCity()+"lat="+location.getLatitude()+"long="+location.getLongitude(),
				// Toast.LENGTH_LONG).show();
				// BaseApp.getModel().setLatitude(location.getLatitude()+"");
				// BaseApp.getModel().setLongitude(location.getLongitude()+"");
				// Tools.toast(A0_Welcome.this,
				// "城市="+location.getCity()+"lat="+location.getLatitude()+"long="+location.getLongitude());
			} else {
				Toast.makeText(A0_Welcome.this, "定位出现异常", Toast.LENGTH_LONG)
						.show();
			}
		}
	};
	JsonHttpResponseHandler res_getCityName1 = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_pointsMallresponse=" + response);
			String error = null;
			JSONObject datas = null;
			try {
				datas = response.getJSONObject("datas");
				error = response.getString("error");
			} catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error == null)// 成功
			{
				try {
					String cityname = datas.getString("area_name");
					String cityid = datas.getString("area_id");
					putSharedPreferenceValue("cityname", cityname);
					putSharedPreferenceValue("cityid", cityid);
				} catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else// 失败
			{
				Tools.Log("res_Points_error=" + error + "");
			}
		}
	};
}
