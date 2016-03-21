package com.zykj.loveattention.adapter;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B172InfoAdapter.RefreshExpandableList;
import com.zykj.loveattention.adapter.B172InfoAdapter.getAddGouWu;
import com.zykj.loveattention.data.FanCaiDan;

public class FanCaiDanXuanZeAdapter extends BaseAdapter{
	private Activity context;
	private B172InfoAdapter adapter;
	private org.json.JSONArray fcdlist;
	private RefreshExpandableList refresh;
	private Map<String,FanCaiDan> fcdmap = new HashMap<String,FanCaiDan>();//翻菜单内列表的数据
	private String a;

	public FanCaiDanXuanZeAdapter(Activity context,Map<String,FanCaiDan> fcdmap,String a) {
		this.context = context;
		this.fcdmap = fcdmap;
		this.a=a;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int a = fcdmap.values().size();
		return a;
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
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder ViewHolder=null;
		String arr[] = a.split(",");
		final String ap = arr[position];
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.fancaidanxuanze_item, null);
//			ViewHolder.tv_xilie=(TextView) convertView.findViewById(R.id.tv_xilie);
//			ViewHolder.lv_chanpin=(AutoListView) convertView.findViewById(R.id.lv_chanpin);
			ViewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			ViewHolder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
			ViewHolder.tv_geshu=(TextView) convertView.findViewById(R.id.tv_geshu);
			ViewHolder.im_jian=(ImageView) convertView.findViewById(R.id.im_jian);
			ViewHolder.im_jia=(ImageView) convertView.findViewById(R.id.im_jia);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		try {
			ViewHolder.tv_name.setText(fcdmap.get(ap).getName());
			ViewHolder.tv_price.setText(fcdmap.get(ap).getJiage());
			int aa = fcdmap.get(ap).getShuliang();
			ViewHolder.tv_geshu.setText(aa+"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		ViewHolder.im_jia.setOnClickListener(new getJia(position,ap));
		ViewHolder.im_jian.setOnClickListener(new getJian(position,ap));
		return convertView;
	}

	//购物车加
	class getJia implements View.OnClickListener {
		int position;
		String ap;
		public getJia(int position,String ap) {
//			this.vh = vh;//价格
			this.position = position;
			this.ap = ap;
		}
		@Override
		public void onClick(View arg0) {		
			try {
				int num = fcdmap.get(ap).getShuliang();
				int b = num+1;
//				vh.tv_geshu.setText(b+"");
				fcdmap.get(ap).setShuliang(b);
				notifyDataSetChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	//购物车减
	class getJian implements View.OnClickListener {
		int position;
		String ap;
		public getJian(int position,String ap) {
			this.ap = ap;//价格
			this.position = position;
		}
		@Override
		public void onClick(View arg0) {
			try {	
				int num = fcdmap.get(ap).getShuliang();
				if (num>1) {
					int b = num-1;
					fcdmap.get(ap).setShuliang(b);
					notifyDataSetChanged();
				}else {
//					Toast.makeText(context, "已经最低，不能再减少了", Toast.LENGTH_LONG).show();
					fcdmap.remove(ap);
					notifyDataSetChanged();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public final class ViewHolder {  
		public TextView tv_name;
		public TextView tv_price;
		public TextView tv_geshu;
		public ImageView im_jian;
		public ImageView im_jia;
    }
}
