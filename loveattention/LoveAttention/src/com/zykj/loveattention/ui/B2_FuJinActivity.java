package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.R.color;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B2_FuJin_Adapter;
import com.zykj.loveattention.adapter.B2_TextSizeAdapter;
import com.zykj.loveattention.adapter.B2_and_B3_Adapter;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss 2015年8月8日	附近－
 *
 */
public class B2_FuJinActivity extends BaseActivity {
	
	public ImageButton map_btn;// 地图按钮
	public ImageButton search_it;// 搜索按钮
	public LinearLayout ll_all;//全部商家
	public LinearLayout ll_some;//返现商家
	public View line1;//下划线－全部商家
	public View line2;//下划线－返现商家
	public TextView tv_shanghuleixing,tv_quancheng,tv_zhinengpaixu,tv_shaixuan;//商户类型，全城，智能排序，筛选
	public ImageView b2_shanghuleixing,b2_quancheng,b2_zhinengpaixu,b2_shaixuan;//底部黄条
	public ImageView arrowdown1,arrowdown2,arrowdown3,arrowdown4;//向下的指示尖头
	public ListView list_fujin;//展示列表
	private B2_FuJin_Adapter fujinAdapter;//适配器
	private RequestQueue mRequestQueue;
	private String json;
	private List<Map<String, String>> fujindata = new ArrayList<Map<String, String>>();
	private String lng="",lat="";
	private List<HashMap<String, String>> zhinengpaixu;//智能排序
	private List<HashMap<String, String>> shaixuan;//筛选
	private List<HashMap<String, String>> shopClass;//商户类型
	private B2_TextSizeAdapter b2tsa;
    private ListView pList;
    private PopupWindow popupWindow;
	private int pagesize = 5;//每页数量
	private int pagenumber = 1;//当前页
	private String districtid = "0";//地区ID
	private String categoryid = "0";//分类id
	private int orderType = 0;//智能排序  1.人气 2.口碑 3.离我最近 4.人均最高 5.人均最低 
	private int searchType = 0;//筛选  1.卡卷 2.免预约 3.节假日可用
	private String isRebate = "1";//返佣商家 1.全部 2.返佣商家 
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_b2_fujin);
		mRequestQueue = Volley.newRequestQueue(this);
		initUI();
        initPop();
		setListener(map_btn,search_it,ll_all,ll_some,tv_shanghuleixing,tv_quancheng,tv_zhinengpaixu,tv_shaixuan);//绑定点击事件
	}
	
	/**
	 * 绑定组件
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		map_btn = (ImageButton) findViewById(R.id.map_btn);
		search_it = (ImageButton) findViewById(R.id.search_it);
		ll_all = (LinearLayout) findViewById(R.id.ll_all);
		ll_some = (LinearLayout) findViewById(R.id.ll_some);
		line1 = (View) findViewById(R.id.line1);
		line2 = (View) findViewById(R.id.line2);
		arrowdown1  = (ImageView) findViewById(R.id.arrowdown1);
		arrowdown2  = (ImageView) findViewById(R.id.arrowdown2);
		arrowdown3  = (ImageView) findViewById(R.id.arrowdown3);
		arrowdown4  = (ImageView) findViewById(R.id.arrowdown4);
		b2_shanghuleixing  = (ImageView) findViewById(R.id.b2_shanghuleixing);
		b2_quancheng  = (ImageView) findViewById(R.id.b2_quancheng);
		b2_zhinengpaixu  = (ImageView) findViewById(R.id.b2_zhinengpaixu);
		b2_shaixuan  = (ImageView) findViewById(R.id.b2_shaixuan);
		tv_shanghuleixing = (TextView) findViewById(R.id.tv_shanghuleixing);
		tv_quancheng = (TextView) findViewById(R.id.tv_quancheng);
		tv_zhinengpaixu = (TextView) findViewById(R.id.tv_zhinengpaixu);
		tv_shaixuan = (TextView) findViewById(R.id.tv_shaixuan);
		list_fujin = (ListView) findViewById(R.id.list_fujin);
	}

    /**
     * 管理店铺(分类、排序)
     */
    private void initPop() {
//    	LeiXing();
        View view=LayoutInflater.from(B2_FuJinActivity.this).inflate(R.layout.popu_layout,null);
        pList = (ListView) view.findViewById(R.id.lv_content);
        if(popupWindow == null){
            popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            ColorDrawable cd = new ColorDrawable(-0000);
            popupWindow.setBackgroundDrawable(cd);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
		requestData();
    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.map_btn:
//			Toast.makeText(this, "map", Toast.LENGTH_LONG).show();
			Intent mapIntent = new Intent(this,B2_Map0fFaxian.class);
			startActivity(mapIntent);
			break;
		case R.id.search_it:
			Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
			break;
		case R.id.ll_all:
			line1.setVisibility(View.VISIBLE);
			line2.setVisibility(View.INVISIBLE);
			isRebate="1";
			requestData();
			break;
		case R.id.ll_some:
			line1.setVisibility(View.INVISIBLE);
			line2.setVisibility(View.VISIBLE);
			isRebate="2";
			requestData();
			break;
		case R.id.tv_shanghuleixing://商户类型
			tv_shanghuleixing.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdownyellow);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.VISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);
			
			//商户类型
			/*b2tsa = new B4_TextSizeAdapter(B2_FuJinActivity.this, shopClass);
			pList.setAdapter(b2tsa);
            pList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
					key = 0;order = 0;curpage = 1;keyword = "";
	                if(popupWindow.isShowing()){
	                    popupWindow.dismiss();
	                }
	                sc_id = shopClass.get(position).get("sc_id");
	    			requestData();
				}
			});
            popupWindow.showAsDropDown(v);
			*/
			break;
		case R.id.tv_quancheng:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdownyellow);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.VISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);
			break;
		case R.id.tv_zhinengpaixu:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(this.getResources().getColor(R.color.yellow));
			tv_shaixuan.setTextColor(android.graphics.Color.BLACK);
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdownyellow);
			arrowdown4.setImageResource(R.drawable.arrowdowngrey);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.VISIBLE);
			b2_shaixuan.setVisibility(View.INVISIBLE);

            //智能排序
			zhinengpaixu = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < 6; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				if (i==0) {
					map.put("sc_name", "全部");
				}else if (i==1) {
					map.put("sc_name", "人气最高");
				}else if (i==2) {
					map.put("sc_name", "口碑最好");
				}else if (i==3) {
					map.put("sc_name", "离我最近");
				}else if (i==4) {
					map.put("sc_name", "人均最高");
				}else if (i==5) {
					map.put("sc_name", "人均最低");
				}
				zhinengpaixu.add(map);
			}
			b2tsa = new B2_TextSizeAdapter(B2_FuJinActivity.this, zhinengpaixu);
			pList.setAdapter(b2tsa);
            pList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					orderType = position;
	                if(popupWindow.isShowing()){
	                    popupWindow.dismiss();
	                }
	    			requestData();
				}
			});
            popupWindow.showAsDropDown(v);
			break;
		case R.id.tv_shaixuan:
			tv_shanghuleixing.setTextColor(android.graphics.Color.BLACK);
			tv_quancheng.setTextColor(android.graphics.Color.BLACK);
			tv_zhinengpaixu.setTextColor(android.graphics.Color.BLACK);
			tv_shaixuan.setTextColor(this.getResources().getColor(R.color.yellow));
			arrowdown1.setImageResource(R.drawable.arrowdowngrey);
			arrowdown2.setImageResource(R.drawable.arrowdowngrey);
			arrowdown3.setImageResource(R.drawable.arrowdowngrey);
			arrowdown4.setImageResource(R.drawable.arrowdownyellow);
			b2_shanghuleixing.setVisibility(View.INVISIBLE);
			b2_quancheng.setVisibility(View.INVISIBLE);
			b2_zhinengpaixu.setVisibility(View.INVISIBLE);
			b2_shaixuan.setVisibility(View.VISIBLE);

            //筛选
			shaixuan = new ArrayList<HashMap<String,String>>();
			for(int i = 0; i < 4; i++){
				HashMap<String, String> map = new HashMap<String, String>();
				if (i==0) {
					map.put("sc_name", "全部");
				}else if (i==1) {
					map.put("sc_name", "卡卷");
				}else if (i==2) {
					map.put("sc_name", "免预约");
				}else if (i==3) {
					map.put("sc_name", "节假日可用");
				}
				shaixuan.add(map);
			}
			b2tsa = new B2_TextSizeAdapter(B2_FuJinActivity.this, shaixuan);
			pList.setAdapter(b2tsa);
            pList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					searchType = position;
	                if(popupWindow.isShowing()){
	                    popupWindow.dismiss();
	                }
	    			requestData();
				}
			});
            popupWindow.showAsDropDown(v);
			break;
		default:
			break;
		}
	}


	/**
	 * 请求服务器数据----店铺列表
	 */
	private void requestData(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("pagenumber", String.valueOf(pagenumber));
		map.put("pagesize", String.valueOf(pagesize));
		map.put("districtid", districtid);
		map.put("categoryid", categoryid);
		map.put("longitude", "2000");
		map.put("latitude", "3000");
		map.put("distance", "1");
		map.put("orderType", String.valueOf(orderType));
		map.put("searchType", String.valueOf(searchType));
		map.put("isRebate", isRebate);
		json = JsonUtils.toJson(map);

		
		// 发现中活动
		HuoDong();
		fujinAdapter = new B2_FuJin_Adapter(this,fujindata);
		list_fujin.setAdapter(fujinAdapter);
//		RequestDailog.showDialog(this, "正在加载数据，请稍后");
//		HttpUtils.getStoreList(curpage == 1?res_getStoresList:res_getMoreStoreList, HttpUtils.iterateParams(params));
	}
	
	//发现中活动
		public void HuoDong(){
			JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_fujin(json), null,
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
									fujindata.clear();
									JSONObject jobs = response.getJSONObject("data");
									org.json.JSONArray array1 = jobs.getJSONArray("resultlist");
									for (int i = 0; i < array1.length(); i++) {
										JSONObject jsonItem1 = array1.getJSONObject(i);
										Map<String, String> map1 = new HashMap<String, String>();
										map1.put("distance", jsonItem1.getString("distance"));
										map1.put("imgpath", jsonItem1.getString("imgpath"));
										map1.put("perperson", jsonItem1.getString("perperson"));
										map1.put("remark", jsonItem1.getString("remark"));
										map1.put("stars", jsonItem1.getString("stars"));
										map1.put("name", jsonItem1.getString("name"));
										map1.put("merchantid", jsonItem1.getString("merchantid"));	
										fujindata.add(map1);
									}
									fujinAdapter = new B2_FuJin_Adapter(B2_FuJinActivity.this,fujindata);
									list_fujin.setAdapter(fujinAdapter);
									
								} else {// 失败,提示失败信息
									String errdesc = status.getString("errdesc");
									Toast.makeText(B2_FuJinActivity.this,errdesc, Toast.LENGTH_LONG).show();
								}
							} catch (org.json.JSONException e) {
								e.printStackTrace();
							}

						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							RequestDailog.closeDialog();
							Tools.Log("ErrorResponse=" + error.getMessage());
							Toast.makeText(B2_FuJinActivity.this, "网络连接失败，请重试",
									Toast.LENGTH_LONG).show();
						}
					});
			mRequestQueue.add(jr);
		}
		
		//商户类型
			public void LeiXing(){
				JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_leixingfenlei(), null,
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
										fujindata.clear();
										JSONObject jobs = response.getJSONObject("data");
										org.json.JSONArray array1 = jobs.getJSONArray("resultlist");
										for (int i = 0; i < array1.length(); i++) {
											JSONObject jsonItem1 = array1.getJSONObject(i);
											Map<String, String> map1 = new HashMap<String, String>();
											map1.put("distance", jsonItem1.getString("distance"));
											map1.put("imgpath", jsonItem1.getString("imgpath"));
											map1.put("perperson", jsonItem1.getString("perperson"));
											map1.put("remark", jsonItem1.getString("remark"));
											map1.put("stars", jsonItem1.getString("stars"));
											map1.put("name", jsonItem1.getString("name"));
											map1.put("merchantid", jsonItem1.getString("merchantid"));	
											fujindata.add(map1);
										}
										fujinAdapter = new B2_FuJin_Adapter(B2_FuJinActivity.this,fujindata);
										list_fujin.setAdapter(fujinAdapter);
										
									} else {// 失败,提示失败信息
										String errdesc = status.getString("errdesc");
										Toast.makeText(B2_FuJinActivity.this,errdesc, Toast.LENGTH_LONG).show();
									}
								} catch (org.json.JSONException e) {
									e.printStackTrace();
								}

							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								RequestDailog.closeDialog();
								Tools.Log("ErrorResponse=" + error.getMessage());
								Toast.makeText(B2_FuJinActivity.this, "网络连接失败，请重试",
										Toast.LENGTH_LONG).show();
							}
						});
				mRequestQueue.add(jr);
			}
	
}
