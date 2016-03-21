package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.zykj.loveattention.adapter.JieSuanAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;

public class QueRenDingDanActivity extends BaseActivity{

	private ImageView querendingdd_back,im_zhifubao,im_weixin,im_xianjin;//返回
	private TextView tv_wodejifen,tv_wodejinbi,tv_wodejifen1,tv_wodejinbi1,tv_jiesuanqueren;//外送，到店
	private RadioButton rb_wodejifen,rb_wodejinbi;//外送，到店
	private AutoListView al_gouwulist;// 列表
	private JieSuanAdapter adapter;
	private int peisongstate=0;
	private int zhifu=0;
	private RequestQueue mRequestQueue;
	private LinearLayout order_add_addresss;
	private String addressid="",linkname,linkprovince,linkcity,linkcounty,linkaddress;
	private TextView tv_shouhuoren,tv_shouhuodizhi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fancaidanqueren);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
	}
	
	private void initView() {
		querendingdd_back = (ImageView) findViewById(R.id.querendingdd_back);
		al_gouwulist = (AutoListView) findViewById(R.id.al_gouwulist);
		tv_wodejifen = (TextView) findViewById(R.id.tv_wodejifen);
		tv_wodejinbi = (TextView) findViewById(R.id.tv_wodejinbi);
		tv_wodejifen1 = (TextView) findViewById(R.id.tv_wodejifen1);
		tv_wodejinbi1 = (TextView) findViewById(R.id.tv_wodejinbi1);
		rb_wodejifen = (RadioButton) findViewById(R.id.rb_wodejifen);
		rb_wodejinbi = (RadioButton) findViewById(R.id.rb_wodejinbi);
		im_zhifubao = (ImageView) findViewById(R.id.im_zhifubao);
		im_weixin = (ImageView) findViewById(R.id.im_weixin);
		im_xianjin = (ImageView) findViewById(R.id.im_xianjin);
		tv_jiesuanqueren = (TextView) findViewById(R.id.tv_jiesuanqueren);
		order_add_addresss = (LinearLayout) findViewById(R.id.order_add_addresss);
		tv_shouhuoren = (TextView) findViewById(R.id.tv_shouhuoren);
		tv_shouhuodizhi = (TextView) findViewById(R.id.tv_shouhuodizhi);
		
		rb_wodejifen.setOnClickListener(this);
		rb_wodejinbi.setOnClickListener(this);
		adapter = new JieSuanAdapter(QueRenDingDanActivity.this);
		al_gouwulist.setAdapter(adapter);
//		listview = (MyListView) findViewById(R.id.listview_orderlist);
//		adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
//		listview.setAdapter(adapter);
//		listview.setPullLoadEnable(true);
//		listview.setPullRefreshEnable(true);
//		listview.setXListViewListener(this);
		
		setListener(querendingdd_back,im_zhifubao,im_weixin,im_xianjin,tv_jiesuanqueren,order_add_addresss);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.querendingdd_back:
			this.finish();
			break;

		case R.id.rb_wodejifen: //点击 --到店
			tv_wodejifen.setVisibility(View.VISIBLE);
			tv_wodejinbi.setVisibility(View.VISIBLE);
			tv_wodejifen1.setVisibility(View.INVISIBLE);
			tv_wodejinbi1.setVisibility(View.INVISIBLE);
			peisongstate=0;
			break;
		case R.id.rb_wodejinbi://点击 --外送
			tv_wodejifen.setVisibility(View.INVISIBLE);
			tv_wodejinbi.setVisibility(View.INVISIBLE);
			tv_wodejifen1.setVisibility(View.VISIBLE);
			tv_wodejinbi1.setVisibility(View.VISIBLE);
			peisongstate=1;
			break;
		case R.id.im_zhifubao:
			setState();
			im_zhifubao.setImageResource(R.drawable.xuanzhong);
			zhifu=0;
			break;
		case R.id.im_weixin:
			setState();
			im_weixin.setImageResource(R.drawable.xuanzhong);
			zhifu=1;
			break;
		case R.id.im_xianjin:
			setState();
			zhifu=2;
			im_xianjin.setImageResource(R.drawable.xuanzhong);
			break;
		case R.id.tv_jiesuanqueren:
			if (addressid.length()<1) {
				Toast.makeText(QueRenDingDanActivity.this, "请先选择收货地址", Toast.LENGTH_LONG).show();
			}else {
				if (zhifu==0) {
					Toast.makeText(QueRenDingDanActivity.this, "支付宝暂不可用", Toast.LENGTH_LONG).show();
				}else if (zhifu==1) {
					Toast.makeText(QueRenDingDanActivity.this, "微信支付", Toast.LENGTH_LONG).show();
					JieSuan();
				}else {
					Toast.makeText(QueRenDingDanActivity.this, "现金支付", Toast.LENGTH_LONG).show();
					JieSuan();
				}
			}
			
			break;
		case R.id.order_add_addresss:
			Intent itdenglu = new Intent();
			itdenglu.setClass(QueRenDingDanActivity.this, B4_10_2_ShouHuoDiZhiActivity.class);
            startActivityForResult(itdenglu, 1000);
			break;
//		case R.id.tv_daodian://到店
//			TAG_H0 = 101;
//			setHorizontalLine(tv_daodian,tv_songhuo);
//			setTextA();//设置不同模式下导航栏的显示
//			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
//			listview.setAdapter(adapter);
//			break;
		default:
			break;
		}
	}
	
	public void setState(){
		im_zhifubao.setImageResource(R.drawable.weixuanzhong);
		im_weixin.setImageResource(R.drawable.weixuanzhong);
		im_xianjin.setImageResource(R.drawable.weixuanzhong);
	}

	public void JieSuan() {
		RequestDailog.showDialog(this, "数据加载中，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		
		String a = getSharedPreferenceValue("id");
		Toast.makeText(QueRenDingDanActivity.this, a, Toast.LENGTH_LONG).show();
		map.put("memberid", getSharedPreferenceValue("memberid"));
		map.put("gooddetail", "6|0.01|1");
		map.put("addressid", "1");
		map.put("payway", "0");
		map.put("score", "0");
		map.put("type", "0");
		map.put("message", "");
		String json = JsonUtils.toJson(map);
		Intent fcd = new Intent(QueRenDingDanActivity.this, B4_7_WoDeDingdan.class);
		startActivity(fcd);
		/*JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_ShoppingCartToorder(json), null,
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
//								cnxhdata.clear();
//								org.json.JSONArray array1 = response.getJSONArray("data");
//								for (int i = 0; i < array1.length(); i++) {
//									JSONObject jsonItem1 = array1.getJSONObject(i);
//									Map<String, String> map1 = new HashMap<String, String>();
//									map1.put("imgpath", jsonItem1.getString("imgpath"));
//									map1.put("perperson", jsonItem1.getString("perperson"));
//									map1.put("remark", jsonItem1.getString("remark"));
//									map1.put("stars", jsonItem1.getString("stars"));
//									map1.put("name", jsonItem1.getString("name"));
//									map1.put("merchantid", jsonItem1.getString("merchantid"));					
//									cnxhdata.add(map1);
//								}
//								syadapter = new B1_ShouYeAdapter(B1_ShouYeActivity.this, cnxhdata);
//								lv_shouyelist.setAdapter(syadapter);
							} else {// 失败,提示失败信息
//								String errdesc = status.getString("errdesc");
//								Toast.makeText(B1_ShouYeActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
//						Toast.makeText(B1_ShouYeActivity.this, "网络连接失败，请重试",
//								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);*/
	}
	
	 /**
     * 所有的Activity对象的返回值都是由这个方法来接收
     * requestCode:    表示的是启动一个Activity时传过去的requestCode值
     * resultCode：表示的是启动后的Activity回传值时的resultCode值
     * data：表示的是启动后的Activity回传过来的Intent对象
     * 
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		  if (resultCode == 1001) {
	           addressid = data.getStringExtra("addressid");
	           linkname = data.getStringExtra("linkname");
	           linkprovince = data.getStringExtra("linkprovince");
	           linkcity = data.getStringExtra("linkcity");
	           linkcounty = data.getStringExtra("linkcounty");
	           linkaddress = data.getStringExtra("linkaddress");
	           tv_shouhuoren.setText("收货人："+linkname);
	           tv_shouhuodizhi.setText(linkprovince+linkcity+linkcounty+linkaddress);
	        }
	}
    
}
