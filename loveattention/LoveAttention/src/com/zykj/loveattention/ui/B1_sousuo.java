package com.zykj.loveattention.ui;


import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.zykj.loveattention.adapter.B1_HotSearchAdapter;
import com.zykj.loveattention.adapter.B1_HistorySearchAdapter1;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.Sousuo;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.GridViewForListView;
import com.zykj.loveattention.view.RequestDailog;
/**
 * 
 * @author lc
 * @date 创建时间：2016-1-9 下午4:34:49
 * @version 1.0 
 * @Description 搜索
 */
public class B1_sousuo extends BaseActivity {
	
	private Context mContext = B1_sousuo.this;
	
	private  GridViewForListView gridView1;//热门搜索
	private  GridViewForListView gridView2;//历史记录
	private ArrayList<Sousuo> list;
	private ArrayList<String> listHistory;
	private B1_HotSearchAdapter gridViewAdapter;
	private B1_HistorySearchAdapter1 gridViewHistoryAdapter;
    private ImageView iv_b1_back;
    private EditText et_sousuo;
    private LinearLayout ll_cleanhistory;//清除历史信息
    private TextView textView2;
    private TextView tv_search;//搜索
    private ArrayAdapter<String> arr_adapter;
    
    private RequestQueue mRequestQueue;
    
    private String old_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_sousuo);
		mRequestQueue = Volley.newRequestQueue(this);
		
		 // 获取搜索记录文件内容
        SharedPreferences sp = getSharedPreferences("search_history", 0);
        String history = sp.getString("history", "暂时没有搜索记录");
     // 用逗号分割内容返回数组
        String[] history_arr = history.split(",");
     // 新建适配器，适配器数据为搜索历史文件内容
        arr_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, history_arr);
        
     // 保留前50条数据
        if (history_arr.length > 50) {
            String[] newArrays = new String[50];
            // 实现数组之间的复制
            System.arraycopy(history_arr, 0, newArrays, 0, 50);
            arr_adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, history_arr);
        }
        
        
		initView();
		setData();
		
		SharedPreferences mysp = getSharedPreferences("search_history", 0);
        old_text = mysp.getString("history", "");
        String[] s = new String[]{};
        s = mysp.getString("history", "").split(",");
        Log.d("----","mysp.getString = "+mysp.getString("history", ""));
        if(!s[0].equals("")){
	        for(int i=0;i<s.length;i++){
	        	listHistory.add(s[i]);
	        }
        }
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_b1_back:
			this.finish();
			break;
		case R.id.ll_cleanhistory://清除历史信息
//			textView2.setVisibility(View.GONE);
//			gridView2.setVisibility(View.GONE);
//			ll_cleanhistory.setVisibility(View.GONE);
			SharedPreferences sp =getSharedPreferences("search_history",0);
	        SharedPreferences.Editor editor=sp.edit();
	        editor.clear();
	        editor.commit();
	        listHistory.clear();
	        gridViewHistoryAdapter.notifyDataSetChanged();
	        Toast.makeText(this, "清除成功", Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_search://搜索
			// 获取搜索框信息
	        String text = et_sousuo.getText().toString();
	        SharedPreferences mysp = getSharedPreferences("search_history", 0);
	        old_text = mysp.getString("history", "");
	        
	        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
	        StringBuilder builder = new StringBuilder(old_text);
	        if(!builder.toString().contains(text+",")){
	        	builder.append(text + ",");
	        }

	        SharedPreferences.Editor myeditor = mysp.edit();
	        
	        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
	        if (!old_text.contains(text + ",")) {
	        	myeditor.putString("history", builder.toString());
	            listHistory.add(text);
	        } 
	        gridViewHistoryAdapter.notifyDataSetChanged();
	        myeditor.commit();
	        
	        Intent intent = new Intent();
	        intent.setClass(mContext, B1_SousuoJieguoActivity.class);
	        intent.putExtra("key",et_sousuo.getText().toString());
	        startActivity(intent);
			
			break;
		default:
			break;
		}
	}
	
	private void setData() {
		RequestDailog.showDialog(mContext, "请稍后...");
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_shouyesousuo(), null,
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
								ArrayList<Sousuo> list1 = new ArrayList<Sousuo>();
								org.json.JSONArray array1 = response.getJSONArray("data");
								TypeToken<ArrayList<Sousuo>> type = new TypeToken<ArrayList<Sousuo>>(){};
								list1 = new Gson().fromJson(array1.toString(), type.getType());
								list.addAll(list1);
								
								gridViewAdapter.notifyDataSetChanged();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B1_sousuo.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B1_sousuo.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
		

	}


	private void initView() {
		// TODO Auto-generated method stub
		gridView1 = (GridViewForListView) findViewById(R.id.gridView1);
		gridView1.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView2 = (GridViewForListView) findViewById(R.id.gridView2);
		gridView2.setSelector(new ColorDrawable(Color.TRANSPARENT));
		iv_b1_back = (ImageView) findViewById(R.id.iv_b1_back);
		et_sousuo = (EditText) findViewById(R.id.et_sousuo);
		et_sousuo.bringToFront();
		et_sousuo.setFocusable(true);   
		et_sousuo.setFocusableInTouchMode(true);   
		et_sousuo.requestFocus();  
		
		list = new ArrayList<Sousuo>();
		gridViewAdapter = new B1_HotSearchAdapter(B1_sousuo.this,list);
		gridView1.setAdapter(gridViewAdapter);
		listHistory = new ArrayList<String>();
		listHistory.clear();
		gridViewHistoryAdapter = new B1_HistorySearchAdapter1(this, listHistory);
		gridView2.setAdapter(gridViewHistoryAdapter);
		
		
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String text = list.get(arg2).getKeywordtitle();
		        SharedPreferences mysp = getSharedPreferences("search_history", 0);
		        old_text = mysp.getString("history", "");
				// 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
		        StringBuilder builder = new StringBuilder(old_text);
		        builder.append(text + ",");

		        SharedPreferences.Editor myeditor = mysp.edit();
		        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
		        if (!old_text.contains(text + ",")) {
		        	myeditor.putString("history", builder.toString());
		            listHistory.add(text);
		        } 
		        gridViewHistoryAdapter.notifyDataSetChanged();
		        myeditor.commit();
		        
		        
				Intent intent = new Intent();
		        intent.setClass(mContext, B1_SousuoJieguoActivity.class);
		        intent.putExtra("key", list.get(arg2).getKeywordtitle());
		        startActivity(intent);
			}
		});
		
		gridView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
		        intent.setClass(mContext, B1_SousuoJieguoActivity.class);
		        intent.putExtra("key", listHistory.get(arg2));
		        startActivity(intent);
			}
		});
		
		ll_cleanhistory = (LinearLayout) findViewById(R.id.ll_cleanhistory);
		textView2 = (TextView) findViewById(R.id.textView2);
		tv_search = (TextView) findViewById(R.id.tv_search);
		setListener(iv_b1_back,ll_cleanhistory,tv_search);
	}
}
