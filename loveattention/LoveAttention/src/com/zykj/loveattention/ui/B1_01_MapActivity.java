package com.zykj.loveattention.ui;

import java.util.Collections;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.SortAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.SortModel;
import com.zykj.loveattention.ui.SideBar.OnTouchingLetterChangedListener;
import com.zykj.loveattention.utils.CharacterParser;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.LocationObsever;
import com.zykj.loveattention.utils.LocationUtil;
import com.zykj.loveattention.utils.PinyinComparator;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author LSS 2015年8月29日 上午8:47:10	地图
 *
 */
public class B1_01_MapActivity extends BaseActivity{
	//城市列表
	private ListView sortListView;
	//提示框
	private TextView dialog;
	//右侧字母
	private SideBar sideBar;
	private SortAdapter adapter;
	private ImageView im_b101_back;
	private TextView tv_findcityname,tv_dangqiancity,tv_zj1,tv_zj2,tv_zj3;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	private List<SortModel> SourceDateList1;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	//获取经纬度
	private DiaryLocationObsever mObs = null;
	LocationUtil mLocationUtil;
	String lng;//经度
	String lat;// 纬度
	String citynamex;
	String area_idx;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_01_map);
		initView();
	}
	
	private void initView(){
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		tv_findcityname = (TextView)findViewById(R.id.tv_findcityname);
		tv_dangqiancity = (TextView)findViewById(R.id.tv_dangqiancity);
		tv_zj1 = (TextView)findViewById(R.id.tv_zj1);
		tv_zj2 = (TextView)findViewById(R.id.tv_zj2);
		tv_zj3 = (TextView)findViewById(R.id.tv_zj3);
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		im_b101_back = (ImageView)findViewById(R.id.im_b101_back);
		if (!getSharedPreferenceValue("tv_zj1").equals("")){
			tv_zj1.setVisibility(View.VISIBLE);
			tv_zj1.setText(getSharedPreferenceValue("tv_zj1").toString());
		}
		if (!getSharedPreferenceValue("tv_zj2").equals("")){
			tv_zj2.setVisibility(View.VISIBLE);
			tv_zj2.setText(getSharedPreferenceValue("tv_zj2").toString());
		}
		if (!getSharedPreferenceValue("tv_zj3").equals("")){
			tv_zj3.setVisibility(View.VISIBLE);
			tv_zj3.setText(getSharedPreferenceValue("tv_zj3").toString());
		}
		mObs = new DiaryLocationObsever();	
		mLocationUtil = new LocationUtil();
		mLocationUtil.initiLocationUtil(this, this.mObs);
		mLocationUtil.RefreshGPS(true);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Intent mapit = new Intent(B1_01_MapActivity.this,B1_ShouYeActivity.class);
				mapit.putExtra("cityname",((SortModel)adapter.getItem(position)).getCityname());
				mapit.putExtra("cityid",((SortModel)adapter.getItem(position)).getCitynum());
				mapit.putExtra("lng",lng);
				mapit.putExtra("lat",lat);
				B1_01_MapActivity.this.setResult(0, mapit);
				B1_01_MapActivity.this.finish();
//				Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		HttpUtils.getCityList(res_getCityList);
		setListener(im_b101_back,tv_findcityname,tv_zj1,tv_zj2,tv_zj3);
	}


	/**
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<SortModel> date){
//		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.size(); i++){
			SortModel sortModel = date.get(i);
//			sortModel.setArea_name(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date.get(i).getCityname());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
//			mSortList.add(sortModel);
		}
		return date;
		
	}
	

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b101_back:
			B1_01_MapActivity.this.finish();
			break;
		case R.id.tv_findcityname:
			HttpUtils.getCityName(res_getCityName1,lng,lat);
			break;
		case R.id.tv_zj1:
			citynamex = getSharedPreferenceValue("tv_zj1");
			area_idx = getSharedPreferenceValue("tv_zj11");
			tv_findcityname.setText(citynamex);
			tv_dangqiancity.setText(citynamex);					
			Intent mapit1 = new Intent(B1_01_MapActivity.this,B1_ShouYeActivity.class);
			mapit1.putExtra("lng",lng);
			mapit1.putExtra("lat",lat);
			mapit1.putExtra("cityname",citynamex);
			mapit1.putExtra("cityid",area_idx);
			B1_01_MapActivity.this.setResult(0, mapit1);
			B1_01_MapActivity.this.finish();
			break;
		case R.id.tv_zj2:
			citynamex = getSharedPreferenceValue("tv_zj2");
			area_idx = getSharedPreferenceValue("tv_zj22");
			tv_findcityname.setText(citynamex);
			tv_dangqiancity.setText(citynamex);					
			Intent mapit2 = new Intent(B1_01_MapActivity.this,B1_ShouYeActivity.class);
			mapit2.putExtra("lng",lng);
			mapit2.putExtra("lat",lat);
			mapit2.putExtra("cityname",citynamex);
			mapit2.putExtra("cityid",area_idx);
			B1_01_MapActivity.this.setResult(0, mapit2);
			B1_01_MapActivity.this.finish();
			break;
		case R.id.tv_zj3:
			citynamex = getSharedPreferenceValue("tv_zj3");
			area_idx = getSharedPreferenceValue("tv_zj33");
			tv_findcityname.setText(citynamex);
			tv_dangqiancity.setText(citynamex);					
			Intent mapit3 = new Intent(B1_01_MapActivity.this,B1_ShouYeActivity.class);
			mapit3.putExtra("lng",lng);
			mapit3.putExtra("lat",lat);
			mapit3.putExtra("cityname",citynamex);
			mapit3.putExtra("cityid",area_idx);
			B1_01_MapActivity.this.setResult(0, mapit3);
			B1_01_MapActivity.this.finish();
			break;
		default:
			break;
		}
	}
	
	JsonHttpResponseHandler res_getCityList = new JsonHttpResponseHandler()
	{		
		public void onSuccess(int statusCode, Header[] headers,
				org.json.JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_getAddress="+response);
			try {
				com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject)JSON.parse(response.toString());
				com.alibaba.fastjson.JSONObject jsonArray = jsonObject.getJSONObject("data");
				JSONArray area_list = jsonArray.getJSONArray("citylist");
				SourceDateList = JSONArray.parseArray(area_list.toString(), SortModel.class);
				SourceDateList1= filledData(SourceDateList);
//				SourceDateList = filledData(getResources().getStringArray(R.array.date));
				
				// 根据a-z进行排序源数据
				Collections.sort(SourceDateList1, pinyinComparator);
				adapter = new SortAdapter(getApplicationContext(), SourceDateList1);
				sortListView.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	};
	
	JsonHttpResponseHandler res_getCityName = new JsonHttpResponseHandler()
	{

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_pointsMallresponse="+response);
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			}catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				try {
					citynamex = datas.getString("area_name");
					area_idx = datas.getString("area_id");
					tv_findcityname.setText(citynamex);
					tv_dangqiancity.setText(citynamex);
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
			}
		}
	};
	
	JsonHttpResponseHandler res_getCityName1 = new JsonHttpResponseHandler()
	{

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_pointsMallresponse="+response);
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			}catch (org.json.JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				try {
					citynamex = datas.getString("area_name");
					area_idx = datas.getString("area_id");
					tv_findcityname.setText(citynamex);
					tv_dangqiancity.setText(citynamex);
					Intent mapit = new Intent(B1_01_MapActivity.this, B1_ShouYeActivity.class);
					mapit.putExtra("cityname",tv_findcityname.getText().toString());
					mapit.putExtra("lng",lng);
					mapit.putExtra("lat",lat);
					mapit.putExtra("cityname",citynamex);
					mapit.putExtra("cityid",area_idx);
					B1_01_MapActivity.this.setResult(0, mapit);
					B1_01_MapActivity.this.finish();
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
			}
		}
	};

	private class DiaryLocationObsever extends LocationObsever{
		@Override
		public void notifyChange(int arg, String des){
			System.out.println("====notifyChange arg="+arg+"des="+des);
			switch (arg) {
			case LocationUtil.GPSTIMEOUT:
	  			setLocationDes(1, null);
			break;
  			case LocationUtil.STATUS_CHANGED:
    		    //setLocationDes(1, null);
				break;
			case LocationUtil.SELECT_LOCATION:	
				break;
			case LocationUtil.DEFAULT_LOCATION_COMPLETED:			
				setLocationDes(2, des);
				break;
			case LocationUtil.GET_LOCATIONBUILDINGLIST_FAILED:
				setLocationDes(1, null);
				break;				
			case LocationUtil.CANCELGPS_COMPLETED:
				System.out.println("====get location des="+des);
				setLocationDes(0,null);
				break;
			case LocationUtil.SELECT_LOCATION_COMPLETED:
				setLocationDes(2, des);
//				mLocationLayout.setVisibility(View.VISIBLE);
				break;
			case LocationUtil.REFRESHGPS_COMPLETED:
				setLocationDes(0, null);
				break;
			case LocationUtil.REFRESHGPS_NOPROVIDER:
				setLocationDes(1, null);
				break;
			case LocationUtil.GETLOCATION_FAILED:
				setLocationDes(1, null);
				break;				
			default:
				break;
		}
	   }
	}
	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocationUtil.destroy();
		mLocationUtil = null;
	}



	private void setLocationDes(int state, String des){
		switch(state){
		case 0:
			if (LocationUtil.getLastLocation().length() > 0) {
//				mLocationDes.setText(LocationUtil.getLastLocation());
//				String spStr[] = des.split(",");
//				lng = spStr[0];
//				lat = spStr[1];
//				Toast.makeText(getApplicationContext(), LocationUtil.getLastLocation()+"1", Toast.LENGTH_LONG).show();
			}
			else {
//				Toast.makeText(getApplicationContext(), LocationUtil.NONE_AVAILABLE_LOCATION+"2", Toast.LENGTH_LONG).show();
//				mLocationDes.setText(LocationUtil.NONE_AVAILABLE_LOCATION);
			}
			break;
		case 1:
			String str = this.getString(R.string.cannot_find_location);
//			mLocationDes.setText(str);
//			Toast.makeText(getApplicationContext(), str+"3", Toast.LENGTH_LONG).show();
			break;
		case 2:
			if(des != null){
				String spStr[] = des.split(",");
				lng = spStr[1];
				lat = spStr[0];
				HttpUtils.getCityName(res_getCityName,lng,lat);
//				Toast.makeText(getApplicationContext(), lng+"xxxxxx"+lat, Toast.LENGTH_LONG).show();
			}else{
//				Toast.makeText(getApplicationContext(), des+"5", Toast.LENGTH_LONG).show();
//				mLocationDes.setText(des);
			}
			break;
		default:
			break;
		}
	}
	
}
