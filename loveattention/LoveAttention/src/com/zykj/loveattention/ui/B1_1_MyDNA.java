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
 * 我的DNA页面
 * @author zhuyikun
 *
 */
public class B1_1_MyDNA extends BaseActivity {

	private ImageView btn_back;//返回
	private Button btn_meishi;//美食
	private Button btn_hunlian;// 婚恋
	private Button btn_gouwu;// 购物
	private Button btn_qinzi;// 亲子
	private Button btn_liren;// 丽人
	private Button btn_shouyiren;// 手艺人
	private Button btn_yundongjianshen;//运动健身
	private Button btn_shenghuofuwu;//生活服务
	private Button btn_xiuxianyule;//休闲娱乐
	private Button btn_next0;//下一步
	
	private int meishi_ischosed = 0; 
	private int hunlian_ischosed = 0; 
	private int gouwu_ischosed = 0; 
	private int qinzi_ischosed = 0; 
	private int liren_ischosed = 0; 
	private int shouyiren_ischosed = 0; 
	private int yundongjianshen_ischosed = 0; 
	private int shenghuofuwu_ischosed = 0; 
	private int xiuxianyule_ischosed = 0; 
	
	private int total_of_ischosed = 0; // 判断选择了多少个选项
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_1_mydna0);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		btn_back = (ImageView) findViewById(R.id.im_back_btn);
		btn_meishi = (Button) findViewById(R.id.btn_meishi);
		btn_hunlian = (Button) findViewById(R.id.btn_hunlian);
		btn_gouwu = (Button) findViewById(R.id.btn_gouwu);
		btn_qinzi = (Button) findViewById(R.id.btn_qinzi);
		btn_liren = (Button) findViewById(R.id.btn_liren);
		btn_shouyiren = (Button) findViewById(R.id.btn_shouyiren);
		btn_yundongjianshen = (Button) findViewById(R.id.btn_yundongjianshen);
		btn_shenghuofuwu = (Button) findViewById(R.id.btn_shenghuofuwu);
		btn_xiuxianyule = (Button) findViewById(R.id.btn_xiuxianyule);
		btn_next0 = (Button) findViewById(R.id.btn_next0);
		setListener(btn_back,
				btn_meishi,btn_hunlian,btn_gouwu,
				btn_qinzi,btn_liren,btn_shouyiren,
				btn_yundongjianshen,btn_shenghuofuwu,btn_xiuxianyule,
				btn_next0);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back_btn:
			this.finish();
			break;
		case R.id.btn_meishi:
			if (meishi_ischosed == 0) {
				btn_meishi.setBackgroundResource(R.drawable.btn_after);
				btn_meishi.setTextColor(android.graphics.Color.WHITE);
				meishi_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_meishi.setBackgroundResource(R.drawable.btn_before);
				btn_meishi.setTextColor(android.graphics.Color.BLACK);
				meishi_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_hunlian:
			if (hunlian_ischosed == 0) {
				btn_hunlian.setBackgroundResource(R.drawable.btn_after);
				btn_hunlian.setTextColor(android.graphics.Color.WHITE);
				hunlian_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_hunlian.setBackgroundResource(R.drawable.btn_before);
				btn_hunlian.setTextColor(android.graphics.Color.BLACK);
				hunlian_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_gouwu:
			if (gouwu_ischosed == 0) {
				btn_gouwu.setBackgroundResource(R.drawable.btn_after);
				btn_gouwu.setTextColor(android.graphics.Color.WHITE);
				gouwu_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_gouwu.setBackgroundResource(R.drawable.btn_before);
				btn_gouwu.setTextColor(android.graphics.Color.BLACK);
				gouwu_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_qinzi:
			if (qinzi_ischosed == 0) {
				btn_qinzi.setBackgroundResource(R.drawable.btn_after);
				btn_qinzi.setTextColor(android.graphics.Color.WHITE);
				qinzi_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_qinzi.setBackgroundResource(R.drawable.btn_before);
				btn_qinzi.setTextColor(android.graphics.Color.BLACK);
				qinzi_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_liren:
			if (liren_ischosed == 0) {
				btn_liren.setBackgroundResource(R.drawable.btn_after);
				btn_liren.setTextColor(android.graphics.Color.WHITE);
				liren_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_liren.setBackgroundResource(R.drawable.btn_before);
				btn_liren.setTextColor(android.graphics.Color.BLACK);
				liren_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_shouyiren:
			if (shouyiren_ischosed == 0) {
				btn_shouyiren.setBackgroundResource(R.drawable.btn_after);
				btn_shouyiren.setTextColor(android.graphics.Color.WHITE);
				shouyiren_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_shouyiren.setBackgroundResource(R.drawable.btn_before);
				btn_shouyiren.setTextColor(android.graphics.Color.BLACK);
				shouyiren_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_yundongjianshen:
			if (yundongjianshen_ischosed == 0) {
				btn_yundongjianshen.setBackgroundResource(R.drawable.btn_after);
				btn_yundongjianshen.setTextColor(android.graphics.Color.WHITE);
				yundongjianshen_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_yundongjianshen.setBackgroundResource(R.drawable.btn_before);
				btn_yundongjianshen.setTextColor(android.graphics.Color.BLACK);
				yundongjianshen_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_shenghuofuwu:
			if (shenghuofuwu_ischosed == 0) {
				btn_shenghuofuwu.setBackgroundResource(R.drawable.btn_after);
				btn_shenghuofuwu.setTextColor(android.graphics.Color.WHITE);
				shenghuofuwu_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_shenghuofuwu.setBackgroundResource(R.drawable.btn_before);
				btn_shenghuofuwu.setTextColor(android.graphics.Color.BLACK);
				shenghuofuwu_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_xiuxianyule:
			if (xiuxianyule_ischosed == 0) {
				btn_xiuxianyule.setBackgroundResource(R.drawable.btn_after);
				btn_xiuxianyule.setTextColor(android.graphics.Color.WHITE);
				xiuxianyule_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_xiuxianyule.setBackgroundResource(R.drawable.btn_before);
				btn_xiuxianyule.setTextColor(android.graphics.Color.BLACK);
				xiuxianyule_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_next0:
//			Toast.makeText(this, "next", Toast.LENGTH_LONG).show();
			if (total_of_ischosed==0) {
				Toast.makeText(this, "您还没有选择兴趣爱好哦～", Toast.LENGTH_LONG).show();
			}else {
				Intent it4next = new Intent(this,B1_2_MyWork.class);
				startActivity(it4next);
			}
			
			break;

		default:
			break;
		}
	}
}
