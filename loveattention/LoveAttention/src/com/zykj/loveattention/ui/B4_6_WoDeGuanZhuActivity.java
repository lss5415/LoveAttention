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
import android.widget.ListView;
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
import com.zykj.loveattention.utils.ListViewSwipeGesture;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;


/**
 * @author lss 2015年8月13日	我的关注
 *
 */
public class B4_6_WoDeGuanZhuActivity extends BaseActivity {
	private ImageView im_b46_back_btn;//返回
	private ListView lv_guanzhu;
	private B4_6_GuanZhuAdapter gzadapter;
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
	private RequestQueue mRequestQueue;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_6_wodeguanzhu);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		GuanZhu();
		GuanZhuList();
	}


	public void initView() {
		im_b46_back_btn = (ImageView) findViewById(R.id.im_b46_back_btn);
		lv_guanzhu = (ListView) findViewById(R.id.lv_guanzhu);
		
		setListener(im_b46_back_btn);
	}
	
	public void GuanZhuList(){
		gzadapter = new B4_6_GuanZhuAdapter(B4_6_WoDeGuanZhuActivity.this, listdata);
		lv_guanzhu.setAdapter(gzadapter);
		 final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
				 lv_guanzhu, swipeListener, B4_6_WoDeGuanZhuActivity.this);
	        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
	        lv_guanzhu.setOnTouchListener(touchListener);
	}

	 ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

	        @Override
	        public void FullSwipeListView(int position) {
	            // TODO Auto-generated method stub
	            Toast.makeText(B4_6_WoDeGuanZhuActivity.this, "Action_2", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void HalfSwipeListView(int position) {
	            // TODO Auto-generated method stub
//	            System.out.println("<<<<<<<" + position);
	            listdata.remove(position);
	            gzadapter.notifyDataSetChanged();
	            Toast.makeText(B4_6_WoDeGuanZhuActivity.this,"删除", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void LoadDataForScroll(int count) {
	            // TODO Auto-generated method stub

	        }

	        @Override
	        public void onDismiss(ListView listView, int[] reverseSortedPositions) {
	            // TODO Auto-generated method stub
//	            Toast.makeText(activity,"Delete", Toast.LENGTH_SHORT).show();
//	            for(int i:reverseSortedPositions){
//	                data.remove(i);
//	                new MyAdapter().notifyDataSetChanged();
//	            }
	        }

			@Override
			public void OnClickListView(int position) {
				// TODO Auto-generated method stub
				
			}

	      /*  @Override
	        public void OnClickListView(int position) {
	        	Intent tuangou = new Intent(B4_6_WoDeGuanZhuActivity.this,
						B1_7_ShangJiaXiangQingActivity.class);
				tuangou.putExtra("merchantid",listdata.get(position).get("id"));
				startActivity(tuangou);
	        }*/

	    };
	
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
		map.put("latitude", getSharedPreferenceValue("lat"));
		map.put("longitude", getSharedPreferenceValue("lng"));
		map.put("pagenumber", "1");
		map.put("pagesize", "10");
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_attention(json),null,new Response.Listener<JSONObject>() {  
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
								map.put("visitnum",jsonObject.getString("visitnum"));
								map.put("mdesc",jsonObject.getString("mdesc"));
								map.put("stars",jsonObject.getString("stars"));
								map.put("distance",jsonObject.getString("distance"));
								listdata.add(map);
							}
//							String invitationList = data.getString("invitationList");
//							yaoqingrenlist = JSONArray.parseArray(invitationList,YaoQingRen.class);

							gzadapter.notifyDataSetChanged();
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
