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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B173_PingLunAdapter;
import com.zykj.loveattention.adapter.B173_TuWenAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.PingLun;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.view.MyListView;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView.IXListViewListener;


public class B1_7_3_XiangQingActivity extends BaseActivity implements IXListViewListener{
	private ImageView suggest_back;//返回
	private TextView tv_remen,tv_zuixin,tv_jinqi;
	private ImageView b3_hongdi_remen,b3_hongdi_zuixin,b3_hongdi_jinqi;
	private RelativeLayout rl_tuwen;
	private LinearLayout ll_pingjia;
	private ArrayList<PingLun> pinglunlist;
	private MyListView lv_pingjia,lv_tuwen;//评价列表,图文列表
	private B173_PingLunAdapter pinglunadapter;
	private B173_TuWenAdapter tuwenadapter;
	private String merchantid="",goodsid,isgoods;//isgoods=0商家内评论，isgoods=1商品内评论
	private RequestQueue mRequestQueue;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_3xiangqing);
		mRequestQueue =  Volley.newRequestQueue(this);  
		initView();
		requestData();
	}


	public void initView() {
		suggest_back = (ImageView) findViewById(R.id.suggest_back);
		tv_remen = (TextView)findViewById(R.id.tv_remen);
		tv_zuixin = (TextView)findViewById(R.id.tv_zuixin);
		tv_jinqi = (TextView)findViewById(R.id.tv_jinqi);
		b3_hongdi_remen = (ImageView) findViewById(R.id.b3_hongdi_remen);//商家介绍 --下方黄条
		b3_hongdi_zuixin = (ImageView) findViewById(R.id.b3_hongdi_zuixin);//图文介绍 --下方黄条
		b3_hongdi_jinqi = (ImageView) findViewById(R.id.b3_hongdi_jinqi);//用户评价 --下方黄条
		rl_tuwen = (RelativeLayout) findViewById(R.id.rl_tuwen);
		ll_pingjia = (LinearLayout) findViewById(R.id.ll_pingjia);
		lv_pingjia = (MyListView) findViewById(R.id.lv_pingjia);
		lv_pingjia.setPullLoadEnable(true);
		lv_pingjia.setPullRefreshEnable(true);
		lv_pingjia.setXListViewListener(this);
		lv_pingjia.setRefreshTime();
		lv_tuwen = (MyListView) findViewById(R.id.lv_tuwen);
//		pinglunlist = getIntent().getParcelableArrayListExtra("pinglunlist");
		isgoods = getIntent().getStringExtra("isgoods");

		if (isgoods.equals("0")) {//商家内评论
			merchantid = getIntent().getStringExtra("merchantid");
		}else {//商品内评论
			goodsid = getIntent().getStringExtra("goodsid");
		}
		
		try {
			String leixing = getIntent().getStringExtra("leixing");
			if (leixing.equals("图文介绍")) {
				requestData();
				TuWenState();
			}else if (leixing.equals("用户评价")) {
	        	PingJiaState();
			}
		} catch (Exception e) {
			
		}
		
		setListener(suggest_back,tv_remen,tv_zuixin,tv_jinqi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.suggest_back:
			this.finish();
			break;
        case R.id.tv_remen:
        	setVisible();
        	b3_hongdi_remen.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_remen.setTextColor(getResources().getColor(R.color.all_huang_color));
//			Intent yhq = new Intent(B1_7_3_XiangQingActivity.this, B1_7_ShangJiaXiangQingActivity.class);
//			yhq.putExtra("merchantid", merchantid);
//			this.finish();
//			startActivity(yhq);
//			requestData();
        	this.finish();
        	break;
        case R.id.tv_zuixin:
        	TuWenState();
        	break;
        case R.id.tv_jinqi:
        	PingJiaState();        	
        	break;
		default:
			break;

		}
	}
	
	//图文时候的状态
	public void TuWenState(){
		setVisible();
    	b3_hongdi_zuixin.setVisibility(View.VISIBLE);
    	setTextColor();
    	tv_zuixin.setTextColor(getResources().getColor(R.color.all_huang_color));
    	rl_tuwen.setVisibility(View.VISIBLE);
    	ll_pingjia.setVisibility(View.GONE);
    	
		B173_TuWenAdapter goodadapter = new B173_TuWenAdapter(B1_7_3_XiangQingActivity.this, data);
		lv_tuwen.setAdapter(goodadapter);
    	lv_tuwen.setPullLoadEnable(true);
    	lv_tuwen.setPullRefreshEnable(true);
    	lv_tuwen.setXListViewListener(this);
    	lv_tuwen.setRefreshTime();
	}
	
	//评价时候的状态
	public void PingJiaState(){
		setVisible();
    	b3_hongdi_jinqi.setVisibility(View.VISIBLE);
    	setTextColor();
    	tv_jinqi.setTextColor(getResources().getColor(R.color.all_huang_color));
    	rl_tuwen.setVisibility(View.GONE);
    	ll_pingjia.setVisibility(View.VISIBLE);
    	PingJiaList();
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

	private void requestData() {
		/** 商家详情优惠活动 */
//		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_merchantResource(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
//	            	RequestDailog.closeDialog();
	            	try {
	            		data.clear();
						JSONObject jobsj = response.getJSONObject("data");
						org.json.JSONArray array = jobsj.getJSONArray("list");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonItem = array.getJSONObject(i);

							Map<String, String> map = new HashMap<String, String>();	
							map.put("objid", jsonItem.getString("objid"));
							map.put("description", jsonItem.getString("description"));
							map.put("linkurl", jsonItem.getString("linkurl"));
							map.put("type", jsonItem.getString("type"));
							map.put("intime", jsonItem.getString("intime"));
							map.put("rstate", jsonItem.getString("rstate"));
							data.add(map);
						}
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
              
            }  
        });  
        mRequestQueue.add(jr);  
		
	}
	public void PingJiaList(){
	/** 评论列表 */
	if (isgoods.equals("0")) {//商家评论列表
//		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		map.put("pagenumber", "1");
		map.put("pagesize", "20");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_merchantmoreComment(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
//	            	RequestDailog.closeDialog();
	            	try {
	            		JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							JSONObject jobsj = response.getJSONObject("data");
							String commentList = jobsj.getString("list");
							pinglunlist = (ArrayList<PingLun>) JSONArray.parseArray(commentList,PingLun.class);
					    	pinglunadapter = new B173_PingLunAdapter(B1_7_3_XiangQingActivity.this,pinglunlist);
							lv_pingjia.setAdapter(pinglunadapter);
						}else {//失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(B1_7_3_XiangQingActivity.this, errdesc, Toast.LENGTH_LONG).show();
						}
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
              
            }  
        });  
        mRequestQueue.add(jr);
	}else {
		//商品评论列表
//		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodid", goodsid);
		map.put("pagenumber", "1");
		map.put("pagesize", "20");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_goodmoreComment(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
	            	try {
	            		JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							JSONObject jobsj = response.getJSONObject("data");
							String commentList = jobsj.getString("commentlist");
							pinglunlist = (ArrayList<PingLun>) JSONArray.parseArray(commentList,PingLun.class);
					    	pinglunadapter = new B173_PingLunAdapter(B1_7_3_XiangQingActivity.this,pinglunlist);
							lv_pingjia.setAdapter(pinglunadapter);
						}else {//失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(B1_7_3_XiangQingActivity.this, errdesc, Toast.LENGTH_LONG).show();
						}
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
              
            }  
        });  
        mRequestQueue.add(jr);
	}
	  
	
}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}