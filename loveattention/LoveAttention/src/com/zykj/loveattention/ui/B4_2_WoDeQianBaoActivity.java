package com.zykj.loveattention.ui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.fragment.B4_2_WoDeJiFen_Fragment;
import com.zykj.loveattention.fragment.B4_2_WoDeJinBi_Fragment;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月11日	我的钱包
 *
 */
public class B4_2_WoDeQianBaoActivity extends FragmentActivity implements OnClickListener{
	private ImageView im_b42_qbback_btn;//返回
	private RadioButton rb_wodejifen,rb_wodejinbi;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private TextView tv_wodejifen,tv_wodejinbi,tv_wodejifen1,tv_wodejinbi1;
	private RequestQueue mRequestQueue; 
	private String id = null;
	private String name = null;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.ui_b4_2_wodeqianbao);
		id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		tv_wodejifen = (TextView) findViewById(R.id.tv_wodejifen);
		tv_wodejinbi = (TextView) findViewById(R.id.tv_wodejinbi);
		tv_wodejifen1 = (TextView) findViewById(R.id.tv_wodejifen1);
		tv_wodejinbi1 = (TextView) findViewById(R.id.tv_wodejinbi1);
		im_b42_qbback_btn = (ImageView) findViewById(R.id.im_b42_qbback_btn);
		rb_wodejifen = (RadioButton) findViewById(R.id.rb_wodejifen);
		rb_wodejinbi = (RadioButton) findViewById(R.id.rb_wodejinbi);
		rb_wodejifen.setOnClickListener(this);
		rb_wodejinbi.setOnClickListener(this);
		im_b42_qbback_btn.setOnClickListener(this);
		rb_wodejifen.setChecked(true);
		if(rb_wodejifen.isChecked())
		{
			fm=getSupportFragmentManager();
			FragmentTransaction ft=fm.beginTransaction();
			B4_2_WoDeJiFen_Fragment f2_NewActivity_IngFragment=new B4_2_WoDeJiFen_Fragment();
			Bundle bundle = new Bundle();
			bundle.putString("name",name);
			f2_NewActivity_IngFragment.setArguments(bundle);
			ft.add(R.id.fl_qianbaofragment, f2_NewActivity_IngFragment,"one");
			ft.commit();
		}
		mRequestQueue =  Volley.newRequestQueue(this); 
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", id);
		String json = JsonUtils.toJson(map);
		Tools.Log(json);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_wallet(json),null,new Response.Listener<JSONObject>() {  
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
	}

	@Override
	public void onClick(View v) {
		ft=fm.beginTransaction();
		if(fm.findFragmentByTag("one")!=null){
			ft.hide(fm.findFragmentByTag("one"));
		}
		if(fm.findFragmentByTag("two")!=null){
			ft.hide(fm.findFragmentByTag("two"));
		}
		switch (v.getId()) {
		case R.id.im_b42_qbback_btn:
			this.finish();
			break;
		case R.id.rb_wodejifen: //点击 --我的积分
			tv_wodejifen.setVisibility(View.VISIBLE);
			tv_wodejinbi.setVisibility(View.VISIBLE);
			tv_wodejifen1.setVisibility(View.INVISIBLE);
			tv_wodejinbi1.setVisibility(View.INVISIBLE);
			if(fm.findFragmentByTag("one")!=null){
				ft.show(fm.findFragmentByTag("one"));
			}else{
				B4_2_WoDeJiFen_Fragment news=new B4_2_WoDeJiFen_Fragment();
				Bundle bundle = new Bundle();
				bundle.putString("name",name);
				news.setArguments(bundle);
				ft.add(R.id.fl_qianbaofragment, news,"one");
			}
			break;
		case R.id.rb_wodejinbi://点击 --我的金币
			tv_wodejifen.setVisibility(View.INVISIBLE);
			tv_wodejinbi.setVisibility(View.INVISIBLE);
			tv_wodejifen1.setVisibility(View.VISIBLE);
			tv_wodejinbi1.setVisibility(View.VISIBLE);
			if(fm.findFragmentByTag("two")!=null){
				ft.show(fm.findFragmentByTag("two"));
			}else{
				B4_2_WoDeJinBi_Fragment news=new B4_2_WoDeJinBi_Fragment();
				Bundle bundle = new Bundle();
				bundle.putString("name",name);
				news.setArguments(bundle);
				ft.add(R.id.fl_qianbaofragment, news,"two");
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
		ft.commit();
	}

}