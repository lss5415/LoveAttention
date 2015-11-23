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

public class B1_ShouYeAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B1_ShouYeAdapter(Context context,List<Map<String, String>> data) {
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
			convertView=LinearLayout.inflate(context, R.layout.b1_shouyeitem, null);
			ViewHolder.d2_img_layout=(ImageView) convertView.findViewById(R.id.d2_img_layout);
			ViewHolder.d2_tv_title=(TextView) convertView.findViewById(R.id.d2_tv_title);
			ViewHolder.tv_renjun=(TextView) convertView.findViewById(R.id.tv_renjun);
			ViewHolder.d2_tv_neirong=(TextView) convertView.findViewById(R.id.d2_tv_neirong);
			ViewHolder.comment_rating_bar=(RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			ViewHolder.tv_juli=(TextView) convertView.findViewById(R.id.tv_juli);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
//		ViewHolder.tv_shijian.setText(data.get(position).get("shijian"));
//		ViewHolder.tv_bianliang.setText(data.get(position).get("bianliang"));
//		ViewHolder.tv_miaoshu.setText(data.get(position).get("miaoshu"));
//		ViewHolder.tv_yue.setText(data.get(position).get("yue"));
//		ImageLoader.getInstance().displayImage((String)data.get(position).get("store_label"), ViewHolder.im_a3_pic);
//		ViewHolder.tv_a3_storename.setText(data.get(position).get("store_name"));
//		ViewHolder.tv_a3_juli.setText(data.get(position).get("juli"));
//		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(data.get(position).get("store_desccredit")));
//		ViewHolder.tv_a3_pinglunsum.setText(data.get(position).get("store_evaluate_count"));
		ImageLoader.getInstance().displayImage((String)data.get(position).get("imgpath"), ViewHolder.d2_img_layout);
		ViewHolder.d2_tv_title.setText(data.get(position).get("name"));
		ViewHolder.tv_renjun.setText(data.get(position).get("perperson"));
		ViewHolder.d2_tv_neirong.setText(data.get(position).get("remark"));
		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(data.get(position).get("stars")));
//		ViewHolder.tv_juli.setText(data.get(position).get("merchantid"));
		return convertView;
	}
	
	public final class ViewHolder {	
		public ImageView d2_img_layout;
		public TextView d2_tv_title;
		public TextView tv_renjun;
		public TextView d2_tv_neirong;
		public RatingBar comment_rating_bar;
		public TextView tv_juli;
    }  
}
