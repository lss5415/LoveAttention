package com.zykj.loveattention.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
/**
 * 订单二级listview
 * @author zyk
 *
 */
public class B5_5_OrderStatuslistviewAdapter extends BaseAdapter {
	
	private Activity c;
	JSONArray extend_order_goods;
	private String datatime;
    
	public B5_5_OrderStatuslistviewAdapter(Activity c, JSONArray extend_order_goods2,String datatime) {
		this.c = c;
		this.extend_order_goods = extend_order_goods2;
		this.datatime = datatime;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return extend_order_goods == null ? 0 : extend_order_goods.size();
//		return 2;
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
		ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(c);
            convertView = mInflater.inflate(R.layout.ui_b5_5_orderlist_list_items_1, null);
            viewHolder.tv_productName = (TextView) convertView.findViewById(R.id.tv_productName);
            viewHolder.tv_goodsprice = (TextView) convertView.findViewById(R.id.tv_goodsprice);
            viewHolder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
            viewHolder.tv_riqi = (TextView) convertView.findViewById(R.id.tv_riqi);
            viewHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
    	try {
    			JSONObject  extend_order_goods1 = (JSONObject) extend_order_goods.get(position);
    			String goods_image_url = AppValue.ImgUrl+extend_order_goods1.getString("imgpath");
    			ImageLoader.getInstance().displayImage(goods_image_url, viewHolder.iv_product);//设置产品图片
    			viewHolder.tv_productName.setText(extend_order_goods1.getString("goodsName").toString());//设置产品名称
    			viewHolder.tv_goodsprice.setText("￥"+extend_order_goods1.getString("price").toString());//设置产品价格
    			viewHolder.tv_num.setText("x "+extend_order_goods1.getString("quantity").toString());
    			viewHolder.tv_riqi.setText(datatime.substring(0, 10));
		} catch (JSONException e) {
			
		}
		return convertView;
	}
	/**
	 * 点击立即兑换按钮之后的跳转
	 * @author zyk
	 *
	 */
	class DeletetheorderListener implements View.OnClickListener {
		int position;
		public DeletetheorderListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//		HttpUtils.cancelOrder(res_cancelOrder, key, order_id);
			
		}
	}
	private static class ViewHolder
    {
        TextView tv_productName;
        TextView tv_goodsprice;
        ImageView iv_product;//
        TextView tv_riqi,tv_num;
    }
}
