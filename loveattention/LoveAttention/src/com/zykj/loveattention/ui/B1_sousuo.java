package com.zykj.loveattention.ui;


import java.util.HashMap;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.GridViewAdapter;
import com.zykj.loveattention.adapter.GridViewAdapter1;
import com.zykj.loveattention.base.BaseActivity;
/**
 * 搜索页面
 * @author zhuyikun
 *
 */
public class B1_sousuo extends BaseActivity {
	private  GridView gridView1;//热门搜索
	private  GridView gridView2;//历史记录
	private  Map<String, String> map = new HashMap<String, String>();
	private  Map<String, String> map1 = new HashMap<String, String>();
    private ImageView iv_b1_back;
    private EditText et_sousuo;
    private LinearLayout ll_cleanhistory;//清除历史信息
    private TextView textView2;
    private TextView tv_search;//搜索
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_sousuo);
		initView();
		setData();
		setAdapter();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_b1_back:
			this.finish();
			break;
		case R.id.ll_cleanhistory://清除历史信息
			textView2.setVisibility(View.GONE);
			gridView2.setVisibility(View.GONE);
			ll_cleanhistory.setVisibility(View.GONE);
			break;
		case R.id.tv_search://搜索
			Toast.makeText(this, et_sousuo.getText(), Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}
	private void setData() {
		// TODO Auto-generated method stub
		//假
		map.put("酸奶", "酸奶");
		map.put("植物果实", "植物果实");
		map.put("苹果", "苹果");
		map.put("草莓", "草莓");
		map.put("柚子文旦", "柚子文旦");
		map.put("火龙果", "火龙果");
		map.put("李子", "李子");
		map.put("草莓", "草莓");
		map.put("青提", "青提");
		
		map1.put("旅游","旅游");
		map1.put("奶茶屋","奶茶屋");
		map1.put("123", "123");
		map1.put("233", "233");
		map1.put("333", "333");
		map1.put("111", "111");
		map1.put("222", "222");
		map1.put("555", "555");
		map1.put("666", "666");
	}
	private void setAdapter() {
		// TODO Auto-generated method stub
		GridViewAdapter gridViewAdapter = new GridViewAdapter(this,map);
		GridViewAdapter1 gridViewAdapter1 = new GridViewAdapter1(this,map1);
		gridView1.setAdapter(gridViewAdapter);
		gridView2.setAdapter(gridViewAdapter1);
	}

	private void initView() {
		// TODO Auto-generated method stub
		gridView1 = (GridView) findViewById(R.id.gridView1);
		gridView1.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView2 = (GridView) findViewById(R.id.gridView2);
		gridView2.setSelector(new ColorDrawable(Color.TRANSPARENT));
		iv_b1_back = (ImageView) findViewById(R.id.iv_b1_back);
		et_sousuo = (EditText) findViewById(R.id.et_sousuo);
		et_sousuo.bringToFront();
		et_sousuo.setFocusable(true);   
		et_sousuo.setFocusableInTouchMode(true);   
		et_sousuo.requestFocus();  
		
		ll_cleanhistory = (LinearLayout) findViewById(R.id.ll_cleanhistory);
		textView2 = (TextView) findViewById(R.id.textView2);
		tv_search = (TextView) findViewById(R.id.tv_search);
		setListener(iv_b1_back,ll_cleanhistory,tv_search);
	}
}
