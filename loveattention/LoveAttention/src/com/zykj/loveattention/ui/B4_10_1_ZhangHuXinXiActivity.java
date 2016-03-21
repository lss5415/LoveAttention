package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author lss 2015年8月14日	账户信息
 *
 */
public class B4_10_1_ZhangHuXinXiActivity extends BaseActivity {
	private ImageView im_b410_1_back_btn;//返回
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_1_zhanghuxinxi);
		initView();
	}


	public void initView() {
		im_b410_1_back_btn = (ImageView) findViewById(R.id.im_b410_1_back_btn);

		setListener(im_b410_1_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_1_back_btn:
			this.finish();
			break;
//		case R.id.im_denglu:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
//			startActivity(itdenglu);
//			break;
		default:
			break;

		}
	}

}