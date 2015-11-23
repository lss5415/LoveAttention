package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author lss 2015年8月14日	新增地址
 *
 */
public class B4_10_2_1_XinZengDiZhiActivity extends BaseActivity {
	private ImageView im_b410_21_back_btn;//返回
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_21_xinzengdizhi);
		initView();
	}


	public void initView() {
		im_b410_21_back_btn = (ImageView) findViewById(R.id.im_b410_21_back_btn);

		setListener(im_b410_21_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_21_back_btn:
			this.finish();
			break;
		default:
			break;

		}
	}

}