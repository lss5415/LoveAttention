package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author lss 2015年8月8日	我的
 *
 */
public class B4_WoDeActivity extends BaseActivity {
	private ImageView im_denglu;//登录
	private ImageView iv_imagehead;// 登录成功之后显示
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
	private LinearLayout ll_yidenglu;//登陆后
	private LinearLayout ll_gerenziliao;//个人资料
	private TextView tv_username;//用户昵称
	private TextView tv_invitecode;//邀请码
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_wode);
		initView();
	}


	public void initView() {
		im_denglu = (ImageView) findViewById(R.id.im_denglu);
		iv_imagehead = (ImageView) findViewById(R.id.iv_imagehead);
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
		iv_imagehead = (ImageView) findViewById(R.id.iv_imagehead);
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_invitecode = (TextView) findViewById(R.id.tv_invitecode);
		iv_mymessage = (ImageView) findViewById(R.id.iv_mymessage);
		if (isLoged()) {
			//请求头像，如果没有头像，提示用户上传头像
			ll_yidenglu.setVisibility(View.VISIBLE);
			im_denglu.setVisibility(View.GONE);
			tv_invitecode.setText(getSharedPreferenceValue("invitecode"));
			tv_username.setText(getSharedPreferenceValue("name"));
		}else {
			//未登陆，不做操作
			ll_yidenglu.setVisibility(View.GONE);
			im_denglu.setVisibility(View.VISIBLE);
		}
		
		setListener(im_denglu,iv_mymessage,ll_wodeqianbao,ll_wodekaquan,rl_wodexiaoxi,
				rl_wodeyaoqing,rl_wodeguanzhu,rl_wodeyuyue,rl_wodeshoucang,
				ll_gerenziliao,rl_wodedingdan,iv_imagehead);
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
		case R.id.iv_imagehead:
			Toast.makeText(this, "upLoadPhoto", Toast.LENGTH_LONG).show();
			break;
		case R.id.ll_wodeqianbao: //我的钱包
			if (isLoged()) {
				Intent itqianbao = new Intent();
				itqianbao.setClass(B4_WoDeActivity.this, B4_2_WoDeQianBaoActivity.class);
				itqianbao.putExtra("id", getSharedPreferenceValue("id"));
				itqianbao.putExtra("name", getSharedPreferenceValue("name"));
				startActivity(itqianbao);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ll_wodekaquan://我的卡券
			if (isLoged()) {
				Intent itkaquan = new Intent();
				itkaquan.setClass(B4_WoDeActivity.this, B4_3_WoDeKaQuanActivity.class);
				startActivity(itkaquan);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
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
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodeyaoqing://我的邀请
			if (isLoged()) {
				Intent ityaoqing = new Intent();
				ityaoqing.setClass(B4_WoDeActivity.this, B4_5_WoDeYaoQingActivity.class);
				startActivity(ityaoqing);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rl_wodeguanzhu://我的关注
			if (isLoged()) {
				Intent itguanzhu = new Intent();
				itguanzhu.setClass(B4_WoDeActivity.this, B4_6_WoDeGuanZhuActivity.class);
				startActivity(itguanzhu);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodedingdan://我的订单
			
			if (isLoged()) {
				Intent itdingdan = new Intent();
				itdingdan.setClass(B4_WoDeActivity.this, B4_7_WoDeDingdan.class);
				startActivity(itdingdan);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rl_wodeshoucang:
			if (isLoged()) {
				Intent itshoucang = new Intent();
				itshoucang.setClass(B4_WoDeActivity.this, B4_9_WoDeShouCangActivity.class);
				startActivity(itshoucang);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.rl_wodeyuyue:
			if (isLoged()) {
				Intent ityuyue = new Intent();
				ityuyue.setClass(B4_WoDeActivity.this, B4_8_WoDeYuYueActivity.class);
				startActivity(ityuyue);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.ll_gerenziliao:
			if (isLoged()) {
				Intent itziliao = new Intent();
				itziliao.setClass(B4_WoDeActivity.this, B4_10_WoDeZiLiaoActivity.class);
				startActivity(itziliao);
			}else {
				Toast.makeText(this, "您未登陆，请先登陆", Toast.LENGTH_LONG).show();
			}
			
			break;
		default:
			break;

		}
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