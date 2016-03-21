package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.AdressAdapter;
import com.zykj.loveattention.adapter.B1_7_3_YouHuiAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月14日	地址管理
 *
 */
public class B4_10_2_ShouHuoDiZhiActivity extends BaseActivity {
	private ImageView im_b410_2_back_btn,im_xinzengdizhi;//返回
	private RequestQueue mRequestQueue;
	private AutoListView lv_dizhilist;
	private AdressAdapter yqadapter;
	private List<Map<String, String>> daray = new ArrayList<Map<String, String>>();
	private String linkmobile,addressid,linkprovince,linkcounty,state,linkname,linkaddress,linkcity;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_2_dizhiguanli);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		HuoQuDiZhi();
	}


	public void initView() {
		im_b410_2_back_btn = (ImageView) findViewById(R.id.im_b410_2_back_btn);
		im_xinzengdizhi = (ImageView) findViewById(R.id.im_xinzengdizhi);
		lv_dizhilist = (AutoListView) findViewById(R.id.lv_dizhilist);
		lv_dizhilist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
	                Intent intent = new Intent();
	                intent.putExtra("addressid", daray.get(arg2).get("addressid"));
	                intent.putExtra("linkname", daray.get(arg2).get("linkname"));
	                intent.putExtra("linkprovince", daray.get(arg2).get("linkprovince"));
	                intent.putExtra("linkcity", daray.get(arg2).get("linkcity"));
	                intent.putExtra("linkcounty", daray.get(arg2).get("linkcounty"));
	                intent.putExtra("linkaddress", daray.get(arg2).get("linkaddress"));
	                setResult(1001, intent);
	                B4_10_2_ShouHuoDiZhiActivity.this.finish();
			}
		});
		setListener(im_b410_2_back_btn,im_xinzengdizhi);
	}
 
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_2_back_btn:
			this.finish();
			break;
		case R.id.im_xinzengdizhi:
			Intent itzhxx = new Intent();
			itzhxx.setClass(B4_10_2_ShouHuoDiZhiActivity.this, B4_10_2_1_XinZengDiZhiActivity.class);
			itzhxx.putExtra("state", "0");
			startActivity(itzhxx);
			break;
		default:
			break;

		}
	}
	
	public void HuoQuDiZhi(){
		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_addressinfo(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						daray.clear();
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							org.json.JSONArray data = response.getJSONArray("data");
							for (int i = 0; i < data.length(); i++) {
								JSONObject jsonItem1 = data.getJSONObject(i);
								Map<String, String> map1 = new HashMap<String, String>();
								linkmobile = jsonItem1.getString("linkmobile");
								addressid = jsonItem1.getString("addressid");
								linkprovince = jsonItem1.getString("linkprovince");
								linkcounty = jsonItem1.getString("linkcounty");
								state = jsonItem1.getString("state");
								linkname = jsonItem1.getString("linkname");
								linkaddress = jsonItem1.getString("linkaddress");
								linkcity = jsonItem1.getString("linkcity");
								map1.put("linkmobile",linkmobile);
								map1.put("addressid",addressid);
								map1.put("linkprovince", linkprovince);
								map1.put("linkcounty", linkcounty);
								map1.put("state", state);
								map1.put("linkname", linkname);
								map1.put("linkaddress", linkaddress);
								map1.put("linkcity", linkcity);
								daray.add(map1);
							}
							yqadapter = new AdressAdapter(
									B4_10_2_ShouHuoDiZhiActivity.this, daray,getSharedPreferenceValue("id"));
							lv_dizhilist.setAdapter(yqadapter);
						}else {//失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(getApplicationContext(), errdesc, Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
                Toast.makeText(getApplicationContext(), "网络连接失败，请重试", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
	}
}