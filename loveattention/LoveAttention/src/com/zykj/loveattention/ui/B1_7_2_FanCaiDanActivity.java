package com.zykj.loveattention.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_7_2_FanCaiDanAdapter;
import com.zykj.loveattention.base.BaseActivity;

/**
 * @author LSS 2015年8月29日 上午11:03:21	翻菜单
 *
 */
public class B1_7_2_FanCaiDanActivity extends BaseActivity {
	private ImageView b172_back;// 返回
	private ListView lv_fancaidan;//翻菜单
	private B1_7_2_FanCaiDanAdapter fancandanadapter;
	private TextView tv_daodian,tv_songhuo,tv_daodian1,tv_songhuo1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_2_fancaidan);
		initView();
	}

	public void initView() {
		b172_back = (ImageView) findViewById(R.id.b172_back);
		lv_fancaidan = (ListView) findViewById(R.id.lv_fancaidan);
		tv_daodian = (TextView) findViewById(R.id.tv_daodian);
		tv_songhuo = (TextView) findViewById(R.id.tv_songhuo);
		tv_daodian1 = (TextView) findViewById(R.id.tv_daodian1);
		tv_songhuo1 = (TextView) findViewById(R.id.tv_songhuo1);
		
		fancandanadapter = new B1_7_2_FanCaiDanAdapter(this);
		lv_fancaidan.setAdapter(fancandanadapter);

		setListener(b172_back,tv_daodian,tv_songhuo,tv_daodian1,tv_songhuo1);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.b172_back:
			this.finish();
			break;
		case R.id.tv_daodian:
			tv_daodian.setVisibility(View.VISIBLE);
			tv_songhuo.setVisibility(View.VISIBLE);
			tv_daodian1.setVisibility(View.GONE);
			tv_songhuo1.setVisibility(View.GONE);
			break;
		case R.id.tv_songhuo:
			tv_daodian.setVisibility(View.GONE);
			tv_songhuo.setVisibility(View.GONE);
			tv_daodian1.setVisibility(View.VISIBLE);
			tv_songhuo1.setVisibility(View.VISIBLE);
			break;
		case R.id.tv_daodian1:
			tv_daodian.setVisibility(View.VISIBLE);
			tv_songhuo.setVisibility(View.VISIBLE);
			tv_daodian1.setVisibility(View.GONE);
			tv_songhuo1.setVisibility(View.GONE);
			break;
		case R.id.tv_songhuo1:
			tv_daodian.setVisibility(View.GONE);
			tv_songhuo.setVisibility(View.GONE);
			tv_daodian1.setVisibility(View.VISIBLE);
			tv_songhuo1.setVisibility(View.VISIBLE);
			break;
		// case R.id.im_denglu:
		// Intent itdenglu = new Intent();
		// itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
		// startActivity(itdenglu);
		// break;
		default:
			break;

		}
	}

}
