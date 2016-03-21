package com.zykj.loveattention.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.PingLun;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;

public class B173_PingLunAdapter extends BaseAdapter {
	private Activity context;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	ArrayList<PingLun> dpgoodsinfolist;

	public B173_PingLunAdapter(Activity context, ArrayList<PingLun> pinglunlist) {
		this.context = context;
		this.dpgoodsinfolist = pinglunlist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dpgoodsinfolist == null ? 0 : dpgoodsinfolist.size();
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
			convertView=LinearLayout.inflate(context, R.layout.b173_pinglun, null);
			ViewHolder.im_headportain=(ImageView) convertView.findViewById(R.id.im_headportain);
			ViewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			ViewHolder.tv_content=(TextView) convertView.findViewById(R.id.tv_content);
			ViewHolder.tv_intime=(TextView) convertView.findViewById(R.id.tv_intime);
			ViewHolder.comment_rating_bar=(RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		try {
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+dpgoodsinfolist.get(position).getHeadportain(), ViewHolder.im_headportain);
		} catch (Exception e) {
			
		}
		ViewHolder.tv_name.setText(dpgoodsinfolist.get(position).getName());
		ViewHolder.tv_content.setText(dpgoodsinfolist.get(position).getContent());
		ViewHolder.tv_intime.setText(dpgoodsinfolist.get(position).getIntime());
		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(dpgoodsinfolist.get(position).getStars()));
		return convertView;
	}
	
	public final class ViewHolder {  
		public ImageView im_headportain;
		public TextView tv_name;
		public TextView tv_content;
		public TextView tv_intime;
		public RatingBar comment_rating_bar;

    }  
}
