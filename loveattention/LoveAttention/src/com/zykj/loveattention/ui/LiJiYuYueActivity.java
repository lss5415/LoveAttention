package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B1_7_3_YouHuiAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.DianPuGoodsInfo;
import com.zykj.loveattention.data.MerchantyhInfo;
import com.zykj.loveattention.data.PingLun;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss
 * 
 */
public class LiJiYuYueActivity extends BaseActivity {
	private ImageView sp_ljyy_back;
	private String goodsid, shangjia, goodname;
	private TextView tv_shangjia, tv_huodong, tv_queren;
	private EditText et_time, et_renshu, et_beizhu;
	private RequestQueue mRequestQueue; 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_lijiyuyue);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this);
	}

	public void initView() {
		sp_ljyy_back = (ImageView) findViewById(R.id.sp_ljyy_back);
		tv_shangjia = (TextView) findViewById(R.id.tv_shangjia);
		tv_huodong = (TextView) findViewById(R.id.tv_huodong);
		et_time = (EditText) findViewById(R.id.et_time);
		et_renshu = (EditText) findViewById(R.id.et_renshu);
		et_beizhu = (EditText) findViewById(R.id.et_beizhu);
		tv_queren = (TextView) findViewById(R.id.tv_queren);

		try {
			goodsid = getIntent().getStringExtra("goodsid");
			shangjia = getIntent().getStringExtra("shangjia");
			goodname = getIntent().getStringExtra("goodname");
			tv_shangjia.setText("商家: "+shangjia);
			tv_huodong.setText("活动： "+goodname);
		} catch (Exception e) {
			goodsid = "";
		}

		setListener(sp_ljyy_back, tv_queren);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.sp_ljyy_back:
			this.finish();
			break;
		case R.id.tv_queren:
			if (et_time.getText().length()<1) {
				Toast.makeText(this, "时间不能为空", Toast.LENGTH_LONG).show();
			}else if (et_renshu.getText().length()<1) {
				Toast.makeText(this, "人数不能为空", Toast.LENGTH_LONG).show();
			}else if (et_beizhu.getText().length()<1) {
				et_beizhu.setText("");
			}else {
				requestData();
			}
			break;
		default:
			break;
		}
	}

	private void requestData() {
		/** 立即预约 */
		RequestDailog.showDialog(this, "正在加载，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("goodid", goodsid);
		map.put("personNum", et_renshu.getText().toString());
		map.put("datetime", et_time.getText().toString());
		map.put("otherinfo", et_beizhu.getText().toString());
		String jsonljyy = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_fillReseration(jsonljyy),null,new Response.Listener<JSONObject>() {
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
	            	Toast.makeText(LiJiYuYueActivity.this, "预约成功", Toast.LENGTH_LONG).show();
	            	LiJiYuYueActivity.this.finish();	            	
            }  
        },new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
            	RequestDailog.closeDialog();
                Tools.Log("ErrorResponse="+error.getMessage());
            }  
        });  
        mRequestQueue.add(jr);  
		
	}
}