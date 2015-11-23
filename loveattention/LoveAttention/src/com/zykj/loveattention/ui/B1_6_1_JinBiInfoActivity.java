package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_5_JinRiXinDanAdapter;
import com.zykj.loveattention.adapter.b161_JinBiAdapter;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author LSS 2015年8月26日 上午9:35:19	金币商城列表
 *
 */
public class B1_6_1_JinBiInfoActivity extends BaseActivity {
	private ImageView im_b162_back_btn;//返回
	public ListView list_jinri;//展示列表
	private B1_5_JinRiXinDanAdapter xindanadapter;
	public TextView tv_xiaoliang,tv_jiage,tv_zuixin;//商户类型，全城，智能排序，筛选
	public ImageView b2_xiaoliang,b2_jiage,b2_zuixin;//底部黄条
	public ImageView rowdown1,rowdown2,rowdown3;//向下的指示尖头
	public ListView list_jinbi;//展示列表
	private b161_JinBiAdapter jinbiAdapter;//适配器
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_1_jinbiinfo);
		initView();
		jinbiAdapter = new b161_JinBiAdapter(this);
		list_jinbi.setAdapter(jinbiAdapter);
	}


	public void initView() {
		im_b162_back_btn = (ImageView) findViewById(R.id.im_b162_back_btn);
		list_jinri = (ListView) findViewById(R.id.list_jinri);
		rowdown1  = (ImageView) findViewById(R.id.rowdown1);
		rowdown2  = (ImageView) findViewById(R.id.rowdown2);
		rowdown3  = (ImageView) findViewById(R.id.rowdown3);
		b2_xiaoliang  = (ImageView) findViewById(R.id.b2_xiaoliang);
		b2_jiage  = (ImageView) findViewById(R.id.b2_jiage);
		b2_zuixin  = (ImageView) findViewById(R.id.b2_zuixin);
		tv_xiaoliang = (TextView) findViewById(R.id.tv_xiaoliang);
		tv_jiage = (TextView) findViewById(R.id.tv_jiage);
		tv_zuixin = (TextView) findViewById(R.id.tv_zuixin);
		list_jinbi = (ListView) findViewById(R.id.list_jinbi);
		list_jinbi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent itdenglu = new Intent();
				itdenglu.setClass(B1_6_1_JinBiInfoActivity.this, B1_6_2_YouHuiHuoDongActivity.class);
				startActivity(itdenglu);
			}
		});
		
		setListener(im_b162_back_btn,tv_xiaoliang,tv_jiage,tv_zuixin);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b162_back_btn:
			this.finish();
			break;
		case R.id.tv_xiaoliang://商户类型
			tv_xiaoliang.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_jiage.setTextColor(android.graphics.Color.BLACK);
			tv_zuixin.setTextColor(android.graphics.Color.BLACK);
			rowdown1.setImageResource(R.drawable.arrowdownyellow);
			rowdown2.setImageResource(R.drawable.arrowdowngrey);
			rowdown3.setImageResource(R.drawable.arrowdowngrey);
			b2_xiaoliang.setVisibility(View.VISIBLE);
			b2_jiage.setVisibility(View.INVISIBLE);
			b2_zuixin.setVisibility(View.INVISIBLE);
			
			break;
		case R.id.tv_jiage:
			tv_xiaoliang.setTextColor(android.graphics.Color.BLACK);
			tv_jiage.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_zuixin.setTextColor(android.graphics.Color.BLACK);
			rowdown1.setImageResource(R.drawable.arrowdowngrey);
			rowdown2.setImageResource(R.drawable.arrowdownyellow);
			rowdown3.setImageResource(R.drawable.arrowdowngrey);
			b2_xiaoliang.setVisibility(View.INVISIBLE);
			b2_jiage.setVisibility(View.VISIBLE);
			b2_zuixin.setVisibility(View.INVISIBLE);
			break;
		case R.id.tv_zuixin:
			tv_xiaoliang.setTextColor(android.graphics.Color.BLACK);
			tv_jiage.setTextColor(android.graphics.Color.BLACK);
			tv_zuixin.setTextColor(this.getResources().getColor(R.color.yellow));
			rowdown1.setImageResource(R.drawable.arrowdowngrey);
			rowdown2.setImageResource(R.drawable.arrowdowngrey);
			rowdown3.setImageResource(R.drawable.arrowdownyellow);
			b2_xiaoliang.setVisibility(View.INVISIBLE);
			b2_jiage.setVisibility(View.INVISIBLE);
			b2_zuixin.setVisibility(View.VISIBLE);
			break;
//		case R.id.tv_myyaoqingren:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_5_WoDeYaoQingActivity.this, B4_5_1_MyYaoQingRenActivity.class);
//			startActivity(itdenglu);
//			break;
		default:
			break;
		}
	}

}
