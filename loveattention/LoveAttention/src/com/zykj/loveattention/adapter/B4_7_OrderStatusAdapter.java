package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.zykj.loveattention.R;
import com.zykj.loveattention.ui.B1_4_TuanGouActivity;
import com.zykj.loveattention.ui.B4_7_WoDeDingdan;
import com.zykj.loveattention.ui.DaoDianDingDanXiangQing;
import com.zykj.loveattention.ui.WaiSongDingDanXiangQing;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;
import com.zykj.loveattention.view.UIDialog;

public class B4_7_OrderStatusAdapter extends BaseAdapter {
	
	private List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private Activity c;
	private com.alibaba.fastjson.JSONArray array;
	private int status=0;
	private String key;
//  order_state 订单状态（全部订单:10,未付款:20,未消费:30,待评价:40）
    private static final int ALLORDER    = 10;
    private static final int WEIFUKUAN   = 20;
    private static final int WEIXIAOFEI  = 30;
    private static final int DAIPINGJIA  = 40;
    
    private int TAG_H0  = 0;
	
    private String price,pay_sn,store_phone,canBeComment;                                     //订单金额
    private static final String CHANNEL_WECHAT = "wx";//通过微信支付
    private static final String CHANNEL_ALIPAY = "alipay";//通过支付宝支付
    private static final int REQUEST_CODE_PAYMENT = 1;
    
    private B5_5_OrderStatuslistviewAdapter adapter;
	private RequestQueue mRequestQueue;
    

	public B4_7_OrderStatusAdapter(Activity c, List<Map<String, String>> data,int status,String key,int TAG_H0 ) {
		this.c = c;
		this.data = data;
		this.status = status;
		this.key = key;
		this.TAG_H0 = TAG_H0;
		mRequestQueue = Volley.newRequestQueue(c);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data == null ? 0 : data.size();
//		return 3;
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
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.ui_b5_5_orderlist_list_items, null);

            viewHolder.tv_storename = (TextView) convertView.findViewById(R.id.tv_storename);
            viewHolder.tv_fanyong = (TextView) convertView.findViewById(R.id.tv_fanyong);
            viewHolder.btn_operate = (Button) convertView.findViewById(R.id.btn_operate);
            viewHolder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
            viewHolder.tv_gongfu = (TextView) convertView.findViewById(R.id.tv_gongfu);
            viewHolder.listView = (ListView) convertView.findViewById(R.id.listview_goodslist);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//      订单状态（待付款:10,待发货:20,待收货:30,已收货:40）
        switch (status) {
			case ALLORDER:
				viewHolder.btn_operate.setVisibility(View.GONE);
				viewHolder.btn_delete.setVisibility(View.GONE);
				viewHolder.tv_gongfu.setVisibility(View.VISIBLE);
				break;
			case WEIFUKUAN:
//				if (TAG_H0 == 101) {
					viewHolder.btn_operate.setText("去付款");
					viewHolder.btn_delete.setText("删除订单");
					viewHolder.btn_operate.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
//							Toast.makeText(c, "未付款内的去付款", Toast.LENGTH_LONG).show();
						}
					});
					viewHolder.btn_delete.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
//							Toast.makeText(c, "未付款内的删除订单", Toast.LENGTH_LONG).show();	
							DelDingDan(position);
						}
					});
//				}else {
//					viewHolder.btn_operate.setText("确认收货");
//				}
				break;
			case WEIXIAOFEI:
				if (TAG_H0 == 101) {
					viewHolder.btn_operate.setText("退订（联系商家）");
					viewHolder.btn_delete.setVisibility(View.GONE);
				}else {
					viewHolder.btn_operate.setText("退订（联系商家）");
					viewHolder.btn_delete.setText("确认收货");
				}
				viewHolder.btn_operate.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
//						Toast.makeText(c, "未消费内的退订（联系商家）", Toast.LENGTH_LONG).show();
						TuiDing(position);
					}
				});
				viewHolder.btn_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
//						Toast.makeText(c, "确认收货", Toast.LENGTH_LONG).show();
						QueRenShouHuo(position);
					}
				});
				break;
			case DAIPINGJIA:
				viewHolder.btn_operate.setText("去评价");
				viewHolder.btn_delete.setText("删除订单");
				viewHolder.btn_operate.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
//						Toast.makeText(c, "去评价", Toast.LENGTH_LONG).show();
						
					}
				});
				viewHolder.btn_delete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
//						Toast.makeText(c, "已收货的删除订单", Toast.LENGTH_LONG).show();
						DelDingDan(position);
					}
				});
				break;
	
			default:
				break;
		}
