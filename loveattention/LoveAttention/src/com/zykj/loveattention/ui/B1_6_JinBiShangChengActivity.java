package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.CommonAdapter;
import com.zykj.loveattention.adapter.ViewHolder;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B1_6_JinBiShangChengActivity extends BaseActivity {
	private ImageView im_b16_back_btn;//返回
	private RadioGroup category_list;//分类左侧
    private RadioGroup.LayoutParams mRadioParams;
	private GridView product_grid;//分类右侧
	private RequestQueue mRequestQueue;
	TextView tv_ceshi;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_jinbishangcheng);
		mRequestQueue = Volley.newRequestQueue(this);
		
		initView();
		requestData();
	}


	public void initView() {
		im_b16_back_btn = (ImageView) findViewById(R.id.im_b16_back_btn);
		category_list = (RadioGroup)findViewById(R.id.category_list);//父分类
		mRadioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		product_grid = (GridView)findViewById(R.id.product_grid);//子分类
		tv_ceshi = (TextView)findViewById(R.id.tv_ceshi);
		category_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
//                oneid = String.valueOf(checkedId);
                getChildCateByPid(String.valueOf(checkedId));
            }
        });
		
		setListener(im_b16_back_btn,tv_ceshi);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b16_back_btn:
			this.finish();
			break;
		case R.id.tv_ceshi:
			Intent jinbi = new Intent();
			jinbi.setClass(B1_6_JinBiShangChengActivity.this, B1_6_1_JinBiInfoActivity.class);
			startActivity(jinbi);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 请求服务器数据----二级分类
	 */
	private void getChildCateByPid(String parentId) {
		JsonObject requsetParams = new JsonObject();
		requsetParams.addProperty("parentId", parentId);
		mRequestQueue.add(new StringRequest(Request.Method.GET, HttpUtils.url_mallChildMenu(requsetParams.getAsString()), 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
						JsonObject status = jsonObject.get("status").getAsJsonObject();
						Gson gson = new Gson();
						if(status.get("succeed").getAsInt() == 1){
							String data = jsonObject.get("data").getAsString();
							ArrayList<HashMap<String, Object>> childs = gson.fromJson(data, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
							product_grid.setAdapter(new CommonAdapter<HashMap<String, Object>>(B1_6_JinBiShangChengActivity.this, R.layout.ui_b1_6_item_classify, childs) {
								@Override
								public void convert(ViewHolder holder, HashMap<String, Object> children) {
									
								}
							});
						}
					}
				}, new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("B1_6_JinBiShangCheng.ErrorResponse=" + error.getMessage());
						Toast.makeText(B1_6_JinBiShangChengActivity.this, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
					}
				}));
	}
	
	/**
	 * 请求服务器数据----一级分类
	 */
	private void requestData(){
		mRequestQueue.add(new StringRequest(Request.Method.GET, HttpUtils.url_mallParentMenu(), 
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
						JsonObject status = jsonObject.get("status").getAsJsonObject();
						if(status.get("succeed").getAsInt() == 1){
							JsonArray data = jsonObject.get("data").getAsJsonArray();
							for (int i = 0; i < data.size(); i++) {
								JsonObject parent = data.get(i).getAsJsonObject();
					            RadioButton radioButton = new RadioButton(B1_6_JinBiShangChengActivity.this);
					            radioButton.setId(parent.get("cateid").getAsInt());
					            radioButton.setText(parent.get("cateid").getAsString());
					            radioButton.setTextSize(18f);
					            radioButton.setPadding(20, 35, 20, 35);
					            radioButton.setTextColor(getResources().getColorStateList(R.drawable.classify_text));
					            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
					            radioButton.setBackgroundResource(R.drawable.checked_classify);
					            radioButton.setChecked(i==0);
					            category_list.addView(radioButton,mRadioParams);
					        }
						}
					}
				}, new Response.ErrorListener(){
					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("B1_6_JinBiShangCheng.ErrorResponse=" + error.getMessage());
						Toast.makeText(B1_6_JinBiShangChengActivity.this, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
					}
				}));
	}
	
	/**
	 * 请求服务器数据----二级分类
	 */
	/*private void getTwoCateByOne(String gc_id){
		HttpUtils.getGoodsClass(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				super.onSuccess(statusCode, headers, response);
				try {
					int code = response.getInt("code");
					list.clear();
					if(code != 200){
						Toast.makeText(B2_ClassifyActivity.this, "请求失败", Toast.LENGTH_LONG).show();
						return;
					}
					JSONObject data = response.getJSONObject("datas");
					JSONArray jsonArray = data.getJSONArray("class_list");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                	HashMap<String,String> map = new HashMap<String,String>();
	                    JSONObject twoClassify = jsonArray.getJSONObject(i);
	                    map.put("gc_id", twoClassify.getString("gc_id"));
	                    map.put("gc_name", twoClassify.getString("gc_name"));
	                    map.put("image", twoClassify.getString("image"));
	                    list.add(map);
	                }
	                product_grid.setAdapter(new BaseAdapter() {
						
						@Override
						public int getCount() { return list.size(); }
						
						@Override
						public HashMap<String,String> getItem(int postion) { return list.get(postion); }
						
						@Override
						public long getItemId(int arg0) { return 0; }
						
						@Override
						public View getView(int postion, View convertView, ViewGroup arg2) {
							convertView = LayoutInflater.from(B2_ClassifyActivity.this).inflate(R.layout.ui_b2_item_classify, null);
							
	                        TextView name= (TextView) convertView.findViewById(R.id.classify_name);
	                        ImageView image= (ImageView) convertView.findViewById(R.id.classify_image);
	                        name.setText(list.get(postion).get("gc_name"));
	                        ImageOptions.displayImage2Circle(image, list.get(postion).get("image"), 10f);
	                        
							return convertView;
						}
					});
	                product_grid.setCacheColorHint(0);
	                
	                product_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
							String gc_id = list.get(position).get("gc_id");
							Toast.makeText(B2_ClassifyActivity.this, gc_id, Toast.LENGTH_LONG).show();
			    			B1_a4_SearchActivity.CHANNEL=0;
							Intent intent = new Intent(B2_ClassifyActivity.this,B1_a4_SearchActivity.class);
							intent.putExtra("gc_id", gc_id);
							startActivity(intent);
						}
					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		},gc_id);
	}*/

}