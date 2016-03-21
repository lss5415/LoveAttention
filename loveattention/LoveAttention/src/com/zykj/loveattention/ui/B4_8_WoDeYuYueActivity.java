package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_8_YuYueAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView;

/**
 * @author lss 2015年8月13日	我的预约
 *
 */
public class B4_8_WoDeYuYueActivity extends BaseActivity {
	private ImageView im_b48_back_btn;// 返回
	private XListView lv_yuyue;
	private B4_8_YuYueAdapter gzadapter;
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
	private RequestQueue mRequestQueue;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_8_wodeyuyue);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		YuYue();
	}

	public void initView() {
		im_b48_back_btn = (ImageView) findViewById(R.id.im_b48_back_btn);
		lv_yuyue = (XListView) findViewById(R.id.lv_yuyue);

		setListener(im_b48_back_btn);
	}

	public void setAdapter() {
		/*for (int i = 0; i < 20; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("shijian", "3.15.10:20");
			itemData.put("bianliang", "100");
			itemData.put("miaoshu", "广告赚取");
			itemData.put("yue", "1000");
			data.add(itemData);
		}*/
		gzadapter = new B4_8_YuYueAdapter(this, listdata);
		lv_yuyue.setAdapter(gzadapter);
		lv_yuyue.setPullLoadEnable(true);
		lv_yuyue.setPullRefreshEnable(true);
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		lv_yuyue.setRefreshTime(str + "刷新");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b48_back_btn:
			this.finish();
			break;
		// case R.id.im_denglu:
		// Intent itdenglu = new Intent();
		// itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
		// startActivity(itdenglu);
		// break;
		default:
			break;

		}
	}
	
	public void YuYue(){
		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_reseration(json),null,new Response.Listener<JSONObject>() {  
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
								map.put("imgpath",jsonObject.getString("imgpath"));
								map.put("name",jsonObject.getString("name"));
								map.put("rstate",jsonObject.getString("rstate"));
								map.put("remark",jsonObject.getString("remark"));
								map.put("visitnum",jsonObject.getString("visitnum"));
								map.put("stars",jsonObject.getString("stars"));
								map.put("price",jsonObject.getString("price"));
								listdata.add(map);
							}
							setAdapter();
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
