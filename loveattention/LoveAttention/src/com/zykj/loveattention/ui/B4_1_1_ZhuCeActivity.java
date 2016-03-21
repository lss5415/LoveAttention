package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月11日	注册
 *
 */
public class B4_1_1_ZhuCeActivity extends BaseActivity {
	private ImageView im_b411_back_btn;//返回
	private TextView tv_getCode;//获取验证码
	
	private EditText et_phone;//手机号
	private EditText et_verifycode;//验证码
	private EditText et_invitecode;//邀请码
	private EditText et_scode;//密码
	
	private Button btn_certification;//确认
	
	public String mobile = null;
	public String password = null;
	public String verify_code = null;
	public String invite_code = null;
	
	private RequestQueue mRequestQueue; 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_1_1_zhuce);
		mRequestQueue =  Volley.newRequestQueue(this);  
		initView();
//	 	短信验证
		initSmsSDK();
	}
	private void initSmsSDK() {
		// TODO Auto-generated method stub
		// 初始化短信SDK
				SMSSDK.initSDK(this, AppValue.APPKey_mob, AppValue.APP_SECRE);
				EventHandler eventHandler = new EventHandler() {
					public void afterEvent(int event, int result, Object data) {
							Message msg = new Message();
							msg.arg1 = event;
							msg.arg2 = result;
							msg.obj = data;
							handler.sendMessage(msg);
					};
				};
				// 注册回调监听接口
				SMSSDK.registerEventHandler(eventHandler);
	}
	Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event="+event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				//短信注册成功后，返回MainActivity,然后提示新好友
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
//					Toast.makeText(getApplicationContext(), "提交验证码成功+可以在这里请求服务器，把手机号+密码+验证码传给服务器", Toast.LENGTH_SHORT).show();
					Map<String, String> map = new HashMap<String, String>();
					map.put("mobile", mobile);
					map.put("username", mobile);
					map.put("invitecode", invite_code);
					map.put("password", password);
					String json = JsonUtils.toJson(map);
					String URL = "http://115.28.67.86:8080/aigz/data/register?json="+json;
					Tools.Log("json="+json);
					JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,URL,null,new Response.Listener<JSONObject>() {  
			            @Override  
			            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) //成功
								{
									Toast.makeText(B4_1_1_ZhuCeActivity.this, "注册成功，请登录", Toast.LENGTH_LONG).show();
									B4_1_1_ZhuCeActivity.this.finish();
								}else {//失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(B4_1_1_ZhuCeActivity.this, errdesc, Toast.LENGTH_LONG).show();
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
			                Toast.makeText(B4_1_1_ZhuCeActivity.this, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
			            }  
			        });  
			        mRequestQueue.add(jr); 
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
					RequestDailog.closeDialog();
					Toast.makeText(getApplicationContext(), "验证码已经发送，请稍后", Toast.LENGTH_SHORT).show();
//					textView2.setText("验证码已经发送");
				}else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
//					Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
//					countryTextView.setText(data.toString());
				}
			} else {
				((Throwable) data).printStackTrace();
//				int resId = getStringRes(MainActivity.this, "smssdk_network_error");
				RequestDailog.closeDialog();
				Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
//				if (resId > 0) {
//					Toast.makeText(RegistActivity.this, resId, Toast.LENGTH_SHORT).show();
//				}
			}
			
		}
		
	};

	public void initView() {
		im_b411_back_btn = (ImageView) findViewById(R.id.im_b411_back_btn);
		tv_getCode = (TextView) findViewById(R.id.tv_getCode);
		et_phone = (EditText) findViewById(R.id.et_phone);
		et_verifycode = (EditText) findViewById(R.id.et_verifycode);
		et_invitecode = (EditText) findViewById(R.id.et_invitecode);
		et_scode = (EditText) findViewById(R.id.et_scode);
		btn_certification = (Button) findViewById(R.id.btn_certification);
		setListener(im_b411_back_btn,tv_getCode,btn_certification);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b411_back_btn:
			B4_1_1_ZhuCeActivity.this.finish();
		break;
		case R.id.tv_getCode://获取验证码
			if(!TextUtils.isEmpty(et_phone.getText().toString())){
				mobile=et_phone.getText().toString().trim();
				RequestDailog.showDialog(this, "正在发送验证码，请稍后");
				SMSSDK.getVerificationCode("86",mobile);
			}else {
				Toast.makeText(this, "电话不能为空", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.btn_certification:
			mobile = et_phone.getText().toString().trim();
			invite_code = et_invitecode.getText().toString().trim();
			verify_code = et_verifycode.getText().toString().trim();
			password = et_scode.getText().toString().trim();
			
			if (TextUtils.isEmpty(mobile)) {
				Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
			}else if (TextUtils.isEmpty(verify_code)) {
				Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
			}else if (TextUtils.isEmpty(password)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else {
				RequestDailog.showDialog(this, "正在注册，请稍后");
				SMSSDK.submitVerificationCode("86", mobile,verify_code);
			}
			break;
//		case R.id.im_denglu:
//			Intent itdenglu = new Intent();
//			itdenglu.setClass(B4_WoDeActivity.this, B4_1_DengLuActivity.class);
//			startActivity(itdenglu);
//			break;
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