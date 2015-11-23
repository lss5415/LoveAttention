package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;

public class B1_3_GuangGaoDingZhiAdapter  extends BaseAdapter {
	private Activity context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B1_3_GuangGaoDingZhiAdapter(Activity context,List<Map<String, String>> data) {
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
			convertView=LinearLayout.inflate(context, R.layout.b1_3_guanggao, null);
			ViewHolder.im_tu=(ImageView) convertView.findViewById(R.id.im_tu);
			ViewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage((String)data.get(position).get("mainimgpath"), ViewHolder.im_tu);
		ViewHolder.tv_name.setText(data.get(position).get("name"));
		return convertView;
	}
	
	public final class ViewHolder {  
        public ImageView im_tu;
        public TextView tv_name;
    }  
}
