package com.zykj.loveattention.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.utils.Log;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.GoodList;
import com.zykj.loveattention.data.SearchList;
import com.zykj.loveattention.ui.ShangPinInfoActivity;

/**
 * @author  lc 
 * @date 创建时间：2016-1-11 上午10:56:30 
 * @version 1.0 
 * @Description 搜索结果Adapters 包括二级listview
 */
public class B1_SousuojieguoAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<SearchList> list;
	private double lat,lon;

	public B1_SousuojieguoAdapter(Context mContext, ArrayList<SearchList> list,double lat,double lon) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.lat = lat;
		this.lon = lon;
	}

	@Override
	public int getCount() {
		int count = 0;
		if(list.size()>0){
			count = list.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView!=null){
			view = convertView;
		}else{
			view = LayoutInflater.from(mContext).inflate(R.layout.b1_sousuojieguolistview_item, parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder();
			
			holder.img_head = (ImageView) view.findViewById(R.id.sousuojieguolistview_img_head);
			holder.txt_name = (TextView) view.findViewById(R.id.sousuojieguolistview_txt_name);
			holder.txt_tag = (TextView) view.findViewById(R.id.sousuojieguolistview_txt_tag);
			holder.txt_perperson = (TextView) view.findViewById(R.id.sousuojieguolistview_txt_perperson);
			holder.txt_distance = (TextView) view.findViewById(R.id.sousuojieguolistview_txt_distance);
			holder.ratingbar = (RatingBar) view.findViewById(R.id.sousuojieguolistview_ratingBar);
			holder.listview = (ListView) view.findViewById(R.id.sousuojieguolistview_listview);
			
			view.setTag(holder);
		}
		
		SearchList search = list.get(position);
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+search.getImgpath(), holder.img_head);
		holder.txt_name.setText(search.getName());
		holder.txt_tag.setText(search.getRemark());
		holder.txt_perperson.setText("人均"+search.getPerperson()+"元");
		holder.txt_distance.setText(search.getDistance()+"千米");
		holder.ratingbar.setRating(search.getStars());
		
		final ArrayList<GoodList> goodlist = new ArrayList<GoodList>(); 
		goodlist.addAll(search.getGoodList());
		if(search.getGoodList().size() !=0){
			DetailAdapter adapter = new DetailAdapter(mContext,goodlist);
			holder.listview.setAdapter(adapter);
			setListViewHeight(holder.listview);
			holder.listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO 跳转商品详情
//					Toast.makeText(mContext, "跳转商品详情", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(mContext, ShangPinInfoActivity.class);
					intent.putExtra("goodsid", goodlist.get(arg2).getId());
					mContext.startActivity(intent);
				}
			});
		}
		return view;
	}
	

	/** 
	 
     * 设置Listview的高度 
 
     */ 
 
    public void setListViewHeight(ListView listView) {   
 
        ListAdapter listAdapter = listView.getAdapter();    
 
        if (listAdapter == null) {   
 
            return;   
 
        }   
 
        int totalHeight = 0;   
 
        for (int i = 0; i < listAdapter.getCount(); i++) {   
 
            View listItem = listAdapter.getView(i, null, listView);   
 
            listItem.measure(0, 0);   
 
            totalHeight += listItem.getMeasuredHeight();   
 
        }   
 
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
 
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) +10;   
 
        listView.setLayoutParams(params);  
 
    }
	
	class ViewHolder{
		TextView txt_name,txt_tag,txt_perperson,txt_distance;
		ImageView img_head;
		RatingBar ratingbar;
		ListView listview;
	}
	
	class DetailAdapter extends BaseAdapter{
		
		private Context mContext;
		private ArrayList<GoodList> goodlist;
		
		public DetailAdapter(Context mContext, ArrayList<GoodList> goodlist) {
			super();
			this.mContext = mContext;
			this.goodlist = goodlist;
			Log.d("----", "goodlist.size = " + goodlist.size());
		}

		@Override
		public int getCount() {
			int count =0;
			if(goodlist.size()>0){
				count = goodlist.size();
			}
			return count;
		}

		@Override
		public Object getItem(int position) {
			return goodlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if(convertView!=null){
				view = convertView;
			}else{
				view = LayoutInflater.from(mContext).inflate(R.layout.b1_sousuojieguolistview_listview_item, parent,false);
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();
			if(holder == null){
				holder = new ViewHolder();
				
				holder.txt_price = (TextView) view.findViewById(R.id.sousuojieguolistview_listview_txt_price);
				holder.txt_name = (TextView) view.findViewById(R.id.sousuojieguolistview_listview_txt_name);
				holder.txt_sellnum = (TextView) view.findViewById(R.id.sousuojieguolistview_listview_txt_sellnum);
				
				view.setTag(holder);
			}
			
			GoodList good = goodlist.get(position);
			
			holder.txt_price.setText("￥"+good.getPrice());
			holder.txt_name.setText(good.getName());
			holder.txt_sellnum.setText("已售"+good.getPaynum());
			
			
			return view;
		}
		
		class ViewHolder{
			TextView txt_price,txt_name,txt_sellnum,txt_gray;
		}
		
	}

}
