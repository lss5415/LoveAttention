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
import com.zykj.loveattention.data.AppValue;

public class B4_6a_ShouCangAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B4_6a_ShouCangAdapter(Context context,List<Map<String, String>> data) {
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
			convertView=LinearLayout.inflate(context, R.layout.b4_6a_shoucangitem, null);
			ViewHolder.a6_img_layout=(ImageView) convertView.findViewById(R.id.a6_img_layout);
			ViewHolder.a6_tv_title=(TextView) convertView.findViewById(R.id.a6_tv_title);
			ViewHolder.a6_tv_renjun=(TextView) convertView.findViewById(R.id.a6_tv_renjun);
			ViewHolder.a6_tv_neirong=(TextView) convertView.findViewById(R.id.a6_tv_neirong);
			ViewHolder.comment_rating_bar=(RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ViewHolder.a6_tv_title.setText(data.get(position).get("name"));
		ViewHolder.a6_tv_renjun.setText("人均"+data.get(position).get("price")+"元");
		ViewHolder.a6_tv_neirong.setText("标签："+data.get(position).get("remark"));
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+data.get(position).get("imgpath"), ViewHolder.a6_img_layout);
		try {
			ViewHolder.comment_rating_bar.setRating(Float.parseFloat(data.get(position).get("stars")));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return convertView;
	}

	public final class ViewHolder {  
		public ImageView a6_img_layout;
        public TextView a6_tv_title;
        public TextView a6_tv_renjun;
        public TextView a6_tv_neirong;
        public RatingBar comment_rating_bar;
    }  
}
