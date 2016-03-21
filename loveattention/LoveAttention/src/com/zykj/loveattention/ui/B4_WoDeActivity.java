package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.JinRiXinDan;
import com.zykj.loveattention.data.UserInfo;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.CircularImage;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月8日	我的
 *
 */
public class B4_WoDeActivity extends BaseActivity {
	private ImageView im_denglu,im_gouwuche;//登录
	private CircularImage iv_imagehead;// 登录成功之后显示
	private ImageView iv_mymessage;//我的消息
	private LinearLayout ll_wodeqianbao;//我的钱包
	private LinearLayout ll_wodekaquan;//我的卡券
	private RelativeLayout rl_wodexiaoxi;//我的消息
	private RelativeLayout rl_wodeyaoqing;//我的邀请
	private RelativeLayout rl_wodeguanzhu;//我的关注
	private RelativeLayout rl_wodeyuyue;//我的预约
	private RelativeLayout rl_wodedingdan;//我的订单
	private RelativeLayout rl_wodeshoucang;//我的收藏
	private String isdenglu="";//是否登录
	private LinearLayout ll_yidenglu;//登录后
	private LinearLayout ll_gerenziliao;//个人资料
	private TextView tv_username;//用户昵称
	private TextView tv_invitecode;//邀请码
	private RequestQueue mRequestQueue; 
	private UserInfo uinfo;
	
