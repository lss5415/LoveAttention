package com.zykj.loveattention.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_2_JiFenAdapter;
import com.zykj.loveattention.adapter.B4_6_GuanZhuAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.view.XListView;

/**
 * @author lss 2015年8月13日	我的关注
 *
 */
public class B4_6_WoDeGuanZhuActivity extends BaseActivity {
	private ImageView im_b46_back_btn;//返回
	private XListView lv_guanzhu;
	private B4_6_GuanZhuAdapter gzadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_6_wodeguanzhu);
		initView();
		setAdapter();
	}


	public void initView() {
		im_b46_back_btn = (ImageView) findViewById(R.id.im_b46_back_btn);
		lv_guanzhu = (XListView) findViewById(R.id.lv_guanzhu);

		setListener(im_b46_back_btn);
	}
	
	public void setAdapter(){
		for (int i = 0; i < 20; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("shijian", "3.15.10:20");
			itemData.put("bianliang", "100");
			itemData.put("miaoshu", "广告赚取");
			itemData.put("yue", "1000");
			data.add(itemData);
		}
		gzadapter = new B4_6_GuanZhuAdapter(this, data);
		lv_guanzhu.setAdapter(gzadapter);
		lv_guanzhu.setPullLoadEnable(true);
		lv_guanzhu.setPullRefreshEnable(true);
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		lv_guanzhu.setRefreshTime(str + "刷新");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b46_back_btn:
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
