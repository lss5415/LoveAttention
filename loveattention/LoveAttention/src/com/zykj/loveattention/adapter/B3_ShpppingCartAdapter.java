package com.zykj.loveattention.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.ChildrenItem;
import com.zykj.loveattention.data.GroupItem;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.HttpUtils;
import com.zykj.loveattention.utils.JsonUtils;
import com.zykj.loveattention.utils.Tools;
import com.zykj.loveattention.view.RequestDailog;

public class B3_ShpppingCartAdapter extends BaseExpandableListAdapter{
	private List<GroupItem> dataList;
	private LayoutInflater inflater;
	Activity activity;
	Context context;
	int ischecked;
	float qwe = 0;
	int childcheck=0;
	private float allprice=0;//总价格
	private boolean allcheckstate;//全选状态
	private int sumtiaoshu;//结算数
	private JsonHttpResponseHandler res_delete;
	private JsonHttpResponseHandler res_add;
	private RefreshExpandableList refresh;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private RequestQueue mRequestQueue;

	public B3_ShpppingCartAdapter(Context context, List<GroupItem> dataList,int ischecked,int sumtiaoshu,RefreshExpandableList refresh) {
		this.dataList = dataList;//购物车列表
		this.context = context;
		this.ischecked =ischecked;
		this.sumtiaoshu = sumtiaoshu;
		this.refresh = refresh;
		inflater = LayoutInflater.from(context);
		mRequestQueue = Volley.newRequestQueue(context);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		/*获取子对象*/
		final GroupItem groupItem = dataList.get(groupPosition);
		if (groupItem == null || groupItem.getGoodList() == null
				|| groupItem.getGoodList().isEmpty()) {
			return null;
		}
		return groupItem.getGoodList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition,boolean isLastChild, View convertView, final ViewGroup parent) {
		final GroupItem groupItem = dataList.get(groupPosition);//购物车中的商铺
		final ChildrenItem childrenItem = groupItem.getGoodList().get(childPosition);//购物车商铺中的产品

		final ChildViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.ui_b3_shoppingcartitem,null);
			viewHolder.childrenNameTV = (TextView) convertView.findViewById(R.id.children_name);
			viewHolder.im_shangpuimg = (ImageView) convertView.findViewById(R.id.im_shangpuimg);
			viewHolder.tv_spec = (TextView)convertView.findViewById(R.id.tv_spec);
			viewHolder.tv_goods_price = (TextView)convertView.findViewById(R.id.tv_goods_price);
			viewHolder.tv_old_price = (TextView)convertView.findViewById(R.id.tv_old_price);
			viewHolder.tv_goods_num = (TextView)convertView.findViewById(R.id.tv_goods_num);
			viewHolder.editconunt = (TextView) convertView.findViewById(R.id.editconunt);
			viewHolder.jiabt = (ImageView)convertView.findViewById(R.id.jiabt);//加商品
			viewHolder.jianbt = (ImageView)convertView.findViewById(R.id.jianbt);//减商品
			viewHolder.ll_goodtiaozhuan = (LinearLayout)convertView.findViewById(R.id.ll_goodtiaozhuan);//编辑
			viewHolder.childrenCB = (CheckBox) convertView.findViewById(R.id.children_cb);//选择
//			viewHolder.tv_del = (TextView)convertView.findViewById(R.id.tv_del);//删除
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}
		viewHolder.childrenNameTV.setText(childrenItem.getGoodsName());
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+childrenItem.getImgpath(), viewHolder.im_shangpuimg);
//		ImageLoader.getInstance().displayImage(childrenItem.getGoods_image_url(), viewHolder.im_shangpuimg, ImageOptions.getOpstion(), animateFirstListener);
		viewHolder.tv_spec.setText(childrenItem.getGoodspecif());
		viewHolder.tv_goods_price.setText("￥"+childrenItem.getPrice());
		try {
			viewHolder.tv_old_price.setText("￥"+childrenItem.getOldprice());
		} catch (Exception e) {
			viewHolder.tv_old_price.setText("￥0.00");
		}
		viewHolder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		viewHolder.tv_goods_num.setText("x"+childrenItem.getQuantity());
		viewHolder.editconunt.setText(childrenItem.getQuantity());
		viewHolder.childrenCB.setChecked(childrenItem.isChecked());//选中
		viewHolder.jiabt.setOnClickListener(new MyOnClickListener(groupItem,childrenItem));//加商品
		viewHolder.jianbt.setOnClickListener(new MyOnClickListener(groupItem,childrenItem));//减商品
		viewHolder.childrenCB.setOnClickListener(new MyOnClickListener(groupItem,childrenItem));
