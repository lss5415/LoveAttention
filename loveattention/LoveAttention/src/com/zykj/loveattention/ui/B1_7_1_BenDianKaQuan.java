package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_7_1_BenDiKaQuanAdapter;
import com.zykj.loveattention.base.BaseActivity;

public class B1_7_1_BenDianKaQuan extends BaseActivity {
	private ImageView img_b171back;//返回
	private ListView list_kaquan;
	private B1_7_1_BenDiKaQuanAdapter kaquanadapter;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_1_bendiankaquan);
		initView();
	}

	public void initView() {
		img_b171back = (ImageView) findViewById(R.id.img_b171back);
		list_kaquan = (ListView) findViewById(R.id.list_kaquan);
		kaquanadapter = new B1_7_1_BenDiKaQuanAdapter(this, data);
		list_kaquan.setAdapter(kaquanadapter);
		list_kaquan.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent kaquaninfo = new Intent(B1_7_1_BenDianKaQuan.this, B4_3_KaQuanInfoActivity.class);
				startActivity(kaquaninfo);
			}
		});

		setListener(img_b171back);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_b171back:
			this.finish();
			break;
		default:
			break;

		}
	}

}