package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

public class WaiSongDingDanXiangQing extends BaseActivity {
	private String state;
	private TextView tv_quxiaodingdan1,tv_qufukuan1,tv_qupingjia1;
	private LinearLayout ll_qxqfk1;
	private ImageView im_wsback_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waisongdingdanxiangqing);
		initView();
		
	}
	public void initView() {
		ll_qxqfk1 = (LinearLayout) findViewById(R.id.ll_qxqfk1);
		tv_quxiaodingdan1 = (TextView) findViewById(R.id.tv_quxiaodingdan1);
		tv_qufukuan1 = (TextView) findViewById(R.id.tv_qufukuan1);
		tv_qupingjia1 = (TextView) findViewById(R.id.tv_qupingjia1);
		im_wsback_btn = (ImageView) findViewById(R.id.im_wsback_btn);
		
		state = getIntent().getStringExtra("state");
		if (state.equals("0")) {
			tv_qupingjia1.setVisibility(View.GONE);
			tv_quxiaodingdan1.setText("取消订单");
			tv_qufukuan1.setText("去付款");
		}else if (state.equals("1")) {
			tv_qupingjia1.setVisibility(View.GONE);
			tv_quxiaodingdan1.setText("退订（联系商家）");
			tv_qufukuan1.setText("确认收货");
		}else if (state.equals("2")) {
			ll_qxqfk1.setVisibility(View.GONE);
			tv_qupingjia1.setText("去评价");
		}else {
			ll_qxqfk1.setVisibility(View.GONE);
			tv_qupingjia1.setVisibility(View.GONE);
		}
		setListener(im_wsback_btn);// 绑定点击事件
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_wsback_btn:
			WaiSongDingDanXiangQing.this.finish();
			break;
		default:
			break;

		}
	}

}