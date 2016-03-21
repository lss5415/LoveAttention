package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.data.Category;
import com.zykj.loveattention.ui.B1_4_TuanGouActivity;
import com.zykj.loveattention.view.GridViewForListView;

/**
 * @author  lc 
 * @date 创建时间：2016-1-11 下午5:02:55 
 * @version 1.0  
 * @Description 全部分类一级列表Adapter
 */
public class B1_AllCategoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Category> list;
	private Map<String,ArrayList<Category>> map;
	
	public B1_AllCategoryAdapter(Context mContext, ArrayList<Category> list,Map<String,ArrayList<Category>> map) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.map = map;
//		Log.d("----", "list1 =  ++++ " + list1.size());
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
			view = LayoutInflater.from(mContext).inflate(R.layout.b1_allcategorylistview_item, parent,false);
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder();
			
			holder.txt = (TextView) view.findViewById(R.id.allcategorylistview_txt_name);
			holder.gridview = (GridViewForListView) view.findViewById(R.id.allcategorysecondrylistview);
		}
		
		
		holder.txt.setText(list.get(position).getName());
		final ArrayList<Category> list2 = new ArrayList<Category>();
		if(holder.gridview!=null){
			list2.clear();
			list2.addAll(map.get("category"+list.get(position).getCategoryid()));
			CategoryGridViewAdapter adapter = new CategoryGridViewAdapter(mContext,list2);
			holder.gridview.setAdapter(adapter);
			
			holder.gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
//					Toast.makeText(mContext, list2.get(position).getName()+list2.get(position).getCategoryid(), Toast.LENGTH_SHORT).show();
					Intent jiudian = new Intent(mContext, B1_4_TuanGouActivity.class);
					jiudian.putExtra("title", list2.get(position).getName());
					jiudian.putExtra("categoryid", list2.get(position).getCategoryid());
					mContext.startActivity(jiudian);	
				}
			});
		}
		
		return view;
	}
	
	class ViewHolder{
		TextView txt;
		GridViewForListView gridview;
	}
	
	class CategoryGridViewAdapter extends BaseAdapter{
		
		private Context mContext;
		private ArrayList<Category> list;

		public CategoryGridViewAdapter(Context mContext,
				ArrayList<Category> list) {
			super();
			this.mContext = mContext;
			this.list = list;
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
				view = LayoutInflater.from(mContext).inflate(R.layout.b1_allcategorysecondrygridview_item,parent,false);
			}
			
			TextView txt = (TextView) view.findViewById(R.id.allcategorysecondry_txt_name);
			
			txt.setText(list.get(position).getName());
			txt.setBackgroundColor(Color.WHITE
					);
			
			return view;
		}
		
	}
	
}
