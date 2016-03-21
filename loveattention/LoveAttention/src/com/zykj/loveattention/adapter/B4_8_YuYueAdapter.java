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

public class B4_8_YuYueAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B4_8_YuYueAdapter(Context context,List<Map<String, String>> data) {
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
			convertView=LinearLayout.inflate(context, R.layout.b4_8_yuyueitem, null);
			ViewHolder.d2_img_layout=(ImageView) convertView.findViewById(R.id.d2_img_layout);
			ViewHolder.d2_tv_title=(TextView) convertView.findViewById(R.id.d2_tv_title);
			ViewHolder.tv_renjun=(TextView) convertView.findViewById(R.id.tv_renjun);
			ViewHolder.d2_tv_neirong=(TextView) convertView.findViewById(R.id.d2_tv_neirong);
			ViewHolder.d2_tv_state=(TextView) convertView.findViewById(R.id.d2_tv_state);
			ViewHolder.comment_rating_bar=(RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			ViewHolder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ViewHolder.d2_tv_title.setText(data.get(position).get("name"));
		ViewHolder.tv_renjun.setText("人均"+data.get(position).get("visitnum")+"元");
		ViewHolder.d2_tv_neirong.setText("标签："+data.get(position).get("remark"));
		ViewHolder.tv_price.setText("距离"+data.get(position).get("price")+"元");
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+data.get(position).get("imgpath"), ViewHolder.d2_img_layout);
		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(data.get(position).get("stars")));
		if (data.get(position).get("rstate").equals("1")) {
			ViewHolder.d2_tv_state.setText("预约中");
		}else {
			ViewHolder.d2_tv_state.setText("预约成功");
		}
		return convertView;
	}
	
	public final class ViewHolder {  
		public ImageView d2_img_layout;
        public TextView d2_tv_title;
        public TextView d2_tv_state;
        public TextView d2_tv_neirong;
        public TextView tv_renjun;
        public RatingBar comment_rating_bar;
        public TextView tv_price;
        
    }  
}
