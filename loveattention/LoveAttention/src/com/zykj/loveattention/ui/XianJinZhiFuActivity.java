package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.pingplusplus.android.PaymentActivity;
import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class XianJinZhiFuActivity  extends BaseActivity{
	private ImageView jinezhifu_back,im_zhifubao,im_weixin;
	private RequestQueue mRequestQueue;
	private TextView tv_yingfu,et_shiyong,tv_haixuzhifu,tv_queren;//应付金额，所使用积分,还需支付
	private int zhifu=0;
	private String merchantid,score="0";
    private static final String CHANNEL_WECHAT = "wx";//通过微信充值
    private static final String CHANNEL_ALIPAY = "alipay";//通过支付宝充值
    private static final int REQUEST_CODE_PAYMENT = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_jinezhifu);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
	}
	
	public void initView(){
		jinezhifu_back = (ImageView)findViewById(R.id.jinezhifu_back);
		tv_yingfu = (TextView)findViewById(R.id.tv_yingfu);
		et_shiyong = (EditText)findViewById(R.id.et_shiyong);
		tv_haixuzhifu=(TextView)findViewById(R.id.tv_haixuzhifu);
		im_zhifubao = (ImageView) findViewById(R.id.im_zhifubao);
		im_weixin = (ImageView) findViewById(R.id.im_weixin);
		tv_queren = (TextView)findViewById(R.id.tv_queren);
		merchantid = getIntent().getStringExtra("merchantid");
		
		try {
			tv_yingfu.setText(getIntent().getStringExtra("zhifujine")+"元");
			tv_haixuzhifu.setText(getIntent().getStringExtra("zhifujine")+"元");
		} catch (Exception e) {
			
		}
		
		setListener(jinezhifu_back,im_zhifubao,im_weixin,tv_queren);
	}
	
	@Override
	public void  onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
        case R.id.jinezhifu_back:
        	XianJinZhiFuActivity.this.finish();
        	break;
		case R.id.im_zhifubao:
			setState();
			im_zhifubao.setImageResource(R.drawable.xuanzhong);
			zhifu=0;
			break;
		case R.id.im_weixin:
			setState();
			im_weixin.setImageResource(R.drawable.xuanzhong);
			zhifu=1;
			break;
		case R.id.tv_queren:
			if (zhifu==0) {
				Toast.makeText(XianJinZhiFuActivity.this, "支付宝暂不可用", Toast.LENGTH_LONG).show();
				ToZhiFu(CHANNEL_ALIPAY);
			}else if (zhifu==1) {
				Toast.makeText(XianJinZhiFuActivity.this, "微信支付", Toast.LENGTH_LONG).show();
				ToZhiFu(CHANNEL_WECHAT);
			}
			break;
        default:
        	break;
        }
   }

	public void setState(){
		im_zhifubao.setImageResource(R.drawable.weixuanzhong);
		im_weixin.setImageResource(R.drawable.weixuanzhong);
	}
	
	//当面立即付款
		public void ToZhiFu(String chanel){
			RequestDailog.showDialog(this, "正在加载，请稍后");
			Map<String, String> map = new HashMap<String, String>();
			map.put("memberid", getSharedPreferenceValue("id"));
			map.put("merchantid", merchantid);
			map.put("amount", merchantid);
			map.put("score", score);
			map.put("channel", chanel);
			map.put("remark", "");
			String json = JsonUtils.toJson(map);
			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_payOrder(json), null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							RequestDailog.closeDialog();
							JSONObject status;
						try {
							Intent intent = new Intent();
							String packageName = getPackageName();
							ComponentName componentName = new ComponentName(
									packageName, packageName
											+ ".wxapi.WXPayEntryActivity");
							intent.setComponent(componentName);
							intent.putExtra(PaymentActivity.EXTRA_CHARGE,
									response.toString());
							startActivityForResult(intent, REQUEST_CODE_PAYMENT);

						} catch (Exception e) {
							
						}

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							RequestDailog.closeDialog();
							Tools.Log("ErrorResponse=" + error.getMessage());
//							Toast.makeText(B3_FaXianActivity.this, "网络连接失败，请重试",
//									Toast.LENGTH_LONG).show();
						}
					});
			mRequestQueue.add(jr);
		}

	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	        //支付页面返回处理
	        if (requestCode == REQUEST_CODE_PAYMENT) {
	            if (resultCode == Activity.RESULT_OK) {
	                String result = data.getExtras().getString("pay_result");
	                /* 处理返回值
	                 * "success" - payment succeed
	                 * "fail"    - payment failed
	                 * "cancel"  - user canceld
	                 * "invalid" - payment plugin not installed
	                 */
//	                Tools.Log("支付结果="+result);
//	                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
//	                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//	                showMsg(result, errorMsg, extraMsg);
	                if (result.equals("success")) {
						Tools.Notic(XianJinZhiFuActivity.this, "您已经支付成功", new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
//								Intent intent_toMy = new Intent(XianJinZhiFuActivity.this,B5_MyActivity.class);
//								startActivity(intent_toMy);
//								XianJinZhiFuActivity.this.finish();
							}
						});
					}else if (result.equals("fail")) {
						Tools.Notic(XianJinZhiFuActivity.this, "支付失败，请重试", null);
					}else if (result.equals("cancel")) {
						Tools.Notic(XianJinZhiFuActivity.this, "支付取消", null);
					}else if (result.equals("invalid")) {
						Tools.Notic(XianJinZhiFuActivity.this, "支付失败，请重新充值", null);
						
					}
	            } else if (resultCode == Activity.RESULT_CANCELED) {
	            	Tools.Notic(XianJinZhiFuActivity.this, "支付取消", null);
	            }
	        }
	    }
	    
}
