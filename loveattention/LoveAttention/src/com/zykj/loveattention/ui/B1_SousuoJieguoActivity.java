package com.zykj.loveattention.ui;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.utils.Log;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_SousuojieguoAdapter;
import com.zykj.loveattention.data.SearchList;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * 
 * @author lc
 * @date 创建时间：2016-1-9 下午4:35:22
 * @version 1.0 
 * @Description 搜索结果
 */
public class B1_SousuoJieguoActivity extends Activity{

	private Context mContext = B1_SousuoJieguoActivity.this;
	
	private RequestQueue mRequestQueue;
	
	private String key;
	
	private ListView listview;
	private ArrayList<SearchList> searchList;
	private B1_SousuojieguoAdapter adapter;
	private TextView textview;
	private ImageView iv_b1_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.b1_activity_b1__sousuo_jieguo);
		
		iv_b1_back = (ImageView) findViewById(R.id.iv_b1_back);
		iv_b1_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B1_SousuoJieguoActivity.this.finish();
			}
		});
		
		mRequestQueue = Volley.newRequestQueue(this);
		
		key = getIntent().getStringExtra("key");
		
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(location != null){
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				}
		}else{
			LocationListener locationListener = new LocationListener() {
				
				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				@Override
				public void onStatusChanged(String provider, int status, Bundle extras) {
					
				}
				
				// Provider被enable时触发此函数，比如GPS被打开
				@Override
				public void onProviderEnabled(String provider) {
					
				}
				
				// Provider被disable时触发此函数，比如GPS被关闭 
				@Override
				public void onProviderDisabled(String provider) {
					
				}
				
				//当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发 
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {   
						Log.e("Map", "Location changed : Lat: "  
						+ location.getLatitude() + " Lng: "  
						+ location.getLongitude());   
					}
				}
			};
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);   
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);   
			if(location != null){   
				latitude = location.getLatitude(); //经度   
				longitude = location.getLongitude(); //纬度
			}   
		}

		
		listview = (ListView) findViewById(R.id.sousuojieguolistview);
		textview = (TextView) findViewById(R.id.sousuojieguotv);
		

		initData();

		
		adapter = new B1_SousuojieguoAdapter(mContext, searchList,latitude,longitude);
		
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 跳转商家详情
//				Toast.makeText(mContext, "跳转商家详情", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.setClass(mContext, B1_7_ShangJiaXiangQingActivity.class);
				intent.putExtra("merchantid", searchList.get(arg2).getId());
				startActivity(intent);
			}
		});
	}
	
	private double latitude=0.0;
	private double longitude =0.0;

	public void initData(){
		RequestDailog.showDialog(mContext, "请稍后...");
		searchList = new ArrayList<SearchList>();
		Map<String, String> map = new HashMap<String, String>();
		String key1 = null;
		try {
			key1 = URLEncoder.encode(key,"UTF-8");
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		map.put("key", key1+"");
		map.put("longitude", longitude+"");
		map.put("latitude", latitude+"");
		String json = JsonUtils.toJson(map);
		Log.d("----", "key1  = +++++" + key1);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_sousuojieguo(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						Log.d("----", "response  = +++++" + response);
						JSONObject status;
						ArrayList<SearchList> list = new ArrayList<SearchList>();
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								JSONArray array1 = response.getJSONArray("data");
								if(array1.toString().equals("") || array1.equals("") || array1.isNull(0) || array1.length()==0){
									listview.setVisibility(View.GONE);
									textview.setVisibility(View.VISIBLE);
								}else{
									listview.setVisibility(View.VISIBLE);
									textview.setVisibility(View.GONE);
									list = new Gson().fromJson(array1.toString(),new TypeToken<ArrayList<SearchList>>(){}.getType());
									searchList.addAll(list);
								}
								
								for (SearchList s : searchList) {
									if(s.getGoodList() == null)
										listview.setDivider(null);
								}
								
								adapter.notifyDataSetChanged();
								
							} else {// 失败,提示失败信息
								
								String errdesc = status.getString("errdesc");
								Toast.makeText(mContext,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(mContext, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

}
