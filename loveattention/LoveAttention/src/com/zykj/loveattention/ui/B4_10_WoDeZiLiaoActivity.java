package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author lss 2015年8月14日	账户信息
 *
 */
public class B4_10_WoDeZiLiaoActivity extends BaseActivity {
	private ImageView im_b410_back_btn;//返回
	private LinearLayout ll_zhanghuxinxi;//账户信息
	private LinearLayout ll_shouhuodizhi;//收货地址
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_wodeziliao);
		initView();
	}


	public void initView() {
		im_b410_back_btn = (ImageView) findViewById(R.id.im_b410_back_btn);
		ll_zhanghuxinxi = (LinearLayout) findViewById(R.id.ll_zhanghuxinxi);
		ll_shouhuodizhi = (LinearLayout) findViewById(R.id.ll_shouhuodizhi);

		setListener(im_b410_back_btn,ll_zhanghuxinxi,ll_shouhuodizhi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_back_btn:
			this.finish();
			break;
		case R.id.ll_zhanghuxinxi:
			Intent itzhxx = new Intent();
			itzhxx.setClass(B4_10_WoDeZiLiaoActivity.this, B4_10_1_ZhangHuXinXiActivity.class);
			startActivity(itzhxx);
			break;
		case R.id.ll_shouhuodizhi:
			Intent itdenglu = new Intent();
			itdenglu.setClass(B4_10_WoDeZiLiaoActivity.this, B4_10_2_ShouHuoDiZhiActivity.class);
			startActivity(itdenglu);
			break;
		default:
			break;

		}
	}

}