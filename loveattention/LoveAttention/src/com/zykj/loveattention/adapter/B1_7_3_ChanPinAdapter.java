package com.zykj.loveattention.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Paint;
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
import com.zykj.loveattention.data.DianPuGoodsInfo;
import com.zykj.loveattention.utils.AnimateFirstDisplayListener;
import com.zykj.loveattention.utils.ImageOptions;

public class B1_7_3_ChanPinAdapter extends BaseAdapter {
	private Activity context;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	List<DianPuGoodsInfo> dpgoodsinfolist;

	public B1_7_3_ChanPinAdapter(Activity context, List<DianPuGoodsInfo> dpgoodsinfolist) {
		this.context = context;
		this.dpgoodsinfolist = dpgoodsinfolist;
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
			ImageLoader.getInstance().displayImage(AppValue.ImgUrl+dpgoodsinfolist.get(position).getImgpath(),  ViewHolder.im_173, ImageOptions.getOpstion(), animateFirstListener);	
		} catch (Exception e) {
			
		}
		
		ViewHolder.tv_173title.setText(dpgoodsinfolist.get(position).getName());
		ViewHolder.tv_173yishou.setText("已售"+dpgoodsinfolist.get(position).getPaynum());
		ViewHolder.tv_173biaoqian.setText(dpgoodsinfolist.get(position).getRemark());
		ViewHolder.tv_173xianjia.setText("￥"+dpgoodsinfolist.get(position).getPrice());
		ViewHolder.tv_173yuanjia.setText("￥"+dpgoodsinfolist.get(position).getOldprice());
		ViewHolder.tv_173yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
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
