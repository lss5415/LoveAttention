package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
/**
 * b2和b3通用的适配器
 * 
 * @author zhuyikun
 *
 */
public class B2_and_B3_Adapter  extends BaseAdapter {
	private Activity context;
	private List<Map<String, String>> faxiandata = new ArrayList<Map<String, String>>();

	public B2_and_B3_Adapter(Activity context,List<Map<String, String>> faxiandata) {
		this.context = context;
		this.faxiandata = faxiandata;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return faxiandata == null ? 0 : faxiandata.size();
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
			convertView=LinearLayout.inflate(context, R.layout.b3_huodong_item, null);
			ViewHolder.d2_img_layout = (ImageView) convertView.findViewById(R.id.d2_img_layout);
			ViewHolder.d2_tv_title = (TextView) convertView.findViewById(R.id.d2_tv_title);
			ViewHolder.tv_jiage = (TextView) convertView.findViewById(R.id.tv_jiage);
			ViewHolder.d2_tv_neirong = (TextView) convertView.findViewById(R.id.d2_tv_neirong);
			ViewHolder.tv_xianjia = (TextView) convertView.findViewById(R.id.tv_xianjia);
			ViewHolder.tv_yuanjia = (TextView) convertView.findViewById(R.id.tv_yuanjia);
			ViewHolder.tv_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
//			ViewHolder.comment_rating_bar = (RatingBar) convertView.findViewById(R.id.comment_rating_bar);
			ViewHolder.tv_juli = (TextView) convertView.findViewById(R.id.tv_juli);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+(String)faxiandata.get(position).get("couponimage"), ViewHolder.d2_img_layout);
		ViewHolder.d2_tv_title.setText(faxiandata.get(position).get("couponname"));
//		ViewHolder.tv_jiage.setText(faxiandata.get(position).get("perperson"));
		ViewHolder.d2_tv_neirong.setText(faxiandata.get(position).get("coupondetail"));
		ViewHolder.tv_xianjia.setText("￥"+faxiandata.get(position).get("couponsellprice"));
		ViewHolder.tv_yuanjia.setText("￥"+faxiandata.get(position).get("couponprice"));
//		ViewHolder.comment_rating_bar.setRating(Float.parseFloat(faxiandata.get(position).get("price")));
		ViewHolder.tv_juli.setText("距离"+faxiandata.get(position).get("distance")+"km");
		return convertView;
	}
	
	public final class ViewHolder {
		public ImageView d2_img_layout;
		public TextView d2_tv_title;
		public TextView tv_jiage;
		public TextView d2_tv_neirong;
//		public RatingBar comment_rating_bar;
		public TextView tv_xianjia;
		public TextView tv_yuanjia;
		public TextView tv_juli;
    }  
}
