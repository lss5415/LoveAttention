package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B172InfoAdapter.RefreshExpandableList;
import com.zykj.loveattention.adapter.B1_7_2_FanCaiDanAdapter;
import com.zykj.loveattention.adapter.FanCaiDanXuanZeAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.FanCaiDan;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author LSS 2015年8月29日 上午11:03:21	翻菜单
 *
 */
public class B1_7_2_FanCaiDanActivity extends BaseActivity implements RefreshExpandableList{
	private ImageView b172_back,im_xuanhaole;// 返回,选好了状态
	private ListView lv_fancaidan;//翻菜单
	private B1_7_2_FanCaiDanAdapter fancandanadapter;
	private FanCaiDanXuanZeAdapter fancandanxuanzeadapter;
	private String merchantid,fcdnum="";//XX，翻菜单内数量
	private RequestQueue mRequestQueue; 
	private TextView tv_price,tv_quzhifu,tv_goodsnum;//价格,去支付,商品数量
	private AutoListView al_gouwulist;//购物车内的列表
	private int statefcd=0;//0:显示，1：不显示
	private LinearLayout ll_gwc;//
	private Map<String,FanCaiDan> fcdmap = new HashMap<String,FanCaiDan>();//翻菜单内列表的数据
	private String a="";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_7_2_fancaidan);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this);  
		FanCaiDan();
	}

	public void initView() {
		b172_back = (ImageView) findViewById(R.id.b172_back);
		lv_fancaidan = (ListView) findViewById(R.id.lv_fancaidan);
		merchantid = getIntent().getStringExtra("merchantid");
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_quzhifu = (TextView) findViewById(R.id.tv_quzhifu);
		im_xuanhaole = (ImageView) findViewById(R.id.im_xuanhaole);
		al_gouwulist = (AutoListView) findViewById(R.id.al_gouwulist);
		ll_gwc = (LinearLayout) findViewById(R.id.ll_gwc);
		tv_goodsnum = (TextView) findViewById(R.id.tv_goodsnum);

		setListener(b172_back,tv_quzhifu,im_xuanhaole);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.b172_back:
			this.finish();
			break;
		case R.id.tv_quzhifu:
			if (fcdnum.equals("1")) {
//				Toast.makeText(B1_7_2_FanCaiDanActivity.this, "去支付", Toast.LENGTH_LONG).show();
				Intent itdd = new Intent();
				itdd.setClass(B1_7_2_FanCaiDanActivity.this, QueRenDingDanActivity.class);
				startActivity(itdd);
			}else {
				Toast.makeText(B1_7_2_FanCaiDanActivity.this, "还没有添加商品，请添加", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.im_xuanhaole:
			if (statefcd<1) {
				statefcd=1;
				ll_gwc.setVisibility(View.VISIBLE);
				fancandanxuanzeadapter = new FanCaiDanXuanZeAdapter(B1_7_2_FanCaiDanActivity.this,fcdmap,a);
				al_gouwulist.setAdapter(fancandanxuanzeadapter);
			}else {
				statefcd=0;
				ll_gwc.setVisibility(View.GONE);
			}
			break;
		default:
			break;

		}
	}
	
	public void FanCaiDan(){
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchantid", merchantid);
		String jsonmerchantid = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_goodsMenu(jsonmerchantid),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
//						JSONObject jobsj = response.getJSONObject("data");
//						String dpgoodsinfo = jobsj.getString("goodsinfoList");
						org.json.JSONArray fcdlist = response.getJSONArray("data");
						fancandanadapter = new B1_7_2_FanCaiDanAdapter(B1_7_2_FanCaiDanActivity.this,fcdlist,B1_7_2_FanCaiDanActivity.this,fcdmap);
						lv_fancaidan.setAdapter(fancandanadapter);
						
					} catch (org.json.JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}

	@Override
	public void refreshState(String fcdnum,Map<String,FanCaiDan> fcdmap,String a) {
		this.fcdnum = fcdnum;
		this.fcdmap = fcdmap;
		this.a=a;
		if (fcdnum.equals("1")) {
			im_xuanhaole.setImageResource(R.drawable.xuanhaolehuang);
//			tv_price.setText(price+"元");
		}else {
			im_xuanhaole.setImageResource(R.drawable.xuanhaolehui);
			tv_price.setText("购物车还是空的");
		}
		try {
			String arr[] = a.split(",");
			int ax = arr.length;
			String ln = ax+"";
			tv_goodsnum.setText("共"+ln+"件商品");
		} catch (Exception e) {
			
		}
	}  

}
