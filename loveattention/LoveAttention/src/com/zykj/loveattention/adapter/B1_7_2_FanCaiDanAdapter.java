package com.zykj.loveattention.adapter;

import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;
import com.zykj.loveattention.adapter.B172InfoAdapter.RefreshExpandableList;
import com.zykj.loveattention.data.FanCaiDan;
import com.zykj.loveattention.view.AutoListView;

public class B1_7_2_FanCaiDanAdapter extends BaseAdapter{
	private Activity context;
	private B172InfoAdapter adapter;
	private org.json.JSONArray fcdlist;
	private RefreshExpandableList refresh;
	Map<String,FanCaiDan> fcdmap;

	public B1_7_2_FanCaiDanAdapter(Activity context,org.json.JSONArray fcdlist,RefreshExpandableList refresh,Map<String,FanCaiDan> fcdmap) {
		this.context = context;
		this.fcdlist = fcdlist;
		this.refresh = refresh;
		this.fcdmap = fcdmap;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fcdlist == null ? 0 : fcdlist.length();
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
		ViewHolder ViewHolder=null;
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.b1_7_2_fancaidan_item, null);
			ViewHolder.tv_xilie=(TextView) convertView.findViewById(R.id.tv_xilie);
			ViewHolder.lv_chanpin=(AutoListView) convertView.findViewById(R.id.lv_chanpin);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		
		try {
			ViewHolder.tv_xilie.setText(fcdlist.getJSONObject(position).getString("name"));
			org.json.JSONArray fcdgoodslist = fcdlist.getJSONObject(position).getJSONArray("goods");
			adapter = new B172InfoAdapter(context, fcdgoodslist,refresh,fcdmap);
			ViewHolder.lv_chanpin.setAdapter(adapter);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	public final class ViewHolder {  
      public TextView tv_xilie;
      public AutoListView lv_chanpin;
    }
}
