package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class B4_1_DengLuActivity extends BaseActivity {
	private TextView tv_b41_zhuce;//注册
	private TextView tv_forgetPassWord;//忘记密码
	private ImageView im_b541_back_btn;//返回
	private Button btn_login;
	private EditText et_login_name;//用户名／手机号
	private EditText et_passWord;//密码
	private RequestQueue mRequestQueue; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_1_denglu);
		initView();
		mRequestQueue =  Volley.newRequestQueue(this);  
	}
	public void initView() {
		tv_b41_zhuce = (TextView) findViewById(R.id.tv_b41_zhuce);
		tv_forgetPassWord = (TextView) findViewById(R.id.tv_forgetPassWord);
		im_b541_back_btn = (ImageView) findViewById(R.id.im_b541_back_btn);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_login_name = (EditText) findViewById(R.id.et_login_name);
		et_passWord = (EditText) findViewById(R.id.et_passWord);
		setListener(tv_b41_zhuce,tv_forgetPassWord,im_b541_back_btn,btn_login);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tv_b41_zhuce://注册
			Intent itzhuce = new Intent();
			itzhuce.setClass(B4_1_DengLuActivity.this, B4_1_1_ZhuCeActivity.class);
			startActivity(itzhuce);
			break;
		case R.id.tv_forgetPassWord://忘记密码
			Intent itforgot = new Intent();
			itforgot.setClass(B4_1_DengLuActivity.this, B4_1_2_WangJiMiMaActivity.class);
			startActivity(itforgot);
			break;
		case R.id.btn_login:
			
			String username = et_login_name.getText().toString().trim();
			String password = et_passWord.getText().toString().trim();
			if (TextUtils.isEmpty(username)) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
			}else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else {
				RequestDailog.showDialog(this, "正在登录请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username);
//				map.put("mobile", username);
				map.put("password", password);
				String json = JsonUtils.toJson(map);
//				Tools.Log(json);
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_login(json),null,new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) //成功
								{
									JSONObject data = response.getJSONObject("data");
									String id = data.getString("id");
									String name = data.getString("name");
									String mobile = data.getString("mobile");
									String invitecode = data.getString("invitecode");
									putSharedPreferenceValue("id", id);
									putSharedPreferenceValue("name", name);
									putSharedPreferenceValue("mobile", mobile);
									putSharedPreferenceValue("invitecode", invitecode);
									putSharedPreferenceValue("isLoged", "1");
									Toast.makeText(B4_1_DengLuActivity.this, "登录成功", Toast.LENGTH_LONG).show();
									Intent itwode = new Intent(B4_1_DengLuActivity.this,B0_MainActivity.class);
									startActivity(itwode);
									
								}else {//失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(B4_1_DengLuActivity.this, errdesc, Toast.LENGTH_LONG).show();
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
		                Toast.makeText(B4_1_DengLuActivity.this, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		            }  
		        });  
		        mRequestQueue.add(jr);  
			}
			break;
		case R.id.im_b541_back_btn:
			this.finish();
			break;
		default:
			break;

		}
	}
	@Override  
	protected void onStop() {  
	    // TODO Auto-generated method stub  
	    super.onStop();  
	    mRequestQueue.cancelAll(this);  
	}  

}