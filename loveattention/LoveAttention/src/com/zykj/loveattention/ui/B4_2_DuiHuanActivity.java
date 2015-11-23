package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

public class B4_2_DuiHuanActivity extends BaseActivity {
	private ImageView im_b42_back_btn;//返回
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_2_duihuan);
		initView();
	}


	public void initView() {
		im_b42_back_btn = (ImageView) findViewById(R.id.im_b42_back_btn);

		setListener(im_b42_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b42_back_btn:
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