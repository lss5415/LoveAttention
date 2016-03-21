package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B5_6_ChangJianWenTiAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.B5_6_ChangJianWenTi;
import com.zykj.loveattention.utils.DateUtil;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.MyListView;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView.IXListViewListener;

public class B5_6_ChangJianWenTiActivity extends BaseActivity implements IXListViewListener{
	
	private ImageView img_back;
	private MyListView listview;
	private B5_6_ChangJianWenTiAdapter adapter;
	private ArrayList<B5_6_ChangJianWenTi> list;
	
	private Handler mHandler = new Handler();//异步加载或刷新
	
	private int curpage;
	
	private String json = null;
	private RequestQueue mRequestQueue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b5_6__chang_jian_wen_ti);
		mRequestQueue = Volley.newRequestQueue(this);		
		img_back = (ImageView) findViewById(R.id.im_b131_back_btn);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B5_6_ChangJianWenTiActivity.this.finish();
			}
		});
		
		listview = (MyListView) findViewById(R.id.list_quetion);
		
		list = new ArrayList<B5_6_ChangJianWenTi>();
		adapter = new B5_6_ChangJianWenTiAdapter(B5_6_ChangJianWenTiActivity.this, list);
		listview.setAdapter(adapter);
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
//		list.setXListViewListener(this, 0);
		listview.setRefreshTime();
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		json = JsonUtils.toJson(map);
		HuoDong();
	}
	
	public void HuoDong(){
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_question(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								list.clear();
								JSONArray array = response.getJSONArray("data");
								ArrayList<B5_6_ChangJianWenTi> list1 = new ArrayList<B5_6_ChangJianWenTi>();
								list1 = new Gson().fromJson(array.toString(), new TypeToken<ArrayList<B5_6_ChangJianWenTi>>(){}.getType());
								
								list.addAll(list1);
								
								adapter.notifyDataSetChanged();
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B5_6_ChangJianWenTiActivity.this,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(B5_6_ChangJianWenTiActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

	
	@Override
	public void onRefresh() {
		/**下拉刷新 重建*/
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				curpage = 1;	
				Map<String, String> map = new HashMap<String, String>();
				map.put("pagenumber", "1");
				map.put("pagesize", "10");
				json = JsonUtils.toJson(map);
				// 发现中活动
				HuoDong();
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
				curpage += 1;
				Map<String, String> map = new HashMap<String, String>();
				map.put("pagenumber", String.valueOf(curpage));
				map.put("pagesize", "10");
				json = JsonUtils.toJson(map);
				// 发现中活动
				HuoDong();
				onLoad();
			}
		}, 1000);		
	}

	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		listview.setRefreshTime();
	}

}
