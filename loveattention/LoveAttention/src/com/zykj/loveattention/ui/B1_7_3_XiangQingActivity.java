package com.zykj.loveattention.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

public class B1_7_3_XiangQingActivity extends BaseActivity {
	private ImageView suggest_back;//返回
	private TextView tv_remen,tv_zuixin,tv_jinqi;
	private ImageView b3_hongdi_remen,b3_hongdi_zuixin,b3_hongdi_jinqi;
	private RelativeLayout rl_tuwen;
	private LinearLayout ll_pingjia;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_3xiangqing);
		initView();
	}


	public void initView() {
		suggest_back = (ImageView) findViewById(R.id.suggest_back);
		tv_remen = (TextView)findViewById(R.id.tv_remen);
		tv_zuixin = (TextView)findViewById(R.id.tv_zuixin);
		tv_jinqi = (TextView)findViewById(R.id.tv_jinqi);
		b3_hongdi_remen = (ImageView) findViewById(R.id.b3_hongdi_remen);//商家介绍 --下方黄条
		b3_hongdi_zuixin = (ImageView) findViewById(R.id.b3_hongdi_zuixin);//图文介绍 --下方黄条
		b3_hongdi_jinqi = (ImageView) findViewById(R.id.b3_hongdi_jinqi);//用户评价 --下方黄条
		rl_tuwen = (RelativeLayout) findViewById(R.id.rl_tuwen);
		ll_pingjia = (LinearLayout) findViewById(R.id.ll_pingjia);
		String leixing = getIntent().getStringExtra("leixing");
		if (leixing.equals("图文介绍")) {
			rl_tuwen.setVisibility(View.VISIBLE);
        	ll_pingjia.setVisibility(View.GONE);
		}else if (leixing.equals("用户评价")) {
			rl_tuwen.setVisibility(View.GONE);
        	ll_pingjia.setVisibility(View.VISIBLE);
		}
		
		setListener(suggest_back,tv_remen,tv_zuixin,tv_jinqi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.suggest_back:
			this.finish();
			break;
        case R.id.tv_remen:
        	setVisible();
        	b3_hongdi_remen.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_remen.setTextColor(getResources().getColor(R.color.all_huang_color));
			Intent yhq = new Intent(B1_7_3_XiangQingActivity.this, B1_7_ShangJiaXiangQingActivity.class);
			startActivity(yhq);
        	
        	break;
        case R.id.tv_zuixin:
        	setVisible();
        	b3_hongdi_zuixin.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_zuixin.setTextColor(getResources().getColor(R.color.all_huang_color));
        	rl_tuwen.setVisibility(View.VISIBLE);
        	ll_pingjia.setVisibility(View.GONE);
        	break;
        case R.id.tv_jinqi:
        	setVisible();
        	b3_hongdi_jinqi.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_jinqi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	rl_tuwen.setVisibility(View.GONE);
        	ll_pingjia.setVisibility(View.VISIBLE);
        	
        	break;
		default:
			break;

		}
	}
	
	//设置所有黄条默认屏蔽
	public void setVisible(){
    	b3_hongdi_remen.setVisibility(View.INVISIBLE);
    	b3_hongdi_zuixin.setVisibility(View.INVISIBLE);
    	b3_hongdi_jinqi.setVisibility(View.INVISIBLE);
	}
	
	//设置活动字体颜色为黑色
	public void setTextColor(){
		tv_remen.setTextColor(Color.BLACK);
		tv_zuixin.setTextColor(Color.BLACK);
		tv_jinqi.setTextColor(Color.BLACK);
	}

}