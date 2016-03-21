package com.zykj.loveattention.adapter;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.FanCaiDan;

public class B172InfoAdapter extends BaseAdapter {
	private Activity context;
	private org.json.JSONArray fcdgoodslist;
	private RefreshExpandableList refresh;
	private RequestQueue mRequestQueue;
	private Map<String,FanCaiDan> fcdmap;
	private String a="";

	public B172InfoAdapter(Activity context,org.json.JSONArray fcdgoodslist,RefreshExpandableList refresh,Map<String,FanCaiDan> fcdmap) {
		this.context = context;
		this.fcdgoodslist = fcdgoodslist;
		this.refresh = refresh;
		this.fcdmap = fcdmap;
		mRequestQueue = Volley.newRequestQueue(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fcdgoodslist == null ? 0 : fcdgoodslist.length();
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
		ViewHolder ViewHolder=null;
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.b172info_item, null);
			ViewHolder.b172imgpath=(ImageView) convertView.findViewById(R.id.b172imgpath);
			ViewHolder.b172name=(TextView) convertView.findViewById(R.id.b172name);
			ViewHolder.b172remark=(TextView) convertView.findViewById(R.id.b172remark);
			ViewHolder.b172price=(TextView) convertView.findViewById(R.id.b172price);
			ViewHolder.b172oldprice=(TextView) convertView.findViewById(R.id.b172oldprice);
			ViewHolder.rl_b172addcar=(RelativeLayout) convertView.findViewById(R.id.rl_b172addcar);
			convertView.setTag(ViewHolder);
			
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		try {
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+fcdgoodslist.getJSONObject(position).getString("imgpath"), ViewHolder.b172imgpath);
			ViewHolder.b172name.setText(fcdgoodslist.getJSONObject(position).getString("name"));
			ViewHolder.b172remark.setText(fcdgoodslist.getJSONObject(position).getString("remark"));
			ViewHolder.b172price.setText(fcdgoodslist.getJSONObject(position).getString("price")+"元");
			ViewHolder.b172oldprice.setText(fcdgoodslist.getJSONObject(position).getString("oldprice")+"元");
			ViewHolder.b172oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ViewHolder.rl_b172addcar.setOnClickListener(new getAddGouWu(fcdgoodslist,position));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	class getAddGouWu implements View.OnClickListener {
		org.json.JSONArray fcdgoodslist;
		int position;
		public getAddGouWu(org.json.JSONArray fcdgoodslist,int position) {
			this.fcdgoodslist = fcdgoodslist;//价格
			this.position = position;
		}
		@Override
		public void onClick(View arg0) {
			Toast.makeText(context, "加入购物车成功", Toast.LENGTH_LONG).show();
//			url_shoppingCartAdd
//			AddShoppingCart();
			
			try {
				String id = fcdgoodslist.getJSONObject(position).getString("id");
				String jiage = fcdgoodslist.getJSONObject(position).getString("price");
				String name = fcdgoodslist.getJSONObject(position).getString("name");
				FanCaiDan fcdmodel = fcdmap.get(id);
				if (fcdmodel==null) {
					FanCaiDan fancaidan = new FanCaiDan();
					fancaidan.setShangpinid(id);
					fancaidan.setJiage(jiage);
					fancaidan.setName(name);
					fancaidan.setShuliang(1);
					fcdmap.put(id, fancaidan);
					if (a=="") {
						a=id;
					}else {
						a=a+","+id;
					} 
				}else {
					int num=fcdmodel.getShuliang()+1;
					fcdmodel.setShuliang(num);
				}
				
//				FanCaiDan fancaidan = new FanCaiDan();
//				price = fcdgoodslist.getJSONObject(position).getString("price");
//				fancaidan.setJiage(price);
//				fancaidan.setShangpinid("1");
//				fancaidan.setShuliang(1);
//				object.add(fancaidan);
				refresh.refreshState("1",fcdmap,a);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public final class ViewHolder {  
      public ImageView b172imgpath;
      public TextView b172name;
      public TextView b172remark;
      public TextView b172price;
      public TextView b172oldprice;
      public RelativeLayout rl_b172addcar;
    }  
	
	public interface RefreshExpandableList{
		void refreshState(String fcdnum,Map<String,FanCaiDan> fcdmap,String a);//刷新购物车数据
	}
}
