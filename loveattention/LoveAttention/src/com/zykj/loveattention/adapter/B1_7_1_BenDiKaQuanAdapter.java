package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B4_3_KaQuanAdapter.getDetail;
import com.zykj.loveattention.ui.B4_3_KaQuanInfoActivity;
import com.zykj.loveattention.view.CircularImage;

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
			convertView=LinearLayout.inflate(context, R.layout.b1_7_1_kaquanitem, null);
			ViewHolder.tv_diyongquan=(TextView) convertView.findViewById(R.id.tv_diyongquan);
			ViewHolder.tv_temai=(TextView) convertView.findViewById(R.id.tv_temai);
			ViewHolder.tv_youxiaoqi=(TextView) convertView.findViewById(R.id.tv_youxiaoqi);
			ViewHolder.rl_shang=(RelativeLayout) convertView.findViewById(R.id.rl_shang);
			ViewHolder.im_touxiang=(CircularImage) convertView.findViewById(R.id.im_touxiang);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		String couponid = data.get(position).get("couponid");
		ViewHolder.rl_shang.setBackgroundColor(android.graphics.Color.parseColor(data.get(position).get("couponcolor")));
		ImageLoader.getInstance().displayImage(data.get(position).get("couponimage"), ViewHolder.im_touxiang);
		ViewHolder.tv_diyongquan.setText(data.get(position).get("couponname"));
		ViewHolder.tv_temai.setText(data.get(position).get("coupontitle"));
		ViewHolder.tv_youxiaoqi.setText(data.get(position).get("createtime").substring(10));
		convertView.setOnClickListener(new getDetail(couponid));
		return convertView;
	}
	
	public final class ViewHolder {  
		public CircularImage im_touxiang;
        public TextView tv_diyongquan;
        public TextView tv_temai;
        public TextView tv_youxiaoqi;
        public RelativeLayout rl_shang;
    }  

	/**
	 * 跳转到卡券详情
	 * @author lss
	 *
	 */
	class getDetail implements View.OnClickListener {
		String couponid;
		public getDetail(String couponid) {
			this.couponid = couponid;
		}
		@Override
		public void onClick(View arg0) {
			Intent itkaquan1 = new Intent(context, B4_3_KaQuanInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("couponid", couponid);
			itkaquan1.putExtra("data",bundle);
			context.startActivity(itkaquan1);
		}
	}
}
