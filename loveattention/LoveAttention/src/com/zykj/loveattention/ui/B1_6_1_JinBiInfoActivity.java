package com.zykj.loveattention.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.CommonAdapter;
import com.zykj.loveattention.adapter.ViewHolder;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.Coupon;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ImageOptions;
import com.zykj.loveattention.utils.StringUtil;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView;
import com.zykj.loveattention.view.XListView.IXListViewListener;

/**
 * @author LSS 2015年8月26日 上午9:35:19	金币商城列表
 *
 */
public class B1_6_1_JinBiInfoActivity extends BaseActivity implements OnItemClickListener,IXListViewListener{
	private ImageView im_b162_back_btn;//返回
	public TextView tv_xiaoliang,tv_jiage,tv_zuixin;//商户类型，全城，智能排序，筛选
	public ImageView b2_xiaoliang,b2_jiage,b2_zuixin;//底部黄条
	public ImageView rowdown1,rowdown2,rowdown3;//向下的指示尖头
	public XListView list_jinbi;//展示列表
	
	private int pagesize = 10;//每页条数
	private int pagenumber = 1;//当前页
	private int orderType = 1;//1-销量 2-价格 3-最新
	
	private RequestQueue mRequestQueue;
	private CommonAdapter<Coupon> jinbiAdapter;//适配器
	private ArrayList<Coupon> coupons = new ArrayList<Coupon>();
	private Handler mHandler;//异步加载或刷新
	private ImageLoader imageLoader;
	private String cateId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_1_jinbiinfo);
		cateId = getIntent().getStringExtra("cateId");
		mRequestQueue = Volley.newRequestQueue(this);
		imageLoader = ImageLoader.getInstance();
		mHandler = new Handler();
		
		initView();
		requestData();
	}


	private void requestData() {
		JsonObject requsetParams = new JsonObject();
		requsetParams.addProperty("childid", cateId);
		requsetParams.addProperty("orderType", orderType+"");
		requsetParams.addProperty("pagenumber", pagenumber+"");
		requsetParams.addProperty("pagesize", pagesize+"");
		mRequestQueue.add(new StringRequest(Request.Method.GET, HttpUtils.url_mallGoods(requsetParams.toString()), 
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
					JsonObject status = jsonObject.get("status").getAsJsonObject();
					Gson gson = new Gson();
					if(status.get("succeed").getAsInt() == 1){
						String data = jsonObject.get("data").getAsJsonArray().toString();
						ArrayList<Coupon> childs = gson.fromJson(data, new TypeToken<ArrayList<Coupon>>(){}.getType());
						if(pagenumber == 1){coupons.clear();}
						coupons.addAll(childs);
						jinbiAdapter.notifyDataSetChanged();
					}
				}
			}, new Response.ErrorListener(){
				@Override
				public void onErrorResponse(VolleyError error) {
					RequestDailog.closeDialog();
					Tools.Log("B1_6_JinBiShangCheng.ErrorResponse=" + error.getMessage());
					Toast.makeText(B1_6_1_JinBiInfoActivity.this, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
				}
			})
		);
	}


	public void initView() {
		im_b162_back_btn = (ImageView) findViewById(R.id.im_b162_back_btn);
		rowdown1  = (ImageView) findViewById(R.id.rowdown1);
		rowdown2  = (ImageView) findViewById(R.id.rowdown2);
		rowdown3  = (ImageView) findViewById(R.id.rowdown3);
		b2_xiaoliang  = (ImageView) findViewById(R.id.b2_xiaoliang);
		b2_jiage  = (ImageView) findViewById(R.id.b2_jiage);
		b2_zuixin  = (ImageView) findViewById(R.id.b2_zuixin);
		tv_xiaoliang = (TextView) findViewById(R.id.tv_xiaoliang);
		tv_jiage = (TextView) findViewById(R.id.tv_jiage);
		tv_zuixin = (TextView) findViewById(R.id.tv_zuixin);
		list_jinbi = (XListView) findViewById(R.id.list_jinbi);
		list_jinbi.setXListViewListener(this);
		jinbiAdapter = new CommonAdapter<Coupon>(this,R.layout.b3_b161_jinbi_item,coupons) {
			@Override
			public void convert(ViewHolder holder, final Coupon coupon) {
				if(!StringUtil.isEmpty(coupon.getCouponimage())){
					ImageView imageView = holder.getView(R.id.d2_img_layout);
					imageLoader.displayImage(coupon.getCouponimage(), imageView, ImageOptions.getOpstion(), new AnimateFirstDisplayListener());
				}
				holder.setText(R.id.aci_name, coupon.getCouponname())
						.setText(R.id.d2_tv_neirong, coupon.getCoupondetail())
						.setText(R.id.aci_price, coupon.getCouponsellprice());
				holder.getView(R.id.aci_button).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Toast.makeText(B1_6_1_JinBiInfoActivity.this, coupon.getCouponid(), Toast.LENGTH_LONG).show();
					}
				});
			}
		};
		list_jinbi.setAdapter(jinbiAdapter);
		list_jinbi.setOnItemClickListener(this);
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
			orderType = 1;pagenumber=1;
			requestData();
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
			orderType = 2;pagenumber=1;
			requestData();
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
			orderType = 3;pagenumber=1;
			requestData();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Coupon coupon = coupons.get(position-1);
		Toast.makeText(this, coupon.getCouponid(), Toast.LENGTH_LONG).show();
		startActivity(new Intent(this, B1_6_2_YouHuiHuoDongActivity.class));
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pagenumber = 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				pagenumber += 1;
				requestData();
				onLoad();
			}
		}, 1000);
	}

	private void onLoad() {
		list_jinbi.stopRefresh();
		list_jinbi.stopLoadMore();
		list_jinbi.setRefreshTime();
	}
}