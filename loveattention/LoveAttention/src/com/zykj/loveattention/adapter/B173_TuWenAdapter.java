package com.zykj.loveattention.adapter;

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
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;

public class B173_TuWenAdapter extends BaseAdapter {
	private Activity context;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private List<Map<String, String>> data;

	public B173_TuWenAdapter(Activity context, List<Map<String, String>> data) {
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
			convertView=LinearLayout.inflate(context, R.layout.b173_tuwen, null);
			ViewHolder.im_tuwen=(ImageView) convertView.findViewById(R.id.im_tuwen);
			ViewHolder.tv_tuwenzi=(TextView) convertView.findViewById(R.id.tv_tuwenzi);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		try {
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+data.get(position).get("linkurl"), ViewHolder.im_tuwen);
		} catch (Exception e) {
			
		}
		ViewHolder.tv_tuwenzi.setText(data.get(position).get("description"));
		return convertView;
	}
	
	public final class ViewHolder {  
		public ImageView im_tuwen;
		public TextView tv_tuwenzi;

    }  
}
