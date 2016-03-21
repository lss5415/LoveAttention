package com.zykj.loveattention.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zykj.loveattention.R;

public class XiaoXiAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, String>> listdata = new ArrayList<Map<String, String>>();

	public XiaoXiAdapter(Context context,List<Map<String, String>> listdata) {
		this.context = context;
		this.listdata = listdata;
	}
	@Override
    public int getCount() {
        return listdata.size();
    }
    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder ViewHolder=null;
		if(convertView==null){
			ViewHolder=new ViewHolder();
			convertView=LinearLayout.inflate(context, R.layout.manager_group_list_item_parent, null);
			ViewHolder.tv_gonggao=(TextView) convertView.findViewById(R.id.tv_gonggao);
			convertView.setTag(ViewHolder);
		}else{
			ViewHolder=(ViewHolder) convertView.getTag();
		}
		ViewHolder.tv_gonggao.setText(listdata.get(position).get("messagetitle"));
		return convertView;
	}
	
	public final class ViewHolder {  
        public TextView tv_gonggao;
    }

}
