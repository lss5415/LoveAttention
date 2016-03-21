package com.zykj.loveattention.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
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
import com.zykj.loveattention.adapter.B3_ShpppingCartAdapter;
import com.zykj.loveattention.adapter.B3_ShpppingCartAdapter.RefreshExpandableList;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.ChildrenItem;
import com.zykj.loveattention.data.GroupItem;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

/**
 * @author lss
 *
 */
public class B4_11_ShoppingCartActivity extends BaseActivity implements RefreshExpandableList{
	//标题
// 	private TextView tv_sp_title;
	//购物车list
	private ExpandableListView expandableList;
	//购物车数据
	private B3_ShpppingCartAdapter adapter;
	private List<GroupItem> dataList = new ArrayList<GroupItem>();
	private int groupCount;
	private TextView tv_jiesuan;//结算（0）
	private TextView tv_sumgoods;//总价
	private ImageView im_checkall;//全选
	private TextView d1_shanchu;//删除
	int ischeck=0;//1是全选，0是取消全选
	String fhgoodsum;
	int sumtiaoshu = 0;
	private RequestQueue mRequestQueue;
	private ImageView im_gouwuche;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_b3_shoppingcart);
		mRequestQueue = Volley.newRequestQueue(this);
		initView();
	}
	
	private void initView(){
		/*tv_sp_title = (TextView)findViewById(R.id.tv_sp_title);
		*/
		expandableList = (ExpandableListView)findViewById(R.id.list_shoppingcar);//购物列表
		expandableList.setGroupIndicator(null);
		tv_jiesuan = (TextView)findViewById(R.id.tv_jiesuan);//结算
		tv_sumgoods = (TextView)findViewById(R.id.tv_sumgoods);//总价
		im_checkall = (ImageView)findViewById(R.id.im_checkall);//全选
		im_gouwuche = (ImageView)findViewById(R.id.im_gouwuche);//返回
		d1_shanchu = (TextView)findViewById(R.id.d1_shanchu);//删除
		
		setListener(tv_jiesuan,im_checkall,im_gouwuche,d1_shanchu);
//		HttpUtils.getShoppingCarInfoList(res_ShoppingCarInfo,getSharedPreferenceValue("key"));
		ShoppingcartInfo();
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
	
		switch (v.getId()) {
		case R.id.im_gouwuche:
			this.finish();
			break;
		case R.id.tv_jiesuan:
			/*结算*/
			String a = tv_jiesuan.getText().toString();
			if (a.equals("结算（0）")) {
				Toast.makeText(getApplicationContext(), "您还没有选择商品哦！", Toast.LENGTH_LONG).show();
			}else {
				String allcheckinfo = "";
				for (int i = 0; i < dataList.size(); i++) {
					List<ChildrenItem> childrenList = dataList.get(i).getGoodList();
					for (int j = 0; j < childrenList.size(); j++) {
						ChildrenItem childrenItem = childrenList.get(j);
						if(childrenItem.isChecked()){
							allcheckinfo = allcheckinfo+(childrenItem.getSid()+"|"+childrenItem.getQuantity()+",");
						}
					}
				}
				allcheckinfo=allcheckinfo.substring(0, allcheckinfo.length()-1);
				Intent itmrhd = new Intent();
				itmrhd.setClass(B4_11_ShoppingCartActivity.this, QueRenDingDanActivity.class);
				itmrhd.putExtra("allpri", tv_sumgoods.getText().toString());
				itmrhd.putExtra("allcheckinfo", allcheckinfo);
				startActivity(itmrhd);
			}
//			showCheckedItems();
			break;
		case R.id.im_checkall:
			if (im_checkall.isSelected()) {
				im_checkall.setSelected(false);
				tv_jiesuan.setText("结算（0）");
				tv_sumgoods.setText("0.00");
				for (int i = 0; i < dataList.size(); i++) {
					dataList.get(i).setChecked(false);
					List<ChildrenItem> childrenList = dataList.get(i).getGoodList();
					for (int j = 0; j < childrenList.size(); j++) {
						childrenList.get(j).setChecked(false);
					}
				}
				adapter.notifyDataSetChanged();
			}else {
				im_checkall.setSelected(true);
				float allprice = 0f;sumtiaoshu = 0;
				for (int i = 0; i < dataList.size(); i++) {
					dataList.get(i).setChecked(true);
					List<ChildrenItem> childrenList = dataList.get(i).getGoodList();
					for (int j = 0; j < childrenList.size(); j++) {
						childrenList.get(j).setChecked(true);
						ChildrenItem childrenItem = childrenList.get(j);
						sumtiaoshu += 1;
						allprice += Float.valueOf(childrenItem.getPrice())*Integer.valueOf(childrenItem.getQuantity());
					}
				}
				tv_jiesuan.setText("结算（"+sumtiaoshu+"）");
				tv_sumgoods.setText(allprice+"");
				adapter.notifyDataSetChanged();
			}
			break;
		case R.id.d1_shanchu:
			/*删除*/
			String delnum = tv_jiesuan.getText().toString();
			if (delnum.equals("结算（0）")) {
				Toast.makeText(getApplicationContext(), "您还没有选择商品哦！", Toast.LENGTH_LONG).show();
			}else {
				String delinfo = "";
				for (int i = 0; i < dataList.size(); i++) {
					List<ChildrenItem> childrenList = dataList.get(i).getGoodList();
					for (int j = 0; j < childrenList.size(); j++) {
						ChildrenItem childrenItem = childrenList.get(j);
						if(childrenItem.isChecked()){
							delinfo = delinfo+(childrenItem.getSid()+",");
						}
					}
				}
				delinfo=delinfo.substring(0, delinfo.length()-1);
				DelCart(delinfo);
			}
			break;
		default:
			
			break;
		}

	}

	/**
	 * 初始化Adapter数据
	 * 
	 * @Title:
	 * @Description:
	 * @return void 返回类型
	 * @author lss
	 * @throws
	 */
	private void initData() {
		for (int i = 0; i < dataList.size(); i++) {
			sumtiaoshu =  sumtiaoshu + dataList.get(i).getGoodList().size();//商品总条数
		}
//		Toast.makeText(getApplicationContext(), ""+sumtiaoshu, Toast.LENGTH_LONG).show();
		adapter = new B3_ShpppingCartAdapter(this, dataList,ischeck,sumtiaoshu,this);
		expandableList.setAdapter(adapter);

		groupCount = expandableList.getCount();

		for (int i = 0; i < groupCount; i++) {

			expandableList.expandGroup(i);

		}
//		List<String> checkedChildren = adapter.getCheckedChildren();
//		Toast.makeText(getApplicationContext(), "结算("+String.valueOf(checkedChildren.size()), Toast.LENGTH_LONG).show();
		/*List<ChildrenItem> list1 = new ArrayList<ChildrenItem>();
		list1.add(new ChildrenItem("1子id", "1子name","1"));
		list1.add(new ChildrenItem("2子id", "2子name","1"));
		list1.add(new ChildrenItem("3子id", "3子name","1"));

		GroupItem groupItem1 = new GroupItem("1组id", "1组name", list1);
		dataList.add(groupItem1);

		List<ChildrenItem> list2 = new ArrayList<ChildrenItem>();
		list2.add(new ChildrenItem("4子id", "4子name","1"));
		list2.add(new ChildrenItem("5子id", "5子name","1"));

		GroupItem groupItem2 = new GroupItem("2组id", "2组name", list2);
		dataList.add(groupItem2);

		List<ChildrenItem> list3 = new ArrayList<ChildrenItem>();
		list3.add(new ChildrenItem("6子id", "6子name","1"));
		list3.add(new ChildrenItem("7子id", "7子name","1"));
		list3.add(new ChildrenItem("8子id", "8子name","1"));

		GroupItem groupItem3 = new GroupItem("3组id", "3组name", list3);
		dataList.add(groupItem3);*/
	}
	
	/**
	 * 获取购物车详细列表
	 */
	public void ShoppingcartInfo(){

//		HttpUtils.url_myShoppingcart(res_ShoppingCarInfo, key);
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberid", getSharedPreferenceValue("id"));
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_myShoppingcart(json), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						JSONArray datas=null;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								datas = response.getJSONArray("data");
								dataList = com.alibaba.fastjson.JSONArray.parseArray(datas.toString(), GroupItem.class);
								initData();
								adapter.notifyDataSetChanged();
								
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
//								Toast.makeText(B1_ShouYeActivity.this,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				},new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(B4_11_ShoppingCartActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				}
				);
		mRequestQueue.add(jr);
		
	}

	
	/**
	 * 删除购物车
	 */
	public void DelCart(String delinfo){

//		HttpUtils.url_myShoppingcart(res_ShoppingCarInfo, key);
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		Map<String, String> map = new HashMap<String, String>();
		map.put("shoppingid", delinfo);
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.shoppingCartDel(json), null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						RequestDailog.closeDialog();
						JSONObject status;
						JSONArray datas=null;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								Toast.makeText(B4_11_ShoppingCartActivity.this, "批量删除成功", Toast.LENGTH_LONG).show();
								tv_jiesuan.setText("结算（0）");
								tv_sumgoods.setText("0.00");
								ShoppingcartInfo();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				},new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						RequestDailog.closeDialog();
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(B4_11_ShoppingCartActivity.this, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				}
				);
		mRequestQueue.add(jr);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
//		HttpUtils.getShoppingCarInfoList(res_ShoppingCarInfo, key);
//		im_checkall.setSelected(false);
//		tv_jiesuan.setText("结算（0）");
//		tv_sumgoods.setText("0.00");
	};

	@Override
	public void refreshShopCarDate(float totalprice, boolean allcheck, int count) {
		tv_sumgoods.setText(Float.toString(totalprice));
		tv_jiesuan.setText("结算（"+count+"）");
		if (allcheck) {
			ischeck=1;
			im_checkall.setSelected(true);
		}else {
			ischeck=0;
			im_checkall.setSelected(false);
		}
	}
}