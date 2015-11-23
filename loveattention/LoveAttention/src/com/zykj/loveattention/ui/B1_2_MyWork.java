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
 * 选择你的职业 页面（从我的DNA第一个页面跳转过来）
 * @author zhuyikun
 *
 */
public class B1_2_MyWork extends BaseActivity {

	private ImageView btn_back;//返回
	private Button btn_jinrong;//金融
	private Button btn_it;// IT
	private Button btn_meiti;// 媒体
	private Button btn_canyin;// 餐饮
	private Button btn_lingshou;// 零售
	private Button btn_jiaoyu;// 教育
	private Button btn_xuesheng;//学生
	private Button btn_yiyao;//医药
	private Button btn_liren1;//丽人
	private Button btn_fuwuye;//服务业
	private Button btn_ziyouzhiye;//自由职业
	private Button btn_zhizaoye;//制造业
	
	private Button btn_next1;//下一步
	
	private int jinrong_ischosed = 0; 
	private int it_ischosed = 0; 
	private int meiti_ischosed = 0; 
	private int canyin_ischosed = 0; 
	private int lingshou_ischosed = 0; 
	private int xuesheng_ischosed = 0; 
	private int yiyao_ischosed = 0; 
	private int shenghuofuwu_ischosed = 0; 
	private int liren1_ischosed = 0; 
	private int fuwuye_ischosed = 0; 
	private int ziyouzhiye_ischosed = 0; 
	private int zhizaoye_ischosed = 0; 
	
