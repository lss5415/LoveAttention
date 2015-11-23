package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.CommonAdapter;
import com.zykj.loveattention.adapter.ViewHolder;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.JinRiXinDan;
import com.zykj.loveattention.utils.HttpErrorHandler;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.view.MyListView;
import com.zykj.loveattention.view.XListView;
import com.zykj.loveattention.view.XListView.IXListViewListener;
/**
 * @author LSS 2015年11月13日 下午3:01:12  今日新单
 *
 */

public class B1_5_JinRiXinDanActivity extends BaseActivity implements IXListViewListener, OnItemClickListener{
	private ImageView im_b15_back_btn;//返回
	public XListView list_jinri;//展示列表
//	private B1_5_JinRiXinDanAdapter xindanadapter;
	private CommonAdapter<JinRiXinDan> adapter;
	private List<JinRiXinDan> jrxd = new ArrayList<JinRiXinDan>();
	private int nowpage = 1;
	private Handler mHandler = new Handler();//异步加载或刷新
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_5_jinrixindan);
		adapter = new CommonAdapter<JinRiXinDan>(B1_5_JinRiXinDanActivity.this, R.layout.b1_5_jrxd, jrxd) {
			@Override
			public void convert(ViewHolder holder, JinRiXinDan jrxd) {
				holder.setText(R.id.d2_tv_title, jrxd.getName())
				.setText(R.id.tv_jiage, jrxd.getPrice())
				.setText(R.id.d2_tv_neirong, jrxd.getRemark());
				RatingBar comment_rating_bar = holder.getView(R.id.comment_rating_bar);
				comment_rating_bar.setRating(Float.parseFloat(jrxd.getStars()));
				ImageView d2_img_layout = holder.getView(R.id.d2_img_layout);
				ImageLoader.getInstance().displayImage(jrxd.getImgpath(), d2_img_layout);
			}
		};
		initView();
		requestData();
	}

	private void requestData() {
		/** 今日新单 */
		HttpUtils.url_todaymenu(res_getJinriXinDan);
	}
	
	/**
	 * 今日新单
	 */
	private AsyncHttpResponseHandler res_getJinriXinDan = new HttpErrorHandler() {
		@Override
		public void onRecevieSuccess(JSONObject json) {
			JSONArray jsonarry = json.getJSONArray("data");
//			String strArray = jsonarry.getString("list");
			List<JinRiXinDan> list = JSONArray.parseArray(jsonarry.toString(), JinRiXinDan.class);
			if(nowpage == 1){jrxd.clear();}
			jrxd.addAll(list);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable throwable) {
			// TODO Auto-generated method stub
			super.onFailure(statusCode, headers, responseBody, throwable);
		}
		
	};

	public void initView() {
		im_b15_back_btn = (ImageView) findViewById(R.id.im_b15_back_btn);
		list_jinri = (XListView) findViewById(R.id.list_jinri);
		list_jinri.setDividerHeight(0);
		list_jinri.setPullLoadEnable(true);
		list_jinri.setXListViewListener(this);
		list_jinri.setOnItemClickListener(this);
		list_jinri.setAdapter(adapter);
		
		setListener(im_b15_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b15_back_btn:
			this.finish();
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


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}


	@Override
	public void onRefresh() {
		/**下拉刷新 重建*/
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				nowpage = 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}


	@Override
	public void onLoadMore() {
		/**上拉加载分页*/
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				nowpage += 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}

	private void onLoad() {
		list_jinri.stopRefresh();
		list_jinri.stopLoadMore();
		list_jinri.setRefreshTime("刚刚");
	}

}