package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

/**
 * 选择你的生活状态 页面（从我的DNA第二个页面跳转过来）
 * @author zhuyikun
 *
 */
public class B1_2_MyLifeState extends BaseActivity {
	
	private ImageView btn_back;//返回
	private Button btn_danshen;//单身
	private Button btn_relian;// 热恋
	private Button btn_jiehun;// 结婚
	private Button btn_youbaobao;// 有宝宝
	
	private Button btn_next2;//下一步
	
	
	private int danshen_ischosed = 0; 
	private int relian_ischosed = 0; 
	private int jiehun_ischosed = 0; 
	private int youbaobao_ischosed = 0;
	private String json;
	private RequestQueue mRequestQueue;
	
	private int total_of_ischosed = 0; // 判断选择了多少个选项
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b1_1_mydna2);
		mRequestQueue = Volley.newRequestQueue(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_back = (ImageView) findViewById(R.id.im_back_btn2);
		
		btn_danshen = (Button) findViewById(R.id.btn_danshen);
		btn_relian = (Button) findViewById(R.id.btn_relian);
		btn_jiehun = (Button) findViewById(R.id.btn_jiehun);
		btn_youbaobao = (Button) findViewById(R.id.btn_youbaobao);
		
		btn_next2 = (Button) findViewById(R.id.btn_next2);
		
		setListener(btn_back,
				btn_danshen,btn_relian,btn_jiehun,btn_youbaobao,
				btn_next2);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_back_btn2:
			this.finish();
			break;
		case R.id.btn_danshen:
			if (danshen_ischosed == 0) {
				btn_danshen.setBackgroundResource(R.drawable.btn_after);
				btn_danshen.setTextColor(android.graphics.Color.WHITE);
				danshen_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_danshen.setBackgroundResource(R.drawable.btn_before);
				btn_danshen.setTextColor(android.graphics.Color.BLACK);
				danshen_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_relian:
			if (relian_ischosed == 0) {
				btn_relian.setBackgroundResource(R.drawable.btn_after);
				btn_relian.setTextColor(android.graphics.Color.WHITE);
				relian_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_relian.setBackgroundResource(R.drawable.btn_before);
				btn_relian.setTextColor(android.graphics.Color.BLACK);
				relian_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_jiehun:
			if (jiehun_ischosed == 0) {
				btn_jiehun.setBackgroundResource(R.drawable.btn_after);
				btn_jiehun.setTextColor(android.graphics.Color.WHITE);
				jiehun_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_jiehun.setBackgroundResource(R.drawable.btn_before);
				btn_jiehun.setTextColor(android.graphics.Color.BLACK);
				jiehun_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_youbaobao:
			if (youbaobao_ischosed == 0) {
				btn_youbaobao.setBackgroundResource(R.drawable.btn_after);
				btn_youbaobao.setTextColor(android.graphics.Color.WHITE);
				youbaobao_ischosed = 1;
				total_of_ischosed++;
			}else {
				btn_youbaobao.setBackgroundResource(R.drawable.btn_before);
				btn_youbaobao.setTextColor(android.graphics.Color.BLACK);
				youbaobao_ischosed = 0;
				total_of_ischosed--;
			}
			break;
		case R.id.btn_next2:
			if (total_of_ischosed>1) {
				Toast.makeText(this, "只能选择一种生活状态哦～", Toast.LENGTH_LONG).show();
			}else if (total_of_ischosed == 0) {
				Toast.makeText(this, "您需要选择一种生活状态哦～", Toast.LENGTH_LONG).show();
			}else{
				String gongzuo = getIntent().getStringExtra("gongzuo");
				String xingqu = getIntent().getStringExtra("xingqu");
				String hunyin = "22";
				String id = getSharedPreferenceValue("id");
//				data/saveDna?json={"memberid":"4","hobyid":"1","occupationid":"1","lifeid":"1"}
				Map<String, String> map = new HashMap<String, String>();
				map.put("memberid", "4");
				map.put("hobyid", xingqu);
				map.put("occupationid", gongzuo);
				map.put("lifeid", hunyin);
				json = JsonUtils.toJson(map);
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
						HttpUtils.url_saveDna(json), null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								RequestDailog.closeDialog();
								JSONObject status;
								try {
									status = response.getJSONObject("status");
									String succeed = status.getString("succeed");
									if (succeed.equals("1")) {
										Toast.makeText(B1_2_MyLifeState.this, "您的DNA信息保存成功", Toast.LENGTH_LONG).show();
//										Intent itmain = new Intent(B1_2_MyLifeState.this,B1_ShouYeActivity.class);
//										startActivity(itmain);
										setResult(RESULT_OK);
										B1_2_MyLifeState.this.finish();
									}else {
										B1_2_MyLifeState.this.finish();
										Toast.makeText(B1_2_MyLifeState.this, "您的DNA信息保存失败", Toast.LENGTH_LONG).show();
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
								Toast.makeText(B1_2_MyLifeState.this, "网络连接失败，请重试",
										Toast.LENGTH_LONG).show();
							}
						});
				mRequestQueue.add(jr);
			}
			break;
		default:
			break;
		}
	}
	
	
	
}
