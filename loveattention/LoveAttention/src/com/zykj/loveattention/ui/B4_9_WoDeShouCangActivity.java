package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
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
import com.zykj.loveattention.adapter.B4_6_GuanZhuAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.AutoListView;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月13日 我的收藏
 * 
 */
public class B4_9_WoDeShouCangActivity extends BaseActivity {
	private ImageView im_b46_back_btn;// 返回
	private ListView lv_guanzhu;
	private B4_6_GuanZhuAdapter gzadapter;
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
	private RequestQueue mRequestQueue;
	private TextView tv_ttle;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_6_wodeguanzhu);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		GuanZhu();
	}


	public void initView() {
		im_b46_back_btn = (ImageView) findViewById(R.id.im_b46_back_btn);
		lv_guanzhu = (ListView) findViewById(R.id.lv_guanzhu);
		tv_ttle = (TextView) findViewById(R.id.tv_ttle);
		tv_ttle.setText("我的收藏");

		lv_guanzhu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent tuangou = new Intent(B4_9_WoDeShouCangActivity.this,
//						B1_7_ShangJiaXiangQingActivity.class);
//				tuangou.putExtra("merchantid",listdata.get(2).get("id"));
//				startActivity(tuangou);
				Intent yhq = new Intent(B4_9_WoDeShouCangActivity.this, ShangPinInfoActivity.class);
				yhq.putExtra("goodsid", listdata.get(arg2).get("id"));
				yhq.putExtra("shangjia", listdata.get(arg2).get("storename"));
				startActivity(yhq);
			}
		});
		
		setListener(im_b46_back_btn);
	}
	
	/*public void setAdapter(){
//		for (int i = 0; i < 20; i++) {
//			Map<String, String> itemData = new HashMap<String, String>();
//			itemData.put("shijian", "3.15.10:20");
//			itemData.put("bianliang", "100");
//			itemData.put("miaoshu", "广告赚取");
//			itemData.put("yue", "1000");
//			data.add(itemData);
//		}
		gzadapter = new B4_6_GuanZhuAdapter(this, listdata);
		lv_guanzhu.setAdapter(gzadapter);
		lv_guanzhu.setPullLoadEnable(true);
		lv_guanzhu.setPullRefreshEnable(true);
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		lv_guanzhu.setRefreshTime(str + "刷新");
	}*/

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
	
	public void GuanZhu(){
		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_collect(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							org.json.JSONArray data = response.getJSONArray("data");
							Log.e("data", data+"");
							for (int i = 0; i < data.length(); i++) {
								Map<String, String> map = new HashMap<String, String>();
								JSONObject jsonObject = (JSONObject) data.get(i);
								map.put("collectid",jsonObject.getString("collectid"));
								map.put("remark",jsonObject.getString("remark"));
								map.put("state",jsonObject.getString("state"));
								map.put("id",jsonObject.getString("id"));
								map.put("imgpath",jsonObject.getString("imgpath"));
								map.put("stars",jsonObject.getString("stars"));
								map.put("name",jsonObject.getString("name"));
								map.put("price",jsonObject.getString("price"));
								map.put("oldprice",jsonObject.getString("oldprice"));
								map.put("storename", jsonObject.getString("storename"));
								listdata.add(map);
							}

							gzadapter = new B4_6_GuanZhuAdapter(B4_9_WoDeShouCangActivity.this, listdata);
							lv_guanzhu.setAdapter(gzadapter);
						}else {//失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(getApplicationContext(), errdesc, Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
                Toast.makeText(getApplicationContext(), "网络连接失败，请重试", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
	}

}