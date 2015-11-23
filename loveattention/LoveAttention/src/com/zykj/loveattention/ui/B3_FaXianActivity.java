package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B2_and_B3_Adapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月8日	发现
 *
 */
public class B3_FaXianActivity extends BaseActivity {
	//热门活动，最新活动，近期活动
	private TextView tv_remen,tv_zuixin,tv_jinqi;
	private ImageView b3_hongdi_remen,b3_hongdi_zuixin,b3_hongdi_jinqi;
	private ListView list_huodong;
	private B2_and_B3_Adapter faxianadapter;
	private RequestQueue mRequestQueue;
	private String json;
	private List<Map<String, String>> faxiandata = new ArrayList<Map<String, String>>();
	String lng="",lat="";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_faxian);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);		
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "0");
		map.put("pagenumber", "1");
		map.put("pagesize", "5");
		map.put("longitude", lng);
		map.put("latitude", lat);
		json = JsonUtils.toJson(map);
		// 发现中活动
		HuoDong();
	}
	
	public void initView(){
		tv_remen = (TextView)findViewById(R.id.tv_remen);
		tv_zuixin = (TextView)findViewById(R.id.tv_zuixin);
		tv_jinqi = (TextView)findViewById(R.id.tv_jinqi);
		b3_hongdi_remen = (ImageView) findViewById(R.id.b3_hongdi_remen);//热门活动 --下方黄条
		b3_hongdi_zuixin = (ImageView) findViewById(R.id.b3_hongdi_zuixin);//最新活动 --下方黄条
		b3_hongdi_jinqi = (ImageView) findViewById(R.id.b3_hongdi_jinqi);//近期活动 --下方黄条
		list_huodong = (ListView) findViewById(R.id.list_huodong);
		lng = getSharedPreferenceValue("lng");
		lat = getSharedPreferenceValue("lat");
		
		setListener(tv_remen,tv_zuixin,tv_jinqi);
	}
	
	@Override
	public void  onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
        case R.id.tv_remen:
        	setVisible();
        	b3_hongdi_remen.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_remen.setTextColor(getResources().getColor(R.color.all_huang_color));

    		Map<String, String> map1 = new HashMap<String, String>();
    		map1.put("type", "0");
    		map1.put("pagenumber", "1");
    		map1.put("pagesize", "5");
    		map1.put("longitude", lng);
    		map1.put("latitude", lat);
    		json = JsonUtils.toJson(map1);
    		// 发现中活动
    		HuoDong();
        	break;
        case R.id.tv_zuixin:
        	setVisible();
        	b3_hongdi_zuixin.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_zuixin.setTextColor(getResources().getColor(R.color.all_huang_color));

    		Map<String, String> map2 = new HashMap<String, String>();
    		map2.put("type", "1");
    		map2.put("pagenumber", "1");
    		map2.put("pagesize", "5");
    		map2.put("longitude", lng);
    		map2.put("latitude", lat);
    		json = JsonUtils.toJson(map2);
    		// 发现中活动
    		HuoDong();
        	break;
        case R.id.tv_jinqi:
        	setVisible();
        	b3_hongdi_jinqi.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_jinqi.setTextColor(getResources().getColor(R.color.all_huang_color));

    		Map<String, String> map3 = new HashMap<String, String>();
    		map3.put("type", "2");
    		map3.put("pagenumber", "1");
    		map3.put("pagesize", "5");
    		map3.put("longitude", lng);
    		map3.put("latitude", lat);
    		json = JsonUtils.toJson(map3);
    		// 发现中活动
    		HuoDong();
        	break;
        default:
        	break;
        }
   }
	
	//设置所有黄条默认屏蔽
	public void setVisible(){
    	b3_hongdi_remen.setVisibility(View.INVISIBLE);
    	b3_hongdi_zuixin.setVisibility(View.INVISIBLE);
    	b3_hongdi_jinqi.setVisibility(View.INVISIBLE);
	}
	
	//设置活动字体颜色为黑色
	public void setTextColor(){
		tv_remen.setTextColor(Color.BLACK);
		tv_zuixin.setTextColor(Color.BLACK);
		tv_jinqi.setTextColor(Color.BLACK);
	}
	
	//发现中活动
		public void HuoDong(){
			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_faxian(json), null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							RequestDailog.closeDialog();
							JSONObject status;
							try {
								status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) // 成功
								{
									faxiandata.clear();
									JSONObject jobs = response.getJSONObject("data");
									org.json.JSONArray array1 = jobs.getJSONArray("list");
									for (int i = 0; i < array1.length(); i++) {
										JSONObject jsonItem1 = array1.getJSONObject(i);
										Map<String, String> map1 = new HashMap<String, String>();
										map1.put("perperson", jsonItem1.getString("perperson"));
										map1.put("merchantid", jsonItem1.getString("merchantid"));
										map1.put("aname", jsonItem1.getString("aname"));
										map1.put("icon", jsonItem1.getString("icon"));
										map1.put("remark", jsonItem1.getString("remark"));
										map1.put("stars", jsonItem1.getString("stars"));
										map1.put("goodid", jsonItem1.getString("goodid"));	
										map1.put("type", jsonItem1.getString("type"));
										map1.put("aid", jsonItem1.getString("aid"));
										map1.put("price", jsonItem1.getString("price"));
										map1.put("oldprice", jsonItem1.getString("oldprice"));
										faxiandata.add(map1);
									}
									faxianadapter = new B2_and_B3_Adapter(B3_FaXianActivity.this,faxiandata);
									list_huodong.setAdapter(faxianadapter);
									
								} else {// 失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(B3_FaXianActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
							Toast.makeText(B3_FaXianActivity.this, "网络连接失败，请重试",
									Toast.LENGTH_LONG).show();
						}
					});
			mRequestQueue.add(jr);
		}
}