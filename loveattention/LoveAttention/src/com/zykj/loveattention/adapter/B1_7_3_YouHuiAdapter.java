package com.zykj.loveattention.adapter;

import java.util.List;

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
import com.zykj.loveattention.data.MerchantyhInfo;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.ImageOptions;

public class B1_7_3_YouHuiAdapter extends BaseAdapter {
	private Activity context;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	List<MerchantyhInfo> meinfolist;

	public B1_7_3_YouHuiAdapter(Activity context, List<MerchantyhInfo> meinfolist) {
		this.context = context;
		this.meinfolist=meinfolist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return meinfolist == null ? 0 : meinfolist.size();
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
			convertView=LinearLayout.inflate(context, R.layout.b1_7_3_youhuiandchanpin, null);
			ViewHolder.im_173=(ImageView) convertView.findViewById(R.id.im_173);
			ViewHolder.tv_173title=(TextView) convertView.findViewById(R.id.tv_173title);
			ViewHolder.tv_173yishou=(TextView) convertView.findViewById(R.id.tv_173yishou);
			ViewHolder.tv_173biaoqian=(TextView) convertView.findViewById(R.id.tv_173biaoqian);
			ViewHolder.tv_173xianjia=(TextView) convertView.findViewById(R.id.tv_173xianjia);
			ViewHolder.tv_173yuanjia=(TextView) convertView.findViewById(R.id.tv_173yuanjia);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		try { 
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+meinfolist.get(position).getHeadportain(),  ViewHolder.im_173, ImageOptions.getOpstion(), animateFirstListener);	
		} catch (Exception e) {
			
		}
		
		ViewHolder.tv_173title.setText(meinfolist.get(position).getName());
//		ViewHolder.tv_173yishou.setText(meinfolist.get(position).get);
		ViewHolder.tv_173biaoqian.setText(meinfolist.get(position).getCouponname());
//		ViewHolder.tv_173xianjia.setText(meinfolist.get(position).get);
//		ViewHolder.tv_173yuanjia.setText(meinfolist.get(position).get);
		return convertView;
	}
	
	public final class ViewHolder {  
		public ImageView im_173;
		public TextView tv_173title;
		public TextView tv_173yishou;
		public TextView tv_173biaoqian;
		public TextView tv_173xianjia;
		public TextView tv_173yuanjia;
    }  
}
