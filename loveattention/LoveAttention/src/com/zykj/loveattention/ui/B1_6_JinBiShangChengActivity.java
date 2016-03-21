package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.CommonAdapter;
import com.zykj.loveattention.adapter.ViewHolder;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.ImageOptions;
import com.zykj.loveattention.utils.StringUtil;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B1_6_JinBiShangChengActivity extends BaseActivity implements OnItemClickListener{
	private ImageView im_b16_back_btn;//返回
	private RadioGroup category_list;//分类左侧
    private RadioGroup.LayoutParams mRadioParams;
	private GridView product_grid;//分类右侧
	private RequestQueue mRequestQueue;
	private ImageLoader imageLoader;
	private ArrayList<HashMap<String, Object>> childs;
	TextView tv_ceshi;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_jinbishangcheng);
		mRequestQueue = Volley.newRequestQueue(this);
		imageLoader = ImageLoader.getInstance();
		
		initView();
		requestData();
	}


	public void initView() {
		im_b16_back_btn = (ImageView) findViewById(R.id.im_b16_back_btn);
		category_list = (RadioGroup)findViewById(R.id.category_list);//父分类
		mRadioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		product_grid = (GridView)findViewById(R.id.product_grid);//子分类
		product_grid.setOnItemClickListener(this);
		category_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                getChildCateByPid(String.valueOf(checkedId));
            }
        });
		setListener(im_b16_back_btn);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b16_back_btn:
			this.finish();
			break;
		default:
			break;
		}
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
				            radioButton.setText(parent.get("name").getAsString());
				            radioButton.setTextSize(16f);
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
			})
		);
	}
	
	/**
	 * 请求服务器数据----二级分类
	 */
	private void getChildCateByPid(String parentid) {
		JsonObject requsetParams = new JsonObject();
		requsetParams.addProperty("parentid", parentid);
		mRequestQueue.add(new StringRequest(Request.Method.GET, HttpUtils.url_mallChildMenu(requsetParams.toString()), 
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
					JsonObject status = jsonObject.get("status").getAsJsonObject();
					Gson gson = new Gson();
					if(status.get("succeed").getAsInt() == 1){
						String data = jsonObject.get("data").getAsJsonArray().toString();
						childs = gson.fromJson(data, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
						product_grid.setAdapter(new CommonAdapter<HashMap<String, Object>>(B1_6_JinBiShangChengActivity.this, R.layout.ui_b1_6_item_classify, childs) {
							@Override
							public void convert(ViewHolder holder, HashMap<String, Object> children) {
								holder.setText(R.id.classify_name, (String)children.get("name"));
								if(!StringUtil.isEmpty((String)children.get("imgpath"))){
									ImageView imageView = holder.getView(R.id.classify_image);
									imageLoader.displayImage(AppValue.ImgUrl+(String)children.get("imgpath"), imageView, 
											ImageOptions.getOpstion(), new AnimateFirstDisplayListener());
								}
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
			})
		);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View converView, int position, long id) {
		HashMap<String, Object> map = childs.get(position);
		startActivity(new Intent(B1_6_JinBiShangChengActivity.this, B1_6_1_JinBiInfoActivity.class).putExtra("cateId", map.get("cid")+""));
	}
}