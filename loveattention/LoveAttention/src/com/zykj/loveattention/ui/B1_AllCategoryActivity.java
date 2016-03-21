package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.utils.Log;
import com.zykj.loveattention.R;
import com.zykj.loveattention.R.id;
import com.zykj.loveattention.R.layout;
import com.zykj.loveattention.adapter.B1_AllCategoryAdapter;
import com.zykj.loveattention.data.AllCategory;
import com.zykj.loveattention.data.Category;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * 
 * @author lc
 * @date 创建时间：2016-1-11 下午5:03:14
 * @version 1.0 
 * @Description 全部分类
 */
public class B1_AllCategoryActivity extends Activity {
	
	private Context mContext = B1_AllCategoryActivity.this;
	
	private ArrayList<Category> categorylist;
	private Map<String,ArrayList<Category>> map;
	private RequestQueue mRequestQueue;
	
	private ListView listview;
	private B1_AllCategoryAdapter adapter;
	
	private ImageView imageview;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b1_activity_all_category);
        mRequestQueue = Volley.newRequestQueue(mContext);
        categorylist = new ArrayList<Category>();
        map = new HashMap<String, ArrayList<Category>>();
        
        init();
        
        initData();
    }
	
	public void init(){
		imageview = (ImageView) findViewById(R.id.iv_b1_back);
		imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B1_AllCategoryActivity.this.finish();
			}
		});
		
		listview = (ListView) findViewById(R.id.allcategory_listview);
		adapter = new B1_AllCategoryAdapter(mContext, categorylist,map);
		listview.setAdapter(adapter);
	}
	
	public void initData(){
		RequestDailog.showDialog(mContext, "数据加载中，请稍后...");
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_allcategory(), null,
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
								AllCategory list1 = new AllCategory();
								
								org.json.JSONObject array1 = response.getJSONObject("data");
								TypeToken<AllCategory> type = new TypeToken<AllCategory>(){};
								list1 = new Gson().fromJson(array1.toString(), type.getType());
								
								categorylist.addAll(list1.getCategory());
								map.put("category"+list1.getCategory().get(0).getCategoryid(), list1.getCategory1());
								map.put("category"+list1.getCategory().get(1).getCategoryid(), list1.getCategory2());
								map.put("category"+list1.getCategory().get(2).getCategoryid(), list1.getCategory3());
								map.put("category"+list1.getCategory().get(3).getCategoryid(), list1.getCategory4());
								map.put("category"+list1.getCategory().get(4).getCategoryid(), list1.getCategory5());
								map.put("category"+list1.getCategory().get(5).getCategoryid(), list1.getCategory6());
								map.put("category"+list1.getCategory().get(6).getCategoryid(), list1.getCategory7());
								map.put("category"+list1.getCategory().get(7).getCategoryid(), list1.getCategory8());
								map.put("category"+list1.getCategory().get(8).getCategoryid(), list1.getCategory9());
								map.put("category"+list1.getCategory().get(9).getCategoryid(), list1.getCategory172());
								map.put("category"+list1.getCategory().get(10).getCategoryid(), list1.getCategory201());
								
								adapter.notifyDataSetChanged();
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(mContext,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(mContext, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

}
