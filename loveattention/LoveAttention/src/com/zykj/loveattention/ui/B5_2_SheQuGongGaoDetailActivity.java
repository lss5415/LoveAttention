package com.zykj.loveattention.ui;

import com.zykj.loveattention.R;
import com.zykj.loveattention.R.id;
import com.zykj.loveattention.R.layout;
import com.zykj.loveattention.base.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class B5_2_SheQuGongGaoDetailActivity extends BaseActivity {

	private ImageView img_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b5_2__she_qu_gong_gao_detail);
		
		img_back = (ImageView) findViewById(R.id.im_b5_2_back_btn);
		
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				B5_2_SheQuGongGaoDetailActivity.this.finish();
			}
		});
	}

	

}