//        pay_sn = data.get(position).get("pay_sn").toString();
//        price = data.get(position).get("order_amount").toString();
//        store_phone= data.get(position).get("store_phone").toString();
        viewHolder.tv_storename.setText(data.get(position).get("mname").toString());//店铺名
        viewHolder.tv_gongfu.setText("共付： ￥"+data.get(position).get("price").toString());
//        adapter = new B5_5_OrderStatuslistviewAdapter(c,extend_order_goods);
//        viewHolder.listView.setAdapter(adapter);
        array = JSON.parseArray(data.get(position).get("goodlist"));  
        adapter = new B5_5_OrderStatuslistviewAdapter(c,array,data.get(position).get("datetime"));
        viewHolder.listView.setAdapter(adapter);
        
        //根据商品数的多少来确定评论显示的高度
        B5_5_OrderStatuslistviewAdapter listAdapter = (B5_5_OrderStatuslistviewAdapter) viewHolder.listView.getAdapter();  
        if (listAdapter == null) { 
            return null; 
        } 
        int totalHeight = 0; 
        for (int i = 0; i < listAdapter.getCount(); i++) { 
            View listItem = listAdapter.getView(i, null, viewHolder.listView); 
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight(); 
        } 
        //设置商品显示的的高度
        ViewGroup.LayoutParams params = viewHolder.listView.getLayoutParams(); 
        params.height = totalHeight + (viewHolder.listView.getDividerHeight() * (listAdapter.getCount())); 
        viewHolder.listView.setLayoutParams(params); 
        
        
//        viewHolder.btn_deletetheorder.setOnClickListener(new DeletetheorderListener(position,data.get(position).get("order_id").toString()));
//        viewHolder.btn_paytheorder.setOnClickListener(new PaytheorderListener(position,pay_sn));
        
        //跳转到订单详情
        viewHolder.listView.setOnItemClickListener(new GetOrderDetail(position,data,c));
        
//        viewHolder.btn_tuihuanhuo.setOnClickListener(new TuiHuan(position,data.get(position).get("store_phone").toString()));
//        viewHolder.btn_querenshouhuo.setOnClickListener(new QueRen(position,data.get(position).get("order_id").toString()));
//        viewHolder.btn_tocomment.setOnClickListener(new PingJia(position,extend_order_goods,price,data.get(position).get("order_id").toString()));
//        viewHolder.btn_delete_this.setOnClickListener(new DeleteTheOrder(data.get(position).get("order_id").toString()));
        return convertView;
	}
	/**
	 * 取消订单
	 * @author zyk
	 *
	 */
