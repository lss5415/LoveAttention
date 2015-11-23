package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_3_GuangGaoDingZhiAdapter;
import com.zykj.loveattention.adapter.B1_ShouYeAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;


/**
 * @author LSS 2015年8月24日 下午4:34:17	广告定制
 * 
 */
public class B1_3_GuangGaoDingZhiActivity extends BaseActivity {
	private ImageView im_b13_back_btn;//返回
	private ListView list_guanggaodingzhi;
	private B1_3_GuangGaoDingZhiAdapter guanggaoadapter;
	private List<Map<String, String>> guanggaodata = new ArrayList<Map<String, String>>();
	private RequestQueue mRequestQueue;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_3_guanggaodingzhi);
		mRequestQueue = Volley.newRequestQueue(this);
		initView();
	}


	public void initView() {
		im_b13_back_btn = (ImageView) findViewById(R.id.im_b13_back_btn);
		list_guanggaodingzhi = (ListView) findViewById(R.id.list_guanggaodingzhi);
		Adverttailor();
		list_guanggaodingzhi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String advertid = guanggaodata.get(arg2).get("advertid");
				Intent itdenglu = new Intent();
				itdenglu.putExtra("advertid", advertid);
				itdenglu.setClass(B1_3_GuangGaoDingZhiActivity.this, B1_3_1_GuangGaoInfoActivity.class);
				startActivity(itdenglu);
				
			}
		});
		
		setListener(im_b13_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b13_back_btn:
			this.finish();
			break;
//		case R.id.tv_myyaoqingren:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_5_WoDeYaoQingActivity.this, B4_5_1_MyYaoQingRenActivity.class);
//			startActivity(itdenglu);
//			break;
		default:
			break;
		}
	}

	public void Adverttailor() {
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_adverttailor(), null,
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
								guanggaodata.clear();
								org.json.JSONArray array1 = response.getJSONArray("data");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject jsonItem1 = array1.getJSONObject(i);
									Map<String, String> map1 = new HashMap<String, String>();
									map1.put("questiontitle", jsonItem1.getString("questiontitle"));
									map1.put("getintegral", jsonItem1.getString("getintegral"));
									map1.put("name", jsonItem1.getString("name"));
									map1.put("advertid", jsonItem1.getString("advertid"));
									map1.put("answer", jsonItem1.getString("answer"));
									map1.put("otherimgpath", jsonItem1.getString("otherimgpath"));	
									map1.put("intruduct", jsonItem1.getString("intruduct"));
									map1.put("mainimgpath", jsonItem1.getString("mainimgpath"));
									map1.put("questionoption", jsonItem1.getString("questionoption"));
									map1.put("merchantid", jsonItem1.getString("merchantid"));				
									guanggaodata.add(map1);
								}
								guanggaoadapter = new B1_3_GuangGaoDingZhiAdapter(B1_3_GuangGaoDingZhiActivity.this,guanggaodata);
								list_guanggaodingzhi.setAdapter(guanggaoadapter);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_3_GuangGaoDingZhiActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_3_GuangGaoDingZhiActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

}