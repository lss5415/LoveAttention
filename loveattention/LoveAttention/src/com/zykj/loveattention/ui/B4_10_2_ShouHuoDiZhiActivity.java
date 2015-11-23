package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author lss 2015年8月14日	地址管理
 *
 */
public class B4_10_2_ShouHuoDiZhiActivity extends BaseActivity {
	private ImageView im_b410_2_back_btn;//返回
	private ImageView im_xinzengdizhi;//新增地址
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_2_dizhiguanli);
		initView();
	}


	public void initView() {
		im_b410_2_back_btn = (ImageView) findViewById(R.id.im_b410_2_back_btn);
		im_xinzengdizhi = (ImageView) findViewById(R.id.im_xinzengdizhi);

		setListener(im_b410_2_back_btn,im_xinzengdizhi);
	}
 
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_2_back_btn:
			this.finish();
			break;
		case R.id.im_xinzengdizhi:
			Intent itxzdz= new Intent();
			itxzdz.setClass(B4_10_2_ShouHuoDiZhiActivity.this, B4_10_2_1_XinZengDiZhiActivity.class);
			startActivity(itxzdz);
			break;
		default:
			break;

		}
	}

}