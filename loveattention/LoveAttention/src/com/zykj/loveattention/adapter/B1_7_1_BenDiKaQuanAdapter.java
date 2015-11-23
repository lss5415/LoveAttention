package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;

public class B1_7_1_BenDiKaQuanAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();

	public B1_7_1_BenDiKaQuanAdapter(Context context,List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return data == null ? 0 : data.size();
		return 5;
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
			convertView=LinearLayout.inflate(context, R.layout.b1_7_1_kaquanitem, null);
			ViewHolder.tv_diyongquan=(TextView) convertView.findViewById(R.id.tv_diyongquan);
			ViewHolder.tv_temai=(TextView) convertView.findViewById(R.id.tv_temai);
			ViewHolder.tv_youxiaoqi=(TextView) convertView.findViewById(R.id.tv_youxiaoqi);
			ViewHolder.rl_shang=(RelativeLayout) convertView.findViewById(R.id.rl_shang);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ViewHolder.rl_shang.setBackgroundColor(android.graphics.Color.parseColor("#F39292"));
//		ViewHolder.tv_shijian.setText(data.get(position).get("shijian"));
//		ViewHolder.tv_bianliang.setText(data.get(position).get("bianliang"));
//		ViewHolder.tv_miaoshu.setText(data.get(position).get("miaoshu"));
//		ViewHolder.tv_yue.setText(data.get(position).get("yue"));
//		ImageLoader.getInstance().displayImage((String)data.get(position).get("store_label"), ViewHolder.im_a3_pic);
//		ViewHolder.tv_a3_storename.setText(data.get(position).get("store_name"));
//		ViewHolder.tv_a3_juli.setText(data.get(position).get("juli"));
//		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(data.get(position).get("store_desccredit")));
//		ViewHolder.tv_a3_pinglunsum.setText(data.get(position).get("store_evaluate_count"));
		return convertView;
	}
	
	public final class ViewHolder {  
        public TextView tv_diyongquan;
        public TextView tv_temai;
        public TextView tv_youxiaoqi;
        public RelativeLayout rl_shang;
    }  
}
