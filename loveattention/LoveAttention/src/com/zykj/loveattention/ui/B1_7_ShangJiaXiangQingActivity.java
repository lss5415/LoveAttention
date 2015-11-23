package com.zykj.loveattention.ui;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.DianPuGoodsInfo;
import com.zykj.loveattention.data.MerchantyhInfo;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;



public class B1_7_ShangJiaXiangQingActivity extends BaseActivity {
	private ImageView b17suggest_back,im_imgpath;//返回,商家详情图片
	private RelativeLayout rl_youhuiquan;//我的优惠券
	private TextView tv_fancaidan,tv_tuwen;//翻菜单,图文
	private RelativeLayout rl_pingjia;//评价
	private String merchantid;
	private JSONObject merchantdetail;
	private TextView tv_namea,tv_remark,tv_opentime,tv_telephone,tv_address,tv_perperso;//店铺名，备注，营业时间，商家电话，商家地址，人均消费
	private RequestQueue mRequestQueue; 
	private TextView tv_youhuihuodong,tv_chanpinxinxi;//优惠活动，产品信息
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_shangjiaxiangqing);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this);  
		requestData();
	}


	public void initView() {
		b17suggest_back = (ImageView) findViewById(R.id.b17suggest_back);
		rl_youhuiquan = (RelativeLayout) findViewById(R.id.rl_youhuiquan);
		tv_fancaidan = (TextView) findViewById(R.id.tv_fancaidan);
		rl_pingjia = (RelativeLayout) findViewById(R.id.rl_pingjia);
		tv_tuwen = (TextView) findViewById(R.id.tv_tuwen);
		merchantid = getIntent().getStringExtra("dpid");
		im_imgpath = (ImageView) findViewById(R.id.im_imgpath);
		tv_namea = (TextView) findViewById(R.id.tv_namea);
		tv_remark = (TextView) findViewById(R.id.tv_remark);
		tv_opentime = (TextView) findViewById(R.id.tv_opentime);
		tv_telephone = (TextView) findViewById(R.id.tv_telephone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_perperso = (TextView) findViewById(R.id.tv_perperso);
		tv_youhuihuodong = (TextView) findViewById(R.id.tv_youhuihuodong);
		tv_chanpinxinxi = (TextView) findViewById(R.id.tv_chanpinxinxi);
		
		setListener(b17suggest_back,rl_youhuiquan,tv_fancaidan,rl_pingjia,tv_tuwen,tv_youhuihuodong,tv_chanpinxinxi);
	}

	private void requestData() {
		/** 商家详情优惠活动 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", "1");
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.getMerChantDetailInfo(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject jobsj = response.getJSONObject("data");
						String dpgoodsinfo = jobsj.getString("goodsinfoList");
						List<DianPuGoodsInfo> dpgoodsinfolist = JSONArray.parseArray(dpgoodsinfo,DianPuGoodsInfo.class);
						String merchantyhinfo = jobsj.getString("merchantyhinfoList");
						List<MerchantyhInfo> meinfolist = JSONArray.parseArray(merchantyhinfo,MerchantyhInfo.class);
						merchantdetail = jobsj.getJSONObject("merchantdetail");
						
						ImageLoader.getInstance().displayImage(merchantdetail.getString("imgpath"), im_imgpath);
						tv_namea.setText(merchantdetail.getString("name"));
						tv_remark.setText("商家标签 ："+merchantdetail.getString("remark"));
						tv_opentime.setText("营业时间："+merchantdetail.getString("opentime"));
						tv_telephone.setText("商家电话："+merchantdetail.getString("telephone"));
						tv_address.setText(merchantdetail.getString("address"));
						tv_perperso.setText("人均消费"+merchantdetail.getString("perperson")+"元");
						
						
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}
	/*
	*//**
	 * 商家详情优惠活动
	 *//*
	private AsyncHttpResponseHandler res_getMerChantDetailInfo = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONObject jobsj = json.getJSONObject("data");
			String dpgoodsinfo = jobsj.getString("goodsinfoList");
			List<DianPuGoodsInfo> dpgoodsinfolist = JSONArray.parseArray(dpgoodsinfo,DianPuGoodsInfo.class);
			String merchantyhinfo = jobsj.getString("merchantyhinfoList");
			List<MerchantyhInfo> meinfolist = JSONArray.parseArray(merchantyhinfo,MerchantyhInfo.class);
			merchantdetail = jobsj.getJSONObject("merchantdetail");
			
			ImageLoader.getInstance().displayImage(merchantdetail.getString("imgpath"), im_imgpath);
			tv_name.setText(merchantdetail.getString("name"));
			tv_remark.setText(merchantdetail.getString("remark"));
			tv_opentime.setText(merchantdetail.getString("opentime"));
			tv_telephone.setText(merchantdetail.getString("telephone"));
			tv_address.setText(merchantdetail.getString("address"));
			tv_perperso.setText(merchantdetail.getString("perperson"));
			
			
//			String strArray = jsonObject.getString("list");
//			List<GuessLike> list = JSONArray.parseArray(strArray, GuessLike.class);
//			String strArray = jsonarry.getString("list");
//			List<JinRiXinDan> list = JSONArray.parseArray(jsonarry.toString(), JinRiXinDan.class);
			
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable throwable) {
			// TODO Auto-generated method stub
			super.onFailure(statusCode, headers, responseBody, throwable);
		}
		
	};*/
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.b17suggest_back:
			this.finish();
			break;
		case R.id.rl_youhuiquan:
			Intent yhq = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_1_BenDianKaQuan.class);
			startActivity(yhq);
			break;
		case R.id.tv_fancaidan:
			Intent fcd = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_2_FanCaiDanActivity.class);
			startActivity(fcd);
			break;
		case R.id.rl_pingjia:
			Intent pingjia = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_3_XiangQingActivity.class);
			pingjia.putExtra("leixing", "用户评价");
			startActivity(pingjia);
			break;
		case R.id.tv_tuwen:
			Intent tuwen = new Intent(B1_7_ShangJiaXiangQingActivity.this, B1_7_3_XiangQingActivity.class);
			tuwen.putExtra("leixing", "图文介绍");
			startActivity(tuwen);
			break;
		case R.id.tv_youhuihuodong:
			tv_youhuihuodong.setTextColor(R.color.youhuihuodong);
			tv_chanpinxinxi.setTextColor(Color.BLACK);
			break;
		case R.id.tv_chanpinxinxi:
			tv_youhuihuodong.setTextColor(Color.BLACK);
			tv_chanpinxinxi.setTextColor(R.color.youhuihuodong);
			break;
		default:
			break;

		}
	}

}