package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.zykj.loveattention.adapter.AdressAdapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月14日	新增地址
 *
 */
public class B4_10_2_1_XinZengDiZhiActivity extends BaseActivity {
	private ImageView im_b410_21_back_btn;//返回
	private EditText tv_name,tv_phone,tv_xiangxi;
	private TextView tv_sheng,tv_shi,tv_qu;
	private String state = "0",addressid;//0，新增，1,编辑
	private TextView tv_queding;
	private RequestQueue mRequestQueue;
	private String[] strsheng,strshengid,strshi,strshiid,strqu,strquid;
	private int ssq=0;//0省，1市，2区
	private String qingqiuid="86";
//	private String provinceid,cityid,countyid;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b4_10_21_xinzengdizhi);
		initView();
		mRequestQueue = Volley.newRequestQueue(this);
	}


	public void initView() {
		im_b410_21_back_btn = (ImageView) findViewById(R.id.im_b410_21_back_btn);
		tv_name = (EditText) findViewById(R.id.tv_name);
		tv_phone = (EditText) findViewById(R.id.tv_phone);
		tv_sheng = (TextView) findViewById(R.id.tv_sheng);
		tv_shi = (TextView) findViewById(R.id.tv_shi);
		tv_qu = (TextView) findViewById(R.id.tv_qu);
		tv_xiangxi = (EditText) findViewById(R.id.tv_xiangxi);
		tv_queding = (TextView) findViewById(R.id.tv_queding);
		try {
			state = getIntent().getStringExtra("state");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (state.equals("1")) {
			try {
				tv_name.setText(getIntent().getStringExtra("name"));
				tv_phone.setText(getIntent().getStringExtra("dianhua"));
				tv_sheng.setText(getIntent().getStringExtra("sheng"));
				tv_shi.setText(getIntent().getStringExtra("shi"));
				tv_qu.setText(getIntent().getStringExtra("qu"));
				tv_xiangxi.setText(getIntent().getStringExtra("xiangxi"));
				try {
					addressid = getIntent().getStringExtra("addressid");
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}else {
			
		}
		
		setListener(im_b410_21_back_btn,tv_queding,tv_sheng,tv_shi,tv_qu);
	}

	@Override
	public void onClick(final View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_b410_21_back_btn:
			this.finish();
			break;
		case R.id.tv_queding:
			if (getIntent().getStringExtra("state").equals("1")) {
				EditDizhi();
			}else {
				AddDizhi();
			}
			break;
		case R.id.tv_sheng:
			qingqiuid = "86";
			getSheng(0);
			break;
		case R.id.tv_shi:
			qingqiuid = "86";
			getSheng(0);
			break;
		case R.id.tv_qu:
			qingqiuid = "86";
			getSheng(0);
			break;
		default:
			break;

		}
	}

	public void AddDizhi(){

		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		map.put("name", tv_name.getText().toString());
		map.put("mobile", tv_phone.getText().toString());
		map.put("province", tv_sheng.getText().toString());
		map.put("city", tv_shi.getText().toString());
		map.put("county", tv_qu.getText().toString());
		map.put("detailaddress", tv_xiangxi.getText().toString());
		
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_addressinfoAdd(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
//							org.json.JSONArray data = response.getJSONArray("data");
							Toast.makeText(B4_10_2_1_XinZengDiZhiActivity.this, "地址保存成功", Toast.LENGTH_LONG).show();
							B4_10_2_1_XinZengDiZhiActivity.this.finish();
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
                Toast.makeText(getApplicationContext(), "此处需要改成可选择城市", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "网络连接失败，请重试", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
	}
	
	public void EditDizhi(){

		Map<String , String > map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("memberid"));
		map.put("addressid", addressid);
		map.put("name", tv_name.getText().toString());
		map.put("mobile", tv_phone.getText().toString());
		map.put("province", tv_sheng.getText().toString());
		map.put("city", tv_shi.getText().toString());
		map.put("county", tv_qu.getText().toString());
		map.put("detailaddress", tv_xiangxi.getText().toString());
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_addressinfoUpdate(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							Toast.makeText(B4_10_2_1_XinZengDiZhiActivity.this, "地址保存成功", Toast.LENGTH_LONG).show();
							B4_10_2_1_XinZengDiZhiActivity.this.finish();
//							yqadapter = new AdressAdapter(
//									B4_10_2_ShouHuoDiZhiActivity.this, daray,getSharedPreferenceValue("id"));
//							lv_dizhilist.setAdapter(yqadapter);
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
                Toast.makeText(getApplicationContext(), "此处需要改成可选择城市", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
	}
	
	//获取省市区
	public void getSheng(final int ssq){
		Map<String , String > map = new HashMap<String, String>();
		map.put("citynum", qingqiuid);
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_getcitylist(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							org.json.JSONArray data = response.getJSONArray("data");
							if (ssq==0) {
								strsheng = new String[data.length()];
								strshengid = new String[data.length()];
								for (int i = 0; i < data.length(); i++) {
									strsheng[i] = data.getJSONObject(i).getString("cityname");
									strshengid[i] = data.getJSONObject(i).getString("citynum");
								}
								TanChuang(ssq,strsheng,strshengid);
							}else if (ssq==1) {
								strshi = new String[data.length()];
								strshiid = new String[data.length()];
								for (int i = 0; i < data.length(); i++) {
									strshi[i] = data.getJSONObject(i).getString("cityname");
									strshiid[i] = data.getJSONObject(i).getString("citynum");
								}
								TanChuang(ssq,strshi,strshiid);
							}else if (ssq==2) {
								strqu = new String[data.length()];
								strquid = new String[data.length()];
								for (int i = 0; i < data.length(); i++) {
									strqu[i] = data.getJSONObject(i).getString("cityname");
									strquid[i] = data.getJSONObject(i).getString("citynum");
								}
								TanChuang(ssq,strqu,strquid);
							}
							
							
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
                Toast.makeText(getApplicationContext(), "网络连接错误，请检查网络。", Toast.LENGTH_LONG).show();
            }  
        });  
        mRequestQueue.add(jr);  
	}
	
	public void TanChuang(final int ssq,final String[] name,final String[] id){
		String title = "请选择";
		if (ssq == 0) {//省
			title = "请选择省";
			new AlertDialog.Builder(B4_10_2_1_XinZengDiZhiActivity.this)
			.setTitle(title)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setSingleChoiceItems(name, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
//							Toast.makeText(B4_10_2_1_XinZengDiZhiActivity.this, name[which], Toast.LENGTH_LONG).toString();
							tv_sheng.setText(name[which]);
							qingqiuid = id[which];
//							provinceid = String.valueOf(id[which]);
							getSheng(ssq+1);
						}
						
					}).setNegativeButton("取消", null).show();
		}else if (ssq == 1) {//市
			title = "请选择市";
			new AlertDialog.Builder(B4_10_2_1_XinZengDiZhiActivity.this)
			.setTitle(title)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setSingleChoiceItems(name, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
//							Toast.makeText(B4_10_2_1_XinZengDiZhiActivity.this, name[which], Toast.LENGTH_LONG).toString();
							tv_shi.setText(name[which]);
							qingqiuid = id[which];
//							cityid = String.valueOf(id[which]);
							getSheng(ssq+1);
						}
						
					}).setNegativeButton("取消", null).show();
		}else if (ssq == 2){//区
			title = "请选择区";
			new AlertDialog.Builder(B4_10_2_1_XinZengDiZhiActivity.this)
			.setTitle(title)
			.setIcon(android.R.drawable.ic_dialog_info)
			.setSingleChoiceItems(name, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
//							Toast.makeText(B4_10_2_1_XinZengDiZhiActivity.this, name[which], Toast.LENGTH_LONG).toString();
							tv_qu.setText(name[which]);
							qingqiuid = "86";
//							countyid = String.valueOf(id[which]);
						}
						
					}).setNegativeButton("取消", null).show();
		}
		
	}
	
}