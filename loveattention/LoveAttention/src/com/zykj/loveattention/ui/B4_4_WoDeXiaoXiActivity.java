package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.zykj.loveattention.adapter.XiaoXiAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.ListViewSwipeGesture;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月12日	我的消息
 *lv_gonggao
 */
public class B4_4_WoDeXiaoXiActivity extends BaseActivity {
	private ImageView im_b44_back_btn;//返回
	private TextView tv_wodexiaoxi,tv_tuisongxiaoxi,tv_xitongxiaoxi;//我的，推送，系统
	private ImageView b3_hongdi_wode,b3_hongdi_tuisong,b3_hongdi_xitong;//红底我的，推送，系统
//    private ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> listdata1 = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> listdata2 = new ArrayList<Map<String, String>>();
    private ListView lv_gonggao;
//    private BaseAdapter baseAdapter;
	private RequestQueue mRequestQueue;
	private int pagenumber = 1;
	private XiaoXiAdapter xxadapter;
	private int xxstate=0;//0,1,2我的，推送，系统
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_4_wodexiaoxi);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
		XiaoXi();
		XiaoXiList();
        
	}
	public void XiaoXiList(){
		xxadapter = new XiaoXiAdapter(B4_4_WoDeXiaoXiActivity.this, listdata);
		lv_gonggao.setAdapter(xxadapter);
        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
                lv_gonggao, swipeListener, this);
        touchListener.SwipeType	=	ListViewSwipeGesture.Double;    //设置两个选项列表项的背景
        lv_gonggao.setOnTouchListener(touchListener);
	}

	public void initView() {
		im_b44_back_btn = (ImageView) findViewById(R.id.im_b44_back_btn);
		tv_wodexiaoxi = (TextView)findViewById(R.id.tv_wodexiaoxi);
		tv_tuisongxiaoxi = (TextView)findViewById(R.id.tv_tuisongxiaoxi);
		tv_xitongxiaoxi = (TextView)findViewById(R.id.tv_xitongxiaoxi);
		b3_hongdi_wode = (ImageView) findViewById(R.id.b3_hongdi_wode);//我的消息--下方黄条
		b3_hongdi_tuisong = (ImageView) findViewById(R.id.b3_hongdi_tuisong);//推送消息 --下方黄条
		b3_hongdi_xitong = (ImageView) findViewById(R.id.b3_hongdi_xitong);//系统推送--下方黄条
        lv_gonggao=(ListView)findViewById(R.id.lv_gonggao);
        
		setListener(im_b44_back_btn,tv_wodexiaoxi,tv_tuisongxiaoxi,tv_xitongxiaoxi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b44_back_btn:
			this.finish();
			break;
        case R.id.tv_wodexiaoxi:
        	setVisible();
        	b3_hongdi_wode.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_wodexiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	xxstate = 0;
        	XiaoXi();
        	break;
        case R.id.tv_tuisongxiaoxi:
        	setVisible();
        	b3_hongdi_tuisong.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_tuisongxiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	xxstate = 1;
        	XiaoXi();
        	break;
        case R.id.tv_xitongxiaoxi:
        	setVisible();
        	b3_hongdi_xitong.setVisibility(View.VISIBLE);
        	setTextColor();
        	tv_xitongxiaoxi.setTextColor(getResources().getColor(R.color.all_huang_color));
        	xxstate = 2;
        	XiaoXi();
        	break;
        default:
        	break;
        }
        }
	
	//设置所有黄条默认屏蔽
	public void setVisible(){
    	b3_hongdi_wode.setVisibility(View.INVISIBLE);
    	b3_hongdi_tuisong.setVisibility(View.INVISIBLE);
    	b3_hongdi_xitong.setVisibility(View.INVISIBLE);
	}
	
	//设置活动字体颜色为黑色
	public void setTextColor(){
		tv_wodexiaoxi.setTextColor(Color.BLACK);
		tv_tuisongxiaoxi.setTextColor(Color.BLACK);
		tv_xitongxiaoxi.setTextColor(Color.BLACK);
	}

	 ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

	        @Override
	        public void FullSwipeListView(int position) {
	            // TODO Auto-generated method stub
	            Toast.makeText(B4_4_WoDeXiaoXiActivity.this, "Action_2", Toast.LENGTH_SHORT).show();
	        }

	        @Override
	        public void HalfSwipeListView(int position) {
	            // TODO Auto-generated method stub
//	            System.out.println("<<<<<<<" + position);
	            listdata.remove(position);
	            xxadapter.notifyDataSetChanged();
	            Toast.makeText(B4_4_WoDeXiaoXiActivity.this,"删除", Toast.LENGTH_SHORT).show();
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
	        	Intent intent = new Intent();
	        	intent.setClass(B4_4_WoDeXiaoXiActivity.this,B5_2_SheQuGongGaoDetailActivity.class);
	        	startActivity(intent);
	        }

	    };
	    
	    public void XiaoXi(){
	    		RequestDailog.showDialog(this, "数据加载中，请稍后");
	    		Map<String, String> map = new HashMap<String, String>();
				map.put("memberid", getSharedPreferenceValue("id"));
				map.put("pagenumber", pagenumber+"");
				map.put("pagesize", "5");
	    		String json = JsonUtils.toJson(map);
	    		if (xxstate==0) {
	    			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
		    				HttpUtils.url_mymessage(json), null,
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
		    								listdata.clear();
		    								JSONObject joba = response.getJSONObject("data");
											JSONArray data = joba.getJSONArray("resultlist");
//											Log.e("data", data+"");
											for (int i = 0; i < data.length(); i++) {
												Map<String, String> map = new HashMap<String, String>();
												JSONObject jsonObject = (JSONObject) data.get(i);
												map.put("createtime",jsonObject.getString("createtime"));
												map.put("messageimg",jsonObject.getString("messageimg"));
												map.put("messagecontent",jsonObject.getString("messagecontent"));
												map.put("messageid",jsonObject.getString("messageid"));
												map.put("messagetitle",jsonObject.getString("messagetitle"));
												map.put("messagecate",jsonObject.getString("messagecate"));
												map.put("state",jsonObject.getString("state"));
												map.put("createuser",jsonObject.getString("createuser"));
												listdata.add(map);
											}
											Log.e("list", listdata.toString());
											xxadapter.notifyDataSetChanged();
		    							} else {// 失败,提示失败信息
		    								String errdesc = status.getString("errdesc");
		    								Toast.makeText(B4_4_WoDeXiaoXiActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
		    						Toast.makeText(B4_4_WoDeXiaoXiActivity.this, "网络连接失败，请重试",
		    								Toast.LENGTH_LONG).show();
		    					}
		    				});
		    		mRequestQueue.add(jr);
				}else if(xxstate==1){
	    			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
		    				HttpUtils.url_pushmessage(json), null,
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
		    								listdata.clear();
		    								JSONObject joba = response.getJSONObject("data");
											JSONArray data = joba.getJSONArray("resultlist");
//											Log.e("data", data+"");
											for (int i = 0; i < data.length(); i++) {
												Map<String, String> map = new HashMap<String, String>();
												JSONObject jsonObject = (JSONObject) data.get(i);
												map.put("createtime",jsonObject.getString("createtime"));
												map.put("messageimg",jsonObject.getString("messageimg"));
												map.put("messagecontent",jsonObject.getString("messagecontent"));
												map.put("messageid",jsonObject.getString("messageid"));
												map.put("messagetitle",jsonObject.getString("messagetitle"));
												map.put("messagecate",jsonObject.getString("messagecate"));
												map.put("state",jsonObject.getString("state"));
												map.put("createuser",jsonObject.getString("createuser"));
												listdata.add(map);
											}
											Log.e("list", listdata.toString());
											xxadapter.notifyDataSetChanged();
		    							} else {// 失败,提示失败信息
		    								String errdesc = status.getString("errdesc");
		    								Toast.makeText(B4_4_WoDeXiaoXiActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
		    						Toast.makeText(B4_4_WoDeXiaoXiActivity.this, "网络连接失败，请重试",
		    								Toast.LENGTH_LONG).show();
		    					}
		    				});
		    		mRequestQueue.add(jr);
				}else if(xxstate==2){
	    			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
		    				HttpUtils.url_systemessage(json), null,
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
		    								listdata.clear();
		    								JSONObject joba = response.getJSONObject("data");
											JSONArray data = joba.getJSONArray("resultlist");
//											Log.e("data", data+"");
											for (int i = 0; i < data.length(); i++) {
												Map<String, String> map = new HashMap<String, String>();
												JSONObject jsonObject = (JSONObject) data.get(i);
												map.put("createtime",jsonObject.getString("createtime"));
												map.put("messageimg",jsonObject.getString("messageimg"));
												map.put("messagecontent",jsonObject.getString("messagecontent"));
												map.put("messageid",jsonObject.getString("messageid"));
												map.put("messagetitle",jsonObject.getString("messagetitle"));
												map.put("messagecate",jsonObject.getString("messagecate"));
												map.put("state",jsonObject.getString("state"));
												map.put("createuser",jsonObject.getString("createuser"));
												listdata.add(map);
											}
											Log.e("list", listdata.toString());
											xxadapter.notifyDataSetChanged();
		    							} else {// 失败,提示失败信息
		    								String errdesc = status.getString("errdesc");
		    								Toast.makeText(B4_4_WoDeXiaoXiActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
		    						Toast.makeText(B4_4_WoDeXiaoXiActivity.this, "网络连接失败，请重试",
		    								Toast.LENGTH_LONG).show();
		    					}
		    				});
		    		mRequestQueue.add(jr);
				}
	    		
	    }
}