	@Override
	protected void onResume() {
		super.onResume();
		if (isLoged()) {
			GetUserInfo();//用户信息 
			//请求头像，如果没有头像，提示用户上传头像
			ll_yidenglu.setVisibility(View.VISIBLE);
			im_denglu.setVisibility(View.GONE);
			
		}else {
			//未登录，不做操作
			ll_yidenglu.setVisibility(View.GONE);
			im_denglu.setVisibility(View.VISIBLE);
		}
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_wode);
		mRequestQueue =  Volley.newRequestQueue(this);
		initView();
	}


	public void initView() {
		im_denglu = (ImageView) findViewById(R.id.im_denglu);
		iv_imagehead = (CircularImage) findViewById(R.id.iv_imagehead);
		ll_wodeqianbao = (LinearLayout) findViewById(R.id.ll_wodeqianbao);
		ll_wodekaquan = (LinearLayout) findViewById(R.id.ll_wodekaquan);
		rl_wodexiaoxi = (RelativeLayout) findViewById(R.id.rl_wodexiaoxi);
		rl_wodeyaoqing = (RelativeLayout) findViewById(R.id.rl_wodeyaoqing);
		rl_wodeguanzhu = (RelativeLayout) findViewById(R.id.rl_wodeguanzhu);
		rl_wodeyuyue = (RelativeLayout) findViewById(R.id.rl_wodeyuyue);
		rl_wodeshoucang = (RelativeLayout) findViewById(R.id.rl_wodeshoucang);
		rl_wodedingdan = (RelativeLayout) findViewById(R.id.rl_wodedingdan);
		ll_yidenglu = (LinearLayout) findViewById(R.id.ll_yidenglu);
		isdenglu = getIntent().getStringExtra("isdenglu");
		ll_gerenziliao = (LinearLayout) findViewById(R.id.ll_gerenziliao);
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_invitecode = (TextView) findViewById(R.id.tv_invitecode);
		iv_mymessage = (ImageView) findViewById(R.id.iv_mymessage);
		im_gouwuche = (ImageView) findViewById(R.id.im_gouwuche);
	/*	if (isLoged()) {
			//请求头像，如果没有头像，提示用户上传头像
			ll_yidenglu.setVisibility(View.VISIBLE);
			im_denglu.setVisibility(View.GONE);
			
		}else {
			//未登录，不做操作
			ll_yidenglu.setVisibility(View.GONE);
			im_denglu.setVisibility(View.VISIBLE);
		}
		*/
		setListener(im_denglu,iv_mymessage,ll_wodeqianbao,ll_wodekaquan,rl_wodexiaoxi,
				rl_wodeyaoqing,rl_wodeguanzhu,rl_wodeyuyue,rl_wodeshoucang,
				ll_gerenziliao,rl_wodedingdan,im_gouwuche);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_denglu:
				Intent itdenglu = new Intent();
				itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
				startActivity(itdenglu);
			break;
		case R.id.ll_wodeqianbao: //我的钱包
			if (isLoged()) {
				Intent itqianbao = new Intent();
				itqianbao.setClass(B4_WoDeActivity.this, B4_2_WoDeQianBaoActivity.class);
				itqianbao.putExtra("id", getSharedPreferenceValue("id"));
				itqianbao.putExtra("name", getSharedPreferenceValue("name"));
				itqianbao.putExtra("touxianga", uinfo.getHeadportain());
				startActivity(itqianbao);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ll_wodekaquan://我的卡券
			if (isLoged()) {
				Intent itkaquan = new Intent();
				itkaquan.setClass(B4_WoDeActivity.this, B4_3_WoDeKaQuanActivity.class);
				startActivity(itkaquan);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		//我的消息
		case R.id.rl_wodexiaoxi:
		case R.id.iv_mymessage:
			if (isLoged()) {
				Intent itxiaoxi = new Intent();
				itxiaoxi.setClass(B4_WoDeActivity.this, B4_4_WoDeXiaoXiActivity.class);
				startActivity(itxiaoxi);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodeyaoqing://我的邀请
			if (isLoged()) {
				Intent ityaoqing = new Intent();
				ityaoqing.setClass(B4_WoDeActivity.this, B4_5_WoDeYaoQingActivity.class);
				ityaoqing.putExtra("touxianga", uinfo.getHeadportain());
				startActivity(ityaoqing);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rl_wodeguanzhu://我的关注
			if (isLoged()) {
				Intent itguanzhu = new Intent();
				itguanzhu.setClass(B4_WoDeActivity.this, B4_6_WoDeGuanZhuActivity.class);
				startActivity(itguanzhu);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodedingdan://我的订单
			
			if (isLoged()) {
				Intent itdingdan = new Intent();
				itdingdan.setClass(B4_WoDeActivity.this, B4_7_WoDeDingdan.class);
				startActivity(itdingdan);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rl_wodeshoucang:
			if (isLoged()) {
				Intent itshoucang = new Intent();
				itshoucang.setClass(B4_WoDeActivity.this, B4_9_WoDeShouCangActivity.class);
				startActivity(itshoucang);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodeyuyue:
			if (isLoged()) {
				Intent ityuyue = new Intent();
				ityuyue.setClass(B4_WoDeActivity.this, B4_8_WoDeYuYueActivity.class);
				startActivity(ityuyue);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.ll_gerenziliao:
			if (isLoged()) {
				Intent itziliao = new Intent();
				itziliao.setClass(B4_WoDeActivity.this, B4_10_WoDeZiLiaoActivity.class);
				itziliao.putExtra("uinfomodel", uinfo);
				startActivity(itziliao);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.im_gouwuche:
			if (isLoged()) {
				Intent itgwc = new Intent();
				itgwc.setClass(B4_WoDeActivity.this, B4_11_ShoppingCartActivity.class);
				startActivity(itgwc);
			}else {
				Toast.makeText(this, "您未登录，请先登录", Toast.LENGTH_LONG).show();
			}
		break;
		default:
			break;

		}
	}
	
	private void GetUserInfo() {
		/** 用户资料 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		String jsonuinfo = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_memberdetail(jsonuinfo),null,new Response.Listener<JSONObject>() {
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
	            	JSONObject status;
					try {
						status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) // 成功
						{
							JSONObject jsonob = response.getJSONObject("data");
							uinfo = com.alibaba.fastjson.JSONObject.parseObject(jsonob.toString(), UserInfo.class);
							tv_invitecode.setText("我的邀请码："+uinfo.getInvitecode());
							tv_username.setText(uinfo.getName());
							ImageLoader.getInstance().displayImage(AppValue.ImgUrl+uinfo.getHeadportain(), iv_imagehead);
						}else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B4_WoDeActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
	
	
	public  boolean isLoged() {
		String isLoged = null;
		if (getSharedPreferenceValue("isLoged")!=null) {
			isLoged = getSharedPreferenceValue("isLoged");
			if (isLoged.equals("1")) {
				return true;
			}else {
				return false;
			}
		}else {
			putSharedPreferenceValue("isLoged", "0");
			return false;
		}
	}
	
}