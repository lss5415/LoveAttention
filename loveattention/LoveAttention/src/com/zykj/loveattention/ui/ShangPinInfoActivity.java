package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_ShouYeAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.ChanPinCanShu;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ImageOptions;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Share;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class ShangPinInfoActivity extends BaseActivity {
	
	public ImageView sp_back;// 返回
	private RequestQueue mRequestQueue;
	private ImageView im_goods,im_shoucang,im_headportain,suggest_share;
	private String goodsid,shangjia;
	private TextView tv_goodname,tv_yuanjia,tv_xianjia,tv_liulan,tv_xiaoshou,tv_guige,tv_youfei;
	private TextView tv_name,tv_content,tv_intime,tv_tuwen;
	private RatingBar comment_rating_bar;
	private RelativeLayout rl_pingjia;//评价
	private String ShareContent,ShareTitle,ShareUrl;
	private LinearLayout guige;//规格选择
	private TextView tv_jiarucar,tv_yuyue,tv_goumai;
	private int carstate=0;
	private org.json.JSONArray goodspecifList;//规格数据
	private TextView tiaomu1,tiaomu2;
	private LinearLayout tiaomu1_zi,tiaomu2_zi,ll_sp_a1_back1;
	private GridView gview,gview1;
	private ArrayAdapter<ChanPinCanShu> sim_adapter,sim_adapter1;
	private String specif1="",specif2="";
	public List<ChanPinCanShu> cpcs,cpcs1;
	private String goodsprice="",goodsoldprice="";
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_shangpinxiangqing);
		mRequestQueue = Volley.newRequestQueue(this);
		initUI();
		GoodsInfo();
	}
	
	/**
	 * 绑定组件
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		sp_back = (ImageView) findViewById(R.id.sp_back);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname);
		tv_yuanjia = (TextView) findViewById(R.id.tv_yuanjia);
		tv_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tv_xianjia = (TextView) findViewById(R.id.tv_xianjia);
		tv_liulan = (TextView) findViewById(R.id.tv_liulan);
		tv_xiaoshou = (TextView) findViewById(R.id.tv_xiaoshou);
		tv_guige = (TextView) findViewById(R.id.tv_guige);
		tv_youfei = (TextView) findViewById(R.id.tv_youfei);
		im_goods = (ImageView) findViewById(R.id.im_goods);
		im_shoucang = (ImageView) findViewById(R.id.im_shoucang);
		rl_pingjia = (RelativeLayout) findViewById(R.id.rl_pingjia);
		suggest_share = (ImageView) findViewById(R.id.suggest_share);
		tv_tuwen = (TextView) findViewById(R.id.tv_tuwen);
		//评论
		im_headportain = (ImageView) findViewById(R.id.im_headportain);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_intime = (TextView) findViewById(R.id.tv_intime);
		comment_rating_bar = (RatingBar) findViewById(R.id.comment_rating_bar);
		guige = (LinearLayout) findViewById(R.id.guige);
		tv_jiarucar = (TextView) findViewById(R.id.tv_jiarucar);
		tv_yuyue = (TextView) findViewById(R.id.tv_yuyue);
		tv_goumai = (TextView) findViewById(R.id.tv_goumai);
		ll_sp_a1_back1 = (LinearLayout) findViewById(R.id.ll_sp_a1_back1);

		tiaomu1 = (TextView) findViewById(R.id.tiaomu1);
		tiaomu2 = (TextView) findViewById(R.id.tiaomu2);
		tiaomu1_zi = (LinearLayout) findViewById(R.id.tiaomu1_zi);
		tiaomu2_zi = (LinearLayout) findViewById(R.id.tiaomu2_zi);
		gview = (GridView) findViewById(R.id.gview);
		gview1 = (GridView) findViewById(R.id.gview1);
		
		try {
			goodsid = getIntent().getStringExtra("goodsid");
			shangjia = getIntent().getStringExtra("shangjia");
		} catch (Exception e) {
			goodsid="";
			shangjia = "";
		}
		try {
			gview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					
					specif1 = cpcs.get(position).getSpecifid();
				}
			});
			gview1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					
					specif2 = cpcs1.get(position).getSpecifid();
				}
			});
		} catch (Exception e) {
			
		}
		
		
		setListener(sp_back,rl_pingjia,suggest_share,tv_tuwen,im_shoucang,tv_jiarucar,tv_yuyue,tv_goumai,ll_sp_a1_back1);//绑定点击事件
	}
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.sp_back:
			ShangPinInfoActivity.this.finish();
			break;
		case R.id.rl_pingjia:
			Intent pingjia = new Intent(ShangPinInfoActivity.this, B1_7_3_XiangQingActivity.class);
			pingjia.putExtra("leixing", "用户评价");
			pingjia.putExtra("isgoods", "1");
			pingjia.putExtra("goodsid", goodsid);
			startActivity(pingjia);
			break;
		case R.id.suggest_share:
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);
			break;
		case R.id.tv_tuwen:
//			Intent tuwen = new Intent(ShangPinInfoActivity.this, B1_7_3_XiangQingActivity.class);
//			tuwen.putExtra("leixing", "图文介绍");
////			tuwen.putParcelableArrayListExtra("pinglunlist", pinglunlist);
//			tuwen.putExtra("merchantid", goodsid);
//			startActivity(tuwen);
			Intent pingjiaa = new Intent(ShangPinInfoActivity.this, B1_7_3_XiangQingActivity.class);
			pingjiaa.putExtra("leixing", "用户评价");
			pingjiaa.putExtra("isgoods", "1");
			pingjiaa.putExtra("goodsid", goodsid);
			startActivity(pingjiaa);
			break;
		case R.id.im_shoucang:
//			ShouCang();
			break;
		//加入购物车
		case R.id.tv_jiarucar:
			if (carstate>0) {
				carstate=0;
				guige.setVisibility(View.GONE);
			}else {
				carstate=1;
				guige.setVisibility(View.VISIBLE);
				try {
					if (goodspecifList.length()==0){
//						Toast.makeText(this, "无型号，直接加入购物车", Toast.LENGTH_LONG).show();
						AddToCar("");
					}else if (goodspecifList.length()==1){
						if (specif1.isEmpty()||specif1.equals("")) {
							Toast.makeText(this, "请选择型号1", Toast.LENGTH_LONG).show();
						}else {
//							Toast.makeText(this, "1个型号，型号为："+specif1, Toast.LENGTH_LONG).show();
							AddToCar(specif1);
						}
					}else if (goodspecifList.length()==2){
						if (specif1.isEmpty()||specif1.equals("")) {
							Toast.makeText(this, "请选择型号1", Toast.LENGTH_LONG).show();
						}else if (specif2.isEmpty()||specif2.equals("")){
							Toast.makeText(this, "请选择型号2："+specif1, Toast.LENGTH_LONG).show();
						}else {
//							Toast.makeText(this, "2个型号，型号为："+specif1+"型号2为："+specif2, Toast.LENGTH_LONG).show();
							AddToCar(specif1+","+specif2);
						}
					}
				} catch (Exception e) {
					
				}
			}
			
			
			
			break;
		//立即购买
		case R.id.tv_goumai:
			if (carstate>0) {
				carstate=0;
				guige.setVisibility(View.GONE);
			}else {
				carstate=1;
				guige.setVisibility(View.VISIBLE);
			}
			break;
		//立即预约
		case R.id.tv_yuyue:
			Intent ljyy = new Intent(this, LiJiYuYueActivity.class);
			ljyy.putExtra("goodsid", goodsid);
			ljyy.putExtra("shangjia", shangjia);
			ljyy.putExtra("goodname", tv_goodname.getText().toString());
			startActivity(ljyy);
			break;
		case R.id.ll_sp_a1_back1:
			carstate=0;
			guige.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}
	
	private void initShare() {
		ShareContent = "我的爱关注邀请码是:"+getSharedPreferenceValue("invitecode")+"，一起来爱关注吧，http://www.pgyer.com/ov1S";
		ShareTitle = "我的邀请码："+getSharedPreferenceValue("invitecode");
		ShareUrl = "http://www.pgyer.com/ov1S";
	}
	
	public void GoodsInfo(){
		/** 商家详情优惠活动 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("goodsid", goodsid);
		map.put("memberid", getSharedPreferenceValue("id"));
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_gooddetail(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject jobsj = response.getJSONObject("data");
						try {
							goodspecifList = jobsj.getJSONArray("goodspecifList");
							if (goodspecifList.length()==0) {
								
							}else if (goodspecifList.length()==1){
								tiaomu1.setVisibility(View.VISIBLE);
								tiaomu1_zi.setVisibility(View.VISIBLE);
								tiaomu1.setText(goodspecifList.getJSONObject(0).getString("speciftitle"));
//								com.alibaba.fastjson.JSONArray jsonarray1 = xuanxiang.getJSONArray(leixing1
//										.getJSONObject(0).getString("id").toString());
								String strarry = goodspecifList.getJSONObject(0).getJSONArray("speciflist").toString();
								cpcs = JSONArray.parseArray(strarry,ChanPinCanShu.class);
//								sim_adapter = new ArrayAdapter<ChanPinCanShu>(ShangPinInfoActivity.class,R.layout.itemss,R.id.textsss,cpcs);
//								sim_adapter = new ArrayAdapter<ChanPinCanShu>(this,R.layout.itemss,R.id.textsss,cpcs);
								// 配置适配器
//								gview.setAdapter(sim_adapter);
							}else if (goodspecifList.length()==2){
								tiaomu1.setVisibility(View.VISIBLE);
								tiaomu2.setVisibility(View.VISIBLE);
								tiaomu1_zi.setVisibility(View.VISIBLE);
								tiaomu2_zi.setVisibility(View.VISIBLE);
								tiaomu1.setText(goodspecifList.getJSONObject(0).getString("speciftitle"));
								tiaomu2.setText(goodspecifList.getJSONObject(1).getString("speciftitle"));
								
								//规格一
								String strarry = goodspecifList.getJSONObject(0).getJSONArray("speciflist").toString();
								cpcs = JSONArray.parseArray(strarry,
										ChanPinCanShu.class);
								sim_adapter = new ArrayAdapter<ChanPinCanShu>(ShangPinInfoActivity.this,
										R.layout.itemss, R.id.text, cpcs); 
								// 配置适配器
								gview.setAdapter(sim_adapter);
								
								//规格2
								String strarry1 = goodspecifList.getJSONObject(1).getJSONArray("speciflist").toString();
								cpcs1 = JSONArray.parseArray(strarry1,
										ChanPinCanShu.class);
								sim_adapter1 = new ArrayAdapter<ChanPinCanShu>(ShangPinInfoActivity.this,
										R.layout.itemss, R.id.text, cpcs1); 
								// 配置适配器
								gview1.setAdapter(sim_adapter1);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						JSONObject dpgoodsinfo = jobsj.getJSONObject("goodDetail");
						tv_goodname.setText(dpgoodsinfo.getString("name"));
						tv_yuanjia.setText("￥"+dpgoodsinfo.getString("oldprice"));
						tv_xianjia.setText("￥"+dpgoodsinfo.getString("price"));
						goodsprice = dpgoodsinfo.getString("price");
						goodsoldprice = dpgoodsinfo.getString("oldprice");
						tv_liulan.setText("浏览"+dpgoodsinfo.getString("visitnum"));
						tv_xiaoshou.setText("销售"+dpgoodsinfo.getString("paynum"));
						try {
							ImageLoader.getInstance().displayImage(AppValue.ImgUrl+dpgoodsinfo.getString("imgpath"),im_goods, ImageOptions.getOpstion());
						} catch (Exception e) {
							// TODO: handle exception
						}
						if (dpgoodsinfo.getString("iscollect").equals("0")) {//未收藏
							im_shoucang.setImageResource(R.drawable.scq);
						}else {//已收藏
							im_shoucang.setImageResource(R.drawable.sch);
						}
						if (dpgoodsinfo.getString("isfreepost").equals("0")) {//不包邮
							tv_youfei.setText("不包邮");
						}else {//包邮
							tv_youfei.setText("卖家包邮");							
						}
//						tv_guige.setText(dpgoodsinfo.getString(""));

						org.json.JSONArray jary = jobsj.getJSONArray("goodComment");
						try {
							ImageLoader.getInstance().displayImage(AppValue.ImgUrl+jary.getJSONObject(0).getString("image"), im_headportain);
						} catch (Exception e) {
							
						}
						try {
							tv_name.setText(jary.getJSONObject(0).getString("name"));
							tv_content.setText(jary.getJSONObject(0).getString("content"));
							tv_intime.setText(jary.getJSONObject(0).getString("intime"));
							comment_rating_bar.setRating(Float.parseFloat(jary.getJSONObject(0).getString("stars")));
						} catch (Exception e) {
							// TODO: handle exception
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
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}

	public void AddToCar(String goodspecif){
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("goodsid", goodsid);
		map.put("goodspecif", goodspecif);
		map.put("goodsprice", goodsprice);
		map.put("goodsoldprice", goodsoldprice);
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_shoppingCartAdd(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
	            	JSONObject status;
	            	try {
						status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) // 成功
						{
							Toast.makeText(ShangPinInfoActivity.this, "加入购物车成功", Toast.LENGTH_LONG	).show();
						} else {// 失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(ShangPinInfoActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
	}
	
	/*private void ShouCang(){
		*//** 商品收藏 *//*
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
//		map.put("objid", merchantid);
		String jsonmerchantid = JsonUtils.toJson(map);
//		HttpUtils.getMerChantDetailInfo(jsonmerchantid);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_attentionAdd(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject jobsj = response.getJSONObject("status");
						if (jobsj.getString("errdesc").equals("关注成功")) {
//							Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "关注成功", Toast.LENGTH_LONG	).show();
							im_shoucang.setImageResource(R.drawable.shoucanghou);
						}else {
//							Toast.makeText(B1_7_ShangJiaXiangQingActivity.this, "已取消关注", Toast.LENGTH_LONG	).show();
							im_shoucang.setImageResource(R.drawable.shoucangqian);
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
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}*/
}