package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;

public class B131GuangGaoList extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B131GuangGaoList(Context context,List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
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
		ViewHolder ViewHolder=null;
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.b131_shangpinitem, null);
			ViewHolder.im_sp=(ImageView) convertView.findViewById(R.id.im_sp);
			ViewHolder.tv_shangpinname=(TextView) convertView.findViewById(R.id.tv_shangpinname);
			ViewHolder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage((String)data.get(position).get("imgpath"), ViewHolder.im_sp);
		ViewHolder.tv_shangpinname.setText(data.get(position).get("name"));
		ViewHolder.tv_price.setText(data.get(position).get("price"));
		return convertView;
	}
	
	public final class ViewHolder {	
		public ImageView im_sp;
		public TextView tv_shangpinname;
		public TextView tv_price;
    }  
}
