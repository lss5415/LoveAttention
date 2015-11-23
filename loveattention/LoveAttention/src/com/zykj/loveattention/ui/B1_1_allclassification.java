package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
/**
 * 全部分类
 * @author zhuyikun
 *
 */

public class B1_1_allclassification extends BaseActivity {
	private ImageView iv_b1_all_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_1_allclassification);
		initUI();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_b1_all_back:
			this.finish();
			break;

		default:
			break;
		}
	}
	private void initUI() {
		// TODO Auto-generated method stub
		iv_b1_all_back = (ImageView) findViewById(R.id.iv_b1_all_back);
		
		setListener(iv_b1_all_back);
			
	}
}
