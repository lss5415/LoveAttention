package com.zykj.loveattention.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
/**
 * 选择你的生活状态 页面（从我的DNA第二个页面跳转过来）
 * @author zhuyikun
 *
 */
public class B1_2_MyLifeState extends BaseActivity {
	
	private ImageView btn_back;//返回
	private Button btn_danshen;//单身
	private Button btn_relian;// 热恋
	private Button btn_jiehun;// 结婚
	private Button btn_youbaobao;// 有宝宝
	
	private Button btn_next2;//下一步
	
	
	private int danshen_ischosed = 0; 
	private int relian_ischosed = 0; 
	private int jiehun_ischosed = 0; 
	private int youbaobao_ischosed = 0; 
	
	private int total_of_ischosed = 0; // 判断选择了多少个选项
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_1_mydna2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_back = (ImageView) findViewById(R.id.im_back_btn2);
		
		btn_danshen = (Button) findViewById(R.id.btn_danshen);
		btn_relian = (Button) findViewById(R.id.btn_relian);
		btn_jiehun = (Button) findViewById(R.id.btn_jiehun);
		btn_youbaobao = (Button) findViewById(R.id.btn_youbaobao);
		
		btn_next2 = (Button) findViewById(R.id.btn_next2);
		
		setListener(btn_back,
				btn_danshen,btn_relian,btn_jiehun,btn_youbaobao,
				btn_next2);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back_btn2:
			this.finish();
			break;
		case R.id.btn_danshen:
			if (danshen_ischosed == 0) {
				btn_danshen.setBackgroundResource(R.drawable.btn_after);
				btn_danshen.setTextColor(android.graphics.Color.WHITE);
				danshen_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_danshen.setBackgroundResource(R.drawable.btn_before);
				btn_danshen.setTextColor(android.graphics.Color.BLACK);
				danshen_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_relian:
			if (relian_ischosed == 0) {
				btn_relian.setBackgroundResource(R.drawable.btn_after);
				btn_relian.setTextColor(android.graphics.Color.WHITE);
				relian_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_relian.setBackgroundResource(R.drawable.btn_before);
				btn_relian.setTextColor(android.graphics.Color.BLACK);
				relian_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_jiehun:
			if (jiehun_ischosed == 0) {
				btn_jiehun.setBackgroundResource(R.drawable.btn_after);
				btn_jiehun.setTextColor(android.graphics.Color.WHITE);
				jiehun_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_jiehun.setBackgroundResource(R.drawable.btn_before);
				btn_jiehun.setTextColor(android.graphics.Color.BLACK);
				jiehun_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_youbaobao:
			if (youbaobao_ischosed == 0) {
				btn_youbaobao.setBackgroundResource(R.drawable.btn_after);
				btn_youbaobao.setTextColor(android.graphics.Color.WHITE);
				youbaobao_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_youbaobao.setBackgroundResource(R.drawable.btn_before);
				btn_youbaobao.setTextColor(android.graphics.Color.BLACK);
				youbaobao_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_next2:
			if (total_of_ischosed>1) {
				Toast.makeText(this, "只能选择一种生活状态哦～", Toast.LENGTH_LONG).show();
			}else if (total_of_ischosed == 0) {
				Toast.makeText(this, "您需要选择一种生活状态哦～", Toast.LENGTH_LONG).show();
			}else{
//				Intent it4next = new Intent(this,B1_2_MyLifeState.class);
//				startActivity(it4next);
				Intent itmain = new Intent(this,B1_ShouYeActivity.class);
				startActivity(itmain);
			}
			break;
		default:
			break;
		}
	}
}
