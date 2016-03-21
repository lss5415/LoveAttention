package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

public class DaoDianDingDanXiangQing extends BaseActivity {
	private String state;
	private TextView tv_isfukuan,tv_quxiaodingdan,tv_qufukuan,tv_qupingjia;
	private LinearLayout ll_qxqfk;
	private ImageView im_ddback_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daodiandingdanxiangqing);
		initView();
		
	}
	public void initView() {
		tv_isfukuan = (TextView) findViewById(R.id.tv_isfukuan);
		ll_qxqfk = (LinearLayout) findViewById(R.id.ll_qxqfk);
		tv_quxiaodingdan = (TextView) findViewById(R.id.tv_quxiaodingdan);
		tv_qufukuan = (TextView) findViewById(R.id.tv_qufukuan);
		tv_qupingjia = (TextView) findViewById(R.id.tv_qupingjia);
		im_ddback_btn = (ImageView) findViewById(R.id.im_ddback_btn);
		
		state = getIntent().getStringExtra("state");
		if (state.equals("0")) {
			tv_isfukuan.setText("未付款");
			tv_qupingjia.setVisibility(View.GONE);
		}else if (state.equals("1")) {
			tv_isfukuan.setText("未消费");
			ll_qxqfk.setVisibility(View.GONE);
			tv_qupingjia.setText("退订（联系商家）");
		}else if (state.equals("2")) {
			tv_isfukuan.setText("已消费");
			ll_qxqfk.setVisibility(View.GONE);
			tv_qupingjia.setText("去评价");
		}else {
			tv_isfukuan.setVisibility(View.GONE);
			ll_qxqfk.setVisibility(View.GONE);
			tv_qupingjia.setVisibility(View.GONE);
		}
		setListener(im_ddback_btn);// 绑定点击事件
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_ddback_btn:
			DaoDianDingDanXiangQing.this.finish();
			break;
		default:
			break;

		}
	}

}