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
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;

public class B2_FuJin_Adapter extends BaseAdapter {
	private Activity context;
	private List<Map<String, String>> fujindata = new ArrayList<Map<String, String>>();

	public B2_FuJin_Adapter(Activity context,List<Map<String, String>> fujindata) {
		this.context = context;
		this.fujindata = fujindata;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fujindata == null ? 0 : fujindata.size();
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
			convertView=LinearLayout.inflate(context, R.layout.b2_fujin_item, null);
			ViewHolder.d2_img_layout = (ImageView) convertView.findViewById(R.id.d2_img_layout);
			ViewHolder.d2_tv_title = (TextView) convertView.findViewById(R.id.d2_tv_title);
			ViewHolder.tv_jiage = (TextView) convertView.findViewById(R.id.tv_jiage);
			ViewHolder.d2_tv_neirong = (TextView) convertView.findViewById(R.id.d2_tv_neirong);
			ViewHolder.comment_rating_bar = (RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			ViewHolder.tv_juli = (TextView) convertView.findViewById(R.id.tv_juli);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+(String)fujindata.get(position).get("imgpath"), ViewHolder.d2_img_layout);
		ViewHolder.d2_tv_title.setText(fujindata.get(position).get("name"));
//		ViewHolder.tv_jiage.setText(fujindata.get(position).get("perperson"));
		ViewHolder.d2_tv_neirong.setText(fujindata.get(position).get("remark"));
		try {
			ViewHolder.comment_rating_bar.setRating(Float.parseFloat(fujindata.get(position).get("stars")));
		} catch (Exception e) {
			
		}
//		ViewHolder.tv_juli.setText(fujindata.get(position).get("price"));
		return convertView;
	}
	
	public final class ViewHolder {
		public ImageView d2_img_layout;
		public TextView d2_tv_title;
		public TextView tv_jiage;
		public TextView d2_tv_neirong;
		public RatingBar comment_rating_bar;
		public TextView tv_juli;
    }  
}
