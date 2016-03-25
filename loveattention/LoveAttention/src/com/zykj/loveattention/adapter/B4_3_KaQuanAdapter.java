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

import com.zykj.loveattention.R;
import com.zykj.loveattention.ui.B4_3_KaQuanInfoActivity;

public class B4_3_KaQuanAdapter extends BaseAdapter {
	private Context context;
	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	//存储随机的颜色的数组
	String [ ] colors = {"#8B8DE5","#C490E5","#044C5E","#E28071","#EDC02F",
			"#7A52F2","#D870B4","#83EADC","#E29C66","#1B66E8"};
	public B4_3_KaQuanAdapter(Context context,List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.e("size", data.size()+"");
		return data == null ? 0 : data.size();
//		return 5;
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
			convertView=LinearLayout.inflate(context, R.layout.b4_3_kaquanitem, null);
			ViewHolder.tv_diyongquan=(TextView) convertView.findViewById(R.id.tv_diyongquan);
			ViewHolder.tv_temai=(TextView) convertView.findViewById(R.id.tv_temai);
			ViewHolder.tv_youxiaoqi=(TextView) convertView.findViewById(R.id.tv_youxiaoqi);
			ViewHolder.rl_shang=(RelativeLayout) convertView.findViewById(R.id.rl_shang);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		String couponname = data.get(position).get("couponname");
		String name = data.get(position).get("name");
		String effecttime = data.get(position).get("effecttime");
//		String couponintroduct = data.get(position).get("couponintroduct");
//		String state = data.get(position).get("state");
//		String overdue = data.get(position).get("overdue");
//		String imgpath = data.get(position).get("imgpath");
//		String couponcolor = data.get(position).get("couponcolor");
//		String telephone = data.get(position).get("telephone");
		String couponid = data.get(position).get("couponid");
//		ViewHolder.rl_shang.setBackgroundColor(android.graphics.Color.parseColor("#F39292"));
		//生成一个随机的背景色
		ViewHolder.rl_shang.setBackgroundColor(android.graphics.Color.parseColor(colors[(int)(Math.random()*10)]));
		ViewHolder.tv_diyongquan.setText(couponname);
		ViewHolder.tv_temai.setText(name);
		ViewHolder.tv_youxiaoqi.setText("有效期："+effecttime);
		
		convertView.setOnClickListener(new getDetail(couponid));
		return convertView;
	}
	/**
	 * 跳转到卡券详情
	 * @author lss
	 *
	 */
	class getDetail implements View.OnClickListener {
//		String couponname,name,effecttime,couponintroduct,state,overdue,imgpath,couponcolor,telephone,couponid;
		String couponid;
		public getDetail(String couponid) {
//			this.couponname = couponname;//卡券名
//			this.name = name;//店家名
//			this.effecttime = effecttime;//有效时间
//			this.couponintroduct = couponintroduct;//使用须知
//			this.state = state;//卡券状态
//			this.overdue = overdue;
//			this.imgpath = imgpath;
//			this.couponcolor = couponcolor;
//			this.telephone = telephone;
			this.couponid = couponid;
		}
		@Override
		public void onClick(View arg0) {
			Intent itkaquan1 = new Intent(context, B4_3_KaQuanInfoActivity.class);
			Bundle bundle = new Bundle();
//			bundle.putString("couponname", couponname);
//			bundle.putString("name", name);
//			bundle.putString("effecttime", effecttime);
//			bundle.putString("couponintroduct", couponintroduct);
//			bundle.putString("state", state);
//			bundle.putString("overdue", overdue);
//			bundle.putString("imgpath", imgpath);
//			bundle.putString("couponcolor", couponcolor);
//			bundle.putString("telephone", telephone);
			bundle.putString("couponid", couponid);
			itkaquan1.putExtra("data",bundle);
			context.startActivity(itkaquan1);
			
		}
	}
	public final class ViewHolder {  
        public TextView tv_diyongquan;//代金券名称
        public TextView tv_temai;
        public TextView tv_youxiaoqi;
        public RelativeLayout rl_shang;
    }  
	
}
