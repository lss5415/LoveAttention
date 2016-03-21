package com.zykj.loveattention.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.data.Sousuo;
import com.zykj.loveattention.utils.Tools;

public class B1_HotSearchAdapter extends BaseAdapter {
	private Context context; 
	private ArrayList<Sousuo> list;
	
	public B1_HotSearchAdapter(Context context, ArrayList<Sousuo> list) {
		super();
		this.context = context;
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
			view = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent,false);
		}
        TextView txt = (TextView) view.findViewById(R.id.tv_content);
		txt.setText(list.get(position).getKeywordtitle());
		return view;
	}

}