//	class DeletetheorderListener implements View.OnClickListener {
//		int position;
//	    String orderidString;
//		public DeletetheorderListener(int position,String orderidString) {
//			this.position = position;
//			this.orderidString = orderidString;
//		}
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
////			Tools.Log("key="+key);
//			
//			Tools.Notic(c, "是否取消该订单？", new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					UIDialog.closeDialog();
////					RequestDailog.showDialog(c, "正在取订单，请稍后");
//					switch (status) 
//					{
//					case DAIFUKUAN://待付款中的取消订单
////						HttpUtils.cancelOrder(res_cancelOrder, key, orderidString);
//						break;
//					case DAIFAHUO://待发货中的取消订单
////						HttpUtils.cancelOrder_paid(res_cancelOrder, key, orderidString);
//						break;
//						
//					default:
//						break;
//					}
//					
//				}
//			});
//		}
//
//	}
	/**
	 * 付款
	 * @author zyk
	 */
	class PaytheorderListener implements View.OnClickListener {
		int position;
		String pay_sn;
		public PaytheorderListener(int position,String pay_sn) {
			this.position = position;
			this.pay_sn = pay_sn;
		}
		@Override
		public void onClick(View v) {
			UIDialog.ForThreeBtn(c, new String[] { "微信", "支付宝","取消" }, new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (v.getId()) {
					case R.id.dialog_modif_1:// 微信
						UIDialog.closeDialog();
						Log.e("key=", key);
						Log.e("pay_sn=", pay_sn);
						Log.e("CHANNEL_WECHAT=", CHANNEL_WECHAT);
//						HttpUtils.payTheOrder(res_payTheOrder, key, pay_sn, CHANNEL_WECHAT);
						break;
					case R.id.dialog_modif_2:// 支付宝
						UIDialog.closeDialog();
//						HttpUtils.payTheOrder(res_payTheOrder, key, pay_sn, CHANNEL_ALIPAY);
						break;
					case R.id.dialog_modif_3:// 取消
						UIDialog.closeDialog();
						break;

					default:
						break;
					}
				}
			});
		}
		
	}
	
	/**
	 * 跳转到订单详情
	 * @author lss
	 */
	class GetOrderDetail implements ListView.OnItemClickListener {
		int position;
		List<Map<String, String>> data;
		Activity c;
		int status;
		public GetOrderDetail(int position,List<Map<String, String>> data,Activity c) {
			this.position = position;
			this.data = data;
			this.c = c;
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String statenum = data.get(position).get("state").toString();
			String typenum = data.get(position).get("type").toString();
			if (typenum.equals("0")) {//到店
				if (statenum.equals("0")) {//0-未付款
					Toast.makeText(c, "未付款", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, DaoDianDingDanXiangQing.class);
					jiudian.putExtra("state", "0");
					c.startActivity(jiudian);
				} else if (statenum.equals("1")){//1-未消费 
					Toast.makeText(c, "未消费", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, DaoDianDingDanXiangQing.class);
					jiudian.putExtra("state", "1");
					c.startActivity(jiudian);
				}else if (statenum.equals("2")){//2-已消费
					Toast.makeText(c, "已消费", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, DaoDianDingDanXiangQing.class);
					jiudian.putExtra("state", "2");
					c.startActivity(jiudian);
				}else{
					Toast.makeText(c, "到店特殊情况", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, DaoDianDingDanXiangQing.class);
					jiudian.putExtra("state", "3");
					c.startActivity(jiudian);
				}
			}else{//外送
				if (statenum.equals("0")) {//0-未付款
					Toast.makeText(c, "未付款1", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, WaiSongDingDanXiangQing.class);
					jiudian.putExtra("state", "0");
					c.startActivity(jiudian);
				} else if (statenum.equals("1")){//1-已付款
					Toast.makeText(c, "已付款", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, WaiSongDingDanXiangQing.class);
					jiudian.putExtra("state", "1");
					c.startActivity(jiudian);
				}else if (statenum.equals("2")){//2-已收货
					Toast.makeText(c, "已收货", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, WaiSongDingDanXiangQing.class);
					jiudian.putExtra("state", "2");
					c.startActivity(jiudian);
				}else{
					Toast.makeText(c, "外送特殊情况", Toast.LENGTH_LONG).show();
					Intent jiudian = new Intent(c, WaiSongDingDanXiangQing.class);
					jiudian.putExtra("state", "3");
					c.startActivity(jiudian);
				}
			}
			// TODO Auto-generated method stub
//			Intent intent_to_detail  = new Intent(c,B5_5_OrderDetail.class);
//			intent_to_detail.putExtra("order_id", order_id);
//			intent_to_detail.putExtra("status", status);
//			intent_to_detail.putExtra("pay_sn", pay_sn);
//			intent_to_detail.putExtra("store_phone", store_phone);
//			intent_to_detail.putExtra("canBeComment", canBeComment);
//			intent_to_detail.putExtra("price", price);
//			intent_to_detail.putExtra("extend_order_goods", extend_order_goods.toString());
//			c.startActivity(intent_to_detail);
			
		}
		
	}
	
   /*
	*//**
	 * 确认订单
	 * @author zyk
	 *//*
	class QueRen implements View.OnClickListener {
		int position;
		String order_id;
		public QueRen(int position,String order_id) {
			this.position = position;
			this.order_id = order_id;
		}
		@Override
		public void onClick(View v) {
//			Tools.Log("key="+key);
//			Tools.Log("order_id="+order_id);
			Tools.Notic(c, "是否确认收货", new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
//					HttpUtils.receiveGoods(res_receiveGoods, key, order_id);
				}
			});
		}
		
	}
	*//**
	 * 评价订单
	 * @author zyk
	 *//*
	class PingJia implements View.OnClickListener {
		int position;
		JSONArray extend_order_goods;
		String price;
		String order_id;
		public PingJia(int position,JSONArray extend_order_goods,String price,String order_id) {
			this.position = position;
			this.extend_order_goods = extend_order_goods;
			this.price = price;
			this.order_id = order_id;
		}
		@Override
		public void onClick(View v) {
//			Intent intent_comment = new Intent(c,B5_5_Comment_order.class);
//			intent_comment.putExtra("extend_order_goods",extend_order_goods.toString());
//			intent_comment.putExtra("price",price);
//			intent_comment.putExtra("order_id",order_id);
//			c.startActivity(intent_comment);
		}
		
	}*/

	private static class ViewHolder
    {
        TextView tv_storename;
        TextView tv_fanyong;
        //评价＋确认消费＋付款＋
        Button btn_operate;//取消订单
        ListView listView;
        Button btn_delete;//删除订单
        TextView tv_gongfu;//共支付
    }
	/**
	 * 付款
	 */
	JsonHttpResponseHandler res_payTheOrder = new JsonHttpResponseHandler()
	{

		@Override
		public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Log.e("付款", response+"");
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = datas.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (error.equals(null))//成功
			{
//				Intent intent = new Intent();
//	            String packageName = c.getPackageName();
//	            ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
//	            intent.setComponent(componentName);
//	            intent.putExtra(PaymentActivity.EXTRA_CHARGE, response.toString());
//	            c.startActivityForResult(intent, REQUEST_CODE_PAYMENT);
			}
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
		
		
	};
	
	/**
	 * 删除订单
	 */
	public void DelDingDan(final int position){
		final String orderid = data.get(position).get("id").toString();
		new AlertDialog.Builder(c).setTitle("确认")
		.setMessage("确认删除么？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				ShanChuDingDan(orderid,position);
			}
		})
		.setNeutralButton("取消", null).show();	
	}

	/**
	 * 删除订单
	 */
	public void ShanChuDingDan(String orderid,final int position){
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderid", orderid);
		String json = JsonUtils.toJson(map);
		json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_orderdel(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								Toast.makeText(c,"订单删除成功", Toast.LENGTH_LONG).show();
								data.remove(position);
								B4_7_OrderStatusAdapter.this.notifyDataSetChanged();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(c,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(c, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}

	/**
	 * 退订（联系商家）
	 */
	public void TuiDing(final int position){
		final String mtelephone = data.get(position).get("mtelephone").toString();
		new AlertDialog.Builder(c).setTitle("提示")
		.setMessage("确认要联系商家？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + mtelephone);
				intent.setData(data);
				c.startActivity(intent);
			}
		})
		.setNeutralButton("取消", null).show();	
	}

	/**
	 * 确认收货
	 */
	public void QueRenShouHuo(final int position){
		final String orderid = data.get(position).get("id").toString();
		new AlertDialog.Builder(c).setTitle("提示")
		.setMessage("确认收货？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				QueRenShouHuo(orderid,position);
			}
		})
		.setNeutralButton("取消", null).show();	
	}

	/**
	 * 确认订单
	 */
	public void QueRenShouHuo(String orderid,final int position){
		Map<String, String> map = new HashMap<String, String>();
		map.put("orderid", orderid);
		String json = JsonUtils.toJson(map);
		json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,
				HttpUtils.url_confirmReceive(json), null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						JSONObject status;
						try {
							status = response.getJSONObject("status");
							String succeed = status.getString("succeed");
							if (succeed.equals("1")) // 成功
							{
								Toast.makeText(c,"订单已收货", Toast.LENGTH_LONG).show();
								data.remove(position);
								B4_7_OrderStatusAdapter.this.notifyDataSetChanged();
							} else {// 失败,提示失败信息
								String errdesc = status.getString("errdesc");
								Toast.makeText(c,errdesc, Toast.LENGTH_LONG).show();
							}
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Tools.Log("ErrorResponse=" + error.getMessage());
						Toast.makeText(c, "网络连接失败，请重试",
								Toast.LENGTH_LONG).show();
					}
				});
		mRequestQueue.add(jr);
	}
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        //支付页面返回处理
//        if (requestCode == REQUEST_CODE_PAYMENT) {
//            if (resultCode == Activity.RESULT_OK) {
//                String result = data.getExtras().getString("pay_result");
//                /* 处理返回值
//                 * "success" - payment succeed
//                 * "fail"    - payment failed
//                 * "cancel"  - user canceld
//                 * "invalid" - payment plugin not installed
//                 */
////                Tools.Log("支付结果="+result);
////                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
////                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
////                showMsg(result, errorMsg, extraMsg);
//                if (result.equals("success")) {
//					Tools.Notic(c, "您已经付款成功", new OnClickListener() {
//						
//						@Override
//						public void onClick(View arg0) {
//							// TODO Auto-generated method stub
//							Intent intent_toMy = new Intent(c,B5_MyActivity.class);
//							c.startActivity(intent_toMy);
//							c.finish();
//						}
//					});
//				}else if (result.equals("fail")) {
//					Tools.Notic(c, "支付失败，请重试", null);
//				}else if (result.equals("cancel")) {
//					Tools.Notic(c, "支付取消", null);
//				}else if (result.equals("invalid")) {
//					Tools.Notic(c, "支付失败，请重新支付", null);
//					
//				}
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//            	Tools.Notic(c, "支付取消", null);
//            }
//        }
//    }
}
