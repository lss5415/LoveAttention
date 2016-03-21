package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_ShouYeAdapter;
import com.zykj.loveattention.adapter.B4_7_OrderStatusAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.MyListView;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView.IXListViewListener;

public class B4_7_WoDeDingdan extends BaseActivity implements IXListViewListener {

	private  ImageView im_b47_back;//返回
	private  TextView tv_daodian;//到店消费
	private  TextView tv_songhuo;//送货上门
	
	private LinearLayout ll_quanbudingdan;//全部订单
	private LinearLayout ll_weifukuan;//未付款
	private LinearLayout ll_weixiaofei;//未消费
	private LinearLayout ll_daipingjia;//待评价
	
	private View v101;
	private View v102;
	private View v103;
	private View v104;
	
	private TextView tv_fukuan;//未付款+已付款
	private TextView tv_xiaofei;//未消费＋未付款
	private TextView tv_comment;//待评价＋已收货
	private int TAG_H0 = 101;
	private int status = 10;
//  order_state 订单状态（全部订单:10,未付款:20,未消费:30,待评价:40）
    private static final int ALLORDER    = 10;
    private static final int WEIFUKUAN   = 20;
    private static final int WEIXIAOFEI  = 30;
    private static final int DAIPINGJIA  = 40;
	
	private MyListView listview;
	private B4_7_OrderStatusAdapter adapter;
	private List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
	private RequestQueue mRequestQueue;
	private String json,type="0",state="1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_7_wodedingdan);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		DingDan();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b47_back:
			this.finish();
			break;
		case R.id.tv_daodian://到店
			type="0";
			TAG_H0 = 101;
			setHorizontalLine(tv_daodian,tv_songhuo);
			setTextA();//设置不同模式下导航栏的显示
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.tv_songhuo://送货上门
			type="1";
			TAG_H0 = 102;
			setHorizontalLine(tv_songhuo,tv_daodian);
			setTextB();
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_quanbudingdan://全部订单
			state="-1";
			setLine(v101,v102,v103,v104);
			status = ALLORDER;
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_weifukuan://未付款
			Map<String, String> mapp = new HashMap<String, String>();
			state="0";
			json = JsonUtils.toJson(mapp);
			setLine(v102,v101,v103,v104);
			status = WEIFUKUAN;
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_weixiaofei://未消费
			state="1";
			setLine(v103,v102,v101,v104);
			status = WEIXIAOFEI;
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_daipingjia://待评价
			state="2";
			setLine(v104,v102,v103,v101);
			status = DAIPINGJIA;
			DingDan();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, 40, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		default:
			break;
		}
	}
	private void setTextB() {
		tv_fukuan.setText("未付款");
		tv_xiaofei.setText("已付款");
		tv_comment.setText("已收货");
	}
	private void setTextA() {
		tv_fukuan.setText("未付款");
		tv_xiaofei.setText("未消费");
		tv_comment.setText("已消费");
	}
	private void setHorizontalLine(TextView tv1,TextView tv2) {
		// TODO Auto-generated method stub
		tv1.setBackgroundResource(R.drawable.btn_b47);
		tv2.setBackgroundResource(R.drawable.btn_b47_white);
		
		tv1.setTextColor(this.getResources().getColor(R.color.white));
		tv2.setTextColor(this.getResources().getColor(R.color.yellow));
	}
	private void setLine(View v1,View v2,View v3,View v4) {
		// TODO Auto-generated method stub
		v1.setVisibility(View.VISIBLE);
		v2.setVisibility(View.INVISIBLE);
		v3.setVisibility(View.INVISIBLE);
		v4.setVisibility(View.INVISIBLE);
	}
	private void initView() {
		// TODO Auto-generated method stub
		im_b47_back = (ImageView) findViewById(R.id.im_b47_back);
		tv_daodian = (TextView) findViewById(R.id.tv_daodian);
		tv_songhuo = (TextView) findViewById(R.id.tv_songhuo);
		
		tv_fukuan = (TextView) findViewById(R.id.tv_fukuan);
		tv_xiaofei = (TextView) findViewById(R.id.tv_xiaofei);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		
		ll_quanbudingdan = (LinearLayout) findViewById(R.id.ll_quanbudingdan);
		ll_weifukuan = (LinearLayout) findViewById(R.id.ll_weifukuan);
		ll_weixiaofei = (LinearLayout) findViewById(R.id.ll_weixiaofei);
		ll_daipingjia = (LinearLayout) findViewById(R.id.ll_daipingjia);
		
		v101 = findViewById(R.id.v101);
		v102 = findViewById(R.id.v102);
		v103 = findViewById(R.id.v103);
		v104 = findViewById(R.id.v104);
		
		listview = (MyListView) findViewById(R.id.listview_orderlist);
		adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setXListViewListener(this);
		
		setListener(im_b47_back,tv_daodian,tv_songhuo,ll_quanbudingdan,ll_weifukuan,ll_weixiaofei,ll_daipingjia);
		
		setLine(v101,v102,v103,v104);
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	public void DingDan(){

		Toast.makeText(this, "type  ="+type+"state  ="+state, Toast.LENGTH_LONG).show();
		
		RequestDailog.showDialog(this, "数据加载中，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("type", type);
		map.put("state", state);
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_order(json), null,
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
								dataList.clear();
								org.json.JSONArray array1 = response.getJSONArray("data");
								for (int i = 0; i < array1.length(); i++) {
									JSONObject jsonItem1 = array1.getJSONObject(i);
									Map<String, String> map1 = new HashMap<String, String>();
									map1.put("mname", jsonItem1.getString("mname"));
									map1.put("userscore", jsonItem1.getString("userscore"));
									map1.put("arrivescore", jsonItem1.getString("arrivescore"));
									map1.put("ordernum", jsonItem1.getString("ordernum"));
									map1.put("state", jsonItem1.getString("state"));
									map1.put("goodlist", jsonItem1.getJSONArray("goodlist").toString());	
									map1.put("maddress", jsonItem1.getString("maddress"));	
									map1.put("payway", jsonItem1.getString("payway"));
									map1.put("type", jsonItem1.getString("type"));
									map1.put("confirmcode", jsonItem1.getString("confirmcode"));
									map1.put("mtelephone", jsonItem1.getString("mtelephone"));
									map1.put("id", jsonItem1.getString("id"));				
									map1.put("mimgpath", jsonItem1.getString("mimgpath"));				
									map1.put("price", jsonItem1.getString("price"));				
									map1.put("goodsdetail", jsonItem1.getString("goodsdetail"));				
									map1.put("datetime", jsonItem1.getString("datetime"));				
									dataList.add(map1);
								}
								adapter.notifyDataSetChanged();
//								syadapter = new B1_ShouYeAdapter(B1_ShouYeActivity.this, cnxhdata);
//								lv_shouyelist.setAdapter(syadapter);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B4_7_WoDeDingdan.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B4_7_WoDeDingdan.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
}
