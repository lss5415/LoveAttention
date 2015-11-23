package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_7_OrderStatusAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.view.MyListView;
import com.zykj.loveattention.view.XListView.IXListViewListener;

public class B4_7_WoDeDingdan extends BaseActivity implements IXListViewListener {

	private  ImageView im_b47_back;//返回
	private  TextView tv_daodian;//到店消费
	private  TextView tv_songhuo;//送货上门
	
	private LinearLayout ll_quanbudingdan;//全部订单
	private LinearLayout ll_weifukuan;//未付款
	private LinearLayout ll_weixiaofei;//未消费
	private LinearLayout ll_daipingjia;//待评价
	
	private View v101;
	private View v102;
	private View v103;
	private View v104;
	
	private TextView tv_fukuan;//未付款+已付款
	private TextView tv_xiaofei;//未消费＋未付款
	private TextView tv_comment;//待评价＋已收货
	private int TAG_H0 = 101;
	private int status = 10;
//  order_state 订单状态（全部订单:10,未付款:20,未消费:30,待评价:40）
    private static final int ALLORDER    = 10;
    private static final int WEIFUKUAN   = 20;
    private static final int WEIXIAOFEI  = 30;
    private static final int DAIPINGJIA  = 40;
	
	private MyListView listview;
    B4_7_OrderStatusAdapter adapter;
    List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_7_wodedingdan);
		initView();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b47_back:
			this.finish();
			break;
		case R.id.tv_daodian://到店
			TAG_H0 = 101;
			setHorizontalLine(tv_daodian,tv_songhuo);
			setTextA();//设置不同模式下导航栏的显示
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.tv_songhuo://送货上门
			TAG_H0 = 102;
			setHorizontalLine(tv_songhuo,tv_daodian);
			setTextB();
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_quanbudingdan://全部订单
			setLine(v101,v102,v103,v104);
			status = ALLORDER;
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_weifukuan://未付款
			setLine(v102,v101,v103,v104);
			status = WEIFUKUAN;
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_weixiaofei://未消费
			setLine(v103,v102,v101,v104);
			status = WEIXIAOFEI;
			adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		case R.id.ll_daipingjia://待评价
			setLine(v104,v102,v103,v101);
			status = DAIPINGJIA;
			adapter = new B4_7_OrderStatusAdapter(this, dataList, 40, null,TAG_H0);
			listview.setAdapter(adapter);
			break;
		default:
			break;
		}
	}
	private void setTextB() {
		tv_fukuan.setText("已付款");
		tv_xiaofei.setText("未付款");
		tv_comment.setText("已收货");
	}
	private void setTextA() {
		tv_fukuan.setText("未付款");
		tv_xiaofei.setText("未消费");
		tv_comment.setText("待评价");
	}
	private void setHorizontalLine(TextView tv1,TextView tv2) {
		// TODO Auto-generated method stub
		tv1.setBackgroundResource(R.drawable.btn_b47);
		tv2.setBackgroundResource(R.drawable.btn_b47_white);
		
		tv1.setTextColor(this.getResources().getColor(R.color.white));
		tv2.setTextColor(this.getResources().getColor(R.color.yellow));
	}
	private void setLine(View v1,View v2,View v3,View v4) {
		// TODO Auto-generated method stub
		v1.setVisibility(View.VISIBLE);
		v2.setVisibility(View.INVISIBLE);
		v3.setVisibility(View.INVISIBLE);
		v4.setVisibility(View.INVISIBLE);
	}
	private void initView() {
		// TODO Auto-generated method stub
		im_b47_back = (ImageView) findViewById(R.id.im_b47_back);
		tv_daodian = (TextView) findViewById(R.id.tv_daodian);
		tv_songhuo = (TextView) findViewById(R.id.tv_songhuo);
		
		tv_fukuan = (TextView) findViewById(R.id.tv_fukuan);
		tv_xiaofei = (TextView) findViewById(R.id.tv_xiaofei);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		
		ll_quanbudingdan = (LinearLayout) findViewById(R.id.ll_quanbudingdan);
		ll_weifukuan = (LinearLayout) findViewById(R.id.ll_weifukuan);
		ll_weixiaofei = (LinearLayout) findViewById(R.id.ll_weixiaofei);
		ll_daipingjia = (LinearLayout) findViewById(R.id.ll_daipingjia);
		
		v101 = findViewById(R.id.v101);
		v102 = findViewById(R.id.v102);
		v103 = findViewById(R.id.v103);
		v104 = findViewById(R.id.v104);
		
		listview = (MyListView) findViewById(R.id.listview_orderlist);
		adapter = new B4_7_OrderStatusAdapter(this, dataList, status, null,TAG_H0);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setXListViewListener(this);
		
		setListener(im_b47_back,tv_daodian,tv_songhuo,ll_quanbudingdan,ll_weifukuan,ll_weixiaofei,ll_daipingjia);
		
		setLine(v101,v102,v103,v104);
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
