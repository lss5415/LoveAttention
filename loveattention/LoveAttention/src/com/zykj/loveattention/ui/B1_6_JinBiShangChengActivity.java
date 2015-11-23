package com.zykj.loveattention.ui;

import java.util.HashMap;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class B1_6_JinBiShangChengActivity extends BaseActivity {
	private ImageView im_b16_back_btn;//返回
	private RadioGroup category_list;//分类左侧
    private RadioGroup.LayoutParams mRadioParams;
	private GridView product_grid;//分类右侧
	TextView tv_ceshi;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_6_jinbishangcheng);
		initView();
		requestData();
	}


	public void initView() {
		im_b16_back_btn = (ImageView) findViewById(R.id.im_b16_back_btn);
		category_list = (RadioGroup)findViewById(R.id.category_list);
		mRadioParams = new RadioGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		product_grid = (GridView)findViewById(R.id.product_grid);
		tv_ceshi = (TextView)findViewById(R.id.tv_ceshi);

		
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
	 * 请求服务器数据----一级分类
	 */
	private void requestData(){
//		HttpUtils.getGoodsClass(new JsonHttpResponseHandler(){.....
		String[] yiji = new String[] { "美食", "电影", "酒店","休闲娱乐", "生活服务", "旅游" , "购物", "丽人",  "生活"};
		for (int i = 0; i < 9; i++) {
            RadioButton radioButton = new RadioButton(B1_6_JinBiShangChengActivity.this);
            radioButton.setText(yiji[i]);
            radioButton.setTextSize(18f);
            radioButton.setPadding(20, 35, 20, 35);
            radioButton.setTextColor(getResources().getColorStateList(R.drawable.classify_text));
            radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
            radioButton.setBackgroundResource(R.drawable.checked_classify);
            radioButton.setChecked(i==0?true:false);
            category_list.addView(radioButton,mRadioParams);
        }
		category_list.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                //oneid = String.valueOf(checkedId);
//                getTwoCateByOne(String.valueOf(checkedId));
            }
        });
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