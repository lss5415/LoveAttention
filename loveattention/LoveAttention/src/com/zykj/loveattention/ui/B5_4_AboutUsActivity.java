package com.zykj.loveattention.ui;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.R.id;
import com.zykj.loveattention.R.layout;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B5_4_AboutUsActivity extends BaseActivity {

	private ImageView img_back,img_head;
	private TextView txt;
	private RequestQueue mRequestQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b5_4__about_us);
		
		img_back = (ImageView) findViewById(R.id.im_b131_back_btn);
		img_head = (ImageView) findViewById(R.id.im_b54_img);
		txt = (TextView) findViewById(R.id.tv_b54_txt);
		mRequestQueue = Volley.newRequestQueue(this);
		
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				B5_4_AboutUsActivity.this.finish();
			}
		});
		
		JsonObjectRequest jr1 = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_about(), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								JSONObject jsonobj = response.getJSONObject("data");
								String imgpath = jsonobj.getString("imgpath");
								String content = jsonobj.getString("content");
								ImageLoader.getInstance().displayImage(AppValue.ImgUrl+imgpath, img_head);
								txt.setText(content);
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(B5_4_AboutUsActivity.this,errdesc, Toast.LENGTH_LONG).show();
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
						Toast.makeText(B5_4_AboutUsActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr1);
	}

	

}
