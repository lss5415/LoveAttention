package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.zykj.loveattention.adapter.B1_7_1_BenDiKaQuanAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B1_7_1_BenDianKaQuan extends BaseActivity {
	private ImageView img_b171back;//返回
	private ListView list_kaquan;
	private B1_7_1_BenDiKaQuanAdapter kaquanadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private String merchantid;
	private RequestQueue mRequestQueue;
	private int pagenumber=1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_1_bendiankaquan);
		mRequestQueue = Volley.newRequestQueue(this);
		initView();
		BenDiKaQuan();
	}

	public void initView() {
		img_b171back = (ImageView) findViewById(R.id.img_b171back);
		list_kaquan = (ListView) findViewById(R.id.list_kaquan);
		merchantid = getIntent().getStringExtra("merchantid");
		setListener(img_b171back);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_b171back:
			this.finish();
			break;
		default:
			break;

		}
	}
	
	//我在本地的卡券
	public void BenDiKaQuan(){
        RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("merchantid", merchantid);
		map.put("pagenumber", String.valueOf(pagenumber));
		map.put("pagesize", "20");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_merchantcouponinfo(json), null,
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
								data.clear();
								JSONObject job = response.getJSONObject("data");
								JSONArray jary = job.getJSONArray("couponList");
//								Log.e("data", data+"");
								for (int i = 0; i < jary.length(); i++) {
									Map<String, String> map = new HashMap<String, String>();
									JSONObject jsonObject = (JSONObject) jary.get(i);
									map.put("couponzhekou",jsonObject.getString("couponzhekou"));
									map.put("couponname",jsonObject.getString("couponname"));
									map.put("couponstock",jsonObject.getString("couponstock"));
									map.put("linkcode",jsonObject.getString("linkcode"));
									map.put("merchantintroduct",jsonObject.getString("merchantintroduct"));
									map.put("couponcolor",jsonObject.getString("couponcolor"));
									map.put("couponclassify",jsonObject.getString("couponclassify"));
									map.put("couponcate",jsonObject.getString("couponcate"));
									map.put("coupondetail",jsonObject.getString("coupondetail"));
									map.put("couponoldprice",jsonObject.getString("couponoldprice"));
									map.put("merchantid",jsonObject.getString("merchantid"));
									map.put("createtime",jsonObject.getString("createtime"));
									map.put("couponscore",jsonObject.getString("couponscore"));
									map.put("couponimage",jsonObject.getString("couponimage"));
									map.put("createuser",jsonObject.getString("createuser"));
									map.put("couponstate",jsonObject.getString("couponstate"));
									map.put("coupontimes",jsonObject.getString("coupontimes"));
									map.put("couponid",jsonObject.getString("couponid"));
									map.put("coupontitle",jsonObject.getString("coupontitle"));
									map.put("couponcondition",jsonObject.getString("couponcondition"));
									map.put("couponintroduct",jsonObject.getString("couponintroduct"));
									map.put("couponprice",jsonObject.getString("couponprice"));
									map.put("couponsellprice",jsonObject.getString("couponsellprice"));
									map.put("couponstore",jsonObject.getString("couponstore"));
									map.put("effecttime",jsonObject.getString("effecttime"));
									data.add(map);
								}
								Log.e("list", data.toString());
								kaquanadapter = new B1_7_1_BenDiKaQuanAdapter(B1_7_1_BenDianKaQuan.this, data);
								list_kaquan.setAdapter(kaquanadapter);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_7_1_BenDianKaQuan.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_7_1_BenDianKaQuan.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
        
        
	}

}