package com.zykj.loveattention.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_5_YaoQingAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Share;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.XListView;

/**
 * @author lss 2015年8月12日	我的邀请
 *
 */
public class B4_5_WoDeYaoQingActivity extends BaseActivity {
	private ImageView im_b45_back_btn;//返回
	private XListView lv_yaoqing;
	private B4_5_YaoQingAdapter yqadapter;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private TextView tv_myyaoqingren;
	private TextView tv_name;//昵称
	private TextView tv_invitecode;//邀请码
	private ImageView iv_invite;//邀请
	
	private RequestQueue mRequestQueue;
	
	private String ShareContent ;
	private String ShareTitle;
	private String ShareUrl ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_5_wodeyaoqing);
		initView();
		initShare();//初始化分享模块
		mRequestQueue = Volley.newRequestQueue(this);
		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_myinvite(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							JSONObject data = response.getJSONObject("data");
							Log.e("data", data+"");
						}else {//失败,提示失败信息
							String errdesc = status.getString("errdesc");
							Toast.makeText(getApplicationContext(), errdesc, Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "网络连接失败，请重试", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
		setAdapter();
	}


	private void initShare() {
		ShareContent = "我的爱关注邀请码是:"+getSharedPreferenceValue("invitecode")+"，一起来爱关注吧，http://www.pgyer.com/ov1S";
		ShareTitle = "我的邀请码："+getSharedPreferenceValue("invitecode");
		ShareUrl = "http://www.pgyer.com/ov1S";
	}


	public void initView() {
		im_b45_back_btn = (ImageView) findViewById(R.id.im_b45_back_btn);
		iv_invite = (ImageView) findViewById(R.id.iv_invite);
		lv_yaoqing = (XListView) findViewById(R.id.lv_yaoqing);
		tv_myyaoqingren = (TextView) findViewById(R.id.tv_myyaoqingren);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_invitecode = (TextView) findViewById(R.id.tv_invitecode);
		
		tv_name.setText(getSharedPreferenceValue("name"));
		tv_invitecode.setText("我的邀请码:  "+getSharedPreferenceValue("invitecode"));
		
		setListener(im_b45_back_btn,tv_myyaoqingren,iv_invite);
	}
	
	public void setAdapter(){
		for (int i = 0; i < 20; i++) {
			Map<String, String> itemData = new HashMap<String, String>();
			itemData.put("name", "小明");
			data.add(itemData);
		}
		yqadapter = new B4_5_YaoQingAdapter(this, data);
		lv_yaoqing.setAdapter(yqadapter);
		lv_yaoqing.setPullLoadEnable(true);
		lv_yaoqing.setPullRefreshEnable(true);
//		SimpleDateFormat formatter = new SimpleDateFormat(
//				"yyyy年MM月dd日   HH:mm:ss     ");
//		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
//		String str = formatter.format(curDate);
//		lv_yaoqing.setRefreshTime(str + "刷新");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b45_back_btn:
			this.finish();
			break;
		case R.id.tv_myyaoqingren:
			Intent itdenglu = new Intent();
			itdenglu.setClass(B4_5_WoDeYaoQingActivity.this, B4_5_1_MyYaoQingRenActivity.class);
			startActivity(itdenglu);
			break;
		case R.id.iv_invite://邀请
			Share.invit(this, ShareContent, ShareTitle, ShareUrl);
			break;
		default:
			break;
		}
	}

}