//		viewHolder.tv_del.setOnClickListener(new MyOnClickListener(groupItem,childrenItem));
		viewHolder.ll_goodtiaozhuan.setOnClickListener(new MyOnClickListener(groupItem,childrenItem));
		return convertView;
	}

	/**
	 * 添加购物车商品
	 */
	private void AddCarNum(String number,String sid,final ChildrenItem childrenItem){
		Map<String , String > map = new HashMap<String, String>();
		map.put("shoppingid", sid);
		map.put("number", number);
		String json = JsonUtils.toJson(map);
		JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,HttpUtils.url_shoppingCartAddOrSub(json),null,new Response.Listener<JSONObject>() {  
            @Override  
            public void onResponse(JSONObject response) {  
	            	RequestDailog.closeDialog();
					try {
						JSONObject status = response.getJSONObject("status");
						String succeed = status.getString("succeed");
						if (succeed.equals("1")) //成功
						{
							int quantity = response.getJSONObject("data").getInt("quantity");
							if(childrenItem.isChecked()){
								childrenItem.setQuantity(quantity+"");
								finalTotalPrice();//结算总数据
							}else{
								childrenItem.setQuantity(quantity+"");
							}
							notifyDataSetChanged();
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
	/*private AsyncHttpResponseHandler res_getAddGoods(final ChildrenItem childrenItem, final int goodnum) {
		res_add = new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				try {
					if(response.getInt("code") == 200){
						int quantity = response.getJSONObject("datas").getInt("quantity");
						if(childrenItem.isChecked()){
							childrenItem.setQuantity(quantity+"");
							finalTotalPrice();//结算总数据
						}else{
							childrenItem.setQuantity(quantity+"");
						}
						notifyDataSetChanged();
					}else{
						Toast.makeText(context, "修改失败", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		};
		return res_add;
	}*/
	
	//增加商品
	class MyOnClickListener implements View.OnClickListener {
		GroupItem groupItem;
		ChildrenItem childrenItem;
		public MyOnClickListener(final GroupItem groupItem, final ChildrenItem childrenItem) {
			this.groupItem = groupItem;
			this.childrenItem = childrenItem;
		}
		@Override
		public void onClick(View view) {
			int goodnum = 0;
			switch (view.getId()) {
			case R.id.jiabt:
				/*加商品*/
				goodnum = Integer.parseInt(childrenItem.getQuantity());
				AddCarNum("1",childrenItem.getSid(),childrenItem);
//				HttpUtils.getAddGoods(res_getAddGoods(childrenItem, goodnum+1), key, childrenItem.getCart_id(), (goodnum+1)+"");
				break;
			case R.id.jianbt:
				/*减商品*/
				goodnum = Integer.parseInt(childrenItem.getQuantity());
				if (goodnum<2) {
					Toast.makeText(context, "受不了了，宝贝不能再减少了哦~", Toast.LENGTH_LONG).show();
				}else{
					AddCarNum("2",childrenItem.getSid(),childrenItem);
//					HttpUtils.getAddGoods(res_getAddGoods(childrenItem, goodnum-1), key, childrenItem.getCart_id(), (goodnum-1)+"");
				}
				break;
			case R.id.children_cb:
				if (!childrenItem.isChecked()) {
					childrenItem.setChecked(true);
					groupItem.setChecked(true);
					for (int i = 0; i < groupItem.getGoodList().size(); i++) {
						if(!groupItem.getGoodList().get(i).isChecked()){
							groupItem.setChecked(false);
						}
					}
				} else {
					childrenItem.setChecked(false);
					groupItem.setChecked(false);
				}
				B3_ShpppingCartAdapter.this.notifyDataSetChanged();
				finalTotalPrice();//结算总数据
				break;
			/*case R.id.tv_del:
				new AlertDialog.Builder(context)
				.setMessage("确认要删除该商品么？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
//						HttpUtils.getDelete(deleteChildrenItem(groupItem, childrenItem), key,childrenItem.getCart_id());
					}
				})
				.setNegativeButton("取消", null)
				.show();
				break;*/
			case R.id.cb_layout:
				if(groupItem.isChecked()){
					groupItem.setChecked(false);
					for (int i = 0; i < groupItem.getGoodList().size(); i++) {
						groupItem.getGoodList().get(i).setChecked(false);
					}
				}else{
					groupItem.setChecked(true);
					for (int i = 0; i < groupItem.getGoodList().size(); i++) {
						groupItem.getGoodList().get(i).setChecked(true);
					}
				}
				B3_ShpppingCartAdapter.this.notifyDataSetChanged();
				finalTotalPrice();//结算总数据
				break;
//			case R.id.tv_bianji:
//				if(groupItem.isSelected()){
//					groupItem.setSelected(false);
//				}else{
//					groupItem.setSelected(true);
//				}
//				B3_ShpppingCartAdapter.this.notifyDataSetChanged();
//				break;
			case R.id.group_name:
				/*Intent intent = new Intent();
				String storeid = groupItem.getStore_id();
				intent.putExtra("store_id", storeid);
				intent.setClass(context,BX_DianPuXiangQingActivity.class);
				context.startActivity(intent);*/
				break;
			case R.id.ll_goodtiaozhuan:
				/*Intent itdayspec = new Intent();
				String goid = childrenItem.getGoods_id();
				itdayspec.putExtra("goods_id",goid);
				itdayspec.setClass(context, Sp_GoodsInfoActivity.class);
				context.startActivity(itdayspec);*/
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 删除购物车商品
	 */
	private AsyncHttpResponseHandler deleteChildrenItem(final GroupItem groupItem, final ChildrenItem childrenItem) {
		if(res_delete == null){
			res_delete = new JsonHttpResponseHandler(){
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					super.onSuccess(statusCode, headers, response);
					RequestDailog.closeDialog();
					groupItem.getGoodList().remove(childrenItem);
					if(groupItem.getGoodList().size() == 0){
						dataList.remove(groupItem);
					}
					B3_ShpppingCartAdapter.this.notifyDataSetChanged();
				};
			};
		}
		return res_delete;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		final GroupItem groupItem = dataList.get(groupPosition);
		if (groupItem == null || groupItem.getGoodList() == null
				|| groupItem.getGoodList().isEmpty()) {
			return 0;
		}
		return groupItem.getGoodList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		if (dataList == null) {
			return null;
		}
		return dataList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		if (dataList == null) {
			return 0;
		}
		return dataList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		try {
			final GroupItem groupItem = dataList.get(groupPosition);

			final GroupViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new GroupViewHolder();
				convertView = inflater.inflate(R.layout.group_item, null);
				viewHolder.groupNameTV = (TextView) convertView.findViewById(R.id.group_name);
				viewHolder.groupCBImg = (ImageView) convertView.findViewById(R.id.group_cb_img);//选中(店铺选择)
				viewHolder.groupCBLayout = (LinearLayout) convertView.findViewById(R.id.cb_layout);
//				viewHolder.tv_bianji = (TextView)convertView.findViewById(R.id.tv_bianji);
				convertView.setClickable(true);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (GroupViewHolder) convertView.getTag();
			}
//			viewHolder.tv_bianji.setText(groupItem.isSelected()?"完成":"编辑");
//			viewHolder.tv_bianji.setOnClickListener(new MyOnClickListener(groupItem, null));
			
			viewHolder.groupNameTV.setText(groupItem.getName());
			viewHolder.groupCBImg.setSelected(groupItem.isChecked());//设置选中状态
			viewHolder.groupCBLayout.setOnClickListener(new MyOnClickListener(groupItem, null));
			viewHolder.groupNameTV.setOnClickListener(new MyOnClickListener(groupItem, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	private void finalTotalPrice(){
		sumtiaoshu = 0;//结算数
		allprice = 0f;//总价格
		allcheckstate = true;//是否全选
		for (int i = 0; i < dataList.size(); i++) {
			List<ChildrenItem> childrenList = dataList.get(i).getGoodList();
			for (int j = 0; j < childrenList.size(); j++) {
				ChildrenItem childrenItem = childrenList.get(j);
				if(childrenItem.isChecked()){
					sumtiaoshu += 1;
					allprice += Float.valueOf(childrenItem.getPrice())*Integer.valueOf(childrenItem.getQuantity());
				}else{
					allcheckstate = false;//如果有未选中的,则为false
				}
			}
		}
		refresh.refreshShopCarDate(allprice, allcheckstate, sumtiaoshu);
	}

	final static class GroupViewHolder {
		TextView groupNameTV;
		ImageView groupCBImg;
		LinearLayout groupCBLayout;
	}

	final static class ChildViewHolder {
		TextView childrenNameTV;
		ImageView im_shangpuimg;
		TextView tv_spec;
		TextView tv_goods_price,tv_old_price;
		TextView tv_goods_num;
		//单个商品数量
		TextView editconunt;
		//加减数量
		ImageView jiabt,jianbt;
		//默认layout，编辑layout
		LinearLayout ll_goodtiaozhuan;
		CheckBox childrenCB;
//		TextView tv_del;
	}
	
	public interface RefreshExpandableList{
		void refreshShopCarDate(float totalprice, boolean allcheck, int count);//刷新购物车数据
	}
}