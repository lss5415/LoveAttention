package com.zykj.loveattention.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zykj.loveattention.R;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.data.YaoQingRen;

public class B4_5_YaoQingAdapter extends BaseAdapter {
	private Context context;
//	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	private List<YaoQingRen> yaoqingrenlist ;

	public B4_5_YaoQingAdapter(Context context,List<YaoQingRen> yaoqingrenlist) {
		this.context = context;
		this.yaoqingrenlist = yaoqingrenlist;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return yaoqingrenlist == null ? 0 : yaoqingrenlist.size();
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
			convertView=LinearLayout.inflate(context, R.layout.b4_5_yaoqingitem, null);
			ViewHolder.im_headportain=(ImageView) convertView.findViewById(R.id.im_headportain);
			ViewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			ViewHolder.tv_invitenum=(TextView) convertView.findViewById(R.id.tv_invitenum);
			ViewHolder.tv_intime=(TextView) convertView.findViewById(R.id.tv_intime);
			ViewHolder.tv_mobile=(TextView) convertView.findViewById(R.id.tv_mobile);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ViewHolder.tv_name.setText(yaoqingrenlist.get(position).getName());
		ViewHolder.tv_invitenum.setText("邀请"+yaoqingrenlist.get(position).getInviteNum()+"人");
		ViewHolder.tv_intime.setText(yaoqingrenlist.get(position).getIntime());
		ViewHolder.tv_mobile.setText(yaoqingrenlist.get(position).getMobile());
		ImageLoader.getInstance().displayImage(AppValue.ImgUrl+yaoqingrenlist.get(position).getMobile(), ViewHolder.im_headportain);
		return convertView;
	}
	
	public final class ViewHolder {  
		public ImageView im_headportain;
        public TextView tv_name;
        public TextView tv_invitenum;
        public TextView tv_intime;
        public TextView tv_mobile;
    }  
}
