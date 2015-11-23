package com.zykj.loveattention.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_3_KaQuanAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月12日	我的卡券
 *
 */
public class B4_3_WoDeKaQuanActivity extends BaseActivity {
	//全部，未支付，提醒
	private TextView tv_quanbu,tv_weizhifu,tv_tixing;
	private ImageView b3_hongdi_quanbu,b3_hongdi_weizhifu,b3_hongdi_tixing;
	private ListView list_huodong;
	private B4_3_KaQuanAdapter kaquanadapter;
	private ImageView im_b5_2_back_btn;
	private RequestQueue mRequestQueue;
	private int status = 0;
	private int pagenumber = 1;
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_3_wodekaquan);
		initView();
		setAdapter();
		mRequestQueue = Volley.newRequestQueue(this);
		getServiceData();
	}
	private void setAdapter() {
		kaquanadapter = new B4_3_KaQuanAdapter(B4_3_WoDeKaQuanActivity.this,listdata);
		list_huodong.setAdapter(kaquanadapter);
	}
	private void getServiceData() {
		// TODO Auto-generated method stub
		//{"memberid":"14","gid":"0","pagenumber":"1","pagesize":"5"}
				Map<String , String > map = new HashMap<String, String>();
				map.put("memberid", getSharedPreferenceValue("id"));
				map.put("gid", status+"");
				map.put("pagenumber", pagenumber+"");
				map.put("pagesize", "5");
				String json = JsonUtils.toJson(map);
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_couponinfo(json),null,new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								Log.e("data", response+"");
								if (succeed.equals("1")) //成功
								{
									listdata.clear();
									JSONArray data = response.getJSONArray("data");
//									Log.e("data", data+"");
									for (int i = 0; i < data.length(); i++) {
										Map<String, String> map = new HashMap<String, String>();
										JSONObject jsonObject = (JSONObject) data.get(i);
										map.put("couponname",jsonObject.getString("couponname"));
										map.put("name",jsonObject.getString("name"));
										map.put("effecttime",jsonObject.getString("effecttime"));
										map.put("couponintroduct",jsonObject.getString("couponintroduct"));
										listdata.add(map);
									}
									Log.e("list", listdata.toString());
									kaquanadapter.notifyDataSetChanged();
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
	public void initView() {
		im_b5_2_back_btn = (ImageView) findViewById(R.id.im_b5_2_back_btn);
		tv_quanbu = (TextView)findViewById(R.id.tv_quanbu);
		tv_weizhifu = (TextView)findViewById(R.id.tv_weizhifu);
		tv_tixing = (TextView)findViewById(R.id.tv_tixing);
		b3_hongdi_quanbu = (ImageView) findViewById(R.id.b3_hongdi_quanbu);//全部 --下方黄条
		b3_hongdi_weizhifu = (ImageView) findViewById(R.id.b3_hongdi_weizhifu);//未支付 --下方黄条
		b3_hongdi_tixing = (ImageView) findViewById(R.id.b3_hongdi_tixing);//提醒--下方黄条
		list_huodong = (ListView) findViewById(R.id.list_huodong);
		
		setListener(tv_quanbu,tv_weizhifu,tv_tixing,im_b5_2_back_btn);
	}
	
	@Override
	public void  onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
        case R.id.im_b5_2_back_btn:
        		this.finish();
        	break;
        case R.id.tv_quanbu:
        		RequestDailog.showDialog(this, "正在加载数据，请稍后");
	        	status = 0;
	        	setVisible();
	        	b3_hongdi_quanbu.setVisibility(View.VISIBLE);
	        	setTextColor();
	        	tv_quanbu.setTextColor(getResources().getColor(R.color.all_huang_color));
         	setAdapter();
         	getServiceData();
        	break;
        case R.id.tv_weizhifu:
        		RequestDailog.showDialog(this, "正在加载数据，请稍后");
	        	status = 1;
	        	setVisible();
	        	b3_hongdi_weizhifu.setVisibility(View.VISIBLE);
	        	setTextColor();
	        	tv_weizhifu.setTextColor(getResources().getColor(R.color.all_huang_color));
	        	setAdapter();
	        	getServiceData();
        	break;
        case R.id.tv_tixing:
        		RequestDailog.showDialog(this, "正在加载数据，请稍后");
	        	status = 2;
	        	setVisible();
	        	b3_hongdi_tixing.setVisibility(View.VISIBLE);
	        	setTextColor();
	        	tv_tixing.setTextColor(getResources().getColor(R.color.all_huang_color));
	        	setAdapter();
	        	getServiceData();
        	break;
        default:
        	break;
        }
        }
	
	//设置所有黄条默认屏蔽
	public void setVisible(){
    	b3_hongdi_quanbu.setVisibility(View.INVISIBLE);
    	b3_hongdi_weizhifu.setVisibility(View.INVISIBLE);
    	b3_hongdi_tixing.setVisibility(View.INVISIBLE);
	}
	
	//设置活动字体颜色为黑色
	public void setTextColor(){
		tv_quanbu.setTextColor(Color.BLACK);
		tv_weizhifu.setTextColor(Color.BLACK);
		tv_tixing.setTextColor(Color.BLACK);
	}
	@Override  
	protected void onStop() {  
	    // TODO Auto-generated method stub  
	    super.onStop();  
	    mRequestQueue.cancelAll(this);  
	}  
}