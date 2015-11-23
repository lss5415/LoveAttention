package com.zykj.loveattention.ui;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B5_Feedback extends BaseActivity {

	private ImageView im_back_feedback;
	private TextView tv_send;
	private EditText et_content;
	private RequestQueue mRequestQueue; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b5_feedback);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this); 
	}
	private void initView() {
		// TODO Auto-generated method stub
		im_back_feedback = (ImageView) findViewById(R.id.im_back_feedback);
		tv_send = (TextView) findViewById(R.id.tv_send);
		et_content = (EditText) findViewById(R.id.et_content);
		
		setListener(im_back_feedback,tv_send);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back_feedback:
			this.finish();
			break;
		case R.id.tv_send:
			String content = et_content.getText().toString();
			if (content.equals("")) {
				Toast.makeText(this, "您没有输入任何内容", Toast.LENGTH_LONG).show();
			}else {
				RequestDailog.showDialog(this, "正在发送反馈，请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("memberid", getSharedPreferenceValue("id"));
				map.put("content", content);
				String json = JsonUtils.toJson(map);
				Log.e("123", HttpUtils.url_feedback(json)+"");
				//请求添加反馈接口
				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HttpUtils.url_feedback(json), null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
					  	RequestDailog.closeDialog();
					  	//{"status":{"succeed":1,"errcode":"0000"}}
						try {
							JSONObject status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) //成功
							{
								Toast.makeText(B5_Feedback.this, "反馈成功", Toast.LENGTH_LONG).show();
								Intent itwode = new Intent(B5_Feedback.this,B0_MainActivity.class);
								startActivity(itwode);
								
							}else {//失败,提示失败信息
								Toast.makeText(getApplicationContext(), "发送反馈失败，请重试", Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						RequestDailog.closeDialog();
		                Tools.Log("ErrorResponse="+error.getMessage());
		                Toast.makeText(getApplicationContext(), "网络连接失败，请重试", Toast.LENGTH_LONG).show();
					}
				});
				mRequestQueue.add(jsonObjectRequest);  
			}
			break;

		default:
			break;
		}
	}
}