	private int total_of_ischosed = 0; // 判断选择了多少个选项
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_1_mydna1);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		btn_back = (ImageView) findViewById(R.id.im_back_btn1);
		
		btn_jinrong = (Button) findViewById(R.id.btn_jinrong);
		btn_it = (Button) findViewById(R.id.btn_it);
		btn_meiti = (Button) findViewById(R.id.btn_meiti);
		
		btn_canyin = (Button) findViewById(R.id.btn_canyin);
		btn_lingshou = (Button) findViewById(R.id.btn_lingshou);
		btn_jiaoyu = (Button) findViewById(R.id.btn_jiaoyu);
		
		btn_xuesheng = (Button) findViewById(R.id.btn_xuesheng);
		btn_yiyao = (Button) findViewById(R.id.btn_yiyao);
		btn_liren1 = (Button) findViewById(R.id.btn_liren1);
		
		btn_fuwuye = (Button) findViewById(R.id.btn_fuwuye);
		btn_ziyouzhiye = (Button) findViewById(R.id.btn_ziyouzhiye);
		btn_zhizaoye = (Button) findViewById(R.id.btn_zhizaoye);
		
		btn_next1= (Button) findViewById(R.id.btn_next1);
		
		setListener(
				btn_back,
				btn_jinrong,btn_it,btn_meiti,
				btn_canyin,btn_lingshou,btn_jiaoyu,
				btn_xuesheng,btn_yiyao,btn_liren1,
				btn_fuwuye,btn_ziyouzhiye,btn_zhizaoye,
				btn_next1);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back_btn1:
			this.finish();
			break;
		case R.id.btn_jinrong:
			if (jinrong_ischosed == 0) {
				btn_jinrong.setBackgroundResource(R.drawable.btn_after);
				btn_jinrong.setTextColor(android.graphics.Color.WHITE);
				jinrong_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_jinrong.setBackgroundResource(R.drawable.btn_before);
				btn_jinrong.setTextColor(android.graphics.Color.BLACK);
				jinrong_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_it:
			if (it_ischosed == 0) {
				btn_it.setBackgroundResource(R.drawable.btn_after);
				btn_it.setTextColor(android.graphics.Color.WHITE);
				it_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_it.setBackgroundResource(R.drawable.btn_before);
				btn_it.setTextColor(android.graphics.Color.BLACK);
				it_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_meiti:
			if (meiti_ischosed == 0) {
				btn_meiti.setBackgroundResource(R.drawable.btn_after);
				btn_meiti.setTextColor(android.graphics.Color.WHITE);
				meiti_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_meiti.setBackgroundResource(R.drawable.btn_before);
				btn_meiti.setTextColor(android.graphics.Color.BLACK);
				meiti_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_canyin:
			if (canyin_ischosed == 0) {
				btn_canyin.setBackgroundResource(R.drawable.btn_after);
				btn_canyin.setTextColor(android.graphics.Color.WHITE);
				canyin_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_canyin.setBackgroundResource(R.drawable.btn_before);
				btn_canyin.setTextColor(android.graphics.Color.BLACK);
				canyin_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_lingshou:
			if (lingshou_ischosed == 0) {
				btn_lingshou.setBackgroundResource(R.drawable.btn_after);
				btn_lingshou.setTextColor(android.graphics.Color.WHITE);
				lingshou_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_lingshou.setBackgroundResource(R.drawable.btn_before);
				btn_lingshou.setTextColor(android.graphics.Color.BLACK);
				lingshou_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_jiaoyu:
			if (xuesheng_ischosed == 0) {
				btn_jiaoyu.setBackgroundResource(R.drawable.btn_after);
				btn_jiaoyu.setTextColor(android.graphics.Color.WHITE);
				xuesheng_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_jiaoyu.setBackgroundResource(R.drawable.btn_before);
				btn_jiaoyu.setTextColor(android.graphics.Color.BLACK);
				xuesheng_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_xuesheng:
			if (yiyao_ischosed == 0) {
				btn_xuesheng.setBackgroundResource(R.drawable.btn_after);
				btn_xuesheng.setTextColor(android.graphics.Color.WHITE);
				yiyao_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_xuesheng.setBackgroundResource(R.drawable.btn_before);
				btn_xuesheng.setTextColor(android.graphics.Color.BLACK);
				yiyao_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_yiyao:
			if (shenghuofuwu_ischosed == 0) {
				btn_yiyao.setBackgroundResource(R.drawable.btn_after);
				btn_yiyao.setTextColor(android.graphics.Color.WHITE);
				shenghuofuwu_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_yiyao.setBackgroundResource(R.drawable.btn_before);
				btn_yiyao.setTextColor(android.graphics.Color.BLACK);
				shenghuofuwu_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_liren1:
			if (liren1_ischosed == 0) {
				btn_liren1.setBackgroundResource(R.drawable.btn_after);
				btn_liren1.setTextColor(android.graphics.Color.WHITE);
				liren1_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_liren1.setBackgroundResource(R.drawable.btn_before);
				btn_liren1.setTextColor(android.graphics.Color.BLACK);
				liren1_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_fuwuye:
			if (fuwuye_ischosed == 0) {
				btn_fuwuye.setBackgroundResource(R.drawable.btn_after);
				btn_fuwuye.setTextColor(android.graphics.Color.WHITE);
				fuwuye_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_fuwuye.setBackgroundResource(R.drawable.btn_before);
				btn_fuwuye.setTextColor(android.graphics.Color.BLACK);
				fuwuye_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_ziyouzhiye:
			if (ziyouzhiye_ischosed == 0) {
				btn_ziyouzhiye.setBackgroundResource(R.drawable.btn_after);
				btn_ziyouzhiye.setTextColor(android.graphics.Color.WHITE);
				ziyouzhiye_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_ziyouzhiye.setBackgroundResource(R.drawable.btn_before);
				btn_ziyouzhiye.setTextColor(android.graphics.Color.BLACK);
				ziyouzhiye_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_zhizaoye:
			if (zhizaoye_ischosed == 0) {
				btn_zhizaoye.setBackgroundResource(R.drawable.btn_after);
				btn_zhizaoye.setTextColor(android.graphics.Color.WHITE);
				zhizaoye_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_zhizaoye.setBackgroundResource(R.drawable.btn_before);
				btn_zhizaoye.setTextColor(android.graphics.Color.BLACK);
				zhizaoye_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_next1:
//			Toast.makeText(this, "next", Toast.LENGTH_LONG).show();
			if (total_of_ischosed>1) {
				Toast.makeText(this, "只能选择一种职业哦～", Toast.LENGTH_LONG).show();
			}else if (total_of_ischosed == 0) {
				Toast.makeText(this, "您需要选择一种职业哦～", Toast.LENGTH_LONG).show();
			}else{
				Intent it4next = new Intent(this,B1_2_MyLifeState.class);
				startActivity(it4next);
			}
//			Intent it4next = new Intent(this,B1_2_MyWork.class);
			break;

		default:
			break;
		}
	}
}
