package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.ui.B4_10_2_1_XinZengDiZhiActivity;
import com.zykj.loveattention.ui.B4_10_2_ShouHuoDiZhiActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class AdressAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private RequestQueue mRequestQueue;
	private String memberid;

	public AdressAdapter(Context context, List<Map<String, String>> data,String memberid) {
		this.context = context;
		this.data = data;
		this.memberid = memberid;
		mRequestQueue = Volley.newRequestQueue(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder ViewHolder = null;
		if (convertView == null) {
			ViewHolder = new ViewHolder();
			convertView = LinearLayout.inflate(context, R.layout.ui_dizhiitem,
					null);
			ViewHolder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			ViewHolder.tv_phone = (TextView) convertView
					.findViewById(R.id.tv_phone);
			ViewHolder.tv_dizhi = (TextView) convertView
					.findViewById(R.id.tv_dizhi);
			ViewHolder.tv_bianji = (TextView) convertView
					.findViewById(R.id.tv_bianji);
			ViewHolder.tv_shanchu = (TextView) convertView
					.findViewById(R.id.tv_shanchu);
			convertView.setTag(ViewHolder);
		} else {
			ViewHolder = (ViewHolder) convertView.getTag();
		}
		ViewHolder.tv_name.setText(data.get(position).get("linkname"));
		ViewHolder.tv_phone.setText(data.get(position).get("linkmobile"));
		ViewHolder.tv_dizhi.setText(data.get(position).get("linkprovince")+data.get(position).get("linkcity")+data.get(position).get("linkcounty")+data.get(position).get("linkaddress"));
		ViewHolder.tv_bianji.setId(position);
		ViewHolder.tv_bianji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent itzhxx = new Intent();
				itzhxx.setClass(context, B4_10_2_1_XinZengDiZhiActivity.class);
				itzhxx.putExtra("name", data.get(arg0.getId()).get("linkname"));
				itzhxx.putExtra("dianhua",  data.get(arg0.getId()).get("linkmobile"));
				itzhxx.putExtra("sheng",  data.get(arg0.getId()).get("linkprovince"));
				itzhxx.putExtra("shi",  data.get(arg0.getId()).get("linkcity"));
				itzhxx.putExtra("qu",  data.get(arg0.getId()).get("linkcounty"));
				itzhxx.putExtra("xiangxi",  data.get(arg0.getId()).get("linkaddress"));
				itzhxx.putExtra("addressid",  data.get(arg0.getId()).get("addressid"));
				itzhxx.putExtra("state", "1");
				context.startActivity(itzhxx);
			}
		});
		ViewHolder.tv_shanchu.setId(position);
		ViewHolder.tv_shanchu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String a = data.get(arg0.getId()).get("addressid");
				Map<String , String > map = new HashMap<String, String>();
				map.put("memberid", memberid);
				map.put("addressid", a);
				String json = JsonUtils.toJson(map);
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_addressinfoDel(json),null,new Response.Listener<JSONObject>() {  
		            @Override  
		            public void onResponse(JSONObject response) {  
			            	RequestDailog.closeDialog();
							try {
								JSONObject status = response.getJSONObject("status");
								String succeed = status.getString("succeed");
								if (succeed.equals("1")) //成功
								{
									Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
								}else {//失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(context, errdesc, Toast.LENGTH_LONG).show();
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
		                Toast.makeText(context, "网络连接失败，请重试", Toast.LENGTH_LONG).show();
		            }  
		        });  
		        mRequestQueue.add(jr); 
			}
		});
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_name;
		public TextView tv_phone;
		public TextView tv_dizhi;
		public TextView tv_bianji;
		public TextView tv_shanchu;
		
	}
}