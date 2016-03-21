package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Share;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView;

public class B1_6_2_YouHuiHuoDongActivity extends BaseActivity {
	private ImageView im_back;
	private XListView list_jinri;
	private String couponid;
	private RequestQueue mRequestQueue;
	private String lng="",lat="";
	private TextView tv_name,tv_juli,tv_manjian,tv_youxiaoqi,tv_yilingnum,tv_xianjia,tv_yuanjia,tv_jieshao;
	private TextView tv_lingqu,tv_shuoming,tv_haoyouchiyounum,tv_dianpu,tv_dizhi,tv_dianhua;
	private RelativeLayout rl_bg;
	private ImageView suggest_share;
	private String ShareContent ;
	private String ShareTitle;
	private String ShareUrl ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_2_youhuihuodong);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		KaQuanInfo();
	}
	
	public void initView(){
		im_back = (ImageView)findViewById(R.id.im_back);
		list_jinri = (XListView)findViewById(R.id.list_jinri);
		tv_name = (TextView)findViewById(R.id.tv_name);
		tv_juli = (TextView)findViewById(R.id.tv_juli);
		tv_manjian = (TextView)findViewById(R.id.tv_manjian);
		tv_youxiaoqi = (TextView)findViewById(R.id.tv_youxiaoqi);
		tv_yilingnum = (TextView)findViewById(R.id.tv_yilingnum);
		tv_xianjia = (TextView)findViewById(R.id.tv_xianjia);
		tv_yuanjia = (TextView)findViewById(R.id.tv_yuanjia);
		tv_jieshao = (TextView)findViewById(R.id.tv_jieshao);
		tv_shuoming = (TextView)findViewById(R.id.tv_shuoming);
		tv_haoyouchiyounum = (TextView)findViewById(R.id.tv_haoyouchiyounum);
		tv_dianpu = (TextView)findViewById(R.id.tv_dianpu);
		tv_dizhi = (TextView)findViewById(R.id.tv_dizhi);
		tv_dianhua = (TextView)findViewById(R.id.tv_dianhua);
		tv_lingqu = (TextView)findViewById(R.id.tv_lingqu);
		rl_bg = (RelativeLayout)findViewById(R.id.rl_bg);
		suggest_share = (ImageView)findViewById(R.id.suggest_share);
		
		couponid = getIntent().getStringExtra("couponid");
		lng = getSharedPreferenceValue("lng");
		lat = getSharedPreferenceValue("lat");
	/*	list_jinri.setDividerHeight(0);
		list_jinri.setPullLoadEnable(true);
		list_jinri.setXListViewListener(this);
		list_jinri.setOnItemClickListener(this);
		list_jinri.setAdapter(adapter);*/
		
		setListener(im_back,tv_lingqu,suggest_share);
	}
	
	@Override
	public void  onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back:
			this.finish();
			break;
        case R.id.tv_lingqu:
        	LingQu();
        	break;
        case R.id.suggest_share:
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);
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
	
	public void KaQuanInfo(){
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("couponid", couponid);
		map1.put("invitecode", "");
		map1.put("longitude", lng);
		map1.put("latitude", lat);
		String json = JsonUtils.toJson(map1);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_coupondetail(json), null,
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
								JSONObject jobs = response.getJSONObject("data");
								tv_name.setText(jobs.getString("name"));
								tv_juli.setText("距离"+jobs.getString("distance")+"米");
								tv_manjian.setText(jobs.getString("couponname"));
								tv_youxiaoqi.setText("有效期"+jobs.getString("effecttime"));
//								tv_yilingnum.setText(jobs.getString("couponcate"));
								tv_xianjia.setText("￥"+jobs.getString("couponprice"));
								tv_yuanjia.setText("￥"+jobs.getString("couponprice"));
								tv_jieshao.setText(jobs.getString("couponname"));
								tv_shuoming.setText(jobs.getString("couponintroduct"));
								if (jobs.getString("friendnum").equals("-1")) {
									tv_haoyouchiyounum.setText("好友持有0人");
								}else {
									tv_haoyouchiyounum.setText("好友持有"+jobs.getString("friendnum")+"人");
								}
								tv_dianpu.setText(jobs.getString("name"));
								tv_dizhi.setText("地址："+jobs.getString("address"));
								tv_dianhua.setText("电话："+jobs.getString("telephone"));
								rl_bg.setBackgroundColor(android.graphics.Color.parseColor(jobs.getString("couponcolor")));
								tv_lingqu.setBackgroundColor(android.graphics.Color.parseColor(jobs.getString("couponcolor")));
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_6_2_YouHuiHuoDongActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_6_2_YouHuiHuoDongActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
	
	public void LingQu(){
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("memberid", getSharedPreferenceValue("id"));
		map1.put("couponid", couponid);
		String json = JsonUtils.toJson(map1);
		RequestDailog.showDialog(this, "正在加载，请稍后");
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_confirmcard(json), null,
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
								Toast.makeText(B1_6_2_YouHuiHuoDongActivity.this,"卡券领取成功", Toast.LENGTH_LONG).show();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_6_2_YouHuiHuoDongActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_6_2_YouHuiHuoDongActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